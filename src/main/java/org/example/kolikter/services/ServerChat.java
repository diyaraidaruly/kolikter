
package org.example.kolikter.services;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerChat {

    private static final Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) {
        int port = 6000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());
                ClientHandler handler = new ClientHandler(socket);
                clientHandlers.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String name;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Enter your name:");
                name = in.readLine();
                out.println("Welcome to the chat, " + name + "!");
                broadcast(name + " has joined the chat.", this);

                String message;
                while ((message = in.readLine()) != null) {
                    broadcast(name + ": " + message, this);
                }

            } catch (IOException e) {
                System.out.println("Connection with " + name + " lost.");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clientHandlers.remove(this);
                broadcast(name + " has left the chat.", this);
            }
        }

        void sendMessage(String message) {
            out.println(message);
        }
    }
}

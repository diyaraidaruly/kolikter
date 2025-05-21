package org.example.kolikter.services;

import org.example.kolikter.DAO.UserDAO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String username = in.readLine();
                System.out.println("Username received: " + username);

                UserDAO userDAO = new UserDAO();
                String password = userDAO.getPasswordByUsername(username); // Бұл әдіс UserDAO-да бар болуы керек

                if (password != null) {
                    out.println("Password: " + password);
                } else {
                    out.println("User not found.");
                }

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

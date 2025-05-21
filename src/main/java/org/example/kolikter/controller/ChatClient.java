
package org.example.kolikter.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;

public class ChatClient {

    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Chat");
    private JTextArea messageArea = new JTextArea();
    private JTextField textField = new JTextField();
    private String name;

    public ChatClient() {
        // UI
        messageArea.setEditable(false);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.add(textField, BorderLayout.SOUTH);
        frame.setVisible(true);

        textField.addActionListener((ActionEvent e) -> {
            out.println(textField.getText());
            textField.setText("");
        });
    }

    private void run() throws IOException {
        Socket socket = new Socket("localhost", 6000);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        name = JOptionPane.showInputDialog(frame, "Enter your name:");
        out.println(name);

        new Thread(() -> {
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    messageArea.append(line + "\n");
                }
            } catch (IOException ex) {
                messageArea.append("Connection closed.\n");
            }
        }).start();
    }

    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient();
        client.run();
    }
}

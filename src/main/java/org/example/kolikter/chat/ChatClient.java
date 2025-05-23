package org.example.kolikter.chat;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatClient implements Runnable {
    public static String username;
    private BufferedWriter writer;
    private BufferedReader reader;

    @Override
    public void run() {
        Platform.runLater(this::openChatWindow);
    }

    public void openChatWindow() {
        Stage chatStage = new Stage();

        VBox root = new VBox(10);
        root.setSpacing(10);

        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);

        TextField inputField = new TextField();
        inputField.setPromptText("Enter");
        Button sendButton = new Button("Send");

        root.getChildren().addAll(chatArea, inputField, sendButton);

        sendButton.setOnAction(e -> {
            String message = inputField.getText();
            try {
                if (message.trim().isEmpty()) return;

                chatArea.appendText("You: " + message + "\n");

                writer.write(message + "\n");
                writer.flush();
                inputField.clear();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(root, 400, 300);
        chatStage.setScene(scene);
        chatStage.setTitle("СHAT  " + username);
        chatStage.show();

        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 5000);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    final String msg = line;
                    Platform.runLater(() -> chatArea.appendText(msg + "\n"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
//package org.example.kolikter.chat;
//
//import javafx.application.Platform;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.stage.Stage;
//
//import java.io.*;
//import java.net.Socket;
//
//public class ChatClient implements Runnable {
//    public static String username;
//    private BufferedWriter writer;
//    private BufferedReader reader;
//
//    @Override
//    public void run() {
//        Platform.runLater(this::openChatWindow);
//    }
//
//
//    public void openChatWindow() {
//        Stage chatStage = new Stage();
//
//        VBox messageContainer = new VBox(10);
//        messageContainer.getStyleClass().add("chat-message-container");
//        messageContainer.setPadding(new Insets(10));
//
//        ScrollPane scrollPane = new ScrollPane(messageContainer);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//
//        TextField inputField = new TextField();
//        inputField.getStyleClass().add("chat-input");
//        inputField.setPromptText("Enter your message");
//
//        Button sendButton = new Button("Send");
//        sendButton.getStyleClass().add("chat-send-button");
//
//        HBox inputBox = new HBox(10, inputField, sendButton);
//        inputBox.setPadding(new Insets(10));
//
//        VBox root = new VBox(10, scrollPane, inputBox);
//
//        sendButton.setOnAction(e -> {
//            String message = inputField.getText();
//            if (message.trim().isEmpty()) return;
//
//            sendMessage(message, messageContainer);
//            try {
//                writer.write(username + ": " + message + "\n");
//                writer.flush();
//                inputField.clear();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        });
//
//        Scene scene = new Scene(root, 450, 350);
//        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
//        chatStage.setScene(scene);
//        chatStage.setTitle("CHAT - " + username);
//        chatStage.show();
//        new Thread(() -> {
//            try {
//                Socket socket = new Socket("localhost", 5000);
//                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//                // Сервер "Enter your name:" деп сұрағанда оқып, жауап қайтарамыз
//                reader.readLine(); // "Enter your name:"
//                writer.write(username + "\n");
//                writer.flush();
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    final String msg = line;
//                    Platform.runLater(() -> receiveMessage(msg, messageContainer));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//    }
//
//    private void sendMessage(String message, VBox container) {
//        Label label = new Label("You: " + message);
//        label.getStyleClass().add("my-message");
//        label.setWrapText(true);
//
//        HBox wrapper = new HBox(label);
//        wrapper.setAlignment(Pos.CENTER_RIGHT);
//        container.getChildren().add(wrapper);
//    }
//
//    private void receiveMessage(String message, VBox container) {
//        if (message.startsWith(username + ":")) return; // өз хабарламасын қайталап көрсетпеу үшін
//
//        Label label = new Label(message);
//        label.getStyleClass().add("other-message");
//        label.setWrapText(true);
//
//        HBox wrapper = new HBox(label);
//        wrapper.setAlignment(Pos.CENTER_LEFT);
//        container.getChildren().add(wrapper);
//    }
//}

package org.example.kolikter.chat;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

        chatStage.getIcons().add(new Image(getClass().getResourceAsStream("/org/example/kolikter/comments_531318.png")));

        Image icon = new Image(getClass().getResourceAsStream("/org/example/kolikter/send_14359825.png"));
        ImageView imageView = new ImageView(icon);

        // Суреттің өлшемдерін орнату (қажет болса)
        imageView.setFitWidth(20);  // Ені
        imageView.setFitHeight(20);

        VBox root = new VBox(10);
        root.getStyleClass().add("vbox");
        root.setSpacing(10);

        TextArea chatArea = new TextArea();
        chatArea.getStyleClass().add("text-area");
        chatArea.setEditable(false);
        chatArea.setPrefWidth(100);

        TextField inputField = new TextField();
        inputField.getStyleClass().add("text-field");
        inputField.setPromptText("Enter");

        Button sendButton = new Button("",imageView);
        sendButton.getStyleClass().add("button");


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
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
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

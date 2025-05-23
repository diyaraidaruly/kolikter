package org.example.kolikter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.kolikter.chat.ChatClient;
import org.example.kolikter.controller.LoginInterface;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginInterface loginInterface = new LoginInterface();

        BorderPane root = new BorderPane();
        root.setCenter(loginInterface.login());
        root.getStyleClass().add("root");

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setTitle("Kolikter");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Чат терезелерін параллель түрде ашу
//        openChatWindow("Admin");
//        openChatWindow("User");
    }

    public static void openChatWindow(String username) {
        new Thread(() -> Platform.runLater(() -> {
            try {
                ChatClient.username = username;
                new ChatClient().openChatWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        })).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

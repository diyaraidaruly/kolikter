package org.example.kolikter;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.kolikter.controller.CarController;
import org.example.kolikter.controller.LoginInterface;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginInterface loginInterface = new LoginInterface();

        BorderPane root = new BorderPane();
        root.setCenter(loginInterface.login());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Көлік тізімі");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}

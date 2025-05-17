package org.example.kolikter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;

import org.example.kolikter.controller.CarController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        CarController carController = new CarController();

        VBox root = carController.getView();

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Көлік тізімі");
        primaryStage.setScene(scene);
        primaryStage.show();

        carController.loadAllCars();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

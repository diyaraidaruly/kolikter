package org.example.kolikter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.kolikter.controller.CarController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Жоғарғы меню панелі
        HBox topMenu = new HBox(10);
        Button showCarsButton = new Button("Cars");
        topMenu.getChildren().add(showCarsButton);
        root.setTop(topMenu);

        // Көлік контроллері
        CarController carController = new CarController();

        // Батырма басылғанда ғана көліктер тізімін көрсет
        showCarsButton.setOnAction(e -> {
            root.setCenter(carController.getView());
            carController.loadAllCars();
        });

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Көлік тізімі");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

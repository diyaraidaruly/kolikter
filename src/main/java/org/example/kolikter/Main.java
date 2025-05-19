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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        CarController carController = new CarController();

        // Ең бірінші экранға кіру батырмасы
        Button loginBtn = new Button("Кіру");
        VBox centerBox = new VBox(loginBtn);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);
        root.setCenter(centerBox);

        // Пароль енгізу өрісі мен кіру батырмасы (алғашында жасырын)
        TextField passwordField = new TextField();
        passwordField.setPromptText("Парольді енгізіңіз");
        Button enterBtn = new Button("Кіру");
        Text errorText = new Text();
        errorText.setFill(Color.RED);

        VBox passwordBox = new VBox(passwordField, enterBtn, errorText);
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.setSpacing(5);
        passwordBox.setVisible(false);

        // Cars батырмасы (алғашында жасырын)
        Button showCarsButton = new Button("Cars");
        showCarsButton.setVisible(false);

        // Кіру батырмасын басқанда пароль енгізу өрісі мен кіру батырмасын көрсету
        loginBtn.setOnAction(e -> {
            centerBox.getChildren().clear();
            passwordField.clear();
            errorText.setText("");
            passwordBox.setVisible(true);
            centerBox.getChildren().add(passwordBox);
        });

        // Парольді тексеріп кіру батырмасын басқанда
        enterBtn.setOnAction(e -> {
            if ("0000".equals(passwordField.getText())) {
                errorText.setText("");
                passwordBox.setVisible(false);
                centerBox.getChildren().clear();
                showCarsButton.setVisible(true);
                centerBox.getChildren().add(showCarsButton);
            } else {
                errorText.setText("Қате пароль! Қайтадан енгізіңіз.");
            }
        });

        // Cars батырмасын басқанда көліктер тізімін көрсету
        showCarsButton.setOnAction(e -> {
            root.setCenter(carController.getView());
            carController.showTableAndFilter();
            carController.loadAllCars();
            showCarsButton.setVisible(false);
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

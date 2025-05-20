package org.example.kolikter.controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.kolikter.services.UserService;
import org.example.kolikter.model.User;

public class LoginInterface {
    public Node login() {
        CarController carController = new CarController();
        UserController userController = new UserController();

        Button loginBtn = new Button("Кіру");
        VBox centerBox = new VBox(loginBtn);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);

        // Пароль енгізу өрісі мен кіру батырмасы (алғашында жасырын)
        TextField passwordField = new TextField();
        passwordField.setPromptText("Парольді енгізіңіз");
        passwordField.setMaxSize(150,25);

        //field for login
        TextField loginField = new TextField();
        loginField.setPromptText("Login ");
        loginField.setMaxSize(150,25);

        //Button for logging in after entering the password
        Button enterBtn = new Button("Кіру");
        Text errorText = new Text();
        errorText.setFill(Color.RED);

        //interface for entering a password
        VBox passwordBox = new VBox(loginField, passwordField, enterBtn, errorText);
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.setSpacing(15);
        passwordBox.setVisible(false);

        // Cars батырмасы (алғашында жасырын)
        Button showCarsButton = new Button("Cars");
        showCarsButton.setVisible(false);

        Button users = new Button("Users");
        users.setVisible(false);

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
            String username = loginField.getText();
            String password = passwordField.getText();

            UserService userService = new UserService();
            if (userService.checkUserLogin(username, password)) {
                errorText.setText("");
                passwordBox.setVisible(false);
                centerBox.getChildren().clear();
                showCarsButton.setVisible(true);
                centerBox.getChildren().add(showCarsButton);
            } else if(userService.checkAdminLogin( password)) {
                User.isAdmin = true;
                errorText.setText("");
                passwordBox.setVisible(false);
                centerBox.getChildren().clear();
                showCarsButton.setVisible(true);
                users.setVisible(true);
                centerBox.getChildren().add(showCarsButton);
                centerBox.getChildren().add(users);

            } else {
                errorText.setText("Қате логин немесе пароль!");
            }
        });

        // Cars батырмасын басқанда көліктер тізімін көрсету
        showCarsButton.setOnAction(e -> {
            centerBox.getChildren().add(carController.getView());
            carController.showTableAndFilter();
            carController.loadAllCars();
            showCarsButton.setVisible(false);
        });

        users.setOnAction(e -> {
            centerBox.getChildren().add(userController.getView());
        });

        return centerBox;
    }

}

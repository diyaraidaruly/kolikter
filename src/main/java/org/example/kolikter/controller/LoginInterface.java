package org.example.kolikter.controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.kolikter.services.ServerChat;
import org.example.kolikter.services.UserService;
import org.example.kolikter.model.User;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginInterface {
    public Node login() {
        CarController carController = new CarController();
        UserController userController = new UserController();

        // Forget Password button
        Button forgetPasswordButton = new Button("Forget Password");
        forgetPasswordButton.setOnAction(e -> {
            String username = JOptionPane.showInputDialog(null, "Enter your username:");
            if (username != null && !username.trim().isEmpty()) {
                try (Socket socket = new Socket("localhost", 5000);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    out.println(username);
                    String response = in.readLine();
                    JOptionPane.showMessageDialog(null, response);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Server error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username cannot be empty.");
            }
        });


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
        VBox passwordBox = new VBox(loginField, passwordField, enterBtn,forgetPasswordButton, errorText);
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.setSpacing(15);
        passwordBox.setVisible(true);

        // Cars and users are invisible at first
        Button showCarsButton = new Button("Cars");
        showCarsButton.setVisible(false);

        Button users = new Button("Users");
        users.setVisible(false);

        VBox centerBox = new VBox(passwordBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);


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
            } else if (userService.checkAdminLogin(password)) {
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

            VBox carTable = new VBox();
            carTable.setAlignment(Pos.TOP_CENTER);
            Button chatButton = new Button("Open Chat");
            if(User.isAdmin) {
                carTable.getChildren().addAll(users, carController.getView(), chatButton);
            }else{
                carTable.getChildren().addAll(carController.getView(), chatButton);
            }
            centerBox.getChildren().clear();
            centerBox.getChildren().add(carTable);
            carController.showTableAndFilter();
            carController.loadAllCars();
            showCarsButton.setVisible(false);
            users.setVisible(true);
            chatButton.setOnAction(e2 -> {
                try {

                    ChatClient.main(null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });


        });

        users.setOnAction(e -> {
            VBox userTable = new VBox();
            userTable.setAlignment(Pos.TOP_CENTER);
            userTable.getChildren().addAll(showCarsButton,userController.getView());
            centerBox.getChildren().clear();
            centerBox.getChildren().add(userTable);
            users.setVisible(false);
            showCarsButton.setVisible(true);
        });


        return centerBox;
    }

}

package org.example.kolikter.controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.kolikter.Main;
import org.example.kolikter.chat.ChatClient;
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

        // Registration button
        Button registerButton = new Button("Sign up");
        registerButton.getStyleClass().add("login-button");

        // Login fields
        TextField loginField = new TextField();
        loginField.setPromptText("Login");
        loginField.setMaxSize(150, 25);

        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter password");
        passwordField.setMaxSize(150, 25);

        Button enterBtn = new Button("Sign in");
        Text errorText = new Text();
        errorText.setFill(Color.RED);

        // Title text
        Text titleText = new Text("KOLIKTER.KZ");
        titleText.getStyleClass().add("title-text");

        // Login interface
        VBox loginBox = new VBox( titleText, loginField, passwordField, enterBtn, registerButton, forgetPasswordButton, errorText);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setSpacing(15);
        loginBox.setVisible(true);
        loginBox.getStyleClass().add("login-box");

        loginField.getStyleClass().add("login-field");
        passwordField.getStyleClass().add("login-field");
        enterBtn.getStyleClass().add("login-button");

        // Registration form fields
        TextField regFullNameField = new TextField();
        regFullNameField.setPromptText("name");
        regFullNameField.setMaxSize(150, 25);

        TextField regAgeField = new TextField();
        regAgeField.setPromptText("age");
        regAgeField.setMaxSize(150, 25);

        TextField regCityField = new TextField();
        regCityField.setPromptText("city");
        regCityField.setMaxSize(150, 25);

        TextField regPhoneField = new TextField();
        regPhoneField.setPromptText("Phone number");
        regPhoneField.setMaxSize(150, 25);

        TextField regLoginField = new TextField();
        regLoginField.setPromptText("username");
        regLoginField.setMaxSize(150, 25);

        TextField regPasswordField = new TextField();
        regPasswordField.setPromptText("password");
        regPasswordField.setMaxSize(150, 25);

        TextField regConfirmPasswordField = new TextField();
        regConfirmPasswordField.setPromptText("confirm password");
        regConfirmPasswordField.setMaxSize(150, 25);

        Button submitRegButton = new Button("Sign up");
        Text regErrorText = new Text();
        regErrorText.setFill(Color.RED);

        Button regBackButton = new Button("Back");


        // Registration interface
        VBox registrationBox = new VBox(titleText, regBackButton, regFullNameField, regAgeField, regCityField,
            regPhoneField, regLoginField, regPasswordField, regConfirmPasswordField, submitRegButton, regErrorText);
        registrationBox.setAlignment(Pos.CENTER);
        registrationBox.setSpacing(15);
        registrationBox.setVisible(false);
        registrationBox.getStyleClass().add("login-box");

        // Style registration fields
        regFullNameField.getStyleClass().add("login-field");
        regAgeField.getStyleClass().add("login-field");
        regCityField.getStyleClass().add("login-field");
        regPhoneField.getStyleClass().add("login-field");
        regLoginField.getStyleClass().add("login-field");
        regPasswordField.getStyleClass().add("login-field");
        regConfirmPasswordField.getStyleClass().add("login-field");
        submitRegButton.getStyleClass().add("login-button");

        // Cars and users buttons
        Button showCarsButton = new Button("Cars");
        showCarsButton.setVisible(false);

        Button users = new Button("Users");
        users.setVisible(false);

        VBox centerBox = new VBox(loginBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);

        VBox centerBox2 = new VBox(registrationBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);

        // Registration button action
        registerButton.setOnAction(e -> {
            centerBox.getChildren().clear();
            centerBox.getChildren().add(centerBox2);
            loginBox.setVisible(false);
            registrationBox.setVisible(true);
        });

        regBackButton.setOnAction(e -> {
            centerBox.getChildren().clear();
            centerBox.getChildren().add(loginBox);
            registrationBox.setVisible(false);
            loginBox.setVisible(true);
        });

        // Submit registration action
        submitRegButton.setOnAction(e -> {
            try {
                User newUser = new User();
                newUser.setFull_name(regFullNameField.getText());
                newUser.setAge(Integer.parseInt(regAgeField.getText()));
                newUser.setCity(regCityField.getText());
                newUser.setPhone_num(regPhoneField.getText());
                newUser.setLogin(regLoginField.getText());
                if(regPasswordField.getText().equals(regConfirmPasswordField.getText())){
                    newUser.setPassword(regPasswordField.getText());
                } else {
                    regErrorText.setText("Passwords don`t match!");
                }

                UserService userService = new UserService();
                if (userService.registerUser(newUser)) {
                    regErrorText.setText("");
                    registrationBox.setVisible(false);
                    loginBox.setVisible(true);
                    Alert.AlertType alertType = Alert.AlertType.INFORMATION;
                    Alert alert = new Alert(alertType, "User registered successfully!");
                    alert.showAndWait();
                    regFullNameField.clear();
                    regAgeField.clear();
                    regCityField.clear();
                    regPhoneField.clear();
                    regLoginField.clear();
                    regPasswordField.clear();
                    regConfirmPasswordField.clear();
                    regErrorText.setText("");
                    regFullNameField.requestFocus();


                } else {
                    regErrorText.setText("Error!");
                }
            } catch (NumberFormatException ex) {
                regErrorText.setText("Enter correct age!");
            } catch (Exception ex) {
                regErrorText.setText("Error!");
                ex.printStackTrace();
            }
        });

        // Login button action
        enterBtn.setOnAction(e -> {
            String username = loginField.getText();
            String password = passwordField.getText();

            UserService userService = new UserService();
            if (userService.checkUserLogin(username, password)) {
                errorText.setText("");
                loginBox.setVisible(false);
                centerBox.getChildren().clear();
                showCarsButton.setVisible(true);
                centerBox.getChildren().add(showCarsButton);

                ChatClient.username = username;
            } else if (userService.checkAdminLogin(password)) {
                User.isAdmin = true;
                errorText.setText("");
                loginBox.setVisible(false);
                centerBox.getChildren().clear();
                showCarsButton.setVisible(true);
                users.setVisible(true);
                centerBox.getChildren().add(showCarsButton);
                centerBox.getChildren().add(users);
            } else {
                errorText.setText("Username or password is incorrect!");
            }
        });

        // Cars button action
//        public void onShowCarsButton(){
//            VBox carTable = new VBox();
//            carTable.setAlignment(Pos.TOP_CENTER);
//
//            Button chatButton = new Button("Open Chat");
//
//            if(User.isAdmin) {
//                carTable.getChildren().addAll(users, carController.getView());
//            } else {
//                carTable.getChildren().addAll(carController.getView(), chatButton);
//            }
//
//            centerBox.getChildren().clear();
//            centerBox.getChildren().add(carTable);
//            carController.showTableAndFilter();
//            carController.loadAllCars();
//            showCarsButton.setVisible(false);
//            users.setVisible(true);
//
//            chatButton.setOnAction(e2 -> {
//                try {
//                    if(!User.isAdmin){
//                        Main.openChatWindow("Admin");
//                        Main.openChatWindow(loginField.getText());
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            });
//        }

        showCarsButton.setOnAction(e -> {
            onShowCarsButton(centerBox, carController, users, showCarsButton, loginField);
        });


        // Users button action
        users.setOnAction(e -> {
            VBox userTable = new VBox();
            userTable.setAlignment(Pos.TOP_CENTER);
            userTable.getChildren().addAll(showCarsButton, userController.getView());
            centerBox.getChildren().clear();
            centerBox.getChildren().add(userTable);
            users.setVisible(false);
            showCarsButton.setVisible(true);
        });

        return centerBox;
    }
    private void onShowCarsButton(VBox centerBox, CarController carController, Button users, Button showCarsButton, TextField loginField) {
        VBox carTable = new VBox();
        carTable.setAlignment(Pos.TOP_CENTER);

        Button chatButton = new Button("Open Chat");

        if(User.isAdmin) {
            carTable.getChildren().addAll(users, carController.getView());
        } else {
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
                if(!User.isAdmin){
                    Main.openChatWindow("Admin");
                    Main.openChatWindow(loginField.getText());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

}

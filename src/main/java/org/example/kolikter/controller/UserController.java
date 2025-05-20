package org.example.kolikter.controller;

import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.example.kolikter.model.User;


public class UserController {

    private TableView<User> usersTable = new TableView();

    public Node getView() {
        VBox layout = new VBox();
        // Бағандарды жасаймыз
        TableColumn<User, String> nameCol = new TableColumn<>("Толық аты");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("full_name"));

        TableColumn<User, Integer> ageCol = new TableColumn<>("Жасы");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<User, String> cityCol = new TableColumn<>("Қала");
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<User, String> phoneCol = new TableColumn<>("Телефон");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone_num"));

        TableColumn<User, String> loginCol = new TableColumn<>("Логин");
        loginCol.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<User, String> passCol = new TableColumn<>("Пароль");
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));

        usersTable.getColumns().addAll(nameCol, ageCol, cityCol, phoneCol, loginCol, passCol);

        layout.getChildren().addAll(usersTable);

        return layout;
    }
}

package org.example.kolikter.controller;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.example.kolikter.model.User;
import org.example.kolikter.services.UserService;


public class UserController {
    UserService userService = new UserService();
    private final java.util.List<User> userList = userService.getUsers();

    private TableView<User> usersTable = new TableView();

    public Node getView() {
        VBox layout = new VBox();
        Button delete = new Button("delete");

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

        delete.setOnAction(e -> deleteUser());

        return layout;
    }

    private void deleteUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            try {
                userService.deleteUser(selectedUser.getLogin());
                userList.remove(selectedUser);
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "Қолданушыны өшіру кезінде қате.");
            }
        } else {
            showAlert("Info", "Алдымен қолданушыны таңдаңыз.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

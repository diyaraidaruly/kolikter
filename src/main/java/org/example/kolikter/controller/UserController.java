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

import java.util.List;

public class UserController {
    UserService userService = new UserService();
    private final List<User> userList = userService.getUsers();

    private final TableView<User> usersTable = new TableView<>();

    public Node getView() {
        VBox layout = new VBox();
        Button delete = new Button("delete");

        // Table columns
        TableColumn<User, String> nameCol = new TableColumn<>("Full name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("full_name"));

        TableColumn<User, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<User, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<User, String> phoneCol = new TableColumn<>("Phone number:");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone_num"));

        TableColumn<User, String> loginCol = new TableColumn<>("Login");
        loginCol.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<User, String> passCol = new TableColumn<>("Password");
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));

        usersTable.getColumns().addAll(nameCol, ageCol, cityCol, phoneCol, loginCol, passCol);

        usersTable.getItems().setAll(userList);

        layout.getChildren().addAll(usersTable, delete);

        delete.setOnAction(e -> deleteUser());

        return layout;
    }

    private void deleteUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            try {
                userService.deleteUser(selectedUser.getLogin());
                usersTable.getItems().remove(selectedUser);
                userList.remove(selectedUser);
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "Error during deleting user.");
            }
        } else {
            showAlert("Info", "Select user to delete.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

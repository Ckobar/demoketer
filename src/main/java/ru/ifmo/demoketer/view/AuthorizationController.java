package ru.ifmo.demoketer.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.ifmo.demoketer.Main;


/**
 * Имя пользователя
 * Пароль
 */

public class AuthorizationController {

    private Main main;
    private Stage dialogStage;
    private boolean okClicked = false;
    private Stage primaryStage;

    @FXML
    private TextField textLogin;
    @FXML
    private TextField textPassword;

    @FXML
    private void initialize() {

    }

    public void setMainApp(Main main) {
        this.main = main;
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void buttonLogin() {
        if(textLogin.getText().equals("keter") && textPassword.getText().equals("keter")){
            okClicked = true;
            dialogStage.close();
        }
        else{
            alert();
        }
    }

    private void alert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(this.primaryStage);
        alert.setTitle("Ошибка авторизации");
        alert.setHeaderText("Неверный логин или пароль");
        alert.showAndWait();
    }

}

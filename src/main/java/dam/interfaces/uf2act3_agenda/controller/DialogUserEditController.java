package dam.interfaces.uf2act3_agenda.controller;

import dam.interfaces.uf2act3_agenda.MainApp;
import dam.interfaces.uf2act3_agenda.model.Person;
import dam.interfaces.uf2act3_agenda.util.DateUtil;
import dam.interfaces.uf2act3_agenda.util.PersonPolicy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DialogUserEditController {

    @FXML
    private TextField txtfFirstName;

    @FXML
    private TextField txtfLastName;

    @FXML
    private TextField txtfStreet;

    @FXML
    private TextField txtfCity;

    @FXML
    private TextField txtfPostalCode;

    @FXML
    private TextField txtfBirthday;

//    @FXML
//    private Button btnOk;
//
//    @FXML
//    private Button btnCancel;

    // ********** UI **********
    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    // ********** Methods **********
    @FXML
    private void initialize() {}

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(txtfFirstName.getText());
            person.setLastName(txtfLastName.getText());
            person.setStreet(txtfStreet.getText());
            person.setPostalCode(Integer.parseInt(txtfPostalCode.getText()));
            person.setCity(txtfCity.getText());
            person.setBirthday(DateUtil.parse(txtfBirthday.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    // ********** Data Validation **********
    private boolean isInputValid() {
        PersonPolicy policy = new PersonPolicy();
        String errorMessage = policy.test(
            txtfFirstName.getText(),
            txtfLastName.getText(),
            txtfStreet.getText(),
            txtfCity.getText(),
            txtfPostalCode.getText(),
            txtfBirthday.getText()
        );
        if (errorMessage.length() == 0)
            return true;
        else {
            error("Invalid Fields", "Please correct invalid fields", errorMessage);
            return false;
        }
    }

    // ********** Getters **********
    public boolean isOkClicked() {
        return okClicked;
    }

    // ********** Setters **********
    public void setPerson(Person person) {
        this.person = person;
        txtfFirstName.setText(person.getFirstName());
        txtfLastName.setText(person.getLastName());
        txtfStreet.setText(person.getStreet());
        txtfCity.setText(person.getCity());
        txtfPostalCode.setText(Integer.toString(person.getPostalCode()));
        txtfBirthday.setText(DateUtil.format(person.getBirthday()));
        txtfBirthday.setPromptText("dd/mm/yyyy");
    }

    // ********** UX Methods **********
    public void showAndWait() {
        dialogStage.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.initOwner(dialogStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void error(String title, String header, String msg) {
        showAlert(Alert.AlertType.ERROR, title, header, msg);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}

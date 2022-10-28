package dam.interfaces.uf2act3_agenda.controller;

import dam.interfaces.uf2act3_agenda.MainApp;
import dam.interfaces.uf2act3_agenda.model.Person;
import dam.interfaces.uf2act3_agenda.util.DateUtil;
import dam.interfaces.uf2act3_agenda.util.PersonPolicy;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a given person.
 *
 * @author jkutkut
 */
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

    // ********** UI **********
    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    // ********** Methods **********
    @FXML
    private void initialize() {}

    /**
     * Handles the OK button.
     */
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

    /**
     * Handles the Cancel button.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    // ********** Data Validation **********

    /**
     * Validates if the current values in the dialog are valid.
     * @return The result of the validation.
     */
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

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // ********** UX Methods **********
    /**
     * Holds the execution until the dialog is closed.
     */
    public void showAndWait() {
        dialogStage.showAndWait();
    }

    /**
     * Shows an error message.
     *
     * @param title The title of the error message.
     * @param header The header of the error message.
     * @param msg The message of the error message.
     */
    public void error(String title, String header, String msg) {
        MainApp.showAlert(dialogStage, Alert.AlertType.ERROR, title, header, msg);
    }
}

package dam.interfaces.uf2act3_agenda.controller;

import dam.interfaces.uf2act3_agenda.model.Person;
import dam.interfaces.uf2act3_agenda.util.DateUtil;
import dam.interfaces.uf2act3_agenda.util.PersonPolicy;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private void initialize() {
    }

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
        // TODO add alert
        return true;
    }

    // ********** Getters **********
    public boolean isOkClicked() {
        return okClicked;
    }

    // ********** Setters **********
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage; // TODO move to constructor?
    }

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
}

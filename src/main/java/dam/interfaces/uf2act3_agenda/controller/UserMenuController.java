package dam.interfaces.uf2act3_agenda.controller;

import dam.interfaces.uf2act3_agenda.MainApp;
import dam.interfaces.uf2act3_agenda.model.Person;
import dam.interfaces.uf2act3_agenda.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserMenuController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application.
    private MainApp mainApp;

    public UserMenuController() {
    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        updateUser(null);

        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateUser(newValue)
        );
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        personTable.setItems(mainApp.getPersonData());
    }

    // ********** UI Methods **********
    private void updateUser(Person p) {
        if (p == null) {
            p = new Person("...", "...");
        }
        firstNameLabel.setText(p.getFirstName());
        lastNameLabel.setText(p.getLastName());
        streetLabel.setText(p.getStreet());
        postalCodeLabel.setText(Integer.toString(p.getPostalCode()));
        cityLabel.setText(p.getCity());
        birthdayLabel.setText(DateUtil.format(p.getBirthday()));
    }

    // ********** UX Methods **********

    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person("", ""); // TODO allow empty fields
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked)
            mainApp.getPersonData().add(tempPerson);
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson == null) {
            mainApp.warn(
                "No Person Selected",
                "Please select a person in the table.",
                ""
            );
            return;
        }
        boolean ok = mainApp.showPersonEditDialog(selectedPerson);
        if (ok)
            updateUser(selectedPerson);
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            mainApp.warn("No selection", "No person selected", "Please select a person in the table.");
            return;
        }
        personTable.getItems().remove(selectedIndex);
    }
}

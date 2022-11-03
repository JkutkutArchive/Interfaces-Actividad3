package dam.interfaces.uf2act3_agenda.controller;

import dam.interfaces.uf2act3_agenda.MainApp;
import dam.interfaces.uf2act3_agenda.model.Person;
import dam.interfaces.uf2act3_agenda.util.DateUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller for the user menu view.
 *
 * @author jkutkut
 */
public class UserMenuController {
    // ********** UI **********
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

    // ********** Attributes **********
    /**
     * Reference to the main application.
     */
    private MainApp mainApp;

    public UserMenuController() {
    }

    /**
     * Initializes the controller class.
     *
     * Note: This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateUser(newValue)
        );

        updateUser(null);
    }

    /**
     * This method allows to have a reference to the main application.
     *
     * Note: Can not be done in the constructor because it will be called before the fxml file is loaded.
     * @param mainApp Reference to the main application.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        personTable.setItems(mainApp.getPersonData());
    }

    // ********** UI Methods **********

    /**
     * Updates the user information in the UI.
     *
     * Note: If the user is not given (null), the data fields are hidden.
     * @param p The user to show.
     */
    private void updateUser(Person p) {
        if (p == null) {
            firstNameLabel.getParent().setVisible(false);
            return;
        }
        firstNameLabel.getParent().setVisible(true);

        firstNameLabel.setText(p.getFirstName());
        lastNameLabel.setText(p.getLastName());
        streetLabel.setText(p.getStreet());
        postalCodeLabel.setText(Integer.toString(p.getPostalCode()));
        cityLabel.setText(p.getCity());
        birthdayLabel.setText(DateUtil.format(p.getBirthday()));
    }

    // ********** UX Methods **********

    /**
     * Called when the new button is clicked.
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked)
            mainApp.getPersonData().add(tempPerson);
    }

    /**
     * Called when the edit button is clicked.
     */
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

    /**
     * Called when the delete button is clicked.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            mainApp.error("No selection", "No person selected", "Please select a person in the table.");
            return;
        }
        boolean ok = mainApp.confirm(
            "Delete Person",
            "Are you sure you want to delete this person?",
            "This action can not be undone."
        );
        if (!ok)
            return;
        personTable.getItems().remove(selectedIndex);
    }
}

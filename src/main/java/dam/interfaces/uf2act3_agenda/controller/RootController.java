package dam.interfaces.uf2act3_agenda.controller;
import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

// import org.controlsfx.dialog.Dialogs; // TODO

import dam.interfaces.uf2act3_agenda.MainApp;

/**
 * Controller for the root layout.
 *
 * @author jkutkut
 */
public class RootController {
    private MainApp mainApp;

    // ********** UI **********
    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)",
                "*.xml"
        );
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPeople(file.getAbsolutePath(), true);
        }
    }

    @FXML
    private void handleSave() {
        String personFileName = mainApp.getPersonFilePath();
        if (personFileName != null) {
            mainApp.savePeople(personFileName);
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
            "XML files (*.xml)",
            "*.xml"
        );
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePeople(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleAbout() {
        mainApp.info(MainApp.APP_NAME, "About", "Author: Jkutkut");
    }

    @FXML
    private void handleExit() {
        boolean exit = mainApp.confirm(MainApp.APP_NAME, "Exit", "Are you sure you want to exit?");
        if (exit)
            System.exit(0);
    }

    // ********** Setters **********
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}

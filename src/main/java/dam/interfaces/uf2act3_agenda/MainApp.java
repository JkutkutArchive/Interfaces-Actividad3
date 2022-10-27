package dam.interfaces.uf2act3_agenda;

import dam.interfaces.uf2act3_agenda.controller.DialogUserEditController;
import dam.interfaces.uf2act3_agenda.controller.UserMenuController;
import dam.interfaces.uf2act3_agenda.model.Person;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private static final String APP_NAME = "Contacts";
    private static final String APP_XML = "app.fxml";
    private static final String USER_MENU_XML = "userMenu.fxml";
    private static final String MENU_XML = "dialog/userEdit.fxml";

    // ********** Attributes **********
    private ObservableList<Person> personData;

    // ********** UI **********
    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane userMenu;

    public MainApp() {
        initComponents();
    }

    private void initComponents() {
        personData = FXCollections.observableArrayList();
        // TODO DEBUG
        personData.add(new Person("Hans", "Muster", "Musterstrasse 1", 12345, "Musterstadt", 2000, 1, 1));
        personData.add(new Person("Ruth", "Mueller", "Musterstrasse 2", 12345, "Musterstadt", 2000, 1, 1));
        personData.add(new Person("Heinz", "Kurz", "Musterstrasse 3", 12345, "Musterstadt", 2000, 1, 1));
        personData.add(new Person("Cornelia", "Meier", "Musterstrasse 4", 12345, "Musterstadt", 2000, 1, 1));
        personData.add(new Person("Werner", "Meyer", "Musterstrasse 5", 12345, "Musterstadt", 2000, 1, 1));
        personData.add(new Person("Lydia", "Kunz", "Musterstrasse 6", 12345, "Musterstadt", 2000, 1, 1));
        personData.add(new Person("Anna", "Best", "Musterstrasse 7", 12345, "Musterstadt", 2000, 1, 1));
        personData.add(new Person("Stefan", "Meier", "Musterstrasse 8", 12345, "Musterstadt", 2000, 1, 1));
        personData.add(new Person("Martin", "Mueller", "Musterstrasse 9", 12345, "Musterstadt", 2000, 1, 1));
    }

    // ********** App Methods **********
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        stage.setTitle(APP_NAME);
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        loadLayouts();
        stage.setMinWidth(600);
        stage.setMinHeight(500);
        stage.setScene(new Scene(rootLayout));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    // ********** Menu logic **********
    private void loadLayouts() throws IOException {
        // App body
        FXMLLoader appLoader = new FXMLLoader(MainApp.class.getResource(APP_XML));
        rootLayout = appLoader.load();

        // User menu
        FXMLLoader userMenuLoader = new FXMLLoader(MainApp.class.getResource(USER_MENU_XML));
        userMenu = userMenuLoader.load();
//        ((UserMenuController) userMenuLoader.getController()).setMainApp(this);
        UserMenuController controller = userMenuLoader.getController();
        controller.setMainApp(this);

        // Default menu
        openUserMenu();
    }

    private void openUserMenu() {
        // TODO Overkill?
        rootLayout.setCenter(userMenu);
    }

    // ********** Getters **********
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    // ********** UX Methods **********
    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource(MENU_XML)
            );

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            DialogUserEditController userEdit = loader.getController();
            userEdit.setDialogStage(dialogStage);
            userEdit.setPerson(person);
            userEdit.showAndWait();
            return userEdit.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void warn(String title, String header, String msg) {
        showAlert(Alert.AlertType.WARNING, title, header, msg);
    }

    public void error(String title, String header, String msg) {
        showAlert(Alert.AlertType.ERROR, title, header, msg);
    }
}
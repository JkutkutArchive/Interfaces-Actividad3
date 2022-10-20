package dam.interfaces.uf2act3_agenda;

import dam.interfaces.uf2act3_agenda.controller.UserMenuController;
import dam.interfaces.uf2act3_agenda.model.Person;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private static final String APP_NAME = "Contacts";
    private static final String APP_XML = "app.fxml";
    private static final String USER_MENU_XML = "userMenu.fxml";

    // ********** Attributes **********
    private ObservableList<Person> personData;

    // ********** UI **********
    private BorderPane rootLayout;
    private AnchorPane userMenu;

    public MainApp() {
        initComponents();
    }

    private void initComponents() {
        personData = FXCollections.observableArrayList();
        // TODO DEBUG
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    // ********** App Methods **********
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle(APP_NAME);
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
}
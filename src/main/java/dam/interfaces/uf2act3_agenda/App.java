package dam.interfaces.uf2act3_agenda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static final String APP_NAME = "Agenda";
    private static final String APP_XML = "app.fxml";
    private static final String USER_MENU_XML = "userMenu.fxml";

    private BorderPane rootLayout;
    private AnchorPane userMenu;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle(APP_NAME);
        loadLayouts();
        Scene scene = new Scene(rootLayout);
        stage.setMinWidth(600);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    private void loadLayouts() throws IOException {
        rootLayout = new FXMLLoader(App.class.getResource(APP_XML)).load();
        userMenu = new FXMLLoader(App.class.getResource(USER_MENU_XML)).load();

        rootLayout.setCenter(userMenu);
    }

    public static void main(String[] args) {
        launch();
    }
}
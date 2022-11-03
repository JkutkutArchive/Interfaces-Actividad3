module dam.interfaces.uf2act3_agenda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;


    opens dam.interfaces.uf2act3_agenda to javafx.fxml;
    exports dam.interfaces.uf2act3_agenda;
    opens dam.interfaces.uf2act3_agenda.controller to javafx.fxml;
    exports dam.interfaces.uf2act3_agenda.controller;
}
module dam.interfaces.uf2act3_agenda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires java.xml;
    requires xstream;


    opens dam.interfaces.uf2act3_agenda to javafx.fxml;
    exports dam.interfaces.uf2act3_agenda;
    opens dam.interfaces.uf2act3_agenda.controller to javafx.fxml;
    exports dam.interfaces.uf2act3_agenda.controller;

//    Xstream
    opens dam.interfaces.uf2act3_agenda.javabeans to xstream;
    exports dam.interfaces.uf2act3_agenda.javabeans;
}
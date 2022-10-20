module dam.interfaces.uf2act3_agenda {
    requires javafx.controls;
    requires javafx.fxml;


    opens dam.interfaces.uf2act3_agenda to javafx.fxml;
    exports dam.interfaces.uf2act3_agenda;
}
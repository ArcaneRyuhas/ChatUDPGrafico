module com.example.chatudpgrafico {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chatudpgrafico to javafx.fxml;
    exports com.example.chatudpgrafico;
}
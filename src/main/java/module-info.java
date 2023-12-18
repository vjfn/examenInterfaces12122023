module com.example.exameninterfaces18122023 {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;


    opens com.example.exameninterfaces18122023 to javafx.fxml;
    exports com.example.exameninterfaces18122023;
}
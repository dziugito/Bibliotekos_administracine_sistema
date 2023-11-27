module com.example.laboras1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;
    requires spring.context;
    requires spring.web;
    requires java.naming;
    requires spring.core;
    requires com.google.gson;
    requires java.persistence;


    opens com.example.laboras1 to javafx.fxml;
    exports com.example.laboras1;
    opens com.example.laboras1.dataStructure to javafx.fxml;
    exports com.example.laboras1.dataStructure;
    exports com.example.laboras1.fxControllers;
    opens com.example.laboras1.fxControllers to javafx.fxml;
    exports com.example.laboras1.controls;
    opens com.example.laboras1.controls to javafx.fxml;
    exports com.example.laboras1.webController;
    opens com.example.laboras1.webController to javafx.fxml;
}
/**
 * modules for the application
 */
module tatsuya4lc.scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens tatsuya4lc.scheduler to javafx.fxml;
    exports tatsuya4lc.scheduler;
    exports tatsuya4lc.controller;
    opens tatsuya4lc.controller to javafx.fxml;
    exports tatsuya4lc.helper;
    opens tatsuya4lc.helper to javafx.fxml;
    exports tatsuya4lc.model;
    opens tatsuya4lc.model to javafx.fxml;
}
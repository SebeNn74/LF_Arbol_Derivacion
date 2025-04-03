module com.formales.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.formales.view to javafx.fxml;
    exports com.formales.view;
    exports com.formales.controller;
    opens com.formales.controller to javafx.fxml;
}
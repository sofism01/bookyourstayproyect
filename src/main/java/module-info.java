module org.uniquindio.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.controlsfx.controls;
    requires org.simplejavamail.core;
    requires org.simplejavamail;
    requires java.desktop;
    requires junit;


    opens org.uniquindio.application to javafx.fxml;
    exports org.uniquindio.application;

    exports org.uniquindio.application.enums;

    opens org.uniquindio.application.controllers to javafx.fxml;
    exports org.uniquindio.application.controllers;

    opens org.uniquindio.application.domain to javafx.fxml;
    exports org.uniquindio.application.domain;
}
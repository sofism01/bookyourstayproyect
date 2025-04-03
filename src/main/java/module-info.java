module org.uniquindio.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.controlsfx.controls;


    opens org.uniquindio.application to javafx.fxml;
    exports org.uniquindio.application;

    opens org.uniquindio.application.controllers to javafx.fxml;
    exports org.uniquindio.application.controllers;

    opens org.uniquindio.application.domain to javafx.fxml;
    exports org.uniquindio.application.domain;
}
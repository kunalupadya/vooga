module AuthoringModel {
    requires java.desktop;

    requires javafx.graphics;
    requires javafx.media;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.web;

    requires Configurations;
    requires DatabaseUtil;
    requires xstream;
    exports BackendExternalAPI;

}
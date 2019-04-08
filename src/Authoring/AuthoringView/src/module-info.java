module AuthoringView {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires java.xml;
    requires java.desktop;
    requires javafx.web;
    requires Configurations;

    exports GameAuthoringEnvironment.AuthoringComponents;
    exports GameAuthoringEnvironment.AuthoringScreen;
    exports Main;
}
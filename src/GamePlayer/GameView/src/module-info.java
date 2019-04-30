module GameView {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires java.xml;
    requires java.desktop;
    requires javafx.web;
    requires GameLogic;
    requires Configurations;
    requires DatabaseUtil;

    exports Player;

}
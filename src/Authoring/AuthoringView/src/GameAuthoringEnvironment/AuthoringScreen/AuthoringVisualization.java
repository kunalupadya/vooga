package GameAuthoringEnvironment.AuthoringScreen;

import BackendExternalAPI.Model;
import Configs.GamePackage.Game;
import GameAuthoringEnvironment.AuthoringComponents.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AuthoringVisualization extends Application {

    private String Title = "VoogaSalad";
    private double screenWidth;
    private double screenHeight;
    private double screenMinX;
    private double screenMinY;
    private Scene myScene;
    private VBox myContainer;
    private GameOutline gameOutline;
    private static final KeyCombination keyCombinationCommandN = new KeyCodeCombination(KeyCode.ESCAPE);
    private Game myGame;
    private Model myModel;

    @Override
    public void start(Stage stage){
        stage.setScene(myScene);
        stage.setTitle(Title);
        stage.show();
    }
    public AuthoringVisualization(Game game, Model model){
        myModel = model;
        myGame = game;
        myContainer = new VBox();
        setScene();
    }


    private void setScene() {
        screenHeight = ScreenSize.getHeight();
        screenWidth = ScreenSize.getWidth();


        //TODO(Hyunjae) Refactor and don't use magic numbers
        gameOutline = new GameOutline((int) ScreenSize.getHeight(), (int) screenWidth);
        StackPane background = new StackPane();
        background.setId("image_backdrop");
        VBox myGameOutline = gameOutline.getModule();
        background.getChildren().add(myGameOutline);
        TopMenuBar topMenuBar = new TopMenuBar(gameOutline, myModel);
        StackPane menu = new StackPane();
        StackPane colored = new StackPane();
        colored.setStyle("-fx-background-color: linear-gradient(rgb(90, 253, 255), rgb(98, 222, 230)); -fx-opacity: 0.4;");
        menu.getChildren().add(colored);
        menu.getChildren().add(topMenuBar.getTopMenuBar());
        myContainer.getChildren().addAll(menu, background);
        myScene = new Scene(myContainer, ScreenSize.getWidth(), ScreenSize.getHeight());
        myScene.getStylesheets().add("authoring_style.css");
    }

    //TODO(Hyunjae) When ctrl+N is pressed, show a new screen
    private void handleKeyInput(KeyEvent e) {
        if (keyCombinationCommandN.match(e)) {

        }
    }

}

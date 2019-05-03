package GameAuthoringEnvironment.AuthoringComponents;

import BackendExternalAPI.AuthoringBackend;
import Configs.GamePackage.Game;
import GameAuthoringEnvironment.AuthoringScreen.AlertFactory;
import GameAuthoringEnvironment.AuthoringScreen.GameController;
import GameAuthoringEnvironment.AuthoringScreen.GameOutline;
import GameAuthoringEnvironment.CloseStage;
import GameAuthoringEnvironment.ImportGame;
import Player.GamePlayMain;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TopMenuBar {

    private HBox TopMenuBar;
    private GameController gameController;
    private GameOutline myGameOutline;
    private AuthoringBackend myAuthoringBackend;
    private TopMenuBar myself;

    //TODO @Hyunjae : Set Style for these buttons

    public TopMenuBar(GameOutline gameOutline, AuthoringBackend authoringBackend){
        myAuthoringBackend = authoringBackend;
        myGameOutline = gameOutline;
        TopMenuBar = new HBox();
        TopMenuBar.setSpacing(5);
        TopMenuBar.setPadding(new Insets(10,5,5,5));
        TopMenuBar.setAlignment(Pos.CENTER);
        Button newGameButton = new Button("New Game");
        myself = this;
        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    gameController = new GameController();
                    gameController.createConfigurable(gameController.getMyGame());
                } catch (NumberFormatException | NoSuchFieldException n) {
                    handle(event);
                    AlertFactory af = new AlertFactory();
                    af.createAlert("Improper Field");
                }
            }
        });

        Button saveButton = new Button("Save");
        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(gameController == null || gameController.getMyGame() == null){
                    createAlert("Make a Game first");}
                else{
                myGameOutline.makeTreeView(gameController.getMyGame());}
            }
        });


        Button exportButton = new Button("Export");
        exportButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(gameController == null){
                    createAlert("Make a Game first");}
                else{
                myAuthoringBackend.saveToXML(gameController.getMyGame());}
            }
        });

        Button loadButton = new Button("Load");
        loadButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                ImportGame importGame = new ImportGame(myAuthoringBackend.getAuthoredGameLibrary(), myAuthoringBackend, myself);
                importGame.start(new Stage());
            }
        });

        Button runButton = new Button("Run");
        runButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GamePlayMain gamePlayMain = new GamePlayMain();
                gamePlayMain.setGameFromAuthoring(gameController.getMyGame());
                gamePlayMain.start(new Stage());
            }
        });

        Button settingsButton = new Button("Settings");
        settingsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

               //TODO This button should show a pop up screen that allows users to change css settings, font size etc.
            }
        });

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(gameController == null){
                    createAlert("Make a Game First");
                }else{
                myGameOutline.makeTreeView(gameController.getMyGame());}
            }
        });

        TopMenuBar.getChildren().addAll(newGameButton, saveButton, exportButton, loadButton, runButton, refreshButton);
    }

    public void loadGame(Game game){
//        gameController = new GameController();
//        gameController.setMyGame(game);
        myGameOutline.makeTreeView(game);
    }


    private void createAlert(String message) {
        AlertFactory alertFactory = new AlertFactory();
        alertFactory.createAlert(message);
    }

    public HBox getTopMenuBar(){
        return TopMenuBar;
    }
}

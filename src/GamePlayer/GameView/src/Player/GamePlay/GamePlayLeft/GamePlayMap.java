package Player.GamePlay.GamePlayLeft;

import BackendExternal.Logic;
import Configs.GamePackage.GameStatus;
import Configs.ImmutableImageView;
import Player.GamePlay.EndLoopInterface;
import Player.GamePlay.LeaderBoard;
import Player.GamePlay.SelectionInterface;
import Player.SetUp.GameSelection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

public class GamePlayMap extends Pane{
    private static final int SPACING = 10;
    private static final String SMALLERBUTTON_STYLE = "smallerButton";
    private static final String CSS_FILE = "style.css";
    private static final int QUIT_SCENE_SIZE = 300;
    private Logic myLogic;
    private List<ImmutableImageView> terrainList;
    private Group mapRoot;
    private EndLoopInterface endGame;
    private SelectionInterface homeStage;
    private GameStatus gameStatus;
    private GamePlaySettingsBar myData;
    private double width;
    private double height;

    GamePlayMap(double width, double height, Logic logic, EndLoopInterface endLoop,
                SelectionInterface stage, GamePlaySettingsBar data) {
        this.width = width;
        this.height = height;
        myData = data;
        homeStage = stage;
        endGame = endLoop;
        myLogic = logic;
        mapRoot=new Group();
        applyCss();
        layout();
        terrainList = myLogic.getLevelTerrain(width, height);
        setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        terrainList.stream().forEach(img -> getChildren().add(img.getAsNode()));
        setPrefWidth(width);
        setPrefHeight(height);
        mapRoot.prefWidth(width);
        mapRoot.prefHeight(height);
    }

    /**
     * This method is the main update method of the game loop. It constantly checks the game status and utilizes
     * various switch cases to execute the desired outcome of any game status, or just continues with the game.
     * @param elapsedTime The update frequency that we use.
     */
    public void update(double elapsedTime){
        gameStatus = myLogic.getGameStatus();
        System.out.println(gameStatus);
        switch (gameStatus){
            case OVER:
                displayGameOver("Game Over! ");
                LeaderBoard leaderBoard = new LeaderBoard(myLogic);
                leaderBoard.start(new Stage());
                break;
            case GAMELOST:
                displayGameOver("You Lost!");
                LeaderBoard leaderBoard1 = new LeaderBoard(myLogic);
                leaderBoard1.start(new Stage());
                break;
            case GAMEWON:
                displayGameOver("You Won!");
                LeaderBoard leaderBoard2 = new LeaderBoard(myLogic);
                leaderBoard2.start(new Stage());
                break;
            case PLAYING:
                myLogic.update(elapsedTime);
                List<ImmutableImageView> imageToAdd = myLogic.getObjectsToAdd();
                List<ImmutableImageView> imageToRemove = myLogic.getObjectsToRemove();
                imageToRemove.stream().forEach(img -> getChildren().remove(img.getAsNode()));
                imageToAdd.stream().forEach(img -> getChildren().add(img.getAsNode()));
                break;
            case LEVELOVER:
                System.out.println("Level ended");
                myData.updateLevel(myLogic.startNextLevel());
                terrainList.clear();
                terrainList = myLogic.getLevelTerrain(width, height);
                terrainList.stream().forEach(img -> getChildren().add(img.getAsNode()));
        }
    }

    /**
     * @return Returns the size of the grid so the draggable objects can be appropriately sized when they are dragged.
     */
    public double getGridSize(){
        return terrainList.get(0).getAsNode().getBoundsInParent().getWidth();
    }

    private void displayGameOver(String result){
        endGame.endLoop();
        Stage gameOver = new Stage();
        VBox root = new VBox();
        root.setId("quit");
        Scene overScene = new Scene(root,QUIT_SCENE_SIZE,QUIT_SCENE_SIZE);
        overScene.getStylesheets().add(CSS_FILE);
        Text text = new Text(result);
        Button quit = new Button("Quit Game");
        quit.setId(SMALLERBUTTON_STYLE);
        quit.setOnAction(e -> closeStages(gameOver,homeStage));
        Button goHome = new Button("Return to Home");
        goHome.setId(SMALLERBUTTON_STYLE);
        goHome.setOnAction(e -> {
            closeStages(gameOver,homeStage);
            GameSelection gameSelection = new GameSelection(myLogic);
            gameSelection.start(new Stage());
        });
        HBox display = new HBox(quit,goHome);
        display.setPrefWidth(QUIT_SCENE_SIZE/2);
        display.setSpacing(SPACING);
        display.setAlignment(Pos.CENTER);
        root.getChildren().addAll(text, display);
        root.setSpacing(SPACING);
        root.setAlignment(Pos.CENTER);
        gameOver.setScene(overScene);
        gameOver.show();
    }

    private void closeStages(Stage modal, SelectionInterface game){
        modal.close();
        game.closeStage();
    }
}

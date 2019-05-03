package Player.GamePlay.GamePlayLeft;

import BackendExternal.Logic;
import Configs.GamePackage.GameStatus;
import Configs.ImmutableImageView;
import Player.GamePlay.EndLoopInterface;
import Player.GamePlay.SelectionInterface;
import Player.SetUp.GameSelection;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

public class GamePlayMap extends Pane{
    private Logic myLogic;
    private List<ImmutableImageView> terrainList;
    private Group mapRoot;
    private EndLoopInterface endGame;
    private SelectionInterface homeStage;
    private GameStatus gameStatus;
    private GamePlaySettingsBar myData;


    GamePlayMap(double width, double height, Logic logic, EndLoopInterface endLoop,
                SelectionInterface stage, GamePlaySettingsBar data) {
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
                break;
            case GAMELOST:
                displayGameOver("You Lost!");
                break;
            case GAMEWON:
                displayGameOver("You Won!");
                break;
            case PLAYING:
                if (gameStatus == GameStatus.LEVELOVER) {
                    myData.updateLevel(myLogic.startNextLevel());
                }
                myLogic.update(elapsedTime);
                List<ImmutableImageView> imageToAdd = myLogic.getObjectsToAdd();
                List<ImmutableImageView> imageToRemove = myLogic.getObjectsToRemove();
                imageToRemove.stream().forEach(img -> getChildren().remove(img.getAsNode()));
                imageToAdd.stream().forEach(img -> getChildren().add(img.getAsNode()));
                break;
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
        Group root = new Group();
        Scene overScene = new Scene(root,200,200);
        Text text = new Text(result);
        Button quit = new Button("Quit Game");
        quit.setOnAction(e -> closeStages(gameOver,homeStage));
        Button goHome = new Button("Return to Home");
        goHome.setOnAction(e -> {
            closeStages(gameOver,homeStage);
            GameSelection gameSelection = new GameSelection(myLogic);
            gameSelection.start(new Stage());
        });
        VBox display = new VBox(text,quit,goHome);
        root.getChildren().addAll(display);
        gameOver.setScene(overScene);
        gameOver.show();
    }

    private void closeStages(Stage modal, SelectionInterface game){
        modal.close();
        game.closeStage();
    }
}

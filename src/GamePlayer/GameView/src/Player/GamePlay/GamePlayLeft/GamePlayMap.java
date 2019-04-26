package Player.GamePlay.GamePlayLeft;

import BackendExternal.Logic;
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

    public GamePlayMap(double width, double height, Logic logic, EndLoopInterface endLoop,
                       SelectionInterface stage) {
        homeStage = stage;
        endGame = endLoop;
        myLogic = logic;
        mapRoot=new Group();
        applyCss();
        layout();
        System.out.println(width/height);

        terrainList = myLogic.getLevelTerrain(width, height);
        setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        terrainList.stream().forEach(img -> getChildren().add(img.getAsNode()));
        setPrefWidth(width);
        setPrefHeight(height);
        mapRoot.prefWidth(width);
        mapRoot.prefHeight(height);
    }

    public void update(double elapsedTime){
//        if (myLogic.checkIfGameEnd()){
            if (true){
                displayGameOver();
            }
            else {
//                if (myLogic.checkIfLevelEnd()) {
//                    myLogic.startNextLevel();
//                }
                //commenting out logic to hardcode animation
                myLogic.update(elapsedTime);
                List<ImmutableImageView> imageToAdd = myLogic.getObjectsToAdd();
                List<ImmutableImageView> imageToRemove = myLogic.getObjectsToRemove();
                imageToRemove.stream().forEach(img -> getChildren().remove(img.getAsNode()));
                imageToAdd.stream().forEach(img -> getChildren().add(img.getAsNode()));
            }
    }

    public double getGridSize(){
        return terrainList.get(0).getAsNode().getBoundsInParent().getWidth();
    }

    private void displayGameOver(){
        endGame.endLoop();
        Stage gameOver = new Stage();
        Group root = new Group();
        Scene overScene = new Scene(root,200,200);
        Text text = new Text("Game Over!");
        Button quit = new Button("Quit Game");
        quit.setOnAction(e -> closeStages(gameOver,homeStage));
        Button goHome = new Button("Return to Home");
        goHome.setOnAction(e -> {
            closeStages(gameOver,homeStage);
            GameSelection gameSelection = new GameSelection();
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

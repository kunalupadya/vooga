package GUI.GamePlay.GameScreen;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GamePlayVisualization extends Application {
    private String Title = "VoogaSalad Game";
    private static final int FRAMES_PER_SECOND = 1;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private double screenWidth = 800;
    private double screenHeight = 500;
    private double screenMinX;
    private double screenMinY;

    @Override
    public void start(Stage stage){
        try {
            Stage primaryStage = stage;
            var root = new Group();
            var startScreen = new Scene(root, screenWidth, screenHeight);
            //TODO:should this be global below?
            GamePlayIDE myGameIDE = new GamePlayIDE();
            root.getChildren().add(myGameIDE);
            primaryStage.setScene(startScreen);
            primaryStage.setTitle(Title);
            primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        launch(args);
    }

}

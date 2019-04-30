package Player;

import BackendExternal.Logic;
import Configs.GamePackage.Game;
import ExternalAPIs.GameInfo;
import Player.GamePlay.GamePlayGUI;
import Player.ScreenSize;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

import static Player.GamePlay.GamePlayGUI.LEFT_RATIO;
import static Player.GamePlay.GamePlayGUI.RIGHT_RATIO;
import static Player.GamePlay.GamePlayLeft.GamePlayLeftSide.TOP_RATIO;


public class GamePlayMain extends Application {
    private String Title = "VoogaSalad Game";
    private String GAME_MUSIC = "resources/gameMusic.mp3";
    public static int FRAMES_PER_SECOND = 3;
    public static int MILLISECOND_DELAY = 1000/FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0/FRAMES_PER_SECOND;
    private static final Paint backgroundColor = Color.NAVY;
    private double screenWidth = ScreenSize.getWidth();
    private double screenHeight = ScreenSize.getHeight();
    // Added by Brian
    private Logic myLogic = new Logic(screenWidth * LEFT_RATIO, screenHeight * TOP_RATIO);
    private Timeline animation;
    private GamePlayGUI myGameGUI;
    private Group root;
    private double currMilliSecond = 0;
    private MediaPlayer mediaPlayer;
    private KeyFrame frame;
    private boolean gameOver;
    private Stage primaryStage;
    @Override
    public void start(Stage stage){
        try {
            primaryStage = stage;
            root = new Group();
            primaryStage.setX(screenWidth);
            primaryStage.setY(screenHeight);
            var startScreen = new Scene(root, screenWidth, screenHeight,backgroundColor);
            startScreen.getStylesheets().add("gameplay.css");
            MediaView music = createWelcomeMusic();
            root.getChildren().add(music);
            myGameGUI = new GamePlayGUI(myLogic, () -> startLoop(), () -> fastFoward(), () -> endLoop(),
                    () -> closeStage(),
                    root,
                    mediaPlayer);
            root.getChildren().add(myGameGUI);
            frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), event -> step());
            animation = new Timeline();
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.getKeyFrames().add(frame);
            primaryStage.setScene(startScreen);
            primaryStage.setTitle(Title);
            primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setGameInfo(GameInfo gameInfo){
        myLogic.createGameInstance(gameInfo);
        myLogic.startAtDefaultState();
    }

    public void setGameFromAuthoring(Game game){
        myLogic.setGameFromAuthoring(game);
        myLogic.startAtDefaultState();
    }

    private void fastFoward(){
        animation.setRate(2.5);
    }

    private MediaView createWelcomeMusic(){
        Media sound = new Media(new File(GAME_MUSIC).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        return mediaView;
    }

    private void startLoop(){
        animation.setRate(1);
        animation.play();
    }

    private void step(){
        if (!gameOver) {
            myGameGUI.update(currMilliSecond);
            currMilliSecond += MILLISECOND_DELAY;

        }
    }

    public void endLoop(){
        gameOver = true;
    }

    private void closeStage(){
        primaryStage.close();
    }

}

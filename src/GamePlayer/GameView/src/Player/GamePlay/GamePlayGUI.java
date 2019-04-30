package Player.GamePlay;

import BackendExternal.Logic;
import Player.GamePlay.GamePlayLeft.GamePlayLeftSide;
import Player.GamePlay.GamePlayRight.GamePlayRightSide;
import Player.ScreenSize;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.awt.*;

public class GamePlayGUI extends HBox {
    public static final double LEFT_RATIO = 0.75;
    public static final double RIGHT_RATIO = 0.25;
    private static final String HBOX_STYLE = "HUD";
    private GamePlayLeftSide myGameLeft;
    private GamePlayRightSide myGameRight;

    public GamePlayGUI(Logic logic, ButtonInterface play, ButtonInterface fastFoward, EndLoopInterface endLoop,
                SelectionInterface home,
                Group root,
                MediaPlayer mediaPlayer){
        setId(HBOX_STYLE);
        setPrefWidth(ScreenSize.getWidth());
        setPrefHeight(ScreenSize.getHeight());
        myGameLeft = new GamePlayLeftSide(ScreenSize.getWidth() * LEFT_RATIO, ScreenSize.getHeight(), logic, endLoop,
                home);
        myGameRight = new GamePlayRightSide(ScreenSize.getWidth() * RIGHT_RATIO, ScreenSize.getHeight(), logic, play,
                fastFoward,
                myGameLeft.getMap(), root, home, mediaPlayer);
        this.getChildren().addAll(myGameLeft,myGameRight);
    }

    public void update(double mili){
        myGameLeft.myMap.update(mili);
        myGameLeft.mySettings.updateVariables();
        myGameRight.update();
    }

}

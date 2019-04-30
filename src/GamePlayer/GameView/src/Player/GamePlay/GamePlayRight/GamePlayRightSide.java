package Player.GamePlay.GamePlayRight;

import BackendExternal.Logic;
import Player.GamePlay.ButtonInterface;
import Player.GamePlay.GamePlayLeft.GamePlayMap;
import Player.GamePlay.SelectionInterface;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

public class GamePlayRightSide extends VBox {

    private static final double ARSENAL_RATIO = 0.8;
    private static final double BUTTON_RATIO = 0.2;
    private GamePlayArsenal myGameArsenal;
    private ButtonPanel myButtonPanel;
    private MediaPlayer mediaPlayer;


    public GamePlayRightSide(double width, double height, Logic logic, ButtonInterface play, ButtonInterface fastForward
            , GamePlayMap myMap, Group root, SelectionInterface home, MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;
        setPrefWidth(width);
        setPrefHeight(height);
        myGameArsenal = new GamePlayArsenal(width, height * ARSENAL_RATIO, logic, myMap, root);
        this.getChildren().addAll(myGameArsenal, createButtonPanel(width, height, play, fastForward, home, logic));
    }


    /**
     * This method updates the arsenal upon each game loop to determine which weapons are still valid to be used.
     */
    public void update(){
        myGameArsenal.recreateArsenal();
    }

    private VBox createButtonPanel(double width, double height, ButtonInterface play, ButtonInterface fastFoward,
                                   SelectionInterface home, Logic logic){
        myButtonPanel = new ButtonPanel(width, height * BUTTON_RATIO, play, fastFoward, home, mediaPlayer, logic);
        return myButtonPanel;
    }
}

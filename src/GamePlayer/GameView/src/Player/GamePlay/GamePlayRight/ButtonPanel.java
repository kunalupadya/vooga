package Player.GamePlay.GamePlayRight;

import BackendExternal.Logic;
import Player.GamePlay.ButtonInterface;
import Player.GamePlay.Buttons.FastFowardButton;
import Player.GamePlay.Buttons.PlayButton;
import Player.GamePlay.SelectionInterface;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

public class ButtonPanel extends VBox {

    private PlayButton myPlayButton;
    private FastFowardButton myFastFowardButton;
    private SettingsPanel mySettingsPanel;

    public ButtonPanel(double width, double height, ButtonInterface method, ButtonInterface fastFoward,
                       SelectionInterface home, MediaPlayer mediaPlayer, Logic logic){

        setStyle("-fx-border-color: blue; -fx-border-width: 10");
        setPadding(new Insets(0, 0, 30, 0));
        setPrefHeight(height);
        setId("HUD");
        setSpacing(1);
        setAlignment(Pos.CENTER);
        myPlayButton = new PlayButton(width, height* 0.4);
        myFastFowardButton = new FastFowardButton(width, height * 0.4);
        myPlayButton.setOnAction(e-> {
                    try {
                        changeToFastFoward();
                        method.actionButton();
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                });
        myFastFowardButton.setOnAction(e->{
            changeToPlay();
            fastFoward.actionButton();
        });
        mySettingsPanel = new SettingsPanel(width, height/2, home, mediaPlayer, logic);
        getChildren().add(mySettingsPanel);
        getChildren().add(myPlayButton);
    }

    private void changeToPlay(){
        getChildren().remove(myFastFowardButton);
        getChildren().add(myPlayButton);
    }
    private void changeToFastFoward(){
        getChildren().remove(myPlayButton);
        getChildren().add(myFastFowardButton);
    }
}

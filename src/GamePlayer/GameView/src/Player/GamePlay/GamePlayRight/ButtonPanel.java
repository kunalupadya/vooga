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

class ButtonPanel extends VBox {

    private static final int PADDING_SMALL = 2;
    private static final int PADDING_WIDE = 30;
    private static final int SPACING = 0;
    private static final double BUTTON_SIZE_RATIO = 0.4;
    private static final double SETTINGSPANEL_RATIO = 0.5;
    private PlayButton myPlayButton;
    private FastFowardButton myFastFowardButton;
    private SettingsPanel mySettingsPanel;

    ButtonPanel(double width, double height, ButtonInterface method, ButtonInterface fastFoward,
                SelectionInterface home, MediaPlayer mediaPlayer, Logic logic){
        setPrefHeight(height);
        setPadding(new Insets(PADDING_SMALL, PADDING_SMALL, PADDING_WIDE , PADDING_SMALL));
        setSpacing(SPACING);
        setMaxHeight(height);
        setAlignment(Pos.CENTER);
        myPlayButton = new PlayButton(width, height* BUTTON_SIZE_RATIO);
        myFastFowardButton = new FastFowardButton(width, height * BUTTON_SIZE_RATIO);
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
        mySettingsPanel = new SettingsPanel(width, height * SETTINGSPANEL_RATIO, home, mediaPlayer, logic);
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

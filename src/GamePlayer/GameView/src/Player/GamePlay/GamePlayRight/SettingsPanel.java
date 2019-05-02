package Player.GamePlay.GamePlayRight;

import BackendExternal.Logic;
import Player.GamePlay.Buttons.HomeButton;
import Player.GamePlay.Buttons.SaveButton;
import Player.GamePlay.SelectionInterface;
import Player.SetUp.GameSelection;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

class SettingsPanel extends HBox {
    private static final int PADDING = 10;
    SettingsPanel(double width, double height, SelectionInterface home, MediaPlayer mediaPlayer, Logic logic){
        setSpacing(PADDING);
        setPrefHeight(height);
        setPrefWidth(width);
        HomeButton homeButton = new HomeButton(width / 3, height);
        homeButton.setOnAction(e-> new QuitConfirmation(home, mediaPlayer, logic));
        SettingsButton settingsButton = new SettingsButton(width / 3, height, mediaPlayer);
        SaveButton saveButton = new SaveButton(width / 3, height);
        saveButton.setOnAction(e -> logic.saveGameState());
        getChildren().addAll(homeButton, settingsButton, saveButton);
    }
}

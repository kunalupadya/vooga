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

public class SettingsPanel extends HBox {

    public SettingsPanel(double width, double height, SelectionInterface home, MediaPlayer mediaPlayer, Logic logic){
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(10);
        setPrefHeight(height);
        setPrefWidth(width);
        HomeButton homeButton = new HomeButton(width / 3, height);
        homeButton.setOnAction(e-> new QuitConfirmation(home, mediaPlayer));
        SettingsButton settingsButton = new SettingsButton(width / 3, height, mediaPlayer);
        SaveButton saveButton = new SaveButton(width / 3, height);
//        saveButton.setOnAction(e -> logic.saveGameState());
        getChildren().addAll(homeButton, settingsButton, saveButton);
    }
}

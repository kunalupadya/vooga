package Player.GamePlay.GamePlayRight;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Window;

class SettingsButton extends Button {

    private static final int MUSIC_OFF = 0;

    private static final String icon = "settings.png";
    private static final String SETTINGS_TITLE = "Game Settings";
    private static final String SETTINGS_SUBTITLE = "Adjust Game Settings Below";
    private static final String SOUND_LEVEL_PROMPT = "Sound Level";
    private static final String OFF_PROMPT = "Sound Off";
    private static final String ON_PROMPT = "Sound On";
    private static final int PADDING = 10;
    private Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(icon));
    private ImageView imageView = new ImageView(image);
    private MediaPlayer mediaPlayer;
    private boolean soundOn;
    private double savedVolume;

    SettingsButton(double width, double height, MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;
        savedVolume = mediaPlayer.getVolume();
        soundOn = true;
        imageView.setFitWidth(width/3);
        imageView.setFitHeight(height/3);
        setGraphic(imageView);
        setPrefWidth(width/3);
        setPrefHeight(height/2);
        setPrefWidth(width);
        setOnAction(e -> displaySettings());
    }

    private void displaySettings(){
        Dialog dialog = new Dialog();
        dialog.setTitle(SETTINGS_TITLE);
        dialog.setHeaderText(SETTINGS_SUBTITLE);
        DialogPane dialogPane = dialog.getDialogPane();
        Button musicInput = new Button(OFF_PROMPT);
        musicInput.setOnAction(e -> switchSound(musicInput));
        Slider sound = new Slider(0,1,0.5);
        setupTicker(sound);
        sound.valueProperty().addListener((observable, oldValue, newValue) -> checkVolumeChange(newValue));
        VBox settings = new VBox(PADDING,musicInput, new Label(SOUND_LEVEL_PROMPT),  sound);
        settings.setAlignment(Pos.CENTER);
        dialogPane.setContent(settings);
        dialog.show();
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
    }

    private void switchSound(Button music){
        if (soundOn){
            music.setText(ON_PROMPT);
            mediaPlayer.setVolume(MUSIC_OFF);
            soundOn = false;
        }
        else {
            music.setText(OFF_PROMPT);
            mediaPlayer.setVolume(savedVolume);
            soundOn = true;
        }
    }

    private void setupTicker(Slider sound){
        sound.setShowTickLabels(true);
        sound.setShowTickMarks(true);
        sound.setBlockIncrement(PADDING);
        sound.setMajorTickUnit(0.25f);
        sound.setBlockIncrement(0.1f);
    }

    private void checkVolumeChange(Number newValue){
        double vol = newValue.doubleValue();
        mediaPlayer.setVolume(vol);
    }
}

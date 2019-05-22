package Player.GamePlay.Buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FastFowardButton extends Button {
    private String icon = "fastFoward.png";

    private Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(icon));
    private ImageView imageView = new ImageView(image);

    public FastFowardButton(double width, double height){
        imageView.setFitWidth(height);
        imageView.setFitHeight(height);
        setGraphic(imageView);
        setPrefWidth(width/3);
        setPrefHeight(height);
        setId("fastForwardButton");
        setPrefHeight(height);
    }
}

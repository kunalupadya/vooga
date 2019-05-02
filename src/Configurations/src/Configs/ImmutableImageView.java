package Configs;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * An interface that allows for only reading of an Imageview object to improve data security
 */
public interface ImmutableImageView {
    //pixel location
    double getX();
    //pixel location
    double getY();
    double getFitWidth();
    double getFitHeight();
//    ImageView getImageView();
    Node getAsNode();

}

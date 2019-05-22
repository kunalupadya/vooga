package GameAuthoringEnvironment.AuthoringScreen;

import javafx.scene.Node;
import javafx.scene.image.Image;

public class ImageBox extends Node {

    private Image myImage;
    private int myNumber;
    public ImageBox(Image image, int imageNumber){
        myImage = image;
        myNumber = imageNumber;
    }

    public Image getMyImage() {
        return myImage;
    }

    public int getMyNumber() {
        return myNumber;
    }
}

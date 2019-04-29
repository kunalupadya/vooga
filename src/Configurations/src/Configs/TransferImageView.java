package Configs;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * An imageview wrapper to be passed from the backend to the frontend with read-only access after instantiation
 */
public class TransferImageView extends ImageView implements ImmutableImageView{
    public TransferImageView(){
        super();
    }
    public TransferImageView(Image image){
        super(image);
    }

    @Override
    public Node getAsNode() {
        return this;
    }
}

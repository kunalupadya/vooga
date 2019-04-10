package Configs;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.scene.image.Image;

public class MapFeature {

    private int gridXPos;
    private int gridYPos;
//    private double pixelXPos;
//    private double pixelYPos;
    private double displayDirection;
    private double trigDirection;
    @XStreamOmitField
    private TransferImageView myImageView;
    private View view;
    private DisplayState displayState;


    public MapFeature(int gridXPos, int gridYPos, double displayDirection, View view) {
        setGridPos(gridXPos,gridYPos,displayDirection);
        this.view = view;
        myImageView = new TransferImageView(new Image(view.getImage()));
        displayState = DisplayState.NEW;
    }

    public MapFeature(double pixelXPos, double pixelYPos, double direction, View view) {
        setPixelPos(pixelXPos,pixelYPos,direction);
        this.view = view;
        myImageView = new TransferImageView(new Image(view.getImage()));
        displayState = DisplayState.NEW;
    }

    public double getPixelXPos() {
        return myImageView.getTranslateX();
    }

    public double getPixelYPos() {
        return myImageView.getTranslateY();
    }

    public int getGridXPos() {
        return gridXPos;
    }

    public int getGridYPos() {
        return gridYPos;
    }



    public void moveRelatively(double deltaPixelX, double deltaPixelY) {
//        pixelXPos+=deltaPixelX;
//        pixelYPos+=deltaPixelY;
        myImageView.setTranslateX(getPixelXPos()+deltaPixelX);
        myImageView.setTranslateY(getPixelYPos()+deltaPixelY);


        //TODO; calculate grid position
    }

    public void setPixelPos(double x, double y, double direction) {
        myImageView.setTranslateX(x);
        myImageView.setTranslateY(y);
        myImageView.setRotate(direction);
        this.displayDirection = direction;
        //TODO: CALCULATE GRID POSITION FROM THIS

        //set imageview x and y in this
    }

    public void setGridPos(int gridXPos, int gridYPos, double direction) {
        this.gridXPos = gridXPos;
        this.gridYPos = gridYPos;
        this.displayDirection = direction;
        //TODO: CALCULATE PIXEL POSITION FROM THIS
        //set imageview x and y in this

    }
    public TransferImageView getImageView() {
        //TODO: throw exception if not defined
        myImageView.setFitHeight(view.getHeight());
        myImageView.setFitWidth(view.getWidth());

        return myImageView;


    }

    public double getDirection() {
        return displayDirection;
    }

    public double getTrigDirection() {
        trigDirection = (360-displayDirection+90)%360;
        return trigDirection;
    }

    public void setTrigDirection(double trigDirection) {
        this.trigDirection = trigDirection;
        displayDirection = (-trigDirection+450)%360;

    }

    public DisplayState getDisplayState() {
        return displayState;
    }

    public void setDisplayState(DisplayState displayState) {
        this.displayState = displayState;
    }
}

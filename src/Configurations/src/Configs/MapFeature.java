package Configs;

import ActiveConfigs.ActiveEnemy;
import ActiveConfigs.ActiveProjectile;
import ActiveConfigs.ActiveWeapon;
import ActiveConfigs.Cell;
//import ExternalAPIs.AuthoringData;
//import ExternalAPIs.Data;
import Configs.GamePackage.Game;
import Configs.GamePackage.Game;
import ExternalAPIs.AuthoringData;
import ExternalAPIs.Data;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.*;

import java.io.InputStream;

/**
 * A class held by objects displayed on the Game Map that serves multiple utilities that are key in implementation
 */
public class MapFeature {

    private int gridXPos;
    private int gridYPos;
    private int gridXSize;
    private int gridYSize;
    private double safeBoxMinGridX, safeBoxMaxGridX, safeBoxMinGridY, safeBoxMaxGridY;
    private double hypotenuse;
    private double pixelXPos;
    private double pixelYPos;
    private double paneWidth;
    private double paneHeight;
    private double displayDirection;
    private double trigDirection;
    @XStreamOmitField
    private transient TransferImageView myImageView;
    private DisplayState displayState;
    private double heightInGridUnits;
    private double widthInGridUnits;
    private MapFeaturable parent;


    private Set<Cell> myCells = new HashSet<>();

    @Deprecated
    public MapFeature(int gridXPos, int gridYPos, double displayDirection, View view) {
        setImage(view);
        this.heightInGridUnits = view.getHeight();
        this.widthInGridUnits = view.getWidth();
        hypotenuse = Math.sqrt(Math.pow(widthInGridUnits/2, 2) + Math.pow(heightInGridUnits/2, 2));
        setGridPos(gridXPos,gridYPos,displayDirection);
        displayState = DisplayState.NEW;

    }

    @Deprecated
    public MapFeature(int gridXPos, int gridYPos, double displayDirection, View view, double paneWidth, double paneHeight,int gridXSize, int gridYSize) {
        this.heightInGridUnits = view.getHeight();
        this.widthInGridUnits = view.getWidth();
        this.paneWidth = paneWidth;
        this.paneHeight = paneHeight;
        this.gridXSize = gridXSize;
        this.gridYSize = gridYSize;
        displayState = DisplayState.NEW;
        hypotenuse = Math.sqrt(Math.pow(widthInGridUnits/2, 2) + Math.pow(heightInGridUnits/2, 2));
        setImage(view);
        setGridPos(gridXPos, gridYPos, displayDirection);
    }

    /**
     * Active construcor the
     * @param gridXPos tells where to display parent object on the grid
     * @param gridYPos tells where to display parent object on the grid
     * @param displayDirection Gives opinting direction
     * @param view the view object that holsd image associated with this object
     * @param paneWidth the size of this level's field (to stay in bounds)
     * @param paneHeight
     * @param gridXSize the number of gridspaces that stretch horizontally across
     * @param gridYSize the number of gridspaces that stretch vertically across
     * @param parent The object that holds this mapFeature
     */
    public MapFeature(int gridXPos, int gridYPos, double displayDirection, View view, double paneWidth, double paneHeight,int gridXSize, int gridYSize, MapFeaturable parent) {
        this.heightInGridUnits = view.getHeight();
        this.widthInGridUnits = view.getWidth();
        this.paneWidth = paneWidth;
        this.paneHeight = paneHeight;
        this.gridXSize = gridXSize;
        this.gridYSize = gridYSize;
        displayState = DisplayState.NEW;
        this.gridYPos = gridYPos;
        this.gridXPos = gridXPos;
        this.parent = parent;
        hypotenuse = Math.sqrt(Math.pow(widthInGridUnits/2, 2) + Math.pow(heightInGridUnits/2, 2));
        setImage(view);
        setGridPos(gridXPos, gridYPos, displayDirection);
    }


    @Deprecated
    public MapFeature( double pixelXPos, double pixelYPos, double direction, View view){
        this.heightInGridUnits = view.getHeight();
        this.widthInGridUnits = view.getWidth();
        setImage(view);
        setPixelPos(pixelXPos, pixelYPos, direction);
        displayState = DisplayState.NEW;
    }

    @Deprecated
    public MapFeature( double pixelXPos, double pixelYPos, double direction, View view,double paneWidth,
                       double paneHeight, int gridXSize, int gridYSize){
        this.heightInGridUnits = view.getHeight();
        this.widthInGridUnits = view.getWidth();
        displayState = DisplayState.NEW;
        this.paneWidth = paneWidth;
        this.paneHeight = paneHeight;
        this.gridXSize = gridXSize;
        this.gridYSize = gridYSize;
        setImage(view);
        setPixelPos(pixelXPos, pixelYPos, direction);
    }



    /**
     * Active construcor the
     * @param pixelXPos tells where to display parent object on the view
     * @param pixelYPos tells where to display parent object on the view
     * @param direction Gives opinting direction in JavaFX terms
     * @param view the view object that holsd image associated with this object
     * @param paneWidth the size of this level's field (to stay in bounds)
     * @param paneHeight
     * @param gridXSize the number of gridspaces that stretch horizontally across
     * @param gridYSize the number of gridspaces that stretch vertically across
     * @param parent The object that holds this mapFeature
     */
    public MapFeature(double pixelXPos, double pixelYPos, double direction, View view, double paneWidth, double paneHeight,int gridXSize, int gridYSize, MapFeaturable parent) {
        this.heightInGridUnits = view.getHeight();
        this.widthInGridUnits = view.getWidth();
        displayState = DisplayState.NEW;
        this.paneWidth = paneWidth;
        this.paneHeight = paneHeight;
        this.gridXSize = gridXSize;
        this.gridYSize = gridYSize;
        this.gridXPos = (int) (pixelXPos*(gridXSize/paneWidth));
        this.gridYPos = (int) (pixelYPos*(gridYSize/paneHeight));
        this.parent = parent;
        hypotenuse = Math.sqrt(Math.pow(widthInGridUnits/2, 2) + Math.pow(heightInGridUnits/2, 2));
        setImage(view);
        setPixelPos(pixelXPos,pixelYPos,direction);
    }

    private void setImage(View view) throws IllegalStateException {
        Game game = parent.getActiveLevel().getGame();
        int imageId = view.getImage();
        Image image;
        if (game.hasImage(imageId)) {
            image = game.getImage(imageId);
        }
        else {
            image = Data.getImageStatic(imageId);
            game.addImage(imageId, image);
        }
        myImageView = new TransferImageView(image);
        myImageView.setFitHeight(paneHeight/gridYSize*heightInGridUnits);
        myImageView.setFitWidth(paneWidth/gridXSize* widthInGridUnits);
    }
    /**
     * cleans and updates displaystate based on what the state is
     * @param displayState
     */
    public void setDisplayState(DisplayState displayState) {
        if(displayState == DisplayState.DIED) {
            removeFromCell();
        }
        this.displayState = displayState;
    }

    public void setDisplayDirection(double displayDirection) {
        this.displayDirection = displayDirection;
    }

    /**
     *
     * @return this parameter
     */
    public double getPixelHeight(){
        return myImageView.getFitHeight();
    }

    /**
     *
     * @return this parameter
     */
    public double getPixelYPos() {
        return myImageView.getY();
    }
    /**
     *
     * @return the state of the feature to see if it should stay on the map or be rmoved
     */
    public DisplayState getDisplayState() {
        return displayState;
    }

    /**
     *
     * @return an unmodifiable set of cells that the mapfeature lives on for data security
     */
    public Set<Cell> getMyCells() {
        return Collections.unmodifiableSet(myCells);
    }

    /**
     *
     * @return this parameter
     */
    public int getGridXPos() {
        return gridXPos;
    }

    /**
     *
     * @return this parameter
     */
    public int getGridYPos() {
        return gridYPos;
    }

    /**
     *
     * @return this parameter
     */
    public double getPixelWidth(){
        return myImageView.getFitWidth();
    }

    /**
     * Allows the movement of an image based on how many pixels away from its current location it should move
     * @param deltaPixelX
     * @param deltaPixelY
     */
    public void moveRelatively(double deltaPixelX, double deltaPixelY) {
        pixelXPos+=deltaPixelX;
        pixelYPos+=deltaPixelY;
        setPixelPos(pixelXPos, pixelYPos, displayDirection);
    }

    /**
     *
     * @return this parameter
     */
    public double getPixelXPos() {
        return myImageView.getX();
    }



//    public double[] returnBounds(){
//        return new double[]{safeBoxMinGridY, safeBoxMaxGridY, safeBoxMinGridX, safeBoxMaxGridX};
//    }

    private boolean isOutOfBounds(int x, int y) {
        return x<0||x>=gridXSize||y<0||y>=gridYSize;
    }
    private boolean isOutOfBoundsPixel(double xPixel, double yPixel) {
        return (xPixel>paneWidth||xPixel<0||yPixel>paneHeight||yPixel<0);
    }

    private void setImageView(double pixelXPos, double pixelYPos, double direction) {
        myImageView.setX(pixelXPos);
        myImageView.setY(pixelYPos);
        myImageView.setRotate(direction);
    }


    /**
     * Allows for the movement of an object based on a new grid position and pointing location
     * @param gridXPos
     * @param gridYPos
     * @param direction
     */
    public void setGridPos(int gridXPos, int gridYPos, double direction) {
        updateSafeBoxBounds();
        removeFromCell();
        if(isOutOfBounds(gridXPos,gridYPos)) {
            displayState = DisplayState.DIED;
        }
        else {
            this.gridXPos = gridXPos;
            this.gridYPos = gridYPos;
            this.displayDirection = direction;
            pixelXPos = (paneWidth/gridXSize)*gridXPos;
            pixelYPos = (paneHeight/gridYSize)*gridYPos;
            setImageView(pixelXPos,pixelYPos,direction);
            setInCell(gridYPos, gridXPos);
        }
    }

    /**
     * returns the imageview - utilizied between config/backend and gameplayer frontend
     * @return
     */
    public TransferImageView getImageView() {
        setImageView(pixelXPos, pixelYPos, displayDirection);
        return myImageView;
    }

    /**
     *
     * @return this parameter
     */
    public double getDirection() {
        return myImageView.getRotate();
    }

    /**
     *
     * @return this parameter
     */
    public double getTrigDirection() {
        trigDirection = (360-getDirection()+90)%360;
        //System.out.println(trigDirection);
        return trigDirection;
    }

    /**
     *
     * @param  trigDirection \the new direction to display something in
     */
    public void setTrigDirection(double trigDirection) {
        this.trigDirection = trigDirection;
        myImageView.setRotate((-trigDirection+450)%360);

    }
    private void updateSafeBoxBounds(){
        safeBoxMinGridX = getGridXPos()+widthInGridUnits/2 -  hypotenuse;
        safeBoxMinGridX = safeBoxMinGridX>0 ? safeBoxMinGridX : 0;

        safeBoxMaxGridX = getGridXPos()+widthInGridUnits/2 +  hypotenuse;
        safeBoxMaxGridX = safeBoxMaxGridX<gridXSize ? safeBoxMaxGridX : gridXSize-1;

        safeBoxMinGridY = getGridYPos()+heightInGridUnits/2 - hypotenuse;
        safeBoxMinGridY = safeBoxMinGridY>0 ? safeBoxMinGridY : 0;

        safeBoxMaxGridY = getGridYPos()+heightInGridUnits/2 + hypotenuse;
        safeBoxMaxGridY = safeBoxMaxGridY<gridXSize ? safeBoxMaxGridY : gridYSize-1;
    }
    private void setInCell(int yPos, int xPos) {
        for(int x = (int)safeBoxMinGridX ;x<safeBoxMaxGridX;x++) {
            for(int y = (int)safeBoxMinGridY; y<safeBoxMaxGridY;y++) {
                if (x >= parent.getActiveLevel().getMyGrid().length || y >= parent.getActiveLevel().getMyGrid()[0].length){
                    this.setDisplayState(DisplayState.DIED);
                    continue;
                }
                Cell cell = parent.getActiveLevel().getMyGrid()[x][y];
                myCells.add(cell);
                if(parent instanceof ActiveEnemy) {
                    cell.addEnemy((ActiveEnemy) parent);

                }
                else if (parent instanceof ActiveWeapon) {
                    cell.setWeapon((ActiveWeapon) parent);
                }
            }
        }
    }

    private void setPixelPos(double pixelXPos, double pixelYPos, double direction) {
        updateSafeBoxBounds();
        removeFromCell();
        if(isOutOfBoundsPixel(pixelXPos,pixelYPos)) {
            displayState = DisplayState.DIED;
        }
        else {
            this.pixelYPos = pixelYPos;
            this.pixelXPos = pixelXPos;
            this.displayDirection = direction;
            this.gridXPos = (int) (pixelXPos*(gridXSize/paneWidth));
            this.gridYPos = (int) (pixelYPos*(gridYSize/paneHeight));
            setImageView(pixelXPos,pixelYPos,direction);
            setInCell(gridYPos, gridXPos);
        }
    }

    private void removeFromCell() {
        for(Cell cell : myCells) {
//        for(int x = 0 ;x<widthInGridUnits;x++) {
//            for(int y = 0; y<heightInGridUnits;y++) {
            //Cell cell = parent.getActiveLevel().getMyGrid()[gridXPos + x][gridYPos + y];
            //myCells.remove(cell);
            if (parent instanceof ActiveWeapon) {
                cell.removeWeapon();
            } else if (parent instanceof ActiveEnemy) {
                cell.removeEnemy((ActiveEnemy) parent);
            }
//            }
//        }
        }
        myCells.clear();
    }
}

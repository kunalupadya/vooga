package Configs.GamePackage;

import ActiveConfigs.ActiveLevel;
import ActiveConfigs.ActiveWeapon;
import ActiveConfigs.LevelSpawner;
import Configs.*;
import Configs.ArsenalConfig.Arsenal;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.Behaviors.Behavior;
import Configs.GamePackage.GameBehaviors.GameBehavior;
import Configs.LevelPackage.Level;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.scene.image.Image;
import org.w3c.dom.events.Event;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulating class for the XML object that will be passed to the player from the authoring environmnet. Everythin needed to run a game can be found here
 */
public class Game implements Updatable, Configurable {

    public static final double gridPixelWidth = 585;
    public static final double gridPixelHeight = 585;
    private static final double STARTING_CASH = 500;
    private Configuration myConfiguration;

    public static final String DISPLAY_LABEL = "Game";

    @Configure
    private String myName;
    @Configure
    private String myDescription;
    @Configure
    private int myThumbnailID;
    @Configure
    private Level[] levelList = new Level[0];
    @Configure
    private Arsenal myArsenal;
    @Configure
    private GameBehavior gameType;
    @Configure
    private double myCash = STARTING_CASH;

    @XStreamOmitField
    private transient double paneWidth;
    @XStreamOmitField
    private transient double paneHeight;

    @XStreamOmitField
    private transient LevelSpawner myLevelSpawner;
    @XStreamOmitField
    private transient GameStatus gameStatus;
    private int myScore = 0;
    @XStreamOmitField
    private transient Map<Integer, Image> imageCache;


    public Game(){
        myConfiguration = new Configuration(this);
    }

    /**
     *
     * @return the arsenal for this game
     */
    public Arsenal getArsenal() {
        return myArsenal;
    }

    /**
     * allows for the access and display of the score
     * @param score
     */
    public void setScore(int score) {
        this.myScore = score;
    }

    /**
     * allows for incrementing of the score
     * @param points
     */
    public void addToScore(int points) {
        myScore+=points;
    }

    /**
     * Determines whether the game has an image to be displayed when it's run in the player
     * @param imageID
     * @return
     */
    public boolean hasImage(int imageID) {
        if(imageCache==null) imageCache = new HashMap<>();
        return imageCache.containsKey(imageID);
    }

    /**
     * Gives the game an image
     * @param imageID
     * @param image
     */
    public void addImage(int imageID, Image image) {
        imageCache.put(imageID, image);
    }

    public Image getImage(int imageID) throws IllegalStateException{
        if(!hasImage(imageID)) throw new IllegalStateException();
        return imageCache.get(imageID);
    }

    /**
     * returns the score to display
     * @return
     */
    public int getScore() {
        return myScore;
    }


    @Override
    public void update(double ms, Updatable parent) {
        gameType.update(ms, this);

        myLevelSpawner.update(ms, this);
    }

    /**
     *
     * @return the game mode
     */
    public GameBehavior getGameType() {
        return gameType;
    }

    /**
     * allows for ending, starting, telling if the game is still in the middle
     * @param gameStatus
     */
    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * initialzer method for when the game starts
     * @param levelNumber level to start on
     * @param paneWidth
     * @param paneHeight
     * @throws IllegalStateException
     */
    public void startGame(int levelNumber, double paneWidth, double paneHeight) throws IllegalStateException{
        if(levelNumber>=levelList.length) {
            throw new IllegalStateException();
        }
        this.paneHeight = paneHeight;
        this.paneWidth = paneWidth;
        this.myLevelSpawner = new LevelSpawner(this, levelNumber, levelList);
        if (myArsenal!=null) {
            myArsenal.setUnlockedWeaponsToNew();
        }
        gameStatus = GameStatus.PLAYING;
    }

    /**
     * lets the logic know whether this is the last level
     * @return
     */
    public boolean isLastLevel() {
        return myLevelSpawner.getLevelIndex()==levelList.length-1;
    }

    public LevelSpawner getLevelSpawner() {
        return myLevelSpawner;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    public ActiveLevel getActiveLevel() {
        return myLevelSpawner.getCurrLevel();
    }

    public String getTitle(){
        return myName;
    }

    public String getDescription(){
        return myDescription;
    }

//    // TODO: Get rid of this method
//    public int getThumbnail(){
//        return myThumbnailID;
//    }

    public int getThumbnailID(){
        return myThumbnailID;
    }

    public double getPaneWidth() {
        return paneWidth;
    }

    public double getPaneHeight() {
        return paneHeight;
    }

    /**
     * allow for the manipulation of player's money
     * @return
     */
    public double getCash(){return myCash;}
    public void addToCash(double newCash){myCash = myCash+newCash;}
    public boolean buy(double cost){
        if (myCash-cost<0){
            return false;
        }
        myCash-=cost;
        return true;
    }

    public Map<String, Integer> getSpecialParameter(){
        return gameType.getSpecialValueForDisplay();
    }

    @Override
    public String getName() {
        return myName;
    }
}

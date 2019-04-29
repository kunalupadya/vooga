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


public class Game implements Updatable, Configurable {

    public static final double gridPixelWidth = 585;
    public static final double gridPixelHeight = 585;

    private transient Configuration myConfiguration;

    public static final String DISPLAY_LABEL = "Game";

    @Configure
    private String myName;
    @Configure
    private String myDescription;
    // TODO: Remove myThumbnail variable
    /*@Configure
    private String myThumbnail;*/
    @Configure
    private int myThumbnailID;
    @Configure
    private Level[] levelList = new Level[0];
    @Configure
    private Arsenal myArsenal;
    @Configure
    private GameBehavior gameType;

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

    //TODO:TEST VALUE OF CASH NEED TO MAKE CONFIGURABLE LATER
    private double myCash = 100000;

    public Game(){
        myConfiguration = new Configuration(this);
    }

    public Arsenal getArsenal() {
        return myArsenal;
    }

    public void setScore(int score) {
        this.myScore = score;
    }

    public void addToScore(int points) {
        myScore+=points;
    }

    public boolean hasImage(int imageID) {
        if(imageCache==null) imageCache = new HashMap<>();
        return imageCache.containsKey(imageID);
    }

    public void addImage(int imageID, Image image) {
        imageCache.put(imageID, image);
    }

    public Image getImage(int imageID) throws IllegalStateException{
        if(!hasImage(imageID)) throw new IllegalStateException();
        return imageCache.get(imageID);
    }

    public int getScore() {
        return myScore;
    }


    @Override
    public void update(double ms, Updatable parent) {
        gameType.update(ms, this);

        myLevelSpawner.update(ms, this);
    }

    public GameBehavior getGameType() {
        return gameType;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void startGame(int levelNumber, double paneWidth, double paneHeight) throws IllegalStateException{
        if(levelNumber>=levelList.length) {
            throw new IllegalStateException();
        }
        this.paneHeight = paneHeight;
        this.paneWidth = paneWidth;
        this.myLevelSpawner = new LevelSpawner(this, levelNumber, levelList);
        gameStatus = GameStatus.PLAYING;
    }

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

    public double getCash(){return myCash;}
    public void addToCash(double newCash){myCash = myCash+newCash;}

    @Override
    public String getName() {
        return myName;
    }
}

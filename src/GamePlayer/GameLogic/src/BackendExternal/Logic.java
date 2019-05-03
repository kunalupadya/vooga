package BackendExternal;

import ActiveConfigs.Cell;
import Configs.*;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.GamePackage.Game;
import Configs.GamePackage.GameStatus;
import Configs.MapPackage.Terrain;
import Data.GameLibrary;
import ExternalAPIs.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Project 4: VoogaSalad
 * Duke CompSci 308 Spring 2019 - Duvall
 * Date Created: 4/4/2019
 * Date Last Modified: 5/2/2019
 * @author Brian Jordan
 */

public class Logic {

    private static final int DEFAULT_START_LEVEL = 0;
    private final double PANE_WIDTH;
    private final double PANE_HEIGHT;

    private Game myGame;
    private PlayerData myPlayerData;

    public Logic(double paneWidth, double paneHeight) {
        myPlayerData = new PlayerData();
        PANE_WIDTH = paneWidth;
        PANE_HEIGHT = paneHeight;
    }

    /**
     * Receives user login input from the front-end and passes it to the database module to check against server data
     * @param username - User input for unique string to identify user
     * @param password - User input for chosen string to verify user identity
     * @return - boolean indicating if existing user credentials were matched
     */
    public boolean authenticateUser(String username, String password)throws IllegalArgumentException, IllegalAccessError{
        return myPlayerData.authenticateUser(username, password);
    }

    /**
     * Receives user create account input from the front-end and passes it to save in the database
     * @param username - User input for unique string to identify user
     * @param password - User input for chosen string to verify user identity
     * @param passwordRepeated - User input for chosen string repeated to verify user identity
     */
    public void createNewUser(String username, String password, String passwordRepeated)throws IllegalArgumentException{
        myPlayerData.createNewUser(username, password, passwordRepeated);
    }

    /**
     * Polls the database to return the list of games that can be played by the user.
     * @return - List of GameInfo Objects containing basic information about created games
     */
    public List<GameInfo> getGameOptions(){
        return myPlayerData.getAuthoredGames();
    }

    /**
     * Polls the database to return the list of score leaders for the current game
     * @param numberOfEntries - number of leaders to retrieve
     * @return - unmodifiable list of LeaderBoardEntry objects
     */
    public List<LeaderBoardEntry> getLeaderBoardEntries(int numberOfEntries){
        return myPlayerData.compileLeaderboardEntries(numberOfEntries);
    }

    /**
     * Retrieves the selected games XML string from the database and deserializes it into the specific game object
     * @param selectedGame - One of the game info objects selected from the provided list
     */
    public void createGameInstance(GameInfo selectedGame){
        XStream serializer = new XStream(new DomDriver());
        String gameXMLString = myPlayerData.getGameString(selectedGame);
        myGame =  (Game)serializer.fromXML(gameXMLString);
    }

    public void setGameFromAuthoring(Game game){
        myGame = game;
    }

    /**
     * Begins the game at the state that the current user left off at when they previously played
     */
    public int startAtUserState(){
        UserState gameState = myPlayerData.getCurrentUserState();
//        System.out.println(gameState);
        int levelIndex = gameState.getMyCurrentScore();
        myGame.setScore(levelIndex);
        myGame.startGame(levelIndex, PANE_WIDTH, PANE_HEIGHT);
        return levelIndex + 1;
    }

    /**
     * Begins the game at the default state
     */
    public int startAtDefaultState(){
        myGame.startGame(DEFAULT_START_LEVEL, PANE_WIDTH, PANE_HEIGHT);
        return DEFAULT_START_LEVEL + 1;
    }

    /**
     * Polls the database for the byte array associated with the specific imageID and converts it to a JavaFX Image object
     * @param imageID - integer value corresponding to the specific image in the database
     * @return - Java image object requested
     */
    public Image getImage(int imageID){
        byte[] imageBytes = myPlayerData.getImage(imageID);
        InputStream byteIS = new ByteArrayInputStream(imageBytes);
        return new Image(byteIS);
    }

    public Map<String, Integer> getSpecialParameterToDisplay(){
        return myGame.getSpecialParameter();
    }

    /**
     * Gets current user game session level index and score to pass to the database to store
     */
    public void saveGameState(){
        UserState currentUserState = new UserState(myGame.getLevelSpawner().getLevelIndex(), myGame.getScore());
        myPlayerData.saveUserState(currentUserState);
    }

    @Deprecated
    public void createGameInstance(GameInfo selectedGame, double paneWidth, double paneHeight) {
      //  myGame = myGameLibrary.getGame(selectedGame);
        myGame.startGame(DEFAULT_START_LEVEL, paneWidth, paneHeight);
    }

    /**
     * Calls method in Game object to begin the next level
     * @return - int level index
     */
    public int startNextLevel(){
        return myGame.getLevelSpawner().startNextLevel();
    }

    @Deprecated
    public List<ImmutableImageView> getLevelTerrain(){
        return myGame
                .getActiveLevel()
                .getMyMapConfig()
                .getTerrain()
                .stream()
                .map(terrain -> getImageView(terrain))
                .collect(Collectors.toList());

    }

    /**
     * Returns the list of initial map features to be added to the root in the visualization side
     * @param screenWidth - double width of Game window
     * @param screenHeight - double width of Game window
     * @return - List of Immutable Image View instances to add to add to visualization
     */
    public List<ImmutableImageView> getLevelTerrain(double screenWidth, double screenHeight){
        return myGame
                .getActiveLevel()
                .getMyMapConfig()
                .getTerrain()
                .stream()
                .map(terrain -> terrain.getImageView(screenWidth, screenHeight, myGame.getActiveLevel().getGridWidth(),myGame.getActiveLevel().getGridHeight()))
                .collect(Collectors.toList());

    }

    @Deprecated
    private ImmutableImageView getImageView(Terrain t) {
        MapFeature mapFeature = new MapFeature(t.getGridXPos(), t.getGridYPos(), 0.0, t.getView());//should eventually be able to get the grid size from the game directly
        return mapFeature.getImageView();

    }

    @Deprecated
    private ImmutableImageView getImageView(Terrain t, double screenWidth, double screenHeight, int gridWidth, int gridHeight) {
        MapFeature mapFeature = new MapFeature(t.getGridXPos(), t.getGridYPos(), 0.0, t.getView(), screenWidth, screenHeight, gridWidth, gridHeight);//should eventually be able to get the grid size from the game directly
        return mapFeature.getImageView();
    }

    /**
     * Fetches a Map of weapon ID to the weapons basic info
     * @return - map of int weapon ID and Info instance
     */
    public Map<Integer, Info> getMyArsenal(){
        return myGame.getArsenal().getAllNewWeaponConfigOptions();
    }

    /**
     * Calls the Game instance to create an Image View for the specified weapon
     * @param weaponID - int ID for identifying weapon in arsenal
     * @param xPixel - double x placement position in window
     * @param yPixel - double y placement position in window
     * @param direction - int direction top of the imagview should point
     * @return - ImmutableImageView instance to add to the visualization window
     * @throws NotEnoughCashException - Exception denoting user has run out of cash
     */
    public ImmutableImageView instantiateWeapon(int weaponID, double xPixel, double yPixel, int direction) throws NotEnoughCashException {
        Optional<TransferImageView> optionalImmutableImageView = myGame.getArsenal().generateNewWeapon(weaponID, xPixel, yPixel, direction);
        if (optionalImmutableImageView.isPresent()) {
            return myGame.getArsenal().generateNewWeapon(weaponID, xPixel, yPixel, direction).get();
        }
        else throw new NotEnoughCashException("Not Enough Cash");
    }

    /**
     * Calls update on the Game object which propagates update down to each part of the game
     * @param currentTime - double current machine time in milliseconds
     */
    public void update(double currentTime){
        myGame.update(currentTime, null);
    }

    /**
     * Calls the Game object to return any objects that have been created since last time method was called
     * @return - List of ImmutableImageView instances of objects to add to display
     */
    public List<ImmutableImageView> getObjectsToAdd(){
        return myGame.getActiveLevel().getViewsToBeAdded();
    }

    /**
     * Polls the Game object to return any objects that should be removed from the visualization
     * @return - List of ImmutableImageView instances of objects to remove from display
     */
    public List<ImmutableImageView> getObjectsToRemove(){
        return myGame.getActiveLevel().getViewsToBeRemoved();
    }

    /**
     * Returns current score of the user from the Game object
     * @return - integer current score
     */
    public int getScore(){
        return myGame.getScore();
    }

    /**
     * Returns current Cash amount of the user from the Game object
     * @return - double cash amount remaining
     */
    public double getCash(){return myGame.getCash();}

    /**
     * Checks the validity of the placement location of the weapon object on the map
     * @param weaponId - int ID of weapon being placed
     * @param xPixel - double x position in window
     * @param yPixel - double y position in window
     * @param direction - int direction top of weapon is pointing
     * @return - boolean indicating validity of placement location
     */
    public boolean checkPlacementLocation(int weaponId, double xPixel, double yPixel, int direction){
        WeaponConfig weapon = myGame.getArsenal().getWeapon(weaponId);
        View weaponView = weapon.getView();
        int height;
        int width;
        if(direction==90||direction==270) {
            height = weaponView.getHeight();
            width = weaponView.getWidth();
        }
        else{
            height = weaponView.getWidth();
            width = weaponView.getHeight();
        }
        Cell[][] grid = myGame.getActiveLevel().getMyGrid();

        int x = (int) (xPixel*(myGame.getActiveLevel().getGridWidth()/myGame.getActiveLevel().getPaneWidth()));
        int y = (int) (yPixel*(myGame.getActiveLevel().getGridHeight()/myGame.getActiveLevel().getPaneHeight()));

        if(x>=myGame.getActiveLevel().getGridWidth()||x<0||y>=myGame.getActiveLevel().getGridHeight()||y<0) return false;

        for(int col = x;col<x+width;col++) {
            for(int row = y;row<y+height;row++) {
                if (myGame.getActiveLevel().isCellValid(col, row)) {
                    if (!grid[col][row].isValidWeaponPlacement(weapon.isPathWeapon())) return false;
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Polls the Game object for the current state of the game
     * @return - GameStatus enum instance
     */
    public GameStatus getGameStatus(){
        return myGame.getGameStatus();
    }

    @Deprecated
    public boolean checkIfLevelEnd(){
        return myGame.getLevelSpawner().isLevelOver();
    }

    @Deprecated
    public boolean checkIfGameEnd(){
        return myGame.getGameStatus()== GameStatus.OVER;
    }


}

package BackendExternal;

import ActiveConfigs.Cell;
import Configs.*;
import Configs.ArsenalConfig.WeaponConfig;
import Configs.GamePackage.Game;
import Configs.MapPackage.Terrain;
import Data.GameLibrary;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.EventObject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Project 4: VoogaSalad
 * Duke CompSci 308 Spring 2019 - Duvall
 * Date Created: 4/4/2019
 * Date Last Modified: 4/4/2019
 * @author Brian Jordan
 */

public class Logic {

    private static final int DEFAULT_START_LEVEL = 0;
//     TODO: Second Sprint
//     private UserAuthenticator myUserAuthenticator;
//     private myUserGameData;

    private Game myGame;
    private GameLibrary myGameLibrary;


    public Logic() {
//        myUserAuthenticator = new UserAuthenticator();
        myGameLibrary = new GameLibrary();

    }

    // View will call this first to get the name and thumbnail file name of each game
    // No Input
    // Return: List of GameInfo objects
    public List<GameInfo> getGameOptions(){
        return myGameLibrary.getImmutableGameList();
    }

//    TODO: Implement User Authentification in Second Sprint
//    UserData authenticateUser(String userName, String userPassword) throws IllegalAccessError{
//
//    }

    // View calls this when user select a game to play
    // Input: Selected GameInfo Object
    // No Return Value
    public void createGameInstance(GameInfo selectedGame, double paneWidth, double paneHeight) {
        myGame = myGameLibrary.getGame(selectedGame);
        // TODO: Second sprint have the option of getting this from User Data (Previous Level)
        myGame.startGame(DEFAULT_START_LEVEL, paneWidth, paneHeight);
    }

    // View calls to get the current level of the game when moving between levels
    // No Input
    // Return: integer Level number
    public int startNextLevel(){
        return myGame.getLevelSpawner().startNextLevel();
    }


    // View calls this when the user presses play or level is over
    // No Input
    // Return: List of Viewable instances of static level items
    public List<ImmutableImageView> getLevelTerrain(double screenWidth, double screenHeight){
        return myGame
                .getActiveLevel()
                .getMyMapConfig()
                .getTerrain()
                .stream()
                .map(terrain -> terrain.getImageView(screenWidth, screenHeight, myGame.getActiveLevel().getGridHeight(),myGame.getActiveLevel().getGridWidth()))
                .collect(Collectors.toList());

    }



    // View call this when the user presses play or a level is over
    // Return: ID and image file of available weapons
    public Map<Integer, Info> getMyArsenal(){
        return myGame.getArsenal().getAllWeaponConfigOptions();
    }

    // View calls this when a weapon is placed onto the map
    // Input: WeaponInfo Object
    // Return: ImageView corresponding to the weapon
    public ImmutableImageView instantiateWeapon(int weaponID, double xPixel, double yPixel, int direction){
        return myGame.getArsenal().generateNewWeapon(weaponID, xPixel, yPixel, direction);
    }

    // View calls to update the state of the Dynamic parts of the level in the game loop
    // Input: Time the method is called
    // No Return
    public void update(double currentTime){
        myGame.update(currentTime);
    }

    // View calls to get objects to add to the view
    // No Input
    // Return: List of Viewable instances
    public List<ImmutableImageView> getObjectsToAdd(){
        return myGame.getActiveLevel().getViewsToBeAdded();
    }

    // View calls to get objects to remove from the view
    // No Input
    // Return: List of Viewable instances
    public List<ImmutableImageView> getObjectsToRemove(){
        return myGame.getActiveLevel().getViewsToBeRemoved();
    }

    // View calls to check the current score of the game in the game loop
    // No Input
    // Return: integer score
    public int getScore(){
        return myGame.getActiveLevel().getMyScore();
    }

    // View calls to check the current lives of the game in the game loop
    // No Input
    // Return: integer lives
//    public int getNumLives(){
//        return myGame.getActiveLevel().ge;
//    }



    // View calls to check if a location is valid to place a weapon
    // Input: WeaponInfo object, x and y coordinate
    // Return: boolean
    public boolean checkPlacementLocation(int weaponId, double xPixel, double yPixel, int direction){
        WeaponConfig weapon = myGame.getArsenal().getConfiguredWeapons()[weaponId-1];
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



        int x = (int) (xPixel/(myGame.getActiveLevel().getGridWidth()/myGame.getActiveLevel().getPaneWidth()));
        int y = (int) (yPixel/(myGame.getActiveLevel().getGridHeight()/myGame.getActiveLevel().getPaneHeight()));

        for(int col = x;col<x+width;col++) {
            for(int row = y;row<y+height;row++) {
                if (!grid[row][col].isValidWeaponPlacement(weapon.isPathWeapon())) return false;
            }
        }
        return true;
    }
    

    // View calls to move a dynamic object that has already been instantiated
    // Input: WeaponInfo object, x and y coordinate
    // No return
    // TODO: Second Sprint move objects mid level
//    public void placeMovingObject(WeaponInfo placedWeapon, double x, double y){
//
//    }

    // View calls this in game Loop to check if the level has ended
    // No input
    // Return: Boolean value indicating the status of the running level
    boolean checkIfLevelEnd(){
        return myGame.getLevelSpawner().isLevelOver();
    }

    boolean checkIfGameEnd(){
        return myGame.isGameOver();
    }


}

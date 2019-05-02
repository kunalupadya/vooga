package ExternalAPIs;

import Queries.ConnectionException;
import Queries.DataQueries.*;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import static Internal.Authentication.*;

public abstract class Data {

    private final int MAX_LOGIN_ATTEMPTS = 4;

    protected int currentUserID;
    protected int currentGameID;
    private int numberOfLoginAttempts;
    private Random saltGenerator;

    private UserData userData;
    private GameData gameData;
    private SessionData sessionData;
    private ImageData imageData;

    public static final ImageData IMAGE_DATA = new ImageData();



    public Data(){
        numberOfLoginAttempts = 0;
        saltGenerator = new SecureRandom();

        userData = new UserData();
        gameData = new GameData();
        sessionData = new SessionData();
        imageData = new ImageData();
    }

    protected UserData getUserData() {
        return userData;
    }

    protected GameData getGameData() {
        return gameData;
    }

    public int getCurrentUserID() {
        return currentUserID;
    }

    protected SessionData getSessionData() {
        return sessionData;
    }
    
    protected ImageData getImageData() {
        return imageData;
    }

    /**
     * Reads in the provided information from the user to cross check with the user data in the database
     * @param username - Unique string to identify user
     * @param password - chosen string to verify user identity
     * @return - boolean indicating if existing user credentials were matched
     */
    public boolean authenticateUser(String username, String password) throws IllegalArgumentException, IllegalAccessError{
        checkArgumentLengths(username, password);
        numberOfLoginAttempts++;
        if (numberOfLoginAttempts > MAX_LOGIN_ATTEMPTS){
            throw new IllegalAccessError("You have used up all of your login attempts");
        }
        try {
            byte[] salt = userData.getSalt(username).getBytes();
           // String hashedPass =  new String(hashPassword(password, salt));
            currentUserID = userData.login(username, password);
            System.out.println(currentUserID);
            if (currentUserID == -1){
                return false;
            }
            numberOfLoginAttempts = 0;
            return true;
        }
        catch (ConnectionException e){
            System.out.println("Did not authenticate");
            return false;
        }
    }

    /**
     * Takes in new user information error checks it and stores it to the database
     * @param username - Unique string to identify user
     * @param password - chosen string to verify user identity
     * @param passwordRepeated - repeated chosen string
     */
    public void createNewUser(String username, String password, String passwordRepeated)throws RuntimeException{
        checkArgumentLengths(username, password, passwordRepeated);
        passwordErrorChecking(password, passwordRepeated);
        byte[] salt = new byte[16];
        saltGenerator.nextBytes(salt);
        System.out.println(username+" " +password);
        byte[] hashedPassword = hashPassword(password, salt);
        //currentUserID = userData.addUser(username, new String(hashedPassword), new String(salt));
        currentUserID = userData.addUser(username, password, new String(salt));

    }

    /**
     * Provides basic information about each game to be displayed as the game library
     * @return - List of GameInfo Objects containing basic information about created games
     */
    public abstract List<GameInfo> getAuthoredGames();

    /**
     * Retrieves specified game XML from the database and returns it as a Java File Object
     * @param chosenGameInfo - One of the game info objects selected from the provided list
     * @return - String containing the XML file of the selected game
     */
    public String getGameString(GameInfo chosenGameInfo){
        currentGameID = chosenGameInfo.getGameID();
        return new String(chosenGameInfo.getBinary());
    }

    /**
     * Retrieves the byte array corresponding to the specific imageID
     * @param imageID - integer value corresponding to the specific image in the database
     * @return - byte array of requested image
     */

    public byte[] getImage(int imageID){
        return getImageData().fetchImage(imageID);
    }

    /**
     * Retrieves specified image byte array from the database and converts it into a JavaFX image object
     * @param imageID - integer value corresponding to the specific image in the database
     * @return - Image object of specified image
     */
    public static Image getImageStatic(int imageID){
        byte[] imageBytes = IMAGE_DATA.fetchImage(imageID);
        InputStream byteIS = new ByteArrayInputStream(imageBytes);
        return new Image(byteIS);
    }


    // For testing purposes only
    // TODO: Remove method call
    public Image getImage2(int imageID){
        byte[] imageBytes = getImage(imageID);
        InputStream byteIS = new ByteArrayInputStream(imageBytes);
        return new Image(byteIS);
    }

}

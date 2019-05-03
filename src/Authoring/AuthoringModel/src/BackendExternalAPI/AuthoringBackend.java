package BackendExternalAPI;

import Configs.GamePackage.Game;
import ExternalAPIs.AuthoringData;
import ExternalAPIs.GameInfo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.scene.image.Image;
import java.io.*;
import java.util.List;

/**
 * Project 4: VoogaSalad
 * Duke CompSci 308 Spring 2019 - Duvall
 * Date Created: 4/4/2019
 * Date Last Modified: 5/2/2019
 * @author Brian Jordan
 */

/**
 * This class is the backend of the Game Authoring Environment.  The AuthoringView module creates a single instance of
 * this when the application is run.  This class provides a means for the view end of the authoring environment to
 * communicate with the DatabaseUtil module indirectly to ensure encapsulation.
 */

public class AuthoringBackend {
    private final String XML_FILE_PATH = "games/GameXMLs/";
    private Game myGame;
    private String myXMLFileName;
    private final String XML_TAG = "XML.xml";


    private final double MAX_FILE_SIZE = 16 * Math.pow(10,6);
    private AuthoringData myAuthoringData;

    /**
     * Creates a AuthoringBackend object to call the public methods.
     */

    public AuthoringBackend(){
        myAuthoringData = new AuthoringData();
    }

    /**
     * Takes in a configured game object, converts it to an XML string and extracts basic info to create GameInfo object to pass to database module
     * @param newGame - Game object created in authoring environment
     */

    public void saveToXML(Game newGame){
        saveToXML2(newGame);
        XStream mySerializer = new XStream(new DomDriver());
        String gameXMLString = mySerializer.toXML(newGame);
        GameInfo savingInfo = new GameInfo(newGame.getTitle(), newGame.getThumbnailID(), newGame.getDescription());
        myAuthoringData.saveGame(gameXMLString, savingInfo);
    }

//    // TODO: Remove this method and use one above
    public void saveToXML2(Game newGame) {
        myGame = newGame;
        myXMLFileName = myGame.getTitle() + XML_TAG;


        try {
          //  updatePropertiesFile();
            writeToXMLFile();

        } catch (Exception e) {
            // TODO: For Testing Purposes
            e.printStackTrace();
        }
    }
    /**
     * Receives user login input from the front-end and passes it to the database module to check against server data
     * @param username - User input for unique string to identify user
     * @param password - User input for chosen string to verify user identity
     * @return - boolean indicating if existing user credentials were matched
     */
    public boolean authenticateUser(String username, String password){
        return myAuthoringData.authenticateUser(username, password);
    }

    /**
     * Receives user create account input from the front-end and passes it to save in the database
     * @param username - User input for unique string to identify user
     * @param password - User input for chosen string to verify user identity
     * @param passwordRepeated - User input for chosen string repeated to verify user identity
     */
    public void createNewUser(String username, String password, String passwordRepeated){
        myAuthoringData.createNewUser(username, password, passwordRepeated);
    }

    /**
     * Polls the database to return the basic information about the Games the current user has created
     * @return - List of GameInfo objects containing basic info about each Game
     */
    public List<GameInfo> getAuthoredGameLibrary(){
        return myAuthoringData.getAuthoredGames();
    }

    /**
     * Polls the database for the specified XML string and converts it into a Game instance
     * @param selectedGame - GameInfo object of user selected Game
     * @return - Game object of specified Game selection
     */
    public Game loadGameObject(GameInfo selectedGame){
        XStream serializer = new XStream(new DomDriver());
        String gameXMLString = myAuthoringData.getGameString(selectedGame);
//        System.out.println(gameXMLString);
        return (Game)serializer.fromXML(gameXMLString);
//        XStream serializer = new XStream(new DomDriver());
//        String gameXMLString = myPlayerData.getGameString(selectedGame);
//        myGame =  (Game)serializer.fromXML(gameXMLString);
    }

    /**
     * Polls the database to return the image IDs corresponding to the specified image type
     * @param type - ImageType enum instance
     * @return - List of integers denoting image IDs
     */
    public List<Integer> getImageOptions(AuthoringData.ImageType type){
        return myAuthoringData.getImages(type);
    }

    public int uploadImage(File newImageFile, AuthoringData.ImageType imageType) throws java.io.IOException, IllegalArgumentException{
        int fileSize = (int) newImageFile.length();
        checkFileSize(fileSize);
        byte[] fileBytes = new byte[fileSize];
        InputStream imageIS = new FileInputStream(newImageFile);
        imageIS.read(fileBytes);
        return myAuthoringData.storeImage(fileBytes, imageType);
    }


    private void checkFileSize(int size) throws IllegalArgumentException{
        if (size > MAX_FILE_SIZE){
            throw new IllegalArgumentException("File too Large > 16MB");
        }
    }

    /**
     * Polls the database for the byte array associated with the specific imageID and converts it to a JavaFX Image object
     * @param imageID - integer value corresponding to the specific image in the database
     * @return - Java image object requested
     */
    public Image getImage(int imageID){
        byte[] imageBytes = myAuthoringData.getImage(imageID);
        InputStream byteIS = new ByteArrayInputStream(imageBytes);
        return new Image(byteIS);
    }

    private void writeToXMLFile() throws IOException {
        XStream mySerializer = new XStream(new DomDriver());
        String gameString = mySerializer.toXML(myGame);
        FileWriter xmlFW = new FileWriter(XML_FILE_PATH + myXMLFileName);
        xmlFW.write(gameString);
        xmlFW.close();

    }


}

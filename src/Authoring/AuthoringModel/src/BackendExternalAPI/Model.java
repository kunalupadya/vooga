package BackendExternalAPI;

import Configs.GamePackage.Game;
import ExternalAPIs.AuthoringData;
import ExternalAPIs.GameInfo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.scene.image.Image;

import javax.print.DocFlavor;
import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Model {

    private final String PROPERTIES_FILE_PATH = "games/GameInfo.properties";
    private final String XML_FILE_PATH = "games/GameXMLs/";
    private final String REGEX = "~";
    private final String XML_TAG = "XML.xml";

    private Game myGame;
    private String myXMLFileName;
    private AuthoringData myAuthoringData;


    public Model(){
        myAuthoringData = new AuthoringData();
    }

    /**
     * Takes in a configured game object, converts it to an XML string and extracts basic info to create GameInfo object to pass to database module
     * @param newGame - Game object created in authoring environment
     */
    // TODO: Make this the called method
    public void saveToXML(Game newGame){
        XStream mySerializer = new XStream(new DomDriver());
        String gameXMLString = mySerializer.toXML(newGame);
        GameInfo savingInfo = new GameInfo(newGame.getTitle(), newGame.getThumbnailID(), newGame.getDescription());
        myAuthoringData.saveGame(gameXMLString, savingInfo);
    }

//    // TODO: Remove this method and use one above
//    public void saveToXML(Game newGame) {
//        myGame = newGame;
//        try {
//            updatePropertiesFile();
//            writeToXMLFile();
//
//        } catch (Exception e) {
//            // TODO: For Testing Purposes
//            e.printStackTrace();
//        }
//    }

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
        return (Game)serializer.fromXML(gameXMLString);
    }

    /**
     * Polls the database to return the image IDs corresponding to the specified image type
     * @param type - ImageType enum instance
     * @return - List of integers denoting image IDs
     */
    public List<Integer> getImageOptions(AuthoringData.ImageType type){
        return myAuthoringData.getImages(type);
    }

    // Do Not Call Yet !!!!!!!!!!!!!!!
    // Use file chooser and pass selected File object into this method
    // TODO: Do not think we are going to use this method anymore
    public int uploadImage(File newImageFile, AuthoringData.ImageType imageType) throws java.io.IOException{
        // TODO: Check length of image file and throw exception if too large
        int fileSize = (int) newImageFile.length();
//        checkFileSize(fileSize);
        byte[] fileBytes = new byte[fileSize];
        InputStream imageIS = new FileInputStream(newImageFile);
        imageIS.read(fileBytes);
        return myAuthoringData.storeImage(fileBytes, imageType);
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

//    // TODO: Remove Method call
//    private void updatePropertiesFile() throws IOException{
//        FileInputStream propertiesIS = new FileInputStream(PROPERTIES_FILE_PATH);
//        Properties myGameDetails = new Properties();
//        myGameDetails.load(propertiesIS);
//        myXMLFileName = myGame.getTitle() + XML_TAG;
//        String propertyValue = myGame.getThumbnail() + REGEX + myGame.getDescription() + REGEX + myXMLFileName;
//
//        //System.out.println(propertyValue);
//
//
//
//        myGameDetails.setProperty(myGame.getTitle(),propertyValue);
//        FileOutputStream propertiesOS = new FileOutputStream(PROPERTIES_FILE_PATH);
//        myGameDetails.store(propertiesOS, null);
//    }

    // TODO: Remove method call
    private void writeToXMLFile() throws IOException {
        XStream mySerializer = new XStream(new DomDriver());
        String gameString = mySerializer.toXML(myGame);
        FileWriter xmlFW = new FileWriter(XML_FILE_PATH + myXMLFileName);
        xmlFW.write(gameString);
        xmlFW.close();

    }


}

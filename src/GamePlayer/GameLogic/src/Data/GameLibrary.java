package Data;

import Configs.GamePackage.Game;
import ExternalAPIs.GameInfo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Project 4: VoogaSalad
 * Duke CompSci 308 Spring 2019 - Duvall
 * Date Created: 4/4/2019
 * Date Last Modified: 4/4/2019
 * @author Brian Jordan
 */

/**
 * This class was used in the first sprint to handle the data storage of the program.  Data was exchanged locally during
 * the first sprint.  This class handled retrieving information about the games that have been created and instantiating
 * a game object.  This class was instantiated by the Logic class in the first sprint.
 */

public class GameLibrary {

    private final String REGEX = "~";
    private final String PROPERTIES_FILE_PATH = "games/GameInfo.properties";
    private final String XML_FILE_PATH = "games/GameXMLs/";
    private List<GameInfo> myGames;
    private Map<String,String> myXMLFileNames;

    /**
     * Instantiates a GameLibrary Object
     */
    public GameLibrary(){
        myGames = new ArrayList<>();
        myXMLFileNames = new HashMap<>();
        try {
            populateLibrary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateLibrary() throws IOException {
        FileInputStream propertiesIS = new FileInputStream(PROPERTIES_FILE_PATH);
        Properties myGameDetails = new Properties();
        myGameDetails.load(propertiesIS);
        for (String s : myGameDetails.stringPropertyNames()){
            String[] gameDetails = myGameDetails.getProperty(s).split(REGEX);
        //    GameInfo newGameInfo = new GameInfo(s, gameDetails[0], gameDetails[1]);
            myXMLFileNames.put(s,gameDetails[2]);
         //   myGames.add(newGameInfo);
        }
    }

    /**
     * Returns an unmodifiable version of the myGames list created in populateLibrary
     * @return - List of GameInfo objects corresponding to the games that have been created
     */
    public List<GameInfo> getImmutableGameList(){
        return Collections.unmodifiableList(myGames);
    }

    /**
     * Retrieves the XML corresponding to the selected game and deserializes it into a Game object instance
     * @param chosenGameInfo - GameInfo object corresponding to the Game the user selected to play
     * @return - Game Object of the chosen Game
     */
    public Game getGame(GameInfo chosenGameInfo){
        XStream serializer = new XStream(new DomDriver());
        String gameXMLFileName = myXMLFileNames.get(chosenGameInfo.getGameTitle());
        File xmlFile = new File(XML_FILE_PATH + gameXMLFileName);
        return (Game)serializer.fromXML(xmlFile);
    }
}

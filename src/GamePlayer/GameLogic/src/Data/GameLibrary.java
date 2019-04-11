package Data;

import BackendExternal.GameInfo;
import Configs.GamePackage.Game;
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

public class GameLibrary {

    private final String REGEX = "~";
    private final String PROPERTIES_FILE_PATH = "games/GameInfo.properties";
    private final String XML_FILE_PATH = "games/GameXMLs/";

    private List<GameInfo> myGames;
    private Map<String,String> myXMLFileNames;


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
        System.out.println("Reading from Game Options Properties File");
        for (String s : myGameDetails.stringPropertyNames()){
            String[] gameDetails = myGameDetails.getProperty(s).split(REGEX);
            GameInfo newGameInfo = new GameInfo(s, gameDetails[0], gameDetails[1]);
            myXMLFileNames.put(s,gameDetails[2]);
            myGames.add(newGameInfo);
        }
    }

    public List<GameInfo> getImmutableGameList(){
        return Collections.unmodifiableList(myGames);
    }

    public Game getGame(GameInfo chosenGameInfo){
        XStream serializer = new XStream(new DomDriver());
        String gameXMLFileName = myXMLFileNames.get(chosenGameInfo.getGameTitle());
        System.out.println("Loading Game XML File: " + gameXMLFileName);
        File xmlFile = new File(XML_FILE_PATH + gameXMLFileName);
        System.out.println("Un-Serializing Game XML File");
        return (Game)serializer.fromXML(xmlFile);
    }




}

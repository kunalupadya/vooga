package Queries;

import Queries.DataQueries.GameData;
import Queries.DataQueries.ImageData;
import Queries.DataQueries.SessionData;
import Queries.DataQueries.UserData;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Tester{

    private UserData userData = new UserData();
    private GameData gameData = new GameData();
    private ImageData imageData = new ImageData();
    private SessionData sessionData = new SessionData();

    private int validUserID = 7;
    private int validImageID = 7;
    private int validGameID = 2;
    private int validSessionID = 2;

    private String gameString = "test";
    private String description = "Description";
    private String title = "title";
    private byte[] image = this.getClass().getClassLoader().getResource("water.jpg").getFile().getBytes();

    @Test
    public void fetchSalt(){
        assertEquals(userData.getSalt("stacy"), "minimouse" );
    }

    @Test void getHighScores(){
        int maxCount = 3;
        assertTrue(sessionData.getHighScoresForGame(validGameID, maxCount).size() <= maxCount);
    }

    @Test void getMostRecentSession(){
        System.out.println(sessionData.getMostRecentSessionForGame(validUserID, validGameID));
    }

    @Test
    public void fetchGame(){
        String input = new String(gameData.fetchGame(validGameID).getBinary());
        String output = new String(gameString.getBytes());
        assertEquals(input, output);
    }

    @Test
    public void fetchImage(){
        int imageID = 28;
        System.out.println(imageData.fetchImage(imageID));
    }

    @Test
    @Disabled
    public void AddGame(){
        gameData.addGame(validUserID, description, gameString.getBytes(), validImageID, title);
    }

    @Test
//    @Disabled
    public void AddSession(){
        sessionData.addSession((int)(Math.random()*5000),(int)(Math.random()*5000), validGameID, validUserID);
    }

    @Test
    public void addImage(){
        imageData.addImage(image, "TERRAIN");
    }

    @Test
    @Disabled
    public void AddUser() {
        userData.addUser("stacy2", "barker", "minimouse");
    }



    @Test
    public void add_TwoPlusTwo_ReturnsFour() {
        assertEquals(4,4);
    }
}

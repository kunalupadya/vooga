package Queries;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class Tester{

    private UserData userData = new UserData();
    private GameData gameData = new GameData();
    private ImageData imageData = new ImageData();
    private SessionData sessionData = new SessionData();

    private int validUserID = 7;
    private int validImageID = 7;
    private int validGameID = 3;
    private int validSessionID = 2;

    private String gameString = "test";
    private String description = "Description";
    private String title = "title";
    private byte[] image = this.getClass().getClassLoader().getResource("water.jpg").getFile().getBytes();


    public void test(){
        //DBUtil util = new DBUtil();
       // System.out.println(new UserData().addUser("stacy2", "barker", "minimouse"));
        //UserData users = new UserData();
        //System.out.println(users.login("stacy","barker"));
//        int id = data.addImage(file, ""+ (int)(Math.random()*10));
//        System.out.println(data.fetchImage(id));

        //System.out.println(data.fetchImageIDs("2"));
        //util.addSession("stacy",1,2,3);
        //util.addTest(file, "tree");
        //util.addImage(file);
    }

    @Test
    @Disabled
    public void AddGame(){
        gameData.addGame(validUserID, description, gameString.getBytes(), validImageID, title);
    }

    @Test
    public void AddSession(){
        sessionData.addSession((int)(Math.random()*5000),(int)(Math.random()*5000), validGameID, validUserID);
    }

    @Test
    public void fetchGame(){
        String input = new String(gameData.fetchGame(3).getBinary());
        String output = new String(gameString.getBytes());
        assertEquals(input, output);
    }

    @Test
    public void addImage(){
        imageData.addImage(image, "TREE");
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

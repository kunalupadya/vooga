package ExternalAPIs;

import ExternalAPIs.*;
import Internal.Authentication;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseTest extends Application {

    public static final int WINDOW_WIDTH = 300;
    public static final int WINDOW_HEIGHT = 300;

    private static AuthoringData myAuthoringData;
    private static PlayerData myPlayerData;

    private static Stage myStage;
    private static Group myRoot;
    private static Scene myScene;

    public static void main (String[]args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        myAuthoringData = new AuthoringData();
        myPlayerData = new PlayerData();

        myStage = stage;
        myRoot = new Group();
        myScene = new Scene(myRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
        myStage.setScene(myScene);
        myStage.setTitle("Database Testing");
        myStage.show();

        System.out.println("Beginning Tests");
        System.out.println();
//        testUserAuthentification();
//        System.out.println();
//        testCreateUser();
//        System.out.println();
//        testGetAuthoredGames();
//        System.out.println();
//        testGetXMLString();
//        System.out.println();
        testGetImage();
//        System.out.println();
//        testStoreBasicInfo();
//        System.out.println();
//        testStoreXMLString();
//        System.out.println();
//        testGetImages();
//        System.out.println();
//        startFileChooser();
//        System.out.println();
//        testGetUserState();
//        System.out.println();
//        testSaveCurrentUserState();
//        System.out.println();
//        testCompileLeaderBoard();
//        System.out.println();
//        hashingTest();

        System.out.println("Done Tests");

    }

    // Data Class

    // TODO: Test Authenticate User

    private static void testUserAuthentification(){
        System.out.println("Testing User Authentication:");

        String username = "brianjordan";
        String password = "AlmostDoneWith308";

        if (myAuthoringData.authenticateUser(username, password)){
            System.out.println("User Authenticated");
        }

        if (myPlayerData.authenticateUser(username, password)){
            System.out.println("User Authenticated");
        }

    }

    // TODO: Test Create User

    private static void testCreateUser(){
        System.out.println("Testing Create User:");

        String username = "brianjordan";
        String password = "AlmostDoneWith308";
        String repeatedPassword = "AlmostDoneWith308";

        myAuthoringData.createNewUser(username, password, repeatedPassword);
        myPlayerData.createNewUser(username, password, repeatedPassword);

        System.out.println("New User Created");

    }

    // TODO: Test get authored games

//    private static void testGetAuthoredGames(){
//        System.out.println("Testing Get Authored Games:");
//
//        ArrayList<GameInfo> games = (ArrayList)myAuthoringData.getAuthoredGames();
//
//        for (GameInfo g : games){
//            System.out.println(g.getGameTitle());
//            System.out.println(g.getGameDescription());
//            System.out.println(g.getGameThumbnail());
//        }
//
//        System.out.println("End of Authored games");
//
//    }

    // TODO: Test Get Game XML String

    private static void testGetXMLString(){
        System.out.println("Testing Get XML String:");

        String title = "My New Game";
        String description = "This is my new game";
        int thumbnail = 123123123;

        // TODO: CHANGE
        GameInfo newGameInfo = new GameInfo(title, thumbnail, description);

        String gameString = myAuthoringData.getGameString(newGameInfo);

        System.out.println(gameString);

    }

    // TODO: Test get Image

    private static void testGetImage(){

        System.out.println("Testing Get Image:");

        int imageID = 70;

        Image image = myAuthoringData.getImage2(imageID);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(WINDOW_WIDTH);
        imageView.setFitHeight(WINDOW_HEIGHT);
        myRoot.getChildren().add(imageView);

        System.out.println("Done Get Image");

    }

    // AuthoringData Class

    // TODO: Test save game

    private static void testSaveGame(){

        System.out.println("Testing Store Game:");

        String gameString = "This is the game XML String";

        String title = "My New Game";
        String description = "This is my new game";
        int thumbnail = 123123123;

        GameInfo newGameInfo = new GameInfo(title, thumbnail, description);

        myAuthoringData.saveGame(gameString,newGameInfo);

        System.out.println("Done Saving Game");
    }

    // TODO: Test Get ImageID list

    private static void testGetImages(){

        System.out.println("Testing Get Images:");

        ArrayList<Integer> imageList = (ArrayList)myAuthoringData.getImages(AuthoringData.ImageType.TERRAIN);

        for (int i : imageList){
            System.out.println(i);
        }

        System.out.println("Done getting image ID");
    }

    // TODO: Test Store Image

    public static void startFileChooser(){

        System.out.println("Testing storing image files:");

        var fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.JPG"));
        Button fileButton = makeButton(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File selectedFile = fc.showOpenDialog(myStage);
                testStoreImage(selectedFile);
            }
        });
        myRoot.getChildren().add(fileButton);
    }

    private static void testStoreImage(File selectedFile){
        try {
            myAuthoringData.uploadImage(selectedFile, AuthoringData.ImageType.TERRAIN);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done storing image file");
    }

    public static Button makeButton(EventHandler<ActionEvent> handler){
        var newButton = new Button();
        newButton.setOnAction(handler);
        return newButton;
    }

    // PlayerData Class

    // TODO: Test get current user state

    private static void testGetUserState(){

        System.out.println("Testing get current user state:");

        UserState us = myPlayerData.getCurrentUserState();

        System.out.println("Level: " + us.getMyCurrentLevel() + "  Score: " + us.getMyCurrentScore());

        System.out.println("Done getting current user state");

    }



    // TODO: Test save current user state

    private static void testSaveCurrentUserState(){

        System.out.println("Testing saving current user state:");

        UserState savingUserState = new UserState(3, 100);

        myPlayerData.saveUserState(savingUserState);

        System.out.println("Done saving current user state");

    }

    // TODO: Test compile leader board entries

    private static void testCompileLeaderBoard(){

        System.out.println("Testing compiling leaderboard:");

        int numEntries = 3;

        ArrayList<LeaderBoardEntry> leaderboard = (ArrayList)myPlayerData.compileLeaderboardEntries(numEntries);

        for (LeaderBoardEntry entry : leaderboard){
            System.out.println("Rank: " + entry.getRank() + "  Username: " + entry.getUserName() + "  Score: " + entry.getScore());
        }

        System.out.println("Done compiling leaderboard");

    }

    private static void hashingTest(){
        String password = "password";
        byte[] salt = new byte[16];

        Authentication.hashPassword(password, new String(salt));
    }


}

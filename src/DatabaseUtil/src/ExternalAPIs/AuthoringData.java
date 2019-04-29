package ExternalAPIs;

import Queries.DataQueries.UserData;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AuthoringData extends Data{

    public enum ImageType {
        // TODO: Add any other image types to this list
        TERRAIN, WEAPON, PROJECTILE, ENEMY, THUMBNAIL;
    }

    public AuthoringData(){
        super();
    }

    /**
     * Returns the list of games that the current user has authored for which he or she has permission to edit
     * @return - List of games authored by the current user, Null if user has not authored a game
     */
    @Override
    public List<GameInfo> getAuthoredGames() {
        List<GameInfo> gameInfos = new ArrayList<>();
        getUserData().getAuthoredGames(currentUserID).stream().forEach(
                (id) -> {gameInfos.add(getGameData().fetchGame(id));}
        );
        return gameInfos;
    }



    /**
     * Converts gameXMLString to byte array, extracts information from GameInfo and calls method to add game to the database
     * @param gameXMLString - String containing XML File of the Game being saved
     * @param newGame - GameInfo object containing the basic info about the Game being saved
     */
    public void saveGame(String gameXMLString, GameInfo newGame){
        byte[] gameByteArray = gameXMLString.getBytes();
        String title = newGame.getGameTitle();
        int thumbnail = newGame.getGameThumbnailID();
        String description = newGame.getGameDescription();
        getGameData().addGame(currentUserID, description, gameByteArray, thumbnail, title);
    }



    /**
     * Returns a list of the image ids of the images saved in the database with the corresponding tag
     * @param imageType - Enum value denoting the type of image
     * @return - List of image ids corresponding to the specific type
     */
    public List<Integer> getImages(ImageType imageType){
        String type = imageType.toString();
        return getImageData().fetchImageIDs(type);
    }

    /**
     * Stores byte array and image type enum of an image in the database image library and returns the Primary Key of the table
     * @param imageBytes - byte array of image file
     * @param imageType - enum corresponding to image being saved
     * @return - Primary key of the image in the database to be referenced later
     */
    public int storeImage(byte[] imageBytes, ImageType imageType){
        return getImageData().addImage(imageBytes, imageType.name());
    }

    // Just for testing Purposes
    // TODO: REMOVE BEFORE SUBMITTING

    public int uploadImage(File newImageFile, AuthoringData.ImageType imageType) throws java.io.IOException{
        // TODO: Check length of image file and throw exception if too large
        int fileSize = (int) newImageFile.length();
        byte[] fileBytes = new byte[fileSize];
        InputStream imageIS = new FileInputStream(newImageFile);
        imageIS.read(fileBytes);
        return storeImage(fileBytes, imageType);
    }


}

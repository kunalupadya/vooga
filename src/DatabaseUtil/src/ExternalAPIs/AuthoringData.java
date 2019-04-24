package ExternalAPIs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuthoringData extends Data{

    public AuthoringData(){
        super();
    }

    /**
     * Returns the list of games that the current user has authored for which he or she has permission to edit
     * @return - List of games authored by the current user, Null if user has not authored a game
     */
    @Override
    public List<GameInfo> getAuthoredGames() {
        // TODO: Use current UserID to loop through game library picking out ones with matching Author field
        currentUserID = currentUserID;
        List<GameInfo> authoredGames = new ArrayList<>();
        // TODO: Change loop to go through authored games list
        for (int i = 0; i < 1; i++){
            // TODO: Fetch game info - title, thumbnail imageID, description
            GameInfo nextGame = new GameInfo("Title", "Thumbnail.jpeg", "Description");
            authoredGames.add(nextGame);
        }
        // TODO: Download Image files with matching IDs to the local machine to display
        return Collections.unmodifiableList(authoredGames);
    }

    /**
     * Stores the basic information and imageIDs pertaining to a created game to the database
     * @param savingInfo - GameInfo Object containing the basic info of the game being saved
     * @param usedImagesIDs - List of image IDs corresponding to the images being used in the game
     */
    public void storeBasicInfo(GameInfo savingInfo, List<String> usedImagesIDs){
        String title = savingInfo.getGameTitle();
        String description = savingInfo.getGameDescription();
        String gameThumbnailID = savingInfo.getGameThumbnail();

        // TODO: Save this information to the game library using game PrimaryKey or create a new one if value is null
    }

    /**
     * Converts game XML String to byte array to store in the database
     * @param gameXMLString - string produced by putting Game object through Xstream
     */
    public void storeXML(String gameXMLString){
        byte[] gameBytes = gameXMLString.getBytes();
        currentGameID = currentGameID;

        // TODO: Store byte[] in table of currentGameID
    }

    // TODO: Override Data todo get images method and call one below as well
    // TODO: Method call to download images with the standard flag selected


}

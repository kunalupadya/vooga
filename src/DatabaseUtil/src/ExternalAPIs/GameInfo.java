package ExternalAPIs;

import java.io.InputStream;

public class GameInfo {

    private int gameID;
    private int authorID;
    // TODO: Remove this variable
//    private String thumbnail;
    private int thumbnailID;
    private String title;
    private String description;
    private InputStream binary;

    public GameInfo(String title, int thumbnailID, String description, int gameId, int authorID,InputStream binary) {
        this(title,thumbnailID,description);
        this.gameID = gameId;
        this.authorID = authorID;
        this.binary = binary;
    }

    public GameInfo(String title, int thumbnailID, String description){
        this.title = title;
        this.description = description;
        this.thumbnailID = thumbnailID;
    }

//    // TODO: Remove this constructor
//    public GameInfo(String title, String thumbnail, String description){
//        this.title = title;
//        this.description = description;
//        this.thumbnail = thumbnail;
//    }

    public int getGameID() { return gameID; }

    public int getAuthorID() {
        return authorID;
    }

    public int getGameThumbnailID() { return thumbnailID; }

//    public String getGameThumbnail(){
//        return thumbnail;
//    }

    public String getGameTitle() {
        return title;
    }

    public String getGameDescription() {
        return description;
    }

    public byte[] getBinary(){
        try {
            return binary.readAllBytes();
        }
        catch (Exception e){
            return null;
        }
    }
}







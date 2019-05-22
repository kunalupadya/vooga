package Queries.DataQueries;

import ExternalAPIs.GameInfo;
import Queries.ConnectionException;

import javax.sql.rowset.serial.SerialBlob;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameData extends DBUtil {

    public int addGame(int authorID, String description, byte[] game, int thumbnailID, String title){
        String insertionQuery =
                " insert into games (authorID, gameFile, thumbnailID, description, title)"
                        + " values (?, ?, ?, ?, ?);";
        try {
            PreparedStatement statement = getConnection().prepareStatement(insertionQuery, RETURN_GENERATED_KEYS);
            statement.setInt(1, authorID);
            statement.setBlob(2, new SerialBlob(game));
            statement.setInt(3, thumbnailID);
            statement.setString(4, description);
            statement.setString(5, title);
            statement.executeUpdate();

            int ID = getGeneratedAutoIndex(statement);
            statement.close();
            return ID;
        }
        catch (SQLException e){
            closeConnection();
            throw new ConnectionException(e.toString());
        }
    }


    public ArrayList<Integer> getAllGameIDs(){
        String selectionQuery =
                "select gameID " +
                        "from games";
        try {
            PreparedStatement statement = getConnection().prepareStatement(selectionQuery);
            ResultSet results = statement.executeQuery();
            ArrayList<Integer> ids = new ArrayList<>();
            while (results.next()){
              ids.add(results.getInt("gameID"));
            }
            results.close();
            statement.close();
            return ids;
        }
        catch (SQLException e){
            closeConnection();
            throw new ConnectionException(e.toString());
        }

    }

    public GameInfo fetchGame(int gameID){
        String selectionQuery =
                "select * " +
                        "from games where gameID = (?)";
        try {
            PreparedStatement statement = getConnection().prepareStatement(selectionQuery);
            statement.setInt(1, gameID);
            ResultSet results = statement.executeQuery();
            GameInfo game = null;
            if(results.next()){
                InputStream binary = results.getBinaryStream("gameFile");
                int authorID = results.getInt("authorID");
                int imageID = results.getInt("thumbnailID");
                String description = results.getString("description");
                String title = results.getString("title");
                game = new GameInfo(title,imageID,description,gameID,authorID,binary);
            }
            results.close();
            statement.close();
            return game;
        }
        catch (SQLException e){
            closeConnection();
            throw new ConnectionException(e.toString());
        }
    }
}

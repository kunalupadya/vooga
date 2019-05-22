package Queries.DataQueries;

import ExternalAPIs.LeaderBoardEntry;
import Queries.ConnectionException;
import Queries.Results.GameSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SessionData extends DBUtil {

    /**
     * @param level the level achieved
     * @param score the
     * @param playerID The desired username
     * @return The userID of the new user if successful, -1 if not
     */
    public int addSession(int level, int score, int gameID, int playerID){
        String insertionQuery =
                " insert into gameSessions (level, score, gameID, playerID)"
                        + " values (?, ?, ?, ?);";
        try {
            PreparedStatement statement = getConnection().prepareStatement(insertionQuery, RETURN_GENERATED_KEYS);
            statement.setInt(1, level);
            statement.setInt(2, score);
            statement.setInt(3, gameID);
            statement.setInt(4, playerID);
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

    /**
     * @param userID The userID of the requested user
     * @return The GameSessions corresponding to the given user
     */
    public ArrayList<GameSession> getSessions(int userID){
        String selectionQuery = "select * from gameSessions where playerID = (?)";
        try {
            PreparedStatement statement = getConnection().prepareStatement(selectionQuery);
            statement.setInt(1, userID);
            ResultSet results = statement.executeQuery();
            ArrayList<GameSession> sessionsList = new ArrayList<>();
            while(results.next()){
                int sessionID = results.getInt("sessionID");
                int gameID = results.getInt("gameID");
                int level = results.getInt("level");
                int score = results.getInt("score");
                GameSession session = new GameSession(level, score, gameID, sessionID);
                sessionsList.add(session);
            }
            results.close();
            statement.close();
            return sessionsList;
        }
        catch (SQLException e){
            closeConnection();
            throw new ConnectionException(e.toString());
        }
    }

    /**
     * @param userID The userID of the requested user
     * @param gameID the gameID of the requested game
     * @return The most recent saved session for the user playing the given game
     */
    public GameSession getMostRecentSessionForGame(int userID, int gameID){
        String selectionQuery =
                "select * from gameSessions " +
                        "where playerID = (?) and gameID = (?)" +
                        " order by score DESC";
        try {
            PreparedStatement statement = getConnection().prepareStatement(selectionQuery);
            statement.setInt(1, userID);
            statement.setInt(2, gameID);

            ResultSet results = statement.executeQuery();
            GameSession session = null;
            if(results.next()){
                int sessionID = results.getInt("sessionID");
                int level = results.getInt("level");
                int score = results.getInt("score");
                session = new GameSession(level, score, gameID, sessionID);
            }
            results.close();
            statement.close();
            return session;
        }
        catch (SQLException e){
            closeConnection();
            throw new ConnectionException(e.toString());
        }
    }




    public ArrayList<LeaderBoardEntry> getHighScoresForGame(int gameID, int maxCount){
        String selectionQuery = " select * from gameSessions where gameID = (?) order by score DESC ";//  DESC limit (?);";
        try {
            PreparedStatement statement = getConnection().prepareStatement(selectionQuery);
            statement.setInt(1, gameID);
            ResultSet results = statement.executeQuery();
            ArrayList<LeaderBoardEntry> entriesList = new ArrayList<>();
            int rank = 1;
            while(results.next() && rank < maxCount+1){
                String username = new UserData().getUsername(results.getInt("playerID"));
                int level = results.getInt("level");
                int score = results.getInt("score");
                entriesList.add(new LeaderBoardEntry(rank, username, score, level));
                rank+=1;
            }
            results.close();
            statement.close();
            return entriesList;
        }
        catch (SQLException e){
            closeConnection();
            throw new ConnectionException(e.toString());
        }

    }
}

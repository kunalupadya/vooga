package Queries.DataQueries;

import Queries.ConnectionException;
import Queries.DataQueries.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class UserData extends DBUtil {

    /**
     * @param username The desired username
     * @param hashedPass The pre-hashed and salted password of the user
     * @param salt The salt used to generate the hashed password
     * @return The userID of the new user if successful, -1 if not
     */
    public int addUser(String username, String hashedPass, String salt)throws RuntimeException{
        String insertionQuery =
                " insert into users (username, password, salt)"
                        + " values (?, ?, ?);";
        try {
            PreparedStatement statement = getConnection().prepareStatement(insertionQuery, RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            statement.setString(2, hashedPass);
            statement.setString(3, salt);
            statement.executeUpdate();
            int ID = getGeneratedAutoIndex(statement);
            statement.close();
            return ID;
        }
        catch (SQLIntegrityConstraintViolationException e){
            throw new RuntimeException("This user is already being used, please choose another");
        }
        catch (SQLException e){
            closeConnection();
            throw new ConnectionException(e.toString());
        }
    }

    /**
     * @param username The username of the requested user
     * @param hashedPass The pre-hashed and salted password of the user
     * @return The userID of the new user if login successful, -1 if not
     */
    public int login(String username, String hashedPass){
        String selectionQuery =
                "select userID " +
                "from users " +
                "where username = (?) and password = (?)";
        try {
            PreparedStatement statement = getConnection().prepareStatement(selectionQuery);
            statement.setString(1, username);
            statement.setString(2, hashedPass);
            ResultSet results = statement.executeQuery();
            int userID = -1;
            if(results.next()){
                userID = results.getInt("userID");
            }
            results.close();
            statement.close();
            return userID;
        }
        catch (SQLException e){
            closeConnection();
            throw new ConnectionException(e.toString());
        }
    }

    /**
     * @param userID The userID of the requested user
     * @return The gameIDs corresponding to all the games the user has authored
     */
    public ArrayList<Integer> getAuthoredGames(int userID){
        String selectionQuery = "select gameID from games where authorID = (?)";
        try {
            PreparedStatement statement = getConnection().prepareStatement(selectionQuery);
            statement.setInt(1,userID);
            ResultSet results = statement.executeQuery();
            ArrayList<Integer> IDList = new ArrayList<>();
            while(results.next()){
                IDList.add(results.getInt("gameID"));
            }
            results.close();
            statement.close();
            return IDList;
        }
        catch (SQLException e){
            closeConnection();
            throw new ConnectionException(e.toString());
        }
    }

    public String getSalt(String username) throws ConnectionException{
        String selectionQuery = "select salt from users where username = (?)";
        try {
            PreparedStatement statement = getConnection().prepareStatement(selectionQuery);
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            String salt = null;
            if(results.next()){
                salt = results.getString("salt");
            }
            else{
                throw new ConnectionException("User "+username+" does not have salt");
            }
            results.close();
            statement.close();
            return salt;
        }
        catch (SQLException e){
            closeConnection();
            throw new ConnectionException(e.toString());
        }
    }

    public String getUsername(int userID){
        String selectionQuery = "select username from users where userID = (?)";
        try {
            PreparedStatement statement = getConnection().prepareStatement(selectionQuery);
            statement.setInt(1, userID);
            ResultSet results = statement.executeQuery();
            String username = null;
            if(results.next()){
                username = results.getString("username");
            }
            else{
                throw new ConnectionException("Could not find username");
            }
            results.close();
            statement.close();
            return username;
        }
        catch (SQLException e){
            closeConnection();
            throw new ConnectionException(e.toString());
        }
    }
}

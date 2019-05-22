package Queries.DataQueries;

import Queries.ConnectionException;

import java.sql.*;

public abstract class DBUtil {

    private Connection connection;
    protected final int RETURN_GENERATED_KEYS = Statement.RETURN_GENERATED_KEYS;

    public DBUtil(){
        final String user = "team";
        final String pass = "pass";
        final String db = "VOOGA";
        final String host = "10.197.131.76";
        //final String host = "localhost";
        String url = String.format("jdbc:mysql://%s/%s?user=%s&password=%s",host,db,user,pass);
        try {
            connection = DriverManager.getConnection(url);
        }
        catch (SQLException e){
            throw new ConnectionException(e.toString());
        }
    }

    protected Connection getConnection(){
        return connection;
    }

    protected int getGeneratedAutoIndex(Statement statement){
        try {
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int auto_id = rs.getInt(1);
            return auto_id;
        }
        catch (SQLException e){
            throw new ConnectionException("Could not find generated key\n"+e.toString());
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        }
        catch (Exception E){
        }
    }
}


package Queries.Results;

import ExternalAPIs.UserState;

public class GameSession {

    private int currentLevel;
    private int currentScore;
    private int gameID;
    private int sessionID;

    public GameSession(int currentLevel, int currentScore, int gameID, int sessionID){
        this.currentLevel = currentLevel;
        this.currentScore = currentScore;
        this.gameID = gameID;
        this.sessionID = sessionID;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getGameID(){
        return gameID;
    }

    public int getSessionID(){
        return sessionID;
    }

    public UserState getState(){return new UserState(currentLevel, currentScore);}

    @Override
    public String toString(){
        return String.format("Level: %s, Score: %s",currentLevel, currentScore);
    }
}

package ExternalAPIs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerData extends Data{

    public PlayerData(){
        super();
    }

    /**
     * Returns a list of all the game that have been created and are stored in the database
     * @return - List of authored games, Null if no games have been authored yet
     */
    @Override
    public List<GameInfo> getAuthoredGames() {
            List<GameInfo> gameInfos = new ArrayList<>();
            getGameData().getAllGameIDs().stream().forEach(
                    (id) -> {gameInfos.add(getGameData().fetchGame(id));}
            );
            return gameInfos;
    }

    /**
     * Uses the user ID and current game to query the database for the level and score where the user left off
     * @return - User state object corresponding to current user and game
     */
    public UserState getCurrentUserState(){
        System.out.println(currentUserID);
        System.out.println(currentGameID);
        return getSessionData().getMostRecentSessionForGame(currentUserID, currentGameID).getState();
    }

    /**
     * Stores the current level and score of the user when they exit the game
     * @param savingState - User state object corresponding to current user and game
     */
    public void saveUserState(UserState savingState){
        int currentLevel = savingState.getMyCurrentLevel();
        int currentScore = savingState.getMyCurrentScore();

        getSessionData().addSession(currentLevel,currentScore,currentGameID,currentUserID);

    }

    /**
     * Retrieves the specified number of leaders from the database for a particular game and passes their information in
     * a list of LeaderBoardEntry Objects
     * @param numberOfEntries - number of leaders to retrieve
     * @return - unmodifiable list of LeaderBoardEntry objects
     */
    public List<LeaderBoardEntry> compileLeaderboardEntries(int numberOfEntries){
        return getSessionData().getHighScoresForGame(currentGameID,numberOfEntries);
    }


}

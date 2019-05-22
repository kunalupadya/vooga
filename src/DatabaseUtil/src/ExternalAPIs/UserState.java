package ExternalAPIs;

public class UserState {

    private int myCurrentLevel;
    private int myCurrentScore;

    public UserState(int currentLevelIndex, int currentScore){
        myCurrentLevel = currentLevelIndex;
        myCurrentScore = currentScore;
    }

    public int getMyCurrentLevel() {
        return myCurrentLevel;
    }

    public int getMyCurrentScore() {
        return myCurrentScore;
    }
}

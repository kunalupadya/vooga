package ExternalAPIs;

public class LeaderBoardEntry {

    private int myRank;
    private String myUserName;
    private int myScore;
    private int level;

    public LeaderBoardEntry(int rank, String userName, int score, int level){
        myRank = rank;
        myUserName = userName;
        myScore = score;
        this.level = level;
    }

    public int getLevel(){return level;}

    public int getRank() {
        return myRank;
    }

    public String getUserName() {
        return myUserName;
    }

    public int getScore() {
        return myScore;
    }

    public String toString(){
        return String.format("Level: %s, Score: %s", level, myScore);
    }
}

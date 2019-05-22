package Configs.GamePackage;

public enum GameStatus {
    OVER(0), GAMELOST(1), GAMEWON(2),PLAYING(3), LEVELOVER(4);

    private final int myState;

    GameStatus(int state) {
        myState=state;
    }

    public int getMyState() {
        return myState;
    }
}

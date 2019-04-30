package Configs;

/**
 * An enum that marks different objects and what their next state is for the gameplayer to sucessfully execute removal/addition
 */
public enum DisplayState {
    NEW(0),
    PRESENT(1),
    DIED(2);
    private final int myState;

    DisplayState(int state) {
    myState=state;
    }

    public int getMyState() {
        return myState;
    }
}

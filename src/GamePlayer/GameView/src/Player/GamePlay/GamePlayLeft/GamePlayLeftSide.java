package Player.GamePlay.GamePlayLeft;

import BackendExternal.Logic;
import Player.GamePlay.ButtonInterface;
import Player.GamePlay.EndLoopInterface;
import Player.GamePlay.SelectionInterface;
import javafx.scene.layout.VBox;

public class GamePlayLeftSide extends VBox {
    public static final double TOP_RATIO = 0.8;
    private static final double BOTTOM_RATIO = 0.2;
    public GamePlayMap myMap;
    public GamePlaySettingsBar mySettings;


    public GamePlayLeftSide(double width, double height, Logic logic, EndLoopInterface endLoop,
                            SelectionInterface home, ButtonInterface resetTime){
        setPrefWidth(width);
        setPrefHeight(height);
        mySettings = new GamePlaySettingsBar(width,height * BOTTOM_RATIO, logic);
        myMap = new GamePlayMap(width,height * TOP_RATIO, logic, endLoop, home, resetTime, mySettings);
        getChildren().addAll(myMap, mySettings);
    }

    /**
     * @return Returns the map of the Game. This is used to check valid placements of the weapons on the Map.
     */
    public GamePlayMap getMap(){
        return myMap;
    }

}

package Player.GamePlay.GamePlayLeft;

import BackendExternal.Logic;
import Player.GamePlay.GamePlayLeft.GamePlayMap;
import Player.GamePlay.GamePlayRight.GamePlaySettingsBar;
import javafx.scene.layout.VBox;

public class GamePlayLeftSide extends VBox {
    public GamePlayMap myMap;
    public GamePlaySettingsBar mySettings;

    public GamePlayLeftSide(double width, double height, Logic logic){
        setPrefWidth(width);
        setPrefHeight(height);
        myMap = new GamePlayMap(width,height * 8 / 10, logic);
        mySettings = new GamePlaySettingsBar(width,height * 2/ 10);
        getChildren().addAll(myMap, mySettings);
    }

    public GamePlayMap getMap(){
        return myMap;
    }
}

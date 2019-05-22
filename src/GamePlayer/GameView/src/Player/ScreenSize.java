package Player;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public final class ScreenSize {
    public static double getWidth(){
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        return primScreenBounds.getWidth();
    }
    public static double getHeight(){
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        return primScreenBounds.getHeight();
    }
}

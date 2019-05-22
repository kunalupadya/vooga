package GameAuthoringEnvironment.AuthoringScreen;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class Screen {

    private final VBox module;
    private Pane content, toolbarPane;
    private int moduleWidth;
    private int moduleHeight;
    private final double toolbarHeight = 5.0;
    private Group myRoot;
    private boolean resizable = false;

    public Screen(int width, int height) {

        module = new VBox();
        content = new Pane();
        moduleWidth = width;
        moduleHeight = height;
        //module.setStyle("-fx-border-color: black;");

        module.setMinSize(ScreenSize.getWidth(), moduleHeight);
        module.setMaxSize(ScreenSize.getWidth(), moduleHeight);
        //module.setId("module");
        content.prefHeightProperty().bind(module.heightProperty());
        content.prefWidthProperty().bind(module.widthProperty());


        module.getChildren().addAll(content);
    }

    //TODO implement to make screen resizable
    private void setResizable(boolean isResizable){

    }


    //public abstract void setContent();

    private String colorToHex(Paint color) {
        String hex1;
        String hex2;

        hex1 = Integer.toHexString(color.hashCode()).toUpperCase();

        switch (hex1.length()) {
            case 2:
                hex2 = "000000";
                break;
            case 3:
                hex2 = String.format("00000%s", hex1.substring(0,1));
                break;
            case 4:
                hex2 = String.format("0000%s", hex1.substring(0,2));
                break;
            case 5:
                hex2 = String.format("000%s", hex1.substring(0,3));
                break;
            case 6:
                hex2 = String.format("00%s", hex1.substring(0,4));
                break;
            case 7:
                hex2 = String.format("0%s", hex1.substring(0,5));
                break;
            default:
                hex2 = hex1.substring(0, 6);
        }
        return hex2;
    }

    public void setContentColor(Paint color) {
        String hexColor = colorToHex(color);
        content.setStyle("-fx-background-color: #" + hexColor);
    }

    public void setBackGroundColor(String style){
        module.setStyle(style);
    }

    public VBox getModule() {
        return module;
    }

    public Pane getContent(){return content;}

    protected int getModuleWidth() {
        return moduleWidth;
    }

    protected int getModuleHeight() { return moduleHeight; }

    public void setLayout(int xCor, int yCor){
        module.setLayoutX(xCor);
        module.setLayoutY(yCor);
    }


}

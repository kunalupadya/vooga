package GameAuthoringEnvironment.AuthoringComponents;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PrimitiveContainer {

    Label DISPLAY_LABEL;
    TextField myTextField;
    Button confirmButton;

    public PrimitiveContainer(String key, VBox layout){
        DISPLAY_LABEL = new Label(key);
        myTextField = new TextField();
        confirmButton = new Button("Confirm");
        layout.getChildren().addAll(DISPLAY_LABEL, myTextField, confirmButton);
    }

    public String getUserInput(){
        return myTextField.getText();
    }

}

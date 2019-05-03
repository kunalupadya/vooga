package GameAuthoringEnvironment.AuthoringScreen;

import Configs.Configurable;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class CompleteButton {
    private Configurable myConfigurable;
    private Stage popupwindow;
    private List<Button> allButton;
    private Map<String, Object> myAttributesMap;

    public CompleteButton(Configurable myConfigurable, Stage popupwindow, List<Button> allButton, Map<String, Object> myAttributesMap) {
        this.myConfigurable = myConfigurable;
        this.popupwindow = popupwindow;
        this.allButton = allButton;
        this.myAttributesMap = myAttributesMap;
    }

    public Button invoke() {
        Button setButton = new Button("Configuration Is Completed");
        setButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TODO(Hyunjae) Should tell the user what attribute is missing
                if(!myConfigurable.getConfiguration().isConfigurationComplete()){
                    AlertFactory af = new AlertFactory();
                    af.createAlert("Configuration Not Complete!");
                }
                else {
                    for (Button button : allButton) {
                        button.fireEvent(event);
                    }
                    myConfigurable.getConfiguration().setAllAttributes(myAttributesMap);
                    popupwindow.close();
                }

            }
        }));
        return setButton;
    }
}
package GameAuthoringEnvironment.AuthoringScreen;

import javafx.scene.control.Alert;

public class AlertFactory {
    public final static String ALERT_TITLE = "Information Dialog";
    public final static String ALERT_HEADER_TEXT = "Error during Configuration";
    public AlertFactory(){}

    public void createAlert(String message){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ALERT_TITLE);
            alert.setHeaderText(ALERT_HEADER_TEXT);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }



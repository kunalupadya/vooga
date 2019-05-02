package GameAuthoringEnvironment.AuthoringScreen;

import BackendExternalAPI.Model;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateAccountScreen extends Application {

    private VBox layout;
    private TextField newIDTf, pwTf, confirmPwTf;
    private Button makeAccountButton;
    private Stage popupwindow;
    private Model myModel;

    public CreateAccountScreen(StartingScreen startingScreen, Model model){
        super();
        myModel = model;
        layout = new VBox(10.00);
        layout.setMaxWidth(250);
        layout.setId("backdrop");
        layout.setAlignment(Pos.CENTER);
        setContent();
    }
    @Override
    public void start(Stage stage){
        popupwindow = new Stage();
        Scene scene= new Scene(layout, 500, 500);
        scene.getStylesheets().add("authoring_style.css");
        popupwindow.setScene(scene);
        popupwindow.show();
    }

    private void setContent(){
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        Text text = new Text("Create Your New Account!");
        text.setFont(Font.font("Veranda", FontWeight.BOLD, 20));
        newIDTf = new TextField();
        newIDTf.setPromptText("Enter ID");
        newIDTf.setMaxWidth(250);
        pwTf = new TextField();
        pwTf.setPromptText("Enter Password");
        pwTf.setMaxWidth(250);
        confirmPwTf = new TextField();
        confirmPwTf.setMaxWidth(250);
        confirmPwTf.setPromptText("Re-enter Password");
        makeAccountButton = new Button("Create Account");
        makeAccountButton.setOnMouseClicked(this::handleMakeAccountButton);
        layout.getChildren().addAll(text, newIDTf, pwTf, confirmPwTf, makeAccountButton);
        newIDTf.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                layout.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
    }

    private void handleMakeAccountButton(MouseEvent event){
        try {
            if (pwTf.getText().equals(confirmPwTf.getText())) {
                //TODO USE regex to check the id, pw etc
                myModel.createNewUser(newIDTf.getText(), pwTf.getText(), confirmPwTf.getText());
                popupwindow.close();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING,"Creating User Failed");
                alert.showAndWait();
            }
        }
        catch(Exception e){
            Alert alert= new Alert(Alert.AlertType.WARNING,e.getMessage());
            alert.showAndWait();
        }
//        else{
//            //TODO Create Alert
//        }
    }

}

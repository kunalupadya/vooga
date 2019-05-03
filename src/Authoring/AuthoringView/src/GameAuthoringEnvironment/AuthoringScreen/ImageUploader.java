package GameAuthoringEnvironment.AuthoringScreen;

import BackendExternalAPI.AuthoringBackend;
import ExternalAPIs.AuthoringData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ImageUploader {

    private static Group myRoot;

    private VBox myVBox;
    private HBox fileSelectionHBox;
    private HBox typeSelectionHBox;
    private FileChooser myFileChooser;
    private File myImageFile;
    private AuthoringData.ImageType myImageType;
    private Stage popUpWindow;
    public static final String DEFAULT_FILE_TEXT = "Select a File to Upload";
    public static final String DEFAULT_TYPE_TEXT = "Select an Image Type";
    public static final String FILE_BUTTON_TXT = "Select File";
    public static final String SAVE_BUTTON_TXT = "Save Image File";
    public static final String DROPDOWN_TXT = "Type";
    private AuthoringBackend myAuthoringBackend;

    private static int imageID;

    private TextField fileTextBox;

    public ImageUploader(AuthoringBackend authoringBackend){
        myAuthoringBackend = authoringBackend;
        setContent();
    }

    private void setContent(){
        popUpWindow = new Stage();
        popUpWindow.setTitle("Image Editor");
        myRoot = new Group();
        myVBox = new VBox();
        fileSelectionHBox = new HBox();
        typeSelectionHBox = new HBox();
        myVBox.setSpacing(50);
        fileSelectionHBox.setSpacing(50);
        typeSelectionHBox.setSpacing(50);
        myVBox.getChildren().add(fileSelectionHBox);
        myVBox.getChildren().add(typeSelectionHBox);
        myRoot.getChildren().add(myVBox);

        myFileChooser = new FileChooser();
        Scene scene= new Scene(myRoot, 800, 800);
        popUpWindow.setScene(scene);
        popUpWindow.show();


        startFileChooser();
        createImageTypeMenu();
        createSaveButton();
    }

    private void startFileChooser(){
        fileTextBox = new TextField(DEFAULT_FILE_TEXT);
        Button fileButton = makeButton(FILE_BUTTON_TXT, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                myImageFile = myFileChooser.showOpenDialog(popUpWindow);

                String fileName;
                if (myImageFile.toString().indexOf('/') != -1){
                    fileName = myImageFile.toString().substring(myImageFile.toString().lastIndexOf('/') + 1);
                }
                else {
                    fileName = myImageFile.toString().substring(myImageFile.toString().lastIndexOf('\\') + 1);
                }
                fileTextBox.setText(fileName);
            }
        });
        fileSelectionHBox.getChildren().add(fileTextBox);
        fileSelectionHBox.getChildren().add(fileButton);
    }

    private void createImageTypeMenu(){
        TextField typeTextBox = new TextField(DEFAULT_TYPE_TEXT);
        MenuButton menuButton = new MenuButton(DROPDOWN_TXT);

        for (int i = 0; i < AuthoringData.ImageType.values().length; i++){
            MenuItem mi = new MenuItem(AuthoringData.ImageType.values()[i].name());
            mi.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String selectedTypeString = mi.getText();
                    typeTextBox.setText(selectedTypeString);
                    myImageType = AuthoringData.ImageType.valueOf(selectedTypeString);
                }
            });
            menuButton.getItems().add(mi);

        }
        typeSelectionHBox.getChildren().add(typeTextBox);
        typeSelectionHBox.getChildren().add(menuButton);
    }

    private void createSaveButton(){
        Button saveButton = makeButton(SAVE_BUTTON_TXT, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                testStoreImage();
            }
        });
        myVBox.getChildren().add(saveButton);
    }

    private void testStoreImage(){
        try {
            imageID = myAuthoringBackend.uploadImage(myImageFile,myImageType);
        } catch (Exception e) {
            fileTextBox.setText(e.getMessage());
        }
    }

    private static Button makeButton(String buttonString, EventHandler<ActionEvent> handler){
        var newButton = new Button(buttonString);
        newButton.setOnAction(handler);
        return newButton;
    }
}

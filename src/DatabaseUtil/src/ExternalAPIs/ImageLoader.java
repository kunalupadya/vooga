package ExternalAPIs;

import javafx.application.Application;
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
import java.io.IOException;

/**
 * Project 4: VoogaSalad
 * Duke CompSci 308 Spring 2019 - Duvall
 * Date Created: 4/4/2019
 * Date Last Modified: 5/2/2019
 * @author Brian Jordan
 */

/**
 * This class provides a user interface for easily uploading image files to the database.  The Application depends on
 * the AuthoringData class to interact with the DatabaseUtil module.  Files greater than 16 MBytes are not accepted
 * and an error message is presented to the user.  The UploadImage class uses this code to perform the same function
 * within the Game Authoring Environment.  This provides the ability to upload images independently from the rest of the
 * program.
 */

public class ImageLoader extends Application {

    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 300;
    public static final int WINDOW_SPACING = 50;
    public static final String TITLE = "Image Uploader";
    public static final String DEFAULT_FILE_TEXT = "Select a File to Upload";
    public static final String DEFAULT_TYPE_TEXT = "Select an Image Type";
    public static final String FILE_BUTTON_TXT = "Select File";
    public static final String SAVE_BUTTON_TXT = "Save Image File";
    public static final String DROPDOWN_TXT = "Type";

    private final double MAX_FILE_SIZE = 16 * Math.pow(10,6);
    private static AuthoringData myAuthoringData;
    private static Stage myStage;
    private static Group myRoot;
    private VBox myVBox;
    private HBox fileSelectionHBox;
    private HBox typeSelectionHBox;
    private FileChooser myFileChooser;
    private File myImageFile;
    private AuthoringData.ImageType myImageType;

    public static void main (String[] args){
        launch(args);
    }

    /**
     * Starts the application
     * @param stage - Stage object for application
     */
    @Override
    public void start(Stage stage){
        myAuthoringData = new AuthoringData();
        myFileChooser = new FileChooser();

        myStage = stage;
        setWindowLayout();
        Scene myScene = new Scene(myRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
        myStage.setScene(myScene);
        myStage.setTitle(TITLE);
        myStage.show();

        startFileChooser();
        createImageTypeMenu();
        createSaveButton();

    }

    private void setWindowLayout(){
        myRoot = new Group();
        myVBox = new VBox();
        fileSelectionHBox = new HBox();
        typeSelectionHBox = new HBox();
        myVBox.setSpacing(WINDOW_SPACING);
        fileSelectionHBox.setSpacing(WINDOW_SPACING);
        typeSelectionHBox.setSpacing(WINDOW_SPACING);
        myVBox.getChildren().add(fileSelectionHBox);
        myVBox.getChildren().add(typeSelectionHBox);
        myRoot.getChildren().add(myVBox);
    }

    private void startFileChooser(){
        TextField fileTextBox = new TextField(DEFAULT_FILE_TEXT);
        Button fileButton = makeButton(FILE_BUTTON_TXT, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File newFile = myFileChooser.showOpenDialog(myStage);
                int fileSize = (int) newFile.length();
                try{
                    checkFileSize(fileSize);
                }
                catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    fileTextBox.setText(e.getMessage());
                    return;
                }
                myImageFile = newFile;
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
            myAuthoringData.uploadImage(myImageFile,myImageType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFileSize(int size){
        if (size > MAX_FILE_SIZE){
            throw new IllegalArgumentException("File too Large > 16MB");
        }
    }
    private static Button makeButton(String buttonString, EventHandler<ActionEvent> handler){
        var newButton = new Button(buttonString);
        newButton.setOnAction(handler);
        return newButton;
    }
}

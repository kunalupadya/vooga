package GameAuthoringEnvironment.AuthoringScreen;

import BackendExternalAPI.Model;
import Configs.GamePackage.Game;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class StartingScreen {

    private VBox myContatiner;
    private Stage myStage;
    private TextField idTf;
    private PasswordField pwTf;
    private Button newGameButton, importGameButton;
    private Text loginDescription;
    private int padding = 20;
    private double width = ScreenSize.getWidth()/3 ;
    private double height = ScreenSize.getHeight() * 0.75;
    private Button loginButton;
    private Button createIDButton;
    private Model myModel;

    public void start (Stage stage) {
        myStage = stage;
        setScene();
        setStage();
    }
    private void setScene(){

        myModel = new Model();
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        myContatiner = new VBox();
        myContatiner.setSpacing(padding);
        myContatiner.setId("backdrop");
        myContatiner.setAlignment(Pos.CENTER);
        Image test = new Image(getClass().getResourceAsStream("/resources/" + "logo" +".png"));
        ImageView imageView = new ImageView(test);
        idTf = new TextField();
        idTf.setPromptText("Enter ID");
        idTf.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                myContatiner.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
        idTf.setPrefColumnCount(10);
        idTf.setMaxWidth(width/2);
        pwTf = new PasswordField();
        pwTf.setPrefColumnCount(10);
        pwTf.setMaxWidth(width/2);
        pwTf.setPromptText("Enter Password");
        loginButton = new Button("Login");
        loginButton.setOnMouseClicked(this::handleLogin);
        createIDButton = new Button("Create Account");
        createIDButton.setOnMouseClicked(this::handleCreateAccount);
        createIDButton.setTranslateX(createIDButton.getWidth());
        newGameButton = new Button("Create New Game");
        newGameButton.setOnMouseClicked(this:: handleNewGameButton);
        importGameButton = new Button("Import Game");
        importGameButton.setOnMouseClicked(this::importGame);

        loginDescription = new Text("Welcome to NoMergeConflicts. First login or create your new account");
        loginDescription.setStyle("-fx-font-size: 10");
        myContatiner.getChildren().addAll(imageView, idTf, pwTf, loginButton, createIDButton, loginDescription);
        Scene scene= new Scene(myContatiner, width, height);
        scene.getStylesheets().add("authoring_style.css");
        myStage.setScene(scene);
    }

    private void handleCreateAccount(MouseEvent event){
        //TODO Complete this part
        CreateAccount createAccount = new CreateAccount(this, myModel);
        createAccount.start(new Stage());
    }

    private void handleLogin(MouseEvent event){
        //TODO Call APIs to confirm password
        if(myModel.authenticateUser(idTf.getText(), pwTf.getText())){
        Text instructions = new Text("Now Click New Game to Make a New Game or Click Import Game to Import a Existing Game");
        instructions.setStyle("-fx-font-size: 10");
        myContatiner.getChildren().removeAll(idTf, pwTf, loginButton, createIDButton, loginDescription);
        myContatiner.getChildren().addAll(newGameButton, importGameButton, instructions);
         }
        else{
            //TODO Alert error
        }
    }

    private void makeGame(Game game){
        AuthoringVisualization authoringVisualization = new AuthoringVisualization(game);
        authoringVisualization.start(new Stage());
        myStage.close();
    }

    private void handleNewGameButton(MouseEvent event){
        Game newGame = new Game();
        makeGame(newGame);
    }

    private void importGame(MouseEvent evemt){

       /* FileChooser fileChooser = new FileChooser();

        File selectedFile = fileChooser.showOpenDialog(myStage);
        if (selectedFile != null) {

            String filepath = selectedFile.toString();
            // TODO(Louis) game should be created by reading in the xml
            /Game importedGame = new Game();
            importedGame = new Model(filepath);

            if (!filepath.endsWith("XML")) {
                //TODO(Hyunjae) alert!
            }
        }*/
        makeGame(new Game());
    }

    private void setStage(){
        //set Stage boundaries to visible bounds of the main screen
        myStage.setX(ScreenSize.getWidth()/2 - width/2);
        myStage.setY(ScreenSize.getHeight()/2);
        myStage.setTitle("Welcome to NoMergeConflicts");
        myStage.show();
    }

}

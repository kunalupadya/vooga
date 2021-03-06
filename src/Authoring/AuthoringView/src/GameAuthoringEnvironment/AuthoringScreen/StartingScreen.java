package GameAuthoringEnvironment.AuthoringScreen;

import BackendExternalAPI.AuthoringBackend;
import Configs.GamePackage.Game;
import GameAuthoringEnvironment.CloseStage;
import GameAuthoringEnvironment.ImportGame;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private AuthoringBackend myAuthoringBackend;

    public void start (Stage stage) {
        myStage = stage;
        setScene();
        setStage();
    }
    private void setScene(){

        myAuthoringBackend = new AuthoringBackend();
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
        newGameButton = new Button("Explore");
        newGameButton.setOnMouseClicked(this:: handleNewGameButton);
//        importGameButton = new Button("Import Game");
//        importGameButton.setOnMouseClicked(this::importGame);

        loginDescription = new Text("Welcome to NoMergeConflicts. First login or create your new account");
        loginDescription.setStyle("-fx-font-size: 10");
        myContatiner.getChildren().addAll(imageView, idTf, pwTf, loginButton, createIDButton, loginDescription);
        Scene scene= new Scene(myContatiner, width, height);
        scene.getStylesheets().add("authoring_style.css");
        myStage.setScene(scene);
    }

    private void handleCreateAccount(MouseEvent event){
        //TODO Complete this part
        CreateAccountScreen createAccountScreen = new CreateAccountScreen(this, myAuthoringBackend);
        createAccountScreen.start(new Stage());
    }

    private void handleLogin(MouseEvent event){
        //TODO Call APIs to confirm password
        try {
            if (myAuthoringBackend.authenticateUser(idTf.getText(), pwTf.getText())) {
                Text instructions = new Text("Login Successful! Click above to create a new game or to load in an existing game.");
                instructions.setStyle("-fx-font-size: 10");
                myContatiner.getChildren().removeAll(idTf, pwTf, loginButton, createIDButton, loginDescription);
                myContatiner.getChildren().addAll(newGameButton, instructions);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING,"Wrong Username or Password");
                alert.showAndWait();
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING,e.getMessage());
            alert.showAndWait();
        }
//        else{
//            //TODO Alert error
//        }
    }

    private void makeGame(Game game){
        AuthoringVisualization authoringVisualization = new AuthoringVisualization(game, myAuthoringBackend);
        authoringVisualization.start(new Stage());
        myStage.close();
    }

    private void handleNewGameButton(MouseEvent event){
        Game newGame = new Game();
        makeGame(newGame);
    }

//    private void importGame(MouseEvent evemt){
//        ImportGame importGame = new ImportGame(myAuthoringBackend.getAuthoredGameLibrary(), myAuthoringBackend);
//        importGame.start(new Stage());
//        CloseStage eventHandler = () -> startGame();
//        importGame.setEventHandler(eventHandler);
//    }
    private void startGame(){
        this.myStage.close();
    }

    private void setStage(){
        //set Stage boundaries to visible bounds of the main screen
        myStage.setX(ScreenSize.getWidth()/2 - width/2);
        myStage.setY(ScreenSize.getHeight()/2);
        myStage.setTitle("Welcome to NoMergeConflicts");
        myStage.show();
    }

}

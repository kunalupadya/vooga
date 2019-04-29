package Player;

import BackendExternal.Logic;
import Player.SetUp.GameSelection;
import Player.SetUp.LogInPreloader;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;


public class LoadingSplashScreen extends Application{

    private final String WELCOME_MUSIC = "resources/gameMusic.mp3";
    private final String TITLE_IMAGE = "title.png";
    private StackPane root;
    private ImageView title;
    private Stage stage;
    private MediaPlayer mediaPlayer;
    private StackPane background;
    private Rectangle rect;
    private VBox accountGrid;
    private LogInGrid logInGrid;
    private CreateAccount createAccountGrid;
    private Logic logic;
    private Text errorMessage;
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        logic = new Logic(ScreenSize.getWidth(), ScreenSize.getHeight());
        background = new StackPane();
        root = new StackPane();
        background.getChildren().add(root);
        root.setId("pane");
        root.applyCss();
        root.layout();
        stage.setX(ScreenSize.getWidth());
        stage.setY(ScreenSize.getHeight());
        var scene = new Scene(background, ScreenSize.getWidth(), ScreenSize.getHeight());
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.getChildren().add(createWelcomeMusic());
        root.getChildren().add(createLogoBackground());
        Button start = createStartButton("shiny-yelow", "Start", 0, 150);
        start.setOnAction(e -> transitionToLogIn(start));
        root.getChildren().add(start);
    }

    private MediaView createWelcomeMusic(){
        Media sound = new Media(new File(WELCOME_MUSIC).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        return new MediaView(mediaPlayer);
    }

    private ImageView createLogoBackground(){
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(TITLE_IMAGE));
        title = new ImageView(image);
        title.setFitWidth(500);
        title.setFitHeight(300);
        title.setTranslateY(-50);
        return title;
    }
    private Button createStartButton(String id, String title, int x, int y){
        Button button = new Button(title);
        button.setId(id);
        button.setTranslateY(y);
        button.setTranslateX(x);
        return button;
    }

    private void transitionToLogIn(Button button){
        root.getChildren().remove(button);
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.8), title);
        st.setByX(-0.6f);
        st.setByY(-0.6f);
        st.setCycleCount(1);
        int xPos = (int)Math.round(title.getX() + title.getBoundsInLocal().getWidth()/2);
        PathTransition bannerTransition = generatePathTransition(generatePath(xPos, 0, -100), title);
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(st,bannerTransition);
        parallelTransition.play();
        parallelTransition.setOnFinished(e-> createSignInSettings(false));
    }
    private void createSignInSettings(boolean repeat){
        if(!repeat) {
            root.setStyle("-fx-opacity: 0.6; -fx-background-color: black;");
            rect = new Rectangle();
            rect.setArcWidth(20);
            rect.setArcHeight(20);
            rect.getStyleClass().add("my-rect");
            background.getChildren().add(rect);
        }else{
            background.getChildren().remove(accountGrid);
        }
        rect.setWidth(400);
        rect.setHeight(200);

        VBox logInScreen = userLogIn();
        logInScreen.setMaxWidth(rect.getWidth());
        logInScreen.setMaxHeight(rect.getHeight());
        logInScreen.setLayoutY(rect.getY());
        background.getChildren().add(logInScreen);
    }
    private VBox userLogIn(){
        VBox vBox = new VBox();
        logInGrid = new LogInGrid();
        HBox hBox = addloginButtonPanel(vBox);
        vBox.getChildren().addAll(logInGrid, hBox);
        return vBox;
    }
    private HBox addloginButtonPanel(VBox vbox){
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        Button login = new Button("Log In");
        login.setId("green");
        login.setOnAction(e-> startGame(vbox));
        Button newAccount = new Button("Sign Up");
        newAccount.setId("green");
        newAccount.setOnAction(e->switchToCreateAccountGrid(vbox));
        hBox.getChildren().addAll(login,newAccount);
        hBox.setMaxWidth(rect.getWidth());
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }
    private void switchToCreateAccountGrid(VBox vbox){
        background.getChildren().remove(vbox);
        rect.setWidth(400);
        rect.setHeight(250);
        createAccountGrid = new CreateAccount();
        Image back = new Image(this.getClass().getClassLoader().getResourceAsStream("back.png"));
        ImageView backToLogIn = new ImageView(back);
        backToLogIn.setFitWidth(30);
        backToLogIn.setFitHeight(30);
        backToLogIn.setOnMousePressed(e->createSignInSettings(true));
        createAccountGrid.add(backToLogIn, 0,0);

        Button createAccount = new Button("Create Account");
        createAccount.setOnAction(e-> createAccount());
        createAccount.setId("createAccount");
        createAccountGrid.setAlignment(Pos.CENTER);

        accountGrid = new VBox(createAccountGrid, createAccount);
        accountGrid.setAlignment(Pos.CENTER);
        background.getChildren().add(accountGrid);
    }
    private Path generatePath(int x, int y, int newY)
    {
        Path path = new Path();
        path.getElements().add(new MoveTo(x , y));
        path.getElements().add(new LineTo(x, newY));
        return path;
    }

    private PathTransition generatePathTransition(Path path, Node node)
    {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(0.8));
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);
        pathTransition.setNode(node);
        return pathTransition;
    }
    private void availableGames(){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(mediaPlayer.volumeProperty(), 0)));
        timeline.play();
        this.stage.close();
        LogInPreloader logInPreloader = new LogInPreloader();
        logInPreloader.start(new Stage());
        logInPreloader.setTitle("We're loading your \n available games!!!");
        logInPreloader.setTransitionEvent(e -> {
            GameSelection gameSelection = new GameSelection(logic);
            gameSelection.start(new Stage());
        });
    }
    private void startGame(VBox vBox){
<<<<<<< HEAD
        if (logic.authenticateUser(logInGrid.getUserName(), logInGrid.getPassword())) {
            availableGames();
        }else{
            if(!vBox.getChildren().contains(errorMessage)){
                errorMessage = new Text("*User does not exist");
                errorMessage.setFill(Color.RED);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().add(errorMessage);
            }
=======
        try {
            if (logic.authenticateUser(logInGrid.getUserName(), logInGrid.getPassword())) {
                availableGames();
            } else {
                Text text = new Text("*User does not exist");
                text.setFill(Color.RED);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().add(text);
            }
        } catch (Exception e){
            Text text = new Text(e.getMessage());
            text.setFill(Color.RED);
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(text);
>>>>>>> 5fed2b6bd0ef4c6b080156698ef8a75e6b4b6cb5
        }
    }
    private void createAccount(){
        try {
            createAccountGrid.getUserName();
            createAccountGrid.getPasswordField();
            createAccountGrid.getPasswordCheck();
            if (!createAccountGrid.getPasswordField().equals(createAccountGrid.getPasswordCheck())) {
                Text text = new Text("*Passwords do not match");
                text.setFill(Color.RED);
                accountGrid.getChildren().add(text);
            }
            logic.createNewUser(createAccountGrid.getUserName(), createAccountGrid.getPasswordField(), createAccountGrid.getPasswordCheck());
        } catch (Exception e){
            Text text = new Text(e.getMessage());
            text.setFill(Color.RED);
            accountGrid.getChildren().add(text);
        }

    }
    public static void main(String [] args){
        Application.launch(args);}
}

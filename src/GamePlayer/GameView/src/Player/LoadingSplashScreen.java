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

    private static final String WELCOME_MUSIC = "resources/gameMusic.mp3";
    private static final String TITLE_IMAGE = "title.png";
    private static final String CSS_FILE = "style.css";
    private static final String START_BUTTON_TITLE = "Start";
    private static final String USER_ERROR = "*Passwords do not match";
    private static final String PASSWORD_ERROR = "*Passwords do not match";
    private static final String CREATE_ACCOUNT_PROMPT = "Create Account";
    private static final String LOG_IN_PROMPT = "Log In";
    private static final String SIGN_UP_PROMPT = "Sign Up";
    private static final String GREEN_BUTTON_ID = "green";
    private static final String ACCOUNT_BUTTON_ID = "createAccount";
    private static final String RECTANGLE_ID = "my-rect";
    private static final String PANE_ID = "pane";
    private static final String LOADING_PROMPT = "We're loading your \n available games!!!";
    private static final int BUTTON_Y_POS = 150;
    private static final int BUTTON_X_POS = 0;
    private static final int LOGO_WIDTH = 500;
    private static final int LOGO_HEIGHT = 300;
    private static final int LOGO_Y_POS = -50;
    private static final double TRANSITION_TIME = 0.8;
    private static final int ARC = 20;
    private static final int RECTANGLE_WIDTH = 400;
    private static final int RECTANGLE_HEIGHT = 200;
    private static final int LARGE_RECTANGLE_HEIGHT = 250;
    private static final double SIZE_TRANSITION = -0.6f;
    private static final int ANIMATION_CYCLE = 1;
    private static final int BACK_LOGO_SIZE = 30;
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

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        logic = new Logic(ScreenSize.getWidth(), ScreenSize.getHeight());
        background = new StackPane();
        root = new StackPane();
        background.getChildren().add(root);
        addElementsToRoot();
        Button start = createStartButton( START_BUTTON_TITLE, BUTTON_X_POS, BUTTON_Y_POS);
        start.setOnAction(e -> transitionToLogIn(start));
        root.getChildren().add(start);

        stage.setX(ScreenSize.getWidth());
        stage.setY(ScreenSize.getHeight());
        var scene = createScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void addElementsToRoot(){
        root.setId(PANE_ID);
        root.applyCss();
        root.layout();
        root.getChildren().add(createWelcomeMusic());
        root.getChildren().add(createLogoBackground());
    }
    private Scene createScene(){
        var scene = new Scene(background, ScreenSize.getWidth(), ScreenSize.getHeight());
        scene.getStylesheets().add(CSS_FILE);
        return scene;
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
        title.setFitWidth(LOGO_WIDTH);
        title.setFitHeight(LOGO_HEIGHT);
        title.setTranslateY(LOGO_Y_POS);
        return title;
    }
    private Button createStartButton(String title, int x, int y){
        Button button = new Button(title);
        button.setTranslateY(y);
        button.setTranslateX(x);
        return button;
    }

    private void transitionToLogIn(Button button){
        root.getChildren().remove(button);
        ScaleTransition st = new ScaleTransition(Duration.seconds(TRANSITION_TIME), title);
        st.setByX(SIZE_TRANSITION);
        st.setByY(SIZE_TRANSITION);
        st.setCycleCount(ANIMATION_CYCLE);
        int xPos = (int)Math.round(title.getX() + title.getBoundsInLocal().getWidth()/2);
        PathTransition bannerTransition = generatePathTransition(generatePath(xPos, 0, LOGO_Y_POS * 2), title);
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(st,bannerTransition);
        parallelTransition.play();
        parallelTransition.setOnFinished(e-> createSignInSettings(false));
    }
    private void createSignInSettings(boolean repeat){
        if(!repeat) {
            root.setStyle("-fx-opacity: 0.6; -fx-background-color: black;");
            rect = new Rectangle();
            rect.setArcWidth(ARC);
            rect.setArcHeight(ARC);
            rect.getStyleClass().add(RECTANGLE_ID);
            background.getChildren().add(rect);
        }else{
            background.getChildren().remove(accountGrid);
        }
        rect.setWidth(RECTANGLE_WIDTH);
        rect.setHeight(RECTANGLE_HEIGHT);

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
        Button login = new Button(LOG_IN_PROMPT);
        login.setId(GREEN_BUTTON_ID);
        login.setOnAction(e-> startGame(vbox));
        Button newAccount = new Button(SIGN_UP_PROMPT);
        newAccount.setId(GREEN_BUTTON_ID);
        newAccount.setOnAction(e->switchToCreateAccountGrid(vbox));
        hBox.getChildren().addAll(login,newAccount);
        hBox.setMaxWidth(rect.getWidth());
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }
    private void switchToCreateAccountGrid(VBox vbox){
        background.getChildren().remove(vbox);
        rect.setWidth(RECTANGLE_WIDTH);
        rect.setHeight(LARGE_RECTANGLE_HEIGHT);
        createAccountGrid = new CreateAccount();
        Image back = new Image(this.getClass().getClassLoader().getResourceAsStream("back.png"));
        ImageView backToLogIn = new ImageView(back);
        backToLogIn.setFitWidth(BACK_LOGO_SIZE);
        backToLogIn.setFitHeight(BACK_LOGO_SIZE);
        backToLogIn.setOnMousePressed(e->createSignInSettings(true));
        createAccountGrid.add(backToLogIn, 0,0);

        Button createAccount = new Button(CREATE_ACCOUNT_PROMPT);
        createAccount.setOnAction(e-> createAccount());
        createAccount.setId(ACCOUNT_BUTTON_ID);
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
        pathTransition.setDuration(Duration.seconds(TRANSITION_TIME));
        pathTransition.setPath(path);
        pathTransition.setCycleCount(ANIMATION_CYCLE);
        pathTransition.setNode(node);
        return pathTransition;
    }
    private void availableGames(){
        fadeOutMusic();
        this.stage.close();
        LogInPreloader logInPreloader = new LogInPreloader();
        logInPreloader.start(new Stage());
        logInPreloader.setTitle(LOADING_PROMPT);
        logInPreloader.setTransitionEvent(e -> {
            GameSelection gameSelection = new GameSelection(logic);
            gameSelection.start(new Stage());
        });
    }
    private void fadeOutMusic(){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(TRANSITION_TIME),
                        new KeyValue(mediaPlayer.volumeProperty(), 0)));
        timeline.play();
    }

    private void startGame(VBox vBox){

        try {
            if (logic.authenticateUser(logInGrid.getUserName(), logInGrid.getPassword())) {
                availableGames();
            } else {
                Text text = new Text(USER_ERROR);
                text.setFill(Color.RED);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().add(text);
            }
        } catch (Exception e){
            Text text = new Text(e.getMessage());
            text.setFill(Color.RED);
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(text);
        }
    }
    private void createAccount(){
        try {
            createAccountGrid.getUserName();
            createAccountGrid.getPasswordField();
            createAccountGrid.getPasswordCheck();
            if (!createAccountGrid.getPasswordField().equals(createAccountGrid.getPasswordCheck())) {
                Text text = new Text(PASSWORD_ERROR);
                text.setFill(Color.RED);
                accountGrid.getChildren().add(text);
            }
            logic.createNewUser(createAccountGrid.getUserName(), createAccountGrid.getPasswordField(), createAccountGrid.getPasswordCheck());
            availableGames();
        } catch (Exception e){
            Text text = new Text(e.getMessage());
            text.setFill(Color.RED);
            accountGrid.getChildren().add(text);
        }

    }
    public static void main(String [] args){
        Application.launch(args);}
}

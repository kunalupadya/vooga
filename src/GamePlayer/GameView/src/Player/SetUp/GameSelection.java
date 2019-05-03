package Player.SetUp;

import BackendExternal.Logic;
import ExternalAPIs.GameInfo;
import Player.GamePlayMain;
import Player.ScreenSize;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.List;

public class GameSelection extends Application {

    private static final String SELECT_PROMPT = "Select a Game";
    private static final String SELECT_STYLE = "selectGame";
    private static final String SMALLER_BUTTON_STYLE = "smallerButton";
    private static final String SCROLLPANE_STYLE = "scrollpane";
    private static final String TITLE_STYLE = "gameTitle";
    private static final String RECT_STYLE = "my-rect";
    private static final String PANE_STYLE = "pane";
    private static final String CSS_FILE = "style.css";
    private static final String DARKER_STYLE = "-fx-opacity: 0.9; -fx-background-color: black;";
    private static final String PLAY = "Play";
    private static final String SAVED_STATE_PROMPT = "Would you like to start from your saved progress?";
    private static final String LOADING_PROMPT = "Get Ready to Play!";
    private static final double SCROLLPANE_RATIO = 1/3;
    private static final int LABEL_HEIGHT = 100;
    private static final int IMAGE_SIZE = 200;
    private static final int IMAGE_SIZE_SMALL = 100;
    private static final int OFFSET = 10;
    private static final int SCROLLPANE_OFFSET = 100;
    private static final int RECT_RADIUS = 20;
    private static final String FONT_VERANDA = "veranda";
    private static final int TITLE_OFFSET = -100;
    private static final int SUBTITLE_OFFSET = -70;
    private static final int PADDING = 5;
    private static final int PLAY_BUTTON_YPOS = 100;
    private static final int HBOX_YPOS = 50;
    private static final int RECT_WIDTH = 400;
    private static final int RECT_HEIGHT = 150;
    private static final double SELECT_RECT_RATIO = 0.5;
    private static final int TITLE_SIZE = 50;
    private static final int SUBTITLE_SIZE = 20;
    private HBox root;
    private Stage stage;
    private ScrollPane scrollPane;
    private double width = ScreenSize.getWidth();
    private double height = ScreenSize.getHeight();
    private Logic logic;
    private StackPane gameStart;
    private StackPane totalBackground;


    public GameSelection(Logic logic){
        super();
        this.logic = logic;
    }
    @Override
    public void start(Stage primaryStage) {
        totalBackground = new StackPane();
        createScrollPane();
        stage = primaryStage;
        stage.setX(width);
        stage.setY(height);
        root = new HBox();
        totalBackground.getChildren().add(root);
        root.setId(PANE_STYLE);
        Label text = createSelectPrompt();
        VBox left = createLeftVBox(scrollPane, text);
        root.getChildren().add(left);
        var scene = new Scene(totalBackground, width, height);
        scene.getStylesheets().add(CSS_FILE);
        primaryStage.setScene(scene);
        primaryStage.show();
        createGameSelectionScreen();

    }
    private VBox createLeftVBox(ScrollPane scrollPane, Label text){
        VBox left = new VBox();
        left.setAlignment(Pos.CENTER);
        left.getChildren().add(text);
        left.getChildren().add(scrollPane);
        return left;
    }

    private Label createSelectPrompt(){
        Label text = new Label(SELECT_PROMPT);
        text.setPrefHeight(LABEL_HEIGHT);
        text.setId(SELECT_STYLE);
        text.setLayoutX(width/2);
        return text;
    }

    private void createScrollPane(){
        scrollPane =  new ScrollPane();
        scrollPane.setId(SCROLLPANE_STYLE);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefSize(width * SCROLLPANE_RATIO ,height);

    }
    private List<GameInfo> uploadAvailableGames(){
        return logic.getGameOptions();
    }

    private void createGameSelectionScreen(){
        VBox vBox = new VBox();
        vBox.setPrefWidth(width*SCROLLPANE_RATIO-OFFSET);
        vBox.setAlignment(Pos.CENTER);
        for(GameInfo gameInfo: uploadAvailableGames()){
            StackPane container = new StackPane();
            container.setAlignment(Pos.CENTER);
            Rectangle bkg = createBackdrop(scrollPane.getWidth() - SCROLLPANE_OFFSET,scrollPane.getWidth() - SCROLLPANE_OFFSET);
            container.getChildren().add(bkg);
            VBox gameLook = new VBox();
            gameLook.setAlignment(Pos.CENTER);
            Text title = new Text(gameInfo.getGameTitle());
            title.setId(TITLE_STYLE);
            gameLook.getChildren().add(title);
            Image image = logic.getImage(gameInfo.getGameThumbnailID());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(IMAGE_SIZE);
            imageView.setFitHeight(IMAGE_SIZE);
            imageView.setOnMouseClicked(e -> startGame(gameInfo, image));
            gameLook.getChildren().add(imageView);
            container.getChildren().add(gameLook);
            vBox.getChildren().add(container);
        }
        scrollPane.setContent(vBox);
    }
    private Rectangle createBackdrop(double width, double height){
        Rectangle rect = new Rectangle();
        rect.setArcWidth(RECT_RADIUS);
        rect.setArcHeight(RECT_RADIUS);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.getStyleClass().add(RECT_STYLE);
        return rect;
    }
    private void startGame(GameInfo gameInfo, Image image){
        setTitle(gameInfo, image);
    }

    public void setTitle(GameInfo gameInfo, Image image){
        if(root.getChildren().contains(gameStart)){
            root.getChildren().remove(gameStart);
        }
        gameStart = new StackPane();
        gameStart.setPrefWidth(width* 2 /3);
        gameStart.setAlignment(Pos.CENTER);
        Rectangle bkg = createBackdrop(gameStart.getPrefWidth() * SELECT_RECT_RATIO, gameStart.getPrefWidth() * SELECT_RECT_RATIO);
        gameStart.getChildren().add(bkg);


        Text title = new Text(gameInfo.getGameTitle());
        title.setFont(Font.font(FONT_VERANDA, FontWeight.BOLD, TITLE_SIZE));
        title.setTranslateY(TITLE_OFFSET);


        Text subtitle = new Text(gameInfo.getGameDescription());
        subtitle.setFont(Font.font(FONT_VERANDA, FontPosture.ITALIC, SUBTITLE_SIZE));
        subtitle.setTranslateY(SUBTITLE_OFFSET);


        gameStart.getChildren().add(title);
        gameStart.getChildren().add(subtitle);
        gameStart.applyCss();
        gameStart.layout();


        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(IMAGE_SIZE_SMALL);
        imageView.setFitHeight(IMAGE_SIZE_SMALL);


        gameStart.getChildren().add(imageView);
        Button play = new Button(PLAY);
        play.setTranslateY(PLAY_BUTTON_YPOS);
        gameStart.getChildren().add(play);
        play.setOnAction(e-> displaySavedOptions(gameInfo));
        root.getChildren().add(gameStart);
    }

    private void displaySavedOptions(GameInfo gameInfo){
        root.setStyle(DARKER_STYLE);
        Rectangle rect = createBackdrop(RECT_WIDTH, RECT_HEIGHT);
        Text choice = new Text(SAVED_STATE_PROMPT);
        Button fromSaved = createSavedButton(gameInfo);
        Button fromStart = createStartOverButton(gameInfo);
        HBox hBox = createButtonHBox(fromSaved, fromStart);
        totalBackground.getChildren().add(rect);
        totalBackground.getChildren().addAll(choice,hBox);
    }

    private HBox createButtonHBox(Button button1, Button button2){
        HBox hbox = new HBox();
        hbox.setSpacing(PADDING);
        hbox.getChildren().addAll(button1, button2);
        hbox.setMaxWidth(ScreenSize.getWidth()/4);
        hbox.setMaxHeight(ScreenSize.getWidth()/4);
        hbox.setAlignment(Pos.CENTER);
        hbox.setTranslateY(HBOX_YPOS);
        return hbox;
    }

    private Button createSavedButton(GameInfo gameInfo){
        Button fromSaved = new Button("Yes");
        fromSaved.setId(SMALLER_BUTTON_STYLE);
        fromSaved.setOnAction(e->startFromSaved(gameInfo));
        return fromSaved;
    }
    private Button createStartOverButton(GameInfo gameInfo){
        Button fromStart = new Button("No, start over");
        fromStart.setId(SMALLER_BUTTON_STYLE);
        fromStart.setOnAction(e->startGame(gameInfo, false));
        return fromStart;
    }

    private void startFromSaved(GameInfo gameInfo){
        startGame(gameInfo, true);
    }

    private void startGame(GameInfo gameInfo, boolean saved){
        this.stage.close();
        LogInPreloader logInPreloader = new LogInPreloader();
        logInPreloader.start(new Stage());
        logInPreloader.setTitle(LOADING_PROMPT);
        logInPreloader.setTransitionEvent(e->transitionToScreen(gameInfo, saved));
    }
    private void transitionToScreen(GameInfo gameInfo, boolean saved){
        GamePlayMain gamePlayMain = new GamePlayMain();
        gamePlayMain.setLogic(logic);
        gamePlayMain.setGameInfo(gameInfo, saved);
        gamePlayMain.start(new Stage());
    }
}

package Player.SetUp;

import BackendExternal.Logic;
import ExternalAPIs.GameInfo;
import Player.GamePlay.GamePlayMain;
import Player.ScreenSize;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class GameSelection extends Application {

    private static final int LABEL_HEIGHT = 100;
    private static final String SELECT_PROMPT = "Select a Game";
    private static final String SELECT_STYLE = "selectGame";
    private HBox root;
    private Stage stage;
    private ScrollPane scrollPane = new ScrollPane();
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
        scrollPane.setId("scrollpane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefSize(width/3,height);

        stage = primaryStage;
        stage.setX(width);
        stage.setY(height);
        root = new HBox();
        totalBackground.getChildren().add(root);
        root.setId("pane");
        Label text = new Label(SELECT_PROMPT);
        text.setPrefHeight(LABEL_HEIGHT);
        text.setId(SELECT_STYLE);

        VBox left = new VBox();
        left.setAlignment(Pos.CENTER);
        left.getChildren().add(text);
        left.getChildren().add(scrollPane);
        root.getChildren().add(left);
        text.setLayoutX(width/2);
        var scene = new Scene(totalBackground, width, height);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        createGameSelectionScreen();

    }
    private List<GameInfo> uploadAvailableGames(){
        return logic.getGameOptions();
    }

    private void createGameSelectionScreen(){
        VBox vBox = new VBox();
        vBox.setPrefWidth(width/3-10);
        vBox.setAlignment(Pos.CENTER);
        for(GameInfo gameInfo: uploadAvailableGames()){
            StackPane container = new StackPane();
            container.setAlignment(Pos.CENTER);
            Rectangle bkg = createBackdrop(scrollPane.getWidth() - 100,scrollPane.getWidth() - 100);
            container.getChildren().add(bkg);
            VBox gameLook = new VBox();
            gameLook.setAlignment(Pos.CENTER);
            Text title = new Text(gameInfo.getGameTitle());
            title.setId("gameTitle");
            gameLook.getChildren().add(title);
            Image image = logic.getImage(gameInfo.getGameThumbnailID());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imageView.setOnMouseClicked(e -> startGame(gameInfo, image));
            gameLook.getChildren().add(imageView);
            container.getChildren().add(gameLook);
            vBox.getChildren().add(container);
        }
        scrollPane.setContent(vBox);
    }
    private Rectangle createBackdrop(double width, double height){
        Rectangle rect = new Rectangle();
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.getStyleClass().add("my-rect");
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
        Rectangle bkg = createBackdrop(gameStart.getPrefWidth()/2, gameStart.getPrefWidth()/2);
        gameStart.getChildren().add(bkg);
        Text title = new Text(gameInfo.getGameTitle());
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        title.setTranslateY(-100);
        Text subtitle = new Text(gameInfo.getGameDescription());
        subtitle.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
        subtitle.setTranslateY(-70);
        gameStart.getChildren().add(title);
        gameStart.getChildren().add(subtitle);
        gameStart.applyCss();
        gameStart.layout();
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        gameStart.getChildren().add(imageView);
        Button play = new Button("Play");
        play.setTranslateX(0);
        play.setTranslateY(100);
        gameStart.getChildren().add(play);
        play.setOnAction(e-> displaySavedOptions(gameInfo));
        root.getChildren().add(gameStart);
    }

    private void displaySavedOptions(GameInfo gameInfo){
        root.setStyle("-fx-opacity: 0.9; -fx-background-color: black;");
        Rectangle rect = createSavedPromptRect();
        Text choice = new Text("Would you like to start from your saved progress?");
        Button fromSaved = createSavedButton(gameInfo);
        Button fromStart = createStartOverButton(gameInfo);
        HBox hBox = createButtonHBox(fromSaved, fromStart);
        totalBackground.getChildren().add(rect);
        totalBackground.getChildren().addAll(choice,hBox);
    }

    private HBox createButtonHBox(Button button1, Button button2){
        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.getChildren().addAll(button1, button2);
        hbox.setMaxWidth(ScreenSize.getWidth()/4);
        hbox.setMaxHeight(ScreenSize.getWidth()/4);
        hbox.setAlignment(Pos.CENTER);
        hbox.setTranslateY(50);
        return hbox;
    }

    private Rectangle createSavedPromptRect(){
        Rectangle rect = new Rectangle();
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        rect.setWidth(400);
        rect.setHeight(150);
        rect.getStyleClass().add("my-rect");
        return rect;
    }
    private Button createSavedButton(GameInfo gameInfo){
        Button fromSaved = new Button("Yes");
        fromSaved.setId("smallerButton");
        fromSaved.setOnAction(e->startFromSaved(gameInfo));
        return fromSaved;
    }
    private Button createStartOverButton(GameInfo gameInfo){
        Button fromStart = new Button("No, start over");
        fromStart.setId("smallerButton");
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
        logInPreloader.setTitle("Get Ready to Play!");
        logInPreloader.setTransitionEvent(e->transitionToScreen(gameInfo, saved));
    }
    private void transitionToScreen(GameInfo gameInfo, boolean saved){
        GamePlayMain gamePlayMain = new GamePlayMain();
        gamePlayMain.setGameInfo(gameInfo, saved);
        if(saved){
            gamePlayMain.setLogic(logic);
        }
        gamePlayMain.start(new Stage());
    }
}

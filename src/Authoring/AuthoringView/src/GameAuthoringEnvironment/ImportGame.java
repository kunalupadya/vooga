package GameAuthoringEnvironment;

import BackendExternalAPI.Model;
import Configs.GamePackage.Game;
import ExternalAPIs.GameInfo;
import GameAuthoringEnvironment.AuthoringScreen.AuthoringVisualization;
import GameAuthoringEnvironment.AuthoringScreen.ScreenSize;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class ImportGame extends Application {
    private Stage stage;
    private List<GameInfo> gameInfoList;
    private Model model;
    private final String title = "Import Game";
    private ScrollPane scrollPane;
    public static final String RESOURCES_PATH = "resources/";
    private CloseStage eventHandler;
    private HBox gameSelector;
    private VBox rightSide;
    @Override
    public void start(Stage stage){
        this.stage = stage;
        gameSelector = new HBox();
        gameSelector.setId("backdrop");
        var scene = new Scene(gameSelector, ScreenSize.getWidth()/2, ScreenSize.getHeight()/2);
        scene.getStylesheets().add("authoring_style.css");
        gameSelector.getChildren().add(createScrollPane());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    public ImportGame(List<GameInfo> gameInfo, Model model){
        super();
        this.gameInfoList = gameInfo;
        this.model = model;
    }
    public void setEventHandler(CloseStage eventHandler){
        this.eventHandler = eventHandler;
    }
    private ScrollPane createScrollPane(){
        scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(ScreenSize.getWidth()/4);
        VBox vBox = new VBox();
        for(GameInfo gameInfo: gameInfoList){
            StackPane stackPane = new StackPane();
            stackPane.setPrefWidth(ScreenSize.getWidth());
            Rectangle rect = createRectangle();
            stackPane.getChildren().add(rect);
            Text title = new Text(gameInfo.getGameTitle());
            stackPane.getChildren().add(title);
            ImageView thumbnailImageView = createImageView(gameInfo);
            thumbnailImageView.setFitWidth(ScreenSize.getWidth()/4);
            thumbnailImageView.setPreserveRatio(true);
            thumbnailImageView.setOnMousePressed(e-> createPrompt(gameInfo, thumbnailImageView));
            thumbnailImageView.setTranslateY(rect.getHeight()/2);
            stackPane.getChildren().add(thumbnailImageView);
            vBox.getChildren().add(stackPane);
        }
        scrollPane.setContent(vBox);
        return scrollPane;
    }
    private void startSelectedGame(GameInfo gameInfo){
        Game game = model.loadGameObject(gameInfo);
        makeGame(game);
        eventHandler.close();
    }
    private void createPrompt(GameInfo gameInfo, ImageView imageView){
        StackPane stackPane = new StackPane();
        Rectangle rect = createRectangle();
        Text text = new Text(gameInfo.getGameTitle());
        stackPane.getChildren().addAll(rect, text, imageView);
        imageView.setTranslateY(rect.getHeight()/2);
        if(rightSide != null){
            gameSelector.getChildren().remove(rightSide);
        }
        Button importButton = new Button("Import");
        importButton.setOnAction(e->startSelectedGame(gameInfo));
        rightSide = new VBox();
        rightSide.getChildren().addAll(stackPane, importButton);
        rightSide.setAlignment(Pos.CENTER);
        gameSelector.getChildren().add(rightSide);
    }
    private ImageView createImageView(GameInfo gameInfo){
        ImageView imageView = new ImageView();
        Image image;
//        try {
            image = model.getImage(gameInfo.getGameThumbnailID());
//            image = new Image(new FileInputStream(RESOURCES_PATH + gameInfo.getGameThumbnail()));
//        }catch (IOException e){
//            return imageView;
//        }
        imageView.setImage(image);
        return imageView;
    }
    private Rectangle createRectangle(){
        Rectangle rect = new Rectangle();
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        rect.setWidth(ScreenSize.getWidth()/4);
        rect.setHeight(ScreenSize.getHeight()/4);
        rect.getStyleClass().add("my-rect");
        return rect;
    }

    private void makeGame(Game game){
        AuthoringVisualization authoringVisualization = new AuthoringVisualization(game, model);
        authoringVisualization.start(new Stage());
        this.stage.close();
    }
}

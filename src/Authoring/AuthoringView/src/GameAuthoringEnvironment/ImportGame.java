package GameAuthoringEnvironment;

import BackendExternalAPI.AuthoringBackend;
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

import java.util.List;

public class ImportGame extends Application {
    private Stage stage;
    private List<GameInfo> gameInfoList;
    private AuthoringBackend authoringBackend;
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
    public ImportGame(List<GameInfo> gameInfo, AuthoringBackend authoringBackend){
        super();
        this.gameInfoList = gameInfo;
        this.authoringBackend = authoringBackend;
    }
    public void setEventHandler(CloseStage eventHandler){
        this.eventHandler = eventHandler;
    }
    private ScrollPane createScrollPane(){
        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefWidth(ScreenSize.getWidth()/4 + 50);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setMaxWidth(ScreenSize.getWidth()/4);
        for(GameInfo gameInfo: gameInfoList){
            StackPane stackPane = new StackPane();
            stackPane.setPrefWidth(ScreenSize.getWidth());
            Rectangle rect = createRectangle();
            stackPane.getChildren().add(rect);
            Text title = new Text(gameInfo.getGameTitle());
            title.setTranslateY(-rect.getHeight()/3);
            stackPane.getChildren().add(title);
            ImageView thumbnailImageView = createImageView(gameInfo);
            thumbnailImageView.setFitWidth(rect.getWidth()/3);
            thumbnailImageView.setPreserveRatio(true);
            thumbnailImageView.setOnMousePressed(e-> createPrompt(gameInfo));
            thumbnailImageView.setTranslateY(rect.getHeight()/3 - thumbnailImageView.getBoundsInLocal().getHeight()/2);
            stackPane.getChildren().add(thumbnailImageView);
            vBox.getChildren().add(stackPane);
        }
        scrollPane.setContent(vBox);
        return scrollPane;
    }
    private void startSelectedGame(GameInfo gameInfo){
        Game game = authoringBackend.loadGameObject(gameInfo);
        makeGame(game);
        eventHandler.close();
    }
    private void createPrompt(GameInfo gameInfo){
        StackPane stackPane = new StackPane();
        Rectangle rect = createRectangle();
        Text text = new Text(gameInfo.getGameTitle());
        text.setTranslateY(-rect.getHeight()/3);
        ImageView imageView = createImageView(gameInfo);
        stackPane.getChildren().addAll(rect, text, imageView);
        imageView.setTranslateY(rect.getHeight()/4);
        imageView.setFitWidth(rect.getWidth()/3);
        imageView.setPreserveRatio(true);
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
        image = authoringBackend.getImage(gameInfo.getGameThumbnailID());
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
        AuthoringVisualization authoringVisualization = new AuthoringVisualization(game, authoringBackend);
        authoringVisualization.start(new Stage());
        this.stage.close();
    }
}

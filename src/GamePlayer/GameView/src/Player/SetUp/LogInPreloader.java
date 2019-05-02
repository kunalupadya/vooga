package Player.SetUp;

import Player.ScreenSize;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LogInPreloader extends Application {
    public static final int MILLISECOND_DELAY = 10;
    private static final String CHARACTER_FILE = "character.png";
    private static final String CLOUD_FILE = "cloud.png";
    private static final String LOADING = "Loading ...";
    private static final String RECT_STYLE = "-fx-fill: linear-gradient(yellow, lightyellow);";
    private static final String FONT = "Veranda";
    private static final String PANE_STYLE = "pane";
    private static final int FONT_SIZE = 30;
    private static final int CLOUD_XPOS = 20;
    private static final int CHARACTER_XPOS = -150;
    private static final double CLOUD_RATIO = 3.8;
    private static final int OFFSET = 10;
    private EventHandler eventHandler;
    private Timeline animation;
    private StackPane root;
    private String title;
    private Rectangle loading;
    private Stage stage;
    private Text text;
        @Override
        public void start(Stage primaryStage){
            stage = primaryStage;
            root = new StackPane();
            Image characterImage = new Image(this.getClass().getClassLoader().getResourceAsStream(CHARACTER_FILE));
            Image cloudImage = new Image(this.getClass().getClassLoader().getResourceAsStream(CLOUD_FILE));
            ImageView imageView = new ImageView(characterImage);
            ImageView cloudView = new ImageView(cloudImage);
            root.getChildren().add(imageView);
            root.getChildren().add(cloudView);
            cloudView.setPreserveRatio(true);
            cloudView.setTranslateX(CLOUD_XPOS);
            cloudView.setFitWidth(ScreenSize.getWidth()/ CLOUD_RATIO);
            imageView.setTranslateX(CHARACTER_XPOS);
            imageView.setTranslateY(ScreenSize.getHeight()/2 - imageView.getBoundsInLocal().getHeight()/2);

            root.setId(PANE_STYLE);
            text = new Text(LOADING);
            HBox hbox = new HBox(text, createInitialLoadingBar());
            root.getChildren().add(hbox);
            text.setFont(Font.font (FONT, FONT_SIZE));
            var scene = new Scene(root, ScreenSize.getWidth(), ScreenSize.getHeight());
            primaryStage.setScene(scene);
            primaryStage.show();
            var animationFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
            animation = new Timeline();
            int animationTime = (int)Math.round((ScreenSize.getWidth() - text.getBoundsInLocal().getWidth())/10);
            animation.setCycleCount(animationTime);
            animation.getKeyFrames().add(animationFrame);
            animation.play();
        }
        public void setTransitionEvent(EventHandler eventHandler){
            this.eventHandler = eventHandler;
            animation.setOnFinished(eventHandler);
        }
        public void setTitle(String text){
            this.title = text;
            Text t = new Text(title);
            root.getChildren().add(t);
        }
        private Rectangle createInitialLoadingBar(){
            loading = new Rectangle();
            loading.setStyle(RECT_STYLE);
            loading.setHeight(text.getBoundsInParent().getHeight() * 2);
            return loading;
        }
        private void step() {
            loading.setWidth(loading.getWidth() + OFFSET );
            animation.statusProperty().addListener((obs, oldStatus, newStatus) -> {
                if (newStatus == Animation.Status.STOPPED) {
                    animation.setOnFinished(eventHandler);
                }
            });
            animation.statusProperty().addListener((obs, oldStatus, newStatus) -> {
                if (newStatus == Animation.Status.STOPPED) {
                    this.stage.close();
                }
            });

        }
}

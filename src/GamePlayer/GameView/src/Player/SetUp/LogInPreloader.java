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
    private EventHandler eventHandler;
    private Image characterImage = new Image(this.getClass().getClassLoader().getResourceAsStream("character.png"));
    private Image cloudImage = new Image(this.getClass().getClassLoader().getResourceAsStream("cloud.png"));
    private Timeline animation;
    private StackPane root;
    private String title;
    private Rectangle loading;
    private Stage stage;
    private Text text = new Text("Loading ...");
        @Override
        public void start(Stage primaryStage){
            stage = primaryStage;
            root = new StackPane();
            ImageView imageView = new ImageView(characterImage);
            ImageView cloudView = new ImageView(cloudImage);
            root.getChildren().add(imageView);
            root.getChildren().add(cloudView);
            cloudView.setPreserveRatio(true);
            cloudView.setTranslateX(20);
            cloudView.setFitWidth(ScreenSize.getWidth()/3.8);
            imageView.setTranslateX(-150);
            imageView.setTranslateY(ScreenSize.getHeight()/2 - imageView.getBoundsInLocal().getHeight()/2);

            root.setId("pane");
            HBox hbox = new HBox(text, createInitialLoadingBar());
            root.getChildren().add(hbox);
            text.setFont(Font.font ("Verdana", 30));
            var scene = new Scene(root, ScreenSize.getWidth(), ScreenSize.getHeight());
            primaryStage.setScene(scene);
            primaryStage.show();
            var animationFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
            animation = new Timeline();
            int animationTime = (int)Math.round((ScreenSize.getWidth() - text.getBoundsInLocal().getWidth())/10);
            System.out.println(animationTime);
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
            loading.setStyle("-fx-fill: linear-gradient(yellow, lightyellow);");
            loading.setHeight(text.getBoundsInParent().getHeight() * 2);
            return loading;
        }
        private void step() {
            loading.setWidth(loading.getWidth() + 10);
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

package Player;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LogInPreloader extends Application {
    public static final int MILLISECOND_DELAY = 200;
    private EventHandler eventHandler;
    private Timeline animation;
        Stage stage;
        Text text = new Text("Loading ...");
        @Override
        public void start(Stage primaryStage){
            stage = primaryStage;
            StackPane root = new StackPane();
            root.setId("pane");
            root.getChildren().add(text);
            text.setFont(Font.font ("Verdana", 20));
            var scene = new Scene(root, ScreenSize.getWidth(), ScreenSize.getHeight());
            primaryStage.setScene(scene);
            primaryStage.show();
            var animationFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
            animation = new Timeline();
            animation.setCycleCount(10);
            animation.getKeyFrames().add(animationFrame);
            animation.play();
        }
        public void setTransitionEvent(EventHandler eventHandler){
            this.eventHandler = eventHandler;
        }

        private void step(){
            if(text.getText().equals("Loading ...")){
                text.setText("Loading .. ");
            }else if(text.getText().equals("Loading .. ")){
                text.setText("Loading .  ");
            }else{
                text.setText("Loading ...");
            }
            animation.setOnFinished(eventHandler);
        }

        public static void main(String [] args){
            launch(args);
        }
}

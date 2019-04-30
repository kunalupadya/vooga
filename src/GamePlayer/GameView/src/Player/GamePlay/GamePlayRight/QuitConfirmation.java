package Player.GamePlay.GamePlayRight;

import BackendExternal.Logic;
import Player.GamePlay.SelectionInterface;
import Player.ScreenSize;
import Player.SetUp.GameSelection;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class QuitConfirmation extends Stage{

    public QuitConfirmation(SelectionInterface quit, MediaPlayer media, Logic logic){
        StackPane root = new StackPane();
        root.setId("quit");
        Scene scene = new Scene(root, ScreenSize.getWidth()/3, ScreenSize.getHeight()/3);
        scene.getStylesheets().add("style.css");
        Text text = new Text("Are you sure you \n want to quit?");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Veranda", FontWeight.BOLD, 30));
        setScene(scene);
        Button yes = createButton("smallerButton","Yes", 0, 50);
        yes.setOnAction(e -> displayChoices(quit, media, logic));
        Button no = createButton("smallerButton", "No", 0, 50);
        no.setOnAction(e -> close());
        HBox buttons = new HBox(yes, no);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        VBox box = new VBox(text,buttons);
        box.setAlignment(Pos.CENTER);
        root.getChildren().add(box);
        show();
    }

    private Button createButton(String style, String title, int x, int y){
        Button button = new Button(title);
        button.setId(style);
        button.setTranslateX(x);
        button.setTranslateY(y);
        return button;
    }

    private void displayChoices(SelectionInterface quit, MediaPlayer media, Logic logic){
        media.stop();
        close();
        quit.closeStage();
        GameSelection gameSelection = new GameSelection(logic);
        gameSelection.start(new Stage());
    }
}

package Player.GamePlay.GamePlayRight;

import BackendExternal.Logic;
import Player.GamePlay.SelectionInterface;
import Player.ScreenSize;
import Player.SetUp.GameSelection;
import javafx.geometry.Pos;
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

class QuitConfirmation extends Stage{
    private static final String STACKPANE_STYLE = "quit";
    private static final String CSS_FILE = "style.css";
    private static final String PROMPT = "Are you sure you \n want to quit?";
    private static final String FONT = "Veranda";
    private static final int FONT_SIZE = 30;
    private static final int SPACING = 10;
    private static final String SMALLBUTTON_STYLE = "smallerButton";
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final int BUTTON_YPOS = 50;
    private static final int BUTTON_XPOS = 0;

    QuitConfirmation(SelectionInterface quit, MediaPlayer media, Logic logic){
        StackPane root = new StackPane();
        root.setId(STACKPANE_STYLE);
        Scene scene = new Scene(root, ScreenSize.getWidth()/3, ScreenSize.getHeight()/3);
        scene.getStylesheets().add(CSS_FILE);
        Text text = new Text(PROMPT);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font(FONT, FontWeight.BOLD, FONT_SIZE));
        setScene(scene);
        Button yes = createButton(SMALLBUTTON_STYLE,YES, BUTTON_XPOS, BUTTON_YPOS);
        yes.setOnAction(e -> displayChoices(quit, media, logic));
        Button no = createButton(SMALLBUTTON_STYLE, NO, BUTTON_XPOS, BUTTON_YPOS);
        no.setOnAction(e -> close());
        HBox buttons = new HBox(yes, no);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(SPACING);
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

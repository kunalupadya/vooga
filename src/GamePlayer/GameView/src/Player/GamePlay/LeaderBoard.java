package Player.GamePlay;

import BackendExternal.Logic;
import ExternalAPIs.LeaderBoardEntry;
import Player.ScreenSize;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LeaderBoard extends Application {
    private static final String LEADERBOARD = "Leaderboard";
    private static final double SCENE_RATIO = 0.5;
    private static final int PADDING = 10;
    private static final int NUMBER_ENTRIES = 5;
    private Logic logic;
    @Override
    public void start(Stage stage){
        VBox vBox = new VBox();
        var scene = new Scene(vBox, ScreenSize.getWidth() * SCENE_RATIO, ScreenSize.getHeight() * SCENE_RATIO);
        ScrollPane scrollPane = createLeaderBoard();
        Label label = new Label(LEADERBOARD);
        vBox.getChildren().addAll(label, scrollPane);
        stage.setScene(scene);
        stage.show();
    }

    public LeaderBoard(Logic logic){
        super();
        this.logic = logic;
    }

    private ScrollPane createLeaderBoard(){
        VBox scores = new VBox();
        for(LeaderBoardEntry leaderBoardEntry: logic.getLeaderBoardEntries(NUMBER_ENTRIES)){
            HBox hBox = new HBox();
            hBox.setSpacing(PADDING);
            Text username = new Text(leaderBoardEntry.getUserName());
            Text score = new Text(Integer.toString(leaderBoardEntry.getScore()));
            Text rank = new Text(Integer.toString(leaderBoardEntry.getRank()));
            hBox.getChildren().addAll(rank, username,score );
            scores.getChildren().add(hBox);
        }
        ScrollPane scrollPane = new ScrollPane();
        return scrollPane;
    }





}

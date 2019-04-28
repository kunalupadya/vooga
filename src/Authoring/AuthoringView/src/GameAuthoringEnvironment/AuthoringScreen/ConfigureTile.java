package GameAuthoringEnvironment.AuthoringScreen;

import Configs.Configurable;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;

public class ConfigureTile {

    private ListView myListView;
    private List<TerrainTile> myTerrainTileList;

    public ConfigureTile(ListView<String> listview, List<TerrainTile> terrainTileList){
        myTerrainTileList = terrainTileList;
        myListView = listview;
        Stage popUpWindow = new Stage();
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Map Editor");

        VBox allLayout = new VBox(10);

        Label tileName = new Label("Name of Tile");
        TextField tf = new TextField();
        VBox nameBox = new VBox(10);
        nameBox.getChildren().addAll(tileName,tf);

        Label isPathLabel = new Label("Set as Path");
        RadioButton trueButton = new RadioButton("True");
        RadioButton falseButton = new RadioButton("False");

        VBox pathRadio = new VBox(10);
        pathRadio.getChildren().addAll(isPathLabel, trueButton, falseButton);

        TextField imageTextField = new TextField();
        Button chooseImageButton = new Button("Choose Image");
        chooseImageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //TODO Connect to the data server
            }
        });

        VBox imageBox = new VBox(10);
        imageBox.getChildren().addAll(chooseImageButton, imageTextField);


        Button submitButton = new Button("Complete");
        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //TODO ADD the new terrain tile to the listview and terraintilelist.
                //TerrainTile newTile = new TerrainTile(new Image());
                if(tf.getText()!=null)
                    myListView.getItems().add(tf.getText());
                    popUpWindow.close();
            }
        });



        allLayout.getChildren().addAll(nameBox,pathRadio,imageBox,submitButton);


        Scene scene= new Scene(allLayout, 500, 500);
        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();
    }
}
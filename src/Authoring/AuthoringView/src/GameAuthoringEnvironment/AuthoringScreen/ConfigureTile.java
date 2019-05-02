package GameAuthoringEnvironment.AuthoringScreen;

import Configs.Configurable;
import GameAuthoringEnvironment.AuthoringComponents.AlertScreen;
import GameAuthoringEnvironment.AuthoringComponents.ConfigureImage;
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
import java.util.Map;

public class ConfigureTile {
    ConfigureImage configureImage;
    public static final int SCENE_BOUNDS=500;
    public static final int VBOX_VAL=10;
    private ListView myListView;
    private List<TerrainTile> myTerrainTileList;
    private Map<String, Integer> typeToImageMap;
    private Map<String, Boolean> typeToPath;

    public ConfigureTile(ListView<String> listview, List<TerrainTile> terrainTileList, Map<String, Integer> map,Map<String, Boolean> boolMap){
        myTerrainTileList = terrainTileList;
        myListView = listview;
        typeToImageMap=map;
        typeToPath=boolMap;
        Stage popUpWindow = new Stage();
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Map Editor");

        VBox allLayout = new VBox(VBOX_VAL);

        Label tileName = new Label("Name of Tile");
        TextField tf = new TextField();
        VBox nameBox = new VBox(VBOX_VAL);
        nameBox.getChildren().addAll(tileName,tf);

        Label isPathLabel = new Label("Set as Path");
        RadioButton trueButton = new RadioButton("True");
        RadioButton falseButton = new RadioButton("False");

        VBox pathRadio = new VBox(VBOX_VAL);
        pathRadio.getChildren().addAll(isPathLabel, trueButton, falseButton);

        TextField imageTextField = new TextField();
        Button chooseImageButton = new Button("Choose Image");
        chooseImageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //TODO Connect to the data server
                configureImage = new ConfigureImage(imageTextField, "TERRAIN");
            }
        });

        VBox imageBox = new VBox(VBOX_VAL);
        imageBox.getChildren().addAll(chooseImageButton, imageTextField);


        Button submitButton = new Button("Complete");
        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //TODO ADD the new terrain tile to the listview and terraintilelist.
                //TerrainTile newTile = new TerrainTile(new Image());
                if(tf.getText()!=null && imageTextField.getText()!=null && !tf.getText().equals("")&& !imageTextField.equals("")) {
                    System.out.println(tf.getText());
                    System.out.println(imageTextField.getText());
                    if(configureImage.getSelectedImage()==null){
                        AlertFactory af = new AlertFactory();
                        af.createAlert("Must choose image");

                    }

                    if(trueButton.isSelected()){
                        TerrainTile newTile = new TerrainTile(configureImage.getSelectedImage(), typeToImageMap);

                        typeToPath.put(tf.getText(),true);
                        newTile.setPath();
                    }
                    else if(falseButton.isSelected()){
                        TerrainTile newTile = new TerrainTile(configureImage.getSelectedImage(), typeToImageMap);

                        typeToPath.put(tf.getText(),false);
                        newTile.setPathFalse();
                    }
                    else{
                        AlertFactory af = new AlertFactory();
                        af.createAlert("Must set tile as path or not!");
                    }
                    if(trueButton.isSelected()||falseButton.isSelected()) {
                        myListView.getItems().add(tf.getText());
                        typeToImageMap.put(tf.getText(), configureImage.getSelectedInteger());
                        popUpWindow.close();
                    }
                }
                else{
                    System.out.println("HELLO");
                    AlertFactory af = new AlertFactory();
                    af.createAlert("One or more fields empty");
                }
            }
        });



        allLayout.getChildren().addAll(nameBox,pathRadio,imageBox,submitButton);


        Scene scene= new Scene(allLayout, SCENE_BOUNDS, SCENE_BOUNDS);
        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();
    }
}
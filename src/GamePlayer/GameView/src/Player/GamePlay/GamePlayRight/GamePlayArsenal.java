package Player.GamePlay.GamePlayRight;

import BackendExternal.Logic;
import BackendExternal.NotEnoughCashException;
import Configs.Info;
import Player.GamePlay.GamePlayLeft.GamePlayMap;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class GamePlayArsenal extends VBox {

    public static final double ARSENAL_RATIO = 1.00;
    public static final double DISPLAY_SECOND_DELAY = 2;
    private static final double DEFAULT_OPACITY = 0.9;
    private static final int IMAGE_SIZE = 100;
    private Logic myLogic;
    private ArrayList<Pair<ImageView, String>> viewList;
    private ListView arsenalDisplay;
    private ImageView selectedImage;
    private ImageView movingImage;
    private GamePlayMap myMap;
    private Group myRoot;
    private Map <String, Integer> weaponMap;
    private Effect defaultEffect;
    private double defaultOpacity;
    private Map<Integer, Info> myArsenal;

    GamePlayArsenal(double arsenalWidth, double arsenalHeight, Logic logic, GamePlayMap map, Group root) {
        myLogic = logic;
        myMap = map;
        myRoot = root;
        arsenalDisplay = new ListView();
        arsenalDisplay.setPrefHeight(arsenalHeight * ARSENAL_RATIO);
        arsenalDisplay.setPrefWidth(arsenalWidth);
        myArsenal = logic.getMyArsenal();
        viewList = new ArrayList<>();
        weaponMap = new HashMap<>();
        defaultOpacity = myMap.getOpacity();
        setArsenalDisplay(myArsenal);
        arsenalDisplay.setPrefHeight(arsenalHeight * ARSENAL_RATIO);
        arsenalDisplay.setPrefWidth(arsenalWidth);
        getChildren().addAll(arsenalDisplay);
    }

    private void setArsenalDisplay(Map<Integer, Info> arsenal) {
        arsenalDisplay.setCellFactory(viewList -> new ImageCell());
        for (Integer id: arsenal.keySet()) {
            arsenalDisplay.getItems().add(loadImageWithCaption(myArsenal.get(id).getImage(),
                    myArsenal.get(id).getName(), weaponMap, id));
        }
        arsenalDisplay.setOnDragDetected(mouseEvent -> dragDetected(mouseEvent));
        myMap.setOnDragOver(event -> dragOver(event));
        myMap.setOnDragEntered(event -> dragEntered(event));
        myMap.setOnDragExited(event -> dragExited(event));
        myMap.setOnDragDropped(event -> dragDropped(event));
    }

    void recreateArsenal(){
        setArsenalDisplay(myLogic.getMyArsenal());
    }

    private void displayNotEnoughCash(String message){
        Stage cashDisplay = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root);
        cashDisplay.setScene(scene);
        Text cash = new Text(message);
        root.getChildren().add(cash);
        PauseTransition delay = new PauseTransition(Duration.seconds(DISPLAY_SECOND_DELAY));
        delay.setOnFinished(event -> cashDisplay.close() );
        delay.play();
    }

    private void dragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            myRoot.getChildren().remove(movingImage);
            if (myLogic.checkPlacementLocation(weaponMap.get(selectedImage.toString()), event.getX(), event.getY(), 0)) {
                try {
                    myRoot.getChildren().add((myLogic.instantiateWeapon(weaponMap.get(selectedImage.toString()), event.getX(),event.getY(), 0)).getAsNode());
                }catch (NotEnoughCashException e){
                    displayNotEnoughCash(e.getMessage());
                }
            }
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void dragExited(DragEvent event){
        myMap.setOpacity(defaultOpacity);
        getChildren().removeAll();
        selectedImage.setEffect(defaultEffect);
        event.consume();
    }

    private void dragEntered(DragEvent event){
        if (event.getGestureSource() != myMap &&
                event.getDragboard().hasString()) {
            myMap.setOpacity(DEFAULT_OPACITY);
        }
        event.consume();
    }

    private void dragOver(DragEvent event){
        movingImage.setTranslateX(event.getX());
        movingImage.setTranslateY(event.getY());
        if (event.getGestureSource() != myMap ) {
            Lighting lighting = new Lighting();
            lighting.setDiffuseConstant(1.0);
            lighting.setSpecularConstant(0.0);
            lighting.setSpecularExponent(0.0);
            lighting.setSurfaceScale(0.0);
            if (myLogic.checkPlacementLocation(weaponMap.get(selectedImage.toString()), event.getX(), event.getY(), 0)) {
                lighting.setLight(new Light.Distant(45, 45, Color.GREEN));
            }
            else{
                lighting.setLight(new Light.Distant(45, 45, Color.RED));
            }
            movingImage.setEffect(lighting);
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    private void dragDetected(MouseEvent mouseEvent){
        selectedImage = (ImageView)((Pair) arsenalDisplay.getSelectionModel().getSelectedItem()).getKey();
        Dragboard db = selectedImage.startDragAndDrop(TransferMode.ANY);
        defaultEffect = selectedImage.getEffect();
        var imageCopy = selectedImage.getImage();
        movingImage = new ImageView();
        movingImage.setImage(copyImage(imageCopy));
        movingImage.setFitWidth(myMap.getGridSize());
        movingImage.setFitHeight(myMap.getGridSize());
        myRoot.getChildren().add(movingImage);
        ClipboardContent content = new ClipboardContent();
        content.putString(selectedImage.toString());
        db.setContent(content);
        mouseEvent.consume();
    }

    private WritableImage copyImage(Image imageCopy){
        PixelReader pixelReader = imageCopy.getPixelReader();
        int width = (int)imageCopy.getWidth();
        int height = (int)imageCopy.getHeight();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Color color = pixelReader.getColor(x, y);
                pixelWriter.setColor(x, y, color);
            }
        }
        return writableImage;
    }

    private static class ImageCell extends ListCell<Pair<ImageView, String>> {
        @Override
        public void updateItem(Pair<ImageView, String> item, boolean empty) {
            super.updateItem(item, empty);
            if(!empty) {
                setGraphic(item.getKey());
                Tooltip.install(this, new Tooltip(item.getValue()));
            }
        }
    }

    private Pair<ImageView, String> loadImageWithCaption(int fileId, String caption, Map <String,
            Integer> weaponMap, Integer id) {
        try {
            var image = new ImageView(myLogic.getImage(fileId));
            weaponMap.put(image.toString(), id);
            image.setFitWidth(IMAGE_SIZE);
            image.setFitHeight(IMAGE_SIZE);
            return new Pair<>(image, caption);
        }
        catch(Exception e){
            myRoot.getChildren().add(new Text(e.getMessage()));
        }
        return null;
    }
}

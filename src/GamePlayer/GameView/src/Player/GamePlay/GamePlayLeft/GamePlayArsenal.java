package Player.GamePlay.GamePlayLeft;

import BackendExternal.Logic;
import Configs.Info;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Popup;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class GamePlayArsenal extends VBox {

    public static final double ARSENAL_RATIO = 0.90;
    public static final double SELECTOR_RATIO = 0.10;

    private Logic myLogic;
    private GamePlayArsenalSelector myArsenalSelector;
    private boolean isWeapon;
    private ArrayList<Pair<ImageView, String>> viewList;
    private ListView arsenalDisplay;
    private double myArsenalWidth;
    private HBox arsenalSelector;
    private ImageView selectedImage;
    private ImageView movingImage;
    private GamePlayMap myMap;
    private Group myRoot;
    private Map <String, Integer> weaponMap;

    //list of WeaponInfo objects which has ID and an imageview
    private Map<Integer, Info> myArsenal;

    public GamePlayArsenal(double arsenalWidth, double arsenalHeight, Logic logic, GamePlayMap map, Group root) throws FileNotFoundException {
        myArsenalWidth = arsenalWidth;
        isWeapon = true;
        myLogic = logic;
        myMap = map;
        myArsenal = logic.getMyArsenal();

        System.out.println(weaponMap);
        myRoot = root;
        arsenalDisplay = new ListView();
        arsenalDisplay.setPrefHeight(arsenalHeight * ARSENAL_RATIO);
        arsenalDisplay.setPrefWidth(arsenalWidth);

        viewList = new ArrayList<>();
        setArsenalDisplay(myArsenal,arsenalWidth);

        arsenalDisplay.setPrefHeight(arsenalHeight * ARSENAL_RATIO);
        arsenalDisplay.setPrefWidth(arsenalWidth);
        getChildren().addAll(arsenalDisplay);

        myArsenalSelector = new GamePlayArsenalSelector(arsenalWidth,arsenalHeight * SELECTOR_RATIO);
        getChildren().add(myArsenalSelector);
    }

    private void setArsenalDisplay(Map<Integer, Info> arsenal, double arsenalWidth) {
        try {
            //creates internal mapping of weapon and id
            arsenalDisplay.setCellFactory(viewList -> new ImageCell());
            weaponMap = new HashMap<>();
            for (Integer id: arsenal.keySet()) {
                arsenalDisplay.getItems().add(loadImageWithCaption(myArsenal.get(id).getImage(),
                        myArsenal.get(id).getName(), weaponMap, id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        arsenalDisplay.setOnDragDetected(mouseEvent -> dragDetected(mouseEvent));
        myMap.setOnDragOver(event -> dragOver(event));
        myMap.setOnDragEntered(event -> dragEntered(event));
        myMap.setOnDragExited(event -> dragExited(event));
        myMap.setOnDragDropped(event -> dragDropped(event));

    }

//    private void switchWeaponDisplay(){
//        if (!isWeapon) {
//            //TODO: implement display switch
//            viewList.clear();
//            setArsenalDisplay(myTestWeapons, myArsenalWidth);
//            isWeapon = true;
//        }
//    }
//
//    private void switchObstacleDisplay(){
//        if (isWeapon) {
//            //TODO: implement display switch
//            viewList.clear();
//            setArsenalDisplay(myTestObstacles, myArsenalWidth);
//            isWeapon = false;
//        }
//    }

    private void dragDropped(DragEvent event){
        System.out.println("inside drop");
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            myRoot.getChildren().remove(movingImage);
            System.out.println("drag dropped");
            myRoot.getChildren().add((myLogic.instantiateWeapon(weaponMap.get(selectedImage.toString()), event.getX(),event.getY(), 0)).getAsNode());

            //if is always false
            if (myLogic.checkPlacementLocation(weaponMap.get(selectedImage.toString()), event.getX(), event.getY(), 0)) {
                System.out.println("location valid");
                myRoot.getChildren().add((myLogic.instantiateWeapon(weaponMap.get(selectedImage.toString()), event.getX(),event.getY(), 0)).getAsNode());
            }

            System.out.println("Image: " + selectedImage.toString());
            System.out.println("X: " + event.getX());
            System.out.println("Y: " + event.getY());
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void dragExited(DragEvent event){
        System.out.println("drag exited");
        event.consume();
    }

    private void dragEntered(DragEvent event){
        if (event.getGestureSource() != myMap &&
                event.getDragboard().hasString()) {
            System.out.println("drag entered");
        }
        event.consume();
    }

    private void dragOver(DragEvent event){
        movingImage.setTranslateX(event.getX());
        movingImage.setTranslateY(event.getY());
        if (event.getGestureSource() != myMap ) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    private void dragDetected(MouseEvent mouseEvent){
        selectedImage = (ImageView)((Pair) arsenalDisplay.getSelectionModel().getSelectedItem()).getKey();
        Dragboard db = selectedImage.startDragAndDrop(TransferMode.ANY);

        //creates deepcopy of imageview
        var imageCopy = selectedImage.getImage();
        PixelReader pixelReader = imageCopy.getPixelReader();

        int width = (int)imageCopy.getWidth();
        int height = (int)imageCopy.getHeight();

        //Copy from source to destination pixel by pixel
        WritableImage writableImage
                = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Color color = pixelReader.getColor(x, y);
                pixelWriter.setColor(x, y, color);
            }
        }

        movingImage = new ImageView();
        movingImage.setImage(writableImage);
        movingImage.setFitWidth(myMap.getGridSize());
        movingImage.setFitHeight(myMap.getGridSize());

        myRoot.getChildren().add(movingImage);
        /* Put a string on a dragboard */
        ClipboardContent content = new ClipboardContent();
        content.putString(selectedImage.toString());
//                content.put(DataFormat.IMAGE,selectedImage);
        db.setContent(content);
        mouseEvent.consume();
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

    private static Pair<ImageView, String> loadImageWithCaption(String filename, String caption, Map <String,
            Integer> weaponMap, Integer id) {
        try {
            var image = new ImageView(new Image(new FileInputStream("resources/" + filename)));
            weaponMap.put(image.toString(), id);
            image.setFitWidth(100);
            image.setFitHeight(100);
            return new Pair<>(image, caption);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

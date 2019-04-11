package Player;

import BackendExternal.Logic;
import Configs.ImmutableImageView;
import Configs.MapPackage.MapConfig;
import Configs.TransferImageView;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GamePlayMap extends GridPane {
    private Logic myLogic;
    private List<ImmutableImageView> terrainList;
    private Group mapRoot;
    public static final String WEAPON_IMAGE = "wood.jpg";
    public static final String GRASS_IMAGE = "grassplay.jpg";
    public static final String RESOURCES_PATH = "resources/";
    //TEST TERRAIN
    private List<ImmutableImageView> testTerrain = new ArrayList<ImmutableImageView>();
    private List<ImmutableImageView> imageToAdd;
    private List<ImmutableImageView> imageToRemove;
    private ImageView imageView;


    public GamePlayMap(double width, double height, Logic logic) {
        myLogic = logic;
        //returns ImmutableImageViewList

        //TODO: uncomment when we have data that works
//        terrainList = myLogic.getLevelTerrain();
//        mapRoot = new Group();
//        terrainList.stream().forEach(img -> mapRoot.getChildren().add(img.getAsNode()));

        mapRoot=new Group();


        createFilledTestTerrain(width, height);
//        createSquareTestTerrain(width, height);

        System.out.println(testTerrain.size());
//        testTerrain.stream().forEach(img -> {
//            getChildren().add(img.getAsNode());
//        });

        setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        //TODO: not sure if this works yet
//        terrainList.forEach(terrainNode -> mapRoot.getChildren().add(terrainNode));
        setPrefWidth(width);
        setPrefHeight(height);
        System.out.println("Daddy: " + width);
        System.out.println("Chill: " + height);
        //for hardocded animation
        createImagesForHardCode();
    }
    // for hardcoded animation
    private void createImagesForHardCode(){
        Image image = null;
        try {
            image = new Image(new FileInputStream(RESOURCES_PATH + "balloon.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView = new ImageView(image);
    }

    public void update(double elapsedTime){
        //commenting out logic to hardcode animation
//        myLogic.update(elapsedTime);
//        imageToAdd = myLogic.getObjectsToAdd();
//        imageToRemove = myLogic.getObjectsToRemove();
//        //TODO: third method to move obejcts?
//        imageToRemove.stream().forEach(img -> mapRoot.getChildren().remove(img.getAsNode()));
//        imageToAdd.stream().forEach(img -> mapRoot.getChildren().add(img.getAsNode()));

        //hardcoded animation
        if(!mapRoot.getChildren().contains(imageView)){
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setLayoutX(100);
            mapRoot.getChildren().add(imageView);
        }
        imageView.setLayoutX(imageView.getX() + 10);
    }

    //NOT yet used
    private void createFilledTestTerrain(double width, double height){
        for (int i = 0; i < 20; i++) {

            for(int j = 0;j<20;j++) {
//                Image test = new Image(getClass().getResourceAsStream("/resources/"+WEAPON_IMAGE));
                try {
                    Image image = new Image(new FileInputStream("resources/grass.jpg"));
//                Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("grass.jpg"));
                    TransferImageView iv = new TransferImageView(image);
                    iv.setFitWidth(width/20);
                    iv.setFitHeight(height/20);
                    iv.setTranslateX(iv.getFitWidth()*i);
                    iv.setTranslateY(iv.getFitHeight()*j);
                    testTerrain.add(iv);
                }
                catch (FileNotFoundException e) {
                    System.out.println(e);
                }

            }
        }
    }

    private void createSquareTestTerrain(double width, double height){
        for (int i = 0; i < 20; i++) {

            for(int j = 0;j<20;j++) {
//                Image test = new Image(getClass().getResourceAsStream("/resources/"+WEAPON_IMAGE));
                try {
                    Image image = new Image(new FileInputStream("resources/grass.jpg"));
//                Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("grass.jpg"));
                    TransferImageView iv = new TransferImageView(image);
                    iv.setFitWidth(height/20);
                    iv.setFitHeight(height/20);
                    iv.setTranslateX(iv.getFitWidth()*i);
                    iv.setTranslateY(iv.getFitHeight()*j);
                    testTerrain.add(iv);
                }
                catch (FileNotFoundException e) {
                    System.out.println(e);
                }

            }
        }
    }




}

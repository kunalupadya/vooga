package GameAuthoringEnvironment.AuthoringScreen;

import BackendExternalAPI.Model;
import Configs.Configurable;
import Configs.LevelPackage.Level;
import Configs.MapPackage.MapConfig;
import Configs.MapPackage.Terrain;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurableMap extends Application {


    public static final int GRID_WIDTH = 32;
    public static final int GRID_HEIGHT = 20;
    public static final int VBOX_SIZE = 10;
    public static final int IMAGE_WIDTH=20;
    public static final int IMAGE_HEIGHT=20;
    public static final int ENEMY_ENTERING_ANGLE=90;
    public static final int MAP_XPOS=450;
    public static final int MAP_SPACING=50;
    Map<String,Boolean> typeToPath;
    Map<String,Integer> typeToImagePathMap;
    Map<String, Object> passedMap;
    List<TerrainTile> terrainTileList;
    GridPane map;
    private List<TerrainTile> myTerrainTileList;
    private List<Point> exitPointsList = new ArrayList<>();
    private List<Point> enterPointsList = new ArrayList<>();
    private ListView<String> tileView = new ListView<>();
    private ListView<Point> enterPosView, exitPosView;
    private String currentTile = "Grass";
    private String dirtTileImage = "dirt.jpg";
    private String waterTileImage="water.jpg";
    private String grassTileImage="grass.jpg";
    private final int tileViewWidth = 400;
    private final int tileViewHeight = 400;
    private Map<String, Object> myAttributesMap;
    private Stage popUpWindow;
    private String mapName;
    private TextField nameTf;
    private Configurable myLevel;
    private Button nameButton, chooseTileImageButton;
    private MapConfig myAttributesMapConfig;
    private Scene scene;
    private AlertFactory myAlertFactory = new AlertFactory();
    private Model model;

    @Override
    public void start(Stage stage){

    }
    public ConfigurableMap(Map<String, Object> attributeMap, Configurable level){
        super();
        myAttributesMap = attributeMap;
        myLevel = level;
    }

    //Constructor for editing the map
    public ConfigurableMap(MapConfig mapConfig, Map<String, Object> myAttributeMap, Configurable level){
        myAttributesMap = myAttributeMap;
        myLevel = level;
        myAttributesMapConfig = mapConfig;
    }

    public void setConfigurations(){
        initMap();
        addComponentToScreen();
    }

    public void resetConfigurations(){
        reinitMap();
        addComponentToScreen();
    }
    public void initMap() {
        popUpWindow = new Stage();
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Map Editor");

        typeToImagePathMap = new HashMap<>();
        typeToImagePathMap.put("Grass",26);
        typeToImagePathMap.put("Water",27);
        typeToImagePathMap.put("Dirt",23);

        typeToPath = new HashMap<>();
        typeToPath.put("Grass",false);
        typeToPath.put("Water",true);
        typeToPath.put("Dirt",true);
        Image image;
        try {
            java.io.FileInputStream fis = new FileInputStream("resources/" + grassTileImage);
            image = new Image(fis);
            map = new GridPane();

            for (int r = 0; r < GRID_WIDTH; r++) {
                for (int c = 0; c < GRID_HEIGHT; c++) {
                    TerrainTile myTile = new TerrainTile(r, c, image, typeToImagePathMap, typeToPath);
//                    Tooltip tooltip = new Tooltip(myTile.getTileImString());
//                    Tooltip.install(myTile,tooltip);
                    map.setStyle("-fx-background-color: white;");
                    map.add(myTile, r, c);
                    map.setGridLinesVisible(false);
                    //map.add(tBuild.getTile("Grass",r,c,20,20),r,c);
                }

            }
            addGridEvent();
        }
        catch (FileNotFoundException e){
            myAlertFactory.createAlert("Could not find Image File for Default Terrain. Setting to null.");
        }
    }

    private void addComponentToScreen() {
        HBox allLayout = new HBox();


        VBox tileViewBox = createTileView();
        VBox nameBox = createNameBox();
        VBox enterViewBox = createEnterView();
        VBox exitViewBox = createExitView();

        VBox otherLayout = new VBox();
        VBox mapLayout = new VBox();
        mapLayout.setAlignment(Pos.CENTER);
        mapLayout.setSpacing(MAP_SPACING);
        VBox rightLayOut = new VBox();

        VBox mapVBox = new VBox();
        Button submitButton = addSubmit();
        mapVBox.getChildren().addAll(map, submitButton);

        mapVBox.setLayoutX(MAP_XPOS);
        mapLayout.getChildren().add(mapVBox);
        rightLayOut.getChildren().addAll(enterViewBox, exitViewBox);
        rightLayOut.setPrefWidth(ScreenSize.getWidth()/3);
        otherLayout.getChildren().addAll(nameBox, tileViewBox);

        allLayout.getChildren().addAll(otherLayout, mapVBox, rightLayOut);

        scene= new Scene(allLayout, ScreenSize.getWidth(), ScreenSize.getHeight());
        scene.getStylesheets().add("authoring_style.css");
        popUpWindow.setScene(scene);
        popUpWindow.show();
    }
    public VBox createTileView(){

        //tileView.setPrefSize(tileViewWidth, tileViewHeight);
        VBox myBox = new VBox(VBOX_SIZE);

        Label messageLbl = new Label("Select tiles from the given list, click tile on map to change to selected tile type");
        //TODO Change this so that no specific tiles are made(and definitely not just my images)
        tileView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        tileView.getItems().add(0,"Grass");
        tileView.getItems().add(1,"Water");//        this.setOnMouseClicked(new EventHandler<MouseEvent>() {

        tileView.getItems().add(2,"Dirt");
        model = new Model();
        tileView.setCellFactory(param->new ListCell<String>(){
            private ImageView image = new ImageView();
            @Override
            public void updateItem(String name, boolean empty){
                super.updateItem(name,empty);
                if(empty){
                    setText(null);
                    setGraphic(null);
                }
                else{
//                    for(String s : typeToImagePathMap.keySet()) {
//                        try {
//                            image.setFitHeight(20);
//                            image.setFitWidth(20);
//                            image.setImage(new Image(new FileInputStream(typeToImagePathMap.get(s))));
//                        }
//                        catch(FileNotFoundException f){
//                            System.out.println(f);
//                        }
//                    }

                    image.setFitHeight(IMAGE_HEIGHT);
                    image.setFitWidth(IMAGE_WIDTH);
//                        if (name.equals("Grass"))
//                            image.setImage(new Image(new FileInputStream("resources/grass.jpg")));
//                        else if (name.equals("Water"))
//                            image.setImage(new Image(new FileInputStream("resources/water.jpg")));
//                        else if (name.equals("Dirt"))
//                            image.setImage(new Image(new FileInputStream("resources/dirt.jpg")));
                    for(String s : typeToImagePathMap.keySet()){
                        if(name.equals(s)){
                            image.setImage(model.getImage(typeToImagePathMap.get(s)));
                        }
                    }



                    setText(name);
                    setGraphic(image);
                }
            }
        });

        tileView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentTile=tileView.getSelectionModel().getSelectedItem();
            }
        });


        Button addTileImageButton = new Button("Add New Tile");
        addTileImageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //TODO Create Pop up screen that can configure Tile and add that tile to the list of tiles
                ConfigurableTile configurableTile = new ConfigurableTile(tileView,terrainTileList,typeToImagePathMap,typeToPath);

            }
        });

        myBox.getChildren().addAll(messageLbl, tileView, addTileImageButton);
        return  myBox;
    }
    private VBox createNameBox(){
        VBox myNameBox = new VBox(VBOX_SIZE);
        Label mapLbl = new Label("Map Name");
        nameTf = new TextField();
        if(myAttributesMapConfig != null){
            nameTf.setText(myAttributesMapConfig.getName());}
        nameButton = new Button("Confirm");
        nameButton.setOnMouseClicked(this::handleConfirmButton);
        myNameBox.getChildren().addAll(mapLbl, nameTf, nameButton);

        return myNameBox;
    }

    private VBox createEnterView(){
        VBox myVbox = new VBox(VBOX_SIZE);
        Label enterPosLabel = new Label("Enter Positions");
        enterPosView = new ListView();
//        for(Point p : enterPointsList){
//            enterPosView.getItems().add("x:"+p.getX()+" y:"+p.getY());
//        }
        enterPosView.getItems().addAll(enterPointsList);
        myVbox.getChildren().addAll(enterPosLabel, enterPosView);
        return myVbox;
    }

    private VBox createExitView(){
        VBox myVbox = new VBox(VBOX_SIZE);
        Label exitPosLabel = new Label("Exit Positions");
        exitPosView = new ListView();
//        for(Point p:exitPointsList){
//            exitPosView.getItems().add("x:"+p.getX()+" "+"y:"+p.getY());
//        }
        exitPosView.getItems().addAll(exitPointsList);
        myVbox.getChildren().addAll(exitPosLabel, exitPosView);
        return myVbox;
    }

    private void reinitMap(){
        List<Terrain> existingTerrainList = myAttributesMapConfig.getTerrain();
        map = new GridPane();

        for(int r=0; r< GRID_WIDTH; r++){
            for(int c=0; c<GRID_HEIGHT; c++){
                Terrain myTerrain = existingTerrainList.get(r*GRID_WIDTH + c);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream("resources/" + myTerrain.getView().getImage());
                } catch (FileNotFoundException e) {
                    AlertFactory alert = new AlertFactory();
                    alert.createAlert("File Not Found!");
                }
                Image image = new Image(fis);
                TerrainTile myTile = new TerrainTile(r, c, image,typeToImagePathMap,typeToPath);
                map.add(myTile, r, c);
            }
        }
        addGridEvent();

        //map.setLayoutX();
        //map.setLayoutY();
    }


    public void handleConfirmButton(MouseEvent event){
        mapName = nameTf.getText();
    }

    private Button addSubmit(){
        //TODO Refactor
        Button subButton = new Button("Submit Map");
        subButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                nameButton.fireEvent(mouseEvent);
                terrainTileList=new ArrayList<>();
                List<Terrain> tileList = new ArrayList<>();
                for(Node child: map.getChildren()){
                    terrainTileList.add((TerrainTile) child);
                }
                MapConfig m = new MapConfig((Level) myLevel);
                boolean hasPath =false;
                for(TerrainTile t : terrainTileList){
                    if(t.getIsPath()){
                        hasPath=true;
                    }
                    Terrain tile = new Terrain(m, t.getImageId(),(int) t.getY(), (int) t.getX(),t.getIsPath());

                    tileList.add(tile);
                }
                if(hasPath==false){
                    AlertFactory af = new AlertFactory();
                    af.createAlert("Map must have at least one path tile!");
                }


                enterPointsList = new ArrayList<>();
                enterPosView.getItems().stream().forEach(obj->enterPointsList.add(obj));
                if(enterPointsList.size()==0){
                    AlertFactory af = new AlertFactory();
                    af.createAlert("Must have at least one entry point for enemies!");
                }


                exitPointsList = new ArrayList<>();
                exitPosView.getItems().stream().forEach(obj->exitPointsList.add(obj));
                if(exitPointsList.size()==0){
                    AlertFactory af = new AlertFactory();
                    af.createAlert("Must have at least one exit point for enemies!");
                }
                //TODO Need to clean this up
                passedMap=new HashMap<>();
                passedMap.put("myName",mapName);
                passedMap.put("myTerrain",tileList);
                passedMap.put("enemyEnteringGridPosList", enterPointsList);
                passedMap.put("enemyExitGridPosList",exitPointsList);
                passedMap.put("enemyEnteringDirection",ENEMY_ENTERING_ANGLE);


                m.getConfiguration().setAllAttributes(passedMap);
                myAttributesMap.put("myMap", m);

                popUpWindow.close();
            }
        });
        return subButton;
    }


    private void addGridEvent(){

        map.getChildren().forEach(item-> {
            item.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Tooltip toolTip = new Tooltip();
                    toolTip.setText(((TerrainTile)item).getType()+" "+((TerrainTile)item).getPathString());
                    Tooltip.install(item,toolTip);
                }
            });
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    MouseButton button = mouseEvent.getButton();
                    if(button == MouseButton.PRIMARY){
                        updateCellMouse(mouseEvent);

                    }

                    else if(button == MouseButton.SECONDARY){
                        item.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                            @Override
                            public void handle(ContextMenuEvent event) {
                                ContextMenu contextMenu = new ContextMenu();
                                MenuItem menuItem1 = new MenuItem("Set as Enter Pos");
                                menuItem1.setOnAction(new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent t) {
                                        TerrainTile terrainTile = (TerrainTile) item;
                                        terrainTile.setPath();
                                        Point enterPoint = new Point((int)terrainTile.getX(), (int)terrainTile.getY());
                                        enterPosView.getItems().add(enterPoint);
                                        try{
                                            terrainTile.setImage(new Image(new FileInputStream("resources/enter.jpg")));
                                        }
                                        catch(FileNotFoundException f){
                                            AlertFactory af = new AlertFactory();
                                            af.createAlert("Could not load image from database!");
                                        }

                                    }
                                });

                                MenuItem menuItem2 = new MenuItem("Set as Exit Pos");
                                menuItem2.setOnAction(new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent t) {
                                        TerrainTile terrainTile = (TerrainTile) item;
                                        Point exitPoint = new Point((int)terrainTile.getX(), (int)terrainTile.getY());
                                        exitPosView.getItems().add(exitPoint);
                                        terrainTile.setPath();

                                        try{
                                            terrainTile.setImage(new Image(new FileInputStream("resources/exit.jpg")));
                                        }
                                        catch(FileNotFoundException f){
                                            AlertFactory af = new AlertFactory();
                                            af.createAlert("Could not load image from database!");

                                        }
                                    }
                                });
                                contextMenu.getItems().addAll(menuItem1, menuItem2);
                                contextMenu.show(map, event.getScreenX(), event.getScreenY());

                            }
                        });

                    }
                }
            });
            item.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    //System.out.println("DRAGDETECTEDDDD");
                    Dragboard db = item.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(currentTile);
                    db.setContent(content);
                    mouseEvent.consume();
                }
            });
            item.setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent dragEvent) {
                    dragEvent.acceptTransferModes(TransferMode.ANY);
                    updateCell(dragEvent);
                    //System.out.println("DRAGGINGGGGGG");
                }
            });

        });
    }

    public void updateCell(DragEvent mouseEvent){
        TerrainTile source = (TerrainTile) mouseEvent.getSource();
        source.changeImage(currentTile);

    }

    public void updateCellMouse(MouseEvent mouseEvent){
        TerrainTile source = (TerrainTile) mouseEvent.getSource();
        source.changeImage(currentTile);

    }
}

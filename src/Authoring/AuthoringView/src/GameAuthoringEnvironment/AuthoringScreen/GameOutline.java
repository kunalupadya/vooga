package GameAuthoringEnvironment.AuthoringScreen;

import Configs.Configurable;
import Configs.GamePackage.Game;
import Configs.MapPackage.MapConfig;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;;
import javafx.scene.layout.Pane;
import java.util.*;

//TODO Change all magic values

public class GameOutline extends Screen {

    private Pane myContent;
    private int myHeight;
    private int myWidth;
    private TreeView<Configurable> myTreeView = new TreeView<>();
    private Game myGame;
    private GameOutline gameOutline;
    private double IMAGEVIEW_WIDTH = 25;
    private double IMAGEVIEW_HEIGHT=25;

    public ImageView getMyImage(){
        Image test = new Image(getClass().getResourceAsStream("/resources/" +"Folder.png"));
        ImageView imageView = new ImageView(test);
        imageView.setFitWidth(IMAGEVIEW_WIDTH);
        imageView.setFitHeight(IMAGEVIEW_HEIGHT);

        return imageView;
    }

    public GameOutline(int width, int height) {
        super(width, height);
        myTreeView.setPrefHeight(ScreenSize.getHeight());
        gameOutline = this;
        myHeight = height;
        myWidth = width;
        myContent = getContent();
    }


    public void makeTreeView(Game game) {
        myContent.getChildren().remove(myTreeView);
        myGame = game;
        TreeItem<Configurable> myRoot = new TreeItem<>(game);
        createTreeView(myRoot);
        myTreeView.setRoot(myRoot);
        setCellFactory();
        myContent.getChildren().add(myTreeView);
    }

    //recursively creates a treeview - only creates something that has been defined
    private void createTreeView(TreeItem<Configurable> myConfigurable) {
        Map<String, Object> myMap = myConfigurable.getValue().getConfiguration().getDefinedAttributes();

        for (String key : myMap.keySet()) {
            var value = myMap.get(key);
            //value is not an array, just a single object
            if (value instanceof Configurable) {
                try {
                    TreeItem<Configurable> treeItem = new TreeItem<>((Configurable) value);
                    myConfigurable.getChildren().add(treeItem);
                    //don't create  additional treeitem for mymap
                    if(!key.toLowerCase().equals("mymap")){
                        createTreeView(treeItem);
                    }
                } catch (Exception e) {
                    //TODO Handle Error
                    AlertFactory af = new AlertFactory();
                    af.createAlert("Tree View Couldn't be Created");
                }
            //value is an array
            } else if (value.getClass().isArray()) {

                Object[] valueArray = (Object[]) value;
                for (Object object : valueArray) {
                    if(object instanceof Configurable) {
                        TreeItem<Configurable> treeItem = new TreeItem<>((Configurable) object);
                        myConfigurable.getChildren().add(treeItem);
                        createTreeView(treeItem);
                    }
                }
            }
        }
    }

    private void setCellFactory() {

        myTreeView.setCellFactory(tree -> {
            //TODO Set Images Accordingly
            TreeCell<Configurable> cell = new TreeCell<>() {
                @Override
                public void updateItem(Configurable item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                            try {
                                setText(item.getClass().getDeclaredField("DISPLAY_LABEL").get(null) + ": " + item.getName());
                            } catch (IllegalAccessException | NoSuchFieldException e) {
                                //TODO Error checking
                                AlertFactory af = new AlertFactory();
                                af.createAlert("Illegal Access Attempted!");
                            }
                        setGraphic(getMyImage());
                        }

                }
            };
            controlTreeCellMouseClick(cell);
            return cell;
        });
    }


    private void controlTreeCellMouseClick(TreeCell<Configurable> cell) {

        cell.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                Object object = cell.getTreeItem().getValue();
                try {
                    findMyClass(object, myGame);
                } catch (NoSuchFieldException e) {
                    //TODO Error checking
                    AlertFactory af = new AlertFactory();
                    af.createAlert("Attempted Entry into Invalid Field");
                }
            }
        });
    }

    //recursively search the right class
    private void findMyClass(Object myObject, Configurable configurable) throws NoSuchFieldException {

        //if myobject is myGame
        if (myObject.equals(configurable)) {
            showTheScreen((Configurable) myObject);
        }

        Map<String, Object> myMap = configurable.getConfiguration().getDefinedAttributes();

        //System.out.println(myMap);



        for (String key : myMap.keySet()) {
            var value = myMap.get(key);
            //base case
            if (value.equals(myObject)) {
                showTheScreen((Configurable) value);
            }
            // if value configurable recurse!
            else if (!value.getClass().isArray() && value instanceof Configurable) {
                Configurable temp = (Configurable) value;
                findMyClass(myObject, temp);
                // if value is an array
            } else if (value.getClass().isArray()) {

                Object[] valueArray = (Object[]) value;
                for (int b = 0; b < valueArray.length; b++) {
                    if (valueArray[b].equals(myObject)) {
                        showTheScreen((Configurable) valueArray[b]);
                    } else {
                        Configurable myConfigurable = (Configurable) valueArray[b];
                        findMyClass(myObject, myConfigurable);
                    }
                }
            }
        }
    }


   public void showTheScreen(Configurable myConfigurable) throws NoSuchFieldException {

       Map<String, Object> myAttributesMap = new HashMap<>();
       Map<String, Object> definedAttributesMap = myConfigurable.getConfiguration().getDefinedAttributes();

       //Handle MapConfig
       if (myConfigurable.getClass().getSimpleName().toLowerCase().equals("mapconfig")) {
           if (!definedAttributesMap.keySet().equals(null)) {
               MapConfig mapConfig = (MapConfig) myConfigurable;
               Map<String, Object> levelMap = mapConfig.getLevel().getConfiguration().getDefinedAttributes();
               ConfigurableMap configurableMap = new ConfigurableMap(mapConfig, levelMap, ((MapConfig) myConfigurable).getLevel());
               configurableMap.resetConfigurations();
           } else {
               ConfigurableMap configurableMap = new ConfigurableMap(myAttributesMap, myConfigurable);
               configurableMap.setConfigurations();
           }
       }
      /* else if(myConfigurable.getClass().getSimpleName().toLowerCase().contains("behavior")){
           if (!definedAttributesMap.keySet().equals(null)) {

               MapConfig mapConfig = (MapConfig) myConfigurable;
               Map<String, Object> levelMap = mapConfig.getLevel().getConfiguration().getDefinedAttributes();
               ConfigurableMap configurableMap = new ConfigurableMap(mapConfig, levelMap, ((MapConfig) myConfigurable).getLevel());
               configurableMap.resetConfigurations();
           } else {
               ConfigurableMap configurableMap = new ConfigurableMap(myAttributesMap, myConfigurable);
               configurableMap.setConfigurations();
           }
       }

       else if(myConfigurable.getClass().isArray() && myConfigurable.getClass().getComponentType().getSimpleName().toLowerCase().contains("behavior")){
           if (!definedAttributesMap.keySet().equals(null)) {
               MapConfig mapConfig = (MapConfig) myConfigurable;
               Map<String, Object> levelMap = mapConfig.getLevel().getConfiguration().getDefinedAttributes();
               ConfigurableMap configurableMap = new ConfigurableMap(mapConfig, levelMap, ((MapConfig) myConfigurable).getLevel());
               configurableMap.resetConfigurations();
           } else {
               ConfigurableMap configurableMap = new ConfigurableMap(myAttributesMap, myConfigurable);
               configurableMap.setConfigurations();
           }
       }*/
       else {
           GameController gameController = new GameController();
           gameController.createConfigurable(myConfigurable);
       }
   }


}


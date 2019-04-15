package GameAuthoringEnvironment.AuthoringScreen;

import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
//import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

//TODO Change all magic values

public class GameOutline extends Screen {

    private Pane content;
    private int moduleWidth;
    private final TextArea textArea = new TextArea();
    private ImageView myImage;
    private Group myRoot;
    private int defaultLevel = 2;

    public GameOutline(Group root, int width, int height, String moduleName){
        super(root, width, height, moduleName, false);
        myRoot = root;
        content = getContent();
        content.setMaxSize(300, 1000);
        content.setMinSize(300, 1000);
        moduleWidth = getModuleWidth();
        setContent(defaultLevel);
    }



    public void setContent(int numberOfLevels) {

        Image test = new Image(getClass().getResourceAsStream("/ButtonImages/"+"Folder.png"));
        myImage = new ImageView(test);

        //TODO magic numbers should be changed based on the screensize
        myImage.setFitHeight(50);
        myImage.setFitWidth(50);

        //TODO helper should be changed so that it takes in a int parameter(number of levels) and produces same number of level treeitems.
        TreeViewHelper helper = new TreeViewHelper(numberOfLevels);
        ArrayList<TreeItem> levels = helper.getLevels();

        // Create the TreeView
        TreeView treeView = new TreeView();
        // Create the Root TreeItem
        TreeItem rootItem = new TreeItem("Game title");
        // Add children to the root
        rootItem.getChildren().addAll(levels);
        // Set the Root Node
        treeView.setRoot(rootItem);
        treeView.setMinWidth(300);
        treeView.setMaxWidth(300);
        treeView.setMinHeight(1000);
        treeView.setMaxHeight(1000);

        treeView.setCellFactory(tree -> {

            TreeCell<String> cell = new TreeCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty) ;
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        if(super.getTreeItem().getValue().equals("Game title")){
                            setGraphic(myImage);
                        }
                        setText(item);
                    }
                }
            };
            controlTreeCellMouseClick(cell);
            return cell ;
        });

        content.getChildren().addAll(treeView);
    }

    //TODO If new component is added, add another if statement
    private void controlTreeCellMouseClick(TreeCell<String> cell) {

        cell.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
//<<<<<<< HEAD:src/GUI/GameAuthoringEnvironment/AuthoringScreen/Modules/GameOutline.java
//                        //TODO Can change this to reflection
//                        if (cell.getTreeItem().getValue().equals("Map")) {
//                            //System.out.println("map screen created");
//                            createMapScreen();
//                        }
//                        if (cell.getTreeItem().getValue().equals("Arsenals")) {
//                            //System.out.println("Arsenal screen created");
//                            createArsenalScreen();
//                        }
//                        if (cell.getTreeItem().getValue().equals("Enemies")) {
//                            //System.out.println("Enemies screen created");
//                            createEnemiesScreen();
//                        }
//=======
                    //TODO Can change this to reflection
                    if (cell.getTreeItem().getValue().equals("Map")) {
                        //System.out.println("map screen created");
                        //createMapScreen();
                    }
                    if (cell.getTreeItem().getValue().equals("Arsenals")) {
                        //System.out.println("Arsenal screen created");
                        //createArsenalScreen();
                    }
                    if (cell.getTreeItem().getValue().equals("Enemies")) {
                        //System.out.println("Enemies screen created");
                        //createEnemiesScreen();
                    }

                }
            }
        });

    }




}

package GameAuthoringEnvironment.AuthoringScreen;

import Configs.Behaviors.Behavior;
import Configs.Configurable;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.List;

public class ConfigureBehavior extends Application {

    List<Class> myList;
    Map<String, Object> myMap;
    Stage popUpWindow;
    VBox layout;
    Configurable myConfigurable;
    private static final DataFormat Behavior_LIST = new DataFormat("Behavior List");
    private ListView<Class> sourceView = new ListView<>();
    private ListView<Class> targetView = new ListView<>();
    int sourceViewWidth = 250;
    int sourceViewHeight = 250;
    int viewGap = 10;
    private final BooleanProperty dragModeActiveProperty =
            new SimpleBooleanProperty(this, "dragModeActive", true);
    GameController myGameController;
    GameOutline myGameOutline;
    String myKey;
    Class myType;
    List<Class> myList2;
    Object[] selectedBehavior;
    List<Object> tempList;
    boolean myBoolean;
    private int PADDING = 20;
    /*public ConfigureBehavior(GameOutline gameOutline, Configurable configurable, Map<String, Object> attributesMap, List<Class> behaviorList) {
        myGameOutline= gameOutline;
        myConfigurable = configurable;
        myList = behaviorList;
        myMap = attributesMap;
        setContent();
    }*/

    @Override
    public void start(Stage stage){
        popUpWindow = stage;
        popUpWindow.setTitle("Behavior Editor");
        Scene scene= new Scene(layout, 800, 800);
        scene.getStylesheets().add("authoring_style.css");
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(PADDING);
        popUpWindow.setScene(scene);
        popUpWindow.show();
    }
    public ConfigureBehavior(GameController gameController, Configurable configurable, Map<String, Object> attributesMap, List<Class> behaviorList, String key, Class clazz, Boolean isArray) {
        myType = clazz;
        selectedBehavior = (Object[]) Array.newInstance(myType, 0);
        tempList = new ArrayList<>(Arrays.asList(selectedBehavior));
        myKey = key;
        myGameController = gameController;
        myConfigurable = configurable;
        myList = behaviorList;
        myList2 = Collections.unmodifiableList(myList);
        myMap = attributesMap;
        myBoolean = isArray;
        setContent();
    }

    private void setContent() {
        //popUpWindow.initModality(Modality.APPLICATION_MODAL);

        layout = new VBox(10.00);

        Label sourceListLbl = new Label("Available Behaviors: ");
        Label targetListLbl = new Label("Selected Behaviors: ");
        Label messageLbl = new Label("Drag and drop behaviors. Some behaviors require further configuration");


        sourceView.setPrefSize(sourceViewWidth, sourceViewHeight);
        targetView.setPrefSize(sourceViewWidth, sourceViewHeight);


        sourceView.getItems().addAll(myList2);
        sourceView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        targetView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //When clicked, calls createconfigurable again if the behavior is configurable
        targetView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        var selected = targetView.getSelectionModel().getSelectedItem();
                        try {
                            Class<?> cl = Class.forName(selected.getName());
                            Constructor<?> cons = cl.getConstructor(myConfigurable.getClass());
                            var object = cons.newInstance(myConfigurable);
                            // For making outline
                            if(!(myGameOutline == null)){
                                myGameOutline.showTheScreen((Configurable) object);
                            }else{
                                myGameController.createConfigurable((Configurable) object);
                                tempList.add(object);
                            }

                        } catch (Exception e) {
                            AlertFactory af = new AlertFactory();
                            af.createAlert("Configuration Not Completed Properly");
                        }
                    }
                }}
        });

        setCellFactory(sourceView);
        setCellFactory(targetView);
        // Create the GridPane
        GridPane pane = new GridPane();
        pane.setHgap(viewGap);
        pane.setVgap(viewGap);


        Button removeButton = new Button("Remove Behavior");
        removeButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int index = targetView.getSelectionModel().getSelectedIndex();
                if(!(targetView.getItems().size() == tempList.size())){
                    targetView.getItems().remove(index);
                }
                else{
                targetView.getItems().remove(index);
                tempList.remove(index);}
            }
        }));
        pane.addRow(0, messageLbl);
        pane.addRow(1, sourceListLbl, targetListLbl);
        pane.addRow(2, sourceView, targetView);
        pane.addRow(3, removeButton);

        pane.setAlignment(Pos.CENTER);
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(pane);

        Button setButton = new Button("This config completed");
        setButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!myConfigurable.getConfiguration().isConfigurationComplete()){
                    Alert alert = new Alert(Alert.AlertType.NONE);
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("Atrributtes not all filled out");
                    alert.showAndWait();
                }
                else {
                    //TODO Add THE TARGETVIEW LIST TO THE ATTRIBUTES BUT HOW?
                    Object[] ob = (Object[]) Array.newInstance(myType, tempList.size());
                    for(int a=0; a<tempList.size(); a++){
                        ob[a] = tempList.get(a);
                    }
                    if(myBoolean == true){
                    myMap.put(myKey, ob);}
                    else{
                        myMap.put(myKey,ob[0]);
                    }
                    popUpWindow.close();
                }
            }
        }));

        setDragAndDrop(sourceView);
        setDragAndDrop(targetView);

        layout.getChildren().addAll(root, setButton);
    }

    public ListView<Class> getTargetView() {
        return targetView;
    }

    public ListView<Class> getSourceView() {
        return sourceView;
    }

    //TODO This can be refactord to a separate class
    private void setDragAndDrop(ListView listView) {

        listView.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                dragDetected(event, sourceView);
            }
        });

        listView.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                dragOver(event, sourceView);
            }
        });

        listView.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                dragDone(event, targetView, sourceView);
            }
        });
    }



    private void dragDetected(MouseEvent event, ListView listView) {
        // Make sure at least one item is selected
        int selectedCount = listView.getSelectionModel().getSelectedIndices().size();

        if (selectedCount == 0) {
            event.consume();
            return;
        }

        // Initiate a drag-and-drop gesture
        Dragboard dragboard = listView.startDragAndDrop(TransferMode.COPY_OR_MOVE);

        // Put the the selected items to the dragboard
        ArrayList<Class> selectedItems = getSelectedBehavior(listView);

        ClipboardContent content = new ClipboardContent();
        content.put(Behavior_LIST, selectedItems);

        dragboard.setContent(content);
        //Stops any further handling of the event
        event.consume();
    }

    private void dragOver(DragEvent event, ListView listView) {
        // If drag board has an ITEM_LIST and it is not being dragged
        // over itself, we accept the MOVE transfer mode
        Dragboard dragboard = event.getDragboard();

        if (event.getGestureSource() != listView && dragboard.hasContent(Behavior_LIST)) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    @SuppressWarnings("unchecked")
    private void dragDone(DragEvent event, ListView source, ListView target) {

        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();
        boolean dragCompleted = false;

        if (dragboard.hasContent(Behavior_LIST)) {
            ArrayList<Behavior> list = (ArrayList<Behavior>) dragboard.getContent(Behavior_LIST);
            source.getItems().addAll(list);

        }

        TransferMode tm = event.getTransferMode();

        if (tm == TransferMode.MOVE) {
            removeSelectedBehaviors(target);
        }

        event.consume();
    }


    private ArrayList<Class> getSelectedBehavior(ListView listView) {

        ArrayList<Class> list = new ArrayList<>(listView.getSelectionModel().getSelectedItems());

        return list;
    }

    private void removeSelectedBehaviors(ListView listView) {

        List<Class> selectedList = new ArrayList<>();

        for (Object object : listView.getSelectionModel().getSelectedItems()) {
            selectedList.add((Class) object);
        }

        // Clear the selection
        listView.getSelectionModel().clearSelection();
        // Remove items from the selected list
        listView.getItems().removeAll(selectedList);
    }


    private void setCellFactory(ListView listView) {

        listView.setCellFactory(list -> {
            //TODO Set Images Accordingly
            ListCell<Class> cell = new ListCell<>() {
                @Override
                public void updateItem(Class item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item.getSimpleName());
                    }
                }
            };
            //controlTreeCellMouseClick(cell);
            return cell;
        });
    }
}

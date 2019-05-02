package GameAuthoringEnvironment.AuthoringScreen;

import Configs.Configurable;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

public class ExistingConfigurations {

    List<Object> myList;
    List<Object> myTempList;
    ListView myListView;
    Stage popUpWindow;
    VBox layout;
    Configurable myConfigurable;
    GameController myGameController;
    ListView<Configurable> sourceView;
    int sourceViewWidth = 250;
    int sourceViewHeight = 250;

    public ExistingConfigurations(List<Object> templist, ListView listView,  List<Object> configuredObjects) {
        myTempList =templist;
        myListView = listView;
        myList = configuredObjects;
        setContent();

    }

    private void setContent() {
        popUpWindow = new Stage();
        //popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Pre Existing Configurations Editor");
        layout = new VBox(10.00);

        Label DISPLAY_LABEL = new Label("Double Click what you want to use");
        sourceView = new ListView<>();
        sourceView.setPrefSize(sourceViewWidth, sourceViewHeight);
        for(Object e: myList){
            sourceView.getItems().add((Configurable) e);
        }
        sourceView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        sourceView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Configurable selected = (Configurable) sourceView.getSelectionModel().getSelectedItem();
                        myTempList.add(selected);
                        myListView.getItems().add(selected.getName());
                        popUpWindow.close();

                    }
                }}
        });

        sourceView.setCellFactory(list -> {
            ListCell<Configurable> cell = new ListCell<>() {
                @Override
                public void updateItem(Configurable item, boolean empty) {
                    super.updateItem(item, empty) ;
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                            setText(item.getName());
                    }
                }
            };
            //controlTreeCellMouseClick(cell);
            return cell ;
        });

        layout.getChildren().addAll(DISPLAY_LABEL, sourceView );
        Scene scene= new Scene(layout, 800, 800);
        popUpWindow.setScene(scene);
        popUpWindow.show();
    }
}

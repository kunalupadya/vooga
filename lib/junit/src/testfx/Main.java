package testfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    public static final String TITLE = "TestFX Example";
    public static final int SIZE = 300;
    public static final boolean DEBUG = true;


    @Override
    public void start(Stage primaryStage) {
        var root = getRootNode();
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(root, SIZE, SIZE));
        primaryStage.show();
    }

    private Parent getRootNode () {
        var root = new GridPane();
        // for testfx of using nested lookup
        root.getStyleClass().add("grid-pane");
        // for testfx of using ID lookup
        root.setId("pane");
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.BASELINE_CENTER);

        // make some testfx components
        var label = new Label("TEXT GOES HERE");
        label.setId("label");
        var inputField = new TextField();
        inputField.setId("inputField");
        var applyButton = new Button("Apply");
        applyButton.setId("applyButton");
        var slider = new Slider();
        slider.setId("slider");
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(80);
        slider.valueProperty().addListener((o, old, neww) -> updateLabel(neww.toString(), label));
        var combo = new ComboBox<String>();
        combo.setId("combo");
        combo.setItems(FXCollections.observableArrayList("a", "b", "c"));
        combo.valueProperty().addListener((o, old, neww) -> updateLabel(neww, label));
        var picker = new ColorPicker(Color.BLUE);
        picker.setId("picker");
        picker.setOnAction(e -> updateLabel(picker.getValue().toString(), label));

        // cannot use var with lambdas
        EventHandler<ActionEvent> action = e -> updateLabel(inputField.getText(), label);
        inputField.setOnAction(action);
        applyButton.setOnAction(action);

        // add all elements to GUI
        root.add(label, 1, 0);
        root.add(inputField, 1, 1);
        root.add(slider, 1, 2);
        root.add(combo, 1, 3);
        root.add(picker, 1, 4);
        root.add(applyButton, 1, 5);

        return root;
    }

    // simple method to help debug
    private void updateLabel (String text, Label l) {
        l.setText(text);
        if (DEBUG) {
            System.out.println(l.getText());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

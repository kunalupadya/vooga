package GameAuthoringEnvironment.AuthoringScreen;

import Configs.Configurable;
import Configs.GamePackage.Game;
import Configs.MapPackage.MapConfig;
import Configs.View;
import GameAuthoringEnvironment.AuthoringComponents.ConfigureImage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.*;
import java.text.Annotation;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;


public class GameController {

    private GameController myGameController;
    private Game myGame;
    private Map<String, List<Object>> configuredObjects;
    private Properties authoringProps = new Properties();
    private AlertFactory myAlertFactory = new AlertFactory();

    public GameController() {
        myGameController = this;
        myGame = new Game();
        configuredObjects = new HashMap<>();
        try{
             File propFile = new File("./src/Authoring/AuthoringView/resources/authoringvars.properties");
            authoringProps.load(new FileInputStream(propFile.getPath()));
     }catch (Exception e){
            System.out.print("No Properties File Found");
        }
    }

    public Game getMyGame(){
        return myGame;
    }


    public void createConfigurable(Configurable myConfigurable) throws NoSuchFieldException {
        Stage popupwindow = new Stage();
        List<Button> allButton = new ArrayList<>();
        popupwindow.setTitle(myConfigurable.getClass().getSimpleName() + " Property Settings");
        VBox layout = new VBox(10.00);
        layout.setAlignment(Pos.CENTER);
        VBox.setMargin(layout, new Insets(20, 20, 20, 20));
        //recursion
        Map<String, Object> myAttributesMap = displayScreens(myConfigurable, popupwindow, allButton, layout);
        Button setButton =  new ConfigureCompleteButton(myConfigurable, popupwindow, allButton, myAttributesMap).invoke();
        layout.getChildren().add(setButton);
        Scene scene= new Scene(layout, 500, 500);
        scene.getStylesheets().add("authoring_style.css");
        popupwindow.setScene(scene);
        popupwindow.showAndWait();

    }

    //traversing through the tree
    private Map<String, Object> displayScreens(Configurable myConfigurable, Stage popupwindow, List<Button> allButton, VBox layout) throws NoSuchFieldException {
        Map<String, Object> myAttributesMap = new HashMap<>();
        Map<String, Class> attributesMap = myConfigurable.getConfiguration().getAttributes();
        Map<String, Object> definedAttributesMap = myConfigurable.getConfiguration().getDefinedAttributes();

        for (String key : attributesMap.keySet()) {
            var value = attributesMap.get(key);

            //handle booleans
            if(value.equals(boolean.class)){
                handleBooleanField(allButton, layout, myAttributesMap, key, definedAttributesMap);
            }

            //handle special case: require image
            else if(key.toLowerCase().contains("thumbnail") || key.toLowerCase().contains("imagepath")){
                handleImageField(popupwindow, allButton, layout, myAttributesMap, key, value, definedAttributesMap, myConfigurable);

            }
            //handle string and primitives except boolean
            else if(value.equals(String.class)){
                handleString(allButton, layout, myAttributesMap, key, value, definedAttributesMap);
            }

            else if(value.isPrimitive()){
                handlePrimitives(allButton, layout, myAttributesMap, key, value, definedAttributesMap, myConfigurable);
            }

            //handle single object
            else if(!value.isArray()){
                handleSingleObject(myConfigurable, layout, myAttributesMap, key, value, definedAttributesMap);
            }

            //handle Array
            else{
                handleConfigurableArray(myConfigurable, allButton, layout, myAttributesMap, key, value, definedAttributesMap);
                }
            }

        return myAttributesMap;
    }

    private void handlePrimitives(List<Button> allButton, VBox layout, Map<String, Object> myAttributesMap, String key, Class value, Map<String, Object> definedAttributesMap, Configurable myconfigurable){
        Label DISPLAY_LABEL = getLabel(key);
        var nameAndTfBar = new HBox(10);
        nameAndTfBar.getChildren().addAll(DISPLAY_LABEL);

        Label infoLabel = new Label("-");
        Slider mySlider = new Slider();;
        mySlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                DecimalFormat df = new DecimalFormat("#.#");

                String result= df.format(newValue.doubleValue());
                infoLabel.setText("New value: " + result);
            }
        });

        mySlider.setShowTickMarks(true);
        mySlider.setShowTickLabels(true);

        TextField myTextField = new TextField();

        Class clazz = myconfigurable.getClass();
        Field[] aaa = clazz.getDeclaredFields();
        for (int a = 0; a < aaa.length; a++) {

            if (aaa[a].getName().toLowerCase().contains(key.toLowerCase()) && aaa[a].isAnnotationPresent(Configurable.Slider.class)) {
                Configurable.Slider annotation = aaa[a].getAnnotation(Configurable.Slider.class);
                mySlider.setMax(annotation.max());
                mySlider.setMin(annotation.min());
                nameAndTfBar.getChildren().addAll(mySlider, infoLabel);
                }
            else if(aaa[a].getName().toLowerCase().contains(key.toLowerCase())){
                nameAndTfBar.getChildren().add(myTextField);
                }
            }
/*
        if (definedAttributesMap.keySet().contains(key)) {
            //TODO Set the slider
            mySlider.setValue(Double.parseDouble(definedAttributesMap.get(key).toString()));
        }*/

        Button confirmButton = new Button("Confirm");
        nameAndTfBar.getChildren().add(confirmButton);
        confirmButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            //TODO DO Errorchecking/Refactor
            @Override
            public void handle(MouseEvent event) {
                if(nameAndTfBar.getChildren().contains(mySlider)){
                if(value.getName().equals("int")){
                    Integer a = (int)mySlider.getValue();
                    myAttributesMap.put(key, a.intValue());
                }
                else if(value.getName().equals("long")){
                    Long b = (long)mySlider.getValue();
                    myAttributesMap.put(key, b.longValue());
                }
                else{
                    Double c = (double)mySlider.getValue();
                    myAttributesMap.put(key, c.doubleValue());
                    }
                }
                else{
                    if(value.getName().equals("int")){
                        Integer a = Integer.parseInt(myTextField.getText());
                        myAttributesMap.put(key, a.intValue());
                    }
                    else if(value.getName().equals("long")){
                        Long b = Long.parseLong(myTextField.getText());
                        myAttributesMap.put(key, b.longValue());
                    }
                    else{
                        Double c =Double.parseDouble(myTextField.getText());
                        myAttributesMap.put(key, c.doubleValue());
                    }

                }
            }
        }));
        allButton.add(confirmButton);
        layout.getChildren().addAll(nameAndTfBar);
    }



    private void handleString(List<Button> allButton, VBox layout, Map<String, Object> myAttributesMap, String key, Class value, Map<String, Object> definedAttributesMap) {
        //TODO get the label string from the properties file
        Label DISPLAY_LABEL = getLabel(key);
        TextField myTextField = new TextField();
        if (definedAttributesMap.keySet().contains(key)) {
            myTextField.setText(definedAttributesMap.get(key).toString());
        }
        Button confirmButton = new Button("Confirm");

        var nameAndTfBar = new HBox();
        nameAndTfBar.getChildren().addAll(DISPLAY_LABEL, myTextField, confirmButton);
        confirmButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            //TODO DO Errorchecking/Refactor
            @Override
            public void handle(MouseEvent event) {
                    myAttributesMap.put(key, myTextField.getText());
            }
        }));
        allButton.add(confirmButton);
        layout.getChildren().addAll(nameAndTfBar);
    }

    private void handleConfigurableArray(Configurable myConfigurable, List<Button> allButton, VBox layout, Map<String, Object> myAttributesMap, String key, Class value, Map<String, Object> definedAttributesMap) {
        List<Object> tempList = new ArrayList<>();
        String objectLabel = null;
        try {
            objectLabel = value.getComponentType().getDeclaredField("DISPLAY_LABEL").get(null).toString();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            myAlertFactory.createAlert("Incorrect Field Entered! Try making that again");
            handleConfigurableArray(myConfigurable, allButton, layout, myAttributesMap, key, value, definedAttributesMap);
        }
        Label listLabel = new Label("Add new " + objectLabel + " here");
        VBox tempVBOx  = new VBox();
        tempVBOx.setSpacing(10);

        //weapon behavior is a special case
        if(value.getComponentType().getSimpleName().toLowerCase().contains("weaponbehavior")) {
            Button chooseWeaponBehavior = new Button("Choose Weapon Behavior");
            chooseWeaponBehavior.setOnMouseClicked((new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        Class<?> cl = Class.forName(value.getComponentType().getName());
                        Field myField = cl.getDeclaredField("IMPLEMENTING_BEHAVIORS");
                        List<Class> behaviorList = (List<Class>) myField.get(null);
                        ConfigureBehavior configureBehavior = new ConfigureBehavior(myGameController, myConfigurable, myAttributesMap, behaviorList, key, cl, true);
                        configureBehavior.start(new Stage());
                    } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                        myAlertFactory.createAlert("Behavior configured incorrectly. Try that again.");
                        handleConfigurableArray(myConfigurable, allButton, layout, myAttributesMap, key, value, definedAttributesMap);
                    }
                }
            }));
            tempVBOx.getChildren().addAll(listLabel, chooseWeaponBehavior);
            layout.getChildren().add(tempVBOx);
        }
        else{

            ListView sourceView = new ListView<>();

            var buttonBar = new HBox();
            buttonBar.setAlignment(Pos.CENTER);
            buttonBar.setSpacing(10);
            Button addNew = new Button("Add new " + objectLabel);
            Button confirmButton = new Button("Confirm");
            Button removeButton = new Button("Remove");
            Button bringExistingConfigButton = new Button("Use Existing " + objectLabel);

            buttonBar.getChildren().addAll(addNew, confirmButton, removeButton, bringExistingConfigButton);
            bringExistingConfigButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ExistingConfigurations existingConfigurations = new ExistingConfigurations(tempList, sourceView, configuredObjects.get(key));

                }
            }));

            //give pre configured options to the users
            if (definedAttributesMap.keySet().contains(key)) {
                Object[] objects = (Object[]) definedAttributesMap.get(key);
                for (Object object : objects) {
                    tempList.add(object);
                    Configurable temp = (Configurable) object;
                    sourceView.getItems().add(temp.getName());
                }
            }
            addNew.setOnMouseClicked((new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    handleArrayAddnewButton(sourceView, value, myConfigurable, tempList);

                }
            }));
            sourceView.setOnMouseClicked((new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 2) {
                            handleArraySourceView(value, myConfigurable, myAttributesMap, tempList, sourceView, key);
                        }
                    }
                }
            }));
            confirmButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    handleArrayConfirmButton(value, tempList, myAttributesMap, key);
                }
            }));
            removeButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    handleArrayRemoveButton(sourceView, tempList);

                }
            }));
            allButton.add(confirmButton);
            tempVBOx.getChildren().addAll(listLabel, sourceView, buttonBar);
            layout.getChildren().add(tempVBOx);
        }
    }

    private void handleArrayRemoveButton(ListView sourceView, List<Object> tempList) {
        int index = sourceView.getSelectionModel().getSelectedIndex();
        sourceView.getItems().remove(index);
        tempList.remove(index);
    }

    private void handleArrayConfirmButton(Class value, List<Object> tempList, Map<String, Object> myAttributesMap, String key) {
        try {
            Class c = Class.forName(value.getComponentType().getName());
            Object[] ob = (Object[]) Array.newInstance(c, tempList.size());
            for(int a=0; a<tempList.size() ; a++){
                ob[a] = tempList.get(a);
            }
            myAttributesMap.put(key, ob);
            List<Object> newObjects = new ArrayList<>(Arrays.asList(ob));
            if(configuredObjects.get(key) != null){
                configuredObjects.get(key).addAll(newObjects);
            }else{
                configuredObjects.put(key, newObjects);
            }
        }
        catch (ClassNotFoundException e){
            myAlertFactory.createAlert("This array has illegal classes. Please configure it again.");
            handleArrayConfirmButton(value, tempList, myAttributesMap, key);
        }
    }

    private void handleArraySourceView(Class value, Configurable myConfigurable, Map<String, Object> myAttributesMap, List<Object> tempList, ListView sourceView, String key) {
        try {
            Class<?> cl = Class.forName(value.getComponentType().getName());
            if(cl.getSimpleName().contains("Behavior")){
                Field myField = cl.getDeclaredField("IMPLEMENTING_BEHAVIORS");
                List<Class> behaviorList = (List<Class>) myField.get(null);
                ConfigureBehavior configureBehavior = new ConfigureBehavior(myGameController, myConfigurable, myAttributesMap, behaviorList, key, cl, true);
                configureBehavior.start(new Stage());
            }
            else{
                createConfigurable((Configurable) tempList.get(sourceView.getSelectionModel().getSelectedIndex()));
            }

        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            myAlertFactory.createAlert("Please try again! Something went wrong during your configuration ");
            handleArraySourceView(value, myConfigurable, myAttributesMap, tempList, sourceView, key);
            System.out.println(e);

        }
    }

    private void handleArrayAddnewButton(ListView sourceView, Class value, Configurable myConfigurable, List<Object> tempList) {
        //adds to the visual
        sourceView.getItems().add(value.getComponentType().getSimpleName() + (sourceView.getItems().size() + 1));
        try {
            //adds to the list
            Class<?> cl = Class.forName(value.getComponentType().getName());
            Constructor<?> cons = cl.getConstructor(myConfigurable.getClass());
            var object = cons.newInstance(myConfigurable);
            tempList.add(object);

        } catch (Exception  e) {
            myAlertFactory.createAlert("This wasn't able to be added. Please try again.");
            handleArrayAddnewButton(sourceView, value, myConfigurable, tempList);

        }
    }

    private void handleSingleObject(Configurable myConfigurable, VBox layout, Map<String, Object> myAttributesMap, String key, Class value, Map<String,Object> definedAttributesMap) throws NoSuchFieldException {
        Button myButton = null;
        try {
            myButton = new Button("Configure " + value.getDeclaredField("DISPLAY_LABEL").get(null));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //TODO Should refactor
        myButton.setOnMouseClicked((new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {

                try {
                    Class<?> clazz = Class.forName(value.getName());
                    //Special Case: Map
                    if (clazz.getSimpleName().equals("MapConfig")) {
                        if (definedAttributesMap.keySet().contains(key)) {
                            MapConfig mapConfig = (MapConfig) definedAttributesMap.get(key);
                            ConfigurableMap configurableMap = new ConfigurableMap(mapConfig, myAttributesMap, myConfigurable);
                            configurableMap.resetConfigurations();
                        } else {
                            ConfigurableMap configurableMap = new ConfigurableMap(myAttributesMap, myConfigurable);
                            configurableMap.setConfigurations();
                        }

                    //Special case : View because view is being used in multiple places
                    } else if (clazz.getSimpleName().equals("View")) {
                        Constructor<?> cons = clazz.getConstructor(Configurable.class);
                        var object = cons.newInstance(myConfigurable);
                        createConfigurable((Configurable) object);
                        myAttributesMap.put(key, object);
                    //Speical case : Behavior is different since drag and drop is required
                    } else if(clazz.getSimpleName().toLowerCase().contains("behavior")){
                        //multiple behaviors allowed
                            Field myField = clazz.getDeclaredField("IMPLEMENTING_BEHAVIORS");
                            List<Class> behaviorList = (List<Class>) myField.get(null);
                            ConfigureBehavior configureBehavior = new ConfigureBehavior(myGameController, myConfigurable, myAttributesMap, behaviorList, key, clazz, false);
                            configureBehavior.start(new Stage());
                    }
                    //rest should follow this
                    else {
                        Constructor<?> cons = clazz.getConstructor(myConfigurable.getClass());
                        var object = cons.newInstance(myConfigurable);
                        createConfigurable((Configurable) object);
                        myAttributesMap.put(key, object);
                    }

                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                    myAlertFactory.createAlert("Something went wrong! Please try again");
                    try {
                        handleSingleObject(myConfigurable, layout, myAttributesMap, key, value, definedAttributesMap);
                    }catch (NoSuchFieldException ex){
                        myAlertFactory.createAlert("There is no field like that. Your configuration was not successfully completed.");
                    }
                }

            }
        }));
        layout.getChildren().add(myButton);
    }


    private Label getLabel(String key) {
        if (authoringProps.getProperty(key)==null){
            System.out.println("LABEL NOT DEFINED: "+key);
        }
        return new Label(authoringProps.getProperty(key));
    }

    private void handleImageField(Stage popupwindow, List<Button> allButton, VBox layout, Map<String, Object> myAttributesMap, String key, Class value,  Map<String, Object> definedAttributesMap, Configurable myConfigurable) {
        System.out.println(myConfigurable);
        String imageType = "";
        if(key.toLowerCase().contains("thumbnail")){
            imageType = "THUMBNAIL";
        }
        else{
            View view = (View) myConfigurable;
            if(view.getMyConfigurableName().toLowerCase().contains("enemy")){
                imageType = "";
            }
        }

        Label DISPLAY_LABEL = getLabel(key);
        TextField myTextField = new TextField();
        if (definedAttributesMap.keySet().contains(key)) {
            myTextField.setText(definedAttributesMap.get(key).toString());
        }
        Button chooseImageButton = new Button("Choose Image");
        Button confirmButton = new Button("Confirm");

        var nameAndTfBar = new HBox();
        nameAndTfBar.setAlignment(Pos.CENTER);
        nameAndTfBar.getChildren().addAll(DISPLAY_LABEL, myTextField, chooseImageButton, confirmButton);
        chooseImageButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            //TODO(Louis) Change this so that image is called in from the server
            @Override
            public void handle(MouseEvent event) {
                ConfigureImage configureImage = new ConfigureImage(myTextField, "");
            }
        }));

        confirmButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                myAttributesMap.put(key, Integer.parseInt(myTextField.getText()));
            }
        }));

        allButton.add(confirmButton);
        layout.getChildren().addAll(nameAndTfBar);
    }

    private void handleBooleanField(List<Button> allButton, VBox layout, Map<String, Object> myAttributesMap, String key, Map<String, Object> definedAttributesMap) {
        HBox box = new HBox(10);
        Label DISPLAY_LABEL = getLabel(key);
        RadioButton trueButton = new RadioButton("True");
        RadioButton falseButton = new RadioButton("False");
        if (definedAttributesMap.keySet().contains(key)) {
            if (definedAttributesMap.get(key).equals(true)) {
                trueButton.setSelected(true);
            } else {
                falseButton.setSelected(true);
            }
        }
        Button confirmButton = new Button("Confirm");
        box.getChildren().addAll(DISPLAY_LABEL, trueButton, falseButton);
        confirmButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    if(trueButton.isSelected()) {
                        myAttributesMap.put(key, true);
                    }
                    else {
                        myAttributesMap.put(key, false);
                    }

            }
        }));
        allButton.add(confirmButton);
        layout.getChildren().add(box);
    }


}

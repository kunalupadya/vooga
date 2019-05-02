package GameAuthoringEnvironment.AuthoringComponents;

import BackendExternalAPI.Model;
import Configs.Configurable;
import ExternalAPIs.AuthoringData;
import GameAuthoringEnvironment.AuthoringScreen.GameController;
import GameAuthoringEnvironment.AuthoringScreen.ScreenSize;
import GameAuthoringEnvironment.AuthoringScreen.ImageUploader;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ConfigureImage {
    private Label configuredPrompt;
    private Image selectedImage;
    private Integer selectedInteger;
    List<Class> myList;
    Map<String, Object> myMap;
    Stage popUpWindow;
    VBox layout;
    Configurable myConfigurable;
    GameController myGameController;
    List<Integer> allImages;
    TextField myTextField;
    AuthoringData.ImageType myImageType;

    public ConfigureImage(TextField textField, String s){
        myTextField = textField;
        myImageType = AuthoringData.ImageType.valueOf(s.toUpperCase());
        setContent();

    }
    private void addImageID(Integer integer, ImageView imageView, VBox promptSide){
        if(promptSide.getChildren().size() > 1){
            promptSide.getChildren().remove(1);
        }
        promptSide.getChildren().add(imageView);
        selectedInteger = integer;
        myTextField.setText(Integer.toString(integer));
        configuredPrompt.setText("Your Image has been Configured!");
    }
    public Image getSelectedImage(){
        return selectedImage;
    }
    public Integer getSelectedInteger(){
        return selectedInteger;
    }

    private void setContent(){
        configuredPrompt = new Label("");
        VBox promptSide = new VBox();
        promptSide.setPrefWidth(400);
        promptSide.getChildren().add(configuredPrompt);
        promptSide.setAlignment(Pos.CENTER);
        configuredPrompt.setFont(Font.font("Veranda", FontWeight.BOLD, 20));
        popUpWindow = new Stage();
        popUpWindow.setTitle("Image Editor");
        layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        Label databaseLabel = new Label("Images from our Database");
        databaseLabel.setFont(Font.font("Veranda", FontWeight.BOLD, 10));
        Model model = new Model();

        //TODO CHANGE This
        //List<Integer> allImages = model.getImageOptions(AuthoringData.ImageType.TERRAIN);
        List<Integer> allImages = model.getImageOptions(myImageType);
        List<Image> allImagesList = new ArrayList<>();
        for(int a=0; a< allImages.size(); a++) {
            allImagesList.add(model.getImage(allImages.get(a)));
        }
        for(int x = 0; x< allImages.size(); x ++){
            selectedImage = allImagesList.get(x);
            ImageView imageView = new ImageView(selectedImage);
            ImageView imageView1 = new ImageView(selectedImage);
            imageView1.setFitWidth(ScreenSize.getWidth()/10);
            imageView1.setPreserveRatio(true);
            imageView.setFitWidth(ScreenSize.getWidth()/6);
            imageView.setPreserveRatio(true);
            layout.getChildren().add(imageView);
            Integer id = allImages.get(x);
            imageView.setOnMousePressed(e->addImageID(id, imageView1, promptSide));
        }
        ScrollPane sp = new ScrollPane();
        sp.setPrefWidth(400);
        sp.setContent(layout);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        VBox right = new VBox();
        right.setAlignment(Pos.CENTER);
        Button finished = new Button("Finish");
        Button uploadeImage = new Button("Upload Your Own Image");
        uploadeImage.setOnMouseClicked((new EventHandler<MouseEvent>() {
            //TODO DO Errorchecking/Refactor
            @Override
            public void handle(MouseEvent event) {
                ImageUploader imageUploader = new ImageUploader(model);
            }
        }));
        finished.setOnAction(e->finish());
        promptSide.getChildren().add(finished);
        right.getChildren().addAll(promptSide, finished, uploadeImage);
        hBox.getChildren().addAll(sp, right);
        Scene scene= new Scene(hBox, 800, 800);
        popUpWindow.setScene(scene);
        popUpWindow.show();
    }
    private void finish(){
        this.popUpWindow.close();
    }

}

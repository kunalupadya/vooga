package GameAuthoringEnvironment.AuthoringScreen;

//import BackendExternalAPI.Model;
import ExternalAPIs.Data;
import GameAuthoringEnvironment.AuthoringComponents.ConfigureImage;
import javafx.event.EventHandler;
import BackendExternalAPI.AuthoringBackend;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class TerrainTile extends ImageView {
    private Map<String, Integer> typeToImageMap;
    private Map<String, Boolean> typeToPath;
    private ImageView imageView;
    private boolean isPath;
    private String tileImString;
    private String type;
    private TextField tf;
    private int imageId;
    private ConfigurableMap configurableMap;


    public TerrainTile(int x, int y, Image image, Map<String, Integer> map, Map<String, Boolean> boolMap, ConfigurableMap configurableMap){
        super(image);
        this.setX(x);
        this.setY(y);
        this.setFitWidth(25);
        this.setFitHeight(25);
        this.imageView=new ImageView(image);
        this.type="Grass";
        isPath=false;
        imageId=26;
        //tileImString="resources/grass.jpg";
//        Tooltip tooltip = new Tooltip(tileImString+""+getPathString());
//        Tooltip.install(this,tooltip);
        typeToImageMap=map;
        typeToPath=boolMap;
        tf=new TextField();
        tf.setText("hello");
        this.configurableMap = configurableMap;

    }
    public TerrainTile(Image image,Map<String,Integer> map){
        super(image);
        this.imageView=new ImageView(image);
        this.typeToImageMap=map;
    }





    public void changeImage(String myType){
//        if(type.equals("Grass")){
//            changeToGrass();
//        }
//        else if(type.equals("Water")){
//            changeToWater();
//        }
//        else if(type.equals("Dirt")){
//            changeToDirt();
//        }

        imageId = typeToImageMap.get(myType);
        Image loadedImage;
        if (configurableMap.hasImage(imageId)) {
            loadedImage = configurableMap.getImage(imageId);
        }
        else {
            loadedImage = Data.getImageStatic(imageId);
            configurableMap.addImage(imageId, loadedImage);
        }
        this.setImage(loadedImage);


//        if(!type.equals("Grass")){
//            setPath();
//        }
        setAsPath(typeToPath.get(myType));
        type=myType;
        imageId=typeToImageMap.get(myType);
        //tileImString=typeToImageMap.get(myType);



    }

    public void changeToWater(){
        try {
            this.setImage(new Image(new FileInputStream("resources/water.jpg")));
        }
        catch(FileNotFoundException f){
            System.out.println(f);

        }
        tileImString="water.jpg";
        isPath=true;
        type="Water";

    }
    public void changeToDirt(){
        try {
            this.setImage(new Image(new FileInputStream("resources/dirt.jpg")));
        }
        catch(FileNotFoundException f){
            System.out.println(f);

        }
        tileImString="dirt.jpg";
        isPath=true;
        type="Dirt";
    }



    public void changeToGrass() {
        try {

            this.setImage(new Image(new FileInputStream("resources/grass.jpg")));
        } catch (FileNotFoundException f) {
            //TODO Exception! NO such file found
        }
        tileImString = "grass.jpg";
        isPath=false;
        type="Grass";
    }

    public int getImageId(){
        return imageId;
    }

    public ImageView getImageView(){
        return imageView;
    }

    public String getTileImString(){
        return tileImString;
    }
    public boolean getIsPath(){
        return isPath;
    }
    public void setPath(){
        isPath=true;
    }
    public void setPathFalse(){
        isPath=false;
    }
    public void setAsPath(boolean b){
        isPath=b;
    }
    public String getPathString(){
        if(this.isPath){
            return "Is a path";
        }
        else{
            return "Is not a path";
        }
    }
    public String getType(){
        return type;
    }

}



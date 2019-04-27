package GameAuthoringEnvironment.AuthoringScreen;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TerrainTile extends ImageView {
    ImageView imageView;
    boolean isPath;
    String tileImString;

    public TerrainTile(int x, int y, Image image, String type){
        super(image);
        this.setX(x);
        this.setY(y);
        this.setFitWidth(20);
        this.setFitHeight(20);
        this.imageView=new ImageView(image);
        isPath=false;
        tileImString="grass.jpg";

    }

    public Image getNewImage(String type){
        return new Image(this.getClass().getClassLoader().getResourceAsStream(type.toLowerCase() + ".jpg"));
    }



    /*public void changeTile(TerrainTile terrainTile){
        try {

            this.setImage(new Image(new FileInputStream("resources/" + type.toLowerCase() + ".jpg")));
        }
        catch(FileNotFoundException f){
            //TODO Exception! NO such file found
        }
        tileImString = type.toLowerCase() + ".jpg";
    }*/

    public ImageView getImageView(){
        return imageView;
    }

    public String getTileImString(){
        return tileImString;
    }
    public boolean getIsPath(){
        return isPath;
    }

}



package Configs;

public class View implements Configurable {
    private Configuration myConfiguration;

    private String myLabel;
    @Configure
    private String imagePath;
//    @Configure
//    private String imageName;
    @Configure
    private int width;
    @Configure
    private int height;
    private Configurable myConfigurable;


    public View(Configurable configurableParent) {
        myConfigurable = configurableParent;
        myConfiguration = new Configuration(this);
    }

    //this constructor is for the special case for the terrain blocks in the map
    public View(String file, int width, int height) {
        imagePath = file;
        this.width = width;
        this.height = height;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String getLabel() {
        return myLabel;
    }


    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    //    public View(String imageName, int x, int y, int width, int height){
//        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(imageName));//classloader probably has to be set
//        ImageView myImageView = new TransferImageView(image);
//        myImageView.setX(x);
//        myImageView.setY(y);
//        myImageView.setFitWidth(width);
//        myImageView.setFitWidth(height);
//        myImageView.getFitWidth();
//    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public String getImage() {
        return imagePath;
    }




}

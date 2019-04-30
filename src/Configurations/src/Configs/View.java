package Configs;


public class View implements Configurable {
    private transient Configuration myConfiguration;

    public static final String DISPLAY_LABEL = "Image";
    @Configure
    private int imageId;
    @Slider(min=5, max = 90)
    @Configure
    private int width;
    @Slider(min=5, max = 90)
    @Configure
    private int height;
    private Configurable myConfigurable;


    public View(Configurable configurableParent) {
        myConfigurable = configurableParent;
        myConfiguration = new Configuration(this);
    }

    //this constructor is for the special case for the terrain blocks in the map
    public View(int image, int width, int height) {
        imageId = image;
        this.width = width;
        this.height = height;
    }



    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }


    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }


    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getImage() {
        return imageId;
    }
    public String getMyConfigurableName(){
        return myConfigurable.getClass().getSimpleName();
    }




}

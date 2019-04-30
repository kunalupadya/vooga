package Configs;

/**
 * Object that holds everything about how an object should look when rendered
 */
public class View implements Configurable {
    private transient Configuration myConfiguration;

    public static final String DISPLAY_LABEL = "Image";
    @Configure
    private int imageId;
    @Slider(min=5, max = 95)
    @Configure
    private int width;
    @Slider(min=5, max = 95)
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


    /**
     *
     * @return this parameter
     */
    @Override
    public String getName() {
        return DISPLAY_LABEL;
    }

    /**
     *
     * @return this objects configuration
     */
    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    /**
     *
     * @return this instance variable
     */
    public int getWidth() {
        return width;
    }
    /**
     *
     * @return this instance variable
     */
    public int getHeight() {
        return height;
    }
    /**
     *
     * @return this instance variable
     */
    public int getImage() {
        return imageId;
    }
    /**
     *
     * @return this instance variable
     */
    public String getMyConfigurableName(){
        return myConfigurable.getClass().getSimpleName();
    }




}

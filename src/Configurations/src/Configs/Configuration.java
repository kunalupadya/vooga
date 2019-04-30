/**
 * A class that is held by all objects set by the authoring user that holds the various components for which users define values
 */
package Configs;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.lang.reflect.Field;
import java.util.*;

public class Configuration {
    @XStreamOmitField
    private transient Map<String,Class> myAttributeTypes;
    @XStreamOmitField
    private transient Map<String,Object> myAttributes = new HashMap<>();
    @XStreamOmitField
    private transient boolean isComplete = true;
    @XStreamOmitField
    private transient Configurable myConfigurable;
    @XStreamOmitField
    private transient Class myConfigurableClass;

    public Configuration(Configurable configurable) {
        myConfigurable = configurable;
        myConfigurableClass = configurable.getClass();
    }

    private boolean isAttributesComplete(Map<String,Object> attributeInputs) {
        return attributeInputs.keySet().containsAll(myAttributeTypes.keySet()) && attributeInputs.size()==myAttributeTypes.size();
    }

    private void validateAttributes(Map<String,Object> attributeInputs) throws IllegalArgumentException{
        /*if(!isAttributesComplete(attributeInputs)) {
            throw new IllegalArgumentException();
        }*/
        myAttributes.keySet().stream().forEach(key -> validateType(key,attributeInputs.get(key)));
    }

    private void validateType(String attributeInput, Object value) throws IllegalArgumentException {
        /*if (value.getClass()!=myAttributeTypes.get(attributeInput)) {
            System.out.println(value.getClass());
            System.out.println(myAttributeTypes.get(attributeInput));
            throw new IllegalArgumentException();
        }*/
    }

    /**
     * Allows for the addition of one key-value pair for an attribute
     * @param name
     * @param value
     */
    public void setOneAttribute(String name, Object value) {
        myAttributes.put(name,value);
        validateType(name,value);
        setAttributesInConfigurable();
        if(isAttributesComplete(myAttributes)) isComplete = true;
    }

    /**
     * Sets all the parameters for a given object based on what the authoring environment gets from the user
     * @param attributes
     */
    public void setAllAttributes(Map<String,Object> attributes) {
        validateAttributes(attributes);
//        for (String key:attributes.keySet()) {
//            if(attributes.get(key) instanceof Behavior[]) {
//                attributes.put(key,new BehaviorManager(new ArrayList<>(Arrays.asList(attributes.get(key)))));
//            }
//            if(attributes.get(key) instanceof Wave[]) {
//                attributes.put(key,new WaveSpawner(new ArrayList<>(Arrays.asList((Wave[]) attributes.get(key)))));
//            }
        myAttributes = attributes;
        setAttributesInConfigurable();
        isComplete = true;
    }

    private void setAttributesInConfigurable() throws IllegalStateException{
        for(String key:myAttributes.keySet()) {
            try {
                Field field = myConfigurableClass.getDeclaredField(key);
                field.setAccessible(true);
                field.set(myConfigurable, myAttributes.get(key));
            }
            catch (NoSuchFieldException e) {
                throw new IllegalStateException();
            }
            catch (IllegalAccessException e) {
                throw new IllegalStateException();
            }

        }

    }

    /**
     *
     * @return the map of the types attributes that can be configured for the object in question
     */
    public Map<String, Class>  getAttributes(){
        Map<String, Class> attributes = new LinkedHashMap<>();
        for (Field field: myConfigurableClass.getDeclaredFields()){
            if (field.isAnnotationPresent(Configurable.Configure.class)){
                attributes.put(field.getName(), field.getType());
//                if(myConfigurableClass.getSimpleName().equals("AmmoExpirable")){
//                    System.out.println(field.getType());
//                }
            }
        }
        myAttributeTypes = attributes;
        return Collections.unmodifiableMap(attributes);
    }

    /**
     * returns the state o the configuration to ensure validation
     * @return
     */
    public boolean isConfigurationComplete() {
        return isComplete;
    }

    /**
     *
     * @return a map with the user-defined attributes for the object that holds this configuration
     *
     */
    public Map<String,Object> getDefinedAttributes() throws IllegalStateException {
       /* if (!isComplete) throw new IllegalStateException();*/
        return myAttributes;
    }

}

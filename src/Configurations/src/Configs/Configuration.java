package Configs;

//import Configs.Behaviors.BehaviorManager;
import Configs.Waves.WaveConfig;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.lang.reflect.Field;
import java.util.*;

public class Configuration {
    @XStreamOmitField
    private Map<String,Class> myAttributeTypes;
    @XStreamOmitField
    private Map<String,Object> myAttributes = new HashMap<>();
    @XStreamOmitField
    private boolean isComplete = false;
    @XStreamOmitField
    private Configurable myConfigurable;
    @XStreamOmitField
    private Class myConfigurableClass;

    public Configuration(Configurable configurable) {
        myConfigurable = configurable;
        myConfigurableClass = configurable.getClass();
    }

    private boolean isAttributesComplete(Map<String,Object> attributeInputs) {
//        return attributeInputs.keySet().containsAll(myAttributeTypes.keySet()) && attributeInputs.size()==myAttributeTypes.size();
        return true;
    }

    private void validateAttributes(Map<String,Object> attributeInputs) {
        if(!isAttributesComplete(attributeInputs)) {
            throw new IllegalArgumentException();
        }
        myAttributes.keySet().stream().forEach(key -> validateType(key,attributeInputs.get(key)));
    }

    private void validateType(String attributeInput, Object value) {
       /* if (value.getClass()!=myAttributeTypes.get(attributeInput)) {
            System.out.println(value.getClass());
            System.out.println(myAttributeTypes.get(attributeInput));
            throw new IllegalArgumentException();
        }*/
    }


    public void setOneAttribute(String name, Object value) {
        myAttributes.put(name,value);
        //validateType(name,value);
        setAttributesInConfigurable();
        if(isAttributesComplete(myAttributes)) isComplete = true;
    }

    public void setAllAttributes(Map<String,Object> attributes) {
        validateAttributes(attributes);
//        for (String key:attributes.keySet()) {
//            if(attributes.get(key) instanceof Behavior[]) {
//                attributes.put(key,new BehaviorManager(new ArrayList<>(Arrays.asList(attributes.get(key)))));
//            }
//            if(attributes.get(key) instanceof WaveConfig[]) {
//                attributes.put(key,new WaveSpawner(new ArrayList<>(Arrays.asList((WaveConfig[]) attributes.get(key)))));
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
                System.out.println("1" + myAttributes.get(key));
                throw new IllegalStateException();
            }
            catch (IllegalAccessException e) {
                System.out.println("2" + myAttributes.get(key));
                throw new IllegalStateException();
            }

        }

    }

    public Map<String, Class>  getAttributes(){
        Map<String, Class> attributes = new LinkedHashMap<>();
        for (Field field: myConfigurableClass.getDeclaredFields()){
            if (field.isAnnotationPresent(Configurable.Configure.class)){
                attributes.put(field.getName(), field.getType());
                if(myConfigurableClass.getSimpleName().equals("AmmoExpirable")){
                    System.out.println(field.getType());
                }
            }
        }

        for (Field field: myConfigurableClass.getDeclaredFields()){
            if (field.getName().equals("IMPLEMENTING_BEHAVIORS")){
                // TODO: Why was this causing an error
               // field.get();
                break;
            }
        }
        myAttributeTypes = attributes;
        return Collections.unmodifiableMap(attributes);
    }

    public boolean isConfigurationComplete() {
        //return isComplete;
        return true;
    }//TODO fix this stuff

    public Map<String,Object> getDefinedAttributes() throws IllegalStateException {
        if (!isComplete) throw new IllegalStateException();
        return Collections.unmodifiableMap(myAttributes);
    }
}

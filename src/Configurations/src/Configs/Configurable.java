/**
 * An interface that defines custom annotations that mark components of variables in the game authoring environment to be set by the user.
 */
package Configs;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface Configurable {
    @Retention(RetentionPolicy.RUNTIME)
    @interface Configure{}

    @Retention(RetentionPolicy.RUNTIME)
    @interface Slider{
        double min();
        double max();
    }
/*
returns the configuration associated with this object
 */
    Configuration getConfiguration();

    /**
     * gets the displayname of the object in question
     * @return
     */
    String getName();

}

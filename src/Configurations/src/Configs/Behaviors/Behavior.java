package Configs.Behaviors;

import Configs.Configurable;
import Configs.Updatable;
import Configs.Viewable;

import java.util.List;

/**
 * marker interface
 * @param <T>
 */

public interface Behavior<T> extends Updatable, Configurable {
    List<Class> getBehaviorOptions();
    Behavior copy();
}
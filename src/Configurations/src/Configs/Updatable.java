package Configs;

/**
 * Marker Interface for shared functionality of the update method which cascades down our design hierarchy
 */
public interface Updatable {
    void update(double ms, Updatable parent);
}

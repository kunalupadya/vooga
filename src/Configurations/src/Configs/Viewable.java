package Configs;

/**
 * interface that allows the top level of the gameEngine to fetch Views from objects on the map to pass to the frontend
 */
public interface Viewable {
//    List<ImmutableImageView> getViewsToBeRemoved();
//    List<ImmutableImageView> getViewsToBeAdded();
    View getView();
}

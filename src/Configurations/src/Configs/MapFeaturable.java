package Configs;

import ActiveConfigs.ActiveLevel;
import Configs.MapFeature;

/*
 * An interface that allows objects to be placed on a map, since they will hold a MapFeature object
 */
public interface MapFeaturable {
    /**
     * gives the mapFeature for this object
     * @return
     */
    MapFeature getMapFeature();

    /**
     *
     * @return a reference to the activeLevel associated with this object
     */
    ActiveLevel getActiveLevel();

    /**
     *
     * @param mapFeature the mapFeature with which to initialize the implementing object with during constructor
     */
    void setMyMapFeature(MapFeature mapFeature);

}

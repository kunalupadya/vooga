package Configs;

/**
 * Interface that allows for the shared functionality of the getpossibleshooter method, since every shootable must contain a shooter
 */
public interface Shootable {
    MapFeaturable getPossibleShooter();
}

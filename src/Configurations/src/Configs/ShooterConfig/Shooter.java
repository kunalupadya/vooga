package Configs.ShooterConfig;

import Configs.*;
import Configs.ArsenalConfig.WeaponBehaviors.Shootable;
import Configs.ProjectilePackage.ProjectileConfig;
import Configs.ShooterConfig.ShooterBehaviors.ShooterBehavior;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Shooter implements Updatable , Configurable {

    private Shootable myShootable;

    public static final String DISPLAY_LABEL="Shooter";
    @Configure
    private String myName;
    @Slider(min=0.2,max = 3)
    @Configure
    private double rateOfFire;
    @Configure
    private ProjectileConfig projectileConfig;
    @Configure
    private double shooterRange;
    @Configure
    private ShooterBehavior shooterBehavior;

    private transient Configuration myConfiguration;
    @XStreamOmitField
    private transient int projectilesFired;

    public Shooter(Shootable shootable){
        myShootable = shootable;
        myConfiguration = new Configuration(this);
        projectilesFired = 0;
    }

    public Shooter(Shooter shooter, Shootable shootable){
        shooterRange = shooter.shooterRange;
        myShootable=shootable;
        projectileConfig = shooter.projectileConfig;
        rateOfFire = shooter.rateOfFire;
        shooterBehavior = (ShooterBehavior) shooter.shooterBehavior.copy();
        myName = shooter.myName;
    }

    public void addToProjectilesFired(int shots) {
        projectilesFired+=shots;
    }

    public int getProjectilesFired() {
        return projectilesFired;
    }

    public Shootable getMyShootable() {
        return myShootable;
    }

    public double getShooterRange() {
        return shooterRange;
    }

    public ProjectileConfig getProjectileConfig() {
        return projectileConfig;
    }

    public double getRateOfFire() {
        return rateOfFire;
    }


    @Override
    public String getName() {
        return myName;
    }

    @Override
    public Configuration getConfiguration() {
        return myConfiguration;
    }

    @Override
    public void update(double ms, Updatable parent) {
        shooterBehavior.update(ms, this);
    }
}

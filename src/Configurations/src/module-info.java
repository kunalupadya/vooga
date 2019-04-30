module Configurations {
    requires java.desktop;
    requires javafx.graphics;
    requires java.base;

    requires xstream;
    requires DatabaseUtil;

    opens Configs to xstream;

    exports Configs;
    exports Configs.ArsenalConfig.WeaponBehaviors;
    exports Configs.ArsenalConfig;
    exports Configs.Behaviors;
    exports Configs.EnemyPackage.EnemyBehaviors;
    exports Configs.EnemyPackage;
    exports Configs.GamePackage;
    exports Configs.GamePackage.GameBehaviors;
    exports Configs.LevelPackage.LevelBehaviors;
    exports Configs.LevelPackage;
    exports Configs.MapPackage;
    exports Configs.MapPackage.TerrainBehaviors;
    exports Configs.ProjectilePackage.ProjectileBehaviors;
    exports Configs.ProjectilePackage;
    exports Configs.Waves.WaveBehaviors;
    exports Configs.Waves;
    exports Configs.ShooterConfig;
    exports Configs.ShooterConfig.ShooterBehaviors;
    exports ActiveConfigs;
    exports Configs.ArsenalConfig.WeaponWaveBehaviors;

}

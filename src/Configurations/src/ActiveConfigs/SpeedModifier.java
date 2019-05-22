package ActiveConfigs;

public class SpeedModifier {
    private double endTime;
    private double speedModifier;

    public SpeedModifier(double speedModifier){
        endTime = Integer.MAX_VALUE;
        this.speedModifier = speedModifier;
    }

    public SpeedModifier(double ms, double timeDuration, double speedModifier){
        endTime = ms+timeDuration;
        this.speedModifier = speedModifier;
    }

    public double getEndTime() {
        return endTime;
    }

    public double getSpeedModifier() {
        return speedModifier;
    }
}

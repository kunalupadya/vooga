package ActiveConfigs;

import Configs.Updatable;
import Configs.Waves.Wave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaveSpawner implements Updatable {
    private List<Wave> myWaves;
    private boolean noMoreEnemies;

    public WaveSpawner(Wave[] waves) {
        myWaves = new ArrayList<>(Arrays.asList(waves));
        noMoreEnemies = false;
    }

    @Override
    public void update(double ms, Updatable parent) {
        ActiveLevel activeLevel = (ActiveLevel) parent;
        List<Wave> myWavesToRemove = new ArrayList<>();
        myWaves.stream().forEach(wave -> {
            if(wave.getTimeToReleaseInMs()<=ms) {
                wave.update(ms, this);
            }
            if(wave.isFinished())
                if(!activeLevel.isSurvival()) myWavesToRemove.add(wave);
                else wave.setTimeToReleaseInMs(ms+wave.getTimeToReleaseInMs());
        });
        myWavesToRemove.stream().forEach(wave ->
            myWaves.remove(wave)
        );
        if(myWaves.isEmpty()) noMoreEnemies = true;
    }

    public boolean isNoMoreEnemies() {
        return noMoreEnemies;
    }
}

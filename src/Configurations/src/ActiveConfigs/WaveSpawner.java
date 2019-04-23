package ActiveConfigs;

import Configs.Updatable;
import Configs.Waves.Wave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaveSpawner implements Updatable {
    private List<Wave> myWaves;
    private List<Wave> myWavesToRemove = new ArrayList<>();
    private boolean noMoreEnemies;

    public WaveSpawner(Wave[] waves) {
        myWaves = new ArrayList<>(Arrays.asList(waves));
        noMoreEnemies = false;
    }


    @Override
    public void update(double ms) {
        myWaves.stream().forEach(wave -> {
            if(wave.getTimeToReleaseInMs()<=ms) {
                wave.update(ms);
            }
            if(wave.isFinished()) myWavesToRemove.add(wave);
        });
        myWavesToRemove.stream().forEach(wave ->
            myWaves.remove(wave)
        );
        myWavesToRemove.clear();
        if(myWaves.isEmpty()) noMoreEnemies = true;
    }


    public boolean isNoMoreEnemies() {
        return noMoreEnemies;
    }
}

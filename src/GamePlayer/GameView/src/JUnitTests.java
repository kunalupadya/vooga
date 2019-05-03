import BackendExternal.Logic;
import Configs.GamePackage.Game;
import Configs.GamePackage.GameBehaviors.Lives;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitTests {

    private int validUserID = 7;
    private int validImageID = 7;
    private int validGameID = 3;
    private int validSessionID = 2;

    private String gameString = "test";
    private String description = "Description";
    private String title = "title";
    private byte[] image = this.getClass().getClassLoader().getResource("water.jpg").getFile().getBytes();

    @Test
    public void testLivesNotWorking(){
        Logic myLogic = new Logic(1000,1000);
        Game game = new Game();
        Lives lives = new Lives(game);
        lives.getConfiguration().setOneAttribute("numEnemies", 1);
        game.getConfiguration().setOneAttribute("gameType", lives);
        myLogic.setGameFromAuthoring(game);
        ArrayList<Integer> integers = new ArrayList<>(myLogic.getSpecialParameterToDisplay().values());
        assertEquals((int)integers.get(0), 1);
    }
}

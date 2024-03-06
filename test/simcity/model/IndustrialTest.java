package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.*;

public class IndustrialTest {

    Industrial ind;
    GameEngine ge;

    @Before
    public void setup() {
        Industrial ind = new Industrial();
        assertNotNull(ind);
    }

    @Before
    public void consetup() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        ge = new GameEngine();
        ind = null;
        ind = new Industrial(10, 10, "data/Sprites/Zone/Industrial/Level1/lv1_1.png", ge.getMap(), 1);
        assertNotNull(ind);
    }

    @Test
    public void upgradetest() {
        int cap = ind.maxCapacity;
        int lvl = ind.level;
        ind.level = 1;
        ind.Upgrade();
        assertEquals(cap * 2, ind.maxCapacity);
        assertEquals(lvl + 1, ind.level);

    }
}

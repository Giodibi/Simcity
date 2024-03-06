package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServiceTest {

    Service ser;
    GameEngine ge;

    @Test
    public void testcreation() throws UnsupportedAudioFileException, IOException, InterruptedException, LineUnavailableException {
        ge = new GameEngine();
        assertNotNull(ge);
        ser = new Service(1, 1, "data/Sprites/Zone/Service/lvl1.png", ge.getMap(), 1);
        assertNotNull(ser);
    }

    @Test
    public void upgradetest() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        ge = new GameEngine();
        assertNotNull(ge);
        ser = new Service(1, 1, "data/Sprites/Zone/Service/lvl1.png", ge.getMap(), 1);
        assertNotNull(ser);

        ser.maxCapacity = 2;
        ser.level = 1;
        assertEquals(1, ser.level);
        assertEquals(2, ser.maxCapacity);

        ser.Upgrade();

        assertEquals(4, ser.maxCapacity);
        assertEquals(2, ser.level);

    }

}

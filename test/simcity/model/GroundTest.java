package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.Test;
import static org.junit.Assert.*;

public class GroundTest {

    GameEngine ge;
    Ground g;

    @Test
    public void create() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        ge = new GameEngine();
        assertNotNull(ge);
        g = new Ground(10, 10, ge.getMap(), 1);
        assertNotNull(g);

    }

}

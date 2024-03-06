package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dani
 */
public class ResidentialTest {

    Residential res;
    GameEngine ge;

    @Test
    public void createclass() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        ge = new GameEngine();
        assertNotNull(ge);
        res = new Residential(1, 1, "data/Sprites/Zone/Residential/Level1/yellow/lv1_1.png", ge.getMap(), 1);
        assertNotNull(res);

    }

    @Test
    public void testcalsublevel() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        ge = new GameEngine();
        assertNotNull(ge);
        res = new Residential(1, 1, "data/Sprites/Zone/Residential/Level1/yellow/lv1_1.png", ge.getMap(), 1);
        assertNotNull(res);

        res.people.add(new Person());
        res.people.add(new Person());
        res.people.add(new Person());
        res.people.add(new Person());
        res.people.add(new Person());
        res.people.add(new Person());
        res.people.add(new Person());
        res.people.add(new Person());
        res.people.add(new Person());
        assertEquals(9, res.people.size());
        res.maxCapacity = 9;
        res.calculateSublevel();
        assertEquals(9, res.getSublevel());

    }

    @Test
    public void hasbuilding() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        ge = new GameEngine();
        assertNotNull(ge);
        res = new Residential(1, 1, "data/Sprites/Zone/Residential/Level1/yellow/lv1_1.png", ge.getMap(), 1);
        assertNotNull(res);

        assertEquals(false, res.Building());
        res.hasBuilding = true;
        assertEquals(true, res.Building());

    }

    @Test
    public void upgrade() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        ge = new GameEngine();
        assertNotNull(ge);
        res = new Residential(1, 1, "data/Sprites/Zone/Residential/Level1/yellow/lv1_1.png", ge.getMap(), 1);
        assertNotNull(res);

        assertEquals(1, res.level);
        res.maxCapacity = 2;

        res.Upgrade();

        assertEquals(4, res.maxCapacity);
        assertEquals(2, res.level);
        assertEquals("red", res.getColor());

    }

}

package simcity.model;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Before;
import org.junit.Test;

public class CatastropheTest {

    private Map map;

    @Before
    public void setUp() {
        map = new Map();
    }

    @Test
    public void testRandomCatastrophe() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        Catastrophe catastrophe = new Catastrophe(map, 1);
        assertTrue(catastrophe instanceof Catastrophe);

        Catastrophe randomCatastrophe = catastrophe.getRandomCatastrophe();
        assertNotNull(randomCatastrophe);

        assertTrue(randomCatastrophe instanceof Catastrophe);

        assertTrue(randomCatastrophe instanceof ChemicalDisaster
                || randomCatastrophe instanceof Meteor
                || randomCatastrophe instanceof Monster
                || randomCatastrophe instanceof Fire);

    }

}

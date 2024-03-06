package simcity.model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import simcity.view.Sprite;

public class MapTest {

    public MapTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of calcScreenCoords method, of class Map.
     */
    @Test
    public void testCalcScreenCoords() {
        System.out.println("calcScreenCoords");

        int x = 0;
        int y = 0;
        Map instance = new Map();

        int first = (int) (x * 0.5 * 132 + y * -0.5 * 132) - 132 / 2 + (132 * instance.getSize()) / 2;
        int scnd = (int) (x * 0.25 * 132 + y * 0.25 * 132) - 132 / 2 + 40;

        assertEquals(first, instance.calcScreenCoords(x, y).x);
        assertEquals(scnd, instance.calcScreenCoords(x, y).y);

    }

    /**
     * Test of setTile method, of class Map.
     */
    @Test
    public void testSetTile() throws InterruptedException {
        System.out.println("setTile");
        int x = 0;
        int y = 0;
        Map instance = new Map();
        Sprite s = null;
        try {
            s = new Road(x, y, instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(instance.getMap_array()[x][y], s);

        try {
            s = new Police(x, y, instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(instance.getMap_array()[x][y], s);

        try {
            s = new Stadium(x, y, instance, "test_image.png", 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(instance.getMap_array()[x][y], s);

        try {
            s = new Residential(x, y, "test", instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(instance.getMap_array()[x][y], s);

        try {
            s = new Industrial(x, y, "test", instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(instance.getMap_array()[x][y], s);

        try {
            s = new Service(x, y, "test", instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(instance.getMap_array()[x][y], s);
        LocalDate date = LocalDate.of(2020, 01, 01);
        try {
            s = new Forest(x, y, date, instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(instance.getMap_array()[x][y], s);
    }

    /**
     * Test of getTileIndex method, of class Map.
     */
    @Test
    public void testGetTileIndex() throws InterruptedException {
        System.out.println("getTileIndex");

        int x = 0;
        int y = 0;
        Map instance = new Map();
        Sprite s = null;
        try {
            s = new Road(x, y, instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            s = new Police(x, y, instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(x, instance.getTileIndex(s)[0]);
        assertEquals(y, instance.getTileIndex(s)[1]);
        try {
            s = new Stadium(x, y, instance, "test_image.png", 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(x, instance.getTileIndex(s)[0]);
        assertEquals(y, instance.getTileIndex(s)[1]);
        try {
            s = new Residential(x, y, "test", instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(x, instance.getTileIndex(s)[0]);
        assertEquals(y, instance.getTileIndex(s)[1]);
        try {
            s = new Industrial(x, y, "test", instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(x, instance.getTileIndex(s)[0]);
        assertEquals(y, instance.getTileIndex(s)[1]);
        try {
            s = new Service(x, y, "test", instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(x, instance.getTileIndex(s)[0]);
        assertEquals(y, instance.getTileIndex(s)[1]);
        LocalDate date = LocalDate.of(2020, 01, 01);
        try {
            s = new Forest(x, y, date, instance, 1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(MapTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.setTile(x, y, s);
        assertEquals(x, instance.getTileIndex(s)[0]);
        assertEquals(y, instance.getTileIndex(s)[1]);

    }
}

package simcity.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class GameEngineTest {

    public GameEngineTest() {
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
     * Test of startGame method, of class GameEngine.
     */
    @Test
    public void testStartGame() {
        System.out.println("startGame");
        GameEngine instance = null;
        try {
            instance = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance.startGame();

        assertNotNull(instance.getNewDateTimer());
        assertNotNull(instance.getNewFrameTimer());
        assertNotNull(instance.getDataRefreshTimer());

    }

    /**
     * Test of getFunds method, of class GameEngine.
     */
    @Test
    public void testGetFunds() {
        System.out.println("getFunds");
        GameEngine instance = null;
        try {
            instance = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        int expResult = 100000;
        int result = instance.getFunds();
        assertEquals(expResult, result);

    }

    /**
     * Test of getHappiness method, of class GameEngine.
     */
    @Test
    public void testGetHappiness() {
        System.out.println("getHappiness");
        GameEngine instance = null;
        try {
            instance = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        int expResult = 0;
        int result = instance.getHappiness();
        assertEquals(expResult, result);
    }

    /**
     * Test of isGameOver method, of class GameEngine.
     */
    @Test
    public void testIsGameOver() throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        System.out.println("isGameOver");
        GameEngine instance = null;
        try {
            instance = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertFalse(instance.isGameOver());

        instance.setHappiness(-1);
        assertTrue(instance.isGameOver());

        instance.setHappiness(5);
        instance.setFunds(-200000000);
        assertTrue(instance.isGameOver());

    }

    /**
     * Test of CalcHappiness method, of class GameEngine.
     */
    @Test
    public void testCalcHappiness() {
        System.out.println("CalcHappiness");
        GameEngine instance = null;
        try {
            instance = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance.CalcHappiness();

        assertEquals(0, instance.getHappiness());
    }

    /**
     * Test of setTaxRate method, of class GameEngine.
     */
    @Test
    public void testSetTaxRate() {
        System.out.println("setTaxRate");

        GameEngine instance = null;
        try {
            instance = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance.setTaxRate(0);
        assertEquals(0, instance.getRate());
        assertEquals(500, instance.getRealTax());

        instance.setTaxRate(25);
        assertEquals(25, instance.getRate());
        assertEquals(625, instance.getRealTax());

        instance.setTaxRate(70);
        assertEquals(70, instance.getRate());
        assertEquals(850, instance.getRealTax());

        instance.setTaxRate(-200);
        assertEquals(25, instance.getRate());
        assertEquals(625, instance.getRealTax());

        instance.setTaxRate(500);
        assertEquals(25, instance.getRate());
        assertEquals(625, instance.getRealTax());
    }

    /**
     * Test of calculateIncome method, of class GameEngine.
     */
    @Test
    public void testCalculateIncome() {
        System.out.println("calculateIncome");
        GameEngine instance = null;
        try {
            instance = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance.calculateIncome();

        instance.setCitizens(new ArrayList<Person>());
        instance.calculateIncome();
        assertEquals(0, instance.getIncome());

        Map map = new Map();
        ArrayList<Person> citizens = new ArrayList<>();

        Person p1 = null;

        p1 = new Person();

        instance.setCitizens(new ArrayList<Person>(Arrays.asList(p1)));
        instance.calculateIncome();
        assertEquals(625, instance.getIncome());
    }

    /**
     * Test of calculateExpenses method, of class GameEngine.
     */
    @Test
    public void testCalculateExpenses() {
        System.out.println("calculateExpenses");
        GameEngine instance = null;
        try {
            instance = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance.calculateExpenses();
        assertEquals(100, instance.getExpenses());

        try {
            instance.getMap().setTile(1, 2, new Police(1, 1, instance.getMap(), 1));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        instance.calculateExpenses();
        assertEquals(500, instance.getExpenses());
    }

    /**
     * Test of changeFunds method, of class GameEngine.
     */
    @Test
    public void testChangeFunds() throws UnsupportedAudioFileException, InterruptedException {
        System.out.println("changeFunds");

        GameEngine instance = null;
        try {
            instance = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance.changeFunds(0);
        assertEquals(100000, instance.getFunds());

        instance.changeFunds(1000);
        assertEquals(101000, instance.getFunds());

        instance.changeFunds(-2000);
        assertEquals(99000, instance.getFunds());

    }

    /**
     * Test of setSpeed method, of class GameEngine.
     */
    @Test
    public void testSetSpeed() {
        System.out.println("setSpeed");

        GameEngine instance = null;
        try {
            instance = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance.startGame();
        instance.setSpeed(0);
        assertEquals(0, instance.getSpeed());
        assertFalse(instance.getDataRefreshTimer().isRunning());

        instance.setSpeed(1);
        assertEquals(1, instance.getSpeed());
        assertEquals(30000, instance.getDataRefreshTimer().getDelay());
        assertTrue(instance.getDataRefreshTimer().isRunning());

        instance.setSpeed(2);
        assertEquals(2, instance.getSpeed());
        assertEquals(15000, instance.getDataRefreshTimer().getDelay());
        assertTrue(instance.getDataRefreshTimer().isRunning());

        instance.setSpeed(3);
        assertEquals(3, instance.getSpeed());
        assertEquals(3000, instance.getDataRefreshTimer().getDelay());
        assertTrue(instance.getDataRefreshTimer().isRunning());

        instance.setSpeed(-20);
        assertEquals(1, instance.getSpeed());
        assertEquals(30000, instance.getDataRefreshTimer().getDelay());
        assertTrue(instance.getDataRefreshTimer().isRunning());

        instance.setSpeed(20);
        assertEquals(1, instance.getSpeed());
        assertEquals(30000, instance.getDataRefreshTimer().getDelay());
        assertTrue(instance.getDataRefreshTimer().isRunning());
    }

    /**
     * Test of getDateString method, of class GameEngine.
     */
    @Test
    public void testGetDateString() {
        System.out.println("getDateString");

        GameEngine instance = null;
        try {
            instance = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngineTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals("2020-01-01", instance.getDate().toString());

    }

}

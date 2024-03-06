package simcity.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import simcity.model.GameEngine;

public class StatsMenuTest {

    private GameEngine engine;
    private StatsMenu statsMenu;

    @Before
    public void setUp() {
        try {
            engine = new GameEngine();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(StatsMenuTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        statsMenu = new StatsMenu(engine);
    }

    @Test
    public void succesfulSetUp() {
        assertNotNull(engine);
        assertNotNull(statsMenu);
    }

    @Test
    public void testTaxSlider() {

        statsMenu.setTaxValue(50);
        Assert.assertEquals("50 %", statsMenu.getTaxPercent().getText());

        statsMenu.setTaxValue(0);
        Assert.assertEquals("0 %", statsMenu.getTaxPercent().getText());

        statsMenu.setTaxValue(100);
        Assert.assertEquals("100 %", statsMenu.getTaxPercent().getText());
    }

    @Test
    public void testCityStatsPicker() {

        statsMenu.setCityStatsPickerIndex(0);

        Assert.assertEquals("Happiness", statsMenu.getCityStatsPickerSelected());

        statsMenu.setCityStatsPickerIndex(1);
        Assert.assertEquals("Population", statsMenu.getCityStatsPickerSelected());
    }

    @Test
    public void testSaveButton() {

        statsMenu.setTaxValue(75);
        statsMenu.getSave().doClick();
        Assert.assertEquals(75, engine.getRate());
    }
}

package simcity.view;

import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import simcity.model.GameEngine;

public class StatsPanelTest {

    private GameEngine gameEngine;
    private StatsPanel statsPanel;
    private JPanel panel;

    @Before
    public void setUp() throws Exception {
        gameEngine = new GameEngine();
        statsPanel = new StatsPanel(gameEngine);
        panel = statsPanel.getPanel();
    }

    @Test
    public void testPanel() {
        assertNotNull(panel);
        assertEquals(6, panel.getComponentCount());
        assertTrue(panel.getComponent(0) instanceof JLabel);
        assertTrue(panel.getComponent(1) instanceof Box.Filler);
        assertTrue(panel.getComponent(2) instanceof JLabel);
        assertTrue(panel.getComponent(3) instanceof Box.Filler);
        assertTrue(panel.getComponent(4) instanceof JLabel);
        assertTrue(panel.getComponent(5) instanceof Box.Filler);
        assertTrue(panel.isOpaque() == false);
    }

    @Test
    public void testFundsLabel() {
        JLabel fundsLabel = (JLabel) panel.getComponent(0);
        assertEquals("Funds: " + gameEngine.getFunds() + " $", fundsLabel.getText());
        assertEquals(new Font("arial", Font.PLAIN, 20), fundsLabel.getFont());
    }

    @Test
    public void testPopsLabel() {
        JLabel popsLabel = (JLabel) panel.getComponent(2);
        assertEquals("Pops: " + gameEngine.getPops(), popsLabel.getText());
        assertEquals(new Font("arial", Font.PLAIN, 20), popsLabel.getFont());
    }

    @Test
    public void testHappinessLabel() {
        JLabel happinessLabel = (JLabel) panel.getComponent(4);
        assertEquals("Happiness: " + gameEngine.getHappiness(), happinessLabel.getText());
        assertEquals(new Font("arial", Font.PLAIN, 20), happinessLabel.getFont());
    }

    @Test
    public void testStartTimer() {
        statsPanel.startTimer();
        Timer timer = statsPanel.getTimer();
        assertTrue(timer.isRunning());
        assertEquals(1000, timer.getDelay());
    }

}

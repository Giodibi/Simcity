package simcity.view;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import simcity.model.GameEngine;

public class TimerPanelTest {

    private TimerPanel tp;
    private GameEngine ga;
    private JPanel testPanel;

    @Before
    public void setUp() throws Exception {
        ga = new GameEngine();
        tp = new TimerPanel(ga);
    }

    @Before
    public void setUpPropPanel() throws Exception {
        testPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        JLabel timeLabel = new JLabel("");
        timeLabel.setFont(new Font("arial", Font.PLAIN, 20));

        panel.setOpaque(false);
        testPanel.setOpaque(false);

        panel.add(timeLabel);

        testPanel.add(BorderLayout.EAST, panel);

    }

    @After
    public void tearDown() throws Exception {
        ga = null;
        tp = null;
    }

    @Test
    public void testElapsedTime() {
        /// Wait for 1 second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long elapsedTime = tp.elapsedTime();
        assertTrue(elapsedTime >= 1000 && elapsedTime < 1100);
    }

    @Test
    public void testStartTimer() {
        tp.StartTimer();
        Timer timer = getPrivateField(tp, "timer");
        assertTrue(timer.isRunning());
    }

    @Test
    public void testStopTimer() {
        tp.StartTimer();
        tp.StopTimer();
        Timer timer = getPrivateField(tp, "timer");
        assertFalse(timer.isRunning());
    }

    @Test
    public void testGetPanel() {
        JPanel panel = tp.getPanel();
        assertNotNull(panel);
        assertEquals(testPanel, panel);
    }

    /// Helper method to access private fields
    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Object obj, String fieldName) {
        try {
            java.lang.reflect.Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}

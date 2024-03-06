package simcity.events;

import org.junit.After;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InteractModeTest {

    private InteractMode interactMode;

    @Before
    public void setUp() {
        interactMode = InteractMode.getInstance();
    }

    @Test
    public void testInteractModeInstance() {
        assertNotNull(interactMode);
    }

    @Test
    public void testGetCurrentMode() {
        assertEquals(Mode.NORMAL, interactMode.getCurrentMode());
        assertEquals(BuildItem.NOTHING, interactMode.getCurrentBuildItem());

    }

    @Test
    public void testSetCurrentMode() {
        interactMode.setCurrentMode(Mode.BUILD);
        assertEquals(Mode.BUILD, interactMode.getCurrentMode());
    }

    @Test
    public void testSetCurrentBuildItem() {
        interactMode.setCurrentBuildItem(BuildItem.POLICE);
        assertEquals(BuildItem.POLICE, interactMode.getCurrentBuildItem());
    }

    @Test
    public void testSetIndustryCurrentBuildItem() {
        interactMode.setCurrentBuildItem(BuildItem.INDUSTRIAL);
        assertEquals(BuildItem.INDUSTRIAL, interactMode.getCurrentBuildItem());
    }

    @Test
    public void testSetRESIDENTIALCurrentBuildItem() {
        interactMode.setCurrentBuildItem(BuildItem.RESIDENTIAL);
        assertEquals(BuildItem.RESIDENTIAL, interactMode.getCurrentBuildItem());
    }

    @Test
    public void testSetROADCurrentBuildItem() {
        interactMode.setCurrentBuildItem(BuildItem.ROAD);
        assertEquals(BuildItem.ROAD, interactMode.getCurrentBuildItem());
    }

    @Test
    public void testSetFORESTCurrentBuildItem() {
        interactMode.setCurrentBuildItem(BuildItem.FOREST);
        assertEquals(BuildItem.FOREST, interactMode.getCurrentBuildItem());
    }

    @Test
    public void testSetSERVICECurrentBuildItem() {
        interactMode.setCurrentBuildItem(BuildItem.SERVICE);
        assertEquals(BuildItem.SERVICE, interactMode.getCurrentBuildItem());
    }

    @Test
    public void testSetSTADIUMCurrentBuildItem() {
        interactMode.setCurrentBuildItem(BuildItem.STADIUM);
        assertEquals(BuildItem.STADIUM, interactMode.getCurrentBuildItem());
    }

    @After
    public void aftertest() {
        interactMode.setCurrentBuildItem(BuildItem.NOTHING);
        assertEquals(BuildItem.NOTHING, interactMode.getCurrentBuildItem());
    }

}

package simcity.events;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuildItemTest {

    @Test
    public void testBuildItemEnumValues() {
        BuildItem[] buildItems = BuildItem.values();

        assertEquals(8, buildItems.length);
        assertEquals(BuildItem.NOTHING, buildItems[0]);
        assertEquals(BuildItem.FOREST, buildItems[1]);
        assertEquals(BuildItem.INDUSTRIAL, buildItems[2]);
        assertEquals(BuildItem.POLICE, buildItems[3]);
        assertEquals(BuildItem.RESIDENTIAL, buildItems[4]);
        assertEquals(BuildItem.ROAD, buildItems[5]);
        assertEquals(BuildItem.SERVICE, buildItems[6]);
        assertEquals(BuildItem.STADIUM, buildItems[7]);
    }

}

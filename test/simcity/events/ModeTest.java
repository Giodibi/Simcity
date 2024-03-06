package simcity.events;

import org.junit.Test;
import static org.junit.Assert.*;

public class ModeTest {

    @Test
    public void testEnumValues() {
        Mode[] modes = Mode.values();

        assertEquals(4, modes.length);
        assertEquals(Mode.NORMAL, modes[0]);
        assertEquals(Mode.BUILD, modes[1]);
        assertEquals(Mode.DESTROY, modes[2]);
        assertEquals(Mode.CATASTROPHE, modes[3]);
    }

}

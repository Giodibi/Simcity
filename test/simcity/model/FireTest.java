package simcity.model;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class FireTest {

    @Test
    public void testFireCreation() {
        try {
            Fire fire = new Fire(0, 0, 100, 100, new Map(), 1);

            assertNotNull(fire);

        } catch (Exception e) {
            fail("Exception thrown during Fire creation: " + e.getMessage());
        }
    }
}

package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;

public class MeteorTest {

    @Test
    public void testMeteorCreation() {
        try {

            Meteor meteor = new Meteor(0, 0, 50, 50, new Map(), 1);
            assertNotNull(meteor);

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException | InterruptedException e) {
            fail("Exception thrown during Meteor creation: " + e.getMessage());
        }
    }

}

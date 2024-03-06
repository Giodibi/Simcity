package simcity.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import static org.junit.Assert.assertNotNull;

public class ChemicalDisasterTest {

    @Test
    public void testChemicalDisasterConstructor() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        Map map = new Map();
        ChemicalDisaster chemicalDisaster = new ChemicalDisaster(10, 20, 30, 40, map, 1);
        assertNotNull(chemicalDisaster);
        assertEquals(10, chemicalDisaster.getX());
        assertEquals(20, chemicalDisaster.getY());
    }
}

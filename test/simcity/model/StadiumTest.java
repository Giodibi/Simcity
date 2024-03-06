package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

public class StadiumTest {

    @Test
    public void testIncreaseHappiness() {
        try {

            GameEngine ge = new GameEngine();

            Stadium stadium = new Stadium(2, 2, ge.getMap(), "data/Sprites/Building/Stadium/stadium.png", 1);

            Residential res = new Residential(1, 1, "data/Sprites/Zone/Residential/Level0/residential0.png", ge.getMap(), 1);
            Person person1 = new Person();
            Person person2 = new Person();
            res.people.add(person2);
            res.people.add(person1);
            ge.addPerson(person2);
            ge.addPerson(person1);

            ge.mapSetTile(1, 1, res);

            ge.CalcHappiness();

            assertEquals(10, ge.getHappiness());

            ge.mapSetTile(2, 2, stadium);
            stadium.IncreaseHappiness();
            ge.CalcHappiness();

            assertEquals(40, ge.getHappiness());

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException | InterruptedException e) {
            fail("Exception thrown during Stadium creation: " + e.getMessage());
        }
    }

}

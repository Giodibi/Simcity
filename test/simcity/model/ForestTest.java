package simcity.model;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class ForestTest {

    GameEngine ge;
    LocalDate plantDate = LocalDate.parse("2023-04-10", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    @Before
    public void testMapCreation() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        ge = new GameEngine();
        assertNotNull(ge.getMap());
    }

    @Test
    public void testForestCreation() {
        try {
            ge = new GameEngine();
            Forest forest = new Forest(10, 10, plantDate, ge.getMap(), 1);
            ge.mapSetTile(10, 10, forest);
            assertNotNull(forest);

            assertTrue(ge.getMap().getSpriteFromIndex(10, 10) instanceof Forest);

            // Add additional assertions for specific properties or behavior of the Forest object
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException | InterruptedException | DateTimeParseException e) {
            fail("Exception thrown during Forest creation: " + e.getMessage());
        }
    }

    @Test
    public void testIncreaseHappiness() throws InterruptedException {
        try {
            ge = new GameEngine();
            LocalDate plantDate = LocalDate.parse("2030-04-10", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Forest forest = new Forest(0, 2, plantDate, ge.getMap(), 1);
            Residential res = new Residential(0, 1, "data/Sprites/Zone/Residential/Level0/residential0.png", ge.getMap(), 1);
            Person person1 = new Person();
            Person person2 = new Person();
            res.people.add(person2);
            res.people.add(person1);
            ge.addPerson(person2);
            ge.addPerson(person1);

            ge.mapSetTile(0, 1, res);

            ge.CalcHappiness();

            System.out.println(ge.getHappiness());

            ge.mapSetTile(0, 2, forest);
            forest.IncreaseHappiness(plantDate);

            ge.CalcHappiness();

            System.out.println(ge.getHappiness());

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException | DateTimeParseException e) {
            fail("Exception thrown during testIncreaseHappiness: " + e.getMessage());
        }
    }

    @Test
    public void testAgeing() throws InterruptedException {
        try {
            ge = new GameEngine();
            LocalDate plantDate1 = LocalDate.parse("2033-04-10", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Forest forest = new Forest(0, 1, plantDate, ge.getMap(), 1);
            ge.mapSetTile(0, 1, forest);
            forest.Ageing(plantDate1);
            assertEquals(500, forest.fee);

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException | DateTimeParseException e) {
            fail("Exception thrown during testAgeing: " + e.getMessage());
        }
    }
}

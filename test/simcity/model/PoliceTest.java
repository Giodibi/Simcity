package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import static org.junit.Assert.fail;
import org.junit.Test;

public class PoliceTest {

    @Test
    public void testIncreaseSafety() {
        try {

            GameEngine ge = new GameEngine();

            Police pol = new Police(2, 0, ge.getMap(), 1);

            Residential res = new Residential(1, 0, "data/Sprites/Zone/Residential/Level0/residential0.png", ge.getMap(), 1);
            Road r1 = new Road(0, 1, ge.getMap(), 1);
            Road r2 = new Road(1, 1, ge.getMap(), 1);
            Road r3 = new Road(2, 1, ge.getMap(), 1);
            Road r4 = new Road(3, 1, ge.getMap(), 1);

            Person person1 = new Person();
            Person person2 = new Person();
            res.people.add(person2);
            res.people.add(person1);
            ge.addPerson(person2);
            ge.addPerson(person1);

            ge.mapSetTile(0, 1, res);

            ge.CalcHappiness();

            System.out.println(ge.getHappiness());

            ge.mapSetTile(0, 2, pol);
            pol.IncreaseSafety(501);
            ge.CalcHappiness();

            System.out.println(ge.getHappiness());

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException | InterruptedException e) {
            fail("Exception thrown during Police creation: " + e.getMessage());
        }
    }

}

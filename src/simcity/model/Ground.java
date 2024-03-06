package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import simcity.view.Sprite;

public class Ground extends Sprite {

    public Ground() {
        super();
    }

    public Ground(int x, int y, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(x, y, "data/Sprites/Zone/Residential/Level0/ground_res.png", "data/Sounds/Zone/Residential/Crowd_16.wav", map, speed);
    }

}

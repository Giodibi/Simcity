package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Fire extends Catastrophe {

    public Fire(int x, int y, int width, int height, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, width, height, "data/Sprites/Catastrophe/fire.png", "data/Sounds/Catastrophe/Lightning_16.wav", map, speed);
        if (speed == 1) {
            play("data/Sounds/Catastrophe/Lightning_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Catastrophe/f_Lightning_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Catastrophe/ff_Lightning_16.wav");
        }

    }
}

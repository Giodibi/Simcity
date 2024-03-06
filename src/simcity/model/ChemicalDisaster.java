package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ChemicalDisaster extends Catastrophe {

    public ChemicalDisaster(int x, int y, int width, int height, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, width, height, "data/Sprites/Catastrophe/explosion.png", "data/Sounds/Catastrophe/ChemicalExplosion_16.wav", map, speed);
        if (speed == 1) {
            play("data/Sounds/Catastrophe/ChemicalExplosion_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Catastrophe/f_ChemicalExplosion_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Catastrophe/ff_ChemicalExplosion_16.wav");
        }

    }
}

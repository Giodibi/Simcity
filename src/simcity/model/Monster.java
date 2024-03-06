package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Monster extends Catastrophe {

    public Monster(int x, int y, int width, int height, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, width, height, "data/Sprites/Catastrophe/monster.png", "data/Sounds/Catastrophe/MonsterRoar_16.wav", map, speed);

        if (speed == 1) {
            play("data/Sounds/Catastrophe/MonsterRoar_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Catastrophe/f_MonsterRoar_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Catastrophe/ff_MonsterRoar_16.wav");
        }
    }
}

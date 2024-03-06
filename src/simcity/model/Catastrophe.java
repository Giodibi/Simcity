package simcity.model;

import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import simcity.view.Sprite;

public class Catastrophe extends Sprite {

    public Catastrophe(int x, int y, int width, int height, String image, String soundFile, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(x, y, image, soundFile, map, speed);

    }

    public Catastrophe(int x, int y, int width, int height, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(x, y, "data/Sprites/Catastrophe/fire.png", "data/Sounds/Catastrophe/Lightning_16.wav", map, speed);

    }

    public Catastrophe(Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(0, 0, "data/Sprites/Catastrophe/fire.png", "data/Sounds/Catastrophe/Lightning_16.wav", map, speed);
        Random();
    }

    /**
     * Choose a random Catastrophe category, in random zone
     */
    private void Random() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Random rand = new Random();
        int randIntX = rand.nextInt(10);
        int randIntY = rand.nextInt(10);

        if (randIntY % 2 == 0) {
            setX(randIntX * 128 + 64);
            setY(randIntY * 34);
        } else {
            setX(randIntX * 128);
            setY(randIntY * 34);
        }
    }

    //new Catastrophe(x,y,w,h).getRandomCatastrophe()
    //new Catastrophe().getRandomCatastrophe()
    public Catastrophe getRandomCatastrophe() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        Random rand = new Random();
        int typeId = rand.nextInt(4);

        Catastrophe temp;

        switch (typeId) {
            case 1: ///chemical
                temp = new ChemicalDisaster(this.x, this.y, this.width, this.height, this.map, this.speed);
                return temp;
            case 2: ///meteor
                temp = new Meteor(this.x, this.y, this.width, this.height, this.map, this.speed);
                return temp;
            case 3: ///monster
                temp = new Monster(this.x, this.y, this.width, this.height, this.map, this.speed);
                return temp;
            default: ///fire
                temp = new Fire(this.x, this.y, this.width, this.height, this.map, this.speed);
                return temp;
        }
    }
}

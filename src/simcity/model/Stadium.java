package simcity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import simcity.view.Sprite;

public class Stadium extends Building {

    @JsonProperty("inactiveTile")
    private boolean inactiveTile = false;

    public Stadium() {
        super();
    }

    public Stadium(int x, int y, Map map, String image, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, image, "data/Sounds/Building/Stadium/Supporters_16.wav", map, speed);
        this.range = 2;
        this.cost = 3000;
        this.fee = 600;
        if (speed == 1) {
            playS("data/Sounds/Building/Stadium/Supporters_16.wav");
        } else if (speed == 2) {
            playS("data/Sounds/Building/Stadium/Supporters_16.wav");
        } else if (speed == 3) {
            playS("data/Sounds/Building/Stadium/Supporters_16.wav");
        }
    }

    public Stadium(int x, int y, Map map, String image, boolean sound, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, image, "data/Sounds/Building/Stadium/Supporters_16.wav", map, speed);
        this.range = 2;
        this.cost = 3000;
        this.fee = 600;
        this.inactiveTile = true;
    }

    public void IncreaseHappiness() {
        //Amúgy a kettő konstruktorral -mondjuk a 2.-al megoldható, hogy csak egy mező adjon happinesst 
        int[] tileIndex = map.getTileIndex(this);
        int x = tileIndex[0];
        int y = tileIndex[1];

        Sprite[][] map_array = map.getMap_array();

        while (x - range < 0) {
            x++;
        }

        while (y - range < 0) {
            y++;
        }

        x -= range;
        y -= range;

        int endx = tileIndex[0];
        int endy = tileIndex[1];

        while (x + range > map_array.length) {
            endx += range;
        }

        while (y + range > map_array[0].length) {
            endy += range;
        }

        if (x >= endx || y >= endy) {
            return; //Nagyobb a kezdő index, mint a végindex
        }

        if (endx + 1 < map_array.length) {
            endx += 1;
        }

        if (endy + 1 < map_array[0].length) {
            endy += 1;
        }

        int happinesModifier = 30;

        for (int i = x; i < endx; i++) {
            for (int j = y; j < endy; j++) {
                if (map_array[i][j] instanceof Residential || map_array[i][j] instanceof Service || map_array[i][j] instanceof Industrial) {
                    Zone current = (Zone) map_array[i][j];
                    int[] src = map.getTileIndex(current);
                    int[] dest = map.getTileIndex(this);

                    if (this.aStarSearch(map_array, new Pair(src[0], src[1]), new Pair(dest[0], dest[1]))) {
                        for (Person person : current.getPeople()) {
                            person.setHappinessMods(this, happinesModifier);
                            System.out.println("Növelés");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void playL() {
        if (speed == 1) {
            play("data/Sounds/Building/Stadium/Supporters_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Building/Stadium/Supporters_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Building/Stadium/Supporters_16.wav");
        }
    }

    @Override
    public void playL(int speed) {
        if (speed == 1) {
            play("data/Sounds/Building/Stadium/Supporters_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Building/Stadium/Supporters_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Building/Stadium/Supporters_16.wav");
        }
    }
}

package simcity.model;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import simcity.view.Sprite;

public class Police extends Building {

    public Police() {
        super();
    }

    public Police(int x, int y, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, "data/Sprites/Building/Police/police.png", "data/Sounds/Building/Police/Office_16.wav", map, speed);
        this.cost = 2000;
        this.fee = 400;
        this.range = 4;
        if (speed == 1) {
            playS("data/Sounds/Building/Police/Office_16.wav");
        } else if (speed == 2) {
            playS("data/Sounds/Building/Police/f_Office_16.wav");
        } else if (speed == 3) {
            playS("data/Sounds/Building/Police/ff_Office_16.wav");
        }

    }

    public void IncreaseSafety(int pop) {
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

        int happinesModifier = pop > 500 ? 50 : (int) Math.round(pop * 0.5);

        //Végigmegyünk az összes mezőn ami 3-as távolságon belül van
        //Ha lakózónát találunk, akkor megnézzük, hogy van-e oda legfeljebb 4 hosszúságú elérhető út
        //Ha találunk ilyet, akkor lakók boldogság módosítójához hozzáadjuk/módosítjuk az erdőt a (népesség * 0.1) módosítóval (max 50)
        for (int i = x; i < endx; i++) {
            for (int j = y; j < endy; j++) {
                if (map_array[i][j] instanceof Residential) {
                    Residential current = (Residential) map_array[i][j];
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
            play("data/Sounds/Building/Police/Office_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Building/Police/f_Office_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Building/Police/ff_Office_16.wav");
        }
    }

    @Override
    public void playL(int speed) {
        if (speed == 1) {
            play("data/Sounds/Building/Police/Office_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Building/Police/f_Office_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Building/Police/ff_Office_16.wav");
        }
    }
}

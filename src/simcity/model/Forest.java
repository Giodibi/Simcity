package simcity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.awt.Image;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import simcity.view.Sprite;

public class Forest extends Building {

    @JsonProperty("plantDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate age; ///date when it was planted

    public Forest() {
        super();
    }

    public Forest(int x, int y, LocalDate date, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, "data/Sprites/Building/Forest/lv0.png", "data/Sounds/Building/Forest/Birds_16.wav", map, speed);
        this.age = date;     //plantDate format = "2023-04-10"
        this.range = 3;
        this.cost = 600;
        if (speed == 1) {
            playS("data/Sounds/Building/Forest/Birds_16.wav");
        } else if (speed == 2) {
            playS("data/Sounds/Building/Forest/f_Birds_16.wav");
        } else if (speed == 3) {
            playS("data/Sounds/Building/Forest/ff_Birds_16.wav");
        }

    }

    public Forest(int x, int y, LocalDate date, Map map, boolean sound, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(x, y, "data/Sprites/Building/Forest/lv0.png", "data/Sounds/Building/Forest/Birds_16.wav", map, speed);
        this.age = date; //plantDate format = "2023-04-10"
        this.range = 3;
        this.cost = 600;
    }

    public void IncreaseHappiness(LocalDate date) {
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
            endx--;
        }

        while (y + range > map_array[0].length) {
            endy--;
        }

        if (x >= endx || y >= endy) {
            return; //Nagyobb a kezdő index, mint a végindex
        }

        LocalDate date2 = date;
        int yearsBetween = Period.between(age, date2).getYears();
        int happinesModifier = yearsBetween < 10 ? Math.round(yearsBetween / 2) : 5;

        //Végigmegyünk az összes mezőn ami 3-as távolságon belül van
        //Ha lakózónát találunk, akkor megnézzük, hogy van-e oda legfeljebb 4 hosszúságú elérhető út
        //Ha találunk ilyet, akkor lakók boldogság módosítójához hozzáadjuk/módosítjuk az erdőt a (erdő éve * 2) módosítóval (max 20)
        for (int i = x; i < endx; i++) {
            for (int j = y; j < endy; j++) {
                if (map_array[i][j] instanceof Residential) {
                    Residential current = (Residential) map_array[i][j];
                    int[] src = map.getTileIndex(current);
                    int[] dest = map.getTileIndex(this);

                    if (this.aStarSearch(map_array, new Pair(src[0], src[1]), new Pair(dest[0], dest[1]))) {
                        for (Person person : current.getPeople()) {
                            person.setHappinessMods(this, happinesModifier);
                        }
                    }
                }
            }
        }
    }

    public static boolean hasShortPath(Sprite[][] grid, int x1, int y1, int x2, int y2) {
        // Ellenőrizzük, hogy a koordináták a térképen vannak-e
        if (x1 < 0 || x1 >= grid.length || y1 < 0 || y1 >= grid[0].length
                || x2 < 0 || x2 >= grid.length || y2 < 0 || y2 >= grid[0].length) {
            throw new IllegalArgumentException("A koordináták a térkép határain kívül vannak.");
        }

        // Létrehozzuk az útvonalat
        List<int[]> path = new ArrayList<>();

        // Megkeressük az utat
        boolean found = mgk(grid, x1, y1, x2, y2, path);

        // Ellenőrizzük az útvonal hosszát és a találatot
        return found && path.size() < 5;
    }

    public void Ageing(LocalDate now) {
        int yearsBetween = Period.between(age, now).getYears();
        if (yearsBetween >= 5 && yearsBetween < 10) {
            Image new_img = new ImageIcon("data/Sprites/Building/Forest/lv1.png").getImage();
            this.image = new_img;
        } else if (yearsBetween >= 10) {
            Image new_img = new ImageIcon("data/Sprites/Building/Forest/lv2.png").getImage();
            this.image = new_img;
            this.fee = 500;
        }
    }

    @Override
    public void playL() {
        if (speed == 1) {
            play("data/Sounds/Building/Forest/Birds_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Building/Forest/f_Birds_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Building/Forest/ff_Birds_16.wav");
        }
    }

    public void playL(int speed) {
        if (speed == 1) {
            play("data/Sounds/Building/Forest/Birds_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Building/Forest/f_Birds_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Building/Forest/ff_Birds_16.wav");
        }
    }
}

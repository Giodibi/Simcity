package simcity.model;

import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.time.LocalDate;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import simcity.view.Sprite;

public class Map {

    private int size = 20;
    private Sprite[][] map_array = new Sprite[size][size];

    public Map() {

    }

    public Map(LocalDate startDate) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Point coords = calcScreenCoords(i, j);
                Random rand = new Random();

                try {

                    if (rand.nextInt(20) == 0) {
                        map_array[i][j] = new Forest(coords.x, coords.y, startDate, this, false, 1);

                    } else {
                        map_array[i][j] = new Ground(coords.x, coords.y, this, 1);
                    }
                } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            map_array[0][0] = new Road(calcScreenCoords(0, 0).x, calcScreenCoords(0, 0).y, "data/Sprites/Road/road_t_a_res.png", this, false, 1); //[0][0] main road to the city
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Point calcScreenCoords(int x, int y) {
        return new Point((int) (x * 0.5 * 132 + y * -0.5 * 132) - 132 / 2 + (132 * size) / 2, (int) (x * 0.25 * 132 + y * 0.25 * 132) - 132 / 2 + 40);
    }

    public Sprite[][] getMap_array() {
        return map_array;
    }

    public void setMap_array(Sprite[][] map_array) {
        this.map_array = map_array;
    }

    public void setTile(int x, int y, Sprite s) {
        this.map_array[x][y] = s;
    }

    public Sprite getSpriteFromIndex(int x, int y) {
        return map_array[x][y];
    }

    public int[] getTileIndex(Sprite s) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map_array[i][j].equals(s)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public void draw(Graphics g) {
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                map_array[i][j].draw(g);
            }
        }
    }

    public int getSize() {
        return size;
    }
}

package simcity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.Image;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import simcity.view.Sprite;

public class Residential extends Zone {

    @JsonIgnore
    private int subLevel;
    @JsonIgnore
    private String levelColor = "yellow";

    public Residential() {
        super();
    }

    public Residential(int x, int y, String image, String soundFile, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, image, soundFile, map, speed);
        this.subLevel = 1;
        if (speed == 1) {
            playS("data/Sounds/Zone/Residential/Crowd_16.wav");
        } else if (speed == 2) {
            playS("data/Sounds/Zone/Residential/f_Crowd_16.wav");
        } else if (speed == 3) {
            playS("data/Sounds/Zone/Residential/ff_Crowd_16.wav");
        }
    }

    public Residential(int x, int y, String image, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, image, "data/Sounds/Zone/Residential/Crowd_16.wav", map, speed);
        this.subLevel = 1;
        if (speed == 1) {
            playS("data/Sounds/Zone/Residential/Crowd_16.wav");
        } else if (speed == 2) {
            playS("data/Sounds/Zone/Residential/f_Crowd_16.wav");
        } else if (speed == 3) {
            playS("data/Sounds/Zone/Residential/ff_Crowd_16.wav");
        }
    }

    public void calculateHappinesMods() {
        int[] tileIndex = map.getTileIndex(this);
        int x = tileIndex[0];
        int y = tileIndex[1];

        Sprite[][] map_array = map.getMap_array();

        while (x - 3 < 0) {
            x++;
        }

        while (y - 3 < 0) {
            y++;
        }

        x -= 3;
        y -= 3;

        int endx = tileIndex[0];
        int endy = tileIndex[1];

        while (x + 3 > map_array.length) {
            endx += 3;
        }

        while (y + 3 > map_array[0].length) {
            endy += 3;
        }

        for (int i = x; i < endx; i++) {
            for (int j = y; j < endy; j++) {
            }
        }
    }

    public void calculateSublevel() {
        switch (this.level) {
            case 1:
                this.subLevel = (int) Math.ceil(people.size() / (maxCapacity / 9.0)) > 0 ? (int) Math.ceil(people.size() / (maxCapacity / 9.0)) : 0;
                break;
            case 2:
                this.subLevel = (int) Math.ceil(people.size() / (maxCapacity / 5.0)) > 0 ? (int) Math.ceil(people.size() / (maxCapacity / 5.0)) : 0;
                break;
            case 3:
                this.subLevel = (int) Math.ceil(people.size() / (maxCapacity / 4.0)) > 0 ? (int) Math.ceil(people.size() / (maxCapacity / 4.0)) : 0;
                break;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public boolean Building() {
        return this.hasBuilding;
    }

    @Override
    public void Doing() {
        LevelUp();
    }

    @Override
    public void Upgrade() {
        if (this.level < 3) {
            this.maxCapacity = this.maxCapacity * 2;
            this.level++;
            switch (this.level) {
                case 1:
                    this.levelColor = "yellow";
                    break;
                case 2:
                    this.levelColor = "red";
                    break;
                case 3:
                    this.levelColor = "blue";
                    break;
                default:

            }
        }
    }

    public void LevelUp() {
        //fejlődő zóna esetén növeljük a kép alszámát
        calculateSublevel();

        String img_str = "data/Sprites/Zone/Residential/Level0/residential0.png";
        if (this.subLevel > 0) {
            img_str = "data/Sprites/Zone/Residential/Level" + Integer.toString(this.level) + "/" + levelColor + "/lv" + Integer.toString(this.level) + "_" + Integer.toString(this.subLevel) + ".png";
        }
        Image new_img = new ImageIcon(img_str).getImage();
        this.image = new_img;
        this.imagePath = img_str;
    }

    @Override
    public void playL() {
        if (speed == 1) {
            play("data/Sounds/Zone/Residential/Crowd_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Zone/Residential/f_Crowd_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Zone/Residential/ff_Crowd_16.wav");
        }
    }

    @Override
    public void playL(int speed) {
        if (speed == 1) {
            play("data/Sounds/Zone/Residential/Crowd_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Zone/Residential/f_Crowd_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Zone/Residential/ff_Crowd_16.wav");
        }
    }
    @JsonIgnore

    public int getSublevel() {
        return this.subLevel;
    }
    
    @JsonIgnore
    public String getColor() {
        return this.levelColor;
    }

}

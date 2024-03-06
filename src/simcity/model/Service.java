package simcity.model;

import java.awt.Image;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class Service extends Zone {

    public Service() {
        super();
    }

    public Service(int x, int y, String image, String soundFile, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, image, soundFile, map, speed);
        if (speed == 1) {
            playS("data/Sounds/Zone/Service/ShopingCenter_16.wav");
        } else if (speed == 2) {
            playS("data/Sounds/Zone/Service/f_ShopingCenter_16.wav");
        } else if (speed == 3) {
            playS("data/Sounds/Zone/Service/ff_ShopingCenter_16.wav");
        }
        level = 0;
    }

    public Service(int x, int y, String image, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, image, "data/Sounds/Zone/Service/ShopingCenter_16.wav", map, speed);
        if (speed == 1) {
            playS("data/Sounds/Zone/Service/ShopingCenter_16.wav");
        } else if (speed == 2) {
            playS("data/Sounds/Zone/Service/f_ShopingCenter_16.wav");
        } else if (speed == 3) {
            playS("data/Sounds/Zone/Service/ff_ShopingCenter_16.wav");
        }
        level = 0;
    }

    @Override
    public boolean Building() {
        return true;
    }

    @Override
    public void Doing() {
    }

    @Override
    public void Upgrade() {
        if (this.level < 3) {
            this.maxCapacity = this.maxCapacity * 2;
            this.level++;
        }

        String img_str = "data/Sprites/Zone/Service/lvl" + this.level + ".png";
        Image new_img = new ImageIcon(img_str).getImage();
        this.image = new_img;
        this.imagePath = img_str;
    }

    @Override
    public void playL() {
        if (speed == 1) {
            play("data/Sounds/Zone/Service/ShopingCenter_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Zone/Service/f_ShopingCenter_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Zone/Service/ff_ShopingCenter_16.wav");
        }
    }

    @Override
    public void playL(int speed) {
        if (speed == 1) {
            play("data/Sounds/Zone/Service/ShopingCenter_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Zone/Service/f_ShopingCenter_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Zone/Service/ff_ShopingCenter_16.wav");
        }
    }

}

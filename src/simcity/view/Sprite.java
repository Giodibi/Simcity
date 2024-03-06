package simcity.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import simcity.events.CustomMouseListener;
import simcity.model.Map;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import simcity.model.Forest;
import simcity.model.Ground;
import simcity.model.Industrial;
import simcity.model.Police;
import simcity.model.Residential;
import simcity.model.Road;
import simcity.model.Service;
import simcity.model.Sound;
import simcity.model.Stadium;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
    @Type(value = Road.class, name = "road"),
    @Type(value = Forest.class, name = "forest"),
    @Type(value = Police.class, name = "police"),
    @Type(value = Stadium.class, name = "stadium"),
    @Type(value = Industrial.class, name = "industrial"),
    @Type(value = Residential.class, name = "residential"),
    @Type(value = Service.class, name = "service"),
    @Type(value = Ground.class, name = "ground"),})
public abstract class Sprite {

    protected int x;
    protected int y;

    @JsonIgnore
    protected int width;
    @JsonIgnore
    protected int height;
    @JsonIgnore
    protected int offset;
    @JsonIgnore
    protected Image image;
    protected String imagePath;
    @JsonIgnore
    protected CustomMouseListener mouseAdapter;
    @JsonIgnore
    protected Map map;
    @JsonIgnore
    Clip sound;
    String soundPath;
    public int speed;

    public Sprite() {
        super();
        this.width = 132;
        this.height = 132;
        image = new ImageIcon(imagePath).getImage();
    }

    public Sprite(int x, int y, String image, String soundFile, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.x = x;
        this.y = y;
        this.width = 132;
        this.height = 132;
        this.imagePath = image;
        Image new_img = new ImageIcon(imagePath).getImage();
        this.image = new_img;
        this.map = map;

        File file = new File(soundFile);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        this.sound = AudioSystem.getClip();
        this.soundPath = soundFile;
        this.speed = speed;
    }

    public void initGraphics() throws LineUnavailableException {
        this.image = new ImageIcon(imagePath).getImage();
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundPath));
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.sound = AudioSystem.getClip();
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y + offset, null);
    }

    public CustomMouseListener getMouseAdapter() {
        return mouseAdapter;
    }

    public void setMouseAdapter(CustomMouseListener mouseAdapter) {
        this.mouseAdapter = mouseAdapter;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImagePath(String path) {
        this.imagePath = path;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public Clip getSound() {
        return sound;
    }

    public void setSound(Clip sound) {
        this.sound = sound;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean hasConnection() {
        return hasShortPath(map.getMap_array(), 0, 0, x, y);
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
        return found;
    }

    public static boolean mgk(Sprite[][] grid, int x, int y, int x2, int y2, List<int[]> path) { //mélységi gráf keresés
        // Ha elértük a célpontot akkor kész és return
        if (x == x2 && y == y2) {
            return true;
        }

        // Ellenőrizzük, hogy a mező érvényes-e
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length
                || !(grid[x][y] instanceof Road)) {
            return false;
        }

        // Hozzáadjuk a mezőt az útvonalhoz
        path.add(new int[]{x, y});

        // Megpróbálunk eljutni az egyik szomszédos mezőre
        boolean found = mgk(grid, x + 1, y, x2, y2, path)
                || // Jobbra
                mgk(grid, x - 1, y, x2, y2, path)
                || // Balra
                mgk(grid, x, y + 1, x2, y2, path)
                || // Le
                mgk(grid, x, y - 1, x2, y2, path);  // Fel

        // Ha nem találtunk utat akkor eltávolítjuk a mezőt az útvonalból
        if (!found) {
            path.remove(path.size() - 1);
        }

        return found;
    }

    public void playS(String soundFile) throws InterruptedException {
        try {
            Sound temp = new Sound();
            temp.playShort(soundFile, this.speed);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void play(String soundFile) {
        try {
            Sound temp = new Sound(soundFile, this.speed);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(CustomMouseListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playL() {
        play("data/Sounds/Zone/Residential/Crowd_16.wav");
    }

    public void playL(int speed) {
        if (speed == 1) {
            play("data/Sounds/Zone/Residential/Crowd_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Zone/Residential/f_Crowd_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Zone/Residential/ff_Crowd_16.wav");
        }
    }

}

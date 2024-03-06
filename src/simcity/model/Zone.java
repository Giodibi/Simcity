package simcity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import simcity.view.Sprite;

public abstract class Zone extends Sprite {

    @JsonIgnore
    protected int cost;
    protected ArrayList<Person> people;
    protected int maxCapacity;
    @JsonIgnore
    protected boolean hasBuilding;
    protected int level;
    @JsonIgnore
    protected int upgradeCost;
    @JsonIgnore
    protected int happines;

    public Zone() {
        super();
    }

    public Zone(int x, int y, String image, String soundFile, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(x, y, image, soundFile, map, speed);
        this.hasBuilding = false;
        this.level = 1;
        this.cost = 2000;   //base build cost
        this.maxCapacity = 10;
        this.upgradeCost = 2000;
        this.happines = 0;
        this.people = new ArrayList<>();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getLevel() {
        return level;
    }

    public void upgradeLevel() {

    }

    public int getCost() {
        return cost;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public int getHappines() {
        return happines;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public abstract boolean Building();

    public abstract void Doing();

    public abstract void Upgrade();

    public Ground Revert() {
        Ground ground = null;
        if (!this.hasBuilding) {
            int[] index = map.getTileIndex(this);
            try {
                ground = new Ground(this.x, this.y, this.map, this.speed);
                map.setTile(index[0], index[1], ground);

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                Logger.getLogger(Residential.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ground;
    }

    public void AddCitizen(Person citizen) {
        boolean contains = false;
        for (Person person : people) {
            if (person == citizen) {
                contains = true;
            }
        }

        if (!contains) {
            this.people.add(citizen);
        }
    }

    public void RemoveCitizen(Person citizen) {
        this.people.remove(citizen);
    }

    public void CalcHappiness(GameEngine engine) {
        double temp = 0;
        for (Person person : people) {
            temp += person.getHappiness(engine);
        }
        temp = temp / people.size();
        happines = (int) Math.round(temp);
    }

    @Override
    public void playL() {
        play("data/Sounds/Zone/Residential/Crowd_16.wav");
    }

}

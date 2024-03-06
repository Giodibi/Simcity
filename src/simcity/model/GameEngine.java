package simcity.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;
import simcity.view.Sprite;
import simcity.events.CustomMouseListener;
import simcity.persistency.GameData;
import simcity.persistency.Persistency;

public class GameEngine extends JPanel {

    private final int FPS = 120;
    private Timer newFrameTimer = null;
    private Timer newDateTimer = null;
    private Timer dataRefreshTimer = null;

    private Catastrophe randCat = null;
    private Map map;
    private LocalDate date;
    private int funds = 100000; ///starting money
    private int happiness = 0; /// -100-100 között
    private int pops = 0;
    private Persistency persistency;
    private int taxRate = 25; /// 0 - 100 intervallum, ahány érték annyi %+ adó pl.: 25% = 125%-os adó
    private int baseTax = 500;     //base amount of money paid per person
    private int realTax = baseTax + ((baseTax / 100) * taxRate);
    private ArrayList<Person> citizens = new ArrayList<>();
    private int newCitizenNum = 0;
    private int leavingCitNum = 0;
    private int income = 0;
    private int expenses = 0;
    private boolean muted = false;
    private boolean starterCitizens = false;
    private int speed = 1; ///0 = stopped, 1 = normal, 2 = faster, 3 = extra fast

    public GameEngine() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super();

        this.date = LocalDate.of(2020, 01, 01);
        this.map = new Map(date);

        try {
            this.randCat = new Catastrophe(this.map, this.speed);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        refreshMouseListeners();

        pops = citizens.size();
        CalcHappiness();
    }

    public GameEngine(GameData saveFile) {

        super();
        this.date = saveFile.getDate();
        this.map = saveFile.getMap();

        //Assign map to sprites, and citizens to citizens array
        for (Sprite[] row : map.getMap_array()) {
            for (Sprite sprite : row) {
                sprite.setMap(map);
                if (sprite instanceof Residential && !(((Residential) sprite).getPeople().isEmpty())) {
                    for (Person citizen : ((Residential) sprite).getPeople()) {
                        citizens.add(citizen);
                    }
                }
                try {
                    sprite.initGraphics();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        //Setup workplace and home based on loaded coordinates
        for (Person citizen : citizens) {
            int homeCoords[] = citizen.getHomeCoords();
            int workCoords[] = citizen.getWorkCoords();
            if (homeCoords != null) {
                citizen.setHome((Residential) map.getSpriteFromIndex(homeCoords[0], homeCoords[1]));
            }
            if (workCoords != null) {
                if (map.getSpriteFromIndex(workCoords[0], workCoords[1]) instanceof Industrial) {
                    citizen.setWorkplace((Industrial) map.getSpriteFromIndex(workCoords[0], workCoords[1]));

                } else {
                    citizen.setWorkplace((Service) map.getSpriteFromIndex(workCoords[0], workCoords[1]));

                }
            }
        }

        this.funds = saveFile.getFunds();
        this.taxRate = saveFile.getTaxRate();

        removeMouseListeners();
        refreshMouseListeners();

        pops = citizens.size();
        CalcHappiness();
        calculateIncome();
        calculateExpenses();
    }

    public void refreshMouseListeners() {

        for (Sprite[] row : map.getMap_array()) {
            for (Sprite sprite : row) {
                try {
                    sprite.setMouseAdapter(new CustomMouseListener(sprite, this));
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
                addMouseListener(sprite.getMouseAdapter());
                addMouseMotionListener(sprite.getMouseAdapter());
            }
        }
    }

    public void removeMouseListeners() {
        for (Sprite[] row : map.getMap_array()) {
            for (Sprite sprite : row) {
                MouseAdapter ma = sprite.getMouseAdapter();
                this.removeMouseListener(ma);
                this.removeMouseMotionListener(ma);
            }
        }
    }

    public void startGame() {
        newDateTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcDate();
            }
        });
        newDateTimer.start();

        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();

        dataRefreshTimer = new Timer(30000, new ActionListener() {   // Initially the delay is 30 secs
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateIncome();
                calculateExpenses();
                funds += income - expenses;

                CalcHappiness();

                newCitizenNum = Math.round(happiness / 5);

                if (citizens.isEmpty()) {
                    newCitizenNum = 1;
                }

                if (newCitizenNum >= 0) {
                    for (int i = 0; i < newCitizenNum; i++) {
                        Person citizen = new Person(map, citizens);
                        if (citizen.getHome() != null && citizen.getWorkplace() != null) {
                            citizens.add(new Person(map, citizens));
                        }
                    }
                } else {
                    int temp = 0;
                    for (int i = 0; i < Math.abs(newCitizenNum); i++) {
                        if (!citizens.isEmpty()) {
                            Person leaving = getMostDissatisfied();
                            leaving.leaveCity();
                            citizens.remove(leaving);
                            temp++;
                        }
                    }
                    leavingCitNum = temp;
                }

                System.out.println("pops: " + pops);

                for (Person citizen : citizens) {
                    if (citizen.getWorkplace() == null) {
                        citizen.findWorkplace(citizens, map);
                    }
                }

                for (Sprite[] sprites : map.getMap_array()) {
                    for (Sprite sprite : sprites) {
                        if (sprite instanceof Forest) {
                            ((Forest) sprite).Ageing(date);
                            ((Forest) sprite).IncreaseHappiness(date);
                        } else if (sprite instanceof Zone) {
                            ((Zone) sprite).Doing();
                        } else if (sprite instanceof Police) {
                            ((Police) sprite).IncreaseSafety(pops);
                        } else if (sprite instanceof Stadium) {
                            ((Stadium) sprite).IncreaseHappiness();
                        }
                    }
                }
                pops = citizens.size();

            }
        });
        dataRefreshTimer.start();

    }

    public int getNewCitizenNum() {
        return newCitizenNum;
    }

    public void setNewCitizenNum(int newCitizenNum) {
        this.newCitizenNum = newCitizenNum;
    }

    public int getLeavingCitNum() {
        return leavingCitNum;
    }

    public void setLeavingCitNum(int leavingCitNum) {
        this.leavingCitNum = leavingCitNum;
    }

    public int getFunds() {
        return funds;
    }

    public boolean getStarterCitizens() {
        return starterCitizens;
    }

    public void setStarterCitizens() {
        starterCitizens = true;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getPops() {
        pops = citizens.size();
        return pops;
    }

    public Catastrophe getRandCat() {
        return randCat;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getIncome() {
        return income;
    }

    public int getFPS() {
        return FPS;
    }

    public Timer getNewFrameTimer() {
        return newFrameTimer;
    }

    public Timer getNewDateTimer() {
        return newDateTimer;
    }

    public Timer getDataRefreshTimer() {
        return dataRefreshTimer;
    }

    public Persistency getPersistency() {
        return persistency;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public int getBaseTax() {
        return baseTax;
    }

    public int getRealTax() {
        return realTax;
    }

    public int getExpenses() {
        return expenses;
    }

    public int getSpeed() {
        return speed;
    }

    public Person getMostDissatisfied() {
        if (citizens.isEmpty()) {
            return null;
        }
        int happines = citizens.get(0).getHappiness(this);
        Person unhappy = citizens.get(0);
        for (Person citizen : citizens) {
            if (happines > citizen.getHappiness(this)) {
                happines = citizen.getHappiness(this);
                unhappy = citizen;
            }
        }
        return unhappy;
    }

    void setCitizens(ArrayList<Person> arrayList) {
        this.citizens = arrayList;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public void setFunds(int funds) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.funds = funds;
        if (isGameOver()) {
            try {
                Sound temp = new Sound("data/Sounds/Etc/GameLost/Lost_16.wav", speed);
                sleep(3000);
                if (speed == 1) {
                    temp = new Sound("data/Sounds/Etc/GameLost/CrowdDisappointment_16.wav", speed);
                } else if (speed == 2) {
                    temp = new Sound("data/Sounds/Etc/GameLost/f_CrowdDisappointment_16.wav", speed);
                } else if (speed == 3) {
                    temp = new Sound("data/Sounds/Etc/GameLost/ff_CrowdDisappointment_16.wav", speed);
                }
            } catch (IOException ex) {
                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.setColor(new Color(150, 162, 82));
        grphcs.fillRect(0, 0, getWidth(), getHeight());
        map.draw(grphcs);

    }

    public Map getMap() {
        return map;
    }

    public void mapSetTile(int x, int y, Sprite s) {
        this.map.setTile(x, y, s);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Person> getCitizens() {
        return citizens;
    }

    public boolean isGameOver() {
        if (this.happiness < -90) {
            return true;
        }
        if (this.happiness < -70 && this.funds < -80000) {
            return true;
        }
        if (this.funds < -100000) {
            return true;
        }
        return false;
    }

    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!isGameOver()) {
                repaint();
            } else {
                dataRefreshTimer.stop();
                newDateTimer.stop();
            }

        }
    }

    /**
     * Calculate avarage happiness in the city (avarage of the citizens
     * happiness) 0-100 is the possible value
     */
    public void CalcHappiness() {
        if (!citizens.isEmpty()) {
            double tmpSum = 0;
            for (Person citizen : citizens) {
                tmpSum += citizen.getHappiness(this);
            }
            this.happiness = (int) Math.round(tmpSum / citizens.size());
        }
    }

    public void addPerson(Person p) {
        this.citizens.add(p);
    }

    public int[] getHappinessModifiersSum() {
        int policeAvg = 0;
        int forestAvg = 0;
        int stadiumAvg = 0;
        int total = 0;
        if (!citizens.isEmpty()) {

            for (Person citizen : citizens) {
                for (SimpleEntry entry : citizen.getHappinessMods()) {
                    if (entry.getKey() instanceof Police) {
                        policeAvg += (Integer) entry.getValue();
                    }
                    if (entry.getKey() instanceof Forest) {
                        forestAvg += (Integer) entry.getValue();

                    }
                    if (entry.getKey() instanceof Stadium) {
                        stadiumAvg += (Integer) entry.getValue();

                    }
                }
            }
            policeAvg = Math.round(policeAvg / citizens.size());
            forestAvg = Math.round(forestAvg / citizens.size());

            stadiumAvg = Math.round(stadiumAvg / citizens.size());
            total = policeAvg + stadiumAvg + forestAvg;

        }
        return new int[]{policeAvg, forestAvg, stadiumAvg, total};
    }

    /**
     * @param rate 25 -> default, 125% tax
     */
    public void setTaxRate(int rate) {
        if (rate < 0 || rate > 100) { ///error, but game must go on -> change it to default
            this.taxRate = 25;
            System.out.print("Log: Tax - Tried to change tax to an invalid value"); //maybe add date? tax? log magunknak
        } else {
            this.taxRate = rate;
        }
        this.realTax = baseTax + ((baseTax / 100) * taxRate);     //alap adóhóz hozzáadjuk annak adott %-át

    }

    public int calcTaxIncome(int rate) {
        return (baseTax + ((baseTax / 100) * rate)) * citizens.size();
    }

    public int getRate() {
        return this.taxRate;
    }

    public void calculateIncome() {
        if (!citizens.isEmpty()) {
            this.income = realTax * citizens.size();
        }
    }

    public void calculateExpenses() {
        int sum = 0;
        for (Sprite[] sprites : map.getMap_array()) {
            for (Sprite s : sprites) {
                if (s instanceof Building) {
                    sum += ((Building) s).getFee();
                } else if (s instanceof Road) {
                    sum += ((Road) s).getFee();
                }
            }
        }
        this.expenses = sum;
    }

    public void changeFunds(int amount) throws UnsupportedAudioFileException, InterruptedException {
        this.funds += amount;
        if (isGameOver()) {
            try {
                Sound temp;
                temp = new Sound("data/Sounds/Etc/GameLost/Lost_16.wav", speed);
                sleep(3000);
                if (speed == 1) {
                    temp = new Sound("data/Sounds/Etc/GameLost/CrowdDisappointment_16.wav", speed);
                } else if (speed == 2) {
                    temp = new Sound("data/Sounds/Etc/GameLost/f_CrowdDisappointment_16.wav", speed);
                } else if (speed == 3) {
                    temp = new Sound("data/Sounds/Etc/GameLost/ff_CrowdDisappointment_16.wav", speed);
                }
            } catch (IOException | LineUnavailableException ex) {
                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * @param speed 0 = stopped, 1 = normal, 2 = faster, 3 = extra fast
     */
    public void setSpeed(int speed) {
        if (speed < 0 || speed > 3) {///error, but game must go on -> change it to default
            this.speed = 1;
            System.out.print("Log: Speed - Tried to change speed to an invalid value"); //maybe add date? speed? log magunknak
        } else {
            this.speed = speed;
        }
        switch (this.speed) { //ha ez van, akkor nem lehet a speed menü alap kattintást hasnálni,
            case 1:
                dataRefreshTimer.setDelay(30000);
                dataRefreshTimer.setInitialDelay(30000);
                dataRefreshTimer.restart();

                break;
            case 2:
                dataRefreshTimer.setDelay(15000);
                dataRefreshTimer.setInitialDelay(15000);
                dataRefreshTimer.restart();

                break;
            case 3:
                dataRefreshTimer.setDelay(3000);
                dataRefreshTimer.setInitialDelay(3000);
                dataRefreshTimer.restart();

                break;
            default:
                dataRefreshTimer.stop();
        }

    }

    /**
     * @return date in the game, in string format
     */
    /**
     * Calculate date from the timer, speed Should call it every second! ->
     * lesser calculation then calling it 1000/FPS
     */
    private void calcDate() {

        switch (this.speed) {
            case 1: ///speed normal -> 1s = 1 day
                this.date = date.plusDays(1);
                return;
            case 2: ///speed fast -> 0,5s = 1 day
                this.date = date.plusDays(2);
                return;
            case 3: ///speed extra fast -> 0,1s = 1 day
                this.date = date.plusDays(15);

                return;
            default: ///speed 0 stopped
                return;
        }
    }

    /**
     * If its not muted, mute it If its muted, unmute it
     */
    public void muted() {
        this.muted = !this.muted;
    }

    public boolean isMuted() {
        return muted;
    }

    public boolean catResult(Catastrophe temp) {
        if (temp instanceof Meteor) { ///lerombolódik a mező
            return true;
        }
        if (temp instanceof Fire) { ///tűznek helyreállítási költsége van
            this.funds -= 10000;
        } else if (temp instanceof ChemicalDisaster) { ///lakhatatlan a környék, a basetax ezért növekszik
            this.baseTax += 100;
        } else { ///egy ideig félnek a szörnytől ezért szomorúbbak
            this.happiness -= 10;
        }

        return false;
    }
}

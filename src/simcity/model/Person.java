package simcity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import simcity.view.Sprite;

public class Person {

    @JsonIgnore
    private Zone workplace;
    private int[] workCoords;
    @JsonIgnore
    private Residential home;
    private int[] homeCoords;
    @JsonIgnore
    private ArrayList<AbstractMap.SimpleEntry<Sprite, Integer>> happinessMods;

    public Person() {
        this.happinessMods = new ArrayList<>();

    }

    public Person(Map map, List<Person> citizens) {
        this.home = findHome(map);
        this.homeCoords = map.getTileIndex(this.home);
        if (home != null) {
            this.workplace = findWorkplace(citizens, map);
            this.workCoords = map.getTileIndex(this.workplace);
        }
        this.happinessMods = new ArrayList<>();
    }

    public int getHappiness(GameEngine engine) {
        int happinessSum = 0;
        boolean safety = false;
        for (AbstractMap.SimpleEntry<Sprite, Integer> happinesMod : happinessMods) {
            if (happinesMod.getKey() instanceof Police) {
                safety = true;
            }
            happinessSum += happinesMod.getValue();
        }

        if (!safety) {
            happinessSum -= engine.getPops() > 500 ? 50 : (int) Math.round(engine.getPops() * 0.1);
        }

        int service = 0;
        int industrial = 0;
        boolean closeIndustrial = false;

        for (Sprite[] places : engine.getMap().getMap_array()) {
            for (Sprite place : places) {
                if (place instanceof Service) {
                    service++;
                }
                if (place instanceof Industrial) {
                    industrial++;
                    if (home != null && Math.max(Math.abs(place.getX() - home.getX()), Math.abs(place.getY() - home.getY())) <= 2) {
                        closeIndustrial = true;
                    }
                }
            }
        }

        if (service > (industrial * 2) || industrial > (service * 2)) {
            happinessSum -= 10;
        }

        if (closeIndustrial) {
            happinessSum -= 10;
        } else {
            happinessSum += 10;
        }

        if (engine.getTaxRate() >= 50) {
            happinessSum -= (int) Math.round(engine.getTaxRate() / 2);
        }

        if (engine.getTaxRate() < 20) {
            happinessSum += (int) Math.round(20 - engine.getTaxRate());
        }

        if (workplace != null && home != null) {
            if (Math.abs(workCoords[0] - homeCoords[0]) < 3 && Math.abs(workCoords[1] - homeCoords[1]) < 3) {
                happinessSum += 20;
            } else {
                if (Math.max(Math.abs(workCoords[0] - homeCoords[0]), Math.abs(workCoords[1] - homeCoords[1])) > 20) {
                    happinessSum -= 20;
                } else {
                    happinessSum -= Math.max(Math.abs(workCoords[0] - homeCoords[0]), Math.abs(workCoords[1] - homeCoords[1]));
                }
            }
        }

        if (happinessSum > 0) {
            return happinessSum >= 100 ? 100 : happinessSum;
        } else {
            return happinessSum <= -100 ? -100 : happinessSum;
        }
    }

    @JsonIgnore
    public ArrayList<AbstractMap.SimpleEntry<Sprite, Integer>> getHappinessMods() {
        return happinessMods;
    }

    public void addHappinessMod(Sprite sprite, int mod) {
        happinessMods.add(new AbstractMap.SimpleEntry(sprite, mod));
    }

    public void removeHappinessMod(Sprite sprite) {
        happinessMods.remove(sprite);
    }

    @JsonIgnore
    public void setHappinessMods(Sprite sprite, int mod) {
        if (happinessMods.indexOf(sprite) == -1) {
            addHappinessMod(sprite, mod);
        } else {
            happinessMods.set(happinessMods.indexOf(sprite), new AbstractMap.SimpleEntry(sprite, mod));
        }
    }

    public Zone getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Zone x) {
        workplace = x;
    }

    public Residential getHome() {
        return home;
    }

    public void setHome(Residential x) {
        home = x;
    }

    public int[] getWorkCoords() {
        return workCoords;
    }

    public void setWorkCoords(int[] workCoords) {
        this.workCoords = workCoords;
    }

    public int[] getHomeCoords() {
        return homeCoords;
    }

    public void setHomeCoords(int[] homeCoords) {
        this.homeCoords = homeCoords;
    }

    public Residential findHome(Map map) {
        Sprite[][] map_array = map.getMap_array();
        ArrayList<Residential> possible_homes = new ArrayList<Residential>();
        boolean found = false;

        //Megkeresi az összes, hellyel rendelkező lakózónát
        for (Sprite[] sprites : map_array) {
            for (Sprite sprite : sprites) {
                if (sprite instanceof Residential) {
                    if (((Zone) sprite).getMaxCapacity() > ((Zone) sprite).getPeople().size()) {
                        possible_homes.add((Residential) sprite);
                    }
                }
            }
        }

        found = !possible_homes.isEmpty();

        //Random választ magának otthont (visszaadja, hogy be tudott-e költözni vagy sem)
        if (found) {
            home = possible_homes.get((int) (Math.random() * possible_homes.size()));
            home.AddCitizen(this);
            homeCoords = map.getTileIndex(home);
            return home;
        } else {
            home = null;
            return null;
        }
    }

    public Zone findWorkplace(List<Person> citizens, Map map) {
        if (home == null) {
            findHome(map);
            if (home == null) {
                return null;
            }
        }

        ArrayList<Zone> possible_workplaces = new ArrayList<Zone>();
        Sprite[][] map_array = map.getMap_array();

        int count = 0;
        for (Person citizen : citizens) {
            if (citizen.getWorkplace() instanceof Service) {
                count++;
            }
        }

        Zone possibleWorkplace = null;

        if (citizens.size() - count > count) {
            //Service
            for (Sprite[] sprites : map_array) {
                for (Sprite sprite : sprites) {
                    if (sprite instanceof Service && ((Zone) sprite).getMaxCapacity() > ((Zone) sprite).getPeople().size()) {
                        possible_workplaces.add((Zone) sprite);
                    }
                }
            }
        }

        if (possible_workplaces.isEmpty()) {
            //Industrial
            for (Sprite[] sprites : map_array) {
                for (Sprite sprite : sprites) {
                    if (sprite instanceof Industrial && ((Zone) sprite).getMaxCapacity() > ((Zone) sprite).getPeople().size()) {
                        possible_workplaces.add((Zone) sprite);
                    }
                }
            }
        }

        if (possible_workplaces.isEmpty()) {
            //Service again
            for (Sprite[] sprites : map_array) {
                for (Sprite sprite : sprites) {
                    if (sprite instanceof Service && ((Zone) sprite).getMaxCapacity() > ((Zone) sprite).getPeople().size()) {
                        possible_workplaces.add((Zone) sprite);
                    }
                }
            }
        }

        possibleWorkplace = !possible_workplaces.isEmpty() ? possible_workplaces.get(0) : null;
        int[] homexy = homeCoords;

        for (Zone possible_workplace : possible_workplaces) {
            int[] xy1 = map.getTileIndex(possibleWorkplace);
            int[] xy2 = map.getTileIndex(possible_workplace);

            double dist1 = Math.sqrt(Math.pow(homexy[0] - xy1[0], 2) + Math.pow(homexy[1] - xy1[1], 2)); //2 pont távolsága
            double dist2 = Math.sqrt(Math.pow(homexy[0] - xy2[0], 2) + Math.pow(homexy[1] - xy2[1], 2));

            if (dist1 > dist2) {
                possibleWorkplace = possible_workplace;
            }
        }

        if (possibleWorkplace != null) {
            workplace = possibleWorkplace;
            possibleWorkplace.AddCitizen(this);
            workCoords = map.getTileIndex(workplace);
            if (!(workplace.getPeople().isEmpty()) && workplace.getLevel() == 0) {
                workplace.Upgrade();
            }
            return workplace;
        } else {
            if (!possible_workplaces.isEmpty()) {
                workplace = possible_workplaces.get(0);
                workCoords = map.getTileIndex(workplace);
                return workplace;
            } else {
                return null;
            }
        }
    }

    public void leaveCity() {
        if (workplace != null) {
            workplace.RemoveCitizen(this);
        }
        if (home != null) {
            home.RemoveCitizen(this);
        }
    }

}

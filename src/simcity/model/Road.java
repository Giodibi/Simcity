package simcity.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import simcity.view.Sprite;

public class Road extends Sprite {

    private int cost;
    private int fee;

    public Road() {
        super();
    }

    public Road(int x, int y, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, "data/Sprites/Road/road_xing_res.png", "data/Sounds/Road/PassingCars_16.wav", map, speed);
        cost = 900;
        fee = 100;
        if (speed == 1) {
            playS("data/Sounds/Road/PassingCars_16.wav");
        } else if (speed == 2) {
            playS("data/Sounds/Road/f_PassingCars_16.wav");
        } else if (speed == 3) {
            playS("data/Sounds/Road/ff_PassingCars_16.wav");
        }

    }

    public Road(int x, int y, String image, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        super(x, y, image, "data/Sounds/Road/PassingCars_16.wav", map, speed);
        cost = 900;
        fee = 100;
        if (speed == 1) {
            playS("data/Sounds/Road/PassingCars_16.wav");
        } else if (speed == 2) {
            playS("data/Sounds/Road/f_PassingCars_16.wav");
        } else if (speed == 3) {
            playS("data/Sounds/Road/ff_PassingCars_16.wav");
        }
    }

    public Road(int x, int y, String image, Map map, boolean sound, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(x, y, image, "data/Sounds/Road/PassingCars_16.wav", map, speed);
        cost = 900;
        fee = 100;
    }

    public int getCost() {
        return cost;
    }

    public int getFee() {
        return fee;
    }

    public void SelectImage(boolean prev) {
        boolean[] sideRoads = new boolean[4];
        int[] position = map.getTileIndex(this);
        int x = position[0];
        int y = position[1];

        //X3X
        //2R0   sideRoads az adott indexén az adott mezőt nézi, hogy út-e
        //X1X
        Sprite[][] map_array = map.getMap_array();
        if (x + 1 > 0 && x + 1 < map.getSize() && map_array[x + 1][y] instanceof Road) {
            sideRoads[0] = true;
        }
        if (x - 1 >= 0 && x - 1 < map.getSize() && map_array[x - 1][y] instanceof Road) {
            sideRoads[2] = true;
        }
        if (y + 1 > 0 && y + 1 < map.getSize() && map_array[x][y + 1] instanceof Road) {
            sideRoads[1] = true;
        }
        if (y - 1 >= 0 && y - 1 < map.getSize() && map_array[x][y - 1] instanceof Road) {
            sideRoads[3] = true;
        }

        int roadType = 0;

        if (sideRoads[2] && sideRoads[3] && !sideRoads[0] && !sideRoads[1]) {
            roadType = 1;
        }
        if (sideRoads[2] && sideRoads[1] && !sideRoads[0] && !sideRoads[3]) {
            roadType = 2;
        }
        if (sideRoads[0] && sideRoads[1] && !sideRoads[3] && !sideRoads[2]) {
            roadType = 3;
        }
        if (sideRoads[0] && sideRoads[3] && !sideRoads[1] && !sideRoads[2]) {
            roadType = 4;
        }
        if (sideRoads[3] && !sideRoads[0] && !sideRoads[1] && !sideRoads[2]) {
            roadType = 5;
        }
        if (sideRoads[2] && !sideRoads[0] && !sideRoads[1] && !sideRoads[3]) {
            roadType = 6;
        }
        if (sideRoads[1] && !sideRoads[0] && !sideRoads[2] && !sideRoads[3]) {
            roadType = 7;
        }
        if (sideRoads[0] && !sideRoads[1] && !sideRoads[2] && !sideRoads[3]) {
            roadType = 8;
        }
        if (sideRoads[1] && sideRoads[3] && !sideRoads[0] && !sideRoads[2]) {
            roadType = 9;
        }
        if (sideRoads[0] && sideRoads[2] && !sideRoads[1] && !sideRoads[3]) {
            roadType = 10;
        }
        if (sideRoads[0] && sideRoads[1] && sideRoads[2] && !sideRoads[3]) {
            roadType = 11;
        }
        if (sideRoads[0] && sideRoads[1] && sideRoads[3] && !sideRoads[2]) {
            roadType = 12;
        }
        if (sideRoads[0] && sideRoads[2] && sideRoads[3] && !sideRoads[1]) {
            roadType = 13;
        }
        if (sideRoads[1] && sideRoads[2] && sideRoads[3] && !sideRoads[0]) {
            roadType = 14;
        }

        switch (roadType) {
            case 1: ///corner fent-bal út
                setImage(new ImageIcon("data/Sprites/Road/road_corner_d_res.png").getImage());
                if (!prev) {
                    if (x - 1 >= 0 && x - 1 < map.getSize()) {
                        ((Road) map_array[x - 1][y]).SelectImage(true);
                    }
                    if (y - 1 >= 0 && y - 1 < map.getSize()) {
                        ((Road) map_array[x][y - 1]).SelectImage(true);
                    }
                }
                return;
            case 2: ///corner bal-lent út
                setImage(new ImageIcon("data/Sprites/Road/road_corner_a_res.png").getImage());
                if (!prev) {
                    if (x - 1 >= 0 && x - 1 < map.getSize()) {
                        ((Road) map_array[x - 1][y]).SelectImage(true);
                    }
                    if (y + 1 >= 0 && y + 1 < map.getSize()) {
                        ((Road) map_array[x][y + 1]).SelectImage(true);
                    }
                }
                return;
            case 3: ///corner lent-jobb út
                setImage(new ImageIcon("data/Sprites/Road/road_corner_b_res.png").getImage());
                if (!prev) {
                    if (x + 1 >= 0 && x + 1 < map.getSize()) {
                        ((Road) map_array[x + 1][y]).SelectImage(true);
                    }
                    if (y + 1 >= 0 && y + 1 < map.getSize()) {
                        ((Road) map_array[x][y + 1]).SelectImage(true);
                    }
                }
                return;
            case 4: ///corner jobb-fent út
                setImage(new ImageIcon("data/Sprites/Road/road_corner_c_res.png").getImage());
                if (!prev) {
                    if (x + 1 >= 0 && x + 1 < map.getSize()) {
                        ((Road) map_array[x + 1][y]).SelectImage(true);
                    }
                    if (y - 1 >= 0 && y - 1 < map.getSize()) {
                        ((Road) map_array[x][y - 1]).SelectImage(true);
                    }
                }
                return;
            case 5: ///zsákutca fent nyitva
                setImage(new ImageIcon("data/Sprites/Road/road_end_c_res.png").getImage());
                if (!prev) {
                    if (y - 1 >= 0 && y - 1 < map.getSize()) {
                        ((Road) map_array[x][y - 1]).SelectImage(true);
                    }
                }
                return;
            case 6: ///zsákutca bal nyitva
                setImage(new ImageIcon("data/Sprites/Road/road_end_d_res.png").getImage());
                if (!prev) {
                    if (x - 1 >= 0 && x - 1 < map.getSize()) {
                        ((Road) map_array[x - 1][y]).SelectImage(true);
                    }
                }
                return;
            case 7: ///zsákutca lent nyitva
                setImage(new ImageIcon("data/Sprites/Road/road_end_a_res.png").getImage());
                if (!prev) {
                    if (y + 1 >= 0 && y + 1 < map.getSize()) {
                        ((Road) map_array[x][y + 1]).SelectImage(true);
                    }
                }
                return;
            case 8: ///zsákutca jobb nyitva
                setImage(new ImageIcon("data/Sprites/Road/road_end_b_res.png").getImage());
                if (!prev) {
                    if (x + 1 >= 0 && x + 1 < map.getSize()) {
                        ((Road) map_array[x + 1][y]).SelectImage(true);
                    }
                }
                return;
            case 9: ///utca fent-lent nyitva
                setImage(new ImageIcon("data/Sprites/Road/road_straight_a_res.png").getImage());
                if (!prev) {
                    if (y - 1 >= 0 && y - 1 < map.getSize()) {
                        ((Road) map_array[x][y - 1]).SelectImage(true);
                    }
                    if (y + 1 >= 0 && y + 1 < map.getSize()) {
                        ((Road) map_array[x][y + 1]).SelectImage(true);
                    }
                }
                return;
            case 10: ///utca bal-jobb nyitva
                setImage(new ImageIcon("data/Sprites/Road/road_straight_b_res.png").getImage());
                if (!prev) {
                    if (x + 1 >= 0 && x + 1 < map.getSize()) {
                        ((Road) map_array[x + 1][y]).SelectImage(true);
                    }
                    if (x - 1 >= 0 && x - 1 < map.getSize()) {
                        ((Road) map_array[x - 1][y]).SelectImage(true);
                    }
                }
                return;
            case 11: ///kereszteződés fent zárva
                setImage(new ImageIcon("data/Sprites/Road/road_t_a_res.png").getImage());
                if (!prev) {
                    if (x + 1 >= 0 && x + 1 < map.getSize()) {
                        ((Road) map_array[x + 1][y]).SelectImage(true);
                    }
                    if (x - 1 >= 0 && x - 1 < map.getSize()) {
                        ((Road) map_array[x - 1][y]).SelectImage(true);
                    }
                    if (y + 1 >= 0 && y + 1 < map.getSize()) {
                        ((Road) map_array[x][y + 1]).SelectImage(true);
                    }
                }
                return;
            case 12: ///kereszteződés bal zárva
                setImage(new ImageIcon("data/Sprites/Road/road_t_b_res.png").getImage());
                if (!prev) {
                    if (x + 1 >= 0 && x + 1 < map.getSize()) {
                        ((Road) map_array[x + 1][y]).SelectImage(true);
                    }
                    if (y - 1 >= 0 && y - 1 < map.getSize()) {
                        ((Road) map_array[x][y - 1]).SelectImage(true);
                    }
                    if (y + 1 >= 0 && y + 1 < map.getSize()) {
                        ((Road) map_array[x][y + 1]).SelectImage(true);
                    }
                }
                return;
            case 13: ///kereszteződés lent zárva
                setImage(new ImageIcon("data/Sprites/Road/road_t_c_res.png").getImage());
                if (!prev) {
                    if (x + 1 >= 0 && x + 1 < map.getSize()) {
                        ((Road) map_array[x + 1][y]).SelectImage(true);
                    }
                    if (x - 1 >= 0 && x - 1 < map.getSize()) {
                        ((Road) map_array[x - 1][y]).SelectImage(true);
                    }
                    if (y - 1 >= 0 && y - 1 < map.getSize()) {
                        ((Road) map_array[x][y - 1]).SelectImage(true);
                    }
                }
                return;
            case 14: ///kereszteződés jobb zárva
                setImage(new ImageIcon("data/Sprites/Road/road_t_d_res.png").getImage());
                if (!prev) {
                    if (x + 1 >= 0 && x + 1 < map.getSize()) {
                        ((Road) map_array[x + 1][y]).SelectImage(true);
                    }
                    if (y - 1 >= 0 && y - 1 < map.getSize()) {
                        ((Road) map_array[x][y - 1]).SelectImage(true);
                    }
                    if (y + 1 >= 0 && y + 1 < map.getSize()) {
                        ((Road) map_array[x][y + 1]).SelectImage(true);
                    }
                }
                return;
            default: ///kereszteződés
                setImage(new ImageIcon("data/Sprites/Road/road_xing_res.png").getImage());
                if (!prev) {
                    if (x + 1 >= 0 && x + 1 < map.getSize() && map_array[x + 1][y] instanceof Road) {
                        ((Road) map_array[x + 1][y]).SelectImage(true);
                    }
                    if (y - 1 >= 0 && y - 1 < map.getSize() && map_array[x][y - 1] instanceof Road) {
                        ((Road) map_array[x][y - 1]).SelectImage(true);
                    }
                    if (y + 1 >= 0 && y + 1 < map.getSize() && map_array[x][y + 1] instanceof Road) {
                        ((Road) map_array[x][y + 1]).SelectImage(true);
                    }
                    if (x - 1 >= 0 && x - 1 < map.getSize() && map_array[x - 1][y] instanceof Road) {
                        ((Road) map_array[x - 1][y]).SelectImage(true);
                    }
                }
                return;
        }
    }

    public Ground Revert() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        Ground ground = null;
        try {
            int[] position = map.getTileIndex(this);
            int x = position[0];
            int y = position[1];

            if (!canRoadDeleted(x, y)) {
                //nem lehet, skip
            } else {
                ground = new Ground(this.x, this.y, this.map, this.speed);
                map.setTile(x, y, ground);
                if (x + 1 >= 0 && x + 1 < map.getSize() && map.getMap_array()[x + 1][y] instanceof Road) {
                    ((Road) map.getMap_array()[x + 1][y]).SelectImage(true);
                }
                if (y - 1 >= 0 && y - 1 < map.getSize() && map.getMap_array()[x][y - 1] instanceof Road) {
                    ((Road) map.getMap_array()[x][y - 1]).SelectImage(true);
                }
                if (y + 1 >= 0 && y + 1 < map.getSize() && map.getMap_array()[x][y + 1] instanceof Road) {
                    ((Road) map.getMap_array()[x][y + 1]).SelectImage(true);
                }
                if (x - 1 >= 0 && x - 1 < map.getSize() && map.getMap_array()[x - 1][y] instanceof Road) {
                    ((Road) map.getMap_array()[x - 1][y]).SelectImage(true);
                }
                if (speed == 1) {
                    play("data/Sounds/Destroy/Explosion_16.wav");
                } else if (speed == 2) {
                    play("data/Sounds/Destroy/f_Explosion_16.wav");
                } else if (speed == 3) {
                    play("data/Sounds/Destroy/ff_Explosion_16.wav");
                }
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Residential.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Residential.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Residential.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ground;
    }

    public boolean canRoadDeleted(int x, int y) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //every neighbour house/jobplace has a neighbour road that is connected to 00?
        //csak olyan ahol van már lakó/dolgozó kéne nézni 
        //saját magát ne nézze mint jó út a szomszédjainak, 00-hoz
        boolean canDelete = true;

        //neighbour is house/jobplace
        if (x + 1 >= 0 && x + 1 < map.getSize() && map.getMap_array()[x + 1][y] instanceof Zone) {
            //4 szomszéd vmelyikje road e && vmelyik csatl e 00
            canDelete = canDelete && neighbourIsRoadConnectedOr(x + 1, y, x, y);
        }
        if (y - 1 >= 0 && y - 1 < map.getSize() && map.getMap_array()[x][y - 1] instanceof Zone) {
            //4 szomszéd vmelyikje road e && vmelyik csatl e 00
            canDelete = canDelete && neighbourIsRoadConnectedOr(x, y - 1, x, y);
        }
        if (y + 1 >= 0 && y + 1 < map.getSize() && map.getMap_array()[x][y + 1] instanceof Zone) {
            //4 szomszéd vmelyikje road e && vmelyik csatl e 00
            canDelete = canDelete && neighbourIsRoadConnectedOr(x, y + 1, x, y);
        }
        if (x - 1 >= 0 && x - 1 < map.getSize() && map.getMap_array()[x - 1][y] instanceof Zone) {
            //4 szomszéd vmelyikje road e && vmelyik csatl e 00
            canDelete = canDelete && neighbourIsRoadConnectedOr(x - 1, y, x, y);
        }

        return canDelete;
    }

    public boolean neighbourIsRoadConnectedOr(int x, int y, int delX, int delY) { //épülethez van jó út (egy elég), ami nem a törlendő út
        boolean canDelete = false;
        if (x + 1 >= 0 && x + 1 < map.getSize() && map.getMap_array()[x + 1][y] instanceof Road && !(x + 1 == delX && y == delY)) {
            canDelete = canDelete || isConnectedTo00(x + 1, y, delX, delY);
            return true;
        }
        if (y - 1 >= 0 && y - 1 < map.getSize() && map.getMap_array()[x][y - 1] instanceof Road && !(x == delX && y - 1 == delY)) {
            canDelete = canDelete || isConnectedTo00(x, y - 1, delX, delY);
            return true;
        }
        if (y + 1 >= 0 && y + 1 < map.getSize() && map.getMap_array()[x][y + 1] instanceof Road && !(x == delX && y + 1 == delY)) {
            canDelete = canDelete || isConnectedTo00(x, y + 1, delX, delY);
            return true;
        }
        if (x - 1 >= 0 && x - 1 < map.getSize() && map.getMap_array()[x - 1][y] instanceof Road && !(x + 1 == delX && y == delY)) {
            canDelete = canDelete || isConnectedTo00(x - 1, y, delX, delY);
            return true;
        }

        return canDelete;
    }

    public boolean isConnectedTo00(int x, int y, int delX, int delY) {
        //ha delX, delY akkor az az út nem jó 00ba
        boolean isConnected00 = false;
        int[][] roads = new int[20][20];

        Sprite[][] mapSprite = map.getMap_array(); //minden útrombolásnál megnézzük hol van út a térképen
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (mapSprite[i][j] instanceof Road) {
                    roads[i][j] = 1;
                } else {
                    roads[i][j] = 0;
                }
            }
        }

        int[][] result = new int[20][20];

        ArrayList<int[]> temp = new ArrayList<int[]>();
        ArrayList<int[]> list = new ArrayList<int[]>();

        dfs(roads, 0, 0, temp, list);

        for (int i = 0; i < list.size(); i++) {
            result[list.get(i)[0]][list.get(i)[1]] = 1;
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (result[i][j] == 1) {
                    return true;
                }
            }
        }

        return false;
    }

    public void dfs(int[][] matrix, int i, int j,
            ArrayList<int[]> temp, ArrayList<int[]> list) {

        int m = matrix.length;

        if (i == m - 1 && j == m - 1) {
            list.clear();
            list.addAll(temp);
            return;
        }

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};

        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];

            if (x >= 0 && y >= 0 && x <= m - 1 && y <= m - 1 && matrix[x][y] == 1) {
                temp.add(new int[]{x, y});
                int prev = matrix[x][y];
                matrix[x][y] = 0;

                dfs(matrix, x, y, temp, list);

                matrix[x][y] = prev;
                temp.remove(temp.size() - 1);
            }
        }
    }

    @Override
    public void playL() {
        if (speed == 1) {
            play("data/Sounds/Road/PassingCars_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Road/f_PassingCars_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Road/ff_PassingCars_16.wav");
        }
    }

    @Override
    public void playL(int speed) {
        if (speed == 1) {
            play("data/Sounds/Road/PassingCars_16.wav");
        } else if (speed == 2) {
            play("data/Sounds/Road/f_PassingCars_16.wav");
        } else if (speed == 3) {
            play("data/Sounds/Road/ff_PassingCars_16.wav");
        }
    }

}

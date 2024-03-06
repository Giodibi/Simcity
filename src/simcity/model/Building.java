package simcity.model;

import java.io.IOException;
import java.util.Comparator;
import java.util.Stack;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import simcity.view.Sprite;

public abstract class Building extends Sprite {

    protected int cost;
    protected int fee;
    protected int range;

    public Building() {
        super();
    }

    public Building(int x, int y, String image, String soundFile, Map map, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(x, y, image, soundFile, map, speed);
        cost = 0;
        fee = 0;
        range = 0;
    }

    static class Pair {

        int first, second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    static class Cell {

        int parent_i, parent_j;
        double f, g, h;

        Cell(int parent_i, int parent_j, double f, double g, double h) {
            this.parent_i = parent_i;
            this.parent_j = parent_j;
            this.f = f;
            this.g = g;
            this.h = h;
        }

        Cell() {
            parent_i = -1;
            parent_j = -1;
            f = Double.MAX_VALUE;
            g = Double.MAX_VALUE;
            h = Double.MAX_VALUE;
        }
    }

    static Comparator<PPair> myComparator = new Comparator<PPair>() {
        public int compare(PPair obj1, PPair obj2) {
            // Rendezési szabályok szerinti összehasonlítás
            // Pl. obj1 és obj2 közötti összehasonlítás eredménye:
            //   negatív, ha obj1 kisebb,
            //   pozitív, ha obj1 nagyobb,
            //   0, ha obj1 és obj2 egyenlő
            return (int) Math.round(obj1.getFirst() - obj2.getFirst());
        }
    };

    static class PPair implements Comparable<PPair> {

        private double first;
        private Pair second;

        PPair(double first, Pair second) {
            this.first = first;
            this.second = second;
        }

        double getFirst() {
            return first;
        }

        Pair getSecond() {
            return second;
        }

        @Override
        public int compareTo(PPair other) {
            return (int) Math.round(this.getFirst() - other.getFirst());
        }
    }

    public int getCost() {
        return cost;
    }

    public int getFee() {
        return fee;
    }

    public void Doing() {
    }

    public Ground Revert() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Ground ground = null;
        int[] index = map.getTileIndex(this);

        for (Sprite[] sprites : map.getMap_array()) {
            for (Sprite sprite : sprites) {
                if (sprite instanceof Zone) {
                    for (Person person : ((Zone) sprite).getPeople()) {
                        person.removeHappinessMod(this);
                    }
                }
            }
        }

        try {
            ground = new Ground(this.x, this.y, this.map, this.speed);
            map.setTile(index[0], index[1], ground);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Residential.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ground;
    }

    private boolean isValid(int row, int col) {
        // Megnézzük, hogy valid-e az adott mező

        Sprite[][] grid = map.getMap_array();

        return (row >= 0) && (row < grid.length) && (col >= 0)
                && (col < grid[0].length || !(grid[row][col] instanceof Ground
                || grid[row][col] instanceof Forest
                || grid[row][col] instanceof Road));
    }

    private boolean isUnBlocked(Sprite[][] grid, int row, int col) {
        // Returns true if the cell is not blocked else false
        if (grid[row][col] instanceof Ground
                || grid[row][col] instanceof Forest
                || grid[row][col] instanceof Road) {
            return (true);
        } else {
            return (false);
        }
    }

    private boolean isDestination(int row, int col, int destRow, int destCol) {
        if (row == destRow && col == destCol) {
            return (true);
        } else {
            return (false);
        }
    }

    private double calculateHValue(int row, int col, int destRow, int destCol) {
        // Return using the distance formula
        return Math.abs(row - destRow) + Math.abs(col - destCol);
    }

    private void tracePath(Cell[][] cellDetails, Pair dest) {
        int row = dest.first;
        int col = dest.second;

        Stack<Pair> Path = new Stack<>();

        while (!(cellDetails[row][col].parent_i == row
                && cellDetails[row][col].parent_j == col)) {
            Path.push(new Pair(row, col));
            int temp_row = cellDetails[row][col].parent_i;
            int temp_col = cellDetails[row][col].parent_j;
            row = temp_row;
            col = temp_col;
        }

        return;
    }

// A Function to find the shortest path between
// a given source cell to a destination cell according
// to A* Search Algorithm
    public boolean aStarSearch(Sprite[][] grid, Pair src, Pair dest) {
        int ROW = map.getMap_array().length;
        int COL = map.getMap_array()[0].length;

        // If the source is out of range
        if (!isValid(src.first, src.second)) {
            return false;
        }

        // If the destination is out of range
        if (!isValid(dest.first, dest.second)) {
            return false;
        }

        // If the destination cell is the same as source cell
        if (isDestination(src.first, src.second, dest.first, dest.second)) {
            return false;
        }

        // Create a closed list and initialise it to false which
        // means that no cell has been included yet This closed
        // list is implemented as a boolean 2D array
        boolean[][] closedList = new boolean[ROW][COL];
        Cell[][] cellDetails = new Cell[ROW][COL];

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                cellDetails[i][j] = new Cell();
            }
        }

        int i, j;

        i = src.first;
        j = src.second;
        cellDetails[i][j].f = 0.0;
        cellDetails[i][j].g = 0.0;
        cellDetails[i][j].h = 0.0;
        cellDetails[i][j].parent_i = i;
        cellDetails[i][j].parent_j = j;

        TreeSet<PPair> openList = new TreeSet<>();

        // Kezdő cella hozzáadása az openList halmazhoz, 'f' értéke 0
        openList.add(new PPair(0.0, new Pair(i, j)));

        boolean foundDest = false;

        while (!openList.isEmpty()) {
            PPair p = openList.first();

            i = p.second.first;
            j = p.second.second;
            closedList[i][j] = true;

            double gNew, hNew, fNew;

            openList.remove(p);

            // North
            if (isValid(i - 1, j)) {
                if (isDestination(i - 1, j, dest.first, dest.second)) {
                    cellDetails[i - 1][j].parent_i = i;
                    cellDetails[i - 1][j].parent_j = j;
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return foundDest;
                } else if (!closedList[i - 1][j] && isUnBlocked(grid, i - 1, j)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i - 1, j, dest.first, dest.second);
                    fNew = gNew + hNew;

                    if (cellDetails[i - 1][j].f == Double.MAX_VALUE || cellDetails[i - 1][j].f > fNew) {
                        openList.add(new PPair(fNew, new Pair(i - 1, j)));
                        cellDetails[i - 1][j].f = fNew;
                        cellDetails[i - 1][j].g = gNew;
                        cellDetails[i - 1][j].h = hNew;
                        cellDetails[i - 1][j].parent_i = i;
                        cellDetails[i - 1][j].parent_j = j;
                    }
                }
            }

            // South
            if (isValid(i + 1, j)) {
                if (isDestination(i + 1, j, dest.first, dest.second)) {
                    cellDetails[i + 1][j].parent_i = i;
                    cellDetails[i + 1][j].parent_j = j;
                    System.out.println("Destination found");
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return foundDest;
                } else if (!closedList[i + 1][j] && isUnBlocked(grid, i + 1, j)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i + 1, j, dest.first, dest.second);
                    fNew = gNew + hNew;

                    if (cellDetails[i + 1][j].f == Double.MAX_VALUE || cellDetails[i + 1][j].f > fNew) {
                        openList.add(new PPair(fNew, new Pair(i + 1, j)));
                        cellDetails[i + 1][j].f = fNew;
                        cellDetails[i + 1][j].g = gNew;
                        cellDetails[i + 1][j].h = hNew;
                        cellDetails[i + 1][j].parent_i = i;
                        cellDetails[i + 1][j].parent_j = j;
                    }
                }
            }

            // East
            if (isValid(i, j + 1)) {
                if (isDestination(i, j + 1, dest.first, dest.second)) {
                    cellDetails[i][j + 1].parent_i = i;
                    cellDetails[i][j + 1].parent_j = j;
                    System.out.println("Destination found");
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return foundDest;
                } else if (!closedList[i][j + 1] && isUnBlocked(grid, i, j + 1)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i, j + 1, dest.first, dest.second);
                    fNew = gNew + hNew;

                    if (cellDetails[i][j + 1].f == Double.MAX_VALUE || cellDetails[i][j + 1].f > fNew) {
                        openList.add(new PPair(fNew, new Pair(i, j + 1)));
                        cellDetails[i][j + 1].f = fNew;
                        cellDetails[i][j + 1].g = gNew;
                        cellDetails[i][j + 1].h = hNew;
                        cellDetails[i][j + 1].parent_i = i;
                        cellDetails[i][j + 1].parent_j = j;
                    }
                }
            }

            // West
            if (isValid(i, j - 1)) {
                if (isDestination(i, j - 1, dest.first, dest.second)) {
                    cellDetails[i][j - 1].parent_i = i;
                    cellDetails[i][j - 1].parent_j = j;
                    System.out.println("Destination found");
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return foundDest;
                } else if (!closedList[i][j - 1] && isUnBlocked(grid, i, j - 1)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i, j - 1, dest.first, dest.second);
                    fNew = gNew + hNew;

                    if (cellDetails[i][j - 1].f == Double.MAX_VALUE || cellDetails[i][j - 1].f > fNew) {
                        openList.add(new PPair(fNew, new Pair(i, j - 1)));
                        cellDetails[i][j - 1].f = fNew;
                        cellDetails[i][j - 1].g = gNew;
                        cellDetails[i][j - 1].h = hNew;
                        cellDetails[i][j - 1].parent_i = i;
                        cellDetails[i][j - 1].parent_j = j;
                    }
                }
            }
        }

        if (!foundDest) {
            System.out.println("Destination not found");
        }
        return foundDest;
    }

    @Override
    public void playL() {
        play("data/Sounds/Building/Stadium/Supporters_16.wav");
    }

    @Override
    public void playL(int speed) {
        play("data/Sounds/Building/Stadium/Supporters_16.wav");
    }

}

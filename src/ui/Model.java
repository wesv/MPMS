package ui;


import logic.Location;

import java.util.HashMap;
import java.util.Map;

public class Model {
    public enum Status {
        UNFLIPPED (-1),
        FLAGGED (-2),
        MINE (-3),
        CLICKED_MINE (-4),
        FLAGGED_MINE (-5),
        INVALID_LOCATION (-6),
        ZERO_NEARBY_MINES(0),
        ONE_NEARBY_MINES(1),
        TWO_NEARBY_MINES(2),
        THREE_NEARBY_MINES(3),
        FOUR_NEARBY_MINES(4),
        FIVE_NEARBY_MINES(5),
        SIX_NEARBY_MINES(6),
        SEVEN_NEARBY_MINES(7),
        EIGHT_NEARBY_MINES(8);

        private static Map<Integer, Status> map = new HashMap<>();

        static {
            for(Status s: Status.values()) {
                map.put(s.state, s);
            }
        }

        private int state;

        Status(int value) {
            this.state = value;
        }

        public int value() {
            return state;
        }

        public static Status valueOf(int i) {
            return map.get(i);
        }
    }


    private Status[][] tileStatus;
    private int numMines;

    /**
     * @param size the size of the grid. The grid is a square with side length of size
     */
    Model(int size, int mines) {
        numMines = mines;

        tileStatus = new Status[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                tileStatus[i][j] = Status.UNFLIPPED;
            }
        }
    }

    void setStatus(int row, int col, Status status) {
        tileStatus[row][col] = status;
    }

    public Status getStatus(int row, int col) {
        return tileStatus[row][col];
    }

    public Status getStatus(Location l) {
        if(isValid(l)) {
            return tileStatus[l.X()][l.Y()];
        }
        return Status.INVALID_LOCATION;
    }

    public boolean isFlipped(int row, int col) {
        return Integer.parseInt(tileStatus[row][col].name()) >= 0;
    }

    public boolean isValid(Location l) {
        return l.X() >= 0 && l.X() < tileStatus.length
                && l.Y() >= 0 && l.Y() < tileStatus[0].length;
    }

    public int getSize() {
        return tileStatus.length;
    }

    int numMines() {
        return numMines;
    }
}

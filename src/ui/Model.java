package ui;


public class Model {
    static final int UNFLIPPED = -1;
    static final int FLAGGED = -2;
    static final int MINE = -3;
    static final int CLICKED_MINE = -4;
    static final int FLAGGED_MINE = -5;

    private int[][] tileStatus;
    private int numMines;

    /**
     * @param size the size of the grid. The grid is a square with side length of size
     */
    Model(int size, int mines) {
        numMines = mines;

        tileStatus = new int[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                tileStatus[i][j] = UNFLIPPED;
            }
        }
    }

    void setStatus(int row, int col, int status) {
        tileStatus[row][col] = status;
    }

    int getStatus(int row, int col) {
        return tileStatus[row][col];
    }

    int getSize() {
        return tileStatus.length;
    }

    int numMines() {
        return numMines;
    }
}

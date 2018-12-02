package ui;


public class Model {
    static final int UNFLIPPED = -1;
    static final int FLAGGED = -2;
    static final int MINE = -3;

    private int[][] tileStatus;

    public Model(int size) {
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
}

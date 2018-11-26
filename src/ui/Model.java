package ui;


public class Model {
    static int UNFLIPPED = -1;
    static int FLAGGED = -2;
    static int FLIPPED = 0;

    private int[][] tileStatus;

    public Model(int size) {
        tileStatus = new int[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                tileStatus[i][j] = UNFLIPPED;
            }
        }
    }

    public void setTile(int row, int col, int status) {
        tileStatus[row][col] = status;
    }

    public int getStatus(int row, int col) {
        return tileStatus[row][col];
    }

    public int getSize() {
        return tileStatus.length;
    }
}

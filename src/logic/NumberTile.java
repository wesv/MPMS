package logic;

public class NumberTile extends Tile {
    private int _surroundingMines;

    public NumberTile(int numMines) {
        _surroundingMines = numMines;
    }

    public int getNumMines() {
        return _surroundingMines;
    }

    public String toString() {
        if(isFlagged())
            return "" + Tile.flagChar;
        if(hasBeenAccessed())
            return "\u001B[31m" + getNumMines() + "\u001B[0m";
        return "" + getNumMines();
    }
}

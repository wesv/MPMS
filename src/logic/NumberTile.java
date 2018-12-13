package logic;

public class NumberTile extends Tile {
    private int _surroundingMines;

    public NumberTile(int numMines) {
        _surroundingMines = numMines;
    }

    public int nearbyMines() {
        return _surroundingMines;
    }

    public String toString() {
        if(isFlagged())
            return "" + Tile.flagChar;
        if(hasBeenAccessed())
            return "\u001B[31m" + nearbyMines() + "\u001B[0m";
        return "" + nearbyMines();
    }
}

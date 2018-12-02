package logic;

import exceptions.GameEndException;

/**
 * The outline for any Tile. The board is filled with tiles.
 */
public abstract class Tile {

    public static char flagChar = '⚑';

    /* Whether this tile has been selected or not */
    protected boolean _accessed;

    /* Whether this tile is flagged or not */
    protected boolean _flag;

    /**
     * This method returns true if the flip method for this class has
     * been called, false otherwise.
     *
     * @return whether this object has been accessed or not.
     */
    public boolean hasBeenAccessed() {
        return _accessed;
    }

    /**
     * If this object has been flipped, return thisChar which should
     * be overwritten in any class that extends this one. Otherwise,
     * it returns an underscore.
     *
     * @return thisChar if accessed, an underscore otherwise.
     */
    public String toString() {
        if(this.isFlagged())
            return "" + flagChar;
        return "_";
    }

    /**
     *  Flips a tile over and sets hasBeenAccessed to true
     * @throws GameEndException if the game ends due to flipping this tile (ie hitting a mine)
     */
    public void flip() throws GameEndException {
        _accessed = true;
    }

    void toggleFlag() {
        _flag = !_flag;
    }

    public boolean isFlagged() {
        return _flag;
    }
}

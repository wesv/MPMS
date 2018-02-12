package logic;

import exceptions.GameEndException;

/**
 * The outline for any Tile. The board is filled with tiles.
 */
public abstract class Tile {
    /* The location of this tile */
    protected Location _loc;

    /* Whether this tile has been selected or not */
    protected boolean _accessed;

    /* The character to place when its flipped */
    protected String thisChar;

    /**
     *  Get a hard copy of this Tile's location
     *
     *  @Return This units Location
     */
    public Location getLocation() {
        return Location.create(_loc.X(), _loc.Y());
    }

    /**
     * Sets the location of this object.
     *
     * @param l the location to set to this object
     */
    public void setLocation(Location l) {
        _loc = l;
    }

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
    public String getChar() {
        if(this.hasBeenAccessed())
            return thisChar;
        return "_";
    }

    /**
     *  Flips a tile over and sets hasBeenAccessed to true
     * @throws GameEndException if the game ends due to flipping this tile (ie hitting a mine)
     */
    public void flip() throws GameEndException {
        _accessed = true;
    }
}

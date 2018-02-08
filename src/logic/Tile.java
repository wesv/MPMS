package logic;

import exceptions.GameEndException;

public abstract class Tile {
    protected Location _loc;
    protected boolean _accessed;
    protected String thisChar;

    public Location getLocation() {
        return Location.create(_loc.X(), _loc.Y());
    }

    public void setLocation(Location l) {
        _loc = l;
    }

    public boolean hasBeenAccessed() {
        return _accessed;
    }

    public String getChar() {
        if(this.hasBeenAccessed())
            return thisChar;
        return "_";
    }

    public void flip() throws GameEndException {
        _accessed = true;
    }
}

public abstract class Tile {
    protected Location _loc;
    protected boolean _accessed;

    public Location getLocation() {
        return Location.create(_loc.X(), _loc.Y());
    }

    public void setLocation(Location l) {
        _loc = l;
    }

    public boolean hasBeenAccessed() {
        return _accessed;
    }

    public void flip() throws GameEndException {
        _accessed = true;
    }
}

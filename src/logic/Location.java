package logic;

public class Location {
    private int _x;
    private int _y;

    public Location(int x, int y) {
        _x = x;
        _y = y;
    }

    public static Location create(int x, int y) {
        return new Location(x, y);
    }

    public static Location create() {
        return Location.create(0, 0);
    }

    public Location up() {
        return Location.create(this._x, this._y - 1);
    }

    public Location down() {
        return Location.create(this._x, this._y + 1);
    }

    public Location left() {
        return Location.create(this._x - 1, this._y);
    }

    public Location right() {
        return Location.create(this._x + 1, this._y);
    }

    public String toString() {
        return String.format("loc:{%d, %d)", _x, _y);
    }

    public int X() {
        return _x;
    }

    public int Y() {
        return _y;
    }
}

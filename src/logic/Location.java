package logic;

/**
 * <code>Location</code> is used to store x, y coordinates to be used for any grid-based system.
 * Here are the guidelines used for this class:
 *  1) (0, 0) is the top-left corner
 *  2) increasing the y value goes down on the grid
 *  3) increasing the x value goes right on the grid
 */
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

    /**
     * Get the location for going up on the grid. This location may or may not be valid.
     * @return A <code>Location</code> at coordinates (x-1, y)
     */
    public Location up() {
        return Location.create(this._x - 1, this._y);
    }

    public Location down() {
        return Location.create(this._x + 1, this._y);
    }

    public Location left() {
        return Location.create(this._x, this._y - 1);
    }

    public Location right() {
        return Location.create(this._x, this._y + 1);
    }

    public Location diagonalUpLeft() {
        return Location.create(this._x - 1, this._y - 1);
    }

    public Location diagonalUpRight() {
        return Location.create(this._x - 1, this._y + 1);
    }

    public Location diagonalDownLeft() {
        return Location.create(this._x + 1, this._y - 1);
    }

    public Location diagonalDownRight() {
        return Location.create(this._x + 1, this._y + 1);
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

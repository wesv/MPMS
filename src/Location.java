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

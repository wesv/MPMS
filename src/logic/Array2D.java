package logic;

public class Array2D<E> {

    private E[][] _elements;

    public Array2D(int n) {
        _elements = (E[][]) new Object[n][n];
    }

    public E at(Location l) {
        return _elements[l.X()][l.Y()];
    }

    public void putAt(E element, Location l) {
        _elements[l.X()][l.Y()] = element;
    }
}

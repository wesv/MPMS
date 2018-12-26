package logic;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * A 2-dimensional Array Object that can interface with the <code>Location</code> class
 * @param <E> The type of data to store in this array.
 * @see Location
 */
public class Array2D<E> {

    private E[][] _elements;

    public Array2D(int n) {
        _elements = (E[][]) new Object[n][n];
    }

    public E at(Location l) {
        return _elements[l.X()][l.Y()];
    }

    public E at(int x, int y) {
        return _elements[x][y];
    }

    @Deprecated
    public void putAt(E element, Location l) {
        _elements[l.X()][l.Y()] = element;
    }

    public void putAt(Location l, E element) {
        _elements[l.X()][l.Y()] = element;
    }

    public Stream<E> stream() {
        return Arrays.stream(_elements).flatMap(Arrays::stream);
    }
}

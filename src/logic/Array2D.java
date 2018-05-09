package logic;

import java.util.Arrays;
import java.util.stream.Stream;

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

    public Stream<E> stream() {
        return Arrays.stream(_elements).flatMap(Arrays::stream);
    }
}

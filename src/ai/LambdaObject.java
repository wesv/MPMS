package ai;

public class LambdaObject<T> {
    private T _t;

    public LambdaObject(T t) {
        this._t = t;
    }

    public T get() {
        return _t;
    }

    public void set(T t) {
        this._t = t;
    }
}
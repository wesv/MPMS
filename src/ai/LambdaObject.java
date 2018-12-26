package ai;

/**
 * An object to be used in order to get and set values inside of a Lambda function.
 * @param <T> the type to set and get.
 */
public class LambdaObject<T> {
    private T _t;

    /**
     * Initialize a new LambdaObject with its type set to T
     * @param t the Object
     */
    public LambdaObject(T t) {
        this._t = t;
    }

    /**
     * Get the object
     * @return the object
     */
    public T get() {
        return _t;
    }

    /**
     * Set the object.
     * @param t the object to set
     */
    public void set(T t) {
        this._t = t;
    }
}
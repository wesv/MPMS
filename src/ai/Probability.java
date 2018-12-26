package ai;

/**
 * This class stores and performs calculations on probabilities.
 */
public class Probability {

    /* The Probability */
    private double _p;

    public Probability(double value) {
        this._p = value;

    }
    /**
     * Multiply this probability with another probability. The equation used is 1-q_this*q_other where q = 1-p.
     * If given a null probability, the value of other will be 0%.
     * @param other The other Probability to Multiply against
     * @return a new Probability with the multiplied value
     */
    public Probability multiply(Probability other) {
        if(other == null)
            return this;

        return new Probability((1-(1-this._p)*(1-other._p)));
    }

    /**
     * Return the Probability value stored
     * @return the Probability of this object
     */
    public double prob() {
        return _p;
    }

    @Override
    public String toString() {
        return String.format("%.2f%%",  _p *100);
    }
}

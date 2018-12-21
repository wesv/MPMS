package ai;

public class Probability {

    private double _p;
    private boolean _negated;
    private boolean _virgin;

    public Probability(double value) {
        if(value > 1) System.err.println("value > 1 (" + value);
        this._p = value;
        _virgin = false;

    }

    public Probability negate() {
        _negated = !_negated;
        return this;
    }

    public Probability setAsVirgin() {
        _virgin = true;
        return this;
    }

    public boolean isVirgin() {
        return _virgin;
    }

    public Probability multiply(Probability other) {
        if(this._negated || other._negated)
            return new Probability((1-(1-this._p)*(1-other._p))).negate();
        return new Probability((1-(1-this._p)*(1-other._p)));
    }

    public double percent() {
        return _negated ?-1* _p : _p;
    }

    public String toString() {
        return String.format("%s%.4f%%", _negated ?"-":"", _p *100);
    }
}

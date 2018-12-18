package ai;

public class Probability {

    private double p;
    private boolean negated;

    public Probability(double value) {
        if(value > 1) System.err.println("value > 1 (" + value);
        this.p = value;

    }

    public Probability negate() {
        negated = !negated;
        return this;
    }

    public Probability multiply(Probability other) {
        if(this.negated || other.negated)
            return new Probability((1-(1-this.p)*(1-other.p))).negate();
        return new Probability((1-(1-this.p)*(1-other.p)));
    }

    public double percent() {
        return negated?-1*p:p;
    }

    public String toString() {
        return String.format("%s%.4f%%", negated?"-":"", p*100);
    }
}

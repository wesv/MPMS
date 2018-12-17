package ai;

public class Probability {

    public double p;

    public Probability(double value) {
        this.p = value;

    }

    public Probability multiply(Probability other) {
        return new Probability(1 - (1-this.p)*(1-other.p));

    }
}

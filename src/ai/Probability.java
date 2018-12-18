package ai;

public class Probability {

    public double p;

    public Probability(double value) {
        if(value > 1) throw new IllegalArgumentException("value > 1");
        this.p = value;

    }

    public Probability multiply(Probability other) {
        return new Probability(1 - (1-this.p)*(1-other.p));

    }

    public String toString() {
        return String.format("%.4f%%", p*100);
    }
}

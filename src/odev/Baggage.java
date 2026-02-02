package odev;

import java.io.Serializable;

public class Baggage implements Serializable {
    private double weight;
    public Baggage(double weight) { this.weight = weight; }
    public double getWeight() { return weight; }
}
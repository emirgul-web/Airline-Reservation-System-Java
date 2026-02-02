package odev;

import java.io.Serializable;

public class Route implements Serializable {
    private String departurePlace;
    private String arrivalPlace;

    public Route(String departure, String arrival) {
        this.departurePlace = departure;
        this.arrivalPlace = arrival;
    }

    public String getDeparture() { return departurePlace; }
    public String getArrival() { return arrivalPlace; }
}
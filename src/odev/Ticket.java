package odev;

import java.io.Serializable;

public class Ticket implements Serializable {
    private String ticketID;
    private Reservation reservation;
    private double price;
    private Baggage baggageAllowance;

    public Ticket(String id, Reservation reservation, double price, Baggage baggage) {
        this.ticketID = id;
        this.reservation = reservation;
        this.price = price;
        this.baggageAllowance = baggage;
    }
}
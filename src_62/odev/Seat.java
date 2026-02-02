package odev;

import java.io.Serializable;

public class Seat implements Serializable {
    private String seatNum; // örn: "15A"
    private SeatClass seatClass;
    private double price;
    private boolean reserveStatus;

    public Seat(String seatNum, SeatClass seatClass, double price) {
        this.seatNum = seatNum;
        this.seatClass = seatClass;
        this.price = price;
        this.reserveStatus = false;
    }

    public boolean isReserved() { return reserveStatus; }
    public void setReserved(boolean status) { this.reserveStatus = status; }
    public String getSeatNum() { return seatNum; }
    public SeatClass getSeatClass() { return seatClass; }
    public double getPrice() { return price; }
}
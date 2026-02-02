package odev;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    private String reservationCode;
    private Flight flight;
    private Passenger passenger;
    private Seat seat;
    private LocalDate dateOfReservation;

    public Reservation(String code, Flight flight, Passenger passenger, Seat seat) {
        this.reservationCode = code;
        this.flight = flight;
        this.passenger = passenger;
        this.seat = seat;
        this.dateOfReservation = LocalDate.now();
    }

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public LocalDate getDateOfReservation() {
		return dateOfReservation;
	}

	public void setDateOfReservation(LocalDate dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}
}
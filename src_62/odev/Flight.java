package odev;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Flight implements Serializable {
    private String flightNum;
    private Route route; 
    private LocalDate date;
    private LocalTime hour;
    private String duration;
    private Plane plane;

    public Flight(String flightNum, Route route, LocalDate date, LocalTime hour, String duration, Plane plane) {
        this.flightNum = flightNum;
        this.route = route;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
        this.plane = plane;
    }
    
    public Plane getPlane() { return plane; }
    public Route getRoute() { return route; }
    public LocalDate getDate() { return date; }
    public LocalTime getHour() { return hour; }
    public String getFlightNum() { return flightNum; }

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setHour(LocalTime hour) {
		this.hour = hour;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	
}
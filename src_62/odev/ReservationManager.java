package odev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
    private static final String FILE_NAME = "reservations.dat";
    private List<Reservation> reservations;

    public ReservationManager() {
        this.reservations = loadReservationsFromFile();
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
        saveReservations();
    }

    public void cancelReservation(Reservation r) {
        reservations.removeIf(res -> res.getReservationCode().equals(r.getReservationCode()));
        saveReservations();

        // KRİTİK: Koltuğu uçuş dosyasında boşalt
        FlightManager fm = new FlightManager();
        for (Flight f : fm.getFlights()) {
            if (f.getFlightNum().equals(r.getFlight().getFlightNum())) {
                Seat[][] matrix = f.getPlane().getSeatMatrix();
                for (Seat[] row : matrix) {
                    for (Seat s : row) {
                        if (s.getSeatNum().equals(r.getSeat().getSeatNum())) {
                            s.setReserved(false); // Koltuğu boşalt
                        }
                    }
                }
            }
        }
        fm.saveUpdates();
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void removeReservationsByFlight(String flightNum) {
        reservations.removeIf(res -> res.getFlight().getFlightNum().equals(flightNum));
        saveReservations();
    }

    private void saveReservations() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(new ArrayList<>(reservations));
        } catch (IOException e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    private List<Reservation> loadReservationsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Reservation>) ois.readObject();
        } catch (Exception e) { return new ArrayList<>(); }
    }
}
package odev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FlightManager {
    private static final String FILE_NAME = "flights.dat";
    private List<Flight> flights;

    public FlightManager() {
        this.flights = loadFlightsFromFile(); // Sadece constructor'da dosyadan yükle
    }

    public void addFlight(Flight f) {
        flights.add(f);
        saveFlights();
    }

    public void updateFlight(Flight updatedFlight) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNum().equals(updatedFlight.getFlightNum())) {
                flights.set(i, updatedFlight);
                saveFlights();
                return;
            }
        }
    }

    public void removeFlight(String flightNum) {
        flights.removeIf(f -> f.getFlightNum().equals(flightNum));
        saveFlights();
    }

    //  loadFlights() yerine mevcut listeyi döndür
    public List<Flight> getFlights() {
        return flights;
    }

    public void saveUpdates() {
        saveFlights();
    }

    private void saveFlights() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(new ArrayList<>(flights));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Flight> loadFlightsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Flight>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
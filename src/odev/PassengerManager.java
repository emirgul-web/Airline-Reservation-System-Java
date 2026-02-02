package odev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerManager {
    private static final String FILE_NAME = "passengers.dat";

    public void register(Passenger p) throws Exception {
        List<Passenger> list = loadAll();
        // İsim kontrolü (Aynı isimle iki kayıt olmasın diye)
        for(Passenger existing : list) {
            if(existing.getName().equalsIgnoreCase(p.getName())) 
                throw new Exception("This name is already registered!");
        }
        list.add(p);
        saveAll(list);
    }

    public Passenger login(String firstName, String password) {
        List<Passenger> list = loadAll();
        for(Passenger p : list) {
            if(p.getName().equalsIgnoreCase(firstName) && p.getPassword().equals(password)) {
                return p; // Giriş başarılı
            }
        }
        return null;
    }

    private void saveAll(List<Passenger> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(list);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    private List<Passenger> loadAll() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Passenger>) ois.readObject();
        } catch (Exception e) { return new ArrayList<>(); }
    }
}
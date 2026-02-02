package odev;

import java.io.Serializable;

public class Passenger implements Serializable {
    private String passengerID;
    private String name;
    private String surname;
    private String contactInfo;
    private String password; 

    public Passenger(String id, String name, String surname, String contact, String password) {
        this.passengerID = id;
        this.name = name;
        this.surname = surname;
        this.contactInfo = contact;
        this.password = password;
    }


    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getPassengerID() { return passengerID; }
    public String getContactInfo() { return contactInfo; }
    public String getPassword() { return password; }
}
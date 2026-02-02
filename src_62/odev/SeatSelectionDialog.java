package odev;



import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class SeatSelectionDialog extends JDialog {
    private Flight flight;
    private FlightManager flightManager;
    private Passenger loggedInUser;
    private boolean reservationMade = false;

    public SeatSelectionDialog(JFrame parent, Flight flight, FlightManager manager, Passenger user) {
        super(parent, "Seat Selection", true);
        this.flight = flight;
        this.flightManager = manager;
        this.loggedInUser = user;

        setSize(750, 800);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        Seat[][] seats = flight.getPlane().getSeatMatrix();
        JPanel seatPanel = new JPanel(new GridLayout(seats.length, seats[0].length, 5, 5));
        
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {
                Seat s = seats[i][j];
                JButton btn = new JButton(s.getSeatNum());
                
                // Doluluk kontrolü: Artık kalıcı olarak kırmızı kalacak
                if (s.isReserved()) {
                    btn.setBackground(Color.RED);
                    btn.setEnabled(false);
                } else {
                    btn.setBackground(s.getSeatClass() == SeatClass.BUSINESS ? Color.CYAN : Color.GREEN);
                }
                
                btn.setForeground(Color.BLACK);
                btn.addActionListener(e -> handleSeatClick(s, btn));
                seatPanel.add(btn);
            }
        }
        add(new JScrollPane(seatPanel), BorderLayout.CENTER);
    }

    private void handleSeatClick(Seat seat, JButton btn) {
        if (loggedInUser == null) {
            JOptionPane.showMessageDialog(this, "Admin mode: View only.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Book seat " + seat.getSeatNum() + "?");
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Koltuğu işaretle
            seat.setReserved(true); 
            
            // 2. Rezervasyonu kaydet
            Reservation res = new Reservation("RES-" + System.currentTimeMillis(), flight, loggedInUser, seat);
            new ReservationManager().addReservation(res);

            // 3. KRİTİK: Uçuş dosyasını (flights.dat) koltuk doluyken kaydet
            flightManager.saveUpdates(); 
            
            reservationMade = true;
            generateTicketFile(res, CalculatePrice.calculate(seat, new Baggage(15)));
            
            JOptionPane.showMessageDialog(this, "Reservation Successful!");
            dispose();
        }
    }

    private void generateTicketFile(Reservation res, double price) {
        try (FileWriter fw = new FileWriter(res.getReservationCode() + "_Ticket.txt")) {
            fw.write("YTÜ Airlines - Ticket\nCode: " + res.getReservationCode() + "\nSeat: " + res.getSeat().getSeatNum());
        } catch (IOException e) { e.printStackTrace(); }
    }

    public boolean isReservationMade() { return reservationMade; }
}
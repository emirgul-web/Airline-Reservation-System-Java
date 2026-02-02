package odev;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReservationSimulation extends JPanel {
    private boolean isSynchronized;
    private final Object lock = new Object();
    private List<JButton> seatButtons = new ArrayList<>(); 
    private int occupiedCount = 0;
    private JLabel countLabel;

    public ReservationSimulation(boolean isSync, JLabel countLabel) {
        this.isSynchronized = isSync;
        this.countLabel = countLabel;
        // 30 sıra x 6 koltuk = 180 koltuklu grid
        this.setLayout(new GridLayout(30, 6)); 

        for (int i = 0; i < 180; i++) {
            JButton btn = new JButton(String.valueOf(i + 1));
            btn.setBackground(Color.GREEN); // Yeşil = Boş
            // Butonları küçük tutmak için font ayarı
            btn.setFont(new Font("Arial", Font.PLAIN, 8));
            seatButtons.add(btn);
            this.add(btn);
        }
    }
    
    // Checkbox değişince modu güncellemek için setter
    public void setSynchronized(boolean isSync) {
        this.isSynchronized = isSync;
    }

    public void startSimulation() {
        // Önce temizle
        resetSimulation();
        
        // 90 Yolcu Thread'i oluştur ve başlat
        for (int i = 0; i < 90; i++) {
            new Thread(new PassengerTask()).start();
        }
    }
    
    private void resetSimulation() {
        occupiedCount = 0;
        countLabel.setText("Occupied: 0");
        for(JButton btn : seatButtons) {
            btn.setBackground(Color.GREEN);
        }
    }

    class PassengerTask implements Runnable {
        @Override
        public void run() {
            if (isSynchronized) {
                synchronized (lock) { 
                    bookRandomSeat();
                }
            } else {
                bookRandomSeat(); 
            }
        }

        private void bookRandomSeat() {
            Random random = new Random();
            int attempt = 0;
            
            while (attempt < 100) { 
                int seatIndex = random.nextInt(180);
                JButton seat = seatButtons.get(seatIndex);
                
                if (seat.getBackground() == Color.GREEN) {
                    try {
                        // Race condition ihtimalini artırmak için küçük gecikme
                        Thread.sleep(5); 
                    } catch (InterruptedException e) {}

                    seat.setBackground(Color.RED);
                    occupiedCount++;
                    
                    // Swing arayüzünü güncelle
                    SwingUtilities.invokeLater(() -> countLabel.setText("Occupied: " + occupiedCount));
                    break;
                }
                attempt++;
            }
        }
    }
}
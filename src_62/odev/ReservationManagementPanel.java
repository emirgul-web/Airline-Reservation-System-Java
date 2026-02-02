package odev;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReservationManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private Passenger loggedInUser;

    public ReservationManagementPanel(Passenger user) {
        this.loggedInUser = user;
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Res Code", "Passenger", "Flight", "Seat"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton refreshBtn = new JButton("Refresh List");
        JButton cancelBtn = new JButton("Cancel Reservation");
        
        refreshBtn.setForeground(Color.BLACK);
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setBackground(new Color(220, 20, 60));

        bottom.add(refreshBtn);
        bottom.add(cancelBtn);
        add(bottom, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadData());
        cancelBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String code = (String) tableModel.getValueAt(row, 0);
                ReservationManager rm = new ReservationManager();
                Reservation target = rm.getReservations().stream()
                        .filter(r -> r.getReservationCode().equals(code)).findFirst().orElse(null);
                if (target != null) {
                    rm.cancelReservation(target);
                    loadData();
                    JOptionPane.showMessageDialog(this, "Reservation cancelled and seat is now free.");
                }
            }
        });

        loadData();
    }

    public void loadData() {
        tableModel.setRowCount(0);
        List<Reservation> all = new ReservationManager().getReservations();
        for (Reservation r : all) {
            // ROL KONTROLÜ: Admin ise hepsini ekle, Yolcu ise sadece kendi ID'sini kontrol et
            if (loggedInUser == null || r.getPassenger().getPassengerID().equals(loggedInUser.getPassengerID())) {
                tableModel.addRow(new Object[]{
                    r.getReservationCode(),
                    r.getPassenger().getName() + " " + r.getPassenger().getSurname(),
                    r.getFlight().getFlightNum(),
                    r.getSeat().getSeatNum()
                });
            }
        }
    }
}
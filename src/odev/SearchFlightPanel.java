package odev;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchFlightPanel extends JPanel {

    private JComboBox<String> fromCombo;
    private JComboBox<String> toCombo;
    private JTable flightTable;
    private DefaultTableModel tableModel;
    private Passenger currentUser;

    public SearchFlightPanel(Passenger user) {
        this.currentUser = user;
        setLayout(new BorderLayout());

        // --- ÜST KISIM: ARAMA ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Flight Search Engine"));

        String[] cities = {"Istanbul", "Ankara", "Izmir", "London", "Berlin", "New York"};
        fromCombo = new JComboBox<>(cities);
        toCombo = new JComboBox<>(cities);
        toCombo.setSelectedIndex(1); 

        JButton searchBtn = new JButton("Search Flights");
        searchBtn.setBackground(new Color(70, 130, 180)); 
        searchBtn.setForeground(Color.BLACK); // Yazı Siyah
        searchBtn.setPreferredSize(new Dimension(150, 30));

        filterPanel.add(new JLabel("From:")); filterPanel.add(fromCombo);
        filterPanel.add(new JLabel("To:")); filterPanel.add(toCombo);
        filterPanel.add(searchBtn);
        add(filterPanel, BorderLayout.NORTH);

        // --- ORTA KISIM: TABLO ---
        String[] columns = {"Flight No", "Departure", "Arrival", "Date", "Time", "Duration"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        flightTable = new JTable(tableModel);
        add(new JScrollPane(flightTable), BorderLayout.CENTER);

        // --- ALT KISIM: DİNAMİK BUTON ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // KRİTİK DEĞİŞİKLİK: Admin ise "View Occupancy", Passenger ise "Book Seat"
        String btnText = (currentUser == null) ? "View Flight Occupancy" : "Select Flight & Book Seat";
        JButton actionBtn = new JButton(btnText);
        
        actionBtn.setBackground(new Color(46, 139, 87)); 
        actionBtn.setForeground(Color.BLACK); // Yazı Siyah
        actionBtn.setPreferredSize(new Dimension(220, 40));
        
        actionPanel.add(actionBtn);
        add(actionPanel, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> searchFlights());

        actionBtn.addActionListener(e -> {
            int selectedRow = flightTable.getSelectedRow();
            if (selectedRow != -1) {
                String flightNo = (String) tableModel.getValueAt(selectedRow, 0);
                FlightManager manager = new FlightManager();
                Flight selectedFlight = manager.getFlights().stream().filter(f -> f.getFlightNum().equals(flightNo)).findFirst().orElse(null);
                
                if (selectedFlight != null) {
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    // Koltuk seçim diyaloğunu açıyoruz (Admin için view-only zaten kurgulanmıştı)
                    SeatSelectionDialog dialog = new SeatSelectionDialog(topFrame, selectedFlight, manager, currentUser);
                    dialog.setVisible(true);
                    if (dialog.isReservationMade()) { searchFlights(); }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a flight from the list.");
            }
        });
    }

    private void searchFlights() {
        tableModel.setRowCount(0);
        String selectedFrom = (String) fromCombo.getSelectedItem();
        String selectedTo = (String) toCombo.getSelectedItem();
        FlightManager manager = new FlightManager();
        List<Flight> allFlights = manager.getFlights();
        for (Flight f : allFlights) {
            if (f.getRoute().getDeparture().equals(selectedFrom) && f.getRoute().getArrival().equals(selectedTo)) {
                tableModel.addRow(new Object[]{f.getFlightNum(), f.getRoute().getDeparture(), f.getRoute().getArrival(), f.getDate(), f.getHour(), f.getDuration()});
            }
        }
    }
}
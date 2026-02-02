package odev;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class AdminPanel extends JPanel {
    
    private JTextField flightNumField, dateField, timeField, durationField;
    private JComboBox<String> fromCombo, toCombo;
    private JTable flightTable;
    private DefaultTableModel tableModel;
    private FlightManager flightManager;
    private JButton updateBtn; 
    private JButton saveBtn;

    public AdminPanel() {
        this.flightManager = new FlightManager();
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Flight Editor"));
        
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        String[] cities = {"Istanbul", "Ankara", "Izmir", "London", "Berlin", "New York"};
        
        flightNumField = new JTextField();
        fromCombo = new JComboBox<>(cities);
        toCombo = new JComboBox<>(cities);
        dateField = new JTextField("2026-01-10");
        timeField = new JTextField("12:00");
        durationField = new JTextField("2h");

        formPanel.add(new JLabel("Flight No:")); formPanel.add(flightNumField);
        formPanel.add(new JLabel("From:")); formPanel.add(fromCombo);
        formPanel.add(new JLabel("To:")); formPanel.add(toCombo);
        formPanel.add(new JLabel("Date (yyyy-MM-dd):")); formPanel.add(dateField);
        formPanel.add(new JLabel("Time (HH:mm):")); formPanel.add(timeField);
        formPanel.add(new JLabel("Duration:")); formPanel.add(durationField);

        // BUTONLAR
        JPanel actionBtnContainer = new JPanel(new GridLayout(2, 1, 5, 5));
        
        saveBtn = new JButton("Add New Flight");
        saveBtn.setBackground(new Color(34, 139, 34));
        saveBtn.setForeground(Color.BLACK); 
        
        updateBtn = new JButton("Update Selected Flight");
        updateBtn.setBackground(new Color(255, 165, 0));
        updateBtn.setForeground(Color.BLACK);
        updateBtn.setEnabled(false); // BAŞLANGIÇTA KAPALI - Sadece Load edilince açılacak

        actionBtnContainer.add(saveBtn);
        actionBtnContainer.add(updateBtn);
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(actionBtnContainer, BorderLayout.SOUTH);

        // --- SAĞ PANEL: LİSTE ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Manage Flights"));
        
        tableModel = new DefaultTableModel(new String[]{"No", "From", "To", "Date"}, 0);
        flightTable = new JTable(tableModel);
        rightPanel.add(new JScrollPane(flightTable), BorderLayout.CENTER);
        
        JPanel bottomBtnPanel = new JPanel();
        JButton loadBtn = new JButton("Load to Edit");
        loadBtn.setForeground(Color.BLACK);
        
        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.setBackground(new Color(220, 20, 60));
        deleteBtn.setForeground(Color.BLACK);

        bottomBtnPanel.add(loadBtn);
        bottomBtnPanel.add(deleteBtn);
        rightPanel.add(bottomBtnPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // --- AKSİYONLAR ---
        saveBtn.addActionListener(e -> saveFlightAction(false));
        updateBtn.addActionListener(e -> saveFlightAction(true));
        deleteBtn.addActionListener(e -> deleteAction());
        
        // Load butonu: Veriyi yükler ve Update butonunu aktif eder
        loadBtn.addActionListener(e -> {
            int row = flightTable.getSelectedRow();
            if (row != -1) {
                String fNum = (String) tableModel.getValueAt(row, 0);
                Flight f = flightManager.getFlights().stream()
                        .filter(fl -> fl.getFlightNum().equals(fNum))
                        .findFirst().orElse(null);
                
                if (f != null) {
                    // Formu doldur
                    flightNumField.setText(f.getFlightNum());
                    flightNumField.setEditable(false); // ID değişemez
                    fromCombo.setSelectedItem(f.getRoute().getDeparture());
                    toCombo.setSelectedItem(f.getRoute().getArrival());
                    dateField.setText(f.getDate().toString());
                    timeField.setText(f.getHour().toString());
                    durationField.setText(f.getDuration());

                    // Buton durumlarını güncelle
                    updateBtn.setEnabled(true);
                    saveBtn.setEnabled(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a flight from the table first!");
            }
        });

        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        flightManager = new FlightManager();
        for (Flight f : flightManager.getFlights()) {
            tableModel.addRow(new Object[]{f.getFlightNum(), f.getRoute().getDeparture(), f.getRoute().getArrival(), f.getDate()});
        }
    }

    private void saveFlightAction(boolean isUpdate) {
        try {
            Route route = new Route((String)fromCombo.getSelectedItem(), (String)toCombo.getSelectedItem());
            Plane plane = new Plane("PL-" + flightNumField.getText(), "Boeing 737", 30, 6);
            
            Flight flight = new Flight(
                flightNumField.getText(), 
                route, 
                LocalDate.parse(dateField.getText()), 
                LocalTime.parse(timeField.getText()), 
                durationField.getText(), 
                plane
            );
            
            if (isUpdate) {
                flightManager.updateFlight(flight);
                JOptionPane.showMessageDialog(this, "Flight updated successfully!");
            } else {
                flightManager.addFlight(flight);
                JOptionPane.showMessageDialog(this, "New flight added!");
            }

            // İşlem sonrası formu sıfırla ve butonları eski haline getir
            resetForm();
            updateTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input error! Ensure yyyy-MM-dd and HH:mm formats.");
        }
    }

    private void resetForm() {
        flightNumField.setText("");
        flightNumField.setEditable(true);
        dateField.setText("2026-01-10");
        timeField.setText("12:00");
        durationField.setText("2h");
        updateBtn.setEnabled(false);
        saveBtn.setEnabled(true);
    }

    private void deleteAction() {
        int row = flightTable.getSelectedRow();
        if (row != -1) {
            String fNum = (String) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete flight " + fNum + "?");
            if (confirm == JOptionPane.YES_OPTION) {
                new ReservationManager().removeReservationsByFlight(fNum);
                flightManager.removeFlight(fNum);
                updateTable();
                resetForm();
            }
        }
    }
}
package odev;

import odev.ReportGenerator;

import javax.swing.*;
import java.awt.*;

/**
 * YTÜ Airlines - Ana Pencere
 * Bu sınıf hem Admin hem de Yolcu arayüzlerini yönetir.
 */
public class MainWindow extends JFrame {
    private Passenger currentUser;

    public MainWindow(String userType, Passenger user) {
        this.currentUser = user;
        
        // Pencere Ayarları
        setTitle("YTÜ Airlines - " + (user != null ? user.getName() : "Admin Mode"));
        setSize(1100, 750);
        
        //  Uygulamayı değil sadece pencereyi kapatır, böylece LoginScreen arkada açık kalır
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // ROL BAZLI SEKME YAPILANDIRMASI
        if ("Admin".equalsIgnoreCase(userType)) {
            // --- ADMIN AKIŞI ---
            // 1. Uçuş Yönetimi (Ekle/Sil/Güncelle)
            tabbedPane.addTab("Admin Panel (Flights)", new AdminPanel());
            
            // 2. Tüm Sistem Rezervasyonları (Global Görünüm)
            tabbedPane.addTab("Global Reservations", new ReservationManagementPanel(null)); 
            
            // 3. Doluluk Görüntüleme (Admin için Rezervasyon Yapılamayan Panel)
            tabbedPane.addTab("Flight Occupancy", new SearchFlightPanel(null));
            
            // 4. Teknik Simülasyonlar
            tabbedPane.addTab("Simulations", createSimulationPanel());
        } else {
            // --- YOLCU AKIŞI ---
            // 1. Uçuş Arama ve Rezervasyon Yapma
            tabbedPane.addTab("Search & Book", new SearchFlightPanel(currentUser));
            
            // 2. Kişisel Rezervasyonlarım
            tabbedPane.addTab("My Reservations", new ReservationManagementPanel(currentUser));
        }

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Multithreading ve Asenkron Görev senaryolarını içeren teknik panel
     */
    private JPanel createSimulationPanel() {
        JPanel container = new JPanel(new BorderLayout());
        
        // --- SENARYO 1: KOLTUK EŞZAMANLILIK (CONCURRENCY) ---
        JPanel simPanelContainer = new JPanel(new BorderLayout());
        simPanelContainer.setBorder(BorderFactory.createTitledBorder("Scenario 1: Seat Concurrency Control"));
        
        JLabel statusLabel = new JLabel("Occupied: 0");
        JCheckBox syncCheck = new JCheckBox("Synchronized Mode", true); 
        
        // Rezervasyon simülasyonu
        ReservationSimulation simPanel = new ReservationSimulation(true, statusLabel);
        
        syncCheck.addActionListener(e -> simPanel.setSynchronized(syncCheck.isSelected()));
        
        JButton startSimBtn = new JButton("Start Simulation (90 Passengers)");
        startSimBtn.setForeground(Color.BLACK); // Yazı Siyah
        startSimBtn.addActionListener(e -> simPanel.startSimulation());

        JPanel controlPanel = new JPanel();
        controlPanel.add(syncCheck);
        controlPanel.add(startSimBtn);
        controlPanel.add(statusLabel);
        
        simPanelContainer.add(controlPanel, BorderLayout.NORTH);
        simPanelContainer.add(new JScrollPane(simPanel), BorderLayout.CENTER);

        // --- SENARYO 2: ASENKRON SİSTEM RAPORU (ASYNC TASK) ---
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBorder(BorderFactory.createTitledBorder("Scenario 2: Real-Time System Analysis"));
        
        JButton reportBtn = new JButton("Generate System Report");
        reportBtn.setForeground(Color.BLACK); // Yazı Siyah
        JLabel reportStatus = new JLabel("Status: Idle");
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        
        JTextArea reportArea = new JTextArea(8, 40);
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        reportBtn.addActionListener(e -> {
            reportStatus.setText("Status: Analyzing Files...");
            reportBtn.setEnabled(false);
            progressBar.setIndeterminate(true);

            // GUI thread'ini dondurmadan arka planda gerçek verileri analiz eder
            new Thread(new ReportGenerator(result -> {
                SwingUtilities.invokeLater(() -> {
                    reportArea.setText(result);
                    reportStatus.setText("Status: Analysis Done");
                    reportBtn.setEnabled(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(100);
                });
            })).start();
        });

        JPanel reportControl = new JPanel(new FlowLayout());
        reportControl.add(reportBtn);
        reportControl.add(reportStatus);
        reportControl.add(progressBar);

        reportPanel.add(reportControl, BorderLayout.NORTH);
        reportPanel.add(new JScrollPane(reportArea), BorderLayout.CENTER);

        // İki senaryoyu panele yerleştir
        JPanel combinedPanel = new JPanel(new GridLayout(2, 1));
        combinedPanel.add(simPanelContainer);
        combinedPanel.add(reportPanel);
        
        container.add(combinedPanel, BorderLayout.CENTER);
        
        return container;
    }
}
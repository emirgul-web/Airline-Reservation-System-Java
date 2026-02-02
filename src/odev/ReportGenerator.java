package odev;

import java.util.List;

public class ReportGenerator implements Runnable {
    
    public interface ReportCallback {
        void onReportReady(String report);
    }

    private ReportCallback callback;

    public ReportGenerator(ReportCallback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            // Gerçekçi bir bekleme süresi (İşlem yapılıyormuş hissi verir)
            Thread.sleep(2000); 

            // Verileri dosyadan çek
            FlightManager flightManager = new FlightManager();
            SeatManager seatManager = new SeatManager();
            List<Flight> allFlights = flightManager.getFlights();

            int totalFlights = allFlights.size();
            int totalCapacity = 0;
            int totalOccupied = 0;

            // Her uçuş için kapasite ve doluluk hesapla
            for (Flight f : allFlights) {
                int capacity = f.getPlane().getCapacity();
                int available = seatManager.getAvailableSeatsCount(f);
                
                totalCapacity += capacity;
                totalOccupied += (capacity - available);
            }

            // Doluluk Oranı Hesaplama (%)
            double occupancyRate = (totalCapacity > 0) ? ((double) totalOccupied / totalCapacity) * 100 : 0;
            
            // Rapor Metni Oluşturma
            String reportResult = String.format(
                "REAL-TIME SYSTEM REPORT\n" +
                "------------------------\n" +
                "Total Flights Managed : %d\n" +
                "Total Seat Capacity   : %d\n" +
                "Total Tickets Sold    : %d\n" +
                "Overall Occupancy Rate: %%%.2f\n" +
                "Generated Status      : SUCCESS",
                totalFlights, 
                totalCapacity, 
                totalOccupied, 
                occupancyRate
            );
            
            if (callback != null) {
                callback.onReportReady(reportResult);
            }
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
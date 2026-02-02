package odev;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AirlineProjectTest {

    private Flight testFlight;
    private SeatManager seatManager;

    @BeforeEach
    void setUp() {
        // Testler başlamadan önce örnek bir uçuş ve manager hazırla
        Route route = new Route("Istanbul", "London");
        Plane plane = new Plane("PL-101", "Boeing 737", 10, 6); // 60 koltuk
        testFlight = new Flight("TK101", route, LocalDate.now().plusDays(1), LocalTime.of(10, 0), "3h", plane);
        seatManager = new SeatManager();
    }

    // TEST 1: Fiyat Hesaplama - Business Class 
    @Test
    void testCalculatePriceBusiness() {
        Seat businessSeat = new Seat("1A", SeatClass.BUSINESS, 200.0);
        Baggage baggage = new Baggage(15); // Sınırda
        double price = CalculatePrice.calculate(businessSeat, baggage);
        // 200 * 1.5 = 300 olmalı
        assertEquals(300.0, price, "Business class fiyati 1.5 katı olmalı.");
    }

    // TEST 2: Fiyat Hesaplama - Fazla Bagaj Ücreti 
    @Test
    void testCalculatePriceWithExtraBaggage() {
        Seat economySeat = new Seat("10A", SeatClass.ECONOMY, 100.0);
        Baggage heavyBaggage = new Baggage(20); // 15kg + 5kg ekstra
        double price = CalculatePrice.calculate(economySeat, heavyBaggage);
        // 100 + (5 * 10) = 150 olmalı
        assertEquals(150.0, price, "Ekstra bagaj ücreti yanlış hesaplandı.");
    }

    // TEST 3: Koltuk Sayısı Azalıyor mu? 
    @Test
    void testAvailableSeatsCountDecreases() throws Exception {
        int initialCount = seatManager.getAvailableSeatsCount(testFlight);
        Seat seatToBook = testFlight.getPlane().getSeatMatrix()[0][0];
        
        seatManager.bookSeat(seatToBook);
        
        int finalCount = seatManager.getAvailableSeatsCount(testFlight);
        assertEquals(initialCount - 1, finalCount, "Koltuk rezerve edildikten sonra sayı 1 azalmalı.");
    }

    // TEST 4: Olmayan Koltuk Hatası (Exception) 
    @Test
    void testNonExistentSeatException() {
        Exception exception = assertThrows(Exception.class, () -> {
            seatManager.bookSeat(null);
        });
        assertEquals("Non-existent seat number", exception.getMessage());
    }

    // TEST 5: Uçuş Filtreleme Mantığı 
    @Test
    void testFlightSearchFiltering() {
        List<Flight> allFlights = new ArrayList<>();
        allFlights.add(testFlight); // Istanbul -> London
        
        String searchFrom = "Istanbul";
        String searchTo = "London";
        
        boolean found = allFlights.stream().anyMatch(f -> 
            f.getRoute().getDeparture().equals(searchFrom) && 
            f.getRoute().getArrival().equals(searchTo)
        );
        
        assertTrue(found, "Arama kriterlerine uygun uçuş bulunmalı.");
    }
}
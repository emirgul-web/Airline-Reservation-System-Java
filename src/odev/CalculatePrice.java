package odev;

public class CalculatePrice {
    
    public static double calculate(Seat seat, Baggage baggage) {
        double finalPrice = seat.getPrice();
        
        // Business class 1.5 katı fiyat
        if (seat.getSeatClass() == SeatClass.BUSINESS) {
            finalPrice *= 1.5; 
        }
        
        // Bagaj ek ücreti (15kg üzeri her kg için 10 birim)
        if (baggage.getWeight() > 15) { 
            finalPrice += (baggage.getWeight() - 15) * 10;
        }
        
        return finalPrice;
    }
}
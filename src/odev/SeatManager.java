package odev;

public class SeatManager {
    
    public int getAvailableSeatsCount(Flight flight) {
        int count = 0;
        Seat[][] matrix = flight.getPlane().getSeatMatrix();
        for (Seat[] row : matrix) {
            for (Seat s : row) {
                if (!s.isReserved()) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public void bookSeat(Seat seat) throws Exception {
        // JUnit testi için null kontrolü ve exception şart
        if(seat == null) throw new Exception("Non-existent seat number");
        
        if(seat.isReserved()) throw new Exception("Seat is already taken");
        
        seat.setReserved(true);
    }
}
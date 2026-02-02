package odev;

import java.io.Serializable;

public class Plane implements Serializable {
    private String planeID;
    private String planeModel;
    private int capacity;
    private Seat[][] seatMatrix; 

    public Plane(String planeID, String planeModel, int rows, int cols) {
        this.planeID = planeID;
        this.planeModel = planeModel;
        this.capacity = rows * cols;
        this.seatMatrix = new Seat[rows][cols];
        initializeSeats(rows, cols);
    }

    private void initializeSeats(int rows, int cols) {
        char[] colLabels = {'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // İlk 5 sıra Business, kalanı Economy
                SeatClass type = (i < 5) ? SeatClass.BUSINESS : SeatClass.ECONOMY;
                double basePrice = (type == SeatClass.BUSINESS) ? 200.0 : 100.0;
                
                // Sütun harfi 
                char colChar = (j < colLabels.length) ? colLabels[j] : '?';
                String num = (i + 1) + "" + colChar;
                
                seatMatrix[i][j] = new Seat(num, type, basePrice);
            }
        }
    }
    
    public Seat[][] getSeatMatrix() { return seatMatrix; }
    public int getCapacity() { return capacity; }
}
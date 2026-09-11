import java.math.BigDecimal;
import java.math.RoundingMode;

public class FareSplitter {
    private final String tripId;
    private final double totalFare;
    private final int passengerCount;

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (totalFare < 0 || passengerCount <= 0) {
            throw new IllegalArgumentException("Fare must be non-negative and passenger count must be positive");
        }
        if (tripId == null || tripId.trim().isEmpty()) {
            throw new IllegalArgumentException("Trip ID cannot be blank");
        }
        this.tripId = tripId.trim();
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    // A provisional split uses a zero fare and two placeholder shares.
    public FareSplitter(String tripId) {
        this(tripId, 0.0, 2);
    }

    public double[] fareBreakdown() {
        BigDecimal totalCents = BigDecimal.valueOf(totalFare)
                .setScale(2, RoundingMode.HALF_UP)
                .movePointRight(2);
        long cents = totalCents.longValueExact();

        long base = cents / passengerCount;
        long remainder = cents % passengerCount;
        double[] result = new double[passengerCount];

        // The specification's example puts every leftover paisa into the final share.
        for (int i = 0; i < passengerCount; i++) {
            long share = base + (i == passengerCount - 1 ? remainder : 0);
            result[i] = BigDecimal.valueOf(share, 2).doubleValue();
        }
        return result;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        if (confirmed < 0 || expected < 0) {
            throw new IllegalArgumentException("Counts cannot be negative");
        }
        return confirmed < expected;
    }

    public static void main(String[] args) {
        FareSplitter splitter = new FareSplitter("TRIP001", 100000, 3);
        for (double share : splitter.fareBreakdown()) {
            System.out.printf("%.2f ", share);
        }
        System.out.println();

        FareSplitter provisional = new FareSplitter("TRIP003");
        for (double share : provisional.fareBreakdown()) {
            System.out.printf("%.1f ", share);
        }
        System.out.println();
    }
}

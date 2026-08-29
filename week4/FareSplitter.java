package week4;

public class FareSplitter {
    private final String tripId;
    private final double totalFare;
    private final int passengerCount;

    // Constructors chained through this(...)
    public FareSplitter(String tripId) {
        this(tripId, 0.0);
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (tripId == null || tripId.trim().isEmpty()) {
            throw new IllegalArgumentException("Trip ID cannot be null or empty");
        }
        if (totalFare < 0.0) {
            throw new IllegalArgumentException("Total fare cannot be negative");
        }
        if (passengerCount <= 0) {
            throw new IllegalArgumentException("Passenger count must be positive");
        }
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public String getTripId() {
        return tripId;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public double[] fareBreakdown() {
        long totalCents = Math.round(totalFare * 100);
        long baseCents = totalCents / passengerCount;
        long remainder = totalCents % passengerCount;

        double[] breakdown = new double[passengerCount];
        for (int i = 0; i < passengerCount; i++) {
            breakdown[i] = baseCents / 100.0;
        }

        // Leftover goes to the last elements (e.g. from end of the array)
        for (int i = 0; i < remainder; i++) {
            int index = passengerCount - 1 - i;
            breakdown[index] = Math.round((breakdown[index] + 0.01) * 100.0) / 100.0;
        }

        return breakdown;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        if (confirmed < 0 || expected < 0) {
            throw new IllegalArgumentException("Counts cannot be negative");
        }
        return confirmed < expected;
    }
}

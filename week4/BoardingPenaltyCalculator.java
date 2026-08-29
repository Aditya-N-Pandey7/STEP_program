package week4;

// The class itself must be locked against modification (final)
public final class BoardingPenaltyCalculator {
    // The field it depends on must be locked against modification (final)
    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException("Minimum penalty percent cannot be negative");
        }
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public double getMinimumPenaltyPercent() {
        return minimumPenaltyPercent;
    }

    // The calculation rule must be locked against modification (final method)
    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Ticket fare and minutes late cannot be negative");
        }
        if (minutesLate == 0) {
            return 0.0;
        }

        double rate1 = 0.005 * ticketFare; // 0.5% per minute
        double rate2 = 0.01 * ticketFare;  // 1% per minute
        double rate3 = 0.02 * ticketFare;  // 2% per minute

        double tieredPenalty;
        if (minutesLate <= 5) {
            tieredPenalty = minutesLate * rate1;
        } else if (minutesLate <= 15) {
            tieredPenalty = 5 * rate1 + (minutesLate - 5) * rate2;
        } else {
            tieredPenalty = 5 * rate1 + 10 * rate2 + (minutesLate - 15) * rate3;
        }

        double floorPenalty = (minimumPenaltyPercent / 100.0) * ticketFare;
        return Math.max(tieredPenalty, floorPenalty);
    }
}

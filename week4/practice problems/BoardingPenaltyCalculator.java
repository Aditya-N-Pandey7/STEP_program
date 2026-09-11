public final class BoardingPenaltyCalculator {
    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException("Minimum penalty percent cannot be negative");
        }
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Fare and minutes late cannot be negative");
        }

        if (minutesLate == 0) {
            return 0.0;
        }

        int firstTierMinutes = Math.min(minutesLate, 5);
        int secondTierMinutes = Math.max(0, Math.min(minutesLate, 15) - 5);
        int thirdTierMinutes = Math.max(0, minutesLate - 15);

        double tieredPenalty = ticketFare * (
                firstTierMinutes * 0.005
                + secondTierMinutes * 0.01
                + thirdTierMinutes * 0.02
        );

        double flatFloor = ticketFare * minimumPenaltyPercent / 100.0;
        return Math.max(tieredPenalty, flatFloor);
    }

    public static void main(String[] args) {
        BoardingPenaltyCalculator calculator = new BoardingPenaltyCalculator(1.0);
        System.out.println(calculator.calculatePenalty(1000, 0));
        System.out.println(calculator.calculatePenalty(1000, 1));
        System.out.println(calculator.calculatePenalty(1000, 16));
    }
}

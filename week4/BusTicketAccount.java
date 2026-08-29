package week4;

public class BusTicketAccount {
    protected final String bookingId;
    protected final double ticketFare;

    // One-time, class-level state set up using a static block
    private static final BoardingPenaltyCalculator penaltyCalculator;
    static {
        // Let's configure it with 1.0% minimum penalty floor
        penaltyCalculator = new BoardingPenaltyCalculator(1.0);
    }

    // Full constructor
    public BusTicketAccount(String bookingId, double ticketFare) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be null or empty");
        }
        if (ticketFare < 0.0) {
            throw new IllegalArgumentException("Ticket fare cannot be negative");
        }
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    // Provisional constructor that chains to full constructor
    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getTicketFare() {
        return ticketFare;
    }

    // The penalty calculation must be final (reusing Problem 4's version)
    public final double calculatePenalty(int minutesLate) {
        return penaltyCalculator.calculatePenalty(this.ticketFare, minutesLate);
    }
}

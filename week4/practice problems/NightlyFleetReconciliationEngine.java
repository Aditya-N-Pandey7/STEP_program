public class NightlyFleetReconciliationEngine {
    private static int processedCount;
    private static int nullSkippedCount;
    private static int sleeperCount;
    private static int regularCount;
    private static double grandTotalPenalties;

    static {
        processedCount = 0;
        nullSkippedCount = 0;
        sleeperCount = 0;
        regularCount = 0;
        grandTotalPenalties = 0.0;
    }

    public static class BusTicketAccount {
        protected final String bookingId;
        protected final double ticketFare;
        protected double amountPaid;

        public BusTicketAccount(String bookingId, double ticketFare) {
            if (bookingId == null || bookingId.trim().isEmpty()) {
                throw new IllegalArgumentException("Booking ID cannot be blank");
            }
            if (ticketFare < 0) {
                throw new IllegalArgumentException("Ticket fare cannot be negative");
            }
            this.bookingId = bookingId.trim();
            this.ticketFare = ticketFare;
        }

        public BusTicketAccount(String bookingId) {
            this(bookingId, 0.0);
        }

        public final double calculatePenalty(int minutesLate) {
            if (minutesLate < 0) {
                throw new IllegalArgumentException("Minutes late cannot be negative");
            }
            // Problem 5 permits a simpler flat-rate version. We use 1% per late minute.
            return ticketFare * 0.01 * minutesLate;
        }

        public void processAccount(double amount, int minutesLate) {
            if (amount < 0) {
                throw new IllegalArgumentException("Payment cannot be negative");
            }
            amountPaid = Math.min(ticketFare, amountPaid + amount);
        }
    }

    public static class SleeperBusTicketAccount extends BusTicketAccount {
        public SleeperBusTicketAccount(String bookingId, double ticketFare) {
            super(bookingId, ticketFare);
        }

        public SleeperBusTicketAccount(String bookingId) {
            super(bookingId);
        }

        @Override
        public void processAccount(double amount, int minutesLate) {
            if (amount < 0) {
                throw new IllegalArgumentException("Payment cannot be negative");
            }
            // The source specifies a separate sleeper settlement path but does not
            // define a numeric sleeper rate. Therefore the sleeper path is dispatched
            // separately without inventing an extra charge.
            amountPaid = Math.min(ticketFare, amountPaid + amount);
        }
    }

    public static void processAccount(BusTicketAccount account, double amount, int minutesLate) {
        if (account == null) {
            nullSkippedCount++;
            return;
        }

        account.processAccount(amount, minutesLate);
        double penalty = account.calculatePenalty(minutesLate);
        grandTotalPenalties += penalty;
        processedCount++;

        if (account instanceof SleeperBusTicketAccount) {
            sleeperCount++;
        } else {
            regularCount++;
        }
    }

    public static void processBatch(BusTicketAccount[] accounts,
                                    double[] amounts,
                                    int[] minutesLateArray) {
        resetCounters();

        if (accounts == null || amounts == null || minutesLateArray == null) {
            throw new IllegalArgumentException("Batch arrays cannot be null");
        }

        if (accounts.length != amounts.length || accounts.length != minutesLateArray.length) {
            // A mismatched batch can silently pair the wrong payment with a passenger.
            // Reject it rather than processing incorrect financial data.
            throw new IllegalArgumentException("Batch arrays must have matching lengths");
        }

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] == null) {
                nullSkippedCount++;
                continue;
            }
            processAccount(accounts[i], amounts[i], minutesLateArray[i]);
        }

        System.out.printf(
                "%d processed | %d null skipped | %d sleeper | %d regular | grand total penalties = %.2f%n",
                processedCount, nullSkippedCount, sleeperCount, regularCount, grandTotalPenalties);
    }

    private static void resetCounters() {
        processedCount = 0;
        nullSkippedCount = 0;
        sleeperCount = 0;
        regularCount = 0;
        grandTotalPenalties = 0.0;
    }

    public static void main(String[] args) {
        BusTicketAccount[] accounts = {
            new SleeperBusTicketAccount("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };
        double[] amounts = {1200, 900, 700};
        int[] minutesLateArray = {10, 5, 0};

        processBatch(accounts, amounts, minutesLateArray);
    }
}

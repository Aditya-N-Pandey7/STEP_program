package week4;

public class NightlyFleetReconciliationEngine {

    // Process a single account and print settlement details
    public void processAccount(BusTicketAccount account, double amount, int minutesLate) {
        if (account == null) {
            return;
        }
        double penalty = account.calculatePenalty(minutesLate);
        double totalOwed = account.getTicketFare() + penalty;
        double balance = amount - totalOwed;

        if (account instanceof Sleeper) {
            System.out.printf("[Sleeper Account] ID: %s | Fare: %.2f | Penalty: %.2f | Paid: %.2f | Settle Status: %s (Balance: %.2f)%n",
                account.getBookingId(), account.getTicketFare(), penalty, amount,
                (balance >= 0 ? "Fully Paid" : "Underpaid"), balance);
        } else {
            System.out.printf("[Regular Account] ID: %s | Fare: %.2f | Penalty: %.2f | Paid: %.2f | Settle Status: %s (Balance: %.2f)%n",
                account.getBookingId(), account.getTicketFare(), penalty, amount,
                (balance >= 0 ? "Fully Paid" : "Underpaid"), balance);
        }
    }

    // Process batch of accounts
    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        if (accounts == null || amounts == null || minutesLateArray == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        // Depot manager decision: throw an error if lengths mismatch, to avoid processing mismatched data
        if (accounts.length != amounts.length || accounts.length != minutesLateArray.length) {
            throw new IllegalArgumentException("Parallel arrays must have matching lengths");
        }

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;
        double grandTotalPenalties = 0.0;

        NightlyFleetReconciliationEngine engine = new NightlyFleetReconciliationEngine();

        for (int i = 0; i < accounts.length; i++) {
            BusTicketAccount acc = accounts[i];
            if (acc == null) {
                nullSkipped++;
            } else {
                processed++;
                if (acc instanceof Sleeper) {
                    sleeperCount++;
                } else {
                    regularCount++;
                }
                double penalty = acc.calculatePenalty(minutesLateArray[i]);
                grandTotalPenalties += penalty;

                engine.processAccount(acc, amounts[i], minutesLateArray[i]);
            }
        }

        System.out.printf("%d processed | %d null skipped | %d sleeper | %d regular | grand total penalties = %.2f%n",
            processed, nullSkipped, sleeperCount, regularCount, grandTotalPenalties);
    }
}

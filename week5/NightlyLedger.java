package week5;

/**
 * NightlyLedger processes daily discharge batches and provides audit statistics.
 * Employs instanceof-based dispatch to distinguish critical-care summaries from routine ones,
 * while safely ignoring null entries.
 */
public class NightlyLedger {

    private static final String LEDGER_VERSION;
    private static int totalBatchesProcessed;

    // Static block to set up one-time shared configuration
    static {
        LEDGER_VERSION = "2026.1";
        totalBatchesProcessed = 0;
    }

    /**
     * Processes a batch of discharge summaries, classifying entries by category
     * without throwing NullPointerException on null array elements.
     *
     * @param summaries Array of DischargeSummary objects (may contain nulls)
     * @return Formatted audit summary:
     *         "X processed | Y null skipped | Z critical-care | W routine"
     */
    public static String processNightlyBatch(DischargeSummary[] summaries) {
        totalBatchesProcessed++;

        if (summaries == null) {
            return "0 processed | 0 null skipped | 0 critical-care | 0 routine";
        }

        int processedCount = 0;
        int nullSkippedCount = 0;
        int criticalCareCount = 0;
        int routineCount = 0;

        for (DischargeSummary summary : summaries) {
            if (summary == null) {
                nullSkippedCount++;
                continue;
            }

            processedCount++;

            // Use instanceof to differentiate specialized critical-care summaries from routine ones
            if (summary instanceof CriticalCareDischargeSummary) {
                criticalCareCount++;
            } else {
                routineCount++;
            }
        }

        return String.format(
            "%d processed | %d null skipped | %d critical-care | %d routine",
            processedCount,
            nullSkippedCount,
            criticalCareCount,
            routineCount
        );
    }

    public static String getLedgerVersion() {
        return LEDGER_VERSION;
    }

    public static int getTotalBatchesProcessed() {
        return totalBatchesProcessed;
    }
}

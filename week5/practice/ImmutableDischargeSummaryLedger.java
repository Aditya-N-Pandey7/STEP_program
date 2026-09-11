import java.util.Arrays;

public class ImmutableDischargeSummaryLedger {
    static {
        System.out.println("Nightly discharge ledger initialized.");
    }

    public static final class DischargeSummary {
        private final String patientId;
        private final String[] medicationCodes;

        public DischargeSummary(String patientId, String[] medicationCodes) {
            if (patientId == null || medicationCodes == null) throw new IllegalArgumentException("Invalid summary");
            for (String code : medicationCodes) {
                if (!isValidMedication(code)) throw new IllegalArgumentException("Invalid medication code");
            }
            this.patientId = patientId;
            this.medicationCodes = medicationCodes.clone();
        }

        public String[] getMedicationCodes() {
            return medicationCodes.clone();
        }

        public DischargeSummary withCorrectedMedication(int index, String newCode) {
            if (index < 0 || index >= medicationCodes.length || !isValidMedication(newCode)) {
                throw new IllegalArgumentException("Invalid medication code");
            }
            String[] corrected = medicationCodes.clone();
            corrected[index] = newCode;
            return new DischargeSummary(patientId, corrected);
        }

        private static boolean isValidMedication(String code) {
            return code != null && code.length() == 5 && code.startsWith("MED-")
                    && code.charAt(4) >= 'A' && code.charAt(4) <= 'Z';
        }
    }

    public static class CriticalCareDischargeSummary {
        private final String patientId;
        private final String[] medicationCodes;
        private final int icuDays;

        public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
            if (patientId == null || medicationCodes == null || icuDays < 0) {
                throw new IllegalArgumentException("Invalid critical-care summary");
            }
            for (String code : medicationCodes) {
                if (code == null || code.length() != 5 || !code.startsWith("MED-")
                        || code.charAt(4) < 'A' || code.charAt(4) > 'Z') {
                    throw new IllegalArgumentException("Invalid medication code");
                }
            }
            this.patientId = patientId;
            this.medicationCodes = medicationCodes.clone();
            this.icuDays = icuDays;
        }
    }

    // The source requires DischargeSummary to be final while also describing a critical-care variant.
    // To keep the class genuinely final and the code compilable, the nightly API accepts Object[] and dispatches with instanceof.
    static String processNightlyBatch(Object[] summaries) {
        int processed = 0, nullSkipped = 0, critical = 0, routine = 0;
        for (Object summary : summaries) {
            if (summary == null) {
                nullSkipped++;
            } else if (summary instanceof CriticalCareDischargeSummary) {
                critical++;
                processed++;
            } else if (summary instanceof DischargeSummary) {
                routine++;
                processed++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + critical + " critical-care | " + routine + " routine";
    }

    public static void main(String[] args) {
        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println(d.getMedicationCodes()[0]);

        Object[] batch = {
            new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
            null,
            new DischargeSummary("MT002", new String[]{"MED-Y"})
        };
        System.out.println(processNightlyBatch(batch));
    }
}

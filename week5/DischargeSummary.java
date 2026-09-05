package week5;

import java.util.regex.Pattern;

/**
 * Immutable record of a patient discharge summary.
 * Designed to be tamper-proof for medical-legal compliance:
 * - All fields are final
 * - Defensive cloning on input and output arrays
 * - Strict medication code validation format: MED-[A-Z]
 * - Wither pattern for returning updated copies
 */
public class DischargeSummary {

    private static final Pattern MED_CODE_PATTERN;

    static {
        // One-time shared regex initialization
        MED_CODE_PATTERN = Pattern.compile("^MED-[A-Z]$");
    }

    private final String patientId;
    private final String[] medicationCodes;

    /**
     * Parameterized constructor that validates and seals the discharge summary.
     * Rejects construction if patientId is invalid, if medication array is null,
     * or if any individual medication code does not match the MED-[A-Z] format.
     *
     * @param patientId       Valid patient identifier
     * @param medicationCodes Array of medication codes
     */
    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (patientId == null || patientId.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient ID cannot be null or empty.");
        }
        if (medicationCodes == null) {
            throw new IllegalArgumentException("Medication codes array cannot be null.");
        }

        // Validate all entries before accepting the array
        for (String code : medicationCodes) {
            validateMedicationCode(code);
        }

        this.patientId = patientId.trim();
        // Defensive copy on entry to prevent caller from modifying internal state
        this.medicationCodes = medicationCodes.clone();
    }

    /**
     * Validates that a medication code follows the MED-[A-Z] pattern.
     */
    protected static void validateMedicationCode(String code) {
        if (code == null || !MED_CODE_PATTERN.matcher(code).matches()) {
            throw new IllegalArgumentException("Invalid medication code format: " + code + ". Must be 'MED-' followed by one uppercase letter.");
        }
    }

    public String getPatientId() {
        return patientId;
    }

    /**
     * Returns a defensive copy of the medication codes array.
     * Modifying the returned array will not mutate this immutable object.
     *
     * @return Clone of medication codes
     */
    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    /**
     * Returns a brand-new DischargeSummary with a corrected medication code at the specified index.
     * Adheres to the wither immutability pattern.
     *
     * @param index   Array index to update
     * @param newCode New medication code (must match format MED-[A-Z])
     * @return A newly constructed DischargeSummary
     */
    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length) {
            throw new IndexOutOfBoundsException("Medication index out of bounds: " + index);
        }
        validateMedicationCode(newCode);

        String[] updatedCodes = medicationCodes.clone();
        updatedCodes[index] = newCode;

        return new DischargeSummary(this.patientId, updatedCodes);
    }

    /**
     * Reconciles a batch of discharge summaries.
     * Delegates to NightlyLedger for batch processing and reporting.
     *
     * @param summaries Batch array of summaries (may include nulls)
     * @return Formatted reconciliation summary string
     */
    public static String processNightlyBatch(DischargeSummary[] summaries) {
        return NightlyLedger.processNightlyBatch(summaries);
    }

    @Override
    public String toString() {
        return "DischargeSummary{" +
                "patientId='" + patientId + '\'' +
                ", medicationCodes=" + java.util.Arrays.toString(medicationCodes) +
                '}';
    }
}

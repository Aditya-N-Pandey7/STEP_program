package week5;

/**
 * Specialized immutable discharge summary for Intensive Care Unit (ICU) patients.
 * Extends DischargeSummary with icuDays tracking and maintains full immutability.
 */
public final class CriticalCareDischargeSummary extends DischargeSummary {

    private final int icuDays;

    /**
     * Constructs an immutable critical care discharge summary.
     *
     * @param patientId       Patient identifier
     * @param medicationCodes Array of medication codes (MED-[A-Z])
     * @param icuDays         Total days spent in ICU (must be >= 0)
     */
    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        if (icuDays < 0) {
            throw new IllegalArgumentException("ICU days cannot be negative: " + icuDays);
        }
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }

    /**
     * Wither implementation preserving ICU days on the newly returned critical care summary.
     *
     * @param index   Index of the medication code to replace
     * @param newCode New medication code (must match format MED-[A-Z])
     * @return Brand-new CriticalCareDischargeSummary instance
     */
    @Override
    public CriticalCareDischargeSummary withCorrectedMedication(int index, String newCode) {
        String[] currentCodes = getMedicationCodes();
        if (index < 0 || index >= currentCodes.length) {
            throw new IndexOutOfBoundsException("Medication index out of bounds: " + index);
        }
        validateMedicationCode(newCode);

        String[] updatedCodes = currentCodes.clone();
        updatedCodes[index] = newCode;

        return new CriticalCareDischargeSummary(getPatientId(), updatedCodes, this.icuDays);
    }

    @Override
    public String toString() {
        return "CriticalCareDischargeSummary{" +
                "patientId='" + getPatientId() + '\'' +
                ", medicationCodes=" + java.util.Arrays.toString(getMedicationCodes()) +
                ", icuDays=" + icuDays +
                '}';
    }
}

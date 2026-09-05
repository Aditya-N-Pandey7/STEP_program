package week5;

/**
 * Represents an intake patient record in the MediTrack Clinic system.
 * Demonstrates intentional field visibility choices:
 * - patientId: private (sensitive core identifier)
 * - wardCode: protected (accessible to specialized medical subclasses like ICU)
 * - vitalsScore: private (clinical score requiring controlled access)
 * - facilityName: public (universal clinic facility info)
 */
public class PatientRecord {

    private String patientId;
    protected String wardCode;
    private double vitalsScore;
    public String facilityName;

    /**
     * Parameterized constructor serving as the construction-time gate.
     * Enforces that patientId must not be null, blank, or shorter than 4 characters.
     *
     * @param patientId    Unique identifier (must have length >= 4 after trimming)
     * @param wardCode     Ward assignment code
     * @param vitalsScore  Initial vitals score reading
     * @param facilityName Hospital or clinic branch name
     */
    public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
        if (patientId == null || patientId.trim().length() < 4) {
            throw new IllegalArgumentException(
                "Patient ID cannot be blank, whitespace-only, or shorter than 4 characters: " + patientId
            );
        }

        this.patientId = patientId.trim();
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }

    /**
     * No usable no-argument constructor is permitted.
     * Declared private with an exception to prevent accidental no-arg instantiation.
     */
    private PatientRecord() {
        throw new UnsupportedOperationException("A valid patient record requires mandatory intake data.");
    }

    public String getPatientId() {
        return patientId;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public double getVitalsScore() {
        return vitalsScore;
    }

    public void setVitalsScore(double vitalsScore) {
        this.vitalsScore = vitalsScore;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    @Override
    public String toString() {
        return "PatientRecord{" +
                "patientId='" + patientId + '\'' +
                ", wardCode='" + wardCode + '\'' +
                ", vitalsScore=" + vitalsScore +
                ", facilityName='" + facilityName + '\'' +
                '}';
    }
}

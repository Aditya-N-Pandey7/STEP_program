package week5;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * JavaBean-compliant model for hospital patient profiles.
 * Features:
 * - Public no-argument constructor and chained convenience constructors using this(...)
 * - Standard getX/setX and isX property accessors
 * - Write-once enforcement on patientId
 * - True write-only locker PIN with deterministic one-way storage
 */
public class PatientProfile {

    private String patientId;
    private String name;
    private boolean discharged;
    private String lockerPinHash;
    private boolean patientIdSet = false;

    /**
     * Default no-arg constructor required by JavaBean reflection frameworks.
     */
    public PatientProfile() {
        this(null, null);
    }

    /**
     * Intake constructor for partial data with name only.
     *
     * @param name Full name of the patient
     */
    public PatientProfile(String name) {
        this(null, name);
    }

    /**
     * Master constructor setting up the core profile data.
     * All constructors route here through this(...) chaining.
     *
     * @param patientId Initial patient ID (optional, can be null if pending intake)
     * @param name      Full name of the patient
     */
    public PatientProfile(String patientId, String name) {
        if (patientId != null && !patientId.trim().isEmpty()) {
            this.patientId = patientId.trim();
            this.patientIdSet = true;
        }
        this.name = name;
        this.discharged = false;
    }

    public String getPatientId() {
        return patientId;
    }

    /**
     * Write-once setter for patientId.
     * Once set (either during construction or via the first valid setter call),
     * subsequent attempts are silently ignored.
     *
     * @param id The patient ID to assign
     */
    public void setPatientId(String id) {
        if (!this.patientIdSet && id != null && !id.trim().isEmpty()) {
            this.patientId = id.trim();
            this.patientIdSet = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    /**
     * Write-only property to securely set the patient's locker PIN.
     * No getter is provided anywhere in the system to ensure write-only protection.
     * The PIN is stored as a deterministic one-way cryptographic hash.
     *
     * @param pin 4 to 6 digit numeric string
     */
    public void setLockerPin(String pin) {
        if (pin == null || !pin.matches("\\d{4,6}")) {
            // Silently ignore or reject invalid PIN formats
            return;
        }

        this.lockerPinHash = computeSha256(pin);
    }

    /**
     * Helper method to compute a deterministic SHA-256 hash.
     */
    private static String computeSha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Fallback deterministic one-way representation
            return Integer.toHexString(input.hashCode());
        }
    }

    @Override
    public String toString() {
        return "PatientProfile{" +
                "patientId='" + patientId + '\'' +
                ", name='" + name + '\'' +
                ", discharged=" + discharged +
                ", pinConfigured=" + (lockerPinHash != null) +
                '}';
    }
}

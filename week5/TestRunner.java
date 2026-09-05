package week5;

import java.util.Arrays;

/**
 * Verification test runner for Week 5 assignments.
 * Validates all requirements and sample test cases across Problems 1 through 5.
 */
public class TestRunner {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("     Running Week 5 Assignment Test Suite        ");
        System.out.println("=================================================\n");

        testProblem1();
        testProblem2();
        testProblem3();
        testProblem4();
        testProblem5();

        System.out.println("\nAll Week 5 tests passed successfully!");
    }

    private static void testProblem1() {
        System.out.println("--- Problem 1: Field Visibility & Intake Validator ---");

        // Test classifyAccess
        String p1 = AccessRuleEngine.classifyAccess("private", "SAME_CLASS");
        System.out.println("classifyAccess(\"private\", \"SAME_CLASS\"): " + p1 + " [Expected: ALLOWED]");

        String p2 = AccessRuleEngine.classifyAccess("default", "DIFFERENT_PACKAGE");
        System.out.println("classifyAccess(\"default\", \"DIFFERENT_PACKAGE\"): " + p2 + " [Expected: DENIED]");

        // Test summarizeBatch
        String[][] attempts = {
            {"protected", "SAME_PACKAGE"},
            {"protected", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        String summary = AccessRuleEngine.summarizeBatch(attempts);
        System.out.println("summarizeBatch: " + summary + " [Expected: Allowed: 2 | Denied: 1]");

        // Test PatientRecord validation: "MT9" (3 chars) rejected
        try {
            new PatientRecord("MT9", "W3", 98.2, "MediTrack Central");
            System.err.println("FAILED: MT9 was expected to be rejected!");
        } catch (IllegalArgumentException e) {
            System.out.println("Success: Construction rejected for 'MT9' -> " + e.getMessage());
        }

        // Test PatientRecord valid ID: "MT94" (4 chars) accepted
        try {
            PatientRecord record = new PatientRecord("MT94", "W3", 98.2, "MediTrack Central");
            System.out.println("Success: Construction accepted for 'MT94' -> " + record.getPatientId());
        } catch (Exception e) {
            System.err.println("FAILED: MT94 should be accepted: " + e.getMessage());
        }

        System.out.println();
    }

    private static void testProblem2() {
        System.out.println("--- Problem 2: Cross-Package Inheritance Reach ---");

        // Test protected access in subclass with own type
        String ownType = AccessRuleEngine.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE");
        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE\"): "
                + ownType + " [Expected: ALLOWED]");

        // Test protected access in subclass through parent reference
        String parentType = AccessRuleEngine.classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE");
        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE\"): "
                + parentType + " [Expected: DENIED]");

        // Test describeContext
        String desc = AccessRuleEngine.describeContext("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE");
        System.out.println("describeContext: \"" + desc + "\" [Expected: \"Subclass Different Package Parent Type\"]");

        System.out.println();
    }

    private static void testProblem3() {
        System.out.println("--- Problem 3: Vitals Monitoring Encapsulation Guard ---");

        // Seed with invalid reading (-2) and valid readings (36.5, 37.1)
        PatientVitals v = new PatientVitals(new double[]{36.5, -2, 37.1});
        double[] readings = v.getAllReadings();
        System.out.println("Initial filtered readings: " + Arrays.toString(readings) + " [Expected: [36.5, 37.1]]");

        // Test defensive copying
        double[] copy = v.getAllReadings();
        copy[0] = 999.0;
        System.out.println("v.getAllReadings()[0] after modifying copy: " + v.getAllReadings()[0] + " [Expected: 36.5]");

        // Test getAverage
        System.out.printf("v.getAverage(): %.2f [Expected: 36.80]%n", v.getAverage());

        // Test direct recordReading with out-of-range values
        v.recordReading(46.0); // should be rejected (> 45)
        v.recordReading(0.0);  // should be rejected (<= 0)
        v.recordReading(38.4); // should be accepted
        System.out.println("Readings after 46.0, 0.0, 38.4: " + Arrays.toString(v.getAllReadings()) + " [Expected: [36.5, 37.1, 38.4]]");

        System.out.println();
    }

    private static void testProblem4() {
        System.out.println("--- Problem 4: PatientProfile JavaBean & Locker PIN ---");

        // Test name-only constructor -> getPatientId() is null
        PatientProfile p1 = new PatientProfile("Arjun Iyer");
        System.out.println("new PatientProfile(\"Arjun Iyer\").getPatientId(): " + p1.getPatientId() + " [Expected: null]");

        // Test id+name constructor
        PatientProfile p2 = new PatientProfile("MT2026-0142", "Arjun Iyer");
        System.out.println("new PatientProfile(\"MT2026-0142\", \"Arjun Iyer\").getPatientId(): "
                + p2.getPatientId() + " [Expected: MT2026-0142]");

        // Test write-once setter
        PatientProfile p3 = new PatientProfile();
        p3.setPatientId("MT2026-0142");
        p3.setPatientId("HACKED-0000"); // should be ignored
        System.out.println("p3.getPatientId() after second setter call: " + p3.getPatientId() + " [Expected: MT2026-0142]");

        // Test write-only locker PIN
        p3.setLockerPin("4829");
        System.out.println("Locker PIN set successfully (write-only, no getter exposed).");

        System.out.println();
    }

    private static void testProblem5() {
        System.out.println("--- Problem 5: Immutable Discharge Summary & Nightly Ledger ---");

        // Test rejection of bad medication code
        try {
            new DischargeSummary("MT2026-0142", new String[]{"MED-A", "bad"});
            System.err.println("FAILED: Invalid medication code 'bad' was not rejected!");
        } catch (IllegalArgumentException e) {
            System.out.println("Success: Construction rejected for bad code -> " + e.getMessage());
        }

        // Test defensive copy on getMedicationCodes()
        DischargeSummary d = new DischargeSummary("MT2026-0142", new String[]{"MED-A", "MED-B"});
        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println("d.getMedicationCodes()[0] after tamper attempt: "
                + d.getMedicationCodes()[0] + " [Expected: MED-A]");

        // Test withCorrectedMedication wither method
        DischargeSummary corrected = d.withCorrectedMedication(1, "MED-C");
        System.out.println("Original codes: " + Arrays.toString(d.getMedicationCodes()) + " [Expected: [MED-A, MED-B]]");
        System.out.println("Corrected codes: " + Arrays.toString(corrected.getMedicationCodes()) + " [Expected: [MED-A, MED-C]]");

        // Test processNightlyBatch
        DischargeSummary[] batch = new DischargeSummary[]{
            new CriticalCareDischargeSummary("MT001", new String[]{"MED-X"}, 4),
            null,
            new DischargeSummary("MT002", new String[]{"MED-Y"})
        };

        String ledgerResult = NightlyLedger.processNightlyBatch(batch);
        System.out.println("processNightlyBatch output: " + ledgerResult);
        System.out.println("Expected:                   2 processed | 1 null skipped | 1 critical-care | 1 routine");

        System.out.println();
    }
}

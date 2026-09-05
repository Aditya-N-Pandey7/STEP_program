package week5;

import java.util.Arrays;

/**
 * Manages vital score history for a patient with strict encapsulation.
 * All fields are private and input data is thoroughly filtered via a single validation pathway.
 */
public class PatientVitals {

    private static final int MAX_CAPACITY = 500;
    private static final double MIN_VALID_READING = 0.0;
    private static final double MAX_VALID_READING = 45.0;

    private final double[] readings;
    private int count;

    /**
     * Seeds the patient's vitals history from an initial array.
     * Reuses recordReading() to avoid duplicating the validation logic.
     *
     * @param initialReadings Array of seed readings (may contain invalid numbers or be null)
     */
    public PatientVitals(double[] initialReadings) {
        this.readings = new double[MAX_CAPACITY];
        this.count = 0;

        if (initialReadings != null) {
            for (double reading : initialReadings) {
                recordReading(reading);
            }
        }
    }

    /**
     * Constructs an empty vitals history tracker.
     */
    public PatientVitals() {
        this(new double[0]);
    }

    /**
     * Records a single temperature / vitals reading.
     * Silently rejects values <= 0.0 or > 45.0 deg C, or NaN, without throwing exceptions.
     *
     * @param reading Value to be recorded
     */
    public void recordReading(double reading) {
        // Silently reject physiologically impossible numbers or NaN values
        if (Double.isNaN(reading) || reading <= MIN_VALID_READING || reading > MAX_VALID_READING) {
            return;
        }

        // Only store if within capacity limits
        if (count < readings.length) {
            readings[count++] = reading;
        }
    }

    /**
     * Calculates the arithmetic mean of all valid readings recorded so far.
     *
     * @return Average reading, or 0.0 if no readings are recorded
     */
    public double getAverage() {
        if (count == 0) {
            return 0.0;
        }

        double total = 0.0;
        for (int i = 0; i < count; i++) {
            total += readings[i];
        }

        return total / count;
    }

    /**
     * Returns a defensive copy of all valid recorded readings.
     * External modifications to the returned array will not mutate internal state.
     *
     * @return Fresh array containing current readings
     */
    public double[] getAllReadings() {
        return Arrays.copyOf(readings, count);
    }

    /**
     * Returns the total count of valid readings recorded.
     *
     * @return Number of stored readings
     */
    public int getCount() {
        return count;
    }
}

package week4;

import java.util.HashSet;
import java.util.Set;

public class BusTicket {
    private final String passengerName;
    private final String destination;
    private boolean isCheckedIn = false;

    // Must have no usable no-argument constructor — only a parameterized one.
    public BusTicket(String passengerName, String destination) {
        if (!isValidString(passengerName)) {
            throw new IllegalArgumentException("Invalid passenger name: " + passengerName);
        }
        if (!isValidString(destination)) {
            throw new IllegalArgumentException("Invalid destination: " + destination);
        }
        this.passengerName = passengerName;
        this.destination = destination;
    }

    private static boolean isValidString(String str) {
        if (str == null) return false;
        String trimmed = str.trim();
        if (trimmed.isEmpty()) return false;
        // Must contain only letters and spaces
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getDestination() {
        return destination;
    }

    public void markCheckedIn() {
        if (isCheckedIn) {
            throw new IllegalStateException("Ticket already checked in");
        }
        isCheckedIn = true;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public static void processBatch(String[][] rawBookings) {
        if (rawBookings == null) {
            throw new IllegalArgumentException("Raw bookings cannot be null");
        }

        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        Set<String> acceptedPairs = new HashSet<>();

        for (String[] booking : rawBookings) {
            if (booking == null || booking.length < 2) {
                rejected++;
                continue;
            }

            try {
                // Construct a ticket to validate
                BusTicket ticket = new BusTicket(booking[0], booking[1]);
                // Create a unique key for duplicate check (case-sensitive)
                String key = ticket.getPassengerName() + "|" + ticket.getDestination();
                if (acceptedPairs.contains(key)) {
                    duplicates++;
                } else {
                    acceptedPairs.add(key);
                    valid++;
                }
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected + " | Duplicates skipped: " + duplicates);
    }
}

import java.util.HashSet;
import java.util.Set;

public class BusTicketBookingValidator {
    private final String passengerName;
    private final String destination;
    private boolean checkedIn;

    // No usable no-argument constructor is provided.
    public BusTicketBookingValidator(String passengerName, String destination) {
        if (!isMeaningfulName(passengerName) || !isMeaningfulDestination(destination)) {
            throw new IllegalArgumentException("Invalid passenger name or destination");
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
    }

    public void markCheckedIn() {
        if (!checkedIn) {
            checkedIn = true;
        }
    }

    private static boolean isMeaningfulName(String value) {
        return value != null && value.trim().matches("[A-Za-z]+(?:[ '-][A-Za-z]+)*");
    }

    private static boolean isMeaningfulDestination(String value) {
        return value != null && value.trim().matches("[A-Za-z]+(?:[ -][A-Za-z]+)*");
    }

    public String bookingKey() {
        return passengerName + "\u0000" + destination;
    }

    public static void processBatch(String[][] rawBookings) {
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;
        Set<String> accepted = new HashSet<>();

        if (rawBookings != null) {
            for (String[] booking : rawBookings) {
                if (booking == null || booking.length < 2) {
                    rejected++;
                    continue;
                }

                try {
                    BusTicketBookingValidator ticket =
                            new BusTicketBookingValidator(booking[0], booking[1]);
                    if (accepted.add(ticket.bookingKey())) {
                        valid++;
                    } else {
                        duplicates++;
                    }
                } catch (IllegalArgumentException ex) {
                    rejected++;
                }
            }
        }

        System.out.println("Valid: " + valid
                + " | Rejected: " + rejected
                + " | Duplicates skipped: " + duplicates);
    }

    public static void main(String[] args) {
        String[][] bookings = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };
        processBatch(bookings);
    }
}

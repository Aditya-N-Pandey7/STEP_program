import java.time.*;
import java.util.*;

abstract class Room {
    private String name;
    private double pricePerNight;

    public Room(String name, double pricePerNight) {
        this.name = name;
        this.pricePerNight = pricePerNight;
    }

    public String getName() {
        return name;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }
}

class StandardRoom extends Room {
    public StandardRoom(String name) {
        super(name, 150.0);
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(String name) {
        super(name, 200.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(String name) {
        super(name, 300.0);
    }
}

class Reservation {
    private Room room;
    private String customer;
    private LocalDate start;
    private LocalDate end;
    private LocalDateTime cancellationDeadline;
    private boolean active = true;

    public Reservation(Room room, String customer, LocalDate start,
                       LocalDate end, LocalDateTime cancellationDeadline) {
        this.room = room;
        this.customer = customer;
        this.start = start;
        this.end = end;
        this.cancellationDeadline = cancellationDeadline;
    }

    public boolean overlaps(LocalDate from, LocalDate to) {
        return active && from.isBefore(end) && to.isAfter(start);
    }

    public double getPrice() {
        return ChronoUnit.DAYS.between(start, end) * room.getPricePerNight();
    }

    public Room getRoom() {
        return room;
    }

    public boolean isActive() {
        return active;
    }

    public boolean cancel(LocalDateTime now) {
        if (!active || now.isAfter(cancellationDeadline)) return false;
        active = false;
        return true;
    }

    public LocalDate getStart() { return start; }
    public LocalDate getEnd() { return end; }
}

class HotelBookingManager {
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Reservation book(Room room, String customer, LocalDate start, LocalDate end,
                            LocalDateTime cancellationDeadline) {
        if (!rooms.contains(room)) throw new IllegalArgumentException("Room is not registered");
        if (!start.isBefore(end)) throw new IllegalArgumentException("Invalid date range");
        for (Reservation r : reservations) {
            if (r.getRoom() == room && r.overlaps(start, end)) {
                System.out.println("Booking failed: " + room.getName()
                        + " is not available for " + start + " to " + end + ".");
                return null;
            }
        }
        Reservation reservation = new Reservation(room, customer, start, end, cancellationDeadline);
        reservations.add(reservation);
        System.out.printf("%s booked from %s to %s. Total price: $%.2f%n",
                room.getName(), start, end, reservation.getPrice());
        return reservation;
    }

    public void cancel(Reservation reservation, LocalDateTime now) {
        if (reservation != null && reservation.cancel(now)) {
            System.out.println("Reservation for " + reservation.getRoom().getName()
                    + " cancelled successfully.");
        } else {
            System.out.println("Cancellation failed: deadline passed or reservation inactive.");
        }
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        HotelBookingManager hotel = new HotelBookingManager();
        Room deluxe = new DeluxeRoom("Deluxe Room 101");
        Room standard = new StandardRoom("Standard Room 205");
        hotel.addRoom(deluxe);
        hotel.addRoom(standard);

        Reservation r1 = hotel.book(deluxe, "Customer A",
                LocalDate.parse("2024-12-01"), LocalDate.parse("2024-12-05"),
                LocalDateTime.parse("2024-11-30T23:59:00"));
        hotel.book(standard, "Customer B",
                LocalDate.parse("2024-12-03"), LocalDate.parse("2024-12-07"),
                LocalDateTime.parse("2024-12-01T23:59:00"));
        hotel.book(deluxe, "Customer C",
                LocalDate.parse("2024-12-03"), LocalDate.parse("2024-12-07"),
                LocalDateTime.parse("2024-12-01T23:59:00"));
        hotel.cancel(r1, LocalDateTime.parse("2024-11-30T12:00:00"));
    }
}

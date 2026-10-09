import java.util.*;

abstract class Vehicle {
    private String name;
    private boolean available = true;

    public Vehicle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateCharge(int days);
}

class StandardCar extends Vehicle {
    public StandardCar(String name) {
        super(name);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 50.0;
    }
}

class LuxuryCar extends Vehicle {
    public LuxuryCar(String name) {
        super(name);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 100.0;
    }
}

class SUV extends Vehicle {
    public SUV(String name) {
        super(name);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 80.0;
    }
}

class Rental {
    private Vehicle vehicle;
    private int days;
    private boolean active;

    public Rental(Vehicle vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
        active = true;
    }

    public double getTotalCharge() {
        return vehicle.calculateCharge(days);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isActive() {
        return active;
    }

    public void returnVehicle() {
        if (active) {
            active = false;
            vehicle.setAvailable(true);
        }
    }
}

class RentalService {
    private List<Rental> rentals = new ArrayList<>();

    public Rental rent(Vehicle vehicle, int days) {
        if (days <= 0) throw new IllegalArgumentException("Days must be positive");
        if (!vehicle.isAvailable()) {
            System.out.println("Rental failed: " + vehicle.getName() + " is unavailable.");
            return null;
        }
        vehicle.setAvailable(false);
        Rental rental = new Rental(vehicle, days);
        rentals.add(rental);
        System.out.printf("%s rented for %d days. Total charge: $%.2f%n",
                vehicle.getName(), days, rental.getTotalCharge());
        return rental;
    }

    public void returnVehicle(Rental rental) {
        if (rental != null && rental.isActive()) {
            rental.returnVehicle();
            System.out.println(rental.getVehicle().getName() + " returned. Now available.");
        }
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalService service = new RentalService();
        LuxuryCar luxury = new LuxuryCar("Luxury Car A");
        StandardCar standard = new StandardCar("Standard Car B");

        Rental first = service.rent(luxury, 3);
        service.rent(standard, 5);
        service.returnVehicle(first);
    }
}

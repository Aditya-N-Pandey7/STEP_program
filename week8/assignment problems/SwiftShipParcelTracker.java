import java.util.*;

interface ShippingType {
    double calculateCharge(double weight);
    String getName();
}

class StandardShipping implements ShippingType {
    public double calculateCharge(double weight) { return 40 + 10 * weight; }
    public String getName() { return "Standard"; }
}

class ExpressShipping implements ShippingType {
    public double calculateCharge(double weight) { return 80 + 15 * weight; }
    public String getName() { return "Express"; }
}

class FragileShipping implements ShippingType {
    public double calculateCharge(double weight) {
        return new StandardShipping().calculateCharge(weight) + 50;
    }
    public String getName() { return "Fragile"; }
}

interface NotificationChannel {
    void notifyStatus(String parcelId, String status);
}

class SmsChannel implements NotificationChannel {
    public void notifyStatus(String parcelId, String status) {
        System.out.println("[SMS] " + parcelId + " is now " + status + ".");
    }
}

class EmailChannel implements NotificationChannel {
    public void notifyStatus(String parcelId, String status) {
        System.out.println("[Email] " + parcelId + " is now " + status + ".");
    }
}

class Parcel {
    enum Status { BOOKED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, CANCELLED }
    private String id;
    private double weight;
    private ShippingType shipping;
    private Status status = Status.BOOKED;
    private List<NotificationChannel> channels = new ArrayList<>();

    public Parcel(String id, double weight, ShippingType shipping) {
        if (weight <= 0) throw new IllegalArgumentException("Weight must be positive");
        this.id = id;
        this.weight = weight;
        this.shipping = shipping;
    }

    public void subscribe(NotificationChannel channel) { channels.add(channel); }
    public String getId() { return id; }
    public Status getStatus() { return status; }
    public double getCharge() { return shipping.calculateCharge(weight); }

    public void updateStatus(Status next) {
        boolean valid =
            (status == Status.BOOKED && next == Status.PICKED_UP) ||
            (status == Status.PICKED_UP && next == Status.IN_TRANSIT) ||
            (status == Status.IN_TRANSIT && next == Status.OUT_FOR_DELIVERY) ||
            (status == Status.OUT_FOR_DELIVERY && next == Status.DELIVERED);
        if (!valid) {
            System.out.println("Invalid transition: " + status + " → " + next + " is not allowed.");
            return;
        }
        status = next;
        for (NotificationChannel channel : channels) channel.notifyStatus(id, status.toString());
    }

    public void cancel() {
        if (status != Status.BOOKED) {
            System.out.println("Cancellation failed: " + id + " can be cancelled only while BOOKED.");
            return;
        }
        status = Status.CANCELLED;
        for (NotificationChannel channel : channels) channel.notifyStatus(id, status.toString());
    }
}

class ParcelService {
    public Parcel book(String id, double weight, ShippingType type, NotificationChannel... channels) {
        Parcel p = new Parcel(id, weight, type);
        for (NotificationChannel channel : channels) p.subscribe(channel);
        System.out.printf("Parcel %s booked (%s, %.0f kg). Charge: ₹%.2f.%n",
                id, type.getName(), weight, p.getCharge());
        for (NotificationChannel channel : channels) channel.notifyStatus(id, "BOOKED");
        return p;
    }
}

public class SwiftShipParcelTracker {
    public static void main(String[] args) {
        ParcelService service = new ParcelService();
        Parcel p = service.book("P101", 2, new ExpressShipping(),
                new SmsChannel(), new EmailChannel());
        p.updateStatus(Parcel.Status.PICKED_UP);
        p.cancel();
        p.updateStatus(Parcel.Status.IN_TRANSIT);
        p.updateStatus(Parcel.Status.DELIVERED);
    }
}

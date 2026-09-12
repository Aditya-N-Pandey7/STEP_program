class EventTicket {
    protected String attendeeId;
    protected double basePrice;
    protected double amountPaid;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("attendeeId must contain at least 4 characters");
        }
        if (basePrice <= 0) {
            throw new IllegalArgumentException("basePrice must be positive");
        }
        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
    }

    public void pay(double amount) {
        if (amount > 0) amountPaid += amount;
    }

    public double getBalanceDue() {
        return Math.max(0.0, basePrice - amountPaid);
    }

    public void printTicket() {
        System.out.println("Standard Event Ticket | Balance Due: " + getBalanceDue());
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;
        for (String attendeeId : attendeeIds) {
            try {
                new EventTicket(attendeeId, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}

class WorkshopTicket extends EventTicket {
    private String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    @Override
    public void printTicket() {
        System.out.println("Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue());
    }
}

public class TicketHierarchyValidator {
    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println(w.getBalanceDue());
        System.out.println(EventTicket.registerBatch(
                new String[]{"STU1", "ST1", "STU2", " ", "STU3"}, 500));
    }
}

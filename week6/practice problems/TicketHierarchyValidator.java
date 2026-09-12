class EventTicket {
    protected String attendeeId;
    protected double basePrice;
    protected double amountPaid;
    private static int issuedCount = 1000;
    final String ticketId;
    private final double[] lateFeeHistory = new double[10];
    private int lateFeeCount;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("attendeeId must contain at least 4 characters");
        }
        if (basePrice <= 0) throw new IllegalArgumentException("basePrice must be positive");
        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
        this.ticketId = "TCK-" + (++issuedCount);
    }

    public EventTicket(double basePrice) {
        if (basePrice <= 0) throw new IllegalArgumentException("basePrice must be positive");
        this.attendeeId = "AUTO-" + (issuedCount + 1);
        this.basePrice = basePrice;
        this.ticketId = "TCK-" + (++issuedCount);
    }

    public void pay(double amount) {
        if (amount > 0) amountPaid += amount;
    }

    public void pay(double amount, String mode) {
        System.out.println("Payment mode: " + mode);
        pay(amount);
    }

    public double getBalanceDue() {
        return Math.max(0.0, basePrice - amountPaid);
    }

    protected void applyLateFee(double amount) {
        if (amount <= 0) return;
        amountPaid -= amount;
        if (lateFeeCount < lateFeeHistory.length) {
            lateFeeHistory[lateFeeCount++] = amount;
        }
    }

    public double[] getLateFeeHistory() {
        double[] copy = new double[lateFeeCount];
        System.arraycopy(lateFeeHistory, 0, copy, 0, lateFeeCount);
        return copy;
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

    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) return false;
        return code.charAt(0) == 'F'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isDigit(code.charAt(3))
                && Character.isUpperCase(code.charAt(4));
    }

    public static int getTicketsIssued() {
        return issuedCount - 1000;
    }
}

class WorkshopTicket extends EventTicket {
    private String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public WorkshopTicket(double basePrice) {
        super(basePrice);
        this.track = "General";
    }

    public String getTrack() { return track; }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
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

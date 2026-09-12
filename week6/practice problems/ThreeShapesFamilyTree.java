class EventTicket2 {
    protected double basePrice;
    protected double amountPaid;

    public EventTicket2(double basePrice) {
        if (basePrice <= 0) throw new IllegalArgumentException("basePrice must be positive");
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
}

class WorkshopTicket2 extends EventTicket2 {
    private String track;

    public WorkshopTicket2(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    public String getTrack() { return track; }

    @Override
    public void printTicket() {
        System.out.println("Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue());
    }
}

class PremiumWorkshopTicket extends WorkshopTicket2 {
    private double kitFee;

    public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
        super(basePrice, track);
        if (kitFee < 0) throw new IllegalArgumentException("kitFee cannot be negative");
        this.kitFee = kitFee;
    }

    @Override
    public void printTicket() {
        System.out.println("Premium Workshop Ticket | Track: " + getTrack()
                + " | Kit Fee: " + kitFee + " | Balance Due: " + getBalanceDue());
    }
}

class HackathonTicket extends EventTicket2 {
    private String teamName;

    public HackathonTicket(String attendeeId, double basePrice, String teamName) {
        super(basePrice);
        this.teamName = teamName;
    }

    @Override
    public void printTicket() {
        System.out.println("Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue());
    }
}

public class ThreeShapesFamilyTree {
    static String classifyGeneration(EventTicket2 ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }
        if (ticket instanceof WorkshopTicket2) {
            return "Intermediate descendant (2 generations deep)";
        }
        return "Base generation";
    }

    static double getTotalBalanceDue(EventTicket2[] tickets) {
        double total = 0;
        for (EventTicket2 ticket : tickets) {
            if (ticket != null) total += ticket.getBalanceDue();
        }
        return total;
    }

    public static void main(String[] args) {
        EventTicket2 standard = new EventTicket2(500);
        WorkshopTicket2 workshop = new WorkshopTicket2(1200, "AI/ML");
        PremiumWorkshopTicket premium = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathon = new HackathonTicket("STU4", 800, "Byte Force");

        standard.printTicket();
        workshop.printTicket();
        premium.printTicket();
        hackathon.printTicket();
        System.out.println(classifyGeneration(premium));
        System.out.println(classifyGeneration(hackathon));
        System.out.println(getTotalBalanceDue(new EventTicket2[]{standard, workshop, premium, hackathon}));
    }
}

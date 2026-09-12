class PremiumWorkshopTicket extends WorkshopTicket {
    private double kitFee;

    public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
        super(attendeeId, basePrice, track);
        if (kitFee < 0) throw new IllegalArgumentException("kitFee cannot be negative");
        this.kitFee = kitFee;
    }

    @Override
    public String printTicket() {
        return "Premium Workshop Ticket | Track: " + getTrack()
                + " | Kit Fee: " + kitFee + " | Balance Due: " + getBalanceDue();
    }
}

class HackathonTicket extends EventTicket {
    private String teamName;

    public HackathonTicket(String attendeeId, double basePrice, String teamName) {
        super(attendeeId, basePrice);
        this.teamName = teamName;
    }

    @Override
    public String printTicket() {
        return "Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue();
    }
}

public class ThreeShapesFamilyTree {
    static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) return "Multilevel descendant (3 generations deep)";
        if (ticket instanceof HackathonTicket) return "Hierarchical sibling (independent branch)";
        if (ticket instanceof WorkshopTicket) return "Workshop descendant (2 generations deep)";
        return "Base generation";
    }

    static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0;
        for (EventTicket ticket : tickets) total += ticket.getBalanceDue();
        return total;
    }

    public static void main(String[] args) {
        EventTicket standardTicket = new EventTicket("STU1", 500);
        WorkshopTicket workshopTicket = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premiumTicket = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathonTicket = new HackathonTicket("STU4", 800, "Byte Force");

        System.out.println(standardTicket.printTicket());
        System.out.println(workshopTicket.printTicket());
        System.out.println(premiumTicket.printTicket());
        System.out.println(hackathonTicket.printTicket());
        System.out.println(classifyGeneration(premiumTicket));
        System.out.println(classifyGeneration(hackathonTicket));
        System.out.println(getTotalBalanceDue(new EventTicket[]{standardTicket, workshopTicket, premiumTicket, hackathonTicket}));
    }
}

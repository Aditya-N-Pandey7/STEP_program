class GroupTicket extends EventTicket {
    private int groupSize;

    public GroupTicket(double basePrice, int groupSize) {
        super(basePrice);
        if (groupSize <= 0) throw new IllegalArgumentException("groupSize must be positive");
        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }

    @Override
    public String printTicket() {
        return "Group Ticket | Group Size: " + groupSize + " | Balance Due: " + getBalanceDue();
    }
}

public class FestWideTicketSettlement {
    static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (EventTicket ticket : tickets) {
            if (ticket == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (ticket instanceof GroupTicket) group++;
            else individual++;
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        EventTicket t1 = new EventTicket(500);
        System.out.println(t1.ticketId);
        System.out.println(EventTicket.getTicketsIssued());

        System.out.println(EventTicket.isValidPromoCode("F123A"));
        System.out.println(EventTicket.isValidPromoCode("F12A"));
        System.out.println(EventTicket.isValidPromoCode("X123A"));

        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println(t1.getBalanceDue());

        System.out.println(processNightlySettlement(
                new EventTicket[]{new GroupTicket(2000, 5), null, new EventTicket(500)}));
    }
}

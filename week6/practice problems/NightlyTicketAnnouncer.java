public class NightlyTicketAnnouncer {
    static String batchPrint(EventTicket[] tickets) {
        StringBuilder report = new StringBuilder();

        for (EventTicket ticket : tickets) {
            report.append(ticket.printTicket()).append(" | ");

            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket workshop = (WorkshopTicket) ticket;
                report.append("[Track via downcast: ")
                      .append(workshop.getTrack())
                      .append("] | ");
            }
        }
        return report.toString();
    }

    public static void main(String[] args) {
        EventTicket plain = new EventTicket("STU1", 500);
        WorkshopTicket workshop = new WorkshopTicket("STU2", 1200, "AI/ML");
        System.out.println(batchPrint(new EventTicket[]{plain, workshop}));

        // Safe downcasting: the instanceof check must happen before this cast.
        if (plain instanceof WorkshopTicket) {
            WorkshopTicket bad = (WorkshopTicket) plain;
            System.out.println(bad.getTrack());
        }
    }
}

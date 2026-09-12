public class LateRegistrationPenaltyAudit {
    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println(w.getBalanceDue());

        double[] history = w.getLateFeeHistory();
        System.out.println(java.util.Arrays.toString(history));
        history[0] = 999;
        System.out.println(java.util.Arrays.toString(w.getLateFeeHistory()));
    }
}

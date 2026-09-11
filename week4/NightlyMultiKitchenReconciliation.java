class DeliveryAccount {
    protected String studentId;
    protected double orderValue;
    static double baseRate;

    static {
        baseRate = 1.0; // one-time class-level default rate, in percent
    }

    public DeliveryAccount(String studentId, double orderValue) {
        if (studentId == null || studentId.trim().isEmpty() || orderValue < 0) {
            throw new IllegalArgumentException("Invalid account data");
        }
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(int delayMinutes) {
        if (delayMinutes < 0) throw new IllegalArgumentException("delayMinutes cannot be negative");
        if (delayMinutes == 0) return 0.0;
        int first = Math.min(delayMinutes, 5);
        int second = Math.min(Math.max(delayMinutes - 5, 0), 10);
        int third = Math.max(delayMinutes - 15, 0);
        double tiered = orderValue * (first * 0.005 + second * 0.01 + third * 0.02);
        double floor = orderValue * (baseRate / 100.0);
        return Math.max(tiered, floor);
    }

    void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
        if (account == null) return;
        double fee = account.calculateSurgeFee(delayMinutes);
        if (account instanceof PremiumDeliveryAccount) fee *= 0.5;
        System.out.println(account.studentId + " surge fee: Rs " + fee);
    }
}

class PremiumDeliveryAccount extends DeliveryAccount {
    PremiumDeliveryAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }
}

public class NightlyMultiKitchenReconciliation {
    static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts.length != amounts.length || accounts.length != delayMinutesArray.length) {
            throw new IllegalArgumentException("Parallel arrays must have matching lengths");
        }

        int processed = 0, nullSkipped = 0, premium = 0, regular = 0;
        double grandTotal = 0;

        for (int i = 0; i < accounts.length; i++) {
            DeliveryAccount account = accounts[i];
            if (account == null) {
                nullSkipped++;
                continue;
            }
            double fee = account.calculateSurgeFee(delayMinutesArray[i]);
            if (account instanceof PremiumDeliveryAccount) {
                fee *= 0.5; // premium settlement: half of the regular surge fee
                premium++;
            } else {
                regular++;
            }
            grandTotal += fee;
            processed++;
        }

        System.out.println(processed + " processed | " + nullSkipped + " null skipped | "
                + premium + " premium | " + regular + " regular | grand total surge fees = Rs " + grandTotal);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new PremiumDeliveryAccount("STU001", 500), null, new DeliveryAccount("STU002", 300)
        };
        processBatch(accounts, new double[]{500, 400, 300}, new int[]{10, 5, 0});
    }
}

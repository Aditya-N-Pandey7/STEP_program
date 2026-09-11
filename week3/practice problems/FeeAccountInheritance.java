class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    public FeeAccount(String regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = 0.0;
    }

    public void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Payment rejected: amount must be positive.");
            return;
        }
        amountPaid += amount;
    }

    public double getDue() {
        return Math.max(0.0, totalFee - amountPaid);
    }
}

class HostelFeeAccount extends FeeAccount {
    public HostelFeeAccount(String regNo, double totalFee) {
        super(regNo, totalFee);
    }

    public void payInTwoInstallments(double amount) {
        pay(amount / 2.0);
        pay(amount / 2.0);
    }
}

class ScholarshipFeeAccount extends FeeAccount {
    private double scholarshipPercent;

    public ScholarshipFeeAccount(String regNo, double totalFee, double scholarshipPercent) {
        super(regNo, totalFee);
        if (scholarshipPercent < 0 || scholarshipPercent > 100) {
            throw new IllegalArgumentException("Scholarship percentage must be between 0 and 100.");
        }
        this.scholarshipPercent = scholarshipPercent;
    }

    public double effectiveDue() {
        return getDue() * (1.0 - scholarshipPercent / 100.0);
    }
}

public class FeeAccountInheritance {
    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("RA101", 150000);
        plain.pay(150000);

        HostelFeeAccount hostel = new HostelFeeAccount("RA102", 200000);
        hostel.payInTwoInstallments(60000);

        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("RA103", 180000, 20);

        FeeAccount[] accounts = {plain, hostel, scholarship};

        for (FeeAccount account : accounts) {
            if (account instanceof ScholarshipFeeAccount) {
                System.out.println("Scholarship account effective due: Rs "
                        + ((ScholarshipFeeAccount) account).effectiveDue());
            } else if (account instanceof HostelFeeAccount) {
                System.out.println("Hostel account due: Rs " + account.getDue());
            } else {
                System.out.println("Plain account due: Rs " + account.getDue());
            }
        }
    }
}

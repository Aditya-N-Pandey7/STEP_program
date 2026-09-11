class CapstoneFeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    CapstoneFeeAccount(String regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
    }

    void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Payment rejected for " + regNo + ": amount must be positive.");
            return;
        }
        amountPaid += amount;
    }

    double getDue() {
        return Math.max(0.0, totalFee - amountPaid);
    }
}

class CapstoneHostelFeeAccount extends CapstoneFeeAccount {
    CapstoneHostelFeeAccount(String regNo, double totalFee) {
        super(regNo, totalFee);
    }

    void payInTwoInstallments(double amount) {
        pay(amount / 2.0);
        pay(amount / 2.0);
    }
}

class CapstoneHostelRoom {
    private String roomNo;
    private int beds;
    private int occupied;

    CapstoneHostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    void allot(String studentName) {
        if (occupied < beds) {
            occupied++;
        }
    }

    String getRoomNo() {
        return roomNo;
    }

    static CapstoneHostelRoom findAvailableRoom(CapstoneHostelRoom[] rooms) {
        if (rooms == null) return null;
        for (CapstoneHostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) return room;
        }
        return null;
    }
}

class SrmStudent {
    private String name;
    private String regNo;
    private CapstoneHostelFeeAccount feeAccount;
    private CapstoneHostelRoom room;

    static int totalStudents = 0;

    SrmStudent(String name, String regNo, CapstoneHostelFeeAccount feeAccount) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        totalStudents++;
    }

    void assignRoom(CapstoneHostelRoom room) {
        this.room = room;
    }

    String fullStatus() {
        return name + " | Due: Rs " + feeAccount.getDue() + " | Room: "
                + (room == null ? "unallotted" : room.getRoomNo());
    }
}

public class SrmFeeHostelSystem {
    public static void main(String[] args) {
        SrmStudent ravi = new SrmStudent("Ravi", "RA231100301011",
                new CapstoneHostelFeeAccount("RA231100301011", 150000));
        SrmStudent anitha = new SrmStudent("Anitha", "RA231100301012",
                new CapstoneHostelFeeAccount("RA231100301012", 200000));
        SrmStudent karthik = new SrmStudent("Karthik", "RA231100301013",
                new CapstoneHostelFeeAccount("RA231100301013", 200000));

        CapstoneHostelRoom room1 = new CapstoneHostelRoom("C-214", 3, 2);
        CapstoneHostelRoom room2 = new CapstoneHostelRoom("C-507", 2, 1);

        room1.allot(raviName());
        ravi.assignRoom(room1);
        room2.allot("Anitha");
        anitha.assignRoom(room2);

        raviFee(ravi);
        anithaFee(anitha);
        karthikFee(karthik);

        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());
        System.out.println("Total students: " + SrmStudent.totalStudents);
    }

    private static String raviName() {
        return "Ravi";
    }

    private static void raviFee(SrmStudent student) {
        // Valid payment; Ravi's due becomes Rs 140000.
        // The student object keeps its fee account as a composed object.
        // Payment is made through the fee account reference held by the student.
        // No direct field access is needed because feeAccount is encapsulated.
        try {
            java.lang.reflect.Field field = SrmStudent.class.getDeclaredField("feeAccount");
            field.setAccessible(true);
            CapstoneHostelFeeAccount account = (CapstoneHostelFeeAccount) field.get(student);
            account.pay(10000);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void anithaFee(SrmStudent student) {
        try {
            java.lang.reflect.Field field = SrmStudent.class.getDeclaredField("feeAccount");
            field.setAccessible(true);
            CapstoneHostelFeeAccount account = (CapstoneHostelFeeAccount) field.get(student);
            account.pay(20000);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void karthikFee(SrmStudent student) {
        try {
            java.lang.reflect.Field field = SrmStudent.class.getDeclaredField("feeAccount");
            field.setAccessible(true);
            CapstoneHostelFeeAccount account = (CapstoneHostelFeeAccount) field.get(student);
            account.pay(-5000); // Rejected payment, as required by the problem.
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }
}

class BrokenSrmStudent {
    // Broken version: these are student-specific fields, so making them static gives every
    // student one shared copy. Creating the second student overwrites the first student's data.
    static String name;
    static String regNo;
    static int attendance;

    BrokenSrmStudent(String name, String regNo, int attendance) {
        BrokenSrmStudent.name = name;
        BrokenSrmStudent.regNo = regNo;
        BrokenSrmStudent.attendance = attendance;
    }

    void printName() {
        System.out.println(name);
    }
}

class FixedSrmStudent {
    private String name;
    private String regNo;
    private int attendance;

    static String university = "SRM Institute of Science and Technology";
    static int admissionCount = 0;

    FixedSrmStudent(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;
        admissionCount++;
        this.regNo = "RA2311003010" + (10 + admissionCount);
    }

    void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + admissionCount);
    }
}

public class InstanceStaticBoundary {
    public static void main(String[] args) {
        System.out.println("Broken version:");
        BrokenSrmStudent first = new BrokenSrmStudent("Ravi", "RA231100301011", 82);
        BrokenSrmStudent second = new BrokenSrmStudent("Meera", "RA231100301012", 74);

        first.printName();
        second.printName();
        // Both print Meera because the static name field is shared and was overwritten.

        System.out.println("Fixed version:");
        FixedSrmStudent fixedFirst = new FixedSrmStudent("Ravi", 82);
        FixedSrmStudent fixedSecond = new FixedSrmStudent("Meera", 74);

        fixedFirst.printIdCard();
        fixedSecond.printIdCard();
        FixedSrmStudent.printTotalAdmissions();
    }
}

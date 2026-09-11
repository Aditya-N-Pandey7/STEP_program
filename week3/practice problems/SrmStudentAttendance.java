public class SrmStudentAttendance {
    private String name;
    private String regNo;
    private int attendance;

    public SrmStudentAttendance(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    public void addAttendanceUpdate(int newAttendance) {
        this.attendance = newAttendance;
    }

    public boolean isEligible() {
        return attendance >= 75;
    }

    public static double classAverage(SrmStudentAttendance[] students) {
        if (students == null || students.length == 0) return 0.0;

        int total = 0;
        for (SrmStudentAttendance student : students) {
            total += student.attendance;
        }
        return (double) total / students.length;
    }

    public void printStatus() {
        System.out.printf("%s - %d%% - %s%n", name, attendance,
                isEligible() ? "Eligible" : "Detained");
    }

    public static void main(String[] args) {
        SrmStudentAttendance[] students = {
            new SrmStudentAttendance("Ravi", "RA231100301011", 82),
            new SrmStudentAttendance("Anitha", "RA231100301012", 68),
            new SrmStudentAttendance("Karthik", "RA231100301013", 91),
            new SrmStudentAttendance("Meera", "RA231100301014", 74),
            new SrmStudentAttendance("Suresh", "RA231100301015", 60)
        };

        // isEligible() is an instance method because eligibility belongs to one student's attendance.
        // classAverage() is static because it operates on the class-level collection of students.
        for (SrmStudentAttendance student : students) {
            student.printStatus();
        }

        System.out.printf("Class average: %.1f%%%n", classAverage(students));
    }
}

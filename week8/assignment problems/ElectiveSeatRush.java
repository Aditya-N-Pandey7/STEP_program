import java.util.*;

interface CreditPolicy {
    int getLimit();
    String getType();
}

class RegularPolicy implements CreditPolicy {
    public int getLimit() { return 24; }
    public String getType() { return "Regular"; }
}
class HonorsPolicy implements CreditPolicy {
    public int getLimit() { return 28; }
    public String getType() { return "Honors"; }
}
class ExchangePolicy implements CreditPolicy {
    public int getLimit() { return 20; }
    public String getType() { return "Exchange"; }
}

class Student {
    private String name;
    private CreditPolicy policy;
    private int currentCredits;

    public Student(String name, CreditPolicy policy, int currentCredits) {
        this.name = name;
        this.policy = policy;
        this.currentCredits = currentCredits;
    }
    public String getName() { return name; }
    public int getCurrentCredits() { return currentCredits; }
    public int getLimit() { return policy.getLimit(); }
    public String getType() { return policy.getType(); }
    public boolean canAdd(int credits) { return currentCredits + credits <= getLimit(); }
    public void addCredits(int credits) { currentCredits += credits; }
    public void removeCredits(int credits) { currentCredits -= credits; }
}

class Elective {
    private String name;
    private int credits;
    private int capacity;
    private List<Student> enrolled = new ArrayList<>();
    private Queue<Student> waitlist = new LinkedList<>();
    private Set<Student> known = new HashSet<>();

    public Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    public String getName() { return name; }

    public void enroll(Student student) {
        if (known.contains(student)) {
            System.out.println(student.getName() + " already enrolled or waitlisted.");
            return;
        }
        if (!student.canAdd(credits)) {
            System.out.println("Enrollment failed: " + student.getName()
                    + " would exceed the " + student.getType()
                    + " credit limit (" + (student.getCurrentCredits() + credits)
                    + "/" + student.getLimit() + ").");
            return;
        }
        if (enrolled.size() < capacity) {
            enrolled.add(student);
            student.addCredits(credits);
            known.add(student);
            System.out.println(student.getName() + " enrolled in " + name + " (credits: "
                    + student.getCurrentCredits() + "/" + student.getLimit() + ").");
        } else {
            waitlist.add(student);
            known.add(student);
            System.out.println(name + " is full. " + student.getName()
                    + " added to waitlist (position " + waitlist.size() + ").");
        }
    }

    public void drop(Student student) {
        if (!enrolled.remove(student)) return;
        student.removeCredits(credits);
        known.remove(student);
        System.out.println(student.getName() + " dropped " + name + " (credits: "
                + student.getCurrentCredits() + "/" + student.getLimit() + ").");
        promoteNext();
    }

    private void promoteNext() {
        while (!waitlist.isEmpty() && enrolled.size() < capacity) {
            Student next = waitlist.remove();
            known.remove(next);
            if (!next.canAdd(credits)) {
                System.out.println("Promotion skipped: " + next.getName()
                        + " would exceed the credit limit.");
                continue;
            }
            enrolled.add(next);
            next.addCredits(credits);
            known.add(next);
            System.out.println(next.getName() + " promoted from waitlist and enrolled in "
                    + name + " (credits: " + next.getCurrentCredits() + "/"
                    + next.getLimit() + ").");
            break;
        }
    }
}

public class ElectiveSeatRush {
    public static void main(String[] args) {
        Elective elective = new Elective("Cloud Computing", 4, 2);
        Student asha = new Student("Asha", new RegularPolicy(), 20);
        Student ravi = new Student("Ravi", new HonorsPolicy(), 22);
        Student neha = new Student("Neha", new ExchangePolicy(), 12);
        Student kiran = new Student("Kiran", new RegularPolicy(), 22);

        elective.enroll(asha);
        elective.enroll(ravi);
        elective.enroll(neha);
        elective.enroll(kiran);
        elective.drop(asha);
    }
}

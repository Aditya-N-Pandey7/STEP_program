public class BookIssue {
    private String title;
    private String borrowerName;
    private int daysOverdue;

    BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    double fineAmount() {
        return daysOverdue > 0 ? daysOverdue * 5.0 : 0.0;
    }

    boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    // fineAmount() belongs to one object, while totalFineCollected() operates on many objects,
    // so the latter is static and can be called using the class name.
    static double totalFineCollected(BookIssue[] issues) {
        double total = 0;
        for (BookIssue issue : issues) total += issue.fineAmount();
        return total;
    }

    public static void main(String[] args) {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Aditi", 18),
            new BookIssue("Effective Java", "Rohan", 5),
            new BookIssue("Refactoring", "Meera", 0),
            new BookIssue("DSA Handbook", "Karan", 21),
            new BookIssue("Design Patterns", "Divya", 9)
        };

        for (BookIssue issue : issues) {
            System.out.println(issue.title + " - " + issue.daysOverdue + " days - "
                    + (issue.isSeverelyOverdue() ? "Severely overdue" : "OK"));
        }
        System.out.println("Total fine collected: Rs " + totalFineCollected(issues));
    }
}

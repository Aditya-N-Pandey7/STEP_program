interface Exportable {
    String exportData();
}

class ExportCounter {
    private static int totalExports = 0;

    static void increment() {
        totalExports++;
    }

    static int getTotalExports() {
        return totalExports;
    }
}

class ReportGenerator implements Exportable {
    private String reportName;

    public ReportGenerator(String reportName) {
        if (reportName == null || reportName.trim().isEmpty())
            throw new IllegalArgumentException("reportName cannot be blank");
        this.reportName = reportName;
    }

    @Override
    public String exportData() {
        ExportCounter.increment();
        return "Exported report: " + reportName;
    }
}

class UserProfile implements Exportable {
    private String username;

    public UserProfile(String username) {
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("username cannot be blank");
        this.username = username;
    }

    @Override
    public String exportData() {
        ExportCounter.increment();
        return "Exported profile: " + username;
    }
}

public class OneClickDataExport {
    static int getTotalExports() {
        return ExportCounter.getTotalExports();
    }

    static void exportAll(Exportable[] items) {
        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }

    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        UserProfile u = new UserProfile("jane_doe");

        System.out.println(r.exportData());
        System.out.println(u.exportData());

        Exportable ref = r;
        exportAll(new Exportable[]{ref, u});

        System.out.println(getTotalExports());
    }
}

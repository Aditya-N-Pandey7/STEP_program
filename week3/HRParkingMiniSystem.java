class EmployeeRecordEmployee {
    private String empId;
    private String empName;
    private double salary;

    EmployeeRecordEmployee(String empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    double getSalary() { return salary; }
}

class RecordManagerEmployee extends EmployeeRecordEmployee {
    private double teamBonus;

    RecordManagerEmployee(String id, String name, double salary, double bonus) {
        super(id, name, salary);
        this.teamBonus = bonus;
    }

    double effectiveSalary() { return getSalary() + teamBonus; }
}

class RecordParkingSlot {
    String slotNo;
    int capacity;
    int occupiedCount;

    RecordParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    void allot(String vehicleNo) {
        if (occupiedCount < capacity) occupiedCount++;
    }
}

class CompanyEmployeeRecord {
    String name;
    String empId;
    EmployeeRecordEmployee employee;
    RecordParkingSlot slot;
    static int totalRecords;

    CompanyEmployeeRecord(String name, String empId, EmployeeRecordEmployee employee, RecordParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    String fullProfile() {
        double pay = employee instanceof RecordManagerEmployee
                ? ((RecordManagerEmployee) employee).effectiveSalary()
                : employee.getSalary();
        return name + " | Pay: Rs " + pay + " | Slot: "
                + (slot == null ? "no parking assigned" : slot.slotNo);
    }
}

public class HRParkingMiniSystem {
    public static void main(String[] args) {
        RecordParkingSlot a1 = new RecordParkingSlot("A1", 1, 0);
        RecordParkingSlot a2 = new RecordParkingSlot("A2", 1, 0);

        CompanyEmployeeRecord divya = new CompanyEmployeeRecord("Divya", "E01",
                new RecordManagerEmployee("E01", "Divya", 70000, 8000), a1);
        CompanyEmployeeRecord karan = new CompanyEmployeeRecord("Karan", "E02",
                new EmployeeRecordEmployee("E02", "Karan", 40000), a2);
        CompanyEmployeeRecord meera = new CompanyEmployeeRecord("Meera", "E03",
                new EmployeeRecordEmployee("E03", "Meera", 12000), null);

        a1.allot("V1");
        a2.allot("V2");

        System.out.println(divya.fullProfile());
        System.out.println(karan.fullProfile());
        System.out.println(meera.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}

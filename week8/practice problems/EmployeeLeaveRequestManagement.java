import java.time.*;
import java.util.*;

interface LeavePolicy {
    boolean isLeaveAllowed(LocalDate start, LocalDate end);
    String getEmployeeType();
}

class FullTimeEmployee implements LeavePolicy {
    public boolean isLeaveAllowed(LocalDate start, LocalDate end) {
        return !start.isAfter(end) && ChronoUnit.DAYS.between(start, end) <= 30;
    }
    public String getEmployeeType() { return "Full-time"; }
}

class PartTimeEmployee implements LeavePolicy {
    public boolean isLeaveAllowed(LocalDate start, LocalDate end) {
        return !start.isAfter(end) && ChronoUnit.DAYS.between(start, end) <= 7;
    }
    public String getEmployeeType() { return "Part-time"; }
}

class ContractEmployee implements LeavePolicy {
    public boolean isLeaveAllowed(LocalDate start, LocalDate end) {
        return !start.isAfter(end) && ChronoUnit.DAYS.between(start, end) <= 3;
    }
    public String getEmployeeType() { return "Contract"; }
}

class Employee {
    private String name;
    private LeavePolicy policy;

    public Employee(String name, LeavePolicy policy) {
        this.name = name;
        this.policy = policy;
    }

    public String getName() { return name; }
    public LeavePolicy getPolicy() { return policy; }
}

class LeaveRequest {
    enum Status { PENDING, APPROVED, REJECTED }

    private Employee employee;
    private LocalDate start;
    private LocalDate end;
    private Status status = Status.PENDING;

    public LeaveRequest(Employee employee, LocalDate start, LocalDate end) {
        if (!employee.getPolicy().isLeaveAllowed(start, end))
            throw new IllegalArgumentException("Leave dates violate employee policy");
        this.employee = employee;
        this.start = start;
        this.end = end;
    }

    public Employee getEmployee() { return employee; }
    public LocalDate getStart() { return start; }
    public LocalDate getEnd() { return end; }
    public Status getStatus() { return status; }

    public void review(Status newStatus) {
        if (status != Status.PENDING)
            throw new IllegalStateException("Approved or rejected request cannot return to Pending or be reviewed again");
        if (newStatus == Status.PENDING)
            throw new IllegalArgumentException("Review must approve or reject the request");
        status = newStatus;
    }
}

class LeaveManager {
    public LeaveRequest submit(Employee employee, LocalDate start, LocalDate end) {
        LeaveRequest request = new LeaveRequest(employee, start, end);
        System.out.println("Leave request submitted by " + employee.getName()
                + " for " + start + " to " + end + ". Status: Pending.");
        return request;
    }

    public void review(LeaveRequest request, LeaveRequest.Status status) {
        request.review(status);
        System.out.println("Leave request for " + request.getEmployee().getName()
                + " " + status.toString().toLowerCase() + ". Status: "
                + status.toString().substring(0, 1) + status.toString().substring(1).toLowerCase() + ".");
    }
}

public class EmployeeLeaveRequestManagement {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();
        Employee john = new Employee("John Doe", new FullTimeEmployee());
        Employee jane = new Employee("Jane Smith", new PartTimeEmployee());

        LeaveRequest first = manager.submit(john,
                LocalDate.parse("2024-10-10"), LocalDate.parse("2024-10-12"));
        manager.review(first, LeaveRequest.Status.APPROVED);

        manager.submit(jane, LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-05"));
        try {
            first.review(LeaveRequest.Status.PENDING);
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Cannot change status: Approved request cannot revert to Pending.");
        }
    }
}

package week4;

import java.util.Arrays;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Running Week 4 Assignment Tests...");
        System.out.println("========================================\n");

        testProblem1();
        testProblem2();
        testProblem3();
        testProblem4();
        testProblem5();

        System.out.println("\nAll tests completed successfully.");
    }

    private static void testProblem1() {
        System.out.println("--- Problem 1: Bus Ticket Booking Validator ---");
        String[][] rawBookings = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };
        System.out.print("Expected: Valid: 1 | Rejected: 3 | Duplicates skipped: 1\nActual:   ");
        BusTicket.processBatch(rawBookings);

        // Test check-in twice exception
        try {
            BusTicket ticket = new BusTicket("Divya", "Chennai");
            ticket.markCheckedIn();
            System.out.println("First check-in successful.");
            ticket.markCheckedIn();
            System.out.println("ERROR: Second check-in didn't throw an exception!");
        } catch (IllegalStateException e) {
            System.out.println("Success: Second check-in threw exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: Unexpected exception: " + e);
        }
        System.out.println();
    }

    private static void testProblem2() {
        System.out.println("--- Problem 2: Remainder-Fair FareSplitter ---");
        FareSplitter f1 = new FareSplitter("TRIP001", 100000, 3);
        double[] b1 = f1.fareBreakdown();
        System.out.println("TRIP001 Expected: [33333.33, 33333.33, 33333.34]");
        System.out.println("TRIP001 Actual:   " + Arrays.toString(b1));

        FareSplitter f2 = new FareSplitter("TRIP003");
        double[] b2 = f2.fareBreakdown();
        System.out.println("TRIP003 Expected: [0.0, 0.0]");
        System.out.println("TRIP003 Actual:   " + Arrays.toString(b2));
        System.out.println();
    }

    private static void testProblem3() {
        System.out.println("--- Problem 3: Bus Route Ranking Engine ---");
        BusRoute[] routes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service") // defaults to priority 0
        };
        BusRoute[] sorted = BusRoute.rankRoutes(routes);
        System.out.println("Expected: [rt201j, RT205L, RT299T]");
        System.out.println("Actual:   " + Arrays.toString(sorted));

        // Test stability when fully tied
        BusRoute[] tiedRoutes = {
            new BusRoute("RT100", "Route A", 5),
            new BusRoute("rt100", "Route A", 5),
            new BusRoute("RT100", "Route A", 5)
        };
        BusRoute[] sortedTied = BusRoute.rankRoutes(tiedRoutes);
        System.out.println("Tied Expected (stable): [RT100, rt100, RT100]");
        System.out.println("Tied Actual:            " + Arrays.toString(sortedTied));
        System.out.println();
    }

    private static void testProblem4() {
        System.out.println("--- Problem 4: Tiered Boarding Penalty Calculator ---");
        BoardingPenaltyCalculator calc = new BoardingPenaltyCalculator(1.0); // 1% floor

        System.out.println("ticketFare = 1000, minutesLate = 0");
        System.out.println("Expected: 0.0");
        System.out.println("Actual:   " + calc.calculatePenalty(1000, 0));

        System.out.println("ticketFare = 1000, minutesLate = 1");
        System.out.println("Expected: 10.0 (Floor of 1% of 1000)");
        System.out.println("Actual:   " + calc.calculatePenalty(1000, 1));

        System.out.println("ticketFare = 1000, minutesLate = 16");
        System.out.println("Expected: 145.0 (Tiered: 5*5 + 10*10 + 1*20)");
        System.out.println("Actual:   " + calc.calculatePenalty(1000, 16));
        System.out.println();
    }

    private static void testProblem5() {
        System.out.println("--- Problem 5: Nightly Fleet Reconciliation Engine ---");
        BusTicketAccount[] accounts = {
            new Sleeper("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };
        double[] amounts = {1200, 900, 700};
        int[] minutesLateArray = {10, 5, 0};

        System.out.println("Expected Summary: 2 processed | 1 null skipped | 1 sleeper | 1 regular | grand total penalties = 150.00");
        System.out.println("Actual Settlement Details:");
        NightlyFleetReconciliationEngine.processBatch(accounts, amounts, minutesLateArray);
        System.out.println();
    }
}

package week4;

public class BusRoute implements Comparable<BusRoute> {
    private final String routeCode;
    private final String routeName;
    private final int priority;

    // Second constructor chains to the first with sensible default priority (e.g. 0)
    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 0);
    }

    public BusRoute(String routeCode, String routeName, int priority) {
        // Resolve field/parameter naming clash with this
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(BusRoute other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare to null BusRoute");
        }
        // First check: priority (higher priority first -> descending order)
        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        }

        // Second check: routeCode (case-insensitive, alphabetical)
        int codeCompare = this.routeCode.compareToIgnoreCase(other.routeCode);
        if (codeCompare != 0) {
            return codeCompare;
        }

        // Third check: routeName (case-sensitive)
        return this.routeName.compareTo(other.routeName);
    }

    @Override
    public String toString() {
        return this.routeCode;
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null) return null;
        BusRoute[] sorted = routes.clone();

        // Insertion sort is a stable sort algorithm O(n^2)
        for (int i = 1; i < sorted.length; i++) {
            BusRoute key = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j].compareTo(key) > 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }
        return sorted;
    }
}

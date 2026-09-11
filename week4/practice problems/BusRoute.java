public class BusRoute implements Comparable<BusRoute> {
    private final String routeCode;
    private final String routeName;
    private final int priority;

    public BusRoute(String routeCode, String routeName, int priority) {
        if (routeCode == null || routeCode.trim().isEmpty()
                || routeName == null || routeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Route code and name cannot be blank");
        }
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    // The two-argument constructor chains to the full constructor.
    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 5);
    }

    public String getRouteCode() {
        return routeCode;
    }

    @Override
    public int compareTo(BusRoute other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare with null route");
        }

        // Deterministic order: priority, then route name, then route code.
        int result = Integer.compare(this.priority, other.priority);
        if (result != 0) return result;

        result = this.routeName.compareToIgnoreCase(other.routeName);
        if (result != 0) return result;

        return this.routeCode.compareToIgnoreCase(other.routeCode);
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null) {
            return new BusRoute[0];
        }

        // Stable insertion sort: equal routes remain in their original order.
        BusRoute[] ranked = routes.clone();
        for (int i = 1; i < ranked.length; i++) {
            BusRoute current = ranked[i];
            int j = i - 1;
            while (j >= 0 && ranked[j].compareTo(current) > 0) {
                ranked[j + 1] = ranked[j];
                j--;
            }
            ranked[j + 1] = current;
        }
        return ranked;
    }

    public static void main(String[] args) {
        BusRoute[] routes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };

        for (BusRoute route : rankRoutes(routes)) {
            System.out.println(route.routeCode);
        }
    }
}

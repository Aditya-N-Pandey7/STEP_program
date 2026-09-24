abstract class HomeDevice {
    private static int counter = 1000;
    private final String serialNumber;

    protected HomeDevice() {
        serialNumber = "HD-" + (++counter);
    }

    public abstract String activate();

    public String getSerialNumber() {
        return serialNumber;
    }
}

interface RemoteControllable {
    String connect(String appId);
}

interface EnergyTrackable {
    double getConsumptionWatts();
}

class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
    private double consumptionWatts;

    public WashingMachine(double consumptionWatts) {
        if (consumptionWatts <= 0)
            throw new IllegalArgumentException("consumptionWatts must be positive");
        this.consumptionWatts = consumptionWatts;
    }

    @Override
    public String activate() {
        return "Washing machine " + getSerialNumber() + " started a cycle";
    }

    @Override
    public String connect(String appId) {
        validateAppId(appId);
        return getSerialNumber() + " connected to " + appId;
    }

    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }

    private void validateAppId(String appId) {
        if (appId == null || appId.trim().isEmpty())
            throw new IllegalArgumentException("appId cannot be blank");
    }
}

class Refrigerator extends HomeDevice implements EnergyTrackable {
    private double consumptionWatts;

    public Refrigerator(double consumptionWatts) {
        if (consumptionWatts <= 0)
            throw new IllegalArgumentException("consumptionWatts must be positive");
        this.consumptionWatts = consumptionWatts;
    }

    @Override
    public String activate() {
        return "Refrigerator " + getSerialNumber() + " activated";
    }

    @Override
    public double getConsumptionWatts() {
        return consumptionWatts;
    }
}

class MobileApp implements RemoteControllable {
    private String appName;

    public MobileApp(String appName) {
        if (appName == null || appName.trim().isEmpty())
            throw new IllegalArgumentException("appName cannot be blank");
        this.appName = appName;
    }

    @Override
    public String connect(String appId) {
        if (appId == null || appId.trim().isEmpty())
            throw new IllegalArgumentException("appId cannot be blank");
        return appName + " connected to " + appId;
    }
}

public class ConnectedHomeControlPanel {
    static void connectAll(RemoteControllable[] items, String appId) {
        for (RemoteControllable item : items) {
            System.out.println(item.connect(appId));
        }
    }

    static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) {
            EnergyTrackable trackable = (EnergyTrackable) d;
            return trackable.getConsumptionWatts();
        }
        return 0.0;
    }

    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);
        Refrigerator fridge = new Refrigerator(150.0);
        MobileApp app = new MobileApp("HomeConnect App");

        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));
        System.out.println(getConsumptionIfTrackable(fridge));
        System.out.println(app.connect("HomeConnect"));

        HomeDevice ref = wm;
        System.out.println(getConsumptionIfTrackable(ref));

        connectAll(new RemoteControllable[]{wm, app}, "HomeConnect");
    }
}

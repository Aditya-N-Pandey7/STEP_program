import java.util.*;

public class SmartHomePlatform {
    static abstract class Device {
        String id;
        boolean on;
        Device(String id) { this.id = id; }
        void commandOn() { on = true; System.out.println(id + " is ON"); }
        void commandOff() { on = false; System.out.println(id + " is OFF"); }
        void dim(int level) { System.out.println(id + " rejected: DIM unsupported"); }
        void schedule(String time) { System.out.println(id + " rejected: SCHEDULE unsupported"); }
        void energy() { System.out.println(id + " rejected: ENERGY unsupported"); }
    }

    static class Light extends Device {
        Light(String id) { super(id); }
        @Override void dim(int level) {
            if (level < 0 || level > 100) {
                System.out.println(id + " rejected: invalid dim level");
            } else {
                System.out.println(id + " dimmed to " + level);
            }
        }
        @Override void schedule(String time) { System.out.println(id + " scheduled " + time); }
    }

    static class Fan extends Device {
        Fan(String id) { super(id); }
        @Override void schedule(String time) { System.out.println(id + " scheduled " + time); }
    }

    static class Plug extends Device {
        double kwh;
        Plug(String id, double kwh) { super(id); this.kwh = kwh; }
        @Override void energy() { System.out.println(id + " energy " + kwh + " kWh"); }
    }

    public static void main(String[] args) {
        Map<String, Device> devices = new HashMap<>();
        devices.put("L1", new Light("L1"));
        devices.put("F1", new Fan("F1"));
        devices.put("P1", new Plug("P1", 12));

        devices.get("L1").commandOn();
        devices.get("L1").dim(40);
        devices.get("F1").dim(30);
        devices.get("F1").schedule("22:00");
        devices.get("P1").energy();
        devices.get("L1").energy();
    }
}
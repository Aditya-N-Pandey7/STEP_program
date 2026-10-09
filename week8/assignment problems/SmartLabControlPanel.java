import java.util.*;

interface Capability {
    String getName();
    boolean apply(Device device, int value);
}

class PowerCapability implements Capability {
    private boolean on;
    public String getName() { return "Power"; }
    public boolean apply(Device device, int value) {
        if (value != 0 && value != 1) return false;
        on = value == 1;
        System.out.println(device.getName() + ": " + (on ? "ON" : "OFF") + ".");
        return true;
    }
}

class BrightnessCapability implements Capability {
    private int brightness;
    public String getName() { return "Brightness"; }
    public boolean apply(Device device, int value) {
        if (value < 0 || value > 100) {
            System.out.println("Rejected: brightness must be between 0% and 100%.");
            return false;
        }
        brightness = value;
        System.out.println(device.getName() + ": brightness set to " + brightness + "%.");
        return true;
    }
}

class TemperatureCapability implements Capability {
    private int temperature;
    public String getName() { return "Temperature"; }
    public boolean apply(Device device, int value) {
        if (value < 16 || value > 30) {
            System.out.println("Rejected: " + device.getName()
                    + " temperature must be between 16°C and 30°C.");
            return false;
        }
        temperature = value;
        System.out.println(device.getName() + ": temperature set to " + temperature + "°C.");
        return true;
    }
}

class Device {
    private String name;
    private Map<String, Capability> capabilities = new LinkedHashMap<>();

    public Device(String name) { this.name = name; }
    public String getName() { return name; }

    public void addCapability(Capability capability) {
        capabilities.put(capability.getName(), capability);
        System.out.println(name + ": " + capability.getName() + " capability added.");
    }

    public boolean apply(String capabilityName, int value) {
        Capability c = capabilities.get(capabilityName);
        return c != null && c.apply(this, value);
    }

    public boolean hasCapability(String capabilityName) {
        return capabilities.containsKey(capabilityName);
    }
}

class SceneStep {
    private String capability;
    private int value;
    public SceneStep(String capability, int value) {
        this.capability = capability;
        this.value = value;
    }
    public String getCapability() { return capability; }
    public int getValue() { return value; }
}

class Scene {
    private String name;
    private List<SceneStep> steps = new ArrayList<>();
    public Scene(String name) { this.name = name; }
    public void addStep(SceneStep step) { steps.add(step); }

    public void execute(List<Device> devices) {
        System.out.println("Scene '" + name + "' started.");
        int applied = 0;
        for (SceneStep step : steps) {
            for (Device d : devices) {
                if (d.hasCapability(step.getCapability())
                        && d.apply(step.getCapability(), step.getValue())) applied++;
            }
        }
        System.out.println("Scene '" + name + "' completed: " + applied + " actions applied.");
    }
}

public class SmartLabControlPanel {
    public static void main(String[] args) {
        Device ac = new Device("Lab AC");
        ac.addCapability(new PowerCapability());
        ac.addCapability(new TemperatureCapability());

        Device lights = new Device("Ceiling Lights");
        lights.addCapability(new PowerCapability());
        lights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List<Device> devices = Arrays.asList(ac, lights, projector);
        Scene scene = new Scene("Lecture Mode");
        scene.addStep(new SceneStep("Power", 1));
        scene.addStep(new SceneStep("Brightness", 40));
        scene.addStep(new SceneStep("Temperature", 24));
        scene.execute(devices);

        ac.apply("Temperature", 12);
        projector.addCapability(new BrightnessCapability());
        projector.apply("Brightness", 70);
    }
}

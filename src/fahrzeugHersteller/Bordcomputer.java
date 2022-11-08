package fahrzeugHersteller;

import java.lang.reflect.Method;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zuliefererInterface.Device;

public class Bordcomputer {

    private static final List<String> SUPPORTED_DEVICE_TYPES = Arrays.asList(new String[] {
            "Radio",
            "CD",
            "USB"
    });
    private static final int MAX_DEVICES = 3;

    // path needs to be changed to the path of the config file
    private static final String CONFIG_FILENAME = "/Users/jonathanschlitt/hkp/src/fahrzeugHersteller/Geraete.config";

    private String[] deviceName;
    private Device[] installedDevices;
    private Device playingDevice;

    // private int playingDevice;

    public Bordcomputer() {
        this.readConfig();
        this.setDevices();
    }

    private void readConfig() {
        // Reset deviceName array
        this.deviceName = new String[MAX_DEVICES];

        // Read config file
        try {
            // Reset line counter
            int lineCtr = 0;

            // Read file line by line
            for (final String line : Files.readAllLines(Paths.get(CONFIG_FILENAME))) {
                // If line counter exceeds size of deviceName array, break
                if (lineCtr >= MAX_DEVICES) {
                    System.err.println("Config file contains more than " + MAX_DEVICES + " lines. Ignoring the rest.");
                    break;
                }

                // Split line by =
                final String[] parts = line.split("=");

                // If line does not contain exactly 2 parts, ignore it
                if (parts.length == 2) {
                    // Get device type
                    final String type = parts[0].trim();
                    // Get class name
                    final String className = parts[1].trim();

                    // Check if type and class name are valid
                    if (className.length() > 0 && type.length() > 0) {
                        // Check if type is supported
                        if (SUPPORTED_DEVICE_TYPES.contains(type)) {
                            // Add class name to deviceName array
                            this.deviceName[lineCtr] = className;
                            // Increment line counter
                            lineCtr++;
                        } else {
                            System.err.println("Unsupported device type: " + type);
                        }
                    } else {
                        System.err.println("Invalid config line: " + line);
                    }
                } else {
                    System.err.println("Invalid line in config file: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDevices() {
        // Reset device array
        this.installedDevices = new Device[MAX_DEVICES];

        // Reset current device
        this.playingDevice = installedDevices[0];

        // Iterate over deviceName array
        for (int i = 0; i < this.deviceName.length; i++) {
            // If deviceName is null, continue
            if (this.deviceName[i] == null) {
                continue;
            }

            // Try to create instance of device
            try {
                // Get class object
                final Class<?> clazz = Class.forName(this.deviceName[i]);
                // Create instance
                final Object obj = clazz.getDeclaredConstructor().newInstance();
                // Check if instance is of type Device
                if (obj instanceof zuliefererInterface.Device) {
                    // Add instance to device array
                    this.installedDevices[i] = (zuliefererInterface.Device) obj;
                } else {
                    System.err.println("Class " + this.deviceName[i] + " is not of type Device.");
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Class " + this.deviceName[i] + " not found.");
            } catch (InstantiationException e) {
                System.err.println("Could not instantiate class " + this.deviceName[i] + ".");
            } catch (IllegalAccessException e) {
                System.err.println("Could not access class " + this.deviceName[i] + ".");
            } catch (NoSuchMethodException e) {
                System.err.println("Class " + this.deviceName[i] + " does not have a default constructor.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.changeDevice();
    }

    public void shutdown() {
        System.out.println("Shutting down...");
    }

    public void changeDevice() {
        // get current device index
        int currentIndex = 0;

        for (int i = 0; i < this.installedDevices.length; i++) {
            if (this.installedDevices[i].equals(this.playingDevice)) {
                currentIndex = i;
            }
        }

        // If device array is null, return
        if (this.installedDevices == null || this.installedDevices.length == 0) {
            System.err.println("No devices available.");
            return;
        }

        // If current device is the last device, set current device to first device
        if (currentIndex == (this.installedDevices.length - 1)) {
            this.playingDevice = installedDevices[0];
        } else {
            // Increment current device
            this.playingDevice = installedDevices[currentIndex + 1];
        }

        // If current device is null, call changeDevice() again
        if (this.playingDevice == null && currentIndex < (this.installedDevices.length - 1)) {
            changeDevice();
            System.out.println("\nChanged to device " + this.playingDevice.getClass().getSimpleName() + "\n");
        }

        System.out.println("\nChanged to device: " + this.playingDevice.getClass().getSimpleName() + "\n");

        showOptions();
    }

    public void showOptions() {
        // Get current device
        final Device device = this.playingDevice;

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        System.out.println("\nInstalled devices: \n");
        for (int i = 0; i < this.installedDevices.length; i++) {
            if (this.installedDevices[i] != null) {
                System.out.println("=> " + this.installedDevices[i].getClass().getSimpleName());
            }
        }

        System.out.println("\nAll Bordcomputer options:\n");

        for (int i = 0; i < getBordcomputerOptions().length; i++) {
            System.out.println(10 + i + ": " + getBordcomputerOptions()[i]);
        }

        System.out.println("\nAll options from current " + device.getClass().getSimpleName() + " - Device:\n");

        // Print options
        for (int i = 0; i < device.getOptions().length; i++) {
            System.out.println(i + ": " + device.getOptions()[i]);
        }

        System.out.println("\n");
    }

    public void enterOption(final int choice) {
        // System.out.println("You entered: " + choice);
        // Get current device
        final Device device = this.playingDevice;

        if (choice >= 10) {

            final String[] bordcomputerOptions = getBordcomputerOptions();
            String opt = getBordcomputerOptions()[choice - 10];

            // System.out.println("You entered: " + opt);

            // Check if choosen option is contained in extra options

            boolean found = false;

            for (final String bcOption : bordcomputerOptions) {
                if (bcOption.equals(opt)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return;
            }

            // Otherwise invoke the method
            try {

                if (opt.equals("louder") || opt.equals("quieter")) {
                    final Method method = Bordcomputer.class.getMethod(opt, Integer.TYPE);
                    method.invoke(this, 1);
                } else {
                    final Method method = Bordcomputer.class.getMethod(opt);
                    method.invoke(this);
                }
            } catch (final Exception e) {
                System.err.println("Error while invoking method '" + opt + "': " + e.getMessage());
            }
        } else {
            // If device is null, return
            if (device == null) {
                System.err.println("No device found.");
                return;
            }

            // If choice is invalid, return
            if (choice < 0) {
                System.err.println("Invalid choice number.");
                return;
            }

            // Get option
            final String option = device.getOptions()[choice];

            // If option is null, return
            if (option == null) {
                System.err.println("Invalid choice.");
                return;
            }

            device.chooseOption(option);
        }

    }

    public void louder(final int p) {
        // Get current device
        final Device device = this.playingDevice;

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        for (int i = 0; i < p; i++) {
            device.louder();
        }

        this.showVolume();
    }

    public void quieter(final int p) {
        // Get current device
        final Device device = this.playingDevice;

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        for (int i = 0; i < p; i++) {
            device.quieter();
        }

        this.showVolume();
    }

    public void showVolume() {
        // Get current device
        final Device device = this.playingDevice;

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        System.out.println("Volume: " + device.getVolume());
    }

    public void next() {
        // Get current device
        final Device device = this.playingDevice;

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        device.next();
    }

    public void prev() {
        // Get current device
        final Device device = this.playingDevice;

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        device.prev();
    }

    public void play() {
        // Get current device
        final Device device = this.playingDevice;

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        System.out.println(device.play());
    }

    public String[] getBordcomputerOptions() {
        ArrayList<String> options = new ArrayList<String>();

        for (Method method : Bordcomputer.class.getDeclaredMethods()) {
            options.add(method.getName());
        }

        options.remove("readConfig");
        options.remove("setDevices");
        options.remove("getCurrentDevice");

        return options.toArray(new String[options.size()]);
    }

}

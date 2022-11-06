package fahrzeugHersteller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import zuliefererInterface.Device;

public class Boardcomputer {

    private static final List<String> SUPPORTED_DEVICE_TYPES = Arrays.asList(new String[] {
            "Radio",
            "CD",
            "USB"
    });
    private static final int MAX_DEVICES = 3;
    private static final String CONFIG_FILENAME = "./Geraete.config";

    private String[] deviceName;
    private Device[] device;

    private int currentDevice;

    public Boardcomputer() {
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
        this.device = new Device[MAX_DEVICES];

        // Reset current device
        this.currentDevice = -1;

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
                final Object obj = clazz.getDeclaredConstructor(null).newInstance(null);
                // Check if instance is of type Device
                if (obj instanceof Device) {
                    // Add instance to device array
                    this.device[i] = (Device) obj;
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
        // If device array is null, return
        if (this.device == null || this.device.length == 0) {
            System.err.println("No devices available.");
            return;
        }

        // If current device is the last device, set current device to first device
        if (this.currentDevice == (this.device.length - 1)) {
            this.currentDevice = 0;
        } else {
            // Increment current device
            this.currentDevice++;
        }

        // If current device is null, call changeDevice() again
        if (this.device[this.currentDevice] == null && this.currentDevice < (this.device.length - 1)) {
            changeDevice();
        }
    }

    private Device getCurrentDevice() {
        // If device array is null, return null
        if (this.currentDevice == -1 || this.device == null) {
            System.err.println("No devices available.");
            return null;
        }

        // Return current device
        return this.device[this.currentDevice];
    }

    public void showOptions() {
        // Get current device
        final Device device = getCurrentDevice();

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        // Print options
        for (int i = 0; i < device.getOptions().length; i++) {
            System.out.println(i + ": " + device.getOptions()[i]);
        }
    }

    public void enterOption(final int choice) {
        // Get current device
        final Device device = getCurrentDevice();

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        // If choice is invalid, return
        if (choice < 0 || choice >= device.getOptions().length) {
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

    public void louder(final int p) {
        // Get current device
        final Device device = getCurrentDevice();

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
        final Device device = getCurrentDevice();

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
        final Device device = getCurrentDevice();

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        System.out.println("Volume: " + device.getVolume());
    }

    public void next() {
        // Get current device
        final Device device = getCurrentDevice();

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        device.next();
    }

    public void prev() {
        // Get current device
        final Device device = getCurrentDevice();

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        device.prev();
    }

    public void play() {
        // Get current device
        final Device device = getCurrentDevice();

        // If device is null, return
        if (device == null) {
            System.err.println("No device found.");
            return;
        }

        System.out.println(device.play());
    }

}

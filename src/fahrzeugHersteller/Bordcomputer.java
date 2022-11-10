package fahrzeugHersteller;

import java.lang.reflect.Method;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zuliefererInterface.Device;

public class Bordcomputer {

    private final Device[] installedDevices;
    private Device playingDevice;

    // private int playingDevice;

    public Bordcomputer(final Device[] installedDevices) {
        this.installedDevices = installedDevices;
        this.playingDevice = this.installedDevices.length > 0 ? this.installedDevices[0] : null;
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
        
        final String[] boardComputerOptions = this.getBordcomputerOptions();
        final String[] deviceOptions = device.getOptions();

        System.out.println("\nInstalled devices: \n");
        for (int i = 0; i < this.installedDevices.length; i++) {
            if (this.installedDevices[i] != null) {
                System.out.println("=> " + this.installedDevices[i].getClass().getSimpleName());
            }
        }

        System.out.println("\nAll Bordcomputer options:\n");

        for (int i = 0; i < boardComputerOptions.length; i++) {
            System.out.println(deviceOptions.length + i + ": " + boardComputerOptions[i]);
        }

        System.out.println("\nAll options from current " + device.getClass().getSimpleName() + " - Device:\n");

        // Print options
        for (int i = 0; i < deviceOptions.length; i++) {
            System.out.println(i + ": " + deviceOptions[i]);
        }

        System.out.println("\n");
    }

    public void enterOption(final int choice) {
        final Device device = this.playingDevice;

        final String[] bordcomputerOptions = getBordcomputerOptions();
    	final String[] deviceOptions = device != null ? device.getOptions() : null;
    	final int deviceOptionsLength = deviceOptions != null ? deviceOptions.length : 0;
        
        if (choice < 0) {
        	System.err.println("Invalid option.");
        	return;
        } else if (choice >= deviceOptionsLength) {
        	
            final String opt = bordcomputerOptions[choice - deviceOptionsLength];

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
            if (deviceOptionsLength == 0) {
                System.err.println("No device options found.");
                return;
            }

            // Get option
            final String option = deviceOptions[choice];

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

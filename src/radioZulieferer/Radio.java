package radioZulieferer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import zuliefererInterface.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Radio implements Device {

    public int volume = 0;

    public Radio() {

    }

    @Override
    public void louder() {
        System.out.println("Increasing Radio volume.");
        this.volume += 10;
    }

    @Override
    public void quieter() {
        System.out.println("Decreasing Radio volume.");
        this.volume -= 10;
    }

    @Override
    public int getVolume() {
        return this.volume;
    }

    @Override
    public void next() {
        System.out.println("Switching to next radio station with higher frequency!");
    }

    @Override
    public void prev() {
        System.out.println("Switching to next radio station with lower frequency!");
    }

    @Override
    public String getInfoText() {
        return "I`m your Radio...";
    }

    @Override
    public String[] getOptions() {
        // Get all methods names that are implemented via the interface as List
        final List<String> superMethodNames = new ArrayList<>();

        // Get all methods of the super class
        for (final Method method : this.getClass().getSuperclass().getMethods()) {
            superMethodNames.add(method.getName());
        }

        // Get all available methods of UsbPlayer as List
        final List<String> RadioMethodNames = new ArrayList<>();

        // Get all methods of UsbPlayer
        for (final Method method : this.getClass().getMethods()) {
            RadioMethodNames.add(method.getName());
        }

        // Remove all methods that are implemented via the interface
        RadioMethodNames.removeAll(superMethodNames);

        // String s = "";

        // for (int i = 0; i < RadioMethodNames.size(); i++) {

        // s = RadioMethodNames.get(i);

        // System.out.println(s.equals(new String("louder")));

        // if (s.equals("getOptions") || s.equals("chooseOption") || s.equals("louder")
        // || s.equals("quieter")
        // || s.equals("getVolume") || s.equals("play") || s.equals("next") ||
        // s.equals("prev")) {
        // RadioMethodNames.remove(i);
        // // System.out.println("Removed " + s);
        // }
        // }
        RadioMethodNames.remove("getInfoText");
        RadioMethodNames.remove("getOptions");
        RadioMethodNames.remove("chooseOption");
        RadioMethodNames.remove("louder");
        RadioMethodNames.remove("quieter");
        RadioMethodNames.remove("getVolume");
        RadioMethodNames.remove("play");
        RadioMethodNames.remove("next");
        RadioMethodNames.remove("prev");

        // for (String string : RadioMethodNames) {
        // System.out.println(string);
        // }

        return RadioMethodNames.toArray(new String[RadioMethodNames.size()]);
    }

    @Override
    public void chooseOption(String opt) {
        // Check if choosen option is contained in extra options
        final String[] extraOptions = getOptions();

        boolean found = false;

        for (final String extraOption : extraOptions) {
            if (extraOption.equals(opt)) {
                found = true;
                break;
            }
        }

        if (!found) {
            return;
        }

        // Otherwise invoke the method
        try {
            final Method method = Radio.class.getMethod(opt);
            method.invoke(this);
        } catch (final Exception e) {
            System.err.println("Error while invoking method '" + opt + "': " + e.getMessage());
        }
    }

    @Override
    public String play() {
        return "Radio is playing...";
    }

    public void changeRadioStation() {
        System.out.println("You entered YouFM ==> Now playing YouFM :)");
    }

    public void changeRadioReceivingMethod() {
        System.out.println("Switched from AM to FM...");
    }

    public void addStationToFavourites() {
        System.out.println("This Radio Station was saved to your favourites.");
    }

}
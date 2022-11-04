import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UsbPlayer implements Device {

    private static final String[] TRACK_LIST = new String[] {
            "Smells Like Teen Spirit - Nirvana",
            "Billie Jean - Michael Jackson",
            "Stayin' Alive - Bee Gees",
            "I Will Survive - Gloria Gaynor",
            "Scream and Shout - Will.I.Am & Britney Spears"
    };

    private static final int MIN_VOL = 0;
    private static final int MAX_VOL = 100;

    private int currentVol;
    private int currentTrack;

    public UsbPlayer() {
        this.currentVol = 0;
        this.currentTrack = 0;
    }

    public void louder() {
        if (this.currentVol < MAX_VOL) {
            this.currentVol++;
        }
    }

    public void quieter() {
        if (this.currentVol > MIN_VOL) {
            this.currentVol--;
        }
    }

    public int getVolume() {
        return this.currentVol;
    }

    public void next() {
        if (this.currentTrack < (TRACK_LIST.length - 1)) {
            this.currentTrack++;
        }
    }

    public void prev() {
        if (this.currentTrack > 0) {
            this.currentTrack--;
        }
    }

    public String getInfoText() {
        return "Awesom USB device";
    }

    public String[] getOptions() {
        // Get all methods names that are implemented via the interface as List
        final List<String> superMethodNames = new ArrayList<>();

        // Get all methods of the interface
        for (final Method method : Device.class.getMethods()) {
            superMethodNames.add(method.getName());
        }

        // Get all methods of the super class
        for (final Method method : this.getClass().getSuperclass().getMethods()) {
            superMethodNames.add(method.getName());
        }

        // Get all available methods of UsbPlayer as List
        final List<String> usbPlayerMethodNames = new ArrayList<>();

        // Get all methods of UsbPlayer
        for (final Method method : this.getClass().getMethods()) {
            usbPlayerMethodNames.add(method.getName());
        }

        // Remove all methods that are implemented via the interface
        usbPlayerMethodNames.removeAll(superMethodNames);

        return usbPlayerMethodNames.toArray(new String[usbPlayerMethodNames.size()]);
    }

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
            final Method method = UsbPlayer.class.getMethod(opt);
            method.invoke(this);
        } catch (final Exception e) {
            System.err.println("Error while invoking method '" + opt + "': " + e.getMessage());
        }
    }

    public String play() {
        return "Playing: " + TRACK_LIST[this.currentTrack];
    }

    public void ejectUsb() {
        System.out.println("USB ejected.");
    }

    public void formatUsb() {
        System.out.println("USB formatted.");
    }

}

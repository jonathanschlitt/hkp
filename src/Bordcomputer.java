import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class Bordcomputer {

	private String[] deviceName = new String[3];

	private Device installedDevices[] = new Device[3];

	private Device playingDevice;

	public static void main(String[] args) {

		// System.out.println("Config Path = " + System.getProperty("user.dir"));

		Bordcomputer B = new Bordcomputer();

		B.readConfig();

		// System.out.println(Arrays.toString(B.deviceName));

		try {
			B.setDevices();
		} catch (Exception e) {
			System.out.println(e);
		}

		B.showOptions();

		Scanner sc = new Scanner(System.in);

		while (true) {

			int input = 0;

			System.out.println(
					"\nPlease choose an option of the list above. (enter number of option || enter -1 for a random choice || -2 to show volume || -3 for changing device || -4 for exit)");

			input = sc.nextInt();

			if (input == -2) {
				B.showVolume();

			} else if (input == -3) {
				B.changeDevice();

			} else if (input == -4) {
				B.shutdown();

			} else {
				B.enterOption(input);
			}

		}

	}

	private void readConfig() {

		Properties prop = new Properties();

		// Path needs to be changed to the path of the config file
		String fileName = System.getProperty("user.dir") + "/src/Geraete.config";

		try (FileInputStream fis = new FileInputStream(fileName)) {
			prop.load(fis);
		} catch (FileNotFoundException ex) {
			// FileNotFoundException catch is optional and can be collapsed
			System.out.println(ex);
		} catch (IOException ex) {
			System.out.println(ex);
		}

		this.deviceName[0] = prop.getProperty("Radio");
		this.deviceName[1] = prop.getProperty("CD");
		this.deviceName[2] = prop.getProperty("USB");

		System.out.println("\nList of installed devices:\n");

		for (int i = 0; i < deviceName.length; i++) {
			if (!deviceName[i].equals("")) {
				System.out.println("=> " + deviceName[i]);
			}

		}

		System.out.println(Arrays.toString(deviceName));

	}

	private void setDevices() throws ClassNotFoundException, Exception {

		// System.out.println(Arrays.toString(deviceName));

		try {
			for (int i = 0; i < this.deviceName.length; i++) {
				if (!this.deviceName[i].equals("")) {
					Constructor<?> constructor = Class.forName(this.deviceName[i]).getConstructor();

					// System.out.println(Arrays.toString(constructor.getParameterTypes()));

					this.installedDevices[i] = (Device) constructor.newInstance();
				} else {
					this.installedDevices[i] = null;
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		this.playingDevice = installedDevices[0];

	}

	public void shutdown() {
		System.out.println("Shutting down the system... Please wait.");
		System.exit(0);
	}

	public void changeDevice() {

		int num = (int) (Math.random() * this.installedDevices.length - 1);

		Scanner sc = new Scanner(System.in);

		String input = "";

		System.out.println(
				"\nPlease choose a device of the list above. (enter number of option || enter random for a random choice)\n");

		input = sc.next();

		// System.out.println(input);

		if (input.equalsIgnoreCase("random")) {
			this.playingDevice = this.installedDevices[num];
		} else {
			// this.playingDevice = this.installedDevices[input];

			for (int i = 0; i < deviceName.length; i++) {
				if (input.equalsIgnoreCase(deviceName[i])) {
					this.playingDevice = installedDevices[i];
					System.out.println("\nChanged device to " + deviceName[i] + "!");

					this.showOptions();
				}
			}
		}

	}

	public void showOptions() {

		System.out.println("\nAll Options for the current " + this.playingDevice.getClass().getName() + "-Device: \n");

		for (int i = 0; i < this.playingDevice.getOptions().length; i++) {

			System.out.println(i + " " + this.playingDevice.getOptions()[i]);
		}

	}

	public void enterOption(int choice) {
		int num = (int) (Math.random() * this.playingDevice.getOptions().length - 1);

		if (choice < 0) {
			System.out.println("Random Choice: " + num);
			String stringOption = this.playingDevice.getOptions()[num];
			this.playingDevice.chooseOption(stringOption);

		} else {

			// Scanner sc = new Scanner(System.in);
			//
			// int input = 0;
			//
			// System.out.println(
			// "\nPlease choose an option of the list above. (enter number of option ||
			// enter -1 for a random choice)");
			//
			// input = sc.nextInt();

			String stringOption = this.playingDevice.getOptions()[choice];

			// System.out.println("\nYour input: " + choice + " which will call " +
			// this.playingDevice.getOptions()[choice]
			// + "\nnow executing request ...\n");

			this.playingDevice.chooseOption(stringOption);

			// sc.close();

		}
	}

	public void louder(int p) {
		this.playingDevice.louder();
	}

	public void quieter(int p) {
		this.playingDevice.quieter();
	}

	public void showVolume() {
		System.out.println("\nVehicle volume: " + this.playingDevice.getVolume() + "\n");
	}

	public void play() {
		System.out.println(this.playingDevice.play());

	}

	public void prev() {

		this.playingDevice.prev();

		// int index = 0;
		//
		// for(int i = 0; i < installedDevices.length; i++) {
		// if(installedDevices[i].equals(playingDevice)) {
		// index = i;
		// }
		// }
		//
		// if(index == installedDevices.length - 1) {
		// this.playingDevice = installedDevices[0];
		//
		// } else if(index == 0) {
		// this.playingDevice = installedDevices[installedDevices.length - 1];
		//
		// } else {
		// this.playingDevice = installedDevices[index - 1];
		// }

	}

	public void next() {

		this.playingDevice.next();

		// int index = 0;
		//
		// for(int i = 0; i < installedDevices.length; i++) {
		// if(installedDevices[i].equals(playingDevice)) {
		// index = i;
		// }
		// }
		//
		// if(index == installedDevices.length - 1) {
		// this.playingDevice = installedDevices[0];
		//
		// } else if(index == 0) {
		// this.playingDevice = installedDevices[index + 1];
		//
		// } else {
		// this.playingDevice = installedDevices[index + 1];
		// }
	}
}

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

		Method[] availableOptions = Radio.class.getDeclaredMethods();
		String[] options = new String[availableOptions.length];

		for (int i = 0; i < availableOptions.length; i++) {
			options[i] = availableOptions[i].getName();
		}

		return options;
	}

	@Override
	public void chooseOption(String opt) {

		Method[] methods = Radio.class.getDeclaredMethods();

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().contains(opt)) {
				// System.out.println(opt);
				try {
					methods[i].invoke(this, null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				continue;
			}
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

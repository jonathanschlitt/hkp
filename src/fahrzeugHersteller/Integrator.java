package fahrzeugHersteller;

import cdZulieferer.CD;
import radioZulieferer.Radio;
import usbZulieferer.UsbPlayer;
import zuliefererInterface.Device;

public class Integrator {
	
	private final Bordcomputer bc;
	
	public Integrator() {
		this.bc = new Bordcomputer(new Device[] {
			new UsbPlayer(),
			new Radio(),
			new CD()
		});
	}
	
	public Bordcomputer getBordcomputer() {
		return this.bc;
	}
	

}

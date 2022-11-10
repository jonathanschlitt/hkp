package radioZulieferer;

import fahrzeugHersteller.DeviceFactory;

public class RadioFactory implements DeviceFactory {

    @Override
    public Radio createDevice() {
        return new Radio();
    }
}

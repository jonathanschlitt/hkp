package cdZulieferer;

import fahrzeugHersteller.DeviceFactory;

public class CDFactory implements DeviceFactory {

    @Override
    public CD createDevice() {
        return new CD();
    }
}

package usbZulieferer;


import fahrzeugHersteller.DeviceFactory;

public class UsbFactory implements DeviceFactory {
    @Override
    public UsbPlayer createDevice() {
        return new UsbPlayer();
    }
}

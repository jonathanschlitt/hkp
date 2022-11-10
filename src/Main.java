import java.util.Scanner;

import fahrzeugHersteller.Bordcomputer;
import fahrzeugHersteller.DeviceFactory;
import radioZulieferer.RadioFactory;
import usbZulieferer.UsbFactory;
import cdZulieferer.CDFactory;

public class Main {

    public static void main(String[] args) {

        final DeviceFactory radio = new RadioFactory();
        final DeviceFactory cd = new CDFactory();
        final DeviceFactory usb = new UsbFactory();

        final Bordcomputer bc = new Bordcomputer(radio, cd, usb);

        Scanner sc = new Scanner(System.in);

        while (true) {

            // bc.showOptions();

            int input = 0;

            System.out.println(
                    "Please choose an option of the list above. || Enter -1 to change device. || Enter -2 to show options again.");

            input = sc.nextInt();

            // if (input == -1) {
            // bc.changeDevice();
            // }

            switch (input) {
                case -1:
                    bc.changeDevice();
                    break;
                case -2:
                    bc.showOptions();
                    break;
                default:
                    bc.enterOption(input);
            }

        }

    }

}
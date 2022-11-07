import java.util.Scanner;

import fahrzeugHersteller.Bordcomputer;

public class Main {

    public static void main(String[] args) {

        final Bordcomputer bc = new Bordcomputer();

        Scanner sc = new Scanner(System.in);

        bc.showOptions();

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
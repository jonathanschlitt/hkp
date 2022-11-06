import java.util.Scanner;

import fahrzeugHersteller.Boardcomputer;

public class Main {

    public static void main(String[] args) {

        final Boardcomputer bc = new Boardcomputer();

        Scanner sc = new Scanner(System.in);

        while (true) {

            bc.showOptions();

            int input = 0;

            System.out.println(
                    "\nPlease choose an option of the list above.");

            input = sc.nextInt();

            if (input == -1) {
                bc.changeDevice();
            }

            switch (input) {
                case -1:
                    bc.changeDevice();
                    break;
                default:
                    bc.enterOption(input);
            }

        }

    }

}
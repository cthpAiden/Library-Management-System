package lab.pkg01;

import java.util.Scanner;

public class InputHelper {
    public static int readInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Input valid number: ");
            }
        }
    }
}

package lab.pkg01;

import java.util.Scanner;

public class InputHelper {
    public static int readInt(Scanner sc) {
        while (true) {
            // Hết input (EOF) -> thoát êm, tránh crash NoSuchElementException / vòng lặp vô hạn.
            if (!sc.hasNextLine()) {
                System.out.println("\nNo more input. Exiting.");
                System.exit(0);
            }
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Input valid number: ");
            }
        }
    }
}

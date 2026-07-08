package app;

import java.util.Scanner;

// Nơi DUY NHẤT đọc bàn phím. 1 Scanner dùng chung cho toàn app
// (tránh nhiều Scanner trên cùng System.in gây tranh buffer -> mất dòng).
public class InputHelper {
    public static final Scanner sc = new Scanner(System.in);

    // Đọc 1 dòng an toàn: hết input (EOF) thì thoát êm thay vì crash NoSuchElementException.
    private static String nextLineSafe() {
        if (!sc.hasNextLine()) {
            System.out.println("\nNo more input. Exiting.");
            System.exit(0);
        }
        return sc.nextLine();
    }

    // Đọc nguyên 1 dòng (dùng cho ID, từ khóa, ngày...).
    public static String readLine() {
        return nextLineSafe();
    }

    // Đọc số cho menu: nhập sai hỏi lại. Không chặn số âm.
    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(nextLineSafe().trim());
            } catch (NumberFormatException e) {
                System.out.print("Input valid number: ");
            }
        }
    }

    // Đọc số không âm kèm prompt (dùng khi nhập quantity).
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int n = Integer.parseInt(nextLineSafe().trim());
                if (n < 0) { System.out.println("Number must be >= 0."); continue; }
                return n;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Đọc chuỗi: bỏ ký tự '|' vì đó là dấu phân cách khi lưu file (tránh vỡ dữ liệu).
    public static String readText(String prompt) {
        System.out.print(prompt);
        return nextLineSafe().replace("|", "/");
    }

    public static String readRequiredText(String prompt) {
        while (true) {
            String s = readText(prompt).trim();
            if (!s.isEmpty()) return s;
            System.out.println("This field cannot be empty.");
        }
    }

    public static String readYear(String prompt) {
        while (true) {
            int y = readInt(prompt);
            if (y >= 1 && y <= 2100) return String.valueOf(y);
            System.out.println("Year must be between 1 and 2100.");
        }
    }
}

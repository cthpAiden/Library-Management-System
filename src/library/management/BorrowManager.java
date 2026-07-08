package library.management;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Scanner;
import schema.*;

public class BorrowManager {
    private final ArrayList<Borrowing> borrowList;
    private final BookManager bookManager;
    private final MemberManager memberManager;
    private final Scanner sc = book.sc;
    // "d/M/uuuu" chấp nhận cả 5/6/2026 lẫn 05/06/2026.
    // STRICT để từ chối ngày không tồn tại (vd 31/02/2026) thay vì tự đổi thành 28/02.
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public BorrowManager(ArrayList<Borrowing> borrowList, BookManager bookManager, MemberManager memberManager) {
        this.borrowList = borrowList;
        this.bookManager = bookManager;
        this.memberManager = memberManager;
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("================================");
            System.out.println("      BORROWING / RETURNING     ");
            System.out.println("================================");
            System.out.println("1. Borrow book");
            System.out.println("2. Return book");
            System.out.println("3. Display borrowed list");
            System.out.println("4. Borrowing history by member");
            System.out.println("5. Return");
            System.out.print("Select your choice: ");
            choice = InputHelper.readInt(sc);
            switch (choice) {
                case 1: borrowBook(); break;
                case 2: returnBook(); break;
                case 3: showBorrowingList(); break;
                case 4:
                    System.out.print("Input Member ID: ");
                    showHistoryByMember(InputHelper.readLine(sc).trim());
                    break;
                case 5: System.out.println("Returning to main menu..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    public void borrowBook() {
        if (bookManager.isEmpty()) {
            System.out.println("Book storage is empty!");
            return;
        }

        System.out.print("Member ID: ");
        String memberID = InputHelper.readLine(sc).trim();
        Member mb = memberManager.findMemberByID(memberID);
        if (mb == null) { System.out.println("Member not found!"); return; }

        int count = 0;
        for (Borrowing br : borrowList) {
            if (br.getMemberID().equalsIgnoreCase(memberID) && !br.isReturned()) count++;
        }
        if (count >= 3) { System.out.println("Member has reached the borrowing limit!"); return; }

        System.out.print("Book ID: ");
        String bookID = InputHelper.readLine(sc).trim();
        book bk = bookManager.findBookByID(bookID);
        if (bk == null) { System.out.println("Book not found!"); return; }
        if (bk.getQuantity() <= 0) { System.out.println("This book is out of stock!"); return; }

        for (Borrowing br : borrowList) {
            if (br.getMemberID().equalsIgnoreCase(memberID) && br.getBookID().equalsIgnoreCase(bookID) && !br.isReturned()) {
                System.out.println("Member is already borrowing this book!");
                return;
            }
        }

        System.out.print("Borrow Date (DD/MM/YYYY): ");
        LocalDate borrowDate = readDate();
        if (borrowDate == null) return;
        if (borrowDate.isAfter(LocalDate.now())) {
            System.out.println("Borrow date cannot be in the future!");
            return;
        }

        if (!confirm()) { System.out.println("Operation cancelled."); return; }

        borrowList.add(new Borrowing(memberID, bookID, borrowDate, null, false));
        bk.setQuantity(bk.getQuantity() - 1);
        System.out.println("Borrow book successfully!");
    }

    public void returnBook() {
        System.out.print("Member ID: ");
        String memberID = InputHelper.readLine(sc).trim();
        if (memberManager.findMemberByID(memberID) == null) { System.out.println("Member not found!"); return; }

        System.out.print("Book ID: ");
        String bookID = InputHelper.readLine(sc).trim();
        book bk = bookManager.findBookByID(bookID);
        if (bk == null) { System.out.println("Book not found!"); return; }

        Borrowing record = null;
        for (Borrowing br : borrowList) {
            if (br.getMemberID().equalsIgnoreCase(memberID) && br.getBookID().equalsIgnoreCase(bookID) && !br.isReturned()) {
                record = br; break;
            }
        }
        if (record == null) { System.out.println("Borrowing record not found!"); return; }

        System.out.print("Return Date (DD/MM/YYYY): ");
        LocalDate returnDate = readDate();
        if (returnDate == null) return;
        if (returnDate.isAfter(LocalDate.now())) {
            System.out.println("Return date cannot be in the future!");
            return;
        }
        if (returnDate.isBefore(record.getBorrowDate())) {
            System.out.println("Return date cannot be before borrow date!");
            return;
        }

        if (!confirm()) { System.out.println("Operation cancelled."); return; }

        record.setReturnDate(returnDate);
        record.setReturned(true);
        bk.setQuantity(bk.getQuantity() + 1);

        long fine = record.tinhTienPhat(returnDate);
        System.out.println("Return book successfully! Fine: " + fine + " VND");
    }

    public void showBorrowingList() {
        if (borrowList.isEmpty()) {
            System.out.println("No borrowing records yet.");
            return;
        }

        Borrowing.showHeader();
        for (Borrowing br : borrowList) {
            br.showInfor();
        }
    }

    public void showHistoryByMember(String memberID) {
        if (memberManager.findMemberByID(memberID) == null) { System.out.println("Member not found!"); return; }
        boolean found = false;
        for (Borrowing br : borrowList) {
            if (br.getMemberID().equalsIgnoreCase(memberID)) {
                if (!found) Borrowing.showHeader();
                br.showInfor();
                found = true;
            }
        }
        if (!found) System.out.println("No borrowing history yet.");
    }

    private LocalDate readDate() {
        try { return LocalDate.parse(InputHelper.readLine(sc).trim(), FMT); }
        catch (DateTimeParseException e) { System.out.println("Invalid date! Format must be DD/MM/YYYY"); return null; }
    }

    private boolean confirm() {
        System.out.println("[1] Confirm  [2] Cancel");
        return InputHelper.readInt(sc) == 1;
    }
}

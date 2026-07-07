package lab.pkg01;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import schema.*;

public class ReportService {
    private final ArrayList<book> bookList;
    private final ArrayList<Member> memberList;
    private final ArrayList<Borrowing> borrowList;
    private final BorrowManager borrowManager;
    private final Scanner sc = book.sc;

    public ReportService(ArrayList<book> bookList, ArrayList<Member> memberList, ArrayList<Borrowing> borrowList, BorrowManager borrowManager) {
        this.bookList = bookList;
        this.memberList = memberList;
        this.borrowList = borrowList;
        this.borrowManager = borrowManager;
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("================================");
            System.out.println("            REPORTS             ");
            System.out.println("================================");
            System.out.println("1. List of borrowed books");
            System.out.println("2. List of overdue books");
            System.out.println("3. Most popular books");
            System.out.println("4. Top borrowing members");
            System.out.println("5. General statistics");
            System.out.println("6. Return");
            System.out.print("Select your choice: ");
            choice = InputHelper.readInt(sc);
            switch (choice) {
                case 1: borrowManager.showBorrowingList(); break;
                case 2: showOverdueBooks(); break;
                case 3: showPopularBooks(); break;
                case 4: showTopMembers(); break;
                case 5: showGeneralStatistics(); break;
                case 6: System.out.println("Returning to main menu..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 6);
    }

    public void showOverdueBooks() {
        boolean found = false;
        LocalDate today = LocalDate.now();
        for (Borrowing br : borrowList) {
            if (!br.isReturned() && br.tinhNgayQuaHan(today) > 0) {
                if (!found) Borrowing.showHeader();
                br.showInfor();
                System.out.println("Overdue days: " + br.tinhNgayQuaHan(today));
                found = true;
            }
        }
        if (!found) System.out.println("No overdue books.");
    }

    public void showPopularBooks() {
        int max = 0;
        for (book bk : bookList) {
            int count = 0;
            for (Borrowing br : borrowList) if (br.getBookID().equalsIgnoreCase(bk.getBookID())) count++;
            if (count > max) max = count;
        }
        if (max == 0) { System.out.println("No borrowing data yet!"); return; }
        book.showHeader();
        for (book bk : bookList) {
            int count = 0;
            for (Borrowing br : borrowList) if (br.getBookID().equalsIgnoreCase(bk.getBookID())) count++;
            if (count == max) { bk.showInfor(); System.out.println("Borrow Times: " + count); }
        }
    }

    public void showTopMembers() {
        int max = 0;
        for (Member mb : memberList) {
            int count = 0;
            for (Borrowing br : borrowList) if (br.getMemberID().equalsIgnoreCase(mb.getMemberID())) count++;
            if (count > max) max = count;
        }
        if (max == 0) { System.out.println("No borrowing data yet!"); return; }
        Member.showHeader();
        for (Member mb : memberList) {
            int count = 0;
            for (Borrowing br : borrowList) if (br.getMemberID().equalsIgnoreCase(mb.getMemberID())) count++;
            if (count == max) { mb.showInfor(); System.out.println("Borrow Times: " + count); }
        }
    }

    public void showGeneralStatistics() {
        int borrowing = 0, returned = 0;
        for (Borrowing br : borrowList) {
            if (br.isReturned()) returned++; else borrowing++;
        }
        System.out.println("Total books: " + bookList.size());
        System.out.println("Total members: " + memberList.size());
        System.out.println("Total borrowings: " + borrowList.size());
        System.out.println("Books currently borrowed: " + borrowing);
        System.out.println("Books returned: " + returned);
    }
}

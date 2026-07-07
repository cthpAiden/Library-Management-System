package lab.pkg01;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import schema.*;

public class LAB01 {
    private final ArrayList<book> bookList = new ArrayList<>();
    private final ArrayList<Member> memberList = new ArrayList<>();
    private final ArrayList<Borrowing> borrowList = new ArrayList<>();
    private final Scanner sc = book.sc;

    private final FileManager fileManager = new FileManager(bookList, memberList, borrowList);
    private final BookManager bookManager = new BookManager(bookList, borrowList);
    private final MemberManager memberManager = new MemberManager(memberList, borrowList);
    private final BorrowManager borrowManager = new BorrowManager(borrowList, bookManager, memberManager);
    private final ReportService reportService = new ReportService(bookList, memberList, borrowList, borrowManager);

    public LAB01() {
        if (fileManager.hasSavedData()) {
            fileManager.loadData(bookList, memberList, borrowList);
        } else {
            seedData();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(fileManager::saveData));
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("================================");
            System.out.println("   LIBRARY MANAGEMENT SYSTEM    ");
            System.out.println("================================");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Members");
            System.out.println("3. Borrowing / Returning");
            System.out.println("4. Reports");
            System.out.println("5. Save to file");
            System.out.println("6. Load from file");
            System.out.println("7. Exit");
            System.out.print("Select your choice: ");
            choice = InputHelper.readInt(sc);
            switch (choice) {
                case 1: bookManager.showMenu(); break;
                case 2: memberManager.showMenu(); break;
                case 3: borrowManager.showMenu(); break;
                case 4: reportService.showMenu(); break;
                case 5: fileManager.saveData(); break;
                case 6: fileManager.loadData(bookList, memberList, borrowList); break;
                case 7:
                    System.out.println("Exiting program... Goodbye! (data is saved automatically)");
                    break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 7);
    }

    private void seedData() {
        // B02 để quantity 3 vì có 2 bản đang được mượn (đủ để 2 người mượn cùng lúc)
        bookList.add(new Novel("B01", "Truyen Kieu", "Nguyen Du", "1820", 4, "Romance"));
        bookList.add(new Textbook("B02", "Mathematics 9", "Phan Duc Chinh", "2005", 3, "Mathematics"));
        bookList.add(new Comic("B03", "Tham Tu Conan", "Gosho Aoyama", "1994", 9, 42));
        bookList.add(new Others("B04", "Tu Dien Tieng Viet", "Hoang Phe", "2018", 4, "Dictionary"));

        memberList.add(new Member("M01", "Nguyen Van A", "vana@gmail.com", "0901111111"));
        memberList.add(new Member("M02", "Tran Thi B", "thib@gmail.com", "0902222222"));
        memberList.add(new Member("M03", "Le Van C", "vanc@gmail.com", "0903333333"));

        borrowList.add(new Borrowing("M01", "B01", LocalDate.now(), null, false));
        borrowList.add(new Borrowing("M01", "B02", LocalDate.now(), null, false));
        borrowList.add(new Borrowing("M02", "B02", LocalDate.now().minusDays(30), null, false));

        // Trừ số lượng sách đang được mượn để tồn kho khớp với thực tế (giống borrowBook)
        for (Borrowing br : borrowList) {
            if (!br.isReturned()) {
                book bk = bookManager.findBookByID(br.getBookID());
                if (bk != null) bk.setQuantity(bk.getQuantity() - 1);
            }
        }
    }
}

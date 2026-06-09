package lab.pkg01;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import schema.Borrowing;
import schema.Comic;
import schema.Member;
import schema.Novel;
import schema.Others;
import schema.Textbook;
import schema.book;

public class LAB01 {
    private List<book> bookList = new ArrayList<>();
    private List<Member> memberList = new ArrayList<>();
    private List<Borrowing> borrowList = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    
    // Định dạng ngày dùng chung cho LAB01
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // constructor: nạp sẵn dữ liệu mẫu
    public LAB01() {
        seedData();
    }

    // Dữ liệu mẫu: 3 sách (mỗi lớp con) và 3 thành viên
    private void seedData() {
        bookList.add(new Novel("B01", "Truyen Kieu", "Nguyen Du", "1820", 5, "Romance"));
        bookList.add(new Textbook("B02", "Mathematics 9", "Phan Duc Chinh", "2005", 3, "Mathematics"));
        bookList.add(new Comic("B03", "Detective Conan", "Gosho Aoyama", "1994", 10, 42));

        memberList.add(new Member("M01", "Nguyen Van A", "vana@gmail.com", "0901111111"));
        memberList.add(new Member("M02", "Tran Thi B", "thib@gmail.com", "0902222222"));
        memberList.add(new Member("M03", "Le Van C", "vanc@gmail.com", "0903333333"));

        // M01 mượn sẵn 3 cuốn (= giới hạn tối đa) để test trường hợp mượn quá số lượng
        seedBorrow("M01", "B01");
        seedBorrow("M01", "B02");
        seedBorrow("M01", "B03");

        // M02 mượn B02 từ 30 ngày trước, chưa trả -> sách quá hạn để test báo cáo
        seedBorrow("M02", "B02", LocalDate.now().minusDays(30));
    }

    // Tạo 1 lượt mượn mẫu với ngày mượn = hôm nay
    private void seedBorrow(String memberID, String bookID) {
        seedBorrow(memberID, bookID, LocalDate.now());
    }

    // Tạo 1 lượt mượn mẫu: thêm record và giảm số lượng sách trong kho
    private void seedBorrow(String memberID, String bookID, LocalDate borrowDate) {
        borrowList.add(new Borrowing(memberID, bookID, borrowDate, null, false));
        book bk = timSachTheoID(bookID);
        if (bk != null) {
            bk.setQuantity(bk.getQuantity() - 1);
        }
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("================================");
            System.out.println("   LIBRARY MANAGEMENT SYSTEM   ");
            System.out.println("================================");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Members");
            System.out.println("3. Borrowing/Returning");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.println("================================");
            System.out.print("Select your choice: ");
            choice = nhapSoNguyen();
 
            switch (choice) {
                case 1: {
                    showMenuBook();
                    break;
                }
                case 2: {
                    showMenuMember();
                    break;
                }
                case 3: {
                    showMenuBorrow();
                    break;
                }
                case 4: {
                    showMenuReport();
                    break;
                }
                case 5: {
                    System.out.println("Exiting program... Goodbye!");
                    break;
                }
                default: {
                    System.out.println("Invalid choice, please input again!");
                }
            }
        } while (choice != 5);
    }
 
    // ===================== MENU SÁCH =====================
    public void showMenuBook() {
        int choice;
        do {
            System.out.println("================================");
            System.out.println("         MANAGE BOOKS          ");
            System.out.println("================================");
            System.out.println("1. Add book");
            System.out.println("2. Display all books");
            System.out.println("3. Find books by ID");
            System.out.println("4. Remove book");
            System.out.println("5. Return");
            System.out.println("================================");
            System.out.print("Select your choice: ");
            choice = nhapSoNguyen();
 
            switch (choice) {
                case 1: {
                    addBook();
                    break;
                }
                case 2: {
                    showListBook();
                    break;
                }
                case 3: {
                    System.out.print("Input Book ID (find by ID): ");
                    String ID = sc.nextLine();
                    findBook(ID);
                    break;
                }
                case 4: {
                    System.out.print("Input Book ID (remove by ID): ");
                    String id = sc.nextLine();
                    deletedBook(id);
                    break;
                }
                case 5: {
                    System.out.println("Returning to main menu...");
                    break;
                }
                default: {
                    System.out.println("Invalid choice, please input again!");
                }
            }
        } while (choice != 5);
    }
    
    public int nhapSoNguyen() {
    while (true) {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.print("Input valid number: ");
        }
    }
}
 
    // ===================== CHỨC NĂNG SÁCH =====================
    public void addBook() {
    System.out.println("--------- BOOK TYPE ---------");
    System.out.println("1. Novel");
    System.out.println("2. Textbook");
    System.out.println("3. Comic");
    System.out.println("4. Others");
    System.out.print("Choose book type: ");
    int type = nhapSoNguyen();

    book bk;
    switch (type) {
        case 1: bk = new Novel();    break;
        case 2: bk = new Textbook(); break;
        case 3: bk = new Comic();    break;
        case 4: bk = new Others();   break;
        default:
            System.out.println("Invalid choice, please input again!");
            return;
    }

    bk.inputInfor(); // gọi đa hình: chạy đúng inputInfor() của lớp con
    if (timSachTheoID(bk.getBookID()) != null) {
        System.out.println("Book ID already existed!");
        return;
    }
    bookList.add(bk); // Upcasting: List<book> chứa đối tượng lớp con
    System.out.println("Successfully added!");
}
 
    public void showListBook() {
        if (bookList.isEmpty()) {
            System.out.println("Empty list!");
            return;
        }
        book.showHeader();
        for (book bk : bookList) {
            bk.showInfor();
        }
    }
 
    public boolean findBook(String bookID) {
        for (book bk : bookList) {
            if (bk.getBookID().equalsIgnoreCase(bookID)) {
                book.showHeader();
                bk.showInfor();
                return true;
            }
        }
        System.out.println("Unable to find book with the following ID: " + bookID);
        return false;
    }

    public book timSachTheoID(String bookID) {
        for (book bk : bookList) {
            if (bk.getBookID().equalsIgnoreCase(bookID)) {
                return bk;
            }
        }
        return null;
    }
 
    public void deletedBook(String bookID) {
    for (Borrowing br : borrowList) {
        if (br.getBookID().equalsIgnoreCase(bookID)
                && !br.isReturned()) {
            System.out.println("Books are being borrowed, unable to process!");
            return;
        }
    }
    boolean removed = false;
    for (int i = 0; i < bookList.size(); i++) {
        if (bookList.get(i).getBookID().equalsIgnoreCase(bookID)) {
            bookList.remove(i);
            System.out.println("Successfully removed!");
            removed = true;
            break;
        }
    }

    if (!removed) {
        System.out.println("Book not found!");
    }
}

    // ===================== MENU THÀNH VIÊN =====================
    public void showMenuMember() {
        int choice;
        do {
            System.out.println("================================");
            System.out.println("        MANAGE MEMBERS         ");
            System.out.println("================================");
            System.out.println("1. Add member");
            System.out.println("2. Display all members");
            System.out.println("3. Find members by ID");
            System.out.println("4. Remove member");
            System.out.println("5. Return");
            System.out.println("================================");
            System.out.print("Select your choice: ");
            choice = nhapSoNguyen();
 
            switch (choice) {
                case 1: {
                    addMember();
                    break;
                }
                case 2: {
                    showListMember();
                    break;
                }
                case 3: {
                    System.out.print("Input Member ID (find by ID): ");
                    String id = sc.nextLine();
                    // ĐÃ SỬA: Thực hiện in dữ liệu khi tìm trực tiếp tại menu này
                    Member mb = findMemberByID(id);
                    if (mb != null) {
                        Member.showHeader();
                        mb.showInfor();
                    } else {
                        System.out.println("Member not found!");
                    }
                    break;
                }
                case 4: {
                    System.out.print("Input Member ID (remove by ID): ");
                    String id = sc.nextLine();
                    deletedMember(id);
                    break;
                }
                case 5: {
                    System.out.println("Returning to main menu...");
                    break;
                }
                default: {
                    System.out.println("Invalid choice, please input again!");
                }
            }
        } while (choice != 5);
    }
 
    // ===================== CHỨC NĂNG THÀNH VIÊN =====================
    public void addMember() {
    Member mb = new Member();
    mb.inputInfor();
    if (findMemberByID(mb.getMemberID()) != null) {
        System.out.println("Member ID already existed!");
        return;
    }
    memberList.add(mb);
    System.out.println("Successfully added!");
}
 
    public void showListMember() {
        if (memberList.isEmpty()) {
            System.out.println("Empty list!");
            return;
        }
        Member.showHeader();
        for (Member mb : memberList) {
            mb.showInfor();
        }
    }
 
    public Member findMemberByID(String memberID) {
        for (Member mb : memberList) {
            if (mb.getMemberID().equalsIgnoreCase(memberID)) {
                return mb;
            }
        }
        return null;
    }
 
   public void deletedMember(String memberID) {
    for (Borrowing br : borrowList) {
        if (br.getMemberID().equalsIgnoreCase(memberID)
                && !br.isReturned()) {
            System.out.println("Member is borrowing books, unable to process!");
            return;
        }
    }
    boolean removed = false;
    for (int i = 0; i < memberList.size(); i++) {
        if (memberList.get(i).getMemberID().equalsIgnoreCase(memberID)) {
            memberList.remove(i);
            System.out.println("Successfully removed!");
            removed = true;
            break;
        }
    }
    if (!removed) {
        System.out.println("Member not found!");
    }
}
 
    // ===================== MENU MƯỢN/TRẢ =====================
    public void showMenuBorrow() {
        int choice;
        do {
            System.out.println("================================");
            System.out.println("      BORROWING / RETURNING    ");
            System.out.println("================================");
            System.out.println("1. Borrow book");
            System.out.println("2. Return book");
            System.out.println("3. Display borrowed list");
            System.out.println("4. Borrowing history by member");
            System.out.println("5. Return");
            System.out.println("================================");
            System.out.print("Select your choice: ");
            choice = nhapSoNguyen();
 
            switch (choice) {
                case 1: { muonSach();  break; }
                case 2: { traSach();   break; }
                case 3: { xemDanhSachDangMuon(); break; }
                case 4: {
                    System.out.print("Input Member ID: ");
                    lichSuMuonTheoMember(sc.nextLine());
                    break;
                }
                case 5: { System.out.println("Returning to main menu..."); break; }
                default: { System.out.println("Invalid choice, please input again!"); }
            }
        } while (choice != 5);
    }
    
    // Mượn Sách
    public void muonSach() {
        if (bookList.isEmpty()) {
            System.out.println("Book storage is empty!");
            return;
        }
        System.out.println("----------- BORROW BOOK -----------");
        
        System.out.print("Member ID: ");
        String memberID = sc.nextLine();
        Member mb = findMemberByID(memberID);
        if (mb == null) {
            System.out.println("Member not found!");
            return;
        }
        
        int borrowingCount = 0;

        for (Borrowing br : borrowList) {
            if (br.getMemberID().equalsIgnoreCase(memberID)
                    && !br.isReturned()) {
                borrowingCount++;
            }
        }

        if (borrowingCount >= 3) {
            System.out.println("Member has reached the borrowing limit!");
            return;
        }
 
        System.out.print("Book ID: ");
        String bookID = sc.nextLine();
        book bk = timSachTheoID(bookID);
        if (bk == null) {
            System.out.println("Book not found!");
            return;
        }
        
        if (bk.getQuantity() <= 0) {
            System.out.println("This book is out of stock!");
            return;
        }
        
        for (Borrowing br : borrowList) {
            if (br.getMemberID().equalsIgnoreCase(memberID)
                    && br.getBookID().equalsIgnoreCase(bookID)
                    && !br.isReturned()) {
                System.out.println("Member is already borrowing this book!");
                return;
            }
        }
        
        System.out.print("Borrow Date (DD/MM/YYYY): ");
        LocalDate borrowDate = nhapNgay();
        if (borrowDate == null) return;
 
        System.out.println("[1] Confirm  [2] Cancel");
        int confirm = sc.nextInt(); sc.nextLine(); // clear buffer
        if (confirm != 1) {
            System.out.println("Operation cancelled.");
            return;
        }
 
        Borrowing record = new Borrowing(memberID, bookID, borrowDate, null, false);// chưa trả, đang mượn
        borrowList.add(record);// add những  infor của thằng này vào  list borrow
        bk.setQuantity(bk.getQuantity() - 1);// giảm số lượn khi đã  được mượn
 
        System.out.printf("Book '%s' borrowed by '%s' successfully.%n",
                bk.getNameBook(), mb.getNameMember());
    }
 
    // Trả sách
    public void traSach() {
        System.out.println("----------- RETURN BOOK -----------");
 
        System.out.print("Member ID: ");
        String memberID = sc.nextLine();
        Member mb = findMemberByID(memberID);
        if (mb == null) {
            System.out.println("Member not found!");
            return;
        }
 
        System.out.print("Book ID: ");
        String bookID = sc.nextLine();
        book bk = timSachTheoID(bookID);
        if (bk == null) {
            System.out.println("Book not found!");
            return;
        }
 
        Borrowing record = null;// record này ko có gì hết 
        for (Borrowing br : borrowList) {
            if (br.getMemberID().equalsIgnoreCase(memberID)
                    && br.getBookID().equalsIgnoreCase(bookID)
                    && !br.isReturned()) {
                record = br;
                break;
            }
        }
        if (record == null) {
            System.out.println("Borrowing history for this book not found!");
            return;
        }
 
        System.out.print("Return Date (DD/MM/YYYY): ");
        LocalDate returnDate = nhapNgay();
        if (returnDate == null) return;
 
        System.out.println("[1] Confirm  [2] Cancel");
        int confirm = sc.nextInt(); sc.nextLine(); // clear buffer
        if (confirm != 1) {
            System.out.println("Operation cancelled.");
            return;
        }
 
        record.setReturnDate(returnDate);
        record.setReturned(true);// đã trả
        bk.setQuantity(bk.getQuantity() + 1);// trả thì số lượng trong kho tăng lên 1
 
        long tienPhat = record.tinhTienPhat(returnDate);
        if (tienPhat == 0) {
            System.out.printf("Book: '%s' returned by '%s'. Not overdue.%n",
                    bk.getNameBook(), mb.getNameMember());
        } else {
            System.out.printf("Book: '%s' returned by '%s'. Overdue | fine: %,d VND.%n",
                    bk.getNameBook(), mb.getNameMember(), tienPhat);
        }
    }
 
    // Xem danh sách đang mượn
    public void xemDanhSachDangMuon() {
        boolean found = false;
        boolean headerShown = false;// biến check
        for (Borrowing br : borrowList) {
            if (!br.isReturned()) {//chạy từng sách xem trả chưa
                if (!headerShown) {// chưa trả sẽ vào luồng in ra
                    Borrowing.showHeader();
                    headerShown = true;
                }
                br.showInfor();
                found = true;
            }
        }
        if (!found) System.out.println("No books are currently being borrowed.");
    }
 
    // Lịch sử mượn sách của 1 thành viên
    public void lichSuMuonTheoMember(String memberID) {
        Member mb = findMemberByID(memberID);
        if (mb == null) {
            System.out.println("Member not found!");
            return;
        }
        System.out.println("Borrowing history of: " + mb.getNameMember());
        boolean found = false;
        boolean headerShown = false;
        for (Borrowing br : borrowList) {
            if (br.getMemberID().equalsIgnoreCase(memberID)) {
                if (!headerShown) {
                    Borrowing.showHeader();
                    headerShown = true;
                }
                br.showInfor();
                found = true;
            }
        }
        if (!found) System.out.println("No borrowing history yet.");
    }
    
    private LocalDate nhapNgay() {
        String input = sc.nextLine();
        try {
            return LocalDate.parse(input, FMT);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date! Format must be DD/MM/YYYY");
            return null;
        }
    }
     // ===================== BÁO CÁO =====================
    public void showMenuReport() {
    int choice;
    do {
        System.out.println("================================");
        System.out.println("            REPORTS            ");
        System.out.println("================================");
        System.out.println("1. List of borrowed books");
        System.out.println("2. List of overdue books");
        System.out.println("3. Most popular books");
        System.out.println("4. Top borrowing members");
        System.out.println("5. General statistics");
        System.out.println("6. Return");
        System.out.println("================================");

        System.out.print("Select your choice: ");
        choice = nhapSoNguyen();

        switch (choice) {
            case 1: {
                xemDanhSachDangMuon();
                break;
            }
            case 2: {
                xemSachQuaHan();
                break;
            }
            case 3: {
                showPopularBooks();
                break;
            }
            case 4: {
                showTopMembers();
                break;
            }
            case 5: {
                showReports();
                break;
            }
            case 6: {
                System.out.println("Returning to main menu...");
                break;
            }
            default: {
                System.out.println("Invalid choice!");
            }
        }   
    } while (choice != 6);
}
    
    public void xemSachQuaHan() {
    boolean found = false;
    LocalDate today = LocalDate.now();
    System.out.println("===== OVERDUE BOOKS =====");
    for (Borrowing br : borrowList) {
        if (!br.isReturned()) {
            long overdue = br.tinhNgayQuaHan(today);
            if (overdue > 0) {
                br.showInfor();
                System.out.println("Overdue Days: " + overdue);
                System.out.println("----------------------");
                found = true;
            }
        }
    }

    if (!found) {
        System.out.println("No overdue books.");
    }
}
    
    public void showPopularBooks() {
    if (borrowList.isEmpty()) {
        System.out.println("No data yet!");
        return;
    }

    int max = 0;
    for (book bk : bookList) {
        int count = 0;
        for (Borrowing br : borrowList) {
            if (br.getBookID().equalsIgnoreCase(bk.getBookID())) {
                count++;
            }
        }

        if (count > max) {
            max = count;

        }
    }

    System.out.println("===== MOST POPULAR BOOK =====");
    for (book bk : bookList) {
        int count = 0;
        for (Borrowing br : borrowList) {
            if (br.getBookID().equalsIgnoreCase(bk.getBookID())) {
                count++;
            }
        }

        if (count == max && count > 0) {
            bk.showInfor();
            System.out.println("Borrow Times: " + count);
            System.out.println("----------------------");
        }
    }
}
    
    public void showTopMembers() {
    if (borrowList.isEmpty()) {
        System.out.println("No data yet!");
        return;
    }

    int max = 0;
    for (Member mb : memberList) {
        int count = 0;
        for (Borrowing br : borrowList) {
            if (br.getMemberID().equalsIgnoreCase(mb.getMemberID())) {
                count++;
            }
        }

        if (count > max) {
            max = count;

        }
    }

    System.out.println("===== TOP MEMBERS =====");
    for (Member mb : memberList) {
        int count = 0;
        for (Borrowing br : borrowList) {
            if (br.getMemberID().equalsIgnoreCase(mb.getMemberID())) {
                count++;
            }
        }

        if (count == max && count > 0) {
            mb.showInfor();
            System.out.println("Borrow Times: " + count);
            System.out.println("----------------------");
        }
    }
}
    
    
        // ===================== THỐNG KÊ TỔNG QUÁT =====================
        public void showReports() {
            System.out.println("================================");
            System.out.println("       GENERAL REPORTS         ");
            System.out.println("================================");
            System.out.println("Total books: " + bookList.size());
            System.out.println("Total members: " + memberList.size());
            System.out.println("Total borrowings: " + borrowList.size());
            int borrowingBooks = 0;
            for (Borrowing br : borrowList) {
                if (!br.isReturned()) {
                    borrowingBooks++;
                }
            }

            System.out.println("Books currently borrowed: " + borrowingBooks);
            int returnedBooks = 0;
            for (Borrowing br : borrowList) {
                if (br.isReturned()) {
                    returnedBooks++;
                }
            }

            System.out.println("Books returned: " + returnedBooks);
            System.out.println("================================");
        }
   
}
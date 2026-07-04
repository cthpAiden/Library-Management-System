package schema;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Borrowing {
    // property
    private String memberID;       // ID thành viên
    private String bookID;         // ID sách
    private LocalDate borrowDate;  // ngày mượn
    private LocalDate returnDate;  // ngày trả (null nếu chưa trả)
    private boolean returned;      // đã trả chưa
    private static final int MAX_BORROW_DAYS = 7;         // số ngày được mượn tối đa
    private static final long FINE_PER_DAY   = 5000;      // phạt 5,000 VND/ngày
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // constructor
    public Borrowing() {
    }

    public Borrowing(String memberID, String bookID, LocalDate borrowDate, LocalDate returnDate, boolean returned) {
        this.memberID = memberID;
        this.bookID = bookID;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.returned = returned;
    }

    //getter
    public String getMemberID() {
        return memberID;
    }

    public String getBookID() {
        return bookID;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    // setter
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    // tính ngày quá hạn
    public long tinhNgayQuaHan(LocalDate ngayTra) {
        LocalDate dueDateDate = borrowDate.plusDays(MAX_BORROW_DAYS);
        long overdue = ChronoUnit.DAYS.between(dueDateDate, ngayTra);
        return overdue > 0 ? overdue : 0;// nêu >0 thì  return số ngày trễ còn ko thì return ngày trễ là 0
    }
 
    // tính tiền phạt
    public long tinhTienPhat(LocalDate ngayTra) {
        return tinhNgayQuaHan(ngayTra) * FINE_PER_DAY;
    }
 
    // ngày đến hạn trả
    public LocalDate getDueDate() {
        return borrowDate.plusDays(MAX_BORROW_DAYS);
    }

    // hiển thị header bảng mượn sách
    public static void showHeader() {
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("|%-10s|%-10s|%-15s|%-15s|%-12s|%n", "BookID", "MemberID", "Borrow Date", "Due Date", "Status");
    }

    // hiển thị 1 dòng record
    public void showInfor() {
        String status = returned ? "Returned" : "Borrowing";
        System.out.printf("|%-10s|%-10s|%-15s|%-15s|%-12s|%n",
                this.bookID, this.memberID, this.borrowDate.format(FMT),
                this.getDueDate().format(FMT), status);
    }

    // dòng ghi file: memberID|bookID|borrowDate|returnDate(hoặc "null")|returned
    @Override
    public String toString() {
        String ret = returnDate == null ? "null" : returnDate.format(FMT);
        return memberID + "|" + bookID + "|" + borrowDate.format(FMT) + "|" + ret + "|" + returned;
    }
}
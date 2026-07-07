package schema;

import java.util.Scanner;

public class book {

    // Scanner dùng chung cho toàn bộ chương trình (tránh tạo nhiều Scanner
    // trên cùng System.in gây tranh buffer -> mất dòng / NoSuchElementException)
    public static final Scanner sc = new Scanner(System.in);

    //property
    private String bookID;         // id sách
    private String nameBook;        // tên sách
    private String author;         // tác giả
    private String publicationYear;// năm xuất bản
    private int quantity;          // số lượng

    // constructor

    public book() {
    }
    
    public book(String bookID, String nameBook,
            String author, String publicationYear, int quantity) {

        this.bookID = bookID;
        this.nameBook = nameBook;
        this.author = author;
        this.publicationYear = publicationYear;
        this.quantity = quantity;
    }
    
    //getter
    public String getBookID() {
        return bookID;
    }

    public String getNameBook() {    
        return nameBook;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public int getQuantity() {    
        return quantity;
    }

    // setter
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Đọc 1 dòng an toàn: hết input (EOF) thì thoát êm thay vì crash NoSuchElementException.
    private static String nextLineSafe() {
        if (!sc.hasNextLine()) {
            System.out.println("\nNo more input. Exiting.");
            System.exit(0);
        }
        return sc.nextLine();
    }

    // Đọc chuỗi: bỏ ký tự '|' vì đó là dấu phân cách khi lưu file (tránh vỡ dữ liệu).
    protected static String readText(String prompt) {
        System.out.print(prompt);
        return nextLineSafe().replace("|", "/");
    }

    // Đọc số nguyên không âm: nhập sai thì hỏi lại (tránh crash NumberFormatException).
    protected static int readInt(String prompt) {
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

    // input infor
    public void inputInfor(){
        bookID = readText("Input Book ID: ");
        nameBook = readText("Input book name: ");
        author = readText("Input author: ");
        publicationYear = readText("Input publication year: ");
        quantity = readInt("Input quantity: ");
    }
    
    // Thông tin riêng của từng loại sách (lớp con sẽ override để thể hiện đa hình)
    public String getThongTinChiTiet() {
        return "Book";
    }

    public static void showHeader() {
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.printf("|%-10s|%-20s|%-20s|%-10s|%-8s|%-35s|%n",
                "BookID","Book Name", "Author", "Year", "Quantity", "Details");
    }

    public void showInfor(){
        System.out.printf("|%-10s|%-20s|%-20s|%-10s|%-8d|%-35s|%n",
                this.bookID,this.nameBook, this.author, this.publicationYear, this.quantity,
                this.getThongTinChiTiet());
    }

    // Dùng bởi FileHelper.saveFromFile: phần chung của dòng ghi file, mỗi lớp con
    // sẽ thêm tiền tố loại sách + trường riêng khi override toString().
    @Override
    public String toString() {
        return bookID + "|" + nameBook + "|" + author + "|" + publicationYear + "|" + quantity;
    }
}
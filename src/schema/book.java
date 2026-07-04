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

    // input infor
    public void inputInfor(){
        System.out.print("Input Book ID: ");
        bookID = sc.nextLine();

        System.out.print("Input book name: ");
        nameBook  = sc.nextLine();

        System.out.print("Input author: ");
        author = sc.nextLine();

        System.out.print("Input publication year: ");
        publicationYear = sc.nextLine();

        System.out.print("Input quantity: ");
        quantity = Integer.parseInt(sc.nextLine());
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

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s) | %s",
                bookID, nameBook, author, publicationYear, getThongTinChiTiet());
    }
}
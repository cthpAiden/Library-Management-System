package model;

public class Book {

    // property
    private String bookID;          // id sách
    private String nameBook;        // tên sách
    private String author;          // tác giả
    private String publicationYear; // năm xuất bản
    private int quantity;           // số lượng

    // constructor
    public Book(String bookID, String nameBook,
            String author, String publicationYear, int quantity) {
        this.bookID = bookID;
        this.nameBook = nameBook;
        this.author = author;
        this.publicationYear = publicationYear;
        this.quantity = quantity;
    }

    // getter
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

    // Thông tin riêng của từng loại sách (lớp con sẽ override để thể hiện đa hình)
    public String getThongTinChiTiet() {
        return "Book";
    }

    public static void showHeader() {
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.printf("|%-10s|%-20s|%-20s|%-10s|%-8s|%-35s|%n",
                "BookID", "Book Name", "Author", "Year", "Quantity", "Details");
    }

    public void showInfor() {
        System.out.printf("|%-10s|%-20s|%-20s|%-10s|%-8d|%-35s|%n",
                this.bookID, this.nameBook, this.author, this.publicationYear, this.quantity,
                this.getThongTinChiTiet());
    }

    // dòng ghi file: bookID|nameBook|author|publicationYear|quantity (lớp con thêm tiền tố loại + trường riêng)
    @Override
    public String toString() {
        return bookID + "|" + nameBook + "|" + author + "|" + publicationYear + "|" + quantity;
    }
}

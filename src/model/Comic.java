package model;

// Lớp con Comic kế thừa từ lớp cha Book
public class Comic extends Book {

    // Thuộc tính riêng của Comic
    private int issueNumber; // số tập / số phát hành

    public Comic(String bookID, String nameBook,
            String author, String publicationYear, int quantity, int issueNumber) {
        super(bookID, nameBook, author, publicationYear, quantity);
        this.issueNumber = issueNumber;
    }

    // getter / setter
    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    // override để hiển thị thông tin riêng (đa hình)
    @Override
    public String getThongTinChiTiet() {
        return "Comic | Issue No.: " + issueNumber;
    }

    // dòng ghi file: COMIC|bookID|nameBook|author|publicationYear|quantity|issueNumber
    @Override
    public String toString() {
        return "COMIC|" + super.toString() + "|" + issueNumber;
    }
}

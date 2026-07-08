package model;

// Lớp con Textbook kế thừa từ lớp cha Book
public class Textbook extends Book {

    // Thuộc tính riêng của Textbook
    private String subject; // môn học (Math, Physics, ...)

    public Textbook(String bookID, String nameBook,
            String author, String publicationYear, int quantity, String subject) {
        super(bookID, nameBook, author, publicationYear, quantity);
        this.subject = subject;
    }

    // getter / setter
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // override để hiển thị thông tin riêng (đa hình)
    @Override
    public String getThongTinChiTiet() {
        return "Textbook | Subject: " + subject;
    }

    // dòng ghi file: TEXTBOOK|bookID|nameBook|author|publicationYear|quantity|subject
    @Override
    public String toString() {
        return "TEXTBOOK|" + super.toString() + "|" + subject;
    }
}

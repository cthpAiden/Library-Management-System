package model;

// Lớp con Novel kế thừa từ lớp cha Book
public class Novel extends Book {

    // Thuộc tính riêng của Novel
    private String genre; // thể loại tiểu thuyết (Fantasy, Romance, ...)

    public Novel(String bookID, String nameBook,
            String author, String publicationYear, int quantity, String genre) {
        super(bookID, nameBook, author, publicationYear, quantity);
        this.genre = genre;
    }

    // getter / setter
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // override để hiển thị thông tin riêng (đa hình)
    @Override
    public String getThongTinChiTiet() {
        return "Novel | Genre: " + genre;
    }

    // dòng ghi file: NOVEL|bookID|nameBook|author|publicationYear|quantity|genre
    @Override
    public String toString() {
        return "NOVEL|" + super.toString() + "|" + genre;
    }
}

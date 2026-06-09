package schema;

import java.util.Scanner;

// Lớp con Novel kế thừa từ lớp cha book
public class Novel extends book {

    // Thuộc tính riêng của Novel
    private String genre; // thể loại tiểu thuyết (Fantasy, Romance, ...)

    // constructor
    public Novel() {
        super();
    }

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

    // override input: nhập thông tin chung rồi nhập thuộc tính riêng
    @Override
    public void inputInfor() {
        super.inputInfor();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter genre (e.g. Fantasy, Romance): ");
        this.genre = sc.nextLine();
    }

    // override để hiển thị thông tin riêng (đa hình)
    @Override
    public String getThongTinChiTiet() {
        return "Novel | Genre: " + genre;
    }
}

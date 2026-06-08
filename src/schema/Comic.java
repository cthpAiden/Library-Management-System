package schema;

import java.util.Scanner;

// Lớp con Comic kế thừa từ lớp cha book
public class Comic extends book {

    // Thuộc tính riêng của Comic
    private String illustrator; // họa sĩ minh họa

    // constructor
    public Comic() {
        super();
    }

    public Comic(String bookID, String nameBook, String title,
            String author, String publicationYear, int quantity, String illustrator) {
        super(bookID, nameBook, title, author, publicationYear, quantity);
        this.illustrator = illustrator;
    }

    // getter / setter
    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    // override input: nhập thông tin chung rồi nhập thuộc tính riêng
    @Override
    public void inputInfor() {
        super.inputInfor();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter illustrator: ");
        this.illustrator = sc.nextLine();
    }

    // override để hiển thị thông tin riêng (đa hình)
    @Override
    public String getThongTinChiTiet() {
        return "Comic | Illustrator: " + illustrator;
    }
}

package schema;

import java.util.Scanner;

// Lớp con Others kế thừa từ lớp cha book
public class Others extends book {

    // Thuộc tính riêng của Others
    private String note; // ghi chú loại sách khác

    // constructor
    public Others() {
        super();
    }

    public Others(String bookID, String nameBook,
            String author, String publicationYear, int quantity, String note) {
        super(bookID, nameBook, author, publicationYear, quantity);
        this.note = note;
    }

    // getter / setter
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // override input: nhập thông tin chung rồi nhập thuộc tính riêng
    @Override
    public void inputInfor() {
        super.inputInfor();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter note: ");
        this.note = sc.nextLine();
    }

    // override để hiển thị thông tin riêng (đa hình)
    @Override
    public String getThongTinChiTiet() {
        return "Others | Note: " + note;
    }
}

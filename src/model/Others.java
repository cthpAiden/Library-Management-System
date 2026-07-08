package model;

// Lớp con Others kế thừa từ lớp cha Book
public class Others extends Book {

    // Thuộc tính riêng của Others
    private String note; // ghi chú loại sách khác

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

    // override để hiển thị thông tin riêng (đa hình)
    @Override
    public String getThongTinChiTiet() {
        return "Others | Note: " + note;
    }

    // dòng ghi file: OTHERS|bookID|nameBook|author|publicationYear|quantity|note
    @Override
    public String toString() {
        return "OTHERS|" + super.toString() + "|" + note;
    }
}

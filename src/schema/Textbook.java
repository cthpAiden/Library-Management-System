package schema;

// Lớp con Textbook kế thừa từ lớp cha book
public class Textbook extends book {

    // Thuộc tính riêng của Textbook
    private String subject; // môn học (Math, Physics, ...)

    // constructor
    public Textbook() {
        super();
    }

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

    // override input: nhập thông tin chung rồi nhập thuộc tính riêng
    @Override
    public void inputInfor() {
        super.inputInfor();
        System.out.print("Enter subject (e.g. Math, Physics): ");
        this.subject = SC.nextLine();
    }

    // override để hiển thị thông tin riêng (đa hình)
    @Override
    public String getThongTinChiTiet() {
        return "Textbook | Subject: " + subject;
    }
}

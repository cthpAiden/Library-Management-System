package schema;

// Lớp con Comic kế thừa từ lớp cha book
public class Comic extends book {

    // Thuộc tính riêng của Comic
    private int issueNumber; // số tập / số phát hành

    // constructor
    public Comic() {
        super();
    }

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

    // override input: nhập thông tin chung rồi nhập thuộc tính riêng
    @Override
    public void inputInfor() {
        super.inputInfor();
        System.out.print("Enter issue number: ");
        this.issueNumber = Integer.parseInt(SC.nextLine());
    }

    // override để hiển thị thông tin riêng (đa hình)
    @Override
    public String getThongTinChiTiet() {
        return "Comic | Issue No.: " + issueNumber;
    }
}

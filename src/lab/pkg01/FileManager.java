package lab.pkg01;

import java.io.File;
import java.util.ArrayList;
import schema.*;
import utils.*;

public class FileManager {
    private static final String DATA_DIR = "data";
    private static final String BOOKS_FILE = "data/books.txt";
    private static final String MEMBERS_FILE = "data/members.txt";
    private static final String BORROWINGS_FILE = "data/borrowings.txt";

    private final BookFileHelper bookFileHelper;
    private final MemberFileHelper memberFileHelper;
    private final BorrowingFileHelper borrowingFileHelper;

    public FileManager(ArrayList<book> bookList, ArrayList<Member> memberList, ArrayList<Borrowing> borrowList) {
        this.bookFileHelper = new BookFileHelper(bookList);
        this.memberFileHelper = new MemberFileHelper(memberList);
        this.borrowingFileHelper = new BorrowingFileHelper(borrowList);
    }

    public boolean hasSavedData() {
        // Chỉ coi là có dữ liệu khi cả 3 file đều tồn tại (tránh load nửa vời)
        return new File(BOOKS_FILE).exists()
                && new File(MEMBERS_FILE).exists()
                && new File(BORROWINGS_FILE).exists();
    }

    public void saveData() {
        new File(DATA_DIR).mkdirs();
        boolean ok = bookFileHelper.saveFromFile(BOOKS_FILE)
                & memberFileHelper.saveFromFile(MEMBERS_FILE)
                & borrowingFileHelper.saveFromFile(BORROWINGS_FILE);
        System.out.println(ok ? "Data saved successfully!" : "Could not save data.");
    }

    public void loadData(ArrayList<book> bookList, ArrayList<Member> memberList, ArrayList<Borrowing> borrowList) {
        bookList.clear(); memberList.clear(); borrowList.clear();
        boolean ok = bookFileHelper.loadFromFile(BOOKS_FILE)
                & memberFileHelper.loadFromFile(MEMBERS_FILE)
                & borrowingFileHelper.loadFromFile(BORROWINGS_FILE);
        System.out.println(ok ? "Data loaded successfully!" : "Could not load data.");
    }
}

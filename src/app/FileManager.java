package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.function.Consumer;
import model.*;

// Đọc/ghi cả 3 file .txt. Tự chứa hết: đọc file, ghi file, parse "|" đều ở đây,
// không còn tầng helper/generic. Muốn hiểu 1 dòng file thành object -> đọc ngay bên dưới.
public class FileManager {
    private static final String DATA_DIR = "data";
    private static final String BOOKS_FILE = "data/books.txt";
    private static final String MEMBERS_FILE = "data/members.txt";
    private static final String BORROWINGS_FILE = "data/borrowings.txt";
    // STRICT để từ chối ngày không tồn tại (vd 31/02/2026) thay vì tự đổi.
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private final ArrayList<Book> bookList;
    private final ArrayList<Member> memberList;
    private final ArrayList<Borrowing> borrowList;

    public FileManager(ArrayList<Book> bookList, ArrayList<Member> memberList, ArrayList<Borrowing> borrowList) {
        this.bookList = bookList;
        this.memberList = memberList;
        this.borrowList = borrowList;
    }

    public boolean hasSavedData() {
        // Chỉ coi là có dữ liệu khi cả 3 file đều tồn tại (tránh load nửa vời).
        return new File(BOOKS_FILE).exists()
                && new File(MEMBERS_FILE).exists()
                && new File(BORROWINGS_FILE).exists();
    }

    // ---------------- SAVE ----------------
    public void saveData() {
        new File(DATA_DIR).mkdirs();
        boolean ok = writeLines(BOOKS_FILE, bookList)
                & writeLines(MEMBERS_FILE, memberList)
                & writeLines(BORROWINGS_FILE, borrowList);
        System.out.println(ok ? "Data saved successfully!" : "Could not save data.");
    }

    // Ghi mỗi object 1 dòng bằng toString(). Dựng toàn bộ nội dung trước rồi mới mở
    // file để ghi 1 lần -> nếu lỗi giữa chừng thì file cũ chưa bị cắt trắng.
    private boolean writeLines(String path, ArrayList<?> list) {
        StringBuilder sb = new StringBuilder();
        for (Object o : list) {
            sb.append(o.toString()).append("\n");
        }
        try (OutputStreamWriter writer =
                new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)) {
            writer.write(sb.toString());
            return true;
        } catch (Exception e) {
            System.out.println("Save to file error (" + path + "): " + e);
            return false;
        }
    }

    // ---------------- LOAD ----------------
    public void loadData() {
        // Chưa đủ file lưu -> giữ nguyên dữ liệu hiện tại, không xoá trắng list.
        if (!hasSavedData()) {
            System.out.println("No saved data found. Keeping current data.");
            return;
        }
        bookList.clear(); memberList.clear(); borrowList.clear();
        boolean ok = readLines(BOOKS_FILE, this::addBook)
                & readLines(MEMBERS_FILE, this::addMember)
                & readLines(BORROWINGS_FILE, this::addBorrow);
        System.out.println(ok ? "Data loaded successfully!" : "Could not load data.");
    }

    // Đọc từng dòng UTF-8, bỏ dòng trống. 1 dòng lỗi chỉ bị bỏ qua, không hỏng cả file.
    private boolean readLines(String path, Consumer<String> parse) {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    parse.accept(line);
                } catch (Exception ex) {
                    System.out.println("Skipping bad line: " + line);
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Load from file error (" + path + "): " + e);
            return false;
        }
    }

    // ---------------- PARSE từng loại ----------------
    // format: TYPE|bookID|name|author|year|quantity|<extra>
    private void addBook(String line) {
        String[] p = line.split("\\|", -1); // -1: giữ field cuối rỗng
        String id = p[1];
        for (Book b : bookList) {
            if (b.getBookID().equalsIgnoreCase(id)) { dup(id); return; }
        }
        int qty = Integer.parseInt(p[5]);
        Book b;
        switch (p[0]) {
            case "NOVEL":    b = new Novel(id, p[2], p[3], p[4], qty, p[6]); break;
            case "COMIC":    b = new Comic(id, p[2], p[3], p[4], qty, Integer.parseInt(p[6])); break;
            case "TEXTBOOK": b = new Textbook(id, p[2], p[3], p[4], qty, p[6]); break;
            case "OTHERS":   b = new Others(id, p[2], p[3], p[4], qty, p[6]); break;
            default:
                System.out.println("Unknown book type skipped: " + p[0]);
                return;
        }
        bookList.add(b);
    }

    // format: memberID|name|email|phone
    private void addMember(String line) {
        String[] p = line.split("\\|", -1);
        for (Member m : memberList) {
            if (m.getMemberID().equalsIgnoreCase(p[0])) { dup(p[0]); return; }
        }
        memberList.add(new Member(p[0], p[1], p[2], p[3]));
    }

    // format: memberID|bookID|borrowDate|returnDate(hoặc "null")|returned
    private void addBorrow(String line) {
        String[] p = line.split("\\|", -1);
        LocalDate borrowDate = LocalDate.parse(p[2], FMT);
        LocalDate returnDate = p[3].equals("null") ? null : LocalDate.parse(p[3], FMT);
        borrowList.add(new Borrowing(p[0], p[1], borrowDate, returnDate, Boolean.parseBoolean(p[4])));
    }

    private void dup(String key) {
        System.out.println("Skipping duplicate record: " + key);
    }
}

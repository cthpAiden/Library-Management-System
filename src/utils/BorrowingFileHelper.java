package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import schema.Borrowing;

public class BorrowingFileHelper extends FileHelper<Borrowing> {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BorrowingFileHelper(ArrayList<Borrowing> dataList) {
        super(dataList);
    }

    // line format: memberID|bookID|borrowDate|returnDate(or "null")|returned
    @Override
    public Borrowing handleLine(String line) {
        String[] p = line.split("\\|", -1); // -1: giữ lại field cuối rỗng
        LocalDate borrowDate = LocalDate.parse(p[2], FMT);
        LocalDate returnDate = p[3].equals("null") ? null : LocalDate.parse(p[3], FMT);
        boolean returned = Boolean.parseBoolean(p[4]);
        return new Borrowing(p[0], p[1], borrowDate, returnDate, returned);
    }
}

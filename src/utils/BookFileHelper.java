package utils;

import java.util.ArrayList;
import schema.Comic;
import schema.Novel;
import schema.Others;
import schema.Textbook;
import schema.book;

public class BookFileHelper extends FileHelper<book> {

    public BookFileHelper(ArrayList<book> dataList) {
        super(dataList);
    }

    // line format: TYPE|bookID|nameBook|author|publicationYear|quantity|<extra>
    @Override
    public book handleLine(String line) {
        String[] p = line.split("\\|");
        String type = p[0];
        String bookID = p[1];
        String name = p[2];
        String author = p[3];
        String year = p[4];
        int quantity = Integer.parseInt(p[5]);

        switch (type) {
            case "NOVEL":
                return new Novel(bookID, name, author, year, quantity, p[6]);
            case "COMIC":
                return new Comic(bookID, name, author, year, quantity, Integer.parseInt(p[6]));
            case "TEXTBOOK":
                return new Textbook(bookID, name, author, year, quantity, p[6]);
            case "OTHERS":
                return new Others(bookID, name, author, year, quantity, p[6]);
            default:
                System.out.println("Unknown book type skipped: " + type);
                return null;
        }
    }
}

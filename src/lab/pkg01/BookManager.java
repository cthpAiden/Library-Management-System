package lab.pkg01;

import java.util.ArrayList;
import java.util.Scanner;
import schema.*;

public class BookManager {
    private final ArrayList<book> bookList;
    private final ArrayList<Borrowing> borrowList;
    private final Scanner sc = book.sc;

    public BookManager(ArrayList<book> bookList, ArrayList<Borrowing> borrowList) {
        this.bookList = bookList;
        this.borrowList = borrowList;
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("================================");
            System.out.println("         MANAGE BOOKS           ");
            System.out.println("================================");
            System.out.println("1. Add book");
            System.out.println("2. View all books");
            System.out.println("3. Search books by title, author, or genre");
            System.out.println("4. Remove book by ID");
            System.out.println("5. Return");
            System.out.print("Select your choice: ");
            choice = InputHelper.readInt(sc);

            switch (choice) {
                case 1: addBook(); break;
                case 2: showAllBooks(); break;
                case 3:
                    System.out.print("Input title / author / genre keyword: ");
                    searchBooks(InputHelper.readLine(sc));
                    break;
                case 4:
                    System.out.print("Input Book ID: ");
                    deleteBook(InputHelper.readLine(sc).trim());
                    break;
                case 5: System.out.println("Returning to main menu..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    public void addBook() {
        System.out.println("--------- BOOK TYPE ---------");
        System.out.println("1. Novel");
        System.out.println("2. Textbook");
        System.out.println("3. Comic");
        System.out.println("4. Others");
        System.out.print("Choose book type: ");
        int type = InputHelper.readInt(sc);

        book bk;
        switch (type) {
            case 1: bk = new Novel(); break;
            case 2: bk = new Textbook(); break;
            case 3: bk = new Comic(); break;
            case 4: bk = new Others(); break;
            default:
                System.out.println("Invalid book type!");
                return;
        }

        bk.inputInfor();
        if (findBookByID(bk.getBookID()) != null) {
            System.out.println("Book ID already existed!");
            return;
        }
        bookList.add(bk);
        System.out.println("Successfully added!");
    }

    public void showAllBooks() {
        if (bookList.isEmpty()) {
            System.out.println("Empty book list!");
            return;
        }
        book.showHeader();
        for (book bk : bookList) bk.showInfor();
    }

    // Đúng yêu cầu đề: search theo title/name, author hoặc genre/details. Không search theo ID.
    public void searchBooks(String keyword) {
        String key = keyword.toLowerCase().trim();
        boolean found = false;
        for (book bk : bookList) {
            boolean matchTitle = bk.getNameBook().toLowerCase().contains(key);
            boolean matchAuthor = bk.getAuthor().toLowerCase().contains(key);
            boolean matchGenre = bk.getThongTinChiTiet().toLowerCase().contains(key);

            if (matchTitle || matchAuthor || matchGenre) {
                if (!found) book.showHeader();
                bk.showInfor();
                found = true;
            }
        }
        if (!found) System.out.println("No book found by title, author, or genre: " + keyword);
    }

    public boolean isEmpty() {
        return bookList.isEmpty();
    }

    public book findBookByID(String bookID) {
        for (book bk : bookList) {
            if (bk.getBookID().equalsIgnoreCase(bookID)) return bk;
        }
        return null;
    }

    public void deleteBook(String bookID) {
        for (Borrowing br : borrowList) {
            if (br.getBookID().equalsIgnoreCase(bookID) && !br.isReturned()) {
                System.out.println("Book is being borrowed, unable to remove!");
                return;
            }
        }
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getBookID().equalsIgnoreCase(bookID)) {
                bookList.remove(i);
                // Xóa luôn lịch sử mượn của sách này để không còn record mồ côi
                for (int j = borrowList.size() - 1; j >= 0; j--) {
                    if (borrowList.get(j).getBookID().equalsIgnoreCase(bookID)) {
                        borrowList.remove(j);
                    }
                }
                System.out.println("Successfully removed!");
                return;
            }
        }
        System.out.println("Book not found!");
    }
}

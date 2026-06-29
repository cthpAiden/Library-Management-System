<!-- ═══════════════════════════════════════════════════════════════════════════
     🤖 CLAUDE — AUTO-TRACK INSTRUCTIONS (read this every session, act without being asked)

     This file is the user's learning tracker. Your job is to KEEP THE CHECKBOXES IN SYNC
     with the user's ACTUAL code. Do this proactively at the start of any session that
     touches this project, and again whenever the user says they finished some steps.

     HOW TO AUTO-TRACK:
     1. Read the real source files: src/schema/*.java and src/lab/pkg01/LAB01.java
        (read the WHOLE files, not snippets).
     2. Go step by step through this roadmap. For each step, check whether the code that
        step describes actually exists AND is correct (right method, right logic, compiles).
     3. Update the checkboxes by editing THIS file:
          - [ ]  → - [x]   ONLY when the step is genuinely done and correct in the code.
          - Tick a Module's "✅ done when" line and its entry in the Progress Tracker
            only when ALL of that module's steps are [x].
        Use the Edit tool to flip each box; do not rewrite the whole file.
     4. If code exists but is WRONG or incomplete, leave the box [ ] and tell the user
        exactly which step is off and why (point to the file:line).
     5. Never tick a step the user hasn't actually written. Never untick a correct step.
        If you're unsure a step is done, ask or leave it unticked — don't guess.
     6. After syncing, give the user a 1-line status: which module they're on and the
        next unchecked step to do.

     The user wants this handled FOR them — don't wait to be told to re-scan; do it when
     they mention progress or when you open this project.
═══════════════════════════════════════════════════════════════════════════ -->

# 📚 ROADMAP — Library Management System (Build From Scratch, Micro-Step Edition)

> **🤖 Auto-tracking is ON.** Claude keeps the `[ ]/[x]` boxes below in sync with your actual
> code: it reads your source files, cross-references each step, and ticks the ones you've
> genuinely completed. Just tell Claude "I finished some steps" (or start a session on this
> project) and it will re-scan and update this file for you, then point you to the next step.

> **Goal:** Re-build the whole project from zero in the **smallest possible steps**, so you understand every line and can defend it in front of the review panel (hội đồng).
>
> **How to read this file:** Work top to bottom. The project is split into **9 Modules**; each Module is split into **Steps**; complex Steps are split again into lettered **micro-actions (a, b, c…)**. Every Step has the same 4 parts:
> - **▸ Do** — the exact thing to write (micro-actions listed a, b, c…).
> - **▸ Why** — the concept it teaches / why it exists.
> - **▸ Trap** — the mistake people make here (skip if none).
> - **▸ Check** — how you know it's done before moving on.
>
> Tick `[ ]` → `[x]` as you finish. **Never skip ahead** — each Step builds on the previous one.
>
> **Naming rule:** Keep the existing Vietnamese method names exactly (`timSachTheoID`, `muonSach`, `traSach`, `nhapSoNguyen`, `nhapNgay`, `tinhNgayQuaHan`, `tinhTienPhat`, `xemSachQuaHan`, `xemDanhSachDangMuon`, `lichSuMuonTheoMember`, `seedBorrow`, `getThongTinChiTiet`). Everything else is English.

---

## 🗺️ Big Picture — what you are building

A console app to manage a small library: **books**, **members**, and **borrow/return records**, plus **reports**.

```
PHASE 1 — MODEL LAYER  (package schema)        "the data + its behavior"
  Module 1: book.java            parent class
  Module 2: Novel / Textbook / Comic / Others    4 subclasses
  Module 3: Member.java          standalone entity
  Module 4: Borrowing.java       owns overdue + fine logic

PHASE 2 — CONTROLLER LAYER  (package lab.pkg01)   "menus + workflows"
  Module 5: LAB01 skeleton       main, menu loop, seed data, input helpers
  Module 6: Book management      add / list / find / remove
  Module 7: Member management    add / list / find / remove
  Module 8: Borrow / Return + Reports
```

### The 3 lists = the entire program state (all inside `LAB01`)

| Field | Type | Meaning |
|-------|------|---------|
| `bookList` | `List<book>` | the inventory (holds any subclass via upcasting) |
| `memberList` | `List<Member>` | all members |
| `borrowList` | `List<Borrowing>` | one record per borrow event |

### The golden rule (remember it the whole way)

> **Borrowing a book → `quantity` goes DOWN by 1. Returning → `quantity` goes UP by 1.** Every workflow must keep this in sync.

### Dependency order (why bottom-up)

```
book ──▶ Novel/Textbook/Comic/Others ──┐
                                        ├──▶ LAB01 (uses all of them)
Member ─────────────────────────────────┤
Borrowing ──────────────────────────────┘
```
Build the things with **no dependencies first**, so every piece is testable the moment you write it.

### One reusable pattern you'll meet 3 times: the `printf` table

Every entity prints as a table with a **separator line** + a **format string**. You'll write it for `book`, `Member`, and `Borrowing`. The recipe is always:
1. `static showHeader()` → print a line of dashes, then `printf` the column **titles**.
2. `showInfor()` → `printf` this object's **values** using the **same widths**.
- `%-10s` = left-aligned text, width 10. `%-8d` = left-aligned int, width 8. `%n` = newline.

---

# PHASE 1 — MODEL LAYER

## Module 1 — The parent class `book`

> Outcome: a fully working base class you can create, fill, and print as a table row.

- [ ] **Step 1.1 — Create the file & class shell**
  - ▸ Do: Make `src/schema/book.java`. Line 1: `package schema;`. Then `public class book { }`.
  - ▸ Why: Every model class lives in package `schema`; the `package` line must be first.
  - ▸ Check: An empty class compiles.

- [ ] **Step 1.2 — Add the 5 private fields**
  - ▸ Do:
    - a) `private String bookID;`
    - b) `private String nameBook;`
    - c) `private String author;`
    - d) `private String publicationYear;` (String, not int — years can have leading text)
    - e) `private int quantity;`
  - ▸ Why: **Encapsulation** — data hidden behind the class.
  - ▸ Trap: Not `public`. The panel will ask why (controlled access / future validation).
  - ▸ Check: Compiles.

- [ ] **Step 1.3 — No-arg constructor**
  - ▸ Do: `public book() { }`
  - ▸ Why: Lets `addBook()` later create a blank book then fill it with `inputInfor()`.
  - ▸ Check: `new book()` works.

- [ ] **Step 1.4 — Full-arg constructor**
  - ▸ Do: `public book(String bookID, String nameBook, String author, String publicationYear, int quantity)`, then assign all 5 with `this.field = field`.
  - ▸ Why: One-line creation for `seedData()`. Two constructors = **overloading**.
  - ▸ Trap: Use `this.` — `bookID = bookID` assigns the parameter to itself.
  - ▸ Check: `new book("B01","x","y","2000",5)` compiles.

- [ ] **Step 1.5 — Getters (write all 5)**
  - ▸ Do: `getBookID`, `getNameBook`, `getAuthor`, `getPublicationYear`, `getQuantity`. Each: `return field;`.
  - ▸ Why: Only legal way to read private fields from outside.
  - ▸ Check: Each returns its field.

- [ ] **Step 1.6 — Setters (write all 5)**
  - ▸ Do: `setBookID` … `setQuantity`. Each: `this.field = field;`.
  - ▸ Why: `setQuantity` is used on every borrow/return.
  - ▸ Check: Each assigns its field.

- [ ] **Step 1.7 — `inputInfor()`: set up the Scanner**
  - ▸ Do: `public void inputInfor() {` then `Scanner sc = new Scanner(System.in);`
  - ▸ Why: Reads one book's data from the keyboard.
  - ▸ Check: Compiles (import `java.util.Scanner`).

- [ ] **Step 1.8 — `inputInfor()`: read the 4 String fields**
  - ▸ Do: For each of bookID, nameBook, author, publicationYear: print a prompt, then `field = sc.nextLine();`.
  - ▸ Check: Running prompts you 4 times.

- [ ] **Step 1.9 — `inputInfor()`: read `quantity` (the int) safely**
  - ▸ Do: Print "Input quantity: ", `quantity = sc.nextInt();`, then **`sc.nextLine();`** to clear the leftover newline.
  - ▸ Why & Trap: **The newline trap.** `nextInt()` leaves a `\n` in the buffer; without `nextLine()` the next string read returns "".
  - ▸ Check: After typing a number you can still type the next field normally.

- [ ] **Step 1.10 — `getThongTinChiTiet()` returning `"Book"`**
  - ▸ Do: `public String getThongTinChiTiet() { return "Book"; }`
  - ▸ Why: **The hinge of polymorphism.** Subclasses override this; it looks pointless now but powers the Details column later.
  - ▸ Check: Returns `"Book"`.

- [ ] **Step 1.11 — `static showHeader()`: the separator line**
  - ▸ Do: `public static void showHeader() {` then `System.out.println("----...----");` (a long dash line).
  - ▸ Why: **Static** — the header is the same for every book, needs no object.
  - ▸ Check: `book.showHeader()` (called on the class!) prints the dashes.

- [ ] **Step 1.12 — `static showHeader()`: the title row**
  - ▸ Do: `printf` titles `BookID, Book Name, Author, Year, Quantity, Details` with widths like `%-10s|%-20s|%-20s|%-10s|%-8s|%-35s|%n`. **Write down these widths** — reuse them next step.
  - ▸ Check: Titles are evenly spaced.

- [ ] **Step 1.13 — `showInfor()`: print this object's row**
  - ▸ Do: `printf` with the **same widths** the 5 fields + `getThongTinChiTiet()` last. Note `quantity` uses `%-8d` (int), the rest `%-...s`.
  - ▸ Why: Calling `getThongTinChiTiet()` here is what makes the table show per-type details in Module 2.
  - ▸ Trap: Widths must match Step 1.12 or columns won't line up.
  - ▸ Check: A row sits perfectly under the header.

- [ ] **Step 1.14 — Override `toString()`**
  - ▸ Do: `@Override public String toString()` returning a compact `String.format(...)` one-liner.
  - ▸ Why: `System.out.println(bk)` prints something readable, not `book@1a2b3c`.
  - ▸ Check: Printing a book shows your text.

- [ ] **✅ Module 1 done when:** create a `book`, call `book.showHeader()` then `bk.showInfor()` → clean aligned table; and you can explain *why fields are private*.

---

## Module 2 — The 4 subclasses (Inheritance + Polymorphism)

> Outcome: 4 book types, each adding one field and overriding 2 methods. **Most-tested module by the panel.**
> Build `Novel` fully (Steps 2.1–2.7), then repeat the identical pattern for the other 3.

**Reference table:**

| Class | Unique field | Type | `getThongTinChiTiet()` returns |
|-------|--------------|------|--------------------------------|
| `Novel` | `genre` | `String` | `"Novel \| Genre: " + genre` |
| `Textbook` | `subject` | `String` | `"Textbook \| Subject: " + subject` |
| `Comic` | `issueNumber` | `int` | `"Comic \| Issue No.: " + issueNumber` |
| `Others` | `note` | `String` | `"Others \| Note: " + note` |

### Novel — the template (do every step)

- [ ] **Step 2.1 — File + `extends book`**
  - ▸ Do: `src/schema/Novel.java`, `package schema;`, `public class Novel extends book { }`.
  - ▸ Why: **Inheritance** — Novel gets all of `book`'s fields/methods for free.
  - ▸ Check: `new Novel()` is also a `book`.

- [ ] **Step 2.2 — The unique field**
  - ▸ Do: `private String genre;`
  - ▸ Check: Compiles.

- [ ] **Step 2.3 — No-arg constructor with `super()`**
  - ▸ Do: `public Novel() { super(); }`
  - ▸ Why: `super()` runs the parent's no-arg constructor first.
  - ▸ Check: Compiles.

- [ ] **Step 2.4 — Full constructor with `super(...)`**
  - ▸ Do:
    - a) Signature takes all 6 params (5 base + `genre`).
    - b) First statement: `super(bookID, nameBook, author, publicationYear, quantity);`
    - c) Then: `this.genre = genre;`
  - ▸ Why: Parent fields are private → you **must** set them through `super(...)`.
  - ▸ Trap: `super(...)` must be the **first** line, or it won't compile.
  - ▸ Check: `new Novel("B01","Kieu","Du","1820",5,"Romance")` compiles.

- [ ] **Step 2.5 — Getter/setter for `genre`.** ▸ Check: both work.

- [ ] **Step 2.6 — Override `inputInfor()`**
  - ▸ Do:
    - a) `@Override public void inputInfor() {`
    - b) `super.inputInfor();` (reuse the parent's 5-field input)
    - c) `Scanner sc = new Scanner(System.in);`
    - d) prompt + `this.genre = sc.nextLine();`
  - ▸ Why: Reuse + extend — DRY and polymorphism together.
  - ▸ Trap: Keep `@Override`; it catches a mistyped method name.
  - ▸ Check: Prompts the 5 base fields then genre.

- [ ] **Step 2.7 — Override `getThongTinChiTiet()`**
  - ▸ Do: `@Override public String getThongTinChiTiet() { return "Novel | Genre: " + genre; }`
  - ▸ Why: Now the **inherited** `showInfor()` prints Novel details with **zero edits to `book`** — runtime polymorphism.
  - ▸ Check: `book bk = new Novel(...); bk.showInfor();` → Details shows the genre.

### Repeat the template

- [ ] **Step 2.8 — `Textbook`** — field `subject` (String). Do Steps 2.1–2.7 with `subject`.
- [ ] **Step 2.9 — `Comic`** — field `issueNumber` (**int**). Do Steps 2.1–2.7, BUT:
  - ▸ Trap (2.6 equivalent): read it as `this.issueNumber = Integer.parseInt(sc.nextLine());` to dodge the newline trap.
- [ ] **Step 2.10 — `Others`** — field `note` (String). Do Steps 2.1–2.7 with `note`.

- [ ] **✅ Module 2 done when:**
  - `book bk = new Comic(...); bk.showInfor();` shows the issue number;
  - you can explain why `List<book>` holds a Novel and a Comic together (upcasting);
  - you can tell **overriding** (2.6/2.7) from **overloading** (the two constructors).

---

## Module 3 — The `Member` class

> Outcome: a simple standalone entity. No inheritance — pure encapsulation. Fast module.

- [ ] **Step 3.1 — File + class shell** (`src/schema/Member.java`, `package schema;`).
- [ ] **Step 3.2 — 4 private fields:** a) `memberID` b) `nameMember` c) `email` d) `phoneNumber` (all String).
- [ ] **Step 3.3 — No-arg constructor.**
- [ ] **Step 3.4 — Full constructor** (4 params, `this.x = x`).
- [ ] **Step 3.5 — Getters** (all 4).
- [ ] **Step 3.6 — Setters** (all 4).
- [ ] **Step 3.7 — `inputInfor()`**
  - ▸ Do: a) new Scanner; b) prompt+read `memberID`; c) `nameMember`; d) `phoneNumber`; e) `email`. All `nextLine()` (no ints → no newline trap).
- [ ] **Step 3.8 — `static showHeader()`** — separator line + titles: MemberID, Name, Phone, Email.
- [ ] **Step 3.9 — `showInfor()`** — one aligned row, same widths.
- [ ] **✅ Module 3 done when:** a one-member table prints; and you can answer *"why no polymorphism for Member but yes for book?"* (one member kind vs. four book kinds).

---

## Module 4 — The `Borrowing` class (Business logic + Java Time)

> Outcome: the "brain" that knows due dates, overdue days, and fines. The Controller only *calls* it.

- [ ] **Step 4.1 — File + imports**
  - ▸ Do: `src/schema/Borrowing.java`; import `java.time.LocalDate`, `java.time.format.DateTimeFormatter`, `java.time.temporal.ChronoUnit`.
  - ▸ Check: Compiles.

- [ ] **Step 4.2 — Fields**
  - ▸ Do: a) `String memberID, bookID;` b) `LocalDate borrowDate, returnDate;` c) `boolean returned;`.
  - ▸ Why: `returnDate == null` will mean "not yet returned".
  - ▸ Check: Compiles.

- [ ] **Step 4.3 — The 3 `static final` constants**
  - ▸ Do: a) `private static final int MAX_BORROW_DAYS = 7;` b) `private static final long FINE_PER_DAY = 5000;` c) `private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");`
  - ▸ Why: Rules shared by the whole class, changeable in one place. Be ready to explain `static final`.
  - ▸ Check: Compiles.

- [ ] **Step 4.4 — Two constructors** (no-arg + full 5-arg). ▸ Check: both compile.

- [ ] **Step 4.5 — Getters/setters**
  - ▸ Do: getters for all 5 + setters for all 5.
  - ▸ Trap: the boolean getter is **`isReturned()`**, not `getReturned()`.
  - ▸ Check: all compile.

- [ ] **Step 4.6 — `getDueDate()`**
  - ▸ Do: `return borrowDate.plusDays(MAX_BORROW_DAYS);`
  - ▸ Why: Due = borrow + 7. Used by the fine math and `showInfor()`.
  - ▸ Check: borrow 01/01 → due 08/01.

- [ ] **Step 4.7 — `tinhNgayQuaHan(LocalDate ngayTra)`: compute the raw gap**
  - ▸ Do: `long overdue = ChronoUnit.DAYS.between(getDueDate(), ngayTra);`
  - ▸ Why: Days between due date and return date.
  - ▸ Trap: returns **negative** when returned early.
  - ▸ Check: due 08/01, return 31/01 → `overdue` = 23.

- [ ] **Step 4.8 — `tinhNgayQuaHan`: clamp negatives to 0**
  - ▸ Do: `return overdue > 0 ? overdue : 0;`
  - ▸ Why: "Not late" must read as 0, never negative.
  - ▸ Check: return 05/01 → 0.

- [ ] **Step 4.9 — `tinhTienPhat(LocalDate ngayTra)`**
  - ▸ Do: `return tinhNgayQuaHan(ngayTra) * FINE_PER_DAY;`
  - ▸ Why: Reuse the overdue method — never duplicate date math.
  - ▸ Check: 23 days → 115000.

- [ ] **Step 4.10 — `static showHeader()`** — separator + titles: BookID, MemberID, Borrow Date, Due Date, Status.

- [ ] **Step 4.11 — `showInfor()`**
  - ▸ Do: a) `String status = returned ? "Returned" : "Borrowing";` b) `printf` BookID, MemberID, `borrowDate.format(FMT)`, `getDueDate().format(FMT)`, status.
  - ▸ Check: an aligned 1-row table prints with a formatted date.

- [ ] **✅ Module 4 done when:** the 4.7/4.8/4.9 test cases pass, and you can explain *why the fine logic lives here, not in `LAB01`* (single responsibility — the record owns its rules).

---

# PHASE 2 — CONTROLLER LAYER (`LAB01`)

## Module 5 — Program skeleton & input helpers

> Outcome: the app launches, shows a working main menu, has seed data + safe input. No real features yet.

- [ ] **Step 5.1 — File + imports + class**
  - ▸ Do: `src/lab/pkg01/LAB01.java`, `package lab.pkg01;`. Import `java.util.{ArrayList,List,Scanner}`, `java.time.{LocalDate}`, `java.time.format.{DateTimeFormatter,DateTimeParseException}`, and `schema.*`.
  - ▸ Check: Compiles.

- [ ] **Step 5.2 — The state fields**
  - ▸ Do: a) `private List<book> bookList = new ArrayList<>();` b) `memberList` c) `borrowList` d) `private Scanner sc = new Scanner(System.in);` e) `private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");`
  - ▸ Why: the whole program's state. **Composition (HAS-A)** vs. inheritance.
  - ▸ Check: Compiles.

- [ ] **Step 5.3 — `main` + a temporary `showMenu()` stub**
  - ▸ Do: a) `public static void main(String[] args) { new LAB01().showMenu(); }` b) a stub `public void showMenu()` that just prints one line.
  - ▸ Check: Program runs, prints the line.

- [ ] **Step 5.4 — `nhapSoNguyen()`: the loop + try**
  - ▸ Do: `while (true) { try { return Integer.parseInt(sc.nextLine()); } ... }`
  - ▸ Why: **Exception handling** — bad input must not crash.
  - ▸ Check: compiles.

- [ ] **Step 5.5 — `nhapSoNguyen()`: the catch**
  - ▸ Do: `catch (NumberFormatException e) { System.out.print("Input valid number: "); }`
  - ▸ Check: feeding "abc" then "3" returns 3.

- [ ] **Step 5.6 — `nhapNgay()`**
  - ▸ Do: a) `String input = sc.nextLine();` b) `try { return LocalDate.parse(input, FMT); }` c) `catch (DateTimeParseException e) { print "Invalid date! Format must be DD/MM/YYYY"; return null; }`
  - ▸ Why: bad dates return `null` so callers can bail.
  - ▸ Check: "31/01/2026" parses; "abc" → error + null.

- [ ] **Step 5.7 — `showMenu()`: the loop skeleton**
  - ▸ Do: replace the stub with `int choice; do { ... } while (choice != 5);`.
  - ▸ Check: compiles.

- [ ] **Step 5.8 — `showMenu()`: print the 5 options + read choice**
  - ▸ Do: inside the loop, print the banner + options (1 Books, 2 Members, 3 Borrow, 4 Reports, 5 Exit), then `choice = nhapSoNguyen();`.
  - ▸ Check: options display, input is read.

- [ ] **Step 5.9 — `showMenu()`: the `switch`**
  - ▸ Do: `switch (choice)` with cases 1–5 printing "TODO" for now (case 5 → "Goodbye"), and a `default` → "Invalid choice".
  - ▸ Why: navigation backbone — features hang off this switch.
  - ▸ Check: you can navigate; 5 exits; bad number → "Invalid".

- [ ] **Step 5.10 — `seedBorrow(memberID, bookID, LocalDate borrowDate)` (3-arg): add the record**
  - ▸ Do: `borrowList.add(new Borrowing(memberID, bookID, borrowDate, null, false));`
  - ▸ Check: compiles (you'll add the quantity drop next).

- [ ] **Step 5.11 — `seedBorrow` (3-arg): drop the stock**
  - ▸ Do: `book bk = timSachTheoID(bookID); if (bk != null) bk.setQuantity(bk.getQuantity() - 1);`
  - ▸ Why: keeps the golden rule even for seeded data.
  - ▸ Note: `timSachTheoID` is written in Module 6; this resolves once that exists.

- [ ] **Step 5.12 — `seedBorrow(memberID, bookID)` (2-arg overload)**
  - ▸ Do: `seedBorrow(memberID, bookID, LocalDate.now());`
  - ▸ Why: **Overloading** — default date "today".

- [ ] **Step 5.13 — `seedData()`: add 4 books**
  - ▸ Do: `bookList.add(new Novel(...)); ... new Textbook(...); new Comic(...); new Others(...);` — one of each type, distinct IDs (B01–B04).
  - ▸ Check: 4 books at startup.

- [ ] **Step 5.14 — `seedData()`: add 3 members**
  - ▸ Do: `memberList.add(new Member("M01", ...)); M02; M03;`.
  - ▸ Check: 3 members.

- [ ] **Step 5.15 — `seedData()`: seed the "borrow-limit" scenario**
  - ▸ Do: `seedBorrow("M01","B01"); seedBorrow("M01","B02"); seedBorrow("M01","B03");` — M01 now holds 3 (the max).
  - ▸ Why: ready-made test for the limit rule.

- [ ] **Step 5.16 — `seedData()`: seed the "overdue" scenario**
  - ▸ Do: `seedBorrow("M02","B02", LocalDate.now().minusDays(30));` — unreturned, 30 days old.
  - ▸ Why: ready-made test for the overdue report.

- [ ] **Step 5.17 — Constructor calls `seedData()`**
  - ▸ Do: `public LAB01() { seedData(); }`
  - ▸ Check: data present at startup. (Full run requires Module 6's `timSachTheoID` — compile both together.)

- [ ] **✅ Module 5 done when:** app launches with seed data, menu navigates and exits, bad input never crashes it.

---

## Module 6 — Book Management (CRUD)

> Outcome: full Books submenu. Where polymorphism does real work.

- [ ] **Step 6.1 — `timSachTheoID(String bookID)`** (write FIRST — many things need it)
  - ▸ Do: loop `bookList`; if `bk.getBookID().equalsIgnoreCase(bookID)` return `bk`; after loop return `null`.
  - ▸ Why: internal lookup; returns the object, prints nothing.
  - ▸ Check: returns the right book or null.

- [ ] **Step 6.2 — `showListBook()`**
  - ▸ Do: a) if `bookList.isEmpty()` print "Empty list!" + return; b) `book.showHeader();` c) `for (book bk : bookList) bk.showInfor();`.
  - ▸ Check: prints all seeded books aligned.

- [ ] **Step 6.3 — `findBook(String bookID)`**
  - ▸ Do: loop; on match print `book.showHeader()` + `bk.showInfor()` and `return true`; after loop print "Unable to find..." + `return false`.
  - ▸ Why: display-oriented search (vs. `timSachTheoID` which only returns) — two responsibilities, two methods.
  - ▸ Check: B01 prints; "ZZZ" → not found.

- [ ] **Step 6.4 — `addBook()`: choose the type**
  - ▸ Do: a) print 4 type options; b) `int type = nhapSoNguyen();` c) `book bk;` then `switch(type)` → `new Novel()/Textbook()/Comic()/Others()`; `default` → print invalid + `return;`.
  - ▸ Why: **upcasting** — subclass object in a `book` variable.
  - ▸ Check: each choice builds the right object.

- [ ] **Step 6.5 — `addBook()`: fill the object (polymorphism)**
  - ▸ Do: `bk.inputInfor();`
  - ▸ Why: **one line** runs the correct subclass version — the payoff of Module 2.
  - ▸ Check: choosing Comic prompts for issue number.

- [ ] **Step 6.6 — `addBook()`: reject duplicate ID**
  - ▸ Do: `if (timSachTheoID(bk.getBookID()) != null) { print "Book ID already existed!"; return; }`
  - ▸ Check: a duplicate ID is blocked.

- [ ] **Step 6.7 — `addBook()`: store it**
  - ▸ Do: `bookList.add(bk); print "Successfully added!";`
  - ▸ Check: the new book shows up in the list.

- [ ] **Step 6.8 — `deletedBook(String bookID)`: guard against active borrows**
  - ▸ Do: loop `borrowList`; if a record matches this bookID and `!isReturned()` → print "Books are being borrowed..." + `return;`.
  - ▸ Why: **referential safety** — don't delete a held book.
  - ▸ Check: deleting B01/B02 is blocked here.

- [ ] **Step 6.9 — `deletedBook`: remove by index**
  - ▸ Do: `for (int i=0; i<bookList.size(); i++)` if IDs match → `bookList.remove(i)`, print success, set a `removed` flag, `break;`. After loop, if `!removed` print "Book not found!".
  - ▸ Trap: use an index loop + `break` — never remove during a for-each.
  - ▸ Check: a free book deletes; a missing ID reports not found.

- [ ] **Step 6.10 — `showMenuBook()`**
  - ▸ Do: `do-while` submenu (1 Add, 2 Display, 3 Find, 4 Remove, 5 Return). Find/Remove read the ID with `sc.nextLine()` first, then call the method.
  - ▸ Check: every option works; 5 returns.

- [ ] **Step 6.11 — Wire into `showMenu()`** — case 1 → `showMenuBook()`.
- [ ] **✅ Module 6 done when:** add (incl. duplicate rejection), list, find, and safe-delete all work end to end.

---

## Module 7 — Member Management (CRUD)

> Outcome: Members submenu — same shape as Books, minus type/polymorphism. You'll feel the **CRUD pattern repeat**.

- [ ] **Step 7.1 — `findMemberByID(String memberID)`** — loop, return `Member` or `null` (`equalsIgnoreCase`).
- [ ] **Step 7.2 — `showListMember()`** — empty check → message; else `Member.showHeader()` + loop rows.
- [ ] **Step 7.3 — `addMember()`: create + fill** — `Member mb = new Member(); mb.inputInfor();`.
- [ ] **Step 7.4 — `addMember()`: reject duplicate + store** — `if (findMemberByID(mb.getMemberID()) != null) {...return;}` else add + success.
- [ ] **Step 7.5 — `deletedMember(String memberID)`: guard** — block if a record matches this member and `!isReturned()`.
- [ ] **Step 7.6 — `deletedMember`: remove by index** — same index-loop pattern as 6.9.
- [ ] **Step 7.7 — `showMenuMember()`** — submenu.
  - ▸ Trap (Find case): after `findMemberByID` returns non-null, you must call `Member.showHeader()` + `mb.showInfor()` yourself (the helper doesn't print).
- [ ] **Step 7.8 — Wire into `showMenu()`** — case 2.
- [ ] **✅ Module 7 done when:** add/list/find/safe-delete work; deleting a borrowing member (M01/M02) is blocked.

---

## Module 8 — Borrow / Return + Reports (Full integration)

> Outcome: the climax — all 3 lists + `Borrowing`'s logic together. Each validation is its **own step** so you build and test one rule at a time.

### 8A — `muonSach()` (borrow), built one guard at a time

- [ ] **Step 8.1 — Empty-storage + read member**
  - ▸ Do: a) if `bookList.isEmpty()` → message + return; b) read memberID; c) `Member mb = findMemberByID(...)`; if null → "Member not found!" + return.
  - ▸ Check: unknown member is rejected.

- [ ] **Step 8.2 — Enforce the borrow limit**
  - ▸ Do: count `borrowList` records where memberID matches and `!isReturned()`; if `>= 3` → "limit reached" + return.
  - ▸ Check: M01 (already 3) is blocked here.

- [ ] **Step 8.3 — Read book + existence + stock**
  - ▸ Do: a) read bookID; b) `book bk = timSachTheoID(...)`; null → "Book not found!" + return; c) if `bk.getQuantity() <= 0` → "out of stock!" + return.
  - ▸ Check: a missing or zero-stock book is rejected.

- [ ] **Step 8.4 — Block double-borrow of the same book**
  - ▸ Do: loop `borrowList`; if a record matches member + book and `!isReturned()` → "already borrowing this book!" + return.
  - ▸ Check: borrowing a book you already hold is blocked.

- [ ] **Step 8.5 — Read date + confirm**
  - ▸ Do: a) prompt + `LocalDate borrowDate = nhapNgay();` if null → return; b) print "[1] Confirm [2] Cancel"; `int confirm = sc.nextInt(); sc.nextLine();`; if `confirm != 1` → "cancelled" + return.
  - ▸ Trap: `sc.nextInt()` then `sc.nextLine()` — clear the buffer or the next read breaks.
  - ▸ Check: cancel aborts cleanly.

- [ ] **Step 8.6 — Commit the borrow**
  - ▸ Do: a) `borrowList.add(new Borrowing(memberID, bookID, borrowDate, null, false));` b) `bk.setQuantity(bk.getQuantity() - 1);` c) print success with book + member names.
  - ▸ Why: **golden rule** — stock drops by 1.
  - ▸ Check: record added; stock down 1.

### 8B — `traSach()` (return), one piece at a time

- [ ] **Step 8.7 — Read member + book**
  - ▸ Do: read memberID (null member → stop), read bookID (null book → stop).
  - ▸ Check: both validated.

- [ ] **Step 8.8 — Find the active record**
  - ▸ Do: loop `borrowList`; capture the record matching member + book with `!isReturned()` into `record`; if still null → "no borrowing history" + return.
  - ▸ Check: returns the right open record.

- [ ] **Step 8.9 — Read return date + confirm** (same pattern as 8.5).

- [ ] **Step 8.10 — Commit the return**
  - ▸ Do: a) `record.setReturnDate(returnDate);` b) `record.setReturned(true);` c) `bk.setQuantity(bk.getQuantity() + 1);`
  - ▸ Why: **golden rule** — stock up by 1.
  - ▸ Check: record flips to Returned; stock up 1.

- [ ] **Step 8.11 — Compute & print the fine**
  - ▸ Do: a) `long days = record.tinhNgayQuaHan(returnDate); long fine = record.tinhTienPhat(returnDate);` b) if `fine == 0` print "Not overdue." else print "Overdue: %d day(s) | fine: %,d VND." with days + fine.
  - ▸ Why: the Controller **calls** `Borrowing` — never recomputes the fine itself.
  - ▸ Check: returning M02's B02 (30 days) shows overdue days + fine.

### 8C — Borrow views + submenu

- [ ] **Step 8.12 — `xemDanhSachDangMuon()`** — loop `borrowList`; for each `!isReturned()` print header once (a `headerShown` flag) + `showInfor()`. None → "No books are currently being borrowed."
- [ ] **Step 8.13 — `lichSuMuonTheoMember(String memberID)`** — validate member; loop printing all their records (header once); none → "No borrowing history yet."
- [ ] **Step 8.14 — `showMenuBorrow()`** — submenu (1 Borrow, 2 Return, 3 Display borrowed, 4 History by member, 5 Return). Case 4 reads the ID then calls 8.13.
- [ ] **Step 8.15 — Wire into `showMenu()`** — case 3.

### 8D — Reports

- [ ] **Step 8.16 — `xemSachQuaHan()`**
  - ▸ Do: `LocalDate today = LocalDate.now();` loop unreturned records; if `tinhNgayQuaHan(today) > 0` print `showInfor()` + "Overdue Days: N" + a divider. None → "No overdue books."
  - ▸ Check: lists exactly the seeded overdue copy.

- [ ] **Step 8.17 — `showPopularBooks()` pass 1: find the max**
  - ▸ Do: a) if `borrowList.isEmpty()` → "No data yet!" + return; b) `int max = 0;` for each book, count its appearances in `borrowList`; track the largest into `max`.
  - ▸ Why: the **two-pass max** pattern — first learn the target.
  - ▸ Check: `max` equals the top borrow count.

- [ ] **Step 8.18 — `showPopularBooks()` pass 2: print all at max**
  - ▸ Do: loop books again; recount; if `count == max && count > 0` print the book + "Borrow Times: count".
  - ▸ Why: handles ties (multiple books can share the max).
  - ▸ Check: correct popular book(s).

- [ ] **Step 8.19 — `showTopMembers()`** — same two passes over `memberList` (count per member).
- [ ] **Step 8.20 — `showReports()`**
  - ▸ Do: print totals — `bookList.size()`, `memberList.size()`, `borrowList.size()`, count `!isReturned()` (currently borrowed), count `isReturned()` (returned).
- [ ] **Step 8.21 — `showMenuReport()`** — 6-option submenu (1 Borrowed→`xemDanhSachDangMuon`, 2 Overdue, 3 Popular, 4 Top members, 5 Stats, 6 Return).
- [ ] **Step 8.22 — Wire into `showMenu()`** — case 4.

- [ ] **✅ Module 8 done when (test against seed data):**
  - M01 can't borrow a 4th book;
  - returning M02's overdue B02 shows the right fine;
  - Overdue report lists exactly that copy;
  - out-of-stock / double-borrow are blocked;
  - Popular books / Top members are correct.

---

## 🎓 Panel Defense Prep (hội đồng)

Be able to answer these out loud:

1. **The 4 OOP pillars** — where is each, with file + line?
2. **Overriding vs. overloading** — one example of each from your code.
3. Why `List<book>` and not `List<Novel>`? (upcasting / polymorphism)
4. `static` vs. instance method — why is `showHeader()` static but `showInfor()` not?
5. Why does the fine logic live in `Borrowing`? (single responsibility)
6. Why are fields `private`? (encapsulation)
7. Where does exception handling protect the program?
8. To add a `Magazine` type, what changes? (one new subclass + one `case` in `addBook` — nothing else)

### Concept → code map (fill in line numbers as you build)

| Concept | Where in your code |
|---------|--------------------|
| Encapsulation | `private` fields + getters/setters in every model class |
| Inheritance | `Novel/Textbook/Comic/Others extends book` |
| Polymorphism (override) | `inputInfor()`, `getThongTinChiTiet()` |
| Polymorphism (upcasting) | `List<book>` holding subclasses; `addBook()` |
| Overloading | two `book` constructors; two `seedBorrow(...)` |
| Abstraction | `book` defining shared behavior |
| Static | `showHeader()`, `Borrowing` constants |
| Exception handling | `nhapSoNguyen()`, `nhapNgay()` |
| Composition (HAS-A) | `LAB01` holding the 3 lists |

---

## 📊 Module Progress Tracker

- [ ] Module 1 — `book` (Steps 1.1–1.14)
- [ ] Module 2 — subclasses (Steps 2.1–2.10)
- [ ] Module 3 — `Member` (Steps 3.1–3.9)
- [ ] Module 4 — `Borrowing` (Steps 4.1–4.11)
- [ ] Module 5 — skeleton + helpers (Steps 5.1–5.17)
- [ ] Module 6 — Book management (Steps 6.1–6.11)
- [ ] Module 7 — Member management (Steps 7.1–7.8)
- [ ] Module 8 — Borrow/Return + Reports (Steps 8.1–8.22)
- [ ] Defense Prep — the 8 questions

> 💡 **Tip:** After each Step (or small group), **compile and run**. One mistake at a time is far easier than debugging a whole class.
>
> Compile/run from the project root:
> ```
> javac -d build_tmp src/schema/*.java src/lab/pkg01/*.java
> java -cp build_tmp lab.pkg01.LAB01
> ```

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
> **How to read this file:** Work top to bottom. The project is split into **11 Modules** (Modules 1–8 = the core app, 9–11 = Milestone 4 File I/O); each Module is split into **Steps**; complex Steps are split again into lettered **micro-actions (a, b, c…)**. Every Step has the same 4 parts:
> - **▸ Do** — the exact thing to write (micro-actions listed a, b, c…).
> - **▸ Why** — the concept it teaches / why it exists.
> - **▸ Trap** — the mistake people make here (skip if none).
> - **▸ Check** — how you know it's done before moving on.
>
> Tick `[ ]` → `[x]` as you finish. **Never skip ahead** — each Step builds on the previous one.
>
> **Naming rule:** Keep the existing Vietnamese method names exactly (`timSachTheoID`, `muonSach`, `traSach`, `nhapSoNguyen`, `nhapNgay`, `tinhNgayQuaHan`, `tinhTienPhat`, `xemSachQuaHan`, `xemDanhSachDangMuon`, `lichSuMuonTheoMember`, `getThongTinChiTiet`). Everything else is English.

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
  Module 5: LAB01 skeleton       main, menu loop, hardcoded starter data, input helpers
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
  - ▸ Why: One-line creation for the hardcoded starter data. Two constructors = **overloading**.
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

> Outcome: the app launches, shows a working main menu, has hardcoded starter data + safe input. No real features yet.

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

> **Note:** the starter data is **hardcoded directly in the constructor** — no `seedData()`/`seedBorrow()`
> helper. Each object gets an explicit `Type var = new Type(...)` line, then is added to its list. This
> compiles and runs on its own (it needs nothing from Module 6).

- [ ] **Step 5.10 — Constructor: hardcode the 4 books**
  - ▸ Do: in `public LAB01() { ... }`, create one of each subclass with a named variable, then add it:
    - `Novel b01 = new Novel("B01","Truyen Kieu","Nguyen Du","1820",4,"Romance");`
    - `Textbook b02 = new Textbook("B02","Mathematics 9","Phan Duc Chinh","2005",1,"Mathematics");`
    - `Comic b03 = new Comic("B03","Tham Tu Conan","Gosho Aoyama","1994",9,42);`
    - `Others b04 = new Others("B04","Tu Dien Tieng Viet","Hoang Phe","2018",4,"Dictionary");`
    - then `bookList.add(b01);` … `add(b04);`
  - ▸ Why: explicit `Type var = new Type(...)` makes every object visible; using each subclass's **full constructor** shows inheritance + overloading at work.
  - ▸ Trap: `quantity` is the copies **left in stock** — set it already reduced by the copies on loan in Step 5.12 (that's why B01=4, B02=1, B03=9, B04=4, not the catalog totals).
  - ▸ Check: 4 books at startup.

- [ ] **Step 5.11 — Constructor: hardcode the 3 members**
  - ▸ Do: `Member m01 = new Member("M01","Nguyen Van A","vana@gmail.com","0901111111");` … `m02`, `m03`; then `memberList.add(m01);` …
  - ▸ Check: 3 members.

- [ ] **Step 5.12 — Constructor: hardcode the borrow records (test scenarios)**
  - ▸ Do: create 4 `Borrowing` objects and add them:
    - `new Borrowing("M01","B01",LocalDate.now(),null,false)` for B01, B02, B03 → M01 holds 3 (the **borrow-limit** scenario).
    - `new Borrowing("M02","B02",LocalDate.now().minusDays(30),null,false)` → unreturned & 30 days old (the **overdue** scenario).
  - ▸ Why: ready-made data to demo the borrow-limit rule and the overdue report.
  - ▸ Trap: these borrows are exactly why the stock numbers in 5.10 are already reduced — keep them consistent (each active borrow = one copy off the shelf).
  - ▸ Check: startup has 4 borrow records; M01 is at the limit; B02 shows overdue in reports.

- [ ] **✅ Module 5 done when:** app launches with the hardcoded starter data, menu navigates and exits, bad input never crashes it. Unlike a seed helper, this compiles and runs **standalone** — no dependency on Module 6.

---

## Module 6 — Book Management (CRUD)

> Outcome: full Books submenu. Where polymorphism does real work.

- [ ] **Step 6.1 — `timSachTheoID(String bookID)`** (write FIRST — many things need it)
  - ▸ Do: loop `bookList`; if `bk.getBookID().equalsIgnoreCase(bookID)` return `bk`; after loop return `null`.
  - ▸ Why: internal lookup; returns the object, prints nothing.
  - ▸ Check: returns the right book or null.

- [ ] **Step 6.2 — `showListBook()`**
  - ▸ Do: a) if `bookList.isEmpty()` print "Empty list!" + return; b) `book.showHeader();` c) `for (book bk : bookList) bk.showInfor();`.
  - ▸ Check: prints all starter books aligned.

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
  - ▸ Check: lists exactly the hardcoded overdue copy.

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

- [ ] **✅ Module 8 done when (test against the hardcoded starter data):**
  - M01 can't borrow a 4th book;
  - returning M02's overdue B02 shows the right fine;
  - Overdue report lists exactly that copy;
  - out-of-stock / double-borrow are blocked;
  - Popular books / Top members are correct.

---

# MILESTONE 4 — PERSISTENCE & ROBUSTNESS (File I/O + Exception Handling)

> **Why this milestone exists:** right now everything lives in RAM — close the program and all
> added books/members/borrows vanish, then the hardcoded starter data rebuilds the same demo every launch.
> Here you make the library **survive a restart** by writing it to text files and reading it back,
> and you make the program **never crash** on bad input or a missing file.
>
> **⚠️ New territory:** File I/O and `try-catch` aren't taught in class yet, so each step below
> explains the *new tool* it introduces in one plain sentence. Go slow; compile after each step.

### The 4 brand-new Java tools you'll meet here

| Tool | One-line meaning |
|------|------------------|
| `try { … } catch (IOException e) { … }` | "Try this risky code; if it fails, run the catch block instead of crashing." |
| `PrintWriter` + `FileWriter` | The **writing** pair. `FileWriter` opens a file for writing; `PrintWriter` wraps it so you get `println()`. |
| `BufferedReader` + `FileReader` | The **reading** pair. `FileReader` opens a file for reading; `BufferedReader` wraps it so you get `readLine()`. |
| `String.split("\\|")` | Cuts one line `"A|B|C"` into an array `["A","B","C"]`. |
| `new File(path).exists()` / `.mkdirs()` | Ask if a file exists / create a folder. |

### The file format you're saving to (plain text, one object per line, `|` between fields)

```
data/books.txt       TYPE|bookID|name|author|year|quantity|extra
                     NOVEL|B01|Truyen Kieu|Nguyen Du|1820|4|Romance
                     COMIC|B03|Tham Tu Conan|Gosho Aoyama|1994|9|42
data/members.txt     memberID|name|email|phone
                     M01|Nguyen Van A|vana@gmail.com|0901111111
data/borrowings.txt  memberID|bookID|borrowDate|returnDate|returned
                     M02|B02|31/05/2026|null|false
```
> The **TYPE** word at the front of each book line is the trick that lets the reader know which
> subclass (`Novel`/`Comic`/`Textbook`/`Others`) to rebuild. `returnDate` is the literal word
> `null` when the book hasn't been returned yet.

---

## Module 9 — Save to file (WRITE)

> Outcome: a menu option that dumps all three lists into readable `.txt` files.

- [ ] **Step 9.1 — Add imports + file-path constants**
  - ▸ Do:
    - a) At the top add: `import java.io.File; import java.io.FileWriter; import java.io.PrintWriter; import java.io.IOException;` (and `BufferedReader`, `FileReader` — you'll need them in Module 10).
    - b) Near your other fields add 4 constants: `DATA_DIR = "data"`, `BOOKS_FILE = "data/books.txt"`, `MEMBERS_FILE`, `BORROWINGS_FILE`.
  - ▸ Why: constants keep file paths in **one place** — change the folder once, not in 6 spots.
  - ▸ Check: compiles (unused imports are fine for now).

- [ ] **Step 9.2 — `saveData()`: the orchestrator**
  - ▸ Do:
    - a) `new File(DATA_DIR).mkdirs();` — creates the `data/` folder if missing.
    - b) Wrap three calls — `saveBooks(); saveMembers(); saveBorrowings();` — inside `try { … } catch (IOException e) { print "Could not save data: " + e.getMessage(); }`.
    - c) On success print `"Data saved..."`.
  - ▸ Why: **try-catch** = "if writing fails (disk full, no permission), show a message instead of crashing." That one `catch` is your whole exception-handling story for saving.
  - ▸ Trap: the three savers will `throw IOException` — `saveData()` must catch it, OR the code won't compile.
  - ▸ Check: compiles once 9.3–9.5 exist.

- [ ] **Step 9.3 — `saveBooks()`: write each book, detect its type**
  - ▸ Do:
    - a) `PrintWriter pw = new PrintWriter(new FileWriter(BOOKS_FILE));` — opens the file (declare the method `throws IOException`).
    - b) Loop `bookList`. Build the 5 **common** fields into one string joined by `"|"`.
    - c) Use an **`instanceof` chain**: `if (bk instanceof Novel) pw.println("NOVEL|" + common + "|" + ((Novel) bk).getGenre());` … and the same for `Comic`(issueNumber) / `Textbook`(subject) / `Others`(note).
    - d) `pw.close();` — **always close**, or the file may be empty/locked.
  - ▸ Why: `List<book>` hides the real type, but at runtime each object still **knows** what it is. `instanceof` asks "what are you really?", the cast `((Novel) bk)` unlocks the subclass-only getter. **This is your #1 polymorphism talking point.**
  - ▸ Trap: forgetting `pw.close()` → data not flushed to disk.
  - ▸ Check: after wiring the menu, saving writes a `books.txt` you can open in Notepad.

- [ ] **Step 9.4 — `saveMembers()`**
  - ▸ Do: same `PrintWriter`/`FileWriter` pattern; for each member `println(memberID + "|" + name + "|" + email + "|" + phone)`; `close()`. Declare `throws IOException`.
  - ▸ Trap: keep the field order identical to the `Member` constructor (ID, name, email, phone) — Module 10 reads them back in that order.
  - ▸ Check: `members.txt` has one line per member.

- [ ] **Step 9.5 — `saveBorrowings()`: dates + the null case**
  - ▸ Do:
    - a) For each record, `String borrow = br.getBorrowDate().format(FMT);` (turns a `LocalDate` into `"dd/MM/yyyy"`).
    - b) `returnDate` may be `null` (not returned) → write the word `"null"`; else `br.getReturnDate().format(FMT)`. Use a ternary: `(br.getReturnDate() == null) ? "null" : br.getReturnDate().format(FMT)`.
    - c) `println(memberID + "|" + bookID + "|" + borrow + "|" + ret + "|" + br.isReturned());`
  - ▸ Why: you reuse the **same `FMT`** you already built in Module 4 — one date format everywhere.
  - ▸ Trap: calling `.format()` on a `null` returnDate → `NullPointerException`. That's exactly why the `"null"` check exists.
  - ▸ Check: `borrowings.txt` shows `null` for unreturned books, a real date for returned ones.

- [ ] **Step 9.6 — Add the "Save to file" menu option**
  - ▸ Do: in `showMenu()` add option `5. Save to file` → `case 5: saveData();`.
  - ▸ Check: choosing 5 prints "Data saved" and the 3 files appear in `data/`.

- [ ] **✅ Module 9 done when:** after adding a book then choosing Save, all three `data/*.txt` files exist and contain your data in readable form (open them in Notepad to prove it).

---

## Module 10 — Load from file (READ)

> Outcome: rebuild all three lists from the `.txt` files — the exact reverse of Module 9.

- [ ] **Step 10.1 — `loadData()`: the orchestrator**
  - ▸ Do:
    - a) **Clear** the three lists first (`bookList.clear();` …) so loading replaces, not duplicates.
    - b) Call `loadBooks(); loadMembers(); loadBorrowings();` inside `try { … } catch (IOException e) { print "Could not load data: " + e.getMessage(); }`.
    - c) Print a summary: how many books / members / borrowings were loaded.
  - ▸ Why: clearing first means hitting "Load" twice doesn't give you double the books.
  - ▸ Trap: if you forget `.clear()`, every Load **appends** — list keeps growing.
  - ▸ Check: compiles once 10.2–10.4 exist.

- [ ] **Step 10.2 — `loadBooks()`: read lines, rebuild the right subclass**
  - ▸ Do:
    - a) `BufferedReader br = new BufferedReader(new FileReader(BOOKS_FILE));` (method `throws IOException`).
    - b) Loop with `String line; while ((line = br.readLine()) != null) { … }` — `readLine()` returns one line, or `null` at end-of-file (that's the loop's stop signal).
    - c) `String[] p = line.split("\\|");` then read `p[0]` (TYPE), `p[1]` (bookID) …; `int quantity = Integer.parseInt(p[5]);` (text `"4"` → number `4`).
    - d) `switch (p[0])`: `"NOVEL"` → `bookList.add(new Novel(..., p[6]));`, `"COMIC"` → `new Comic(..., Integer.parseInt(p[6]))`, etc.
    - e) `br.close();`
  - ▸ Why: the saved **TYPE** word tells you which constructor to call — this is how you turn flat text back into the correct object. It's the mirror image of the `instanceof` chain in 9.3.
  - ▸ **Trap (important):** `line.split("|")` is **WRONG** — in a regex `|` means "OR", so it splits between every character. You must escape it: `line.split("\\|")`.
  - ▸ Trap: `Comic.issueNumber` is an `int` — wrap it in `Integer.parseInt(p[6])`, not just `p[6]`.
  - ▸ Check: after Load, "Display all books" shows exactly what was in the file.

- [ ] **Step 10.3 — `loadMembers()`**
  - ▸ Do: `readLine` loop; `String[] p = line.split("\\|");` then `memberList.add(new Member(p[0], p[1], p[2], p[3]));` `close()`.
  - ▸ Check: members reload correctly.

- [ ] **Step 10.4 — `loadBorrowings()`: parse dates, undo the null trick**
  - ▸ Do:
    - a) `LocalDate borrowDate = LocalDate.parse(p[2], FMT);` (text → date, using your `FMT`).
    - b) `LocalDate returnDate = p[3].equals("null") ? null : LocalDate.parse(p[3], FMT);` — turns the word `"null"` back into a real `null`.
    - c) `boolean returned = Boolean.parseBoolean(p[4]);` (text `"true"`/`"false"` → boolean).
    - d) `borrowList.add(new Borrowing(p[0], p[1], borrowDate, returnDate, returned));`
  - ▸ Why: `Borrowing` stores **IDs**, not object references — so loading is trivial: no need to relink to book/member objects, the lookups already happen by ID elsewhere.
  - ▸ Trap: `LocalDate.parse("null", FMT)` would throw — that's why you check for `"null"` first.
  - ▸ Check: returned vs. not-returned status survives a save+load round-trip.

- [ ] **Step 10.5 — Add the "Load from file" menu option**
  - ▸ Do: add `6. Load from file` → `case 6: loadData();`.
  - ▸ Check: choosing 6 prints the "Data loaded: N books…" summary.

- [ ] **✅ Module 10 done when:** Save → exit → relaunch → Load shows the *same* data you saved. Edit a number inside `books.txt` by hand, Load, and confirm the change appears — proof it's truly reading the file.

---

## Module 11 — Wire-up & robustness

> Outcome: persistence is automatic, and the two crash-prone confirm prompts are made safe.

- [ ] **Step 11.1 — Auto-load on startup (first run uses hardcoded data, later runs load)**
  - ▸ Do: in the `LAB01()` constructor, wrap the hardcoded starter data (the books/members/borrows from Module 5) in an `else` so it only runs when there's no save file yet:
    ```
    if (new File(BOOKS_FILE).exists()) {
        loadData();
    } else {
        // ... the hardcoded Novel/Textbook/Comic/Others + Member + Borrowing block ...
    }
    ```
  - ▸ Why: the **first** ever run has no file → use the hardcoded demo data; **every run after a save** finds the file → load real data. This is the behaviour the panel wants to see.
  - ▸ Trap: check the *file*, not the folder — an empty `data/` folder with no `books.txt` should still fall back to the hardcoded data.
  - ▸ Check: delete `data/`, run → hardcoded data; Save+exit; run again → "Data loaded".

- [ ] **Step 11.2 — Auto-save on exit + new exit number**
  - ▸ Do: your main menu now has 5 (Save) and 6 (Load), so **Exit moves to 7**. Update: the printed `7. Exit`, `case 7:` → `saveData();` then print Goodbye, and the loop condition `while (choice != 7);`.
  - ▸ Why: auto-saving on exit means a user who forgets to hit "Save" still keeps their work.
  - ▸ Trap: forgetting to change `while (choice != 5)` → `!= 7` will exit the program on the wrong key.
  - ▸ Check: exiting writes the files even if you never chose option 5.

- [ ] **Step 11.3 — Stop the confirm-prompt crash (exception hardening)**
  - ▸ Do: in `muonSach()` and `traSach()`, replace `int confirm = sc.nextInt(); sc.nextLine();` with `int confirm = nhapSoNguyen();`.
  - ▸ Why: `sc.nextInt()` **throws `InputMismatchException` and crashes** if the user types a letter. `nhapSoNguyen()` (built in Module 5) already loops with `try-catch`, so a bad key just re-asks. You **reuse** a safe helper instead of writing new try-catch.
  - ▸ Trap: this was a real latent crash — type `x` at the confirm prompt in the old code and the whole program dies.
  - ▸ Check: at a confirm prompt, typing `x` shows "Input valid number:" and waits — no stack trace.

- [ ] **✅ Module 11 done when:** delete `data/` → run → add a book → exit (auto-saves) → relaunch → your book is still there; and typing a letter at any confirm prompt never crashes the program.

---

## 🆕 New Java concepts in Milestone 4 (be ready to explain)

| Concept | Where in your code | One-sentence defense answer |
|---------|--------------------|------------------------------|
| Exception handling (`try-catch`) | `saveData()`, `loadData()`, `nhapSoNguyen()`, `nhapNgay()` | "Risky code (file/parse) runs in `try`; if it fails, `catch` shows a message so the program keeps running." |
| Writing files | `saveBooks/Members/Borrowings` | "`FileWriter` opens the file, `PrintWriter` lets me `println` each object as a line." |
| Reading files | `loadBooks/Members/Borrowings` | "`FileReader`+`BufferedReader` let me `readLine()` until `null`, then `split('\\|')` the columns." |
| `instanceof` + casting | `saveBooks()` | "The list holds `book`, but at runtime I check the real subclass to grab its unique field." |
| `switch` on a type tag | `loadBooks()` | "The saved TYPE word tells me which constructor to call to rebuild the right subclass." |

### 3 extra panel questions for this milestone
9. **Why plain-text CSV and not Java serialization (`.dat`)?** → readable/debuggable in Notepad, easy to explain, no `Serializable` needed.
10. **When reading a book line, how do you know whether to build a `Novel` or a `Comic`?** → the `TYPE` word saved as the first column; `switch` on it.
11. **Where could this still crash, and how did you guard it?** → bad menu input (guarded by `nhapSoNguyen`), bad date (guarded by `nhapNgay`), missing/locked file (guarded by `try-catch IOException`).

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
| Overloading | two `book` constructors (default + full) |
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
- [ ] Module 9 — Save to file / WRITE (Steps 9.1–9.6) · *Milestone 4*
- [ ] Module 10 — Load from file / READ (Steps 10.1–10.5) · *Milestone 4*
- [ ] Module 11 — Wire-up & robustness (Steps 11.1–11.3) · *Milestone 4*
- [ ] Defense Prep — the 11 questions + CT Reflection report

> 💡 **Tip:** After each Step (or small group), **compile and run**. One mistake at a time is far easier than debugging a whole class.
>
> Compile/run from the project root:
> ```
> javac -d build_tmp src/schema/*.java src/lab/pkg01/*.java
> java -cp build_tmp lab.pkg01.LAB01
> ```

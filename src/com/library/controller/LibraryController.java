package com.library.controller;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.service.LibraryService;
import com.library.util.InputValidator;

import java.time.LocalDate;
import java.util.List;

public class LibraryController {

    private final LibraryService service = new LibraryService();

    // ─────────────────────────────────────────
    // MAIN MENU
    // ─────────────────────────────────────────

    public void displayMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║   📚 LIBRARY MANAGEMENT SYSTEM   ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Book Management               ║");
            System.out.println("║  2. Member Management             ║");
            System.out.println("║  3. Transaction Management        ║");
            System.out.println("║  0. Exit                          ║");
            System.out.println("╚══════════════════════════════════╝");

            int choice = InputValidator.readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> bookSubMenu();
                case 2 -> memberSubMenu();
                case 3 -> transactionSubMenu();
                case 0 -> {
                    System.out.println("👋 Goodbye!");
                    return;
                }
                default -> System.err.println("❌ Invalid choice. Try again.");
            }
        }
    }

    // ─────────────────────────────────────────
    // BOOK SUB-MENU
    // ─────────────────────────────────────────

    private void bookSubMenu() {
        while (true) {
            System.out.println("\n── 📖 Book Management ──");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("0. Back");

            int choice = InputValidator.readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> showAllBooks();
                case 3 -> updateBook();
                case 4 -> deleteBook();
                case 0 -> { return; }
                default -> System.err.println("❌ Invalid choice.");
            }
        }
    }

    private void addBook() {
        System.out.println("\n── Add New Book ──");
        String title    = InputValidator.readNonEmptyString("Title: ", "title");
        String author   = InputValidator.readNonEmptyString("Author: ", "author");
        String category = InputValidator.readNonEmptyString("Category: ", "category");
        int year        = InputValidator.readPositiveInt("Published Year: ", "published year");
        int copies      = InputValidator.readPositiveInt("Total Copies: ", "total copies");

        Book book = new Book(title, author, category, year, copies, copies);
        boolean result = service.addBook(book);
        System.out.println(result ? "✅ Book added successfully!" : "❌ Failed to add book.");
    }

    private void showAllBooks() {
        List<Book> books = service.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("📭 No books found.");
            return;
        }
        System.out.println("\n── All Books ──");
        System.out.printf("%-5s %-30s %-20s %-15s %-6s %-7s %-9s%n",
                "ID", "Title", "Author", "Category", "Year", "Total", "Available");
        System.out.println("─".repeat(95));
        for (Book b : books) {
            System.out.printf("%-5d %-30s %-20s %-15s %-6d %-7d %-9d%n",
                    b.getBookId(), b.getTitle(), b.getAuthor(),
                    b.getCategory(), b.getPublishedYear(),
                    b.getTotalCopies(), b.getAvailableCopies());
        }
    }

    private void updateBook() {
        int id = InputValidator.readPositiveInt("\nEnter Book ID to update: ", "book ID");
        Book book = service.getBookById(id);
        if (book == null) {
            System.err.println("❌ Book not found.");
            return;
        }
        System.out.println("Press Enter to keep current value.");

        String title = InputValidator.readOptionalString("Title [" + book.getTitle() + "]: ");
        if (!title.isBlank()) book.setTitle(title);

        String author = InputValidator.readOptionalString("Author [" + book.getAuthor() + "]: ");
        if (!author.isBlank()) book.setAuthor(author);

        String category = InputValidator.readOptionalString("Category [" + book.getCategory() + "]: ");
        if (!category.isBlank()) book.setCategory(category);

        String yearStr = InputValidator.readOptionalString("Published Year [" + book.getPublishedYear() + "]: ");
        if (!yearStr.isBlank()) {
            try {
                int year = Integer.parseInt(yearStr);
                if (year > 0) book.setPublishedYear(year);
                else System.err.println("❌ Invalid published year: must be greater than 0.");
            } catch (NumberFormatException e) {
                System.err.println("❌ Invalid published year: please enter a valid number.");
            }
        }

        String copiesStr = InputValidator.readOptionalString("Total Copies [" + book.getTotalCopies() + "]: ");
        if (!copiesStr.isBlank()) {
            try {
                int copies = Integer.parseInt(copiesStr);
                if (copies > 0) book.setTotalCopies(copies);
                else System.err.println("❌ Invalid total copies: must be greater than 0.");
            } catch (NumberFormatException e) {
                System.err.println("❌ Invalid total copies: please enter a valid number.");
            }
        }

        boolean result = service.updateBook(book);
        System.out.println(result ? "✅ Book updated successfully!" : "❌ Failed to update book.");
    }

    private void deleteBook() {
        int id = InputValidator.readPositiveInt("\nEnter Book ID to delete: ", "book ID");
        String confirm = InputValidator.readNonEmptyString("Are you sure? (yes/no): ", "confirmation");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Cancelled.");
            return;
        }
        boolean result = service.deleteBook(id);
        System.out.println(result ? "✅ Book deleted successfully!" : "❌ Failed to delete book.");
    }

    // ─────────────────────────────────────────
    // MEMBER SUB-MENU
    // ─────────────────────────────────────────

    private void memberSubMenu() {
        while (true) {
            System.out.println("\n── 👤 Member Management ──");
            System.out.println("1. Add Member");
            System.out.println("2. View All Members");
            System.out.println("3. Update Member");
            System.out.println("4. Delete Member");
            System.out.println("0. Back");

            int choice = InputValidator.readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addMember();
                case 2 -> showAllMembers();
                case 3 -> updateMember();
                case 4 -> deleteMember();
                case 0 -> { return; }
                default -> System.err.println("❌ Invalid choice.");
            }
        }
    }

    private void addMember() {
        System.out.println("\n── Add New Member ──");
        String name     = InputValidator.readNonEmptyString("Name: ", "name");
        String email    = InputValidator.readNonEmptyString("Email: ", "email");
        String phone    = InputValidator.readOptionalString("Phone (optional): ");
        int maxBooks    = InputValidator.readPositiveInt("Max Books Allowed: ", "max books allowed");

        Member member = new Member(name, email, phone, LocalDate.now(), maxBooks);
        boolean result = service.addMember(member);
        System.out.println(result ? "✅ Member added successfully!" : "❌ Failed to add member.");
    }

    private void showAllMembers() {
        List<Member> members = service.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("📭 No members found.");
            return;
        }
        System.out.println("\n── All Members ──");
        System.out.printf("%-5s %-20s %-25s %-15s %-12s %-10s%n",
                "ID", "Name", "Email", "Phone", "Member Since", "Max Books");
        System.out.println("─".repeat(90));
        for (Member m : members) {
            System.out.printf("%-5d %-20s %-25s %-15s %-12s %-10d%n",
                    m.getMemberId(), m.getName(), m.getEmail(),
                    m.getPhone(), m.getMembershipDate(), m.getMaxBooksAllowed());
        }
    }

    private void updateMember() {
        int id = InputValidator.readPositiveInt("\nEnter Member ID to update: ", "member ID");
        Member member = service.getMemberById(id);
        if (member == null) {
            System.err.println("❌ Member not found.");
            return;
        }
        System.out.println("Press Enter to keep current value.");

        String name = InputValidator.readOptionalString("Name [" + member.getName() + "]: ");
        if (!name.isBlank()) member.setName(name);

        String email = InputValidator.readOptionalString("Email [" + member.getEmail() + "]: ");
        if (!email.isBlank()) member.setEmail(email);

        String phone = InputValidator.readOptionalString("Phone [" + member.getPhone() + "]: ");
        if (!phone.isBlank()) member.setPhone(phone);

        String maxStr = InputValidator.readOptionalString("Max Books Allowed [" + member.getMaxBooksAllowed() + "]: ");
        if (!maxStr.isBlank()) {
            try {
                int max = Integer.parseInt(maxStr);
                if (max > 0) member.setMaxBooksAllowed(max);
                else System.err.println("❌ Invalid max books allowed: must be greater than 0.");
            } catch (NumberFormatException e) {
                System.err.println("❌ Invalid max books allowed: please enter a valid number.");
            }
        }

        boolean result = service.updateMember(member);
        System.out.println(result ? "✅ Member updated successfully!" : "❌ Failed to update member.");
    }

    private void deleteMember() {
        int id = InputValidator.readPositiveInt("\nEnter Member ID to delete: ", "member ID");
        String confirm = InputValidator.readNonEmptyString("Are you sure? (yes/no): ", "confirmation");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Cancelled.");
            return;
        }
        boolean result = service.deleteMember(id);
        System.out.println(result ? "✅ Member deleted successfully!" : "❌ Failed to delete member.");
    }

    // ─────────────────────────────────────────
    // TRANSACTION SUB-MENU
    // ─────────────────────────────────────────

    private void transactionSubMenu() {
        while (true) {
            System.out.println("\n── 🔄 Transaction Management ──");
            System.out.println("1. Issue Book");
            System.out.println("2. Return Book");
            System.out.println("3. View All Transactions");
            System.out.println("0. Back");

            int choice = InputValidator.readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> issueBook();
                case 2 -> returnBook();
                case 3 -> showAllTransactions();
                case 0 -> { return; }
                default -> System.err.println("❌ Invalid choice.");
            }
        }
    }

    private void issueBook() {
        System.out.println("\n── Issue Book ──");
        int bookId   = InputValidator.readPositiveInt("Enter Book ID: ", "book ID");
        int memberId = InputValidator.readPositiveInt("Enter Member ID: ", "member ID");
        String result = service.issueBook(bookId, memberId);
        System.out.println(result);
    }

    private void returnBook() {
        System.out.println("\n── Return Book ──");
        int transactionId = InputValidator.readPositiveInt("Enter Transaction ID: ", "transaction ID");
        String result = service.returnBook(transactionId);
        System.out.println(result);
    }

    private void showAllTransactions() {
        List<Transaction> transactions = service.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("📭 No transactions found.");
            return;
        }
        System.out.println("\n── All Transactions ──");
        System.out.printf("%-5s %-8s %-8s %-12s %-12s %-12s %-10s%n",
                "ID", "Book ID", "Mem ID", "Issue Date", "Due Date", "Return Date", "Status");
        System.out.println("─".repeat(75));
        for (Transaction t : transactions) {
            System.out.printf("%-5d %-8d %-8d %-12s %-12s %-12s %-10s%n",
                    t.getTransactionId(), t.getBookId(), t.getMemberId(),
                    t.getIssueDate(),
                    t.getDueDate()    != null ? t.getDueDate()    : "-",
                    t.getReturnDate() != null ? t.getReturnDate() : "-",
                    t.getStatus());
        }
    }
}
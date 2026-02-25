	package com.library.controller;
	
	import com.library.model.Book;
	import com.library.model.Member;
	import com.library.model.Transaction;
	import com.library.service.LibraryService;
	
	import java.time.LocalDate;
	import java.util.List;
	import java.util.Scanner;
	
	public class LibraryController {
	
	    private final LibraryService service = new LibraryService();
	    private final Scanner scanner = new Scanner(System.in);
	
	    // ─────────────────────────────────────────
	    // MAIN MENU
	    // ─────────────────────────────────────────
	
	    public void displayMenu() {
	        while (true) {
	            System.out.println("\n╔══════════════════════════════════╗");
	            System.out.println("║     📚 LIBRARY MANAGEMENT SYSTEM  ║");
	            System.out.println("╠══════════════════════════════════╣");
	            System.out.println("║  1. Book Management               ║");
	            System.out.println("║  2. Member Management             ║");
	            System.out.println("║  3. Transaction Management        ║");
	            System.out.println("║  0. Exit                          ║");
	            System.out.println("╚══════════════════════════════════╝");
	            System.out.print("Enter your choice: ");
	
	            int choice = readInt();
	            switch (choice) {
	                case 1 -> bookSubMenu();
	                case 2 -> memberSubMenu();
	                case 3 -> transactionSubMenu();
	                case 0 -> {
	                    System.out.println("👋 Goodbye!");
	                    return;
	                }
	                default -> System.out.println("❌ Invalid choice. Try again.");
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
	            System.out.print("Enter your choice: ");
	
	            int choice = readInt();
	            switch (choice) {
	                case 1 -> addBook();
	                case 2 -> showAllBooks();
	                case 3 -> updateBook();
	                case 4 -> deleteBook();
	                case 0 -> { return; }
	                default -> System.out.println("❌ Invalid choice.");
	            }
	        }
	    }
	
	    private void addBook() {
	        System.out.println("\n── Add New Book ──");
	        System.out.print("Title: ");
	        String title = scanner.nextLine();
	        System.out.print("Author: ");
	        String author = scanner.nextLine();
	        System.out.print("Category: ");
	        String category = scanner.nextLine();
	        System.out.print("Published Year: ");
	        int year = readInt();
	        System.out.print("Total Copies: ");
	        int copies = readInt();
	
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
	        System.out.print("\nEnter Book ID to update: ");
	        int id = readInt();
	        Book book = service.getBookById(id);
	        if (book == null) {
	            System.out.println("❌ Book not found.");
	            return;
	        }
	        System.out.println("Leave blank to keep current value.");
	        System.out.print("Title [" + book.getTitle() + "]: ");
	        String title = scanner.nextLine();
	        if (!title.isBlank()) book.setTitle(title);
	
	        System.out.print("Author [" + book.getAuthor() + "]: ");
	        String author = scanner.nextLine();
	        if (!author.isBlank()) book.setAuthor(author);
	
	        System.out.print("Category [" + book.getCategory() + "]: ");
	        String category = scanner.nextLine();
	        if (!category.isBlank()) book.setCategory(category);
	
	        System.out.print("Published Year [" + book.getPublishedYear() + "]: ");
	        String yearStr = scanner.nextLine();
	        if (!yearStr.isBlank()) book.setPublishedYear(Integer.parseInt(yearStr));
	
	        System.out.print("Total Copies [" + book.getTotalCopies() + "]: ");
	        String copiesStr = scanner.nextLine();
	        if (!copiesStr.isBlank()) book.setTotalCopies(Integer.parseInt(copiesStr));
	
	        boolean result = service.updateBook(book);
	        System.out.println(result ? "✅ Book updated successfully!" : "❌ Failed to update book.");
	    }
	
	    private void deleteBook() {
	        System.out.print("\nEnter Book ID to delete: ");
	        int id = readInt();
	        System.out.print("Are you sure? (yes/no): ");
	        String confirm = scanner.nextLine();
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
	            System.out.print("Enter your choice: ");
	
	            int choice = readInt();
	            switch (choice) {
	                case 1 -> addMember();
	                case 2 -> showAllMembers();
	                case 3 -> updateMember();
	                case 4 -> deleteMember();
	                case 0 -> { return; }
	                default -> System.out.println("❌ Invalid choice.");
	            }
	        }
	    }
	
	    private void addMember() {
	        System.out.println("\n── Add New Member ──");
	        System.out.print("Name: ");
	        String name = scanner.nextLine();
	        System.out.print("Email: ");
	        String email = scanner.nextLine();
	        System.out.print("Phone: ");
	        String phone = scanner.nextLine();
	        System.out.print("Max Books Allowed: ");
	        int maxBooks = readInt();
	
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
	        System.out.print("\nEnter Member ID to update: ");
	        int id = readInt();
	        Member member = service.getMemberById(id);
	        if (member == null) {
	            System.out.println("❌ Member not found.");
	            return;
	        }
	        System.out.println("Leave blank to keep current value.");
	        System.out.print("Name [" + member.getName() + "]: ");
	        String name = scanner.nextLine();
	        if (!name.isBlank()) member.setName(name);
	
	        System.out.print("Email [" + member.getEmail() + "]: ");
	        String email = scanner.nextLine();
	        if (!email.isBlank()) member.setEmail(email);
	
	        System.out.print("Phone [" + member.getPhone() + "]: ");
	        String phone = scanner.nextLine();
	        if (!phone.isBlank()) member.setPhone(phone);
	
	        System.out.print("Max Books Allowed [" + member.getMaxBooksAllowed() + "]: ");
	        String maxStr = scanner.nextLine();
	        if (!maxStr.isBlank()) member.setMaxBooksAllowed(Integer.parseInt(maxStr));
	
	        boolean result = service.updateMember(member);
	        System.out.println(result ? "✅ Member updated successfully!" : "❌ Failed to update member.");
	    }
	
	    private void deleteMember() {
	        System.out.print("\nEnter Member ID to delete: ");
	        int id = readInt();
	        System.out.print("Are you sure? (yes/no): ");
	        String confirm = scanner.nextLine();
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
	            System.out.print("Enter your choice: ");
	
	            int choice = readInt();
	            switch (choice) {
	                case 1 -> issueBook();
	                case 2 -> returnBook();
	                case 3 -> showAllTransactions();
	                case 0 -> { return; }
	                default -> System.out.println("❌ Invalid choice.");
	            }
	        }
	    }
	
	    private void issueBook() {
	        System.out.println("\n── Issue Book ──");
	        System.out.print("Enter Book ID: ");
	        int bookId = readInt();
	        System.out.print("Enter Member ID: ");
	        int memberId = readInt();
	        String result = service.issueBook(bookId, memberId);
	        System.out.println(result);
	    }
	
	    private void returnBook() {
	        System.out.println("\n── Return Book ──");
	        System.out.print("Enter Transaction ID: ");
	        int transactionId = readInt();
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
	                    t.getDueDate() != null ? t.getDueDate() : "-",
	                    t.getReturnDate() != null ? t.getReturnDate() : "-",
	                    t.getStatus());
	        }
	    }
	
	    // ─────────────────────────────────────────
	    // UTILITY
	    // ─────────────────────────────────────────
	
	    private int readInt() {
	        while (true) {
	            try {
	                int value = Integer.parseInt(scanner.nextLine().trim());
	                return value;
	            } catch (NumberFormatException e) {
	                System.out.print("❌ Please enter a valid number: ");
	            }
	        }
	    }
	}
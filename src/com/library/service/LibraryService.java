package com.library.service;

import com.library.dao.BookDao;
import com.library.dao.MemberDao;
import com.library.dao.TransactionDao;
import com.library.dao.impl.BookDaoImpl;
import com.library.dao.impl.MemberDaoImpl;
import com.library.dao.impl.TransactionDaoImpl;
import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public class LibraryService {

    private final BookDao bookDao = new BookDaoImpl();
    private final MemberDao memberDao = new MemberDaoImpl();
    private final TransactionDao transactionDao = new TransactionDaoImpl();

    // ─────────────────────────────────────────
    // BOOK OPERATIONS
    // ─────────────────────────────────────────

    public boolean addBook(Book book) {
        return bookDao.addBook(book);
    }

    public Book getBookById(int bookId) {
        return bookDao.getBookById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public boolean updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    public boolean deleteBook(int bookId) {
        return bookDao.deleteBook(bookId);
    }

    // ─────────────────────────────────────────
    // MEMBER OPERATIONS
    // ─────────────────────────────────────────

    public boolean addMember(Member member) {
        return memberDao.addMember(member);
    }

    public Member getMemberById(int memberId) {
        return memberDao.getMemberById(memberId);
    }

    public List<Member> getAllMembers() {
        return memberDao.getAllMembers();
    }

    public boolean updateMember(Member member) {
        return memberDao.updateMember(member);
    }

    public boolean deleteMember(int memberId) {
        return memberDao.deleteMember(memberId);
    }

    // ─────────────────────────────────────────
    // TRANSACTION OPERATIONS (with business logic)
    // ─────────────────────────────────────────

    public String issueBook(int bookId, int memberId) {
        // Check book exists and is available
        Book book = bookDao.getBookById(bookId);
        if (book == null) {
            return "❌ Book not found.";
        }
        if (book.getAvailableCopies() <= 0) {
            return "❌ No copies available for this book.";
        }

        // Check member exists
        Member member = memberDao.getMemberById(memberId);
        if (member == null) {
            return "❌ Member not found.";
        }

        // Check member borrowing limit
        long currentlyIssued = transactionDao.getAllTransactions().stream()
                .filter(t -> t.getMemberId() == memberId && "ISSUED".equals(t.getStatus()))
                .count();

        if (currentlyIssued >= member.getMaxBooksAllowed()) {
            return "❌ Member has reached the maximum borrowing limit of " + member.getMaxBooksAllowed() + " books.";
        }

        // Issue the book
        Transaction transaction = new Transaction(
                bookId, memberId,
                LocalDate.now(),
                LocalDate.now().plusDays(14), // 2-week due date
                "ISSUED"
        );
        transactionDao.addTransaction(transaction);

        // Decrease available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookDao.updateBook(book);

        return "✅ Book issued successfully! Due date: " + LocalDate.now().plusDays(14);
    }

    public String returnBook(int transactionId) {
        Transaction transaction = transactionDao.getTransactionById(transactionId);
        if (transaction == null) {
            return "❌ Transaction not found.";
        }
        if ("RETURNED".equals(transaction.getStatus())) {
            return "❌ This book has already been returned.";
        }

        // Mark as returned
        transaction.setReturnDate(LocalDate.now());
        transaction.setStatus("RETURNED");
        transactionDao.updateTransaction(transaction);

        // Increase available copies
        Book book = bookDao.getBookById(transaction.getBookId());
        if (book != null) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookDao.updateBook(book);
        }

        // Check for overdue fine
        if (LocalDate.now().isAfter(transaction.getDueDate())) {
            long overdueDays = LocalDate.now().toEpochDay() - transaction.getDueDate().toEpochDay();
            double fine = overdueDays * 2.0; // ₹2 per day
            return "✅ Book returned successfully.\n⚠️  Overdue by " + overdueDays + " day(s). Fine: ₹" + fine;
        }

        return "✅ Book returned successfully. No fine.";
    }

    public List<Transaction> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }

    public Transaction getTransactionById(int transactionId) {
        return transactionDao.getTransactionById(transactionId);
    }
    
    
}
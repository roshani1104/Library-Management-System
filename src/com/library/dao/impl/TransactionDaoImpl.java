package com.library.dao.impl;

import com.library.dao.TransactionDao;
import com.library.model.Transaction;
import com.library.util.DbUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    @Override
    public boolean addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (book_id, member_id, issue_date, due_date, return_date, status) " +
                     "VALUES (?, ?, ?, ?, ?,?)";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

        	ps.setInt(1, transaction.getBookId());
            ps.setInt(2, transaction.getMemberId());
            ps.setDate(3, Date.valueOf(transaction.getIssueDate()));
            ps.setDate(4, transaction.getDueDate() != null ? Date.valueOf(transaction.getDueDate()) : null); // ← was missing
            ps.setDate(5, transaction.getReturnDate() != null ? Date.valueOf(transaction.getReturnDate()) : null);
            ps.setString(6, transaction.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error adding transaction", e);
        }
    }

    @Override
    public Transaction getTransactionById(int transactionId) {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToTransaction(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching transaction by ID", e);
        }

        return null;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        String sql = "SELECT * FROM transactions";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                transactions.add(mapRowToTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching transactions", e);
        }

        return transactions;
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET book_id=?, member_id=?, issue_date=?, return_date=?, status=? " +
                     "WHERE transaction_id=?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, transaction.getBookId());
            ps.setInt(2, transaction.getMemberId());
            ps.setDate(3, Date.valueOf(transaction.getIssueDate()));
            ps.setDate(4, transaction.getReturnDate() != null ? Date.valueOf(transaction.getReturnDate()) : null);
            ps.setString(5, transaction.getStatus());
            ps.setInt(6, transaction.getTransactionId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating transaction", e);
        }
    }

    // 🔹 Helper method: map ResultSet → Transaction
    private Transaction mapRowToTransaction(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setTransactionId(rs.getInt("transaction_id"));
        t.setBookId(rs.getInt("book_id"));
        t.setMemberId(rs.getInt("member_id"));
        t.setIssueDate(rs.getDate("issue_date").toLocalDate());
        t.setReturnDate(rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null);
        t.setStatus(rs.getString("status"));
        return t;
    }
    
    @Override
    public List<Transaction> getTransactionsByMemberId(int memberId) {
        String sql = "SELECT * FROM transactions WHERE member_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                transactions.add(mapRowToTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching transactions by member ID", e);
        }

        return transactions;
    }
}

package com.library.dao;

import com.library.model.Transaction;
import java.util.List;

public interface TransactionDao {

    boolean addTransaction(Transaction transaction);
    
    Transaction getTransactionById(int transactionId);
    
    List<Transaction> getAllTransactions();
    
    boolean updateTransaction(Transaction transaction);

}

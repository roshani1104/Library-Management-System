package com.library.dao;

import com.library.model.Book;
import java.util.List;

public interface BookDao {

    boolean addBook(Book book);
    
    Book getBookById(int bookId);
    
    List<Book> getAllBooks();
    
    boolean updateBook(Book book);
    
    boolean deleteBook(int bookId);

}

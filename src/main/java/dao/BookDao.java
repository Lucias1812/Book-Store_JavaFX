package dao;

import model.Book;
import java.sql.SQLException;
import java.util.List;

public interface BookDao {
    void setup() throws SQLException;
    List<Book> getAllBooks() throws SQLException;
    void updateBookCopies(String title, int newPhysicalCopies) throws SQLException;
    
    // New method to get physical copies by book title
    int getPhysicalCopiesByTitle(String title) throws SQLException;
    
    void updateBookCopiesAndSold(String title, int newPhysicalCopies, int copiesSoldIncrement) throws SQLException;
}

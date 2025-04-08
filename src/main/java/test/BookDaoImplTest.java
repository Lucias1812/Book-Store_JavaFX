package test;

import dao.BookDao;
import dao.BookDaoImpl;
import model.Book;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;

public class BookDaoImplTest {

    @Test
    public void testGetBookByTitle() throws SQLException {
        BookDao bookDao = new BookDaoImpl();
        Book book = bookDao.getBookByTitle("Effective Java"); // Replace with an actual book title
        assertNotNull(book);
        assertEquals("Effective Java", book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals(45.99, book.getPrice(), 0.01);
    }
}

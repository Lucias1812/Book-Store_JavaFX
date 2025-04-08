package test;

import model.Book;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void testBookAttributes() {
        Book book = new Book("Effective Java", "Joshua Bloch", 20, 45.99, 100);
        
        // Test initial values
        assertEquals("Effective Java", book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertEquals(20, book.getPhysicalCopies());
        assertEquals(45.99, book.getPrice(), 0.01);
        assertEquals(100, book.getCopiesSold());
        
        // Test setting and getting quantity
        book.setQuantity(5);
        assertEquals(5, book.getQuantity());
    }
}

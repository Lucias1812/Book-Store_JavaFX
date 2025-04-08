package test;

import model.Model;
import dao.BookDao;
import dao.BookDaoImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;

public class ModelUpdateTest {

    private Model model;

    @Before
    public void setUp() {
        model = new Model();
    }

    @Test
    public void testUpdateBookCopies() throws SQLException {
        String title = "Effective Java";
        model.updateBookCopies(title, 18); // Update to 18 copies

        // Verify that the update was successful
        BookDao bookDao = new BookDaoImpl();
        int updatedCopies = bookDao.getPhysicalCopiesByTitle(title);
        assertEquals(18, updatedCopies);
    }
}

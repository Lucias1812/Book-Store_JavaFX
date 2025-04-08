package test;

import model.Model;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;

public class ModelTest {

    @Test
    public void testGetAvailableStock() throws SQLException {
        Model model = new Model();
        int stock = model.getAvailableStock("Effective Java"); 
        assertEquals(20, stock); 
    }
}


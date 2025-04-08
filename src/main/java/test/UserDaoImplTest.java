package test;

import dao.UserDao;
import dao.UserDaoImpl;
import org.junit.Test;
import java.sql.SQLException;

public class UserDaoImplTest {

    @Test
    public void testUserSetup() throws SQLException {
        UserDao userDao = new UserDaoImpl();
        userDao.setup();
        // If no exceptions are thrown, setup is considered successful.
    }
}


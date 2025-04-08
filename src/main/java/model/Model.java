package model;

import java.sql.SQLException;

import dao.UserDao;
import dao.UserDaoImpl;
import dao.BookDao;
import dao.BookDaoImpl;

public class Model {
	private UserDao userDao;
	private User currentUser;
	private BookDao bookDao;
	
	public Model() {
		userDao = new UserDaoImpl();
		bookDao = new BookDaoImpl();
	}
	
	public void setup() throws SQLException {
		userDao.setup();
		bookDao.setup();
	}
	public UserDao getUserDao() {
		return userDao;
	}
	
	public BookDao getBookDao() {   
        return bookDao;
    }
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}
	
	public void updateBookCopies(String title, int newPhysicalCopies) throws SQLException {
        bookDao.updateBookCopies(title, newPhysicalCopies);
    }
	
	public int getAvailableStock(String title) throws SQLException {
	    return bookDao.getPhysicalCopiesByTitle(title);
	}
	
	// Update in Model class to handle book updates
    public static void updateBookCopies(String title, int newPhysicalCopies, int copiesSoldIncrement) throws SQLException {
        BookDao bookDao = new BookDaoImpl();
        bookDao.updateBookCopiesAndSold(title, newPhysicalCopies, copiesSoldIncrement);
    }
}

package dao;

import model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private final String TABLE_NAME = "books";

    public BookDaoImpl() {
    }

    @Override
    public void setup() throws SQLException {
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "title VARCHAR(100) NOT NULL, "
                    + "author VARCHAR(100) NOT NULL, "
                    + "physical_copies INTEGER NOT NULL, "
                    + "price REAL NOT NULL, "
                    + "copies_sold INTEGER NOT NULL)";
            stmt.executeUpdate(sql);
        }
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT title, author, physical_copies, price, copies_sold FROM books";

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                int physicalCopies = rs.getInt("physical_copies");
                double price = rs.getDouble("price");
                int copiesSold = rs.getInt("copies_sold");

                books.add(new Book(title, author, physicalCopies, price, copiesSold));
            }
        }
        return books;
    }

    @Override
    public void updateBookCopies(String title, int newPhysicalCopies) throws SQLException {
        String sql = "UPDATE books SET physical_copies = ? WHERE title = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newPhysicalCopies);
            stmt.setString(2, title);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public int getPhysicalCopiesByTitle(String title) throws SQLException {
        String query = "SELECT physical_copies FROM books WHERE title = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("physical_copies");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return 0; // Return 0 if the book is not found
    }
    
    @Override
    public void updateBookCopiesAndSold(String title, int newPhysicalCopies, int copiesSoldIncrement) throws SQLException {
        String query = "UPDATE books SET physical_copies = ?, copies_sold = copies_sold + ? WHERE title = ?";
        
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setInt(1, newPhysicalCopies);
            stmt.setInt(2, copiesSoldIncrement);
            stmt.setString(3, title);
            
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Failed to update book: " + title + ". Book not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";

	public UserDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
	                + "username VARCHAR(10) NOT NULL PRIMARY KEY, "
	                + "password VARCHAR(8) NOT NULL, "
	                + "firstName VARCHAR(50) NOT NULL, "   
	                + "lastName VARCHAR(50) NOT NULL)"; 
			stmt.executeUpdate(sql);
		} 
	}

	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstName"),   // Retrieve firstName
                        rs.getString("lastName")     // Retrieve lastName
                    );
                }
                return null;
			} 
		}
	}

	@Override
    public User createUser(String username, String password, String firstName, String lastName) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (username, password, firstName, lastName) VALUES (?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return new User(username, password, firstName, lastName);
            }
            return null;
        }
    }
	
	@Override
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET firstName = ?, lastName = ?, password = ? WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getUsername());

            stmt.executeUpdate();
        }
    }
}

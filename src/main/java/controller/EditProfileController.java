package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;


import java.io.IOException;
import java.sql.SQLException;

public class EditProfileController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField passwordField;
    @FXML private Button savechange;
    @FXML private Button cancel;
    @FXML private Label statusLabel;

    private Model model;
    private Stage stage;

    public EditProfileController(Stage stage, Model model) {
    	this.stage = stage;
    	this.model = model;
    }

    @FXML
    public void initialize() {
        // Load current user's data into the form fields
        User currentUser = model.getCurrentUser();
        firstNameField.setText(currentUser.getFirstName());
        lastNameField.setText(currentUser.getLastName());
        passwordField.setText(currentUser.getPassword());

        savechange.setOnAction(event -> updateProfile());
    }

    private void updateProfile() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String password = passwordField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
            statusLabel.setText("All fields are required.");
            statusLabel.setTextFill(Color.RED);
            return;
        }

        // Update the current user's data
        User currentUser = model.getCurrentUser();
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setPassword(password);

        try {
            model.getUserDao().updateUser(currentUser);
            statusLabel.setText("Profile updated successfully.");
            statusLabel.setTextFill(Color.GREEN);
        } catch (SQLException e) {
            statusLabel.setText("Error updating profile: " + e.getMessage());
            statusLabel.setTextFill(Color.RED);
        }
    }
    
    @FXML
    public void handleCancel() {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
            
            // Instantiate HomeController with the stage and model
            HomeController homeController = new HomeController((Stage) cancel.getScene().getWindow(), model);
            loader.setController(homeController);

            // Load the HomeView layout and set the scene
            Pane root = loader.load();
            
            // Show the HomeView stage and set the welcome message after loading
            homeController.showStage(root);
            homeController.setWelcomeMessage(model.getCurrentUser().getUsername());
        } catch (IOException e) {
            System.out.println("Error loading HomeView: " + e.getMessage());
        }
    }
    
    public void showStage(VBox root) {
        Scene scene = new Scene(root, 680, 400); 
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Edit Profile");
        stage.show();
    }
  
}

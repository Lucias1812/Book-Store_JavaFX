package controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class SignupController {
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML 
	private TextField firstName;   
    @FXML 
    private TextField lastName;
	@FXML
	private Button createUser;
	@FXML
	private Button close;
	@FXML
	private Label status;
	
	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public SignupController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	@FXML
    public void initialize() {
        createUser.setOnAction(event -> {
            if (!username.getText().isEmpty() && !password.getText().isEmpty() 
                && !firstName.getText().isEmpty() && !lastName.getText().isEmpty()) {
                
                User user;
                try {
                    // Create user with all fields
                    user = model.getUserDao().createUser(username.getText(), password.getText(), 
                                                         firstName.getText(), lastName.getText());
                    if (user != null) {
                        status.setText("Created " + user.getUsername());
                        status.setTextFill(Color.GREEN);
                    } else {
                        status.setText("Cannot create user");
                        status.setTextFill(Color.RED);
                    }
                } catch (SQLException e) {
                    status.setText(e.getMessage());
                    status.setTextFill(Color.RED);
                }
                
            } else {
                status.setText("All fields are required");
                status.setTextFill(Color.RED);
            }
        });

        close.setOnAction(event -> {
            stage.close();
            parentStage.show();
        });
    }
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Sign up");
		stage.show();
	}
}

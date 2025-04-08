package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Model;
import model.User;

public class LoginController {
	@FXML
	private TextField name;
	@FXML
	private PasswordField password;
	@FXML
	private Label message;
	@FXML
	private Button login;
	@FXML
	private Button signup;

	private Model model;
	private Stage stage;
	
	public LoginController(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
	}
	
	@FXML
    public void initialize() {
        login.setOnAction(event -> {
            if (!name.getText().isEmpty() && !password.getText().isEmpty()) {
                try {
                    User user = model.getUserDao().getUser(name.getText(), password.getText());
                    if (user != null) {
                        model.setCurrentUser(user);
                        loadHomeView();
                    } else {
                        message.setText("Incorrect username or password");
                        message.setTextFill(Color.RED);
                    }
                } catch (SQLException e) {
                    if (e.getMessage().contains("no such column: 'firstName'")) {
                        message.setText("Signup first");
                    } else {
                        message.setText(e.getMessage());
                    }
                    message.setTextFill(Color.RED);
                }
            } else {
                message.setText("Username and password required");
                message.setTextFill(Color.RED);
            }
            name.clear();
            password.clear();
        });

        signup.setOnAction(event -> loadSignupView());
    }

    private void loadHomeView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
            HomeController homeController = new HomeController(stage, model);
            loader.setController(homeController);
            Pane root = loader.load();
            homeController.setWelcomeMessage(model.getCurrentUser().getUsername());
            homeController.showStage(root);
        } catch (IOException e) {
            message.setText("Error loading HomeView: " + e.getMessage());
        }
    }

    private void loadSignupView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignupView.fxml"));
            SignupController signupController = new SignupController(stage, model);
            loader.setController(signupController);
            VBox root = loader.load();
            signupController.showStage(root);
        } catch (IOException e) {
            message.setText("Error loading SignupView: " + e.getMessage());
        }
    }
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Welcome");
		stage.show();
	}
}


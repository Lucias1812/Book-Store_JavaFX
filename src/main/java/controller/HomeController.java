package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Book;
import model.Model;

import java.sql.SQLException;
import java.util.List;

public class HomeController {
    @FXML private Label welcomeLabel;
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> copiesColumn;
    @FXML private TableColumn<Book, Double> priceColumn;
    @FXML private TableColumn<Book, Integer> soldColumn;
    @FXML private Button edituser;
    @FXML private Button shopcart;
    @FXML private Button logout;
    private Model model;
    private Stage stage;

    public HomeController(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
    }

    @FXML
    public void initialize() {
        try {
            // Ensure TableView columns are correctly bound to Book properties
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
            copiesColumn.setCellValueFactory(new PropertyValueFactory<>("physicalCopies"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            soldColumn.setCellValueFactory(new PropertyValueFactory<>("copiesSold"));

            // Load book data from database via Model
            loadBookData();
        } catch (Exception e) {
            System.out.println("Error initializing HomeController: " + e.getMessage());
            e.printStackTrace();
        }
        edituser.setOnAction(event -> openEditProfile());
        shopcart.setOnAction(event -> openCart());
        logout.setOnAction(event -> openLog());
        
    }

    private void loadBookData() {
        ObservableList<Book> books = FXCollections.observableArrayList();

        try {
            // Retrieve book list using BookDao via Model
            List<Book> bookList = model.getBookDao().getAllBooks();
            books.addAll(bookList);
            bookTableView.setItems(books);
        } catch (SQLException e) {
            System.out.println("SQL Error loading book data: " + e.getMessage());
            e.printStackTrace();
            // Optionally, show an error message in the UI
        } catch (Exception e) {
            System.out.println("Unexpected error loading book data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void setWelcomeMessage(String username) {
        welcomeLabel.setText("Welcome, " + username + "!");
    }
    
    @FXML
    public void openEditProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditProfileView.fxml"));
            EditProfileController editProfileController = new EditProfileController(stage, model);
            loader.setController(editProfileController);  // Set controller here only once

            VBox root = loader.load();
            editProfileController.showStage(root);
        } catch (IOException e) {
            System.out.println("Error loading EditProfileView: " + e.getMessage());
        } 
    }
    @FXML
    public void openLog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            LoginController loginController = new LoginController(stage, model);
            loader.setController(loginController);  // Set controller here only once

            GridPane root = loader.load();
            loginController.showStage(root);
        } catch (IOException e) {
            System.out.println("Error loading EditProfileView: " + e.getMessage());
        } 
    }
    
    
    @FXML
    private void openCart() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CartView.fxml"));
            CartController cartController = new CartController(stage, model);
            loader.setController(cartController);  // Set controller here only once

            Pane root = loader.load();
            cartController.showStage(root);
        } catch (IOException e) {
            System.out.println("Error loading EditProfileView: " + e.getMessage());
        }
    }

    public void showStage(Pane root) {
        Scene scene = new Scene(root, 680, 400);  // Adjusted dimensions for better display
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Home");
        stage.show();
    }
}

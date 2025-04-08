package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Book;
import model.Model;

import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CartController {
    @FXML private Label infolabel;
    @FXML private TableView<Book> cartTableView;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> copiesColumn;
    @FXML private TableColumn<Book, Double> priceColumn;
    @FXML private TableColumn<Book, Integer> soldColumn;
    @FXML private Button backhome;
    @FXML private Button checkout;
    @FXML private ChoiceBox<Integer> choiceBox1;
    @FXML private ChoiceBox<Integer> choiceBox2;
    @FXML private ChoiceBox<Integer> choiceBox3;
    @FXML private ChoiceBox<Integer> choiceBox4;
    @FXML private ChoiceBox<Integer> choiceBox5;
    @FXML private ChoiceBox<Integer> choiceBox6;
    @FXML private ChoiceBox<Integer> choiceBox7;
    @FXML private ChoiceBox<Integer> choiceBox8;
    @FXML private ChoiceBox<Integer> choiceBox9;
    private Model model;
    private Stage stage;

    public CartController(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
    }
    
    private Map<String, Integer> selectedQuantities = new HashMap<>();
    
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
        
        
     // Configure each ChoiceBox with quantity options and add validation listeners
        configureChoiceBox(choiceBox1, "Absolute Java");
        configureChoiceBox(choiceBox2, "JAVA: How to Program");
        configureChoiceBox(choiceBox3, "Computing Concepts with JAVA 8 Essentials");
        configureChoiceBox(choiceBox4, "Java Software Solutions");
        configureChoiceBox(choiceBox5, "Java Program Design");
        configureChoiceBox(choiceBox6, "Clean Code");
        configureChoiceBox(choiceBox7, "Gray Hat C#");
        configureChoiceBox(choiceBox8, "Python Basics");
        configureChoiceBox(choiceBox9, "Bayesian Statistics The Fun Way");

        
        // Initialize checkout button action
        checkout.setOnAction(e -> handleCheckout());
        backhome.setOnAction(event -> backHome());
    }
    
    private void configureChoiceBox(ChoiceBox<Integer> choiceBox, String bookTitle) {
    	// Populate ChoiceBox with values from 0 to 1000
        ObservableList<Integer> quantityOptions = FXCollections.observableArrayList();
        for (int i = 0; i <= 1000; i++) {
            quantityOptions.add(i);
        }
        choiceBox.setItems(quantityOptions);
        choiceBox.setValue(0); // Default to 0

        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            try {
                int availableStock = model.getAvailableStock(bookTitle); // Get stock from Model

                if (newVal > availableStock) {
                    // Display warning if selected quantity exceeds available stock
                    infolabel.setText("Warning: Selected quantity exceeds available stock for " + bookTitle);
                    
                    // Delay resetting to 0 to ensure label update
                    choiceBox.getSelectionModel().clearAndSelect(0); 
                } else {
                    selectedQuantities.put(bookTitle, newVal);
                    infolabel.setText("sdfdgf"); // Clear warning if selection is valid
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleCheckout() {
        boolean isSuccessful = true;
        
        for (Map.Entry<String, Integer> entry : selectedQuantities.entrySet()) {
            String bookTitle = entry.getKey();
            int selectedQuantity = entry.getValue();
            
            if (selectedQuantity > 0) { // Only proceed if a quantity is selected
                try {
                    int availableStock = model.getAvailableStock(bookTitle);
                    
                    // Double-check if the selected quantity is still valid
                    if (selectedQuantity <= availableStock) {
                        // Update physical copies and copies sold in the database
                        int newPhysicalCopies = availableStock - selectedQuantity;
                        Model.updateBookCopies(bookTitle, newPhysicalCopies, selectedQuantity);
                    } else {
                        infolabel.setText("Error: Insufficient stock for " + bookTitle);
                        isSuccessful = false;
                        break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();                 
                }
            }
        }

        if (isSuccessful) {
            infolabel.setText("Checkout successful! Database updated.");
            clearSelections();
        } else {
            infolabel.setText("Checkout failed. Please review your selections.");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CheckView.fxml"));
            CheckController checkController = new CheckController(stage, model);
            loader.setController(checkController);  // Set controller here only once

            Pane root = loader.load();
            checkController.showStage(root);
        } catch (IOException e) {
            System.out.println("Error loading EditProfileView: " + e.getMessage());
        }
    }

    // Clears all selections after successful checkout
    private void clearSelections() {
        choiceBox1.setValue(0);
        choiceBox2.setValue(0);
        choiceBox3.setValue(0);
        choiceBox4.setValue(0);
        choiceBox5.setValue(0);
        choiceBox6.setValue(0);
        choiceBox7.setValue(0);
        choiceBox8.setValue(0);
        choiceBox9.setValue(0);
        selectedQuantities.clear();
    }

    private void loadBookData() {
        ObservableList<Book> books = FXCollections.observableArrayList();

        try {
            // Retrieve book list using BookDao via Model
            List<Book> bookList = model.getBookDao().getAllBooks();
            books.addAll(bookList);
            cartTableView.setItems(books);
        } catch (SQLException e) {
            System.out.println("SQL Error loading book data: " + e.getMessage());
            e.printStackTrace();
            // Optionally, show an error message in the UI
        } catch (Exception e) {
            System.out.println("Unexpected error loading book data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void backHome() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
            HomeController homeController = new HomeController(stage, model);
            loader.setController(homeController);  // Set controller here only once

            Pane root = loader.load();
            homeController.showStage(root);
        } catch (IOException e) {
            System.out.println("Error loading EditProfileView: " + e.getMessage());
        }
    }
    
    public void showStage(Pane root) {
        Scene scene = new Scene(root, 754, 453);  // Adjusted dimensions for better display
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Cart");
        stage.show();
    }
}

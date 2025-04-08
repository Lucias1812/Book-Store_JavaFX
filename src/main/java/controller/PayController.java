package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Book;
import model.Model;

import java.sql.SQLException;
import java.util.List;

public class PayController {
    @FXML private Label mess_label;
    @FXML private TextField cardname;
    @FXML private TextField cardnumber;
    @FXML private TextField expdate;
    @FXML private TextField cvv;
    @FXML private Button buybutton;
    @FXML private Button home_button;
    private Model model;
    private Stage stage;

    public PayController(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
    }

    @FXML
    public void initialize() {
    	buybutton.setOnAction(event -> handleBuy());
    	home_button.setOnAction(event -> backHome());
    }
    
    @FXML
    private void handleBuy() {
    	
    }
    
    @FXML
    public void backHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
            HomeController homeController = new HomeController(stage, model);
            loader.setController(homeController);  // Set controller here only once

            Pane root = loader.load();
            homeController.showStage(root);
        } catch (IOException e) {
            System.out.println("Error loading homeView: " + e.getMessage());
        } 
    }
    
    public void showStage(Pane root) {
        Scene scene = new Scene(root, 611, 453);  // Adjusted dimensions for better display
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Pay");
        stage.show();
    }
}

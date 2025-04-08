package controller;


import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Book;
import model.Model;


public class CheckController {
    @FXML private Label totalprice;
    @FXML private TableView<Book> checkTableView;
    @FXML private TableColumn<Book, String> titlecolumn;
    @FXML private TableColumn<Book, String> authorcolumn;
    @FXML private TableColumn<Book, Integer> noofcopycolumn;
    @FXML private TableColumn<Book, Double> pricecolumn;
    @FXML private Button paybutton;
    private Model model;
    private Stage stage;
    
   

    public CheckController(Stage stage, Model model) {
    	this.model = model;
        this.stage = stage;
        
    }

    @FXML
    public void initialize() {
    	paybutton.setOnAction(event -> openPay());
    	
    }
    
    @FXML
    private void openPay() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PayView.fxml"));
            PayController payController = new PayController(stage, model);
            loader.setController(payController);  // Set controller here only once

            Pane root = loader.load();
            payController.showStage(root);
        } catch (IOException e) {
            System.out.println("Error loading EditProfileView: " + e.getMessage());
        }
    }

    public void showStage(Pane root) {
        Scene scene = new Scene(root, 754, 453);  // Adjusted dimensions for better display
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Check");
        stage.show();
    }
}

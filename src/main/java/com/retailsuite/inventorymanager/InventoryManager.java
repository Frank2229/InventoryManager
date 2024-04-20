package com.retailsuite.inventorymanager;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class InventoryManager extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // MAIN MENU SETUP
        Label mainMenuLabel = new Label("Inventory Manager");
        mainMenuLabel.getStyleClass().add("menu-header");
        Button addRemoveArticleMainButton = new Button("Add/Remove Article");
        FlowPane mainMenuFlowPane = new FlowPane(addRemoveArticleMainButton);
        mainMenuFlowPane.setAlignment(Pos.CENTER);
        mainMenuFlowPane.setMaxWidth(300);
        mainMenuFlowPane.setHgap(5);
        mainMenuFlowPane.setVgap(5);
        VBox mainMenuVBox = new VBox(mainMenuLabel, mainMenuFlowPane);
        mainMenuVBox.setAlignment(Pos.CENTER);
        mainMenuVBox.setSpacing(20);

        // ADD/REMOVE ARTICLE MENU SETUP

        // SCAN ARTICLE PROMPT
        Label addRemoveArticleScanLabel = new Label("Enter/Scan Article:");
        TextField addRemoveArticleTextField = new TextField();
        HBox addRemoveArticleMenuHBox = new HBox(addRemoveArticleScanLabel, addRemoveArticleTextField);
        addRemoveArticleMenuHBox.setAlignment(Pos.CENTER);
        addRemoveArticleMenuHBox.setSpacing(5);

        // ADD ARTICLE PROMPT
        GridPane addArticleGridPane = new GridPane();
        addArticleGridPane.setAlignment(Pos.CENTER);
        Label addArticleNameLabel = new Label("Product Name:");
        addArticleGridPane.add(addArticleNameLabel,0,0);
        TextField addArticleNameTextField = new TextField();
        addArticleGridPane.add(addArticleNameTextField,1,0);
        Label addArticleUPCLabel = new Label("UPC:");
        addArticleGridPane.add(addArticleUPCLabel,0,1);
        TextField addArticleUPCTextField = new TextField();
        addArticleUPCTextField.setDisable(true);
        addArticleGridPane.add(addArticleUPCTextField,1,1);
        Label addArticleOnHandLabel = new Label("On-Hand:");
        addArticleGridPane.add(addArticleOnHandLabel,0,2);
        TextField addArticleOnHandTextField = new TextField("0");
        addArticleOnHandTextField.setDisable(true);
        addArticleGridPane.add(addArticleOnHandTextField,1,2);
        Label addArticleCostLabel = new Label("Cost:");
        addArticleGridPane.add(addArticleCostLabel,0,3);
        TextField addArticleCostTextField = new TextField();
        addArticleGridPane.add(addArticleCostTextField,1,3);
        Label addArticleRetailLabel = new Label("Retail:");
        addArticleGridPane.add(addArticleRetailLabel,0,4);
        TextField addArticleRetailTextField = new TextField();
        addArticleGridPane.add(addArticleRetailTextField,1,4);
        Label addArticleTaxLabel = new Label("Tax:");
        addArticleGridPane.add(addArticleTaxLabel, 0, 5);
        TextField addArticleTaxTextField = new TextField();
        addArticleGridPane.add(addArticleTaxTextField, 1, 5);
        Button addArticleButton = new Button("Add");
        addArticleGridPane.add(addArticleButton,0,6);
        Label addArticleInvalidLabel = new Label("INVALID");
        addArticleInvalidLabel.setStyle("-fx-text-fill: red;");

        // REMOVE/EDIT ARTICLE PROMPT
        GridPane removeArticleGridPane = new GridPane();
        removeArticleGridPane.setAlignment(Pos.CENTER);
        Label removeArticleNameLabel = new Label("Product Name:");
        removeArticleGridPane.add(removeArticleNameLabel,0,0);
        TextField removeArticleNameTextField = new TextField();
        removeArticleNameTextField.setDisable(true);
        removeArticleGridPane.add(removeArticleNameTextField,1,0);
        Label removeArticleUPCLabel = new Label("UPC:");
        removeArticleGridPane.add(removeArticleUPCLabel,0,1);
        TextField removeArticleUPCTextField = new TextField();
        removeArticleUPCTextField.setDisable(true);
        removeArticleGridPane.add(removeArticleUPCTextField,1,1);
        Label removeArticleOnHandLabel = new Label("Current On-Hand:");
        removeArticleGridPane.add(removeArticleOnHandLabel,0,2);
        TextField removeArticleOnHandTextField = new TextField("0");
        removeArticleOnHandTextField.setDisable(true);
        removeArticleGridPane.add(removeArticleOnHandTextField,1,2);
        Label removeArticleCostLabel = new Label("Cost:");
        removeArticleGridPane.add(removeArticleCostLabel,0,3);
        TextField removeArticleCostTextField = new TextField();
        removeArticleGridPane.add(removeArticleCostTextField,1,3);
        Label removeArticleRetailLabel = new Label("Retail:");
        removeArticleGridPane.add(removeArticleRetailLabel,0,4);
        TextField removeArticleRetailTextField = new TextField();
        removeArticleGridPane.add(removeArticleRetailTextField,1,4);
        Label removeArticleTaxLabel = new Label("Tax:");
        removeArticleGridPane.add(removeArticleTaxLabel, 0, 5);
        TextField removeArticleTaxTextField = new TextField();
        removeArticleGridPane.add(removeArticleTaxTextField, 1, 5);
        Button removeArticleButton = new Button("Remove");
        Button editArticleButton = new Button("Edit");
        Label removeArticleInvalidLabel = new Label("INVALID");
        removeArticleInvalidLabel.setStyle("-fx-text-fill: red;");
        HBox removeArticleButtonHBox = new HBox(removeArticleButton, editArticleButton);
        removeArticleButtonHBox.setAlignment(Pos.CENTER);
        removeArticleButtonHBox.setSpacing(20);
        Button addRemoveArticleMenuBackButton = new Button("Back");
        VBox removeArticleVBox = new VBox(removeArticleGridPane, removeArticleButtonHBox);
        VBox addRemoveArticleMenuVBox = new VBox(addRemoveArticleMenuHBox, addRemoveArticleMenuBackButton);
        addRemoveArticleMenuVBox.setAlignment(Pos.CENTER);
        addRemoveArticleMenuVBox.setSpacing(10);

        // APPLICATION WINDOW SETUP
        StackPane root = new StackPane(mainMenuVBox);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/retailsuite/inventorymanager/Stylesheet.css").toString());
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();

        // BUTTON EVENTS

        addRemoveArticleMainButton.setOnAction(event -> {
            root.getChildren().remove(mainMenuVBox);
            root.getChildren().add(addRemoveArticleMenuVBox);
        });

        addArticleButton.setOnAction(event -> {
            // If all required fields are filled in, add a database entry for the new article.
            // After the entry is made, all text fields are cleared.
            if (addArticleNameTextField.getText().isBlank() || addArticleCostTextField.getText().isBlank() || addArticleRetailTextField.getText().isBlank() || addArticleTaxTextField.getText().isBlank()) addArticleGridPane.add(addArticleInvalidLabel, 1,6);
            else {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retail","root","Musician2229");
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO inventory (Product_Name, UPC, On_Hand, Cost, Retail, Tax) VALUES (?, ?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, addArticleNameTextField.getText());
                    preparedStatement.setString(2, addArticleUPCTextField.getText());
                    preparedStatement.setDouble(3, Double.parseDouble(addArticleOnHandTextField.getText()));
                    preparedStatement.setDouble(4, Double.parseDouble(addArticleCostTextField.getText()));
                    preparedStatement.setDouble(5, Double.parseDouble(addArticleRetailTextField.getText()));
                    preparedStatement.setDouble(6, Double.parseDouble(addArticleTaxTextField.getText()));
                    preparedStatement.execute();
                    addRemoveArticleMenuVBox.getChildren().remove(addArticleGridPane);
                    addArticleNameTextField.clear();
                    addArticleUPCTextField.clear();
                    addArticleOnHandTextField.clear();
                    addArticleCostTextField.clear();
                    addArticleRetailTextField.clear();
                    addArticleTaxTextField.clear();
                    addArticleGridPane.getChildren().remove(addArticleInvalidLabel);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        addRemoveArticleMenuBackButton.setOnAction(event -> {
            root.getChildren().remove(addRemoveArticleMenuVBox);
            root.getChildren().add(mainMenuVBox);
        });

        editArticleButton.setOnAction(event -> {
            // If all required fields are filled in, send new article changes to the database.
            // After the changes are made, all text fields are cleared.
            if (removeArticleCostTextField.getText().isBlank() || removeArticleRetailTextField.getText().isBlank() || removeArticleTaxTextField.getText().isBlank()) removeArticleButtonHBox.getChildren().add(removeArticleInvalidLabel);
            else {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retail", "root", "Musician2229");
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE inventory SET cost = ?, retail = ? , tax = ? WHERE upc = " + removeArticleUPCTextField.getText());
                    preparedStatement.setDouble(1, Double.parseDouble(removeArticleCostTextField.getText()));
                    preparedStatement.setDouble(2, Double.parseDouble(removeArticleRetailTextField.getText()));
                    preparedStatement.setDouble(3, Double.parseDouble(removeArticleTaxTextField.getText()));
                    preparedStatement.execute();
                    addRemoveArticleMenuVBox.getChildren().remove(removeArticleVBox);
                    removeArticleNameTextField.clear();
                    removeArticleUPCTextField.clear();
                    removeArticleOnHandTextField.clear();
                    removeArticleCostTextField.clear();
                    removeArticleRetailTextField.clear();
                    removeArticleTaxTextField.clear();
                    removeArticleButtonHBox.getChildren().remove(removeArticleInvalidLabel);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        removeArticleButton.setOnAction(event -> {
            // Delete database entry for article. Clear all fields afterward.
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retail","root","Musician2229");
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM inventory WHERE upc = " + removeArticleUPCTextField.getText());
                preparedStatement.execute();
                addRemoveArticleMenuVBox.getChildren().remove(removeArticleVBox);
                removeArticleNameTextField.clear();
                removeArticleUPCTextField.clear();
                removeArticleOnHandTextField.clear();
                removeArticleCostTextField.clear();
                removeArticleRetailTextField.clear();
                removeArticleTaxTextField.clear();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // TEXT FIELD EVENTS

        addRemoveArticleTextField.setOnAction(event -> {
            try {
                // Search all inventory table entries.
                // If there is a match, ask whether item should be deleted or edited.
                // If there is no match, allow user to create a new entry.
                boolean doesExist = false;
                addArticleOnHandTextField.setText("0");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retail","root","Musician2229");
                Statement statement = connection.createStatement();
                ResultSet inventoryResultSet = statement.executeQuery("SELECT * FROM inventory");
                String tempNameString = "";
                String tempOnHandString = "";
                while (inventoryResultSet.next()) {
                    if (inventoryResultSet.getString("UPC").equals(addRemoveArticleTextField.getText())) {
                        doesExist = true;
                        tempNameString = inventoryResultSet.getString("Product_Name");
                        tempOnHandString = inventoryResultSet.getString("On_Hand");
                    }
                }
                while (addRemoveArticleMenuVBox.getChildren().size() > 1) addRemoveArticleMenuVBox.getChildren().remove(1);
                if (doesExist) {
                    removeArticleNameTextField.setText(tempNameString);
                    removeArticleOnHandTextField.setText(tempOnHandString);
                    addRemoveArticleMenuVBox.getChildren().addAll(removeArticleVBox, addRemoveArticleMenuBackButton);
                    removeArticleUPCTextField.setText(addRemoveArticleTextField.getText());
                }
                else {
                    addRemoveArticleMenuVBox.getChildren().addAll(addArticleGridPane, addRemoveArticleMenuBackButton);
                    addArticleUPCTextField.setText(addRemoveArticleTextField.getText());
                }
                addRemoveArticleTextField.clear();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) { launch(); }
}
module com.retailsuite.inventorymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.retailsuite.inventorymanager to javafx.fxml;
    exports com.retailsuite.inventorymanager;
}
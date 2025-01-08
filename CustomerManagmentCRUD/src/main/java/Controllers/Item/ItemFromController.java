package Controllers.Item;

import Controllers.Customer.CustomerController;
import Models.Customer;
import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFromController implements Initializable {


    public TextField cusId;
    public TextField cusName;
    public TextField cusAddress;
    public TextField cusSalary;
    public TableView tblCustomer;
    public TableColumn itemIdCol;
    public TableColumn itemDescCol;
    public TableColumn itemPriceCol;
    public TableColumn itemQtyCol;

    public void viewItemOnAction(ActionEvent actionEvent) {
    }

    public void addItemOnAction(ActionEvent actionEvent) {
    }

    public void deleteItemOnAction(ActionEvent actionEvent) {
    }

    public void updateItemOnAction(ActionEvent actionEvent) {
    }

    public void searchItemOnAction(ActionEvent actionEvent) {
    }

    private void loadTable() throws SQLException {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList(ItemController.getInstance().getAll());
        tblCustomer.setItems(itemObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        itemIdCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        itemDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        itemQtyCol.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));


    }
}

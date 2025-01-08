package Controllers.Item;

import Controllers.Customer.CustomerController;
import Models.Customer;
import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemFromController implements Initializable {



    public TableView tblCustomer;
    public TableColumn itemIdCol;
    public TableColumn itemDescCol;
    public TableColumn itemPriceCol;
    public TableColumn itemQtyCol;
    public TextField itemId;
    public TextField itemName;
    public TextField itemPrice;
    public TextField itemQty;

    public void viewItemOnAction(ActionEvent actionEvent) {
    }

    public void addItemOnAction(ActionEvent actionEvent) {

        try {
            if (ItemController.getInstance().saveCustomer(
                    new Item(
                            itemId.getText(),
                            itemName.getText(),
                            Double.parseDouble(itemPrice.getText()),
                            Integer.parseInt(itemQty.getText())
                    )
            )) {
                new Alert(Alert.AlertType.INFORMATION, "Item Added Successfully").show();
                generateItemId();
                itemName.clear();
                itemPrice.clear();
                itemQty.clear();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Item Added Failed").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Please Fill All Empty TEXT Fields.").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
        generateItemId();
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

    private void generateItemId() {
        String generatedId = ItemController.getInstance().generateId();
        int id = Integer.parseInt(generatedId.substring(1));
        String newId = String.format("P%03d", id + 1);
        itemId.setText(newId);
    }



}

package Controllers.Item;

import Controllers.Customer.CustomerController;
import Models.Customer;
import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
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
        try {
            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addItemOnAction(ActionEvent actionEvent) {

        try {
            if (ItemController.getInstance().saveItem(
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

        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure Want to Delete it?.", ButtonType.YES, ButtonType.NO).showAndWait();
        ButtonType buttonType = result.orElse(ButtonType.NO);
        if (buttonType == ButtonType.YES) {
            if (ItemController.getInstance().deleteItem(itemId.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Delete Successful").show();
                try {
                    loadTable();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Delete Failed").show();
            }
        }
        try {
            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public void updateItemOnAction(ActionEvent actionEvent) throws SQLException {

        if (ItemController.getInstance().updateItem(new Item(itemId.getText(), itemName.getText(), Double.parseDouble(itemPrice.getText()),Integer.parseInt(itemQty.getText())))) {
            new Alert(Alert.AlertType.INFORMATION, "Item Update Successful").show();
            try {
                loadTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Item Update Failed").show();
        }


    }

    public void searchItemOnAction(ActionEvent actionEvent) {
        Item item = ItemController.getInstance().searchItem(itemId.getText());
        System.out.println(item);
        if(item == null) {
            new Alert(Alert.AlertType.INFORMATION, "Item Not Found").show();
            return;
        }

        itemName.setText(item.getDescription());
        itemQty.setText(item.getQtyOnHand() + "");
        itemPrice.setText(String.valueOf(item.getUnitPrice()));
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

        // get selected row in table
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTexttoValues((Item) newValue);
            }
        });


    }

    private void setTexttoValues(Item item){
        itemId.setText(item.getCode());
        itemName.setText(item.getDescription());
        itemQty.setText(item.getQtyOnHand() + "");
        itemPrice.setText(item.getUnitPrice() + "");
    }

    private void generateItemId() {
        String generatedId = ItemController.getInstance().generateId();
        int id = Integer.parseInt(generatedId.substring(1));
        String newId = String.format("P%03d", id + 1);
        itemId.setText(newId);
    }



}

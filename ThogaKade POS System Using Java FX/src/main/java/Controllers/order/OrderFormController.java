package Controllers.order;

import Controllers.Customer.CustomerController;
import Controllers.Dashboard.togakadeDashboardFormController;
import Controllers.Item.ItemController;
import Models.Item;
import Models.Order;
import Models.OrderDetail;
import Models.OrderFormOrder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {


    public Label nowTimeLbl;
    public Label nowDateLbl;
    public Label orderIdlbl;
    public Label customerNameLbl;
    public ComboBox customerComboBox;
    public ComboBox itemComboBox1;
    public TextField itemDesctxtField;
    public TextField itemPricetxtField;
    public TextField itemQtyOnHandtxtField;
    public TextField itemQtytxtField;
    public TableView orderTable;
    public TableColumn orderIdcol;
    public TableColumn descCol;
    public TableColumn qtyCol;
    public TableColumn unitPriceCol;
    public TableColumn totalCol;
    public Label TotalTxt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            genarateOrderId();
            loadDateAndTime();
            loadAllCustomerIds();
            loadAllItemCodes();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        itemDesctxtField.setEditable(false);
        itemQtyOnHandtxtField.setEditable(false);
        itemPricetxtField.setEditable(false);

    }

    private void genarateOrderId() throws SQLException {
        String OrderId=OrderController.getInstance().getLastOrderId();
        if (OrderId != null) {
            String strLastDigits = OrderId.substring(1);
            int lastDigits = Integer.parseInt(strLastDigits);
            String lastId=String.format("D%03d",lastDigits+1);
            orderIdlbl.setText(lastId);
        } else {
            orderIdlbl.setText("D001");
        }

    }


    private void loadAllCustomerIds() throws ClassNotFoundException, SQLException {
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        for(String id : CustomerController.getInstance().getAllCustomerIds()) {
            customerIds.add(id);
        }
        customerComboBox.setItems(customerIds);
    }

    private void loadAllItemCodes() throws ClassNotFoundException, SQLException {
        ObservableList<String> itemIds = FXCollections.observableArrayList();

        for (String tempId : ItemController.loadAllItemCodes()) {
            itemIds.add(tempId);
        }
        itemComboBox1.setItems(itemIds);

    }


    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        nowDateLbl.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime cTime = LocalTime.now();
            nowTimeLbl.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void customerComboxOnAction(ActionEvent actionEvent) {
            String customerId = (String) customerComboBox.getSelectionModel().getSelectedItem();
            if (customerId != null) {
                customerNameLbl.setText(CustomerController.searchCustomerforOrderForm(customerId).getName());
            }
    }

    public void itemComboxOnAction(ActionEvent actionEvent) {
        String selectedItem = (String) itemComboBox1.getSelectionModel().getSelectedItem();

        if (selectedItem == null || selectedItem.isEmpty()) {
            System.out.println(" ");
            return;
        }

        Item item = ItemController.searchItemforOrderForm(selectedItem);

        if (item == null) {
            System.out.println("No matching item found for the selected item.");
            return;
        }

        itemDesctxtField.setText(item.getDescription() != null ? item.getDescription() : "N/A");
        itemPricetxtField.setText(item.getUnitPrice() != 0 ? String.valueOf(item.getUnitPrice()) : "0.00");
        itemQtyOnHandtxtField.setText(item.getQtyOnHand() != 0 ? String.valueOf(item.getQtyOnHand()) : "0");
    }

    private final ObservableList<OrderFormOrder> orderObservableList = FXCollections.observableArrayList();

    public void btnAddOnAction(ActionEvent actionEvent) {
        orderIdcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        String qtyText = itemQtytxtField.getText().trim();

        if (qtyText.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please Fill Qty Field").show();
            return;
        }

        String itemId = itemComboBox1.getSelectionModel().getSelectedItem().toString();
        String itemDescription = itemDesctxtField.getText();
        int qty = Integer.parseInt(itemQtytxtField.getText());
        double unitPrice = Double.parseDouble(itemPricetxtField.getText());
        double total = unitPrice * qty;

        boolean itemExists = false;

        for (OrderFormOrder order : orderObservableList) {
            if (order.getId().equals(itemId)) {
                order.setQty(order.getQty() + qty);
                order.setTotal(order.getQty() * order.getUnitprice());
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            OrderFormOrder newOrder = new OrderFormOrder(itemId, itemDescription, qty, unitPrice, total);
            orderObservableList.add(newOrder);
        }

        orderTable.setItems(orderObservableList);
        orderTable.refresh();
        updateTotalTxt();
    }

    private void updateTotalTxt() {
        double grandTotal = 0;

        for (OrderFormOrder order : orderObservableList) {
            grandTotal += order.getTotal();
        }

        TotalTxt.setText(String.format("%.2f", grandTotal));
    }


    public void btnRemoveOnAction(ActionEvent actionEvent) {

        OrderFormOrder selectedOrder = (OrderFormOrder) orderTable.getSelectionModel().getSelectedItem();

        if (selectedOrder != null) {
            orderObservableList.remove(selectedOrder);
            orderTable.setItems(orderObservableList);
            updateTotalTxt();
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select an item to remove").show();
        }


    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, IOException {


        String orderId=orderIdlbl.getText();
        String orderData=nowDateLbl.getText();
        String customerId=customerComboBox.getSelectionModel().getSelectedItem().toString();
        ArrayList<OrderDetail> orderDetailList=new ArrayList<>();

        for (OrderFormOrder order : orderObservableList) {
            String itemCode = order.getId();
            int orderQty = order.getQty();
            double unitPrice = order.getUnitprice();

            OrderDetail orderDetail = new OrderDetail(orderId, itemCode, orderQty, unitPrice);
            orderDetailList.add(orderDetail);
        }

        Order order=new Order(orderId, orderData, customerId, orderDetailList);
        boolean isAdded = OrderController.getInstance().placeOrder(order);
        if(isAdded){
            new Alert(Alert.AlertType.INFORMATION, "Added Success").show();
            genarateOrderId();
            clear();

        }else{
            new Alert(Alert.AlertType.WARNING, "Added Faild").show();

        }
    }

    private void clear(){
        itemComboBox1.getSelectionModel().clearSelection();
        customerComboBox.getSelectionModel().clearSelection();
        itemDesctxtField.clear();
        itemQtyOnHandtxtField.clear();
        itemPricetxtField.clear();
        itemQtytxtField.clear();
        itemQtyOnHandtxtField.clear();
        orderObservableList.clear();
        customerNameLbl.setText("");
        TotalTxt.setText("");
    }
}

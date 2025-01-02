package Controllers.Customer;

import DB.DBConnection;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;



public class CustomerManageMainFormController implements Initializable {
    public TextField cusId;
    public TextField cusName;
    public TextField cusSalary;
    public TextField cusAddress;
    public TableView tblCustomer;
    public TableColumn cusIdCol;
    public TableColumn cusNameCol;
    public TableColumn cusAddressCol;
    public TableColumn cusSalaryCol;


    public void addaCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        try {
            if (CustomerController.getInstance().saveCustomer(new Customer(cusId.getText(), cusName.getText(), cusAddress.getText(), Double.parseDouble(cusSalary.getText())))) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Added Successful").show();
                cusId.clear();
                cusName.clear();
                cusAddress.clear();
                cusSalary.clear();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Added Failed").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Please Fill the All Empty TEXT Fields..").show();
        }

    }

    public void deleteCustomerOnAction(ActionEvent actionEvent) {

        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure Want to Delete it?.", ButtonType.YES, ButtonType.NO).showAndWait();
        ButtonType buttonType = result.orElse(ButtonType.NO);
        if (buttonType == ButtonType.YES) {
            if (CustomerController.getInstance().deleteCustomer(cusId.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Delete Successful").show();
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

    public void updateCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        if (CustomerController.getInstance().updateCustomer(new Customer(cusId.getText(), cusName.getText(), cusAddress.getText(), Double.parseDouble(cusSalary.getText())))) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Update Successful").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer Update Failed").show();
        }
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) {

        Customer customer = CustomerController.getInstance().searchCustomer(cusId.getText());
        cusName.setText(customer.getName());
        cusAddress.setText(customer.getAddress());
        cusSalary.setText(String.valueOf(customer.getSalary()));

    }

    private void loadTable() throws SQLException {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        for (Customer customer : CustomerController.getInstance().getAll()) {
            customerObservableList.add(customer);
        }
        tblCustomer.setItems(customerObservableList);
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        cusNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cusAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        cusSalaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        generateCusId();
        loadTable();

        // get selected row in table
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setTexttoValues((Customer) newValue);
            }
        });

    }
    private void setTexttoValues(Customer customer){
        cusId.setText(customer.getId());
        cusName.setText(customer.getName());
        cusAddress.setText(customer.getAddress());
        cusSalary.setText(String.valueOf(customer.getSalary()));
    }

    public void viewCustomerOnAction(ActionEvent actionEvent) throws SQLException {

    }


    private void generateCusId() {
        String generatedId = CustomerController.getInstance().generateId();
        int id = Integer.parseInt(generatedId.substring(1));
        String newId = String.format("C%03d", id + 1);
        cusId.setText(newId);
    }





}

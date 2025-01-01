package Controllers;

import DB.DBConnection;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void addCustomerOnAction(ActionEvent actionEvent) {
    }

    public void addaCustomerOnAction(ActionEvent actionEvent) {
    }

    public void deleteCustomerOnAction(ActionEvent actionEvent) {
    }

    public void updateCustomerOnAction(ActionEvent actionEvent) {
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) {
    }

    private void loadTable() throws SQLException {
        ObservableList<Customer> customerObservableList= FXCollections.observableArrayList();
        Connection connection= DBConnection.getInstance().getConnection();
        Statement statement= connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select * from customer");

        while(resultSet.next()){
            String idd = resultSet.getString(1);
            String namee = resultSet.getString(2);
            String addresss = resultSet.getString(3);
            Double salaryy= resultSet.getDouble(4);

            Customer customer=new Customer(idd,namee,addresss,salaryy);
            customerObservableList.add(customer);
        }

        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        cusNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        cusAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        cusSalaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        tblCustomer.setItems(customerObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) ->{

            if(t1!=null){
                setTexttoValues((Customer)t1);
            }

        } );

    }
    private void setTexttoValues(Customer customer){
        cusId.setText(customer.getId());
        cusName.setText(customer.getName());
        cusAddress.setText(customer.getAddress());
        cusSalary.setText(String.valueOf(customer.getSalary()));
    }
}

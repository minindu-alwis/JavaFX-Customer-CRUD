package Controllers;

import DB.DBConnection;
import Models.Customer;
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
import java.sql.*;
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



    public void clear(){

        cusId.clear();
        cusName.clear();
        cusAddress.clear();
        cusSalary.clear();

    }

    public void givealert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Adding");
        alert.setHeaderText(null);
        alert.setContentText("Added SuccsessFully !!!!!!");
        alert.show();
    }




    public void addaCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        String id=cusId.getText();
        String name=cusName.getText();
        String address=cusAddress.getText();
        Double salary=Double.parseDouble(cusSalary.getText());

        Connection connection=DBConnection.getInstance().getConnection();

        String sql = "insert into customer (id, name, address, salary) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, address);
        preparedStatement.setDouble(4, salary);

        int addToTable = preparedStatement.executeUpdate();
        if (addToTable > 0) {
            givealert();
            clear();
            loadTable();
        } else {
            clear();
            System.out.println("Failed to add customer.");
        }

        preparedStatement.close();
        connection.close();
    }

    public void deleteCustomerOnAction(ActionEvent actionEvent) {

        Customer selectedCustomer = (Customer) tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            tblCustomer.getItems().remove(selectedCustomer);
        }

    }

    public void updateCustomerOnAction(ActionEvent actionEvent) {

        Customer selectedCustomer = (Customer) tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            try {
                String updatedName = cusName.getText();
                String updatedAddress = cusAddress.getText();
                double updatedSalary = Double.parseDouble(cusSalary.getText());

                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE customer SET name = ?, address = ?, salary = ? WHERE id = ?"
                );
                preparedStatement.setString(1, updatedName);
                preparedStatement.setString(2, updatedAddress);
                preparedStatement.setDouble(3, updatedSalary);
                preparedStatement.setString(4, selectedCustomer.getId());

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    selectedCustomer.setName(updatedName);
                    selectedCustomer.setAddress(updatedAddress);
                    selectedCustomer.setSalary(updatedSalary);
                    tblCustomer.refresh();
                    System.out.println("Customer updated successfully.");
                } else {
                    System.out.println("Update failed. No rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for salary.");
            }
        } else {
            System.out.println("No customer selected for update.");
        }






    }

    public void searchCustomerOnAction(ActionEvent actionEvent) {

        String searchId = cusId.getText();

        if (!searchId.isEmpty()) {
            try {
                // Search in the database
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM customer WHERE id = ?"
                );
                preparedStatement.setString(1, searchId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Display values in text fields
                    cusName.setText(resultSet.getString("name"));
                    cusAddress.setText(resultSet.getString("address"));
                    cusSalary.setText(String.valueOf(resultSet.getDouble("salary")));
                } else {
                    System.out.println("No customer found with ID: " + searchId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



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

    public void viewCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        loadTable();
    }
}

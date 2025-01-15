package Controllers.Customer;

import DB.DBConnection;
import Models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerService{

    private static CustomerController instance;

    public CustomerController() {
    }

    public static CustomerController getInstance() {
        return instance == null ? instance = new CustomerController() : instance;
    }

    public List<Customer> getAll() {
        List<Customer> customerList = new ArrayList<>();
        ResultSet rst = null;
        try {
            rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("Select * from Customer");
            while (rst.next()) {
                String id = rst.getString(1);  // Assuming ID is in the first column
                String name = rst.getString(2);  // Assuming Name is in the second column
                String address = rst.getString(3);  // Assuming Address is in the third column
                double balance = rst.getDouble(4);  // Assuming Balance is in the fourth column
                customerList.add(new Customer(id, name, address, balance));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching customers from database", e);
        } finally {
            if (rst != null) {
                try {
                    rst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return customerList;
    }

    @Override
    public boolean saveCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer (id, name, address, salary) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters
            preparedStatement.setString(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setDouble(4, customer.getSalary());

            // Execute the query
            int result = preparedStatement.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
            throw e; // Re-throw the exception for higher-level handling
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        try {
            PreparedStatement prepareStm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE customer SET name=?,address=?,salary=? WHERE id=?");
            prepareStm.setString(1, customer.getName());
            prepareStm.setString(2, customer.getAddress());
            prepareStm.setDouble(3, customer.getSalary());
            prepareStm.setString(4, customer.getId());
            return prepareStm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String cusId) {
        try {
            return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM customer WHERE id='" + cusId + "'") > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String customerid) {
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT name,address,salary FROM customer WHERE id='" + customerid + "'");
            if (rst.next()) {
                return new Customer(null, rst.getString("name"), rst.getString("address"), rst.getDouble("salary"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public String generateId() {
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1");
            if (rst.next()) {
                return rst.getString("id");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public  ArrayList<String> getAllCustomerIds() throws ClassNotFoundException, SQLException{
        ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("Select id From Customer");
        ArrayList<String> cusids=new ArrayList<>();
        while(rst.next()){
            cusids.add(rst.getString(1));
        }
        return cusids;
    }

    public static Customer searchCustomerforOrderForm(String customerid) {
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT name,address,salary FROM customer WHERE id='" + customerid + "'");
            if (rst.next()) {
                return new Customer(null, rst.getString("name"), rst.getString("address"), rst.getDouble("salary"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

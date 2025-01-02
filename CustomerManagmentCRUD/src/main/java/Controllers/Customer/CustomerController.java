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

    @Override
    public List<Customer> getAll() {
        List<Customer> customerList=new ArrayList<>();
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("Select * from Customer");
            while (rst.next()) {
                customerList.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
            }
            return customerList;
        }catch (SQLException e){
           throw new RuntimeException();
        }
    }

    @Override
    public boolean saveCustomer(Customer customer) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer (id, name, address, salary) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, customer.getId());
        preparedStatement.setString(2, customer.getName());
        preparedStatement.setString(3, customer.getAddress());
        preparedStatement.setDouble(4, customer.getSalary());

        int result = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return result > 0;
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


}

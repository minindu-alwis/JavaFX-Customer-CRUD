package Controllers.Customer;

import Models.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {

    List<Customer> getAll();

    boolean saveCustomer(Customer customer) throws SQLException;

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(String cusId);

    Customer searchCustomer(String customerid);

}

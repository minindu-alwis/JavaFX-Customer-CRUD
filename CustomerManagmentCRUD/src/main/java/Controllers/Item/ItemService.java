package Controllers.Item;

import Models.Customer;
import Models.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {

    List<Item> getAll();

    boolean saveCustomer(Item item) throws SQLException;

    boolean updateItem(Item item) throws SQLException;

    boolean deleteCustomer(String itemid);

    Customer searchCustomer(String itemid);


}

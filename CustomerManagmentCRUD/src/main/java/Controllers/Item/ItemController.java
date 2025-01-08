package Controllers.Item;

import Controllers.Customer.CustomerController;
import DB.DBConnection;
import Models.Customer;
import Models.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements ItemService{

    private static ItemController instance;

    public ItemController() {
    }

    public static ItemController getInstance() {
        return instance == null ? instance = new ItemController() : instance;
    }

    @Override
    public List<Item> getAll() {
        List<Item> itemList = new ArrayList<>();
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("Select * from item");
            while (rst.next()) {
                itemList.add(new Item(rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getInt(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching customers from database", e);
        }
        return itemList;
    }

    @Override
    public boolean saveCustomer(Item item) throws SQLException {
        return false;
    }

    @Override
    public boolean updateCustomer(Item item) {
        return false;
    }

    @Override
    public boolean deleteCustomer(String itemid) {
        return false;
    }

    @Override
    public Customer searchCustomer(String itemid) {
        return null;
    }
}

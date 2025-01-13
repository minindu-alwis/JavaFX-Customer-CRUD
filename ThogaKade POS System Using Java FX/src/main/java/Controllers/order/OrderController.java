package Controllers.order;

import Controllers.Item.ItemController;
import DB.DBConnection;
import Models.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderController implements OrderService {

    private static OrderController instance;

    public static OrderController getInstance() {
        return instance == null ? instance = new OrderController() : instance;
    }

    @Override
    public String getLastOrderId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        return rst.next() ? rst.getString("id") : null;
    }

    @Override
    public boolean placeOrder(Order order) {
        return false;
    }
}

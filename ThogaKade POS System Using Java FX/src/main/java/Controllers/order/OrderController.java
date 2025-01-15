package Controllers.order;

import Controllers.Item.ItemController;
import DB.DBConnection;
import Models.Order;

import java.sql.*;

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
    public boolean placeOrder(Order order) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement("Insert into Orders values(?,?,?)");
            stm.setObject(1, order.getId());
            stm.setObject(2, order.getDate());
            stm.setObject(3, order.getCustomerId());
            boolean isAddedOrder = stm.executeUpdate() > 0;
            if (isAddedOrder) {
                boolean addOrderDetails = OrderDetailController.getInstance().addOrderDetail(order.getOrderDetailList());
                if (addOrderDetails) {
                    boolean updateStock = ItemController.getInstance().updateStock(order.getOrderDetailList());
                    if (updateStock) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

}

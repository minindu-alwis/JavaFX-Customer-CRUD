package Controllers.order;

import Models.Order;

import java.sql.SQLException;

public interface OrderService {
    String getLastOrderId() throws SQLException;
    boolean placeOrder(Order order);

}
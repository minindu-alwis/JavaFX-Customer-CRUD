package Controllers.order;

import Models.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsService {

    boolean addOrderDetail(OrderDetail orderDetail) throws SQLException;
    boolean addOrderDetail(ArrayList<OrderDetail> orderDetailList) throws SQLException;

}

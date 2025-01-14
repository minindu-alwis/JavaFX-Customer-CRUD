package Controllers.order;

import DB.DBConnection;
import Models.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailController implements OrderDetailsService {

    private static OrderDetailController instance;

    public static OrderDetailController getInstance() {
        return instance == null ? instance = new OrderDetailController() : instance;
    }

    @Override
    public boolean addOrderDetail(OrderDetail orderDetail) throws SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Insert into OrderDetail values(?,?,?,?)");
        stm.setObject(1, orderDetail.getOrderId());
        stm.setObject(2, orderDetail.getItemCode());
        stm.setObject(3, orderDetail.getQty());
        stm.setObject(4, orderDetail.getUnitPrice());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean addOrderDetail(ArrayList<OrderDetail> orderDetailList) throws SQLException {
        for (OrderDetail orderDetail : orderDetailList) {
            boolean isAdded = addOrderDetail(orderDetail);
            if(!isAdded){
                return  false;
            }
        }
        return !orderDetailList.isEmpty();
    }



}

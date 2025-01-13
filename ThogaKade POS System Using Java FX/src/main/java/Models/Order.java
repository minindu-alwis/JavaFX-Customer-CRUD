package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString



public class Order {
    private String id;
    private String date;
    private String customerId;
    private ArrayList<OrderDetail> orderDetailList;
}

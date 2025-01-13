package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class OrderFormOrder {

    private String id;
    private String description;
    private int qty;
    private double unitprice;
    private double total;

}

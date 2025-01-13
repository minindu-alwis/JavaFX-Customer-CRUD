package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class Item {
    private String code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;


}

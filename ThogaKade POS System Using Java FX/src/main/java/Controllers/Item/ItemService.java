package Controllers.Item;

import Models.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {

    List<Item> getAll();

    boolean saveItem(Item item) throws SQLException;

    boolean updateItem(Item item) throws SQLException;

    boolean deleteItem(String itemid);

    Item searchItem(String itemid);


}

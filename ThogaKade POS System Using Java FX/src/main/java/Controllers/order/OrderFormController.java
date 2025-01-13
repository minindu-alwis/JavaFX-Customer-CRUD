package Controllers.order;

import Controllers.Customer.CustomerController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class OrderFormController implements Initializable {


    public Label nowTimeLbl;
    public Label nowDateLbl;
    public Label orderIdlbl;
    public Label customerNameLbl;
    public ComboBox customerComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadDateAndTime();
        try {
            loadAllCustomerIds();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private void loadAllCustomerIds() throws ClassNotFoundException, SQLException {
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        for(String id : CustomerController.getAllCustomerIds()) {
            customerIds.add(id);
        }
        customerComboBox.setItems(customerIds);
    }


    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        nowDateLbl.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime cTime = LocalTime.now();
            nowTimeLbl.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void customerComboxOnAction(ActionEvent actionEvent) {
            String customerId = (String) customerComboBox.getSelectionModel().getSelectedItem();
            if (customerId != null) {
                customerNameLbl.setText(CustomerController.searchCustomerforOrderForm(customerId).getName());
            }
    }

}

package Controllers.Dashboard;

import Controllers.order.OrderFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class togakadeDashboardFormController {
    public AnchorPane dashboardAncorPane;
    public Button OrderButtonlbl;

    public togakadeDashboardFormController() throws IOException {
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CustomerManageMainForm.fxml"));
            AnchorPane pane = loader.load();
            dashboardAncorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the Customer Manage Main Form: " + e.getMessage()).show();
            e.printStackTrace();
        }


    }


    public void btnItemFormOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ItemForm.fxml"));
            AnchorPane pane = loader.load();
            dashboardAncorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the Customer Manage Main Form: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }


    public void btnOrderFormAction(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/OrderForm.fxml"));
            AnchorPane pane = loader.load();
            dashboardAncorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the Customer Manage Main Form: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }

    public static void handleOrderButtonClick() {
        System.out.println("Order form refreshed!");

        // Logic to refresh the order form
        refreshOrderForm();
    }

    public static void refreshOrderForm() {
        // Implement your refresh logic here
        System.out.println("Refreshing Order Form...");
        // E.g., clear fields, reload data, etc.
    }

}

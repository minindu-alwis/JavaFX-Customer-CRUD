package Controllers.Dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class togakadeDashboardFormController {
    public AnchorPane dashboardAncorPane;

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
}

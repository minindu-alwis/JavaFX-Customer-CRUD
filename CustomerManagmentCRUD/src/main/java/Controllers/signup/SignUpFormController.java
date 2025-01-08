package Controllers.signup;

import Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {
    public TextField txtNewUserName;
    public TextField txtNewUserPassword;
    public Label userIdDisplay;
    public TextField txtEmail;


    public void btnSingUpOnAction(ActionEvent actionEvent) throws IOException {
        String password=encryptPassword();

        boolean isRegistered = false;
        try {
            isRegistered = SignupController.getInstance().registerUser(
                    new User(
                            userIdDisplay.getText(),
                            txtNewUserName.getText(),
                            txtEmail.getText(),
                            password
                    )
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (isRegistered) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "User Registered Successfully");
            alert.showAndWait();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
                Parent root = loader.load();
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "User Not Registerd ! Try Agin Later !").show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String id= SignupController.getInstance().generateuserId();
        userIdDisplay.setText(id);
    }

    public String encryptPassword(){
        String key="12345";
        BasicTextEncryptor basicTextEncryptor=new BasicTextEncryptor();
        String password=txtNewUserPassword.getText();

        basicTextEncryptor.setPassword(key);
        String encriptpassword=basicTextEncryptor.encrypt(password);
        return encriptpassword;
    }

}

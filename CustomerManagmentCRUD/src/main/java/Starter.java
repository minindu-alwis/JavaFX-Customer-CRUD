import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

public class Starter extends Application {
    @Override
    public void start(Stage stage) throws Exception {

            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/login_form.fxml"))));
            stage.show();



    }

    public static void main(String[] args) {

        String key="12345";
        BasicTextEncryptor basicTextEncryptor=new BasicTextEncryptor();
        String password="icet123";

        basicTextEncryptor.setPassword(key);
        String encriptpassword=basicTextEncryptor.encrypt(password);
        System.out.println(encriptpassword);

        String decriptpassword=basicTextEncryptor.decrypt(encriptpassword);
        System.out.println(decriptpassword);

        launch();


    }



}

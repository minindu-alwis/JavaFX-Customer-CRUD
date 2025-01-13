package Controllers.signup;

import DB.DBConnection;
import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupController implements SignupService {

    private static SignupController instance;

    private SignupController() {}

    public static synchronized SignupController getInstance() {
        return instance == null ? instance = new SignupController() : instance;
    }

    @Override
    public boolean registerUser(User user) throws SQLException {
        PreparedStatement prepareStm = DBConnection.getInstance().getConnection().prepareStatement(
                "INSERT INTO users (username, email, password) VALUES (?, ?, ?)"
        );
        prepareStm.setString(1, user.getUsername());
        prepareStm.setString(2, user.getEmail());
        prepareStm.setString(3, user.getPassword());
        return prepareStm.executeUpdate() > 0;
    }

    @Override
    public String generateuserId() {
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(
                    "SELECT id FROM users ORDER BY id DESC LIMIT 1"
            );
            if (rst.next()) {
                int existId = rst.getInt(1);
                return String.format("C%04d", existId + 1);
            } else {
                return "C0001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

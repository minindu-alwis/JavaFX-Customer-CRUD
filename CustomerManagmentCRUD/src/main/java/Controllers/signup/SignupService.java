package Controllers.signup;


import Models.User;

import java.sql.SQLException;

public interface SignupService {

    boolean registerUser(User newUser) throws SQLException;

    String generateuserId();


}

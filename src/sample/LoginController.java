package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.LoginDTO;

import java.io.IOException;
import java.io.Serializable;


public class LoginController implements Serializable {

    private Main main;

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;

    @FXML
    void loginAction(ActionEvent event) throws IOException {
        String userName = userText.getText();
        String password = passwordText.getText();
        LoginDTO loginDTO = new LoginDTO();
        //main.getNetworkUtil().write("Restaurant");
        loginDTO.setUserName(userName);
        loginDTO.setPassword(password);
        try {
            main.getNetworkUtil().write(loginDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    void setMain(Main main) {
        this.main = main;
    }

}

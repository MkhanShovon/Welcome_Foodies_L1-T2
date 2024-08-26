package server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.LoginDTO;
import util.NetworkUtil;

public class Welcome {
    @FXML
    private Button button;
    Customer main;
    @FXML
    Button searchFood;
    LoginDTO loginDTO;
    NetworkUtil networkUtil;
    @FXML
    private ImageView image;

    public void init(LoginDTO loginDTO, NetworkUtil networkUtil)
    {
        //customerName.setText("Customer Name:" + loginDTO.getUserName());
        this.loginDTO=loginDTO;
        this.networkUtil=networkUtil;
        searchFood.setOnAction(e-> {
            try {
                searchFoodAction();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        Image img = new Image(Customer.class.getResourceAsStream("Welcome.png"));
        image.setImage(img);
        //button.setOnAction(e->logoutAction());
    }
    public void setMain(Customer main) {
        this.main = main;
    }

    @FXML
    void searchFoodAction() throws Exception {
        main.showHomePage(loginDTO,networkUtil);
    }
    @FXML
    void logoutAction(ActionEvent event) {
        try {
            main.showLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

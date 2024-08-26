package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import server.Restaurant;
import util.NetworkUtil;
import java.io.IOException;

public class Main extends Application {
    HomeController homeController;
    MenuList menuList;

    private Stage stage;
    private Stage detailStage;
    private NetworkUtil networkUtil;

    public Stage getStage() {
        return stage;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        detailStage=new Stage();
        connectToServer();
        showLoginPage();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 55555;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReadThread(this);
    }

    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Loading the controller
        LoginController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 400, 250));
        stage.show();
    }

    public void showHomePage(Restaurant restaurant,NetworkUtil networkUtil) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        // Loading the controller
        HomeController controller = loader.getController();
        controller.init(restaurant,networkUtil);
        controller.setMain(this);
        this.homeController=controller;

        // Set the primary stage
        stage.setTitle("Restaurant");
        stage.setScene(new Scene(root, 700, 700));
        stage.show();
    }

    public void showDetails(Restaurant restaurant,NetworkUtil networkUtil) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MenuList.fxml"));
        Parent root = loader.load();

        // Loading the controller
        MenuList controller = loader.getController();
        controller.init(restaurant,networkUtil);
        controller.setMain(this);
        this.menuList=controller;

        // Set the primary stage
        detailStage.setTitle("Restaurant Details");
        detailStage.setScene(new Scene(root, 494, 400));
        detailStage.show();
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        // This will launch the JavaFX application
        launch(args);
    }
    HomeController getHomeController()
    {
        return this.homeController;
    }

    MenuList getMenuList()
    {
        return  this.menuList;
    }
}

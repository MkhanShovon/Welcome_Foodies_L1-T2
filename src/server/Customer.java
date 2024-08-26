//package server;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.stage.Stage;
//import server.Restaurant;
//import util.LoginDTO;
//import util.NetworkUtil;
//import java.io.IOException;
//
//public class Customer extends Application {
//
//    private Stage stage;
//    private NetworkUtil networkUtil;
//
//    public Stage getStage() {
//        return stage;
//    }
//
//    public NetworkUtil getNetworkUtil() {
//        return networkUtil;
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        stage = primaryStage;
//        connectToServer();
//        showLoginPage();
//    }
//
//    private void connectToServer() throws IOException {
//        String serverAddress = "127.0.0.1";
//        int serverPort = 44333;
//        networkUtil = new NetworkUtil(serverAddress, serverPort);
//        new ReadThreadCustomer(this);
//    }
//
//    public void showLoginPage() throws Exception {
//        // XML Loading using FXMLLoader
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("customerLogin.fxml"));
//        Parent root = loader.load();
//
//        // Loading the controller
//        CustomerLogin controller = loader.getController();
//        controller.setMain(this);
//
//        // Set the primary stage
//        stage.setTitle("Login");
//        stage.setScene(new Scene(root, 400, 250));
//        stage.show();
//    }
//
//    public void showHomePage(LoginDTO loginDTO) throws Exception {
//
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("customerHome.fxml"));
//        Parent root = loader.load();
//
//        // Loading the controller
//        CustomerHomeController controller = loader.getController();
//        controller.init(loginDTO);
//        controller.setMain(this);
//
//        // Set the primary stage
//        stage.setTitle("Customer");
//        stage.setScene(new Scene(root, 700, 700));
//        stage.show();
//    }
//
//    public void showAlert() {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Incorrect Credentials");
//        alert.setHeaderText("Incorrect Credentials");
//        alert.setContentText("The username and password you provided is not correct.");
//        alert.showAndWait();
//    }
//
//    public static void main(String[] args) {
//        // This will launch the JavaFX application
//        launch(args);
//    }
//}
package server;

import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import server.CustomerHomeController;
import server.CustomerLogin;
import util.LoginDTO;
import util.NetworkUtil;
import java.io.IOException;
import java.util.ArrayList;

public class Customer extends Application {
    CustomerHomeController customerHomeController;
    Welcome welcome;
    SearchedTable searchedTable;
    ConfirmationTable confirmationTable;
    private ListProperty<String> orderedItems = new SimpleListProperty<>(FXCollections.observableArrayList());


    private Stage stage;
    private Stage homeStage;
    private Stage cmeStage;

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
        homeStage = new Stage();
        cmeStage=new Stage();
        connectToServer();
        showLoginPage();
        //showSearchFood();
    }

    private void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 55555;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReadThreadCustomer(this);
    }

    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CustomerLogin.fxml"));
        Parent root = loader.load();

        // Loading the controller
        CustomerLogin controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Customer Login");
        stage.setScene(new Scene(root, 400, 250));
        stage.show();
    }

    public void showHomePage(LoginDTO loginDTO,NetworkUtil networkUtil) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CustomerHome.fxml"));
        Parent root = loader.load();

        // Loading the controller
        CustomerHomeController controller = loader.getController();
        this.customerHomeController=controller;
        controller.init(loginDTO,networkUtil);
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle(loginDTO.getUserName()+"'s Home");
        stage.setScene(new Scene(root, 926, 709));
        stage.show();
    }

    public void showWelcome(LoginDTO loginDTO,NetworkUtil networkUtil) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Welcome.fxml"));
        Parent root = loader.load();

        // Loading the controller
        Welcome controller = loader.getController();
        this.welcome=controller;
        controller.init(loginDTO,networkUtil);
        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Welcome To Food Delivery App");
        stage.setScene(new Scene(root, 600.0, 600.0));
        stage.show();
    }

    public void showSearched(ArrayList<Food>foods,LoginDTO loginDTO, NetworkUtil networkUtil) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Searched.fxml"));
        Parent root = loader.load();

        // Loading the controller
        SearchedTable controller = loader.getController();
        this.searchedTable=controller;
        controller.init(foods,loginDTO,networkUtil);
        controller.setMain(this);

        // Set the primary stage
        homeStage.setTitle("Searched Food Items");
        homeStage.setScene(new Scene(root, 600.0, 700.0));
        homeStage.show();
    }

    public void showConfirm(ListProperty<String> orderedItems,LoginDTO loginDTO, NetworkUtil networkUtil) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ConfirmationTable.fxml"));
        Parent root = loader.load();

        // Loading the controller
        ConfirmationTable controller = loader.getController();
        this.confirmationTable=controller;
        controller.init(orderedItems,loginDTO,networkUtil);
        controller.setMain(this);

        // Set the primary stage
        cmeStage.setTitle("Confirm to Order Food Items");
        cmeStage.setScene(new Scene(root, 600.0, 700.0));
        cmeStage.show();
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

    CustomerHomeController getCustomerHomeController()
    {
        return customerHomeController;
    }
    Welcome getWelcome()
    {
        return this.welcome;
    }
    SearchedTable getSearchedTable()
    {
        return this.searchedTable;
    }
    public ConfirmationTable getConfirmationTable()
    {
        return this.confirmationTable;
    }
    ListProperty<String>getOrderedItems()
    {
        return this.orderedItems;
    }

//    public void showSearchFood() throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("searchFood.fxml"));
//        Parent root = loader.load();
//
//        // Loading the controller
//        SearchFoodController controller = loader.getController();
//        controller.init();
//        //controller.init(loginDTO);
//        //controller.setMain(this);
//
//        // Set the primary stage
//        stage.setTitle("Customer Home");
//        stage.setScene(new Scene(root, 921, 687));
//        stage.show();
//    }
}

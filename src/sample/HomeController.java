package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.Food;
import server.Restaurant;
import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;


public class HomeController {

    @FXML
            Button menu;
    Restaurant restaurant;
    Food orderedFood;
    NetworkUtil networkUtil;

    private Main main;

    @FXML
    private Label message;
    @FXML
    TableColumn<Food,String>orderName;
    @FXML
    TableColumn<Food, Double>orderPrice;
    @FXML
    TableColumn<Food,String>orderCat;
    @FXML
    TableView<Food>orderTable=new TableView<>();

    @FXML
    private ImageView image;

    @FXML
    private Button button;
    @FXML
    Button deliver;
    public HomeController()
    {
        orderName=new TableColumn<Food,String>();
        orderPrice=new TableColumn<Food,Double>();
        orderCat=new TableColumn<Food,String>();
        orderCat.setCellValueFactory(new PropertyValueFactory<>("category"));
        orderName.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    //@FXML
    //private Label categoryLabel;

    /* @FXML
    private TableView<Food> foodTable;

    @FXML
    private TableColumn<Food, String> foodTypeColumn;

    @FXML
    private TableColumn<Food, String> foodNameColumn;*/






    public void init(Restaurant restaurant,NetworkUtil networkUtil) {
        this.restaurant=restaurant;
        this.networkUtil=networkUtil;
        StringBuilder restaurantInfo = new StringBuilder();
        restaurantInfo.append("Restaurant ID: ").append(restaurant.getId()).append("\n");
        restaurantInfo.append("Restaurant Name: ").append(restaurant.getName()).append("\n");
        restaurantInfo.append("Restaurant Score: ").append(restaurant.getScore()).append("\n");
        restaurantInfo.append("Restaurant Price: ").append(restaurant.getPrice()).append("\n");
        restaurantInfo.append("Restaurant Zip Code: ").append(restaurant.getZipCode()).append("\n");


        /*StringBuilder categories = new StringBuilder("\nCategories: ");
        for (String category : restaurant.getCategory()) {
            categories.append(category).append(", ");
        }
        // Remove the trailing comma and space
        categories.delete(categories.length() - 2, categories.length());
        categoryLabel.setText(categories.toString());*/
        String details = restaurantInfo.toString();//+categories.toString();
        //String details="Restaurant ID: " +(String) restaurant.getId()\n;
        message.setText(details);

        //System.out.println(restaurant.showFoods());
        //restaurant.showFoods();
        //foodListView.setItems(FXCollections.observableArrayList(restaurant.getFoods()));


        // Populate the TableView with food items
        //foodTypeColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        //foodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //foodTable.setItems(foodList);
        Image img = new Image(Main.class.getResourceAsStream(restaurant.getName()+".png"));
        image.setImage(img);
        menu.setOnAction(e-> {
            try {
                detailsAction();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    @FXML
    void logoutAction(ActionEvent event) {
        try {
            main.showLoginPage();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setMain(Main main) {
        this.main = main;
    }


    ArrayList<Food> foods = new ArrayList<>();
    public void updateOrder(Food f) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                foods.add(f);
                orderedFood=f;
                for(var it:foods){
                    System.out.println(it.getName());
                }
                System.out.println(f.getName());
                ObservableList<Food> foodList = FXCollections.observableArrayList(foods);
                orderName.setCellValueFactory(new PropertyValueFactory<>("name"));
                orderCat.setCellValueFactory(new PropertyValueFactory<>("category"));
                orderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
//                orderName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
//                orderPrice.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getPrice())));
//                orderCat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

              orderTable.setItems(foodList);
            }
        });
    }

    @FXML
    void deliverAction() throws IOException {

        ObservableList<Food> emptyList = FXCollections.observableArrayList();
        orderTable.setItems(emptyList);
        for(var i:foods)
        {
            networkUtil.write(i.getCustomerName()+"!"+i.getRestaurantId()+","+i.getCategory()+","+i.getName()+","+i.getPrice()+";"+i.getName()+" received from "+this.restaurant.getName());
        }
        foods.clear();
    }

    @FXML
    void detailsAction() throws Exception {
        main.showDetails(restaurant,networkUtil);
    }

}

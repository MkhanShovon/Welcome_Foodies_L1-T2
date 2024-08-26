package server;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import server.Customer;
import util.LoginDTO;
import util.NetworkUtil;

import java.io.IOException;
import java.net.URL;
import java.time.format.TextStyle;
import java.util.*;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class CustomerHomeController  {

    @FXML
    private ImageView image;
    @FXML
    Label receive;
    @FXML
    Button order;


    ArrayList<Restaurant> restaurants;
    //ArrayList<String>orderedFoods=new ArrayList<>();


    int choice;
    @FXML
    Label userName;
    @FXML
    Label message;
    @FXML
    Label message2;
    @FXML
    TextField text1;
    @FXML
    TextField text2;
    @FXML
    TextField text3;
    @FXML
    MenuButton searchFood = new MenuButton();
    @FXML
    MenuItem sfName = new MenuItem("By Name");
    @FXML
    MenuItem sfNameR = new MenuItem("By Name in a Given Restaurant");
    @FXML
    MenuItem sfCategory = new MenuItem("By Category");
    @FXML
    MenuItem sfCategoryR = new MenuItem("By Category in a Given Restaurant");
    @FXML
    MenuItem sfPrice = new MenuItem("By Price Range");
    @FXML
    MenuItem sfPriceR = new MenuItem("By Price Range in a Given Restaurant");
    @FXML
    MenuItem sfCostly = new MenuItem("Costliest Food Item(s) on the Menu in a Given Restaurant");

    LoginDTO loginDTO;

    @FXML
    private ListView<String> listView = new ListView<>();
    private Customer main;

    @FXML
    TextField foodText;
    @FXML
    TextField restaurantText;

    @FXML
    private Button button;

    @FXML
    private Button search;

    SearchingFood searchingFood = new SearchingFood();
    ArrayList<Food> foods = searchingFood.getFoods();
    ArrayList<Food> foundFoods = new ArrayList<>();
    Map<String, ArrayList<Food>> categories = new HashMap<String, ArrayList<Food>>();
    //ArrayList<Restaurant> restaurants = searchingFood.getRestaurants();
    ArrayList<Restaurant> foundRestaurants = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    boolean equals(String s1, String s2) {
        String s11 = s1.toLowerCase();
        String s22 = s2.toLowerCase();
        return s11.contains(s22);
    }

    NetworkUtil networkUtil;


    public void init(LoginDTO loginDTO, NetworkUtil networkUtil) {

        this.networkUtil = networkUtil;
        //message.setText(loginDTO.getUserName());
        this.loginDTO = loginDTO;



        ///this.networkUtil=networkUtil;
        this.restaurants = loginDTO.getRestaurants();

        // receive.setText("No Food received");
        text2.setVisible(false);
        text1.setVisible(false);
        text3.setVisible(false);
        searchFood.getItems().addAll(sfName, sfNameR, sfCategory, sfCategoryR, sfPrice, sfPriceR, sfCostly);
        sfName.setOnAction(e -> nameAction());
        //search.setOnAction(e->sea(foundFoods));
        sfNameR.setOnAction(e -> nameRAction());
        sfCategory.setOnAction(e -> categoryAction());
        sfCategoryR.setOnAction(e -> categoryRAction());
        sfPrice.setOnAction(e -> priceAction());
        sfPriceR.setOnAction(e -> priceRAction());
        sfCostly.setOnAction(e -> costlyAction());

        listView.setVisible(false);


        Image img = new Image(Customer.class.getResourceAsStream("food-delivery-app" + ".png"));
        image.setImage(img);

//            ObservableList names = FXCollections.observableArrayList();
//            names.addAll(
//                    "Adam", "Alex", "Alfred", "Albert",
//                    "Brenda", "Connie", "Derek", "Donny",
//                    "Lynne", "Myrtle", "Rose", "Rudolph",
//                    "Tony", "Trudy", "Williams", "Zach"
//            );
//
//            listView.setItems(names);
//
//            listView.getSelectionModel().selectedItemProperty().addListener(
//                    (observableValue, oldValue, newValue) -> {
//                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
//                        a.setContentText(newValue);
//                        a.showAndWait();
//                    }
//            );
    }

    @FXML
    private void nameAction() {
        if (text3.isVisible()) {
            text3.setVisible(false);
            text3.clear();
        }
        if (text2.isVisible()) {
            text2.setVisible(false);
        }
        if (text1.isVisible()) {
            //text1.setVisible(false);
            text1.clear();
        } else {
            text1.setVisible(true); // Show the TextField
        }
        text1.setPromptText("Enter a Food name"); // Set the floating text
        choice = 1;
//        String foodName=text1.getText();
//
//        this.foundFoods.clear();
//        this.foundFoods=searchingFood.searchByName(foodName);
//        for(var i:foundFoods)
//        {
//            System.out.println(i.getName());
//        }
    }

    @FXML
    private void nameRAction() {
        if (text3.isVisible()) {
            text3.setVisible(false);
            text3.clear();
        }
        if (text2.isVisible()) {
            text2.clear();
        } else {
            text2.setVisible(true);
        }
        if (text1.isVisible()) {
            //text1.setVisible(false);
            text1.clear();
        } else {
            text1.setVisible(true); // Show the TextField
        }
        text2.setPromptText("Enter a Restaurant name");
        text1.setPromptText("Enter a Food name"); // Set the floating text
        choice = 2;

//        String foodName=text1.getText();
//        String restaurantName=text2.getText();
//        this.foundFoods=searchingFood.searchByNameGivenRestaurant(foodName,restaurantName);
    }

    @FXML
    void categoryAction() {

        if (text2.isVisible()) {
            text2.setVisible(false);
        }
        if (text3.isVisible()) {
            text3.setVisible(false);
            text3.clear();
        }
        if (text1.isVisible()) {
            //text1.setVisible(false);
            text1.clear();
        } else {
            text1.setVisible(true); // Show the TextField
        }
        text1.setPromptText("Enter a Category name"); // Set the floating text
        choice = 3;
//        String category=text1.getText();
//        this.foundFoods.clear();
//        this.foundFoods=searchingFood.searchByCategory(category);
    }

    @FXML
    void categoryRAction() {
        if (text3.isVisible()) {
            text3.setVisible(false);
            text3.clear();
        }
        if (text2.isVisible()) {
            text2.clear();
        } else {
            text2.setVisible(true);
        }
        if (text1.isVisible()) {
            //text1.setVisible(false);
            text1.clear();
        } else {
            text1.setVisible(true); // Show the TextField
        }
        text2.setPromptText("Enter a Restaurant name");
        text1.setPromptText("Enter a Category name"); // Set the floating text
        choice = 4;
    }

    @FXML
    void priceAction() {
        if (text3.isVisible()) {
            text3.setVisible(false);
            text3.clear();
        }
        if (text2.isVisible()) {
            text2.clear();
        } else {
            text2.setVisible(true);
        }
        if (text1.isVisible()) {
            //text1.setVisible(false);
            text1.clear();
        } else {
            text1.setVisible(true); // Show the TextField
        }
        text2.setPromptText("Price range");
        text1.setPromptText("Price range"); // Set the floating text
        choice = 5;
    }

    @FXML
    void priceRAction() {
        if (text2.isVisible()) {
            text2.clear();
        } else {
            text2.setVisible(true);
        }
        if (text1.isVisible()) {
            //text1.setVisible(false);
            text1.clear();
        } else {
            text1.setVisible(true); // Show the TextField
        }
        if (text3.isVisible()) {
            text3.clear();
        } else {
            text3.setVisible(true);
        }
        text2.setPromptText("Price range");
        text1.setPromptText("Price range"); // Set the floating text
        text3.setPromptText("Enter a Restaurant name");
        choice = 6;
    }

    @FXML
    void costlyAction() {
        if (text3.isVisible()) {
            text3.setVisible(false);
            text3.clear();
        }
        if (text2.isVisible()) {
            text2.setVisible(false);
            text3.clear();
        }
        if (text1.isVisible()) {
            //text1.setVisible(false);
            text1.clear();
        } else {
            text1.setVisible(true); // Show the TextField
        }
        text1.setPromptText("Enter a Restaurant name"); // Set the floating text
        choice = 7;
    }

    @FXML
    void logoutAction(ActionEvent event) {
        try {
            main.showWelcome(loginDTO,networkUtil);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMain(Customer main) {
        this.main = main;
    }

    @FXML
    public void searchAction(ActionEvent event) throws Exception {
        // Your code for handling the search action here
//        String foodName=foodText.getText();
//        String restaurantName=restaurantText.getText();
//        System.out.println(foodName);
//        System.out.println(restaurantName);
//        if(!foodName.isEmpty() && !restaurantName.isEmpty())
//        {
//            this.foundFoods=searchingFood.searchByNameGivenRestaurant(foodName,restaurantName);
//        }
//        else if(!foodName.isEmpty() && restaurantName.isEmpty())
//        {
//            this.foundFoods=searchingFood.searchByName(foodName);
//        }
//        for(var i:foundFoods)
//        {
//            System.out.println(i.getName());
//        }
//
        foundFoods.clear();
        //message.setVisible(true);
        //listView.setVisible(true);
        if (choice == 1) {
            String name = text1.getText();
            this.foundFoods = searchingFood.searchByName(name);
        } else if (choice == 2) {
            String fname = text1.getText();
            String rname = text2.getText();
            this.foundFoods = searchingFood.searchByNameGivenRestaurant(fname, rname);
        } else if (choice == 3) {
            String category = text1.getText();
            //String s2=text2.getText();
            this.foundFoods = searchingFood.searchByCategory(category);
        } else if (choice == 4) {
            String category = text1.getText();
            String rName = text2.getText();
            this.foundFoods = searchingFood.searchByCategoryGivenRestaurant(category, rName);
        } else if (choice == 5) {
            String p1 = text1.getText();
            String p2 = text2.getText();
            this.foundFoods = searchingFood.searchByPrice(parseDouble(p1), parseDouble(p2));
        } else if (choice == 6) {
            String p1 = text1.getText();
            String p2 = text2.getText();
            String rName = text3.getText();
            this.foundFoods = searchingFood.searchByPriceGivenRestaurant(parseDouble(p1), parseDouble(p2), rName);
        } else if (choice == 7) {
            String rName = text1.getText();
            this.foundFoods.add(searchingFood.costliestFood(rName));
        }
        //updateListView(this.foundFoods);
        main.showSearched(foundFoods,loginDTO,networkUtil);
    }



    ArrayList<Food>getFoundFoods()
    {
        return this.foundFoods;
    }
}

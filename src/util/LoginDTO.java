package util;

import server.Food;
import server.Restaurant;
import server.SearchingFood;
import server.SearchingRestaurant;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginDTO implements Serializable {

    ArrayList<String>foods;
    ArrayList<Restaurant>restaurants;
    public void setRestaurants(ArrayList<Restaurant>restaurants)
    {
        this.restaurants=restaurants;
    }

    public ArrayList<Restaurant> getRestaurants()
    {
        return  this.restaurants;
    }

    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setFoods(ArrayList<String>foods)
    {
        this.foods=foods;
    }

    public ArrayList<String> getFoods()
    {
        return this.foods;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant=restaurant;
    }

    public Restaurant getRestaurant()
    {
        return this.restaurant;
    }

    public void setSearchingFood(SearchingFood searchingFood)
    {
        this.searchingFood=searchingFood;
    }

    public void setSearchingRestaurant(SearchingRestaurant searchingRestaurant)
    {
        this.searchingRestaurant=searchingRestaurant;
    }

    public SearchingRestaurant getSearchingRestaurant()
    {
        return this.searchingRestaurant;
    }

    public SearchingFood getSearchingFood()
    {
        return this.searchingFood;
    }

    private String userName;
    private String password;
    private Restaurant restaurant;
    private boolean status;
//    ArrayList<Food>foods;
//    ArrayList<Restaurant>restaurants;

    SearchingFood searchingFood;
    SearchingRestaurant searchingRestaurant;

//    public void setFoods(ArrayList<Food>foods)
//    {
//        this.foods=foods;
//    }
//
//    public void setRestaurants(ArrayList<Restaurant>restaurants)
//    {
//        this.restaurants=restaurants;
//    }
//
//    public ArrayList<Food> getFoods()
//    {
//        return this.foods;
//    }
//
//    public ArrayList<Restaurant> getRestaurants()
//    {
//        return this.restaurants;
//    }
}

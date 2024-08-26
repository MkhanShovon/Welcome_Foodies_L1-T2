package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class MainMenu {
    Server f = new Server();
    ArrayList<Restaurant> restaurants = f.getRestaurants();
    ArrayList<Food> foods = f.getFoods();
    private static final String INPUT_FILE_NAME = "restaurant.txt";
    private static final String INPUT_FILE_NAME2 = "menu.txt";

    boolean equals(String s1, String s2) {
        String s11 = s1.toLowerCase();
        String s22 = s2.toLowerCase();
        return s11.contains(s22);
    }

    void showMenu() throws IOException {

        // ArrayList<Restaurant>foundrestaurants= new ArrayList<>();
        SearchingRestaurant searchingRestaurant = new SearchingRestaurant();
        SearchingFood searchingFood = new SearchingFood();

        String choice = "";
        while (!choice.equals("5")) {
            System.out.println("1) Search Restaurants\n" + "2) Search Food Items\n" + "3) Add Restaurant\n"
                    + "4) Add Food Item to the Menu\n" + "5) Exit System");

            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.print("Enter choice:");
            choice = sc.nextLine();
            if (choice.equals("1")) {
                searchingRestaurant.searchRestaurant();
            } else if (choice.equals("2")) {
                searchingFood.searchFood();
            }

            else if (choice.equals("3")) {
                System.out.print("Enter restaurantId: ");
                String restaurantId = sc.nextLine();
                System.out.print("Enter restaurantName: ");
                String restaurantName = sc.nextLine();
                System.out.print("Enter score: ");
                String score = sc.nextLine();
                System.out.print("Enter price: ");
                String price = sc.nextLine();
                System.out.print("Enter Zip Code: ");
                String zipCode = sc.nextLine();
                System.out.println("Enter categories: ");
                String category;
                ArrayList<String> categories = new ArrayList<>();
                for (int i = 1; i <= 3; i++) {
                    System.out.print("Enter Category" + i + ": ");
                    category = sc.nextLine();
                    if (!category.equals("")) {
                        categories.add(category);
                    }

                }
                addRestaurant(parseInt(restaurantId), restaurantName, parseDouble(score), price, zipCode, categories);
                System.out.println("Restaurant added successfully!!");
                BufferedWriter writer = new BufferedWriter(new FileWriter(INPUT_FILE_NAME));
                for (var i : restaurants) {
                    String input = i.getId() + "," + i.getName() + "," + i.getScore() + "," + i.getPrice() + ","
                            + i.getZipCode();
                    for (int j = 0; j < i.getCategory().size(); j++) {
                        input += "," + i.getCategory().get(j);
                    }
                    writer.write(input);
                    writer.write(System.lineSeparator());
                }
                writer.close();
            }

            else if (choice.equals("4")) {
                System.out.print("Enter restaurantName: ");
                String restaurantName = sc.nextLine();
                System.out.print("Enter restaurantId: ");
                String restaurantId = sc.nextLine();
                System.out.print("Enter category: ");
                String category = sc.nextLine();
                System.out.print("Enter FoodName: ");
                String foodName = sc.nextLine();
                System.out.print("Enter price: ");
                String price = sc.nextLine();
                if (addFood(restaurantName, parseInt(restaurantId), category, foodName, parseDouble(price))) {
                    System.out.println("Food added successfully!!");
                }
                BufferedWriter menuWriter = new BufferedWriter(new FileWriter(INPUT_FILE_NAME2));
                for (var i : foods) {
                    String menuInput = i.getRestaurantId() + "," + i.getCategory() + "," + i.getName() + ","
                            + i.getPrice();

                    menuWriter.write(menuInput);
                    menuWriter.write(System.lineSeparator());
                }
                menuWriter.close();
            }
            System.out.println();
        }
    }

    boolean addFood(String restaurantName, int restaurantId, String category, String name, double price) {
        boolean b = false;
        Food f = new Food(restaurantId, category, name, price);
        int flag = 0, f2 = 0;
        for (int i = 0; i < restaurants.size(); i++) {
            if (equals(restaurants.get(i).getName(), restaurantName)) {
                ArrayList<String> categories = restaurants.get(i).getCategory();
                for (int j = 0; j < categories.size(); j++) {
                    flag = 1;
                    if (categories.get(j).equals(category)) {
                        f2 = 1;
                        b = true;
                        restaurants.get(i).addFood(f);
                        // add to foods also
                        foods.add(f);
                    }

                    if (f2 == 0 && categories.size() < 3) {
                        restaurants.get(i).setCategory(category);
                        f2 = 1;
                        b = true;
                    }

                }
            }
        }
        if (flag == 0) {
            System.out.println("Given Restaurant is not present in the Database!!");
        } else if (flag == 1 && f2 == 0) {
            System.out.println("Given category is not present in the Restaurant!!");
        }
        return b;
    }

    void addRestaurant(int id, String name, double score, String price, String zipCode, ArrayList<String> categories) {
        Restaurant restaurant = new Restaurant(id, name, score, price, zipCode);
        for (int i = 0; i < categories.size(); i++) {
            restaurant.setCategory(categories.get(i));
        }
        restaurants.add(restaurant);
    }
}

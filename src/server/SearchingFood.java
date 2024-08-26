package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static java.lang.Double.parseDouble;

public class SearchingFood
{
    FileOperations f;
    ArrayList<Food> foods;
    Map<String, ArrayList<Food>> categories;
    ArrayList<Restaurant> restaurants;
    Scanner sc = new Scanner(System.in);

    boolean equals(String s1, String s2)
    {
        String s11 = s1.toLowerCase();
        String s22 = s2.toLowerCase();
        return s11.contains(s22);
    }

    public SearchingFood()
    {
        f = new FileOperations();
        foods = f.getFoods();
        categories = new HashMap<String, ArrayList<Food>>();
        restaurants = f.getRestaurants();
    }

    void searchFood()
    {

        ArrayList<Food> foundFoods = new ArrayList<>();
        String choice = "";
        while (!choice.equals("9"))
        {
            System.out.println("1) By Name\n" + "2) By Name in a Given Restaurant\n" + "3) By Category\n" + "4) By Category in a Given Restaurant \n" + "5) By Price Range\n" + "6) By Price Range in a Given Restaurant\n" + "7) Costliest Food Item(s) on the Menu in a Given Restaurant\n" + "8) List of Restaurants and Total Food Item on the Menu\n" + "9) Back to Main Menu");

            System.out.println();
            System.out.print("Enter choice:");

            choice = sc.nextLine();

            if (choice.equals("1"))
            {
                System.out.print("Enter name:");
                String name = sc.nextLine();
                foundFoods = searchByName(name);
            }

            else if (choice.equals("2"))
            {
                System.out.print("Enter food name:");
                String foodName = sc.nextLine();
                System.out.print("Enter restaurant name:");
                String restaurantName = sc.nextLine();
                foundFoods = searchByNameGivenRestaurant(foodName, restaurantName);
            }

            else if (choice.equals("3"))
            {
                /*
                 * gettingCategories();
                 * System.out.println("Category-wise food names: ");
                 * for (Map.Entry<String, ArrayList<Food>> e : categories.entrySet()) {
                 * System.out.print(e.getKey() + ": " + e.getValue().get(0).getName());
                 * int j = 0;
                 * for (var i : e.getValue()) {
                 * if (j == 0) {
                 * j++;
                 * continue;
                 * }
                 * System.out.print(", " + i.getName());
                 * }
                 * System.out.println();
                 * }
                 */

                System.out.print("Enter category: ");
                String category = sc.nextLine();

                foundFoods = searchByCategory(category);
            }

            else if (choice.equals("4"))
            {
                System.out.print("Enter category:");
                String category = sc.nextLine();
                System.out.print("Enter restaurant name:");
                String restaurantName = sc.nextLine();
                foundFoods = searchByNameGivenRestaurant(category, restaurantName);
            }

            else if (choice.equals("5"))
            {
                System.out.print("Enter a price range: ");
                String scoreInput = sc.nextLine();
                String[] scores = scoreInput.split(" ");

                if (scores.length == 2)
                {
                    double score1 = parseDouble(scores[0]);
                    double score2 = parseDouble(scores[1]);
                    foundFoods = searchByPrice(score1, score2);
                }
                else
                {
                    System.out.println("Invalid input format for score range.");
                }
            }

            else if (choice.equals("6"))
            {
                System.out.print("Enter a price range: ");
                String scoreInput = sc.nextLine();
                String[] scores = scoreInput.split(" ");

                if (scores.length >= 2)
                {
                    double score1 = parseDouble(scores[0]);

                    double score2 = parseDouble(scores[1]);
                    System.out.print("Enter restaurant name:");
                    String restaurantName = sc.nextLine();
                    foundFoods = searchByPriceGivenRestaurant(score1, score2, restaurantName);
                }
                else
                {
                    System.out.println("No Food items in this range.");
                }
            }

            else if (choice.equals("7"))
            {
                System.out.print("Enter restaurant name:");
                String restaurantName = sc.nextLine();
                Food food = costliestFood(restaurantName);
                foundFoods.add(food);
            }

            else if (choice.equals("8"))
            {
                for (int i = 0; i < restaurants.size(); i++)
                {
                    System.out.println(restaurants.get(i).getName() + ": " + restaurants.get(i).numberOfFoods());
                }
            }

            if (!choice.equals("9") && !choice.equals("8"))
            {
                for (var i : foundFoods)
                {
                    i.showDetails();
                    System.out.println();
                }
            }
            System.out.println();

        }

    }

    void gettingCategories()
    {
        for (var i : foods)
        {
            String fCat = i.getCategory();

            categories.putIfAbsent(fCat, new ArrayList<Food>());
            categories.get(fCat).add(i);

        }
    }

    public ArrayList<Food> searchByName(String name)
    {
        ArrayList<Food> foundFoods = new ArrayList<>();
        // String newName=name.toLowerCase();
        for (var i : foods)
        {
            if (equals(i.getName(), name))
            {
                foundFoods.add(i);
            }
        }
        return foundFoods;
    }

    ArrayList<Food> searchByCategory(String category)
    {
        ArrayList<Food> foundFoods = new ArrayList<>();
        // String newCategory=category.toLowerCase();
        for (var i : foods)
        {
            if (equals(i.getCategory(), category))
            {
                foundFoods.add(i);
            }
        }
        return foundFoods;
    }

    ArrayList<Food> searchByPrice(double price1, double price2)
    {
        ArrayList<Food> foundFoods = new ArrayList<>();
        for (var i : foods)
        {
            if (i.getPrice() >= price1 && i.getPrice() <= price2)
            {
                foundFoods.add(i);
            }
        }
        return foundFoods;
    }

    public ArrayList<Food> searchByNameGivenRestaurant(String foodName, String restaurantName)
    {
        ArrayList<Food> foundFoods = new ArrayList<>();
        for (int i = 0; i < restaurants.size(); i++)
        {
            if (equals(restaurants.get(i).getName(), restaurantName))
            {
                ArrayList<Food> restaurantFoods = restaurants.get(i).getFoods();
                for (var j : restaurantFoods)
                {
                    if (equals(j.getName(), foodName))
                    {
                        foundFoods.add(j);
                    }
                }
            }
        }
        return foundFoods;
    }

    ArrayList<Food> searchByCategoryGivenRestaurant(String category, String restaurantName)
    {
        ArrayList<Food> foundFoods = new ArrayList<>();
        for (int i = 0; i < restaurants.size(); i++)
        {
            if (equals(restaurants.get(i).getName(), restaurantName))
            {
                ArrayList<Food> restaurantFoods = restaurants.get(i).getFoods();
                for (var j : restaurantFoods)
                {
                    if (equals(j.getName(), category))
                    {
                        foundFoods.add(j);
                    }
                }
            }
        }
        return foundFoods;
    }

    ArrayList<Food> searchByPriceGivenRestaurant(double price1, double price2, String restaurantName)
    {
        ArrayList<Food> foundFoods = new ArrayList<>();
        for (int i = 0; i < restaurants.size(); i++)
        {
            if (equals(restaurants.get(i).getName(), restaurantName))
            {
                ArrayList<Food> restaurantFoods = restaurants.get(i).getFoods();
                for (var j : restaurantFoods)
                {
                    if (j.getPrice() >= price1 && j.getPrice() <= price2)
                    {
                        foundFoods.add(j);
                    }
                }
            }
        }
        return foundFoods;
    }

    Food costliestFood(String restaurantName)
    {
        Food f = new Food(0, restaurantName, restaurantName, 0);
        for (int i = 0; i < restaurants.size(); i++)
        {
            if (equals(restaurants.get(i).getName(), restaurantName))
            {
                ArrayList<Food> restaurantFoods = restaurants.get(i).getFoods();
                double maxi = 0;
                for (var j : restaurantFoods)
                {
                    if (j.getPrice() > maxi)
                    {
                        maxi = j.getPrice();
                        f = j;
                    }
                }
            }
        }
        return f;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }
}

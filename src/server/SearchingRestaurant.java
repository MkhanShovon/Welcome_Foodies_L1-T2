package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Double.parseDouble;

public class SearchingRestaurant
{
    Server f = new Server();
    ArrayList<Restaurant> restaurants = f.getRestaurants();

    Scanner sc = new Scanner(System.in);

    boolean equals(String s1, String s2)
    {
        String s11 = s1.toLowerCase();
        String s22 = s2.toLowerCase();
        return s11.contains(s22);
    }

    void searchRestaurant()
    {
        String choice = "";
        ArrayList<Restaurant> foundrestaurants = new ArrayList<>();
        while (!choice.equals("7"))
        {
            System.out.println("1) By Name\r\n" + //
                               "2) By Score\r\n" + //
                               "3) By Category\r\n" + //
                               "4) By Price\r\n" + //
                               "5) By Zip Code\r\n" + //
                               "6) Different Category Wise List of Restaurants\r\n" + //
                               "7) Back to Main Menu");

            System.out.println();
            System.out.print("Enter choice:");

            choice = sc.nextLine();

            if (choice.equals("1"))
            {

                System.out.print("Enter name:");
                String name = sc.nextLine();
                foundrestaurants = searchByName(name);
            }
            else if (choice.equals("2"))
            {

                System.out.print("Enter a score range: ");
                String scoreInput = sc.nextLine();
                String[] scores = scoreInput.split(" ");

                if (scores.length == 2)
                {
                    double score1 = parseDouble(scores[0]);
                    double score2 = parseDouble(scores[1]);
                    foundrestaurants = searchByScore(score1, score2);
                }
                else
                {
                    System.out.println("No Restaurant in this score range.");
                }
            }

            else if (choice.equals("3"))
            {

                System.out.print("Enter category name: ");
                String category = sc.nextLine();
                foundrestaurants = searchByCategory(category);
            }

            else if (choice.equals("4"))
            {

                System.out.print("Enter price: ");
                String price = sc.nextLine();
                foundrestaurants = searchByPrice(price);
            }

            else if (choice.equals("5"))
            {

                System.out.print("Enter Zip Code: ");
                String zipCode = sc.nextLine();
                foundrestaurants = searchByZipCode(zipCode);
            }

            else if (choice.equals("6"))
            {
                Map<String, ArrayList<Restaurant>> categories = new HashMap<String, ArrayList<Restaurant>>();
                gettingCategories(categories);
                System.out.println("Category-wise restaurant names: ");
                for (Map.Entry<String, ArrayList<Restaurant>> e : categories.entrySet())
                {
                    System.out.print(e.getKey() + ": " + e.getValue().get(0).getName());
                    int j = 0;
                    for (var i : e.getValue())
                    {
                        if (j == 0)
                        {
                            j++;
                            continue;
                        }
                        System.out.print(", " + i.getName());
                    }
                    System.out.println();
                }
            }
            if (!choice.equals("6") && !choice.equals("7"))
            {
                for (var i : foundrestaurants)
                {
                    i.showDetails();
                }
            }
            System.out.println();

        }

    }

    void gettingCategories(Map<String, ArrayList<Restaurant>> categories)
    {
        for (var i : restaurants)
        {
            ArrayList<String> resCat = i.getCategory();
            for (var j : resCat)
            {
                categories.putIfAbsent(j, new ArrayList<Restaurant>());
                categories.get(j).add(i);
            }
        }
    }

    public ArrayList<Restaurant> searchByName(String name)
    {
        ArrayList<Restaurant> foundrestaurants = new ArrayList<>();
        // String newName=name.toLowerCase();
        for (var i : restaurants)
        {
            if (equals(i.getName(), name))
            {
                foundrestaurants.add(i);
            }
        }
        return foundrestaurants;
    }

    boolean matchingCategory(String category, Restaurant r)
    {
        ArrayList<String> categories = r.getCategory();
        for (var i : categories)
        {
            if (equals(i, category))
            {
                return true;
            }
        }
        return false;
    }

    ArrayList<Restaurant> searchByCategory(String category)
    {
        ArrayList<Restaurant> foundrestaurants = new ArrayList<>();
        // String newCategory=category.toLowerCase();
        for (var i : restaurants)
        {
            if (matchingCategory(category, i))
            {
                foundrestaurants.add(i);
            }
        }
        return foundrestaurants;
    }

    ArrayList<Restaurant> searchByPrice(String price)
    {
        ArrayList<Restaurant> foundrestaurants = new ArrayList<>();
        // String newPrice=price.toLowerCase();
        for (var i : restaurants)
        {
            if (equals(i.getPrice(), price))
            {
                foundrestaurants.add(i);
            }
        }
        return foundrestaurants;
    }

    ArrayList<Restaurant> searchByZipCode(String zipCode)
    {
        ArrayList<Restaurant> foundrestaurants = new ArrayList<>();
        // String newZipCode=zipCode.toLowerCase();
        for (var i : restaurants)
        {
            if (equals(i.getZipCode(), zipCode))
            {
                foundrestaurants.add(i);
            }
        }
        return foundrestaurants;
    }

    ArrayList<Restaurant> searchByScore(double score1, double score2)
    {
        ArrayList<Restaurant> foundrestaurants = new ArrayList<>();
        for (var i : restaurants)
        {
            if (i.getScore() >= score1 && i.getScore() <= score2)
            {
                foundrestaurants.add(i);
            }
        }
        return foundrestaurants;
    }

}

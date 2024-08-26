package server;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable
{
    private static final long serialVersionUID = 1L;
    int id;
    String name;
    double score;
    String price;
    String zipCode;
    ArrayList<String> category = new ArrayList<>();
    ArrayList<Food> foods = new ArrayList<>();

    Restaurant()
    {

    }

    Restaurant(int id, String name, double score, String price, String zipCode)
    {
        this.name = name;
        this.id = id;
        this.score = score;
        this.price = price;
        this.zipCode = zipCode;
    }

    void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

    void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    void setScore(double score)
    {
        this.score = score;
    }

    public double getScore()
    {
        return this.score;
    }

    void setPrice(String price)
    {
        this.price = price;
    }

    public String getPrice()
    {
        return this.price;
    }

    void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getZipCode()
    {
        return this.zipCode;
    }

    void setCategory(String category)
    {
        this.category.add(category);
    }

    public ArrayList<String> getCategory()
    {
        return this.category;
    }

    public void showDetails()
    {
        System.out.println("Restaurant ID: " + this.id);
        System.out.println("Restaurant Name: " + this.name);
        System.out.println("Restaurant Score: " + this.score);
        System.out.println("Restaurant Price: " + this.price);
        System.out.println("Restaurant Zip Code: " + this.zipCode);
        if (!category.isEmpty())
        {
            System.out.print("Restaurant Categories: " + category.get(0));

            for (int i = 1; i < category.size(); i++)
            {
                System.out.print(", " + category.get(i));
            }

            System.out.println();
        }
        System.out.println();
    }

    void addFood(Food food)
    {
        foods.add(food);
    }

    public ArrayList<Food> getFoods()
    {
        return foods;
    }

    int numberOfFoods()
    {
        return foods.size();
    }

    public void showFoods()
    {
        for(var i:foods)
        {
            i.showDetails();
        }
        //return false;
    }
}

package server;

import java.io.Serializable;

public class Food implements Serializable
{
    int restaurantId;
    String category;
    String name;
    double price;

    String customerName;

    public Food(int restaurantId, String category, String name, double price)
    {
        this.restaurantId = restaurantId;
        this.category = category;
        this.name = name;
        this.price = price;
    }

    void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    void setPrice(double price)
    {
        this.price = price;
    }

    public double getPrice()
    {
        return this.price;
    }

    void setRestaurantId(int restaurantId)
    {
        this.restaurantId = restaurantId;
    }

    public int getRestaurantId()
    {
        return this.restaurantId;
    }

    void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategory()
    {
        return this.category;
    }

    public void showDetails()
    {
        System.out.println("Name: " + this.name);
        System.out.println("Restaurant Id: " + this.restaurantId);
        System.out.println("Category: " + this.category);
        System.out.println("Price: " + this.price);
        System.out.println();
    }

    @Override
    public String toString() {
        return restaurantId+","+category+","+name+"," + price;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName=customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Food)) {
            return false;
        }
        Food p = (Food) o;
        if (p.restaurantId == this.restaurantId && p.name == this.name)
            return true;
        return false;
    }
}

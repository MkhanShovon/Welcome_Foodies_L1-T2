package server;

import java.io.*;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class FileOperations
{

    private static final String INPUT_FILE_NAME =  "D:\\Project 1-2\\part2\\JavaFX\\JavaFX\\LoginServer\\src\\server\\restaurant.txt";
    private static final String INPUT_FILE_NAME2 = "D:\\Project 1-2\\part2\\JavaFX\\JavaFX\\LoginServer\\src\\server\\menu.txt";

    public static ArrayList<Restaurant> restaurants = new ArrayList<>();
    public static ArrayList<Food> foods = new ArrayList<>();

    public FileOperations() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (true)
        {
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line == null) break;
            //System.out.println(line);
            String[] array = line.split(",", -1);

            Restaurant restaurant = new Restaurant(parseInt(array[0]), array[1], parseDouble(array[2]), array[3], array[4]);
//            for (int i = 0; i < array.length; i++)
//            {
//                System.out.println(array[i]);
//            }
            for (int i = 5; i < array.length; i++)
            {
                restaurant.setCategory(array[i]);
            }
            restaurants.add(restaurant);
        }
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(INPUT_FILE_NAME2));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (true)
        {
            String line = null;
            try {
                line = bf.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (line == null) break;
            //System.out.println(line);
            String[] array = line.split(",", -1);
            int restaurantId = parseInt(array[0]);
            Food food = new Food(restaurantId, array[1], array[2], parseDouble(array[3]));
//            for (int i = 0; i < array.length; i++)
//            {
//                System.out.println(array[i]);
//            }
            for (int i = 0; i < restaurants.size(); i++)
            {
                if (restaurants.get(i).getId() == restaurantId)
                {
                    restaurants.get(i).addFood(food);
                }
            }
            foods.add(food);
        }
        try {
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        MainMenu mainMenu = new MainMenu();
//        try {
//            mainMenu.showMenu();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        /* BufferedWriter writer = new BufferedWriter(new FileWriter(INPUT_FILE_NAME));
        for (var i : restaurants)
        {
            String input = i.getId() + "," + i.getName() + "," + i.getScore() + "," + i.getPrice() + "," + i.getZipCode();
            for (int j = 0; j < i.getCategory().size(); j++)
            {
                input += "," + i.getCategory().get(j);
            }
            writer.write(input);
            writer.write(System.lineSeparator());
        }
        writer.close(); */

        /* BufferedWriter menuWriter = new BufferedWriter(new FileWriter(INPUT_FILE_NAME2));
        for (var i : foods)
        {
            String menuInput = i.getRestaurantId() + "," + i.getCategory() + "," + i.getName() + "," + i.getPrice();

            menuWriter.write(menuInput);
            menuWriter.write(System.lineSeparator());
        }
        menuWriter.close(); */
    }

    ArrayList<Restaurant> getRestaurants()
    {
        return restaurants;
    }

    ArrayList<Food> getFoods()
    {
        return foods;
    }
}

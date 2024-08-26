package server;

import util.LoginDTO;
import util.NetworkUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Server {
    HashMap<String,NetworkUtil>clientMap=new HashMap<>();
    private static final String INPUT_FILE_NAME = "D:\\Project 1-2\\part2\\JavaFX\\JavaFX\\LoginServer\\src\\server\\restaurant.txt";
    private static final String INPUT_FILE_NAME2 = "D:\\Project 1-2\\part2\\JavaFX\\JavaFX\\LoginServer\\src\\server\\menu.txt";

    public static ArrayList<Restaurant> restaurants = new ArrayList<>();
    public static ArrayList<Food> foods = new ArrayList<>();

    private ServerSocket serverSocket;
    public static HashMap<String, String> userMap;
    public static HashMap<String, Restaurant> restaurantMap;
    public static HashMap<String,String>resNameMap=new HashMap<>();

    public Server() {

        try {
            serverSocket = new ServerSocket(55555);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server Connected");
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }


    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        LoginDTO loginDTO=(LoginDTO)networkUtil.read();
        clientMap.put(loginDTO.getUserName(),networkUtil);
        String password = userMap.get(loginDTO.getUserName());
        System.out.println(password+" "+loginDTO.getPassword());
        loginDTO.setStatus(loginDTO.getPassword().equals(password));

        loginDTO.setRestaurant(restaurantMap.get(loginDTO.getUserName()));
        new ReadThreadServer(resNameMap,clientMap,restaurantMap,userMap,foods,restaurants, networkUtil);
    }



    public static void main(String[] args) throws IOException {
        userMap = new HashMap<>();
        userMap.put("a", "a");
        userMap.put("b", "b");
        userMap.put("c", "c");
        userMap.put("d", "d");
        userMap.put("e", "e");
        restaurantMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true)
        {
            String line = br.readLine();
            if (line == null) break;
            System.out.println(line);
            String[] array = line.split(",", -1);
            String name=array[1];
            Restaurant restaurant = new Restaurant(parseInt(array[0]), array[1], parseDouble(array[2]), array[3], array[4]);
            for (int i = 0; i < array.length; i++)
            {
                System.out.println(array[i]);
            }
            for (int i = 5; i < array.length; i++)
            {
                restaurant.setCategory(array[i]);
            }
            restaurantMap.put(array[0], restaurant);
            resNameMap.put(array[0],array[1]);
            userMap.put(array[0],name);
            //restaurantMap.get(name)=restaurant;
            restaurants.add(restaurant);
        }
        br.close();

        BufferedReader bf = new BufferedReader(new FileReader(INPUT_FILE_NAME2));
        while (true)
        {
            String line = bf.readLine();
            if (line == null) break;
            System.out.println(line);
            String[] array = line.split(",", -1);
            int restaurantId = parseInt(array[0]);
            Food food = new Food(restaurantId, array[1], array[2], parseDouble(array[3]));
            for (int i = 0; i < array.length; i++)
            {
                System.out.println(array[i]);
            }
            for (int i = 0; i < restaurants.size(); i++)
            {
                if (restaurants.get(i).getId() == restaurantId)
                {
                    restaurants.get(i).addFood(food);
                }
            }
            foods.add(food);
        }
        bf.close();
        System.out.println(foods.size());
        new Server();
        //MainMenu mainMenu = new MainMenu();
        //mainMenu.showMenu();

    }
    public ArrayList<Restaurant> getRestaurants()
    {
        return restaurants;
    }

    public ArrayList<Food> getFoods()
    {
        System.out.println(foods.size());
        for(var f:foods)
        {
            System.out.println(f.getName());
        }
        return foods;
    }
}

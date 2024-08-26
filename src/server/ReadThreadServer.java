package server;

import util.LoginDTO;
import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    HashMap<String,NetworkUtil>clientMap=new HashMap<>();

    private final NetworkUtil networkUtil;
    public HashMap<String, Restaurant> restaurantMap;
    HashMap<String, String> userMap;
    ArrayList<Food>foods;
    ArrayList<Restaurant>restaurants;
    HashMap<String,String>resNameMap;

    public ReadThreadServer(HashMap<String,String>resNameMap,    HashMap<String,NetworkUtil>clientMap,HashMap<String, Restaurant> map, HashMap<String, String> userMap, ArrayList<Food>foods,ArrayList<Restaurant>restaurants ,NetworkUtil networkUtil) {
        this.restaurantMap = map;
        this.userMap=userMap;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
        this.foods=foods;
        this.restaurants=restaurants;
        this.clientMap=clientMap;
        this.resNameMap=resNameMap;
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        String password = userMap.get(loginDTO.getUserName());
                        loginDTO.setStatus(loginDTO.getPassword().equals(password));

                        loginDTO.setRestaurant(restaurantMap.get(loginDTO.getUserName()));
                        loginDTO.setRestaurants(this.restaurants);
//                        loginDTO.setFoods(foods);
//                        loginDTO.setRestaurants(restaurants);
                        networkUtil.write(loginDTO);


                    }
                    if(o instanceof Food)
                    {
                        Food food=(Food) o;
                        System.out.println(food.getName());
                        NetworkUtil nu=clientMap.get(food.getRestaurantId()+"");
                        if(nu!=null)
                        {
                            nu.write(food);
                        }
                        else{
                            String name=resNameMap.get(food.getRestaurantId()+"");
                            networkUtil.write(name+" is Closed now");
                        }

                    }

                    if(o instanceof String)
                    {
                        String resMessage=(String) o;
                        String[] array = resMessage.split("!", -1);
                        NetworkUtil nc=clientMap.get(array[0]);
                        nc.write(array[1]);
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




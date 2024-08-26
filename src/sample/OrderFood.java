package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.Food;

import java.util.ArrayList;

public class OrderFood {

    @FXML
    TableColumn<Food,String> orderName;
    @FXML
    TableColumn<Food, Double>orderPrice;
    @FXML
    TableColumn<Food,String>orderCat;
    @FXML
    TableView<Food> orderTable=new TableView<>();


    ArrayList<Food> foods = new ArrayList<>();
    public void updateOrder(Food f) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                foods.add(f);
                for(var it:foods){
                    System.out.println(it.getName());
                }
                System.out.println(f.getName());
                ObservableList<Food> foodList = FXCollections.observableArrayList(foods);
//                orderName.setCellValueFactory(new PropertyValueFactory<>("name"));
//                orderCat.setCellValueFactory(new PropertyValueFactory<>("category"));
//                orderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
//                orderName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
//                orderPrice.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getPrice())));
//                orderCat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

                //  orderTable.setItems(foodList);
                orderCat.setCellValueFactory(new PropertyValueFactory<>("category"));
                orderName.setCellValueFactory(new PropertyValueFactory<>("name"));
                orderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


                orderTable.setItems(foodList);
            }
        });
    }
}

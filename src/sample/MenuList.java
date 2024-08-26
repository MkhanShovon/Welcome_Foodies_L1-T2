package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.Food;
import server.Restaurant;
import util.NetworkUtil;

public class MenuList {
    Main main;
    @FXML
    private TableColumn<Food, String> categoryColumn;

    @FXML
    private TableColumn<Food, String> foodColumn;

    @FXML
    private TableColumn<Food, Double> priceColumn;
    @FXML
    private TableView<Food> foodTableView;
    public void init(Restaurant restaurant, NetworkUtil networkUtil)
    {
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        foodColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        foodTableView.setItems(FXCollections.observableArrayList(restaurant.getFoods()));
    }

    public void setMain(Main main)
    {
        this.main=main;
    }
}

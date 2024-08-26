package server;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import util.LoginDTO;
import util.NetworkUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchedTable implements Initializable {
    private ListProperty<String> orderedItems = new SimpleListProperty<>(FXCollections.observableArrayList());

    @FXML
    private TableView<Food> foodTableView;

    @FXML
    private TableColumn<Food, String> categoryColumn;

    @FXML
    private TableColumn<Food, String> foodColumn;

    @FXML
    private TableColumn<Food, Double> priceColumn;
    @FXML
    private TableColumn<Food, Integer> resIdColumn;
    ArrayList<Food> foods;
    ArrayList<Food> orderedFoods = new ArrayList<>();
    LoginDTO loginDTO;
    NetworkUtil networkUtil;
//    @FXML
//    Button order;

    public void init(ArrayList<Food> foods, LoginDTO loginDTO, NetworkUtil networkUtil) {
        this.foods = foods;
        this.loginDTO = loginDTO;
        this.networkUtil = networkUtil;
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        foodColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        resIdColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantId"));

        foodTableView.setItems(FXCollections.observableArrayList(foods));

    }

    Customer main;


    public void setMain(Customer main) {
        this.main = main;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        TableColumn<Food, Boolean> addToCartColumn = new TableColumn<>("Add to Cart");

        // Define a custom cell factory to create buttons in each row
        addToCartColumn.setCellFactory(new Callback<TableColumn<Food, Boolean>, TableCell<Food, Boolean>>() {
            @Override
            public TableCell<Food, Boolean> call(TableColumn<Food, Boolean> column) {
                return new TableCell<Food, Boolean>() {
                    final Button addToCartButton = new Button("Add to Cart");

                    {
                        addToCartButton.setOnAction(event -> {
                            Food food = getTableRow().getItem();
                            if (food != null) {
                                // Add the selected food to the orderedFoods list
                                //orderedFoods.add(food);
                                //orderedItems=main.getConfirmationTable().getOrderedItems();


                                main.getOrderedItems().add(String.valueOf(food));
                                if(main.getOrderedItems().size()==1)
                                {

                                    try {
                                        main.showConfirm(main.getOrderedItems(),loginDTO,networkUtil);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setGraphic(addToCartButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        // Add the "Add to Cart" column to the table
        foodTableView.getColumns().add(addToCartColumn);

        //foodTableView.getColumns().addAll(categoryColumn,priceColumn,foodColumn,resIdColumn);
//     order.setOnAction(e-> {
//            try {
//                orderAction();
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//        });

    }

//    @FXML
//    void orderAction() throws Exception {
//        main.showConfirm(orderedItems,loginDTO,networkUtil);
//    }
}

package server;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import util.LoginDTO;
import util.NetworkUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class ConfirmationTable implements Initializable{
    @FXML
    private ListProperty<String> orderedItems = new SimpleListProperty<>(FXCollections.observableArrayList());
    @FXML
    ListView<String> orderedList = new ListView<>();

//    @FXML
//    private TableView<Food> foodTableView;
//
//    @FXML
//    private TableColumn<Food, String> categoryColumn;
//
//    @FXML
//    private TableColumn<Food, String> foodColumn;
//
//    @FXML
//    private TableColumn<Food, Double> priceColumn;
//    @FXML
//    private TableColumn<Food, Integer> resIdColumn;
    ArrayList<Food> foods;
    LoginDTO loginDTO;
    NetworkUtil networkUtil;
    @FXML
    Button confirm;

    public void init( ListProperty<String> orderedItems,LoginDTO loginDTO, NetworkUtil networkUtil) {
        this.foods = foods;
        this.loginDTO = loginDTO;
        this.networkUtil = networkUtil;
//        for(var i:foods)
//        {
//            orderedItems.add(String.valueOf(i));
//        }
        this.orderedItems=orderedItems;
        this.orderedList.itemsProperty().bind(orderedItems);
        //orderedList.setVisible(false);

        //foodTableView.setItems(FXCollections.observableArrayList(foods));
        confirm.setOnAction(e-> {
            try {
                confirmAction();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    Customer main;

    public void setMain(Customer main) {
        this.main = main;
    }

    @FXML
    void confirmAction() throws IOException {
        for (int j = 0; j < orderedList.getItems().size(); j++) {
            String currentLine = (String) orderedList.getItems().get(j);
            //String[] cells=currentLine.split(";");
            System.out.println(currentLine);
            //System.out.println(Arrays.toString(cells));
            String[] array = currentLine.split(",", -1);
            int restaurantId = parseInt(array[0]);
            Food food = new Food(restaurantId, array[1], array[2], parseDouble(array[3]));
            for (int i = 0; i < array.length; i++) {
                System.out.println(array[i]);
            }
//                for (int i = 0; i < restaurants.size(); i++)
//                {
//                    if (restaurants.get(i).getId() == restaurantId)
//                    {
//                        restaurants.get(i).addFood(food);
//                    }
//                }
            food.setCustomerName(loginDTO.getUserName());
            //orderedFoods.add(newValue);
            try {
                this.networkUtil.write(food);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        class CustomListCell extends ListCell<String> {
            private HBox hbox = new HBox();
            private Label label = new Label();
            private Button removeButton = new Button("Remove");
            private String lastItem;

            public CustomListCell() {
                super();

                removeButton.setOnAction(event -> {
                    if (lastItem != null) {
                        orderedItems.remove(lastItem);
                    }
                });

                hbox.getChildren().addAll(label, removeButton);
                HBox.setHgrow(label, Priority.ALWAYS);
                HBox.setHgrow(removeButton, Priority.ALWAYS);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                if (empty) {
                    lastItem = null;
                    setGraphic(null);
                } else {
                    lastItem = item;
                    label.setText(item);
                    setGraphic(hbox);
                }
            }
        }
        orderedList.setCellFactory(param -> new CustomListCell());

    }

    void showReceive(String serverMessage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (!serverMessage.contains(" is Closed now")) {
//                    orderedList.setVisible(false);
//                    message2.setVisible(false);
                    String[] array = serverMessage.split(";", -1);
                    orderedItems.remove(array[0]);
                    //orderedItems.clear();
                    //receive.setText(array[1]);
                    showAlertFound(array[1]);
                } else {
                    //receive.setText(serverMessage);
                    showAlertNotFound(serverMessage);
                }

            }

        });

    }

    public void showAlertNotFound(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Not Found");
        alert.setHeaderText("Restaurant is not opened yet");
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    public void showAlertFound(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Order Delivered");
        alert.setHeaderText(alertMessage);
        alert.setContentText("Receive Delivery");
        alert.showAndWait();
    }
    ListProperty<String> getOrderedItems()
    {
        return this.orderedItems;
    }

}

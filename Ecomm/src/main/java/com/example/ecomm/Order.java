package com.example.ecomm;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Order {
    static TableView<Product> orderTable;


    public static boolean placeOrder(Customer customer,Product product){
            try {
                String placeOrder="insert into orders(customer_id,product_id,status) values("+customer.getId()+","+product.getId()+",'ordered')";
                DatabaseConnection dbconn=new DatabaseConnection();
                dbconn.insertUpdate(placeOrder);
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
    }
    public  static int placeMultipleOrders(ObservableList<Product> productObservableList,Customer customer){
              int count=0;
              for(Product product:productObservableList){
                  if(placeOrder(customer,product)){
                      count++;
                  }
              }
              return count;
    }
//    public  static Pane getOrders() {
//        TableColumn id = new TableColumn("IdA");
//        id.setCellValueFactory(new PropertyValueFactory<>("id"));
//        TableColumn name = new TableColumn("Name");
//        name.setCellValueFactory(new PropertyValueFactory<>("name"));
//        TableColumn price = new TableColumn("price");
//        price.setCellValueFactory(new PropertyValueFactory<>("price"));



//        ObservableList<Product> productList=Product.getAllProducts();
//        return createTableFromList(productList);
//        productTable = new TableView<>();
//        productTable.setItems(productsList);
//        productTable.getColumns().addAll(id,name,price);
//
//        Pane tablePane = new Pane();
//        tablePane.getChildren().add(productTable);
//
//        return tablePane;
//    }
    public static   Pane createTableFromList(ObservableList<Product> orderList) {
        TableColumn id = new TableColumn("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn price = new TableColumn("price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        //        ObservableList<Product> data = FXCollections.observableArrayList();
//        data.addAll(new Product(123, "Laptop", (double) 234.5),
//                new Product(1245, "Laptop", (double) 234.5)
//        );

        orderTable = new TableView<>();
        orderTable.setItems(orderList);
        orderTable.getColumns().addAll(id,name,price);

        Pane tablePane = new Pane();
        tablePane.getChildren().add(orderTable);

        return tablePane;
    }
    public static Pane getOrders(Customer customer){
        String order="select orders.oid,products.name,products.price from orders inner join products on orders.product_id=products.pid where customer_id="+customer.getId();
        ObservableList<Product> orderList=Product.getProduct(order);
        return createTableFromList(orderList);
    }
    public static Pane getOrders2(Customer customer){
        String order="select * from orders where not oid ="+orderTable.getSelectionModel().getSelectedItem().getId();
        ObservableList<Product> orderList=Product.getProduct(order);
        return createTableFromList(orderList);
    }

    public static Pane search(String s){
            String order="select * from products where name like '%"+s+"%'";
            ObservableList<Product> orderList=Product.getProducts(order);
            return createTableFromList(orderList);
    }

//    public Pane deleteOrders(int ){
//       String query ="delete from products where customer_id="+
//        ObservableList<Product> orderList=Product.getProduct(order);
//        return createTableFromList(orderList);
//    }
public Product getSelectedProduct(){

    return orderTable.getSelectionModel().getSelectedItem();
}
}

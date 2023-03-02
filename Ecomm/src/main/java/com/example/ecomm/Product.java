package com.example.ecomm;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.sql.ResultSet;

public class Product {


    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;


    public  int getId(){
        return id.get();
    }

    public  String getName(){
        return name.get();
    }

    public  Double getPrice(){
        return price.get();
    }

    public Product(int id, String name, Double price){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }
   public static ObservableList<Product> getAllProducts(){

            String allProductsList="select * from products";

            return getProducts(allProductsList);

   }

    public static ObservableList<Product> getProducts(String query) {
        DatabaseConnection dnconn=new DatabaseConnection();
        ResultSet rs= dnconn.getQueryTable(query);
        ObservableList<Product> result= FXCollections.observableArrayList();
        try {
            if(rs!=null){
                while(rs.next()){
                    result.add(new Product(rs.getInt("pid"),rs.getString("name"),rs.getDouble("price")));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public static ObservableList<Product> getProduct(String query) {
        DatabaseConnection dnconn=new DatabaseConnection();
        ResultSet rs= dnconn.getQueryTable(query);
        ObservableList<Product> result= FXCollections.observableArrayList();
        try {
            if(rs!=null){
                while(rs.next()){
                    result.add(new Product(rs.getInt("oid"),rs.getString("name"),rs.getDouble("price")));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

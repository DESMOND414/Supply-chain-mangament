package com.example.ecomm;
import java.sql.*;

public class DatabaseConnection {
    String dburl
            = "jdbc:mysql://localhost:3306/ecomm";
    String user="root";
    String password="Starlord@1234";
    public Statement getStatement(){
        try {
            Connection con= DriverManager.getConnection(dburl,user,password);
            return con.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
    public boolean insertUpdate(String query){
        Statement statement=getStatement();
        try {
             statement.executeUpdate(query);
             return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet getQueryTable(String query){
        Statement statement=getStatement();
        try {
           return statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static void main(String[] args) {
//        String query="select * from products";
//        DatabaseConnection DbConn=new DatabaseConnection();
//        ResultSet rs= DbConn.getQueryTable(query);
//        if(rs!=null){
//            System.out.println("database connection sucessfull");
//        }
//    }

}

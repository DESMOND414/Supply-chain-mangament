package com.example.ecomm;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.MessageDigestSpi;
import java.sql.ResultSet;

public class Login {

    private static byte[] getSha(String input){
        try {
            MessageDigest md= MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private static String getEcryptedPassword(String password){
        try {
            BigInteger num=new BigInteger(1,getSha(password));
            StringBuilder hexString=new StringBuilder(num.toString(16));
            return hexString.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public  static  Customer customerLogin(String userEmail,String password){
         String ep=getEcryptedPassword(password);
        String query="select * from customer where email='"+userEmail+ "'and password='"+ep+"'";
        DatabaseConnection Dbcon=new DatabaseConnection();
        try {
            ResultSet rs=Dbcon.getQueryTable(query);
            if(rs!=null && rs.next()){
                return new Customer(rs.getInt("cid"),rs.getString("email"),rs.getString("name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args)
    {
        System.out.println(getEcryptedPassword("abc"));
    }
}

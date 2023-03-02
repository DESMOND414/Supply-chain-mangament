package com.example.ecomm;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Ecommerce extends Application {


    private final int width=500,height=400,headerline=50;
    ProductList productList=new ProductList();
    Pane bodyPane;


    ObservableList<Product> cartItemsList= FXCollections.observableArrayList();


    Label welcomeLabel=new Label("welcome customer");
    Customer loggedIncustomer=null;
    Button signInButton=new Button("Sign in");
    Button placeOrderButton=new Button("Place Order");

    Order order=new Order();

    GridPane footerBar;
    private  void addItemsToCart(Product product){
        if(cartItemsList.contains(product)){
            return;
        }
         cartItemsList.add(product);
        System.out.println("products in  cart " +cartItemsList.stream().count());
    }
    private GridPane loginPage(){
        Label userLabel=new Label("username");
        Label passLabel=new Label("password");
        TextField userName=new TextField();
        userName.setPromptText("enter user Name");
        PasswordField password=new PasswordField();
        password.setPromptText("enter the password");
        Button loginButton=new Button("Login");
        Label messageLabel=new Label("Login-Message");
        Image image = new Image("D:\\spring\\images\\cart.png");
        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitHeight(400);
        imageView.setFitWidth(255);
        bodyPane.getChildren().add(imageView);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Pane root=new Pane();
                root.setPrefSize(width,height+2*headerline);
                String user=userName.getText();
                String pass=password.getText();
                loggedIncustomer=Login.customerLogin(user,pass);
                if(loggedIncustomer!=null){
                    messageLabel.setText("login is sucessful");
                    bodyPane.getChildren().clear();

                  bodyPane.getChildren().addAll(createContent2());
                    welcomeLabel.setText("welcome"+" " +loggedIncustomer.getName());
                }
//                if(loggedIncustomer==null){
//                    bodyPane.getChildren().clear();
//                    bodyPane.getChildren().addAll(createContent());
//                }
                else {
                    messageLabel.setText("login is not sucessful");
                }
            }
        });

        GridPane loginPane=new GridPane();


        loginPane.setTranslateY(100);
        loginPane.setTranslateX(260);
        loginPane.setHgap(10);
        loginPane.setVgap(25);
        loginPane.add(userLabel,0,0);
        loginPane.add(userName,1,0);
        loginPane.add(passLabel,0,1);
        loginPane.add(password,1,1);
        loginPane.add(loginButton,0,2);
        loginPane.add(messageLabel,1,2);
        return loginPane;

    }
    public void showDialog(String message){
        Dialog<String> dialog = new Dialog<String>();
        //Setting the title
        dialog.setTitle("order satus");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText(message);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);
        //Setting the label
            dialog.showAndWait();
    }
   private GridPane footer(){
        Button buyNowButton=new Button("BUY NOW");
        Button addToCartButton=new Button("Add To Cart");
        Button delete=new Button("Delete Orders");


       buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
              Product product=productList.getSelectedProduct();
              Boolean orderStatus=false;
              if(product!=null && loggedIncustomer!=null){
                  orderStatus=Order.placeOrder(loggedIncustomer,product);
              }
              if(orderStatus==true){
                 showDialog("Order is Successful");
              }
              else{

              }
            }
        });

       addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               Product product=productList.getSelectedProduct();
               addItemsToCart(product);
           }
       });

       placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {

               int orderCount=0;
               if(cartItemsList!=null && loggedIncustomer!=null){
                   orderCount=Order.placeMultipleOrders(cartItemsList,loggedIncustomer);
               }
               if(orderCount>0){
                   showDialog(orderCount+" Orders Placed Successful");
               }
               else{

               }
           }
       });
//       delete.setOnAction(new EventHandler<ActionEvent>() {
//           @Override
//           public void handle(ActionEvent actionEvent) {
//               bodyPane.getChildren().clear();
//               bodyPane.getChildren().add(Order.getOrders2(loggedIncustomer));
//           }
//       });
       GridPane footer=new GridPane();
       footer.setHgap(10);
       footer.setVgap(50);
       footer.setTranslateY(height+headerline+10);
        footer.add(buyNowButton,4,0);
        footer.add(addToCartButton,6,0);
        footer.add(placeOrderButton,8,0);
      buyNowButton.setStyle("-fx-background-color: #f29b72; ");
       addToCartButton.setStyle("-fx-background-color: #f29b72; ");
       placeOrderButton.setStyle("-fx-background-color: #f29b72; ");
//       footer.add(delete,9,0);
        return footer;
   }
    private Pane createContent(){
        Pane root=new Pane();
        root.setPrefSize(width,height+2*headerline);
        bodyPane=new Pane();
        bodyPane.setPrefSize(width,height);
        bodyPane.setTranslateY(headerline);
        bodyPane.setTranslateX(0);

        bodyPane.getChildren().add(loginPage());
            root.getChildren().addAll(
//                loginPage(),
//                productList.getAllProducts()
                    bodyPane
                    );


//            root.getChildren().addAll(headerBar(),
////                loginPage(),
////                productList.getAllProducts()
//                    bodyPane
//                    ,footer());

        return root;
    }
    private Pane createContent2(){
        Pane root=new Pane();
        root.setPrefSize(width,height+2*headerline);
        bodyPane=new Pane();
        bodyPane.setPrefSize(width,height);
        bodyPane.setTranslateY(headerline);
        bodyPane.setTranslateX(10);

        root.getChildren().addAll(headerBar(),
//                loginPage(),
//                productList.getAllProducts()
                bodyPane,footer()
        );


//            root.getChildren().addAll(headerBar(),
////                loginPage(),
////                productList.getAllProducts()
//                    bodyPane
//                    ,footer());

        return root;
    }
//
    private GridPane headerBar(){
     GridPane header=new GridPane();
        TextField searchBar=new TextField();
        Button searchButton=new Button("search");
        Button  cartButton=new Button("Cart");
        Button ordersButton=new Button("Orders");
        String s=searchBar.getText();

        ordersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(Order.getOrders(loggedIncustomer));
            }
        });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                  String s=searchBar.getText();
                  if(s==""){
                      bodyPane.getChildren().clear();
                      bodyPane.setTranslateX(100);
                      bodyPane.setPrefSize(100,100);
                      bodyPane.getChildren().add(productList.getAllProducts());
                  }
                  else{
                      bodyPane.getChildren().clear();
                      bodyPane.setTranslateX(50);
                      bodyPane.getChildren().add(Order.search(s));
                  }


            }
        });
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                loggedIncustomer=null;
                bodyPane.getChildren().add(loginPage());
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productList.productsInCart(cartItemsList));
            }
        });
        header.setHgap(10);
        header.setTranslateX(10);
        header.add(searchBar,0,0);
        header.add(searchButton,1,0);

        header.add(signInButton,2,0);
        header.add(welcomeLabel,3,0);
        header.add(cartButton,4,0);
        header.add(ordersButton,5,0);
        searchButton.setStyle("-fx-background-color: #f29b72; ");
        signInButton.setStyle("-fx-background-color: #f29b72; ");
        cartButton.setStyle("-fx-background-color: #f29b72; ");
        ordersButton.setStyle("-fx-background-color: #f29b72; ");

     return header;

    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Ecommerce.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Ecommerce");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
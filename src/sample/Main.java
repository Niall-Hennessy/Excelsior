package sample;

import com.sun.webkit.graphics.ScrollBarTheme;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.ColorPicker;
import java.awt.*;
import javafx.scene.control.Button;
import java.io.FileInputStream;

import java.io.FileInputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Excelsior");

        int width = (int) Screen.getPrimary().getBounds().getWidth();
        int height = (int) Screen.getPrimary().getBounds().getHeight();

        int widthPrcnt = (width / 100) * 95;
        int heightPrcnt = (height / 100) * 95;

        primaryStage.setHeight(heightPrcnt);
        primaryStage.setWidth(widthPrcnt);


        GridPane gridPane = new GridPane();


        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: #B9EBFF");

        Menu file = new Menu("File");
        MenuItem menuItem1 = new MenuItem("Save XML");
        MenuItem menuItem2 = new MenuItem("Save HTMl");
        MenuItem menuItem3 = new MenuItem("Load XML");
        MenuItem menuItem4 = new MenuItem("Load HTML");
        file.getItems().add(menuItem1);
        file.getItems().add(menuItem2);
        file.getItems().add(menuItem3);
        file.getItems().add(menuItem4);

        menuBar.getMenus().add(file);

        Menu edit = new Menu("Edit");
        menuBar.getMenus().add(edit);
        Menu view = new Menu("View");
        menuBar.getMenus().add(view);


        HBox menuBox = new HBox(menuBar);
//        menuBox.setMinWidth(960);
        menuBox.setStyle("-fx-background-color: #B9EBFF");

        GridPane bottomPane = new GridPane();
        bottomPane.setStyle("-fx-border-color: black; -fx-background-color: #FEF7D3; -fx-border-width: 3px");//pink white FFFEF1
        bottomPane.setPrefHeight(300);
        bottomPane.setPrefWidth(width);

        ColorPicker colorPicker = new ColorPicker();
        Color value = colorPicker.getValue();
        HBox colorBox = new HBox(colorPicker);
        colorBox.setAlignment(Pos.TOP_RIGHT);
       // colorBox.setPrefHeight(250);
       // colorBox.setPrefWidth(100);

        Button hairColor = new Button("Hair");
        Button skinColor = new Button("Skin");


        FileInputStream lookLeftImageInput = new FileInputStream("src/images/lookLeft.png");
        Image lookLeftImage = new Image(lookLeftImageInput);
        ImageView lookLeftImageView = new ImageView(lookLeftImage);
        lookLeftImageView.setFitHeight(50);
        lookLeftImageView.setFitWidth(50);
        Button lookLeft = new Button("", lookLeftImageView);

        FileInputStream lookRightImageInput = new FileInputStream("src/images/lookRight.png");
        Image lookRightImage = new Image(lookRightImageInput);
        ImageView lookRightImageView = new ImageView(lookRightImage);
        lookRightImageView.setFitHeight(50);
        lookRightImageView.setFitWidth(50);
        Button lookRight = new Button("", lookRightImageView);


        bottomPane.addRow(1, lookRight, lookLeft, colorBox, hairColor, skinColor);


        HBox optionBox = new HBox(bottomPane);
        optionBox.setAlignment(Pos.BOTTOM_LEFT);
        optionBox.setMargin(bottomPane, new Insets(50, 10, 10, 10));
        optionBox.setStyle("-fx-background-color: #B9EBFF");
        optionBox.setPrefHeight(400);


        ComicPanel comicPanel1 = new ComicPanel();
        ComicPanel comicPanel2 = new ComicPanel();
        ComicPanel comicPanel3 = new ComicPanel();
        ComicPanel comicPanel4 = new ComicPanel();
        ComicPanel comicPanel5 = new ComicPanel();



        HBox comicStrip = new HBox();
        comicStrip.getChildren().add(comicPanel1);
        comicStrip.getChildren().add(comicPanel2);
        comicStrip.getChildren().add(comicPanel3);
        comicStrip.getChildren().add(comicPanel4);
        comicStrip.getChildren().add(comicPanel5);
        comicStrip.setAlignment(Pos.CENTER);
        comicStrip.setMargin(comicPanel1, new Insets(10,10,10,10));
        comicStrip.setMargin(comicPanel2, new Insets(10,10,10,10));
        comicStrip.setMargin(comicPanel3, new Insets(10,10,10,10));
        comicStrip.setMargin(comicPanel4, new Insets(10,10,10,10));
        comicStrip.setMargin(comicPanel5, new Insets(10,10,10,10));
        comicStrip.setPrefHeight(280);
        comicStrip.setPrefWidth(width);
        comicStrip.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 3px");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(comicStrip);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(300);
       // ScrollBarTheme.setThickness(3);

        gridPane.addRow(0, menuBox);
        gridPane.addRow(1, scrollPane);
        gridPane.addRow(2, optionBox);
        //gridPane.setStyle("-fx-background-color: #FFFFFF");
        gridPane.setStyle("-fx-background-color: #B9EBFF");
        gridPane.setMargin(scrollPane, new Insets(0,0,0,5));

        Scene scene = new Scene(gridPane, width, height);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}

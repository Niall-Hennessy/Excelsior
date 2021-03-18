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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.awt.*;
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
        menuBar.setStyle("-fx-background-color: #F7705D");

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
        menuBox.setStyle("-fx-background-color: #F7705D");

        GridPane bottomPane = new GridPane();
        bottomPane.setStyle("-fx-border-color: black; -fx-background-color: #FFFFFF; -fx-border-width: 2px");
        bottomPane.setPrefHeight(300);
        bottomPane.setPrefWidth(width);


        HBox optionBox = new HBox(bottomPane);
        optionBox.setAlignment(Pos.BOTTOM_LEFT);
        optionBox.setMargin(bottomPane, new Insets(10, 10, 10, 10));
        optionBox.setStyle("-fx-background-color: #FFFFFF");

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
        comicStrip.setStyle("-fx-background-color: #FFFFFF");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(comicStrip);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(320);
       // scrollPane.setStyle("-fx-background-color: blue")
       // ScrollBarTheme.setThickness(3);

        gridPane.addRow(0, menuBox);
        gridPane.addRow(1, scrollPane);
        gridPane.addRow(2, optionBox);
        gridPane.setStyle("-fx-background-color: #FFFFFF");
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

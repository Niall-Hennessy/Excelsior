package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;

import java.io.FileInputStream;

import java.io.FileInputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Group root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Excelsior");

        GridPane gridPane = new GridPane();


        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: grey");

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


        Image image = new Image(new FileInputStream("src/images/confused.png"));

        HBox menuBox = new HBox(menuBar);
        menuBox.setMinWidth(960);
        menuBox.setStyle("-fx-background-color: gray");

        Button colourHair = new Button("Colour Hair");
        Button colourBody = new Button("Colour Body");
        //HBox hbox = new HBox(20, colourBody, colourHair);
        //hbox.setSpacing(50);

        GridPane bottomPane = new GridPane();
        //bottomPane.getChildren().add(new Label("Test"));
        //bottomPane.add(colourHair, 1,1,1,1); for later
        //bottomPane.add(colourBody, 2, 1, 1, 1); for later
        bottomPane.setStyle("-fx-border-color: blue");
        bottomPane.setMinSize(800, 100);


        HBox bottomBox = new HBox(bottomPane);
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setStyle("-fx-border-color: red");
        bottomBox.setMargin(bottomPane, new Insets(10, 10, 10, 10));
        //bottomBox.setMargin(colourBody, new Insets(10, 10, 10, 10));
        //bottomBox.setMargin(colourHair, new Insets(10, 10, 10, 10));

        /*
        CheckBox colourBox = new CheckBox("Hair");
        HBox colourCheckBox = new HBox(colourBox);
        //boolean isSelected = colourCheckBox.isSelected(true);

         */

        ComicPanel comicPanel = new ComicPanel();

        HBox comicStrip = new HBox();
        comicStrip.getChildren().add(comicPanel);
        comicStrip.setStyle("-fx-border-color: hotpink; -fx-border-width: 1px");


        gridPane.addRow(0, menuBox);
        gridPane.addRow(1, comicStrip);
        gridPane.add(bottomBox, 0, 1);
        //gridPane.add(colourCheckBox, 0, 2);

        Scene scene = new Scene(gridPane, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Group root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Excelsior");

        GridPane gridPane = new GridPane();


        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: grey");

        Menu file = new Menu("File");
        menuBar.getMenus().add(file);
        Menu edit = new Menu("Edit");
        menuBar.getMenus().add(edit);
        Menu view = new Menu("View");
        menuBar.getMenus().add(view);

        HBox menuBox = new HBox(menuBar);
        menuBox.setMinWidth(960);
        menuBox.setStyle("-fx-background-color: gray");

        Pane bottomPane = new Pane();
        bottomPane.getChildren().add(new Label("Test"));
        bottomPane.setStyle("-fx-background-color: green");
        bottomPane.setMinSize(500, 100);

        Button colourHair = new Button("Colour Hair");
        Button colourBody = new Button("Colour Body");
        //HBox hbox = new HBox(20, colourBody, colourHair);
        //hbox.setSpacing(50);

        HBox bottomBox = new HBox(bottomPane, colourHair, colourBody);
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setStyle("-fx-background-color: red");
        bottomBox.setMargin(bottomPane, new Insets(10, 10, 10, 10));
        bottomBox.setMargin(colourBody, new Insets(10, 10, 10, 10));
        bottomBox.setMargin(colourHair, new Insets(10, 10, 10, 10));

        /*
        CheckBox colourBox = new CheckBox("Hair");
        HBox colourCheckBox = new HBox(colourBox);
        //boolean isSelected = colourCheckBox.isSelected(true);

         */

        gridPane.addRow(0, menuBox);
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

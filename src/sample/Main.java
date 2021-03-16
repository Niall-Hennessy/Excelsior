package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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


        gridPane.addRow(0, menuBox);
        Scene scene = new Scene(gridPane, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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


        HBox menuBox = new HBox(menuBar);
        menuBox.setMinWidth(960);
        menuBox.setStyle("-fx-background-color: gray");


        ComicPanel comicPanel = new ComicPanel();
        comicPanel.setCharacter1("accusing");
        ImageView character1View = new ImageView(comicPanel.getCharacter1());
        character1View.setFitHeight(100);
        character1View.setFitWidth(100);
        character1View.setX(430);
        character1View.setY(200);
        comicPanel.setCharacter2("attacking");
        ImageView character2View = new ImageView(comicPanel.getCharacter2());
        character2View.setFitHeight(100);
        character2View.setFitWidth(100);
        character2View.setX(530);
        character2View.setY(200);
        comicPanel.getChildren().add(character2View);
        comicPanel.getChildren().add(character1View);
        comicPanel.setStyle("-fx-border-color: black");

        HBox comicStrip = new HBox();
        comicStrip.getChildren().add(comicPanel);


        gridPane.addRow(0, menuBox);
        gridPane.addRow(1, comicStrip);
        Scene scene = new Scene(gridPane, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

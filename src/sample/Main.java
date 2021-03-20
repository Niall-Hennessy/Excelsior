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
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.ColorPicker;
import java.awt.*;
import javafx.scene.control.Button;
import java.io.FileInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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


        GridPane mainPane = new GridPane();

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

        GridPane buttonLayout = new GridPane();
        buttonLayout.setStyle("-fx-border-color: black; -fx-background-color: #FEF7D3; -fx-border-width: 3px");
        buttonLayout.setPrefHeight(300);
        buttonLayout.setPrefWidth(width);

        ColorPicker skinColorPicker = new ColorPicker();
        skinColorPicker.getStyleClass().add("button");
        skinColorPicker.setStyle("-fx-color-label-visible: false ; -fx-color-rect-width: 100; -fx-color-rect-height: 100; -fx-padding: -10");
        skinColorPicker.setPrefHeight(100);
        skinColorPicker.setPrefWidth(100);

        ColorPicker hairColorPicker = new ColorPicker();
        hairColorPicker.getStyleClass().add("button");
        hairColorPicker.setStyle("-fx-color-label-visible: false ; -fx-color-rect-width: 100; -fx-color-rect-height: 100; -fx-padding: -10");
        hairColorPicker.setPrefHeight(100);
        hairColorPicker.setPrefWidth(100);

        buttonLayout.setHgap(50);
        buttonLayout.setVgap(50);
        buttonLayout.setPadding(new Insets(50,50,50,50));

        Text skin = new Text();
        skin.setText("Skin:");
        skin.setStyle("-fx-font-size: 22; -fx-font-family: 'Comic Sans MS'");
        buttonLayout.add(skin, 4, 0);

        Text hair = new Text();
        hair.setText("Hair:");
        hair.setStyle("-fx-font-size: 22; -fx-font-family: 'Comic Sans MS'");
        buttonLayout.add(hair, 4, 1);
        buttonLayout.add(skinColorPicker,5,0);
        buttonLayout.add(hairColorPicker,5,1);



        ButtonIcon buttonIcon = new ButtonIcon();
        Button lookLeft = buttonIcon.getButtonIcon("src/images/lookLeft.png");
        Button lookRight = buttonIcon.getButtonIcon("src/images/lookRight.png");
        Button flipButton = buttonIcon.getButtonIcon("src/images/swapLR.png");
        Button genderButton = buttonIcon.getButtonIcon("src/images/MFButton.png");
        Button textButton = buttonIcon.getButtonIcon("src/images/T_Button.png");
        Button bubbleButton = buttonIcon.getButtonIcon("src/images/speech_bubble.png");



        Button deleteButton = buttonIcon.getButtonIcon("src/images/trashcan.png");




        buttonLayout.addColumn(1, lookRight, lookLeft);
        buttonLayout.addColumn(2, flipButton, genderButton);
        buttonLayout.addColumn(3, textButton, bubbleButton);
        buttonLayout.addColumn(6, deleteButton);


        HBox optionBox = new HBox(buttonLayout);
        optionBox.setAlignment(Pos.BOTTOM_LEFT);
        optionBox.setMargin(buttonLayout, new Insets(50, 10, 10, 10));
        optionBox.setStyle("-fx-background-color: #B9EBFF");
        optionBox.setPrefHeight(400);


        ComicPanel comicPanel1 = new ComicPanel();

        Button newPanelLeft = buttonIcon.getButtonIcon("src/images/plus.png");
        Button newPanelRight = buttonIcon.getButtonIcon("src/images/plus.png");
        newPanelLeft.setScaleX(0.5);
        newPanelLeft.setScaleY(0.5);
        newPanelRight.setScaleX(0.5);
        newPanelRight.setScaleY(0.5);



        HBox comicStrip = new HBox();
        comicStrip.getChildren().add(newPanelLeft);
        comicStrip.getChildren().add(comicPanel1);
        comicStrip.getChildren().add(newPanelRight);
        comicStrip.setAlignment(Pos.CENTER);
        comicStrip.setMargin(comicPanel1, new Insets(10,10,10,10));
        comicStrip.setPrefHeight(280);
        comicStrip.setPrefWidth(width - 10);
        comicStrip.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 3px");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(comicStrip);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(300);

        mainPane.addRow(0, menuBox);
        mainPane.addRow(1, scrollPane);
        mainPane.addRow(2, optionBox);
        //mainPane.setStyle("-fx-background-color: #FFFFFF");
        mainPane.setStyle("-fx-background-color: #B9EBFF");
        mainPane.setMargin(scrollPane, new Insets(5,5,5,5));

        Scene scene = new Scene(mainPane, width, height);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}

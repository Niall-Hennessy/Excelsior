package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
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

        ComicPanel comicPanel = new ComicPanel();

        final String[] character = new String[1];

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

        buttonLayout.setHgap(50);
        buttonLayout.setVgap(50);
        buttonLayout.setPadding(new Insets(50,50,50,50));

//      Lower Panel Buttons and and placement
        ButtonIcon buttonIcon = new ButtonIcon();
        Button rightCharacter = buttonIcon.getButtonIcon("src/images/buttons/lookLeft.png");
        Button leftCharacter = buttonIcon.getButtonIcon("src/images/buttons/lookRight.png");
        Button flipButton = buttonIcon.getButtonIcon("src/images/buttons/swapLR.png");
        Button genderButton = buttonIcon.getButtonIcon("src/images/buttons/MFButton.png");
        Button textButton = buttonIcon.getButtonIcon("src/images/buttons/T_Button.png");
        Button bubbleButton = buttonIcon.getButtonIcon("src/images/buttons/speech_bubble.png");

        rightCharacter.setOnAction(new EventHandler<ActionEvent>() {

            final Stage addCharacter = new Stage();

            @Override
            public void handle(ActionEvent event) {
                addCharacter.initModality(Modality.APPLICATION_MODAL);
                addCharacter.initOwner(primaryStage);
                ScrollPane gallery = new ScrollPane();
                TilePane poses = new TilePane();

                File folder = new File("src/images/characters");
                File[] listOfFiles = folder.listFiles();

                for (final File file : listOfFiles)
                {
                    ImageView imageView;
                    imageView = createImageView(file);
                    poses.getChildren().addAll(imageView);

                }

                gallery.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //horizonral
                gallery.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                gallery.setFitToWidth(true);
                gallery.setContent(poses);

                addCharacter.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
                addCharacter.setHeight(Screen.getPrimary().getVisualBounds().getHeight());

                Scene scene = new Scene(gallery);
                addCharacter.setScene(scene);
                addCharacter.show();
            }

            private ImageView createImageView(final File imageFile) {

                ImageView imageView = null;
                try {
                    final Image image = new Image(new FileInputStream(imageFile), 150, 0, true,
                            true);
                    imageView = new ImageView(image);
                    imageView.setFitWidth(150);
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent mouseEvent) {

                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                                if (mouseEvent.getClickCount() == 2) {
                                    try {
                                        comicPanel.setRightCharacter(imageFile.getPath());
                                        addCharacter.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }
                    });
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                return imageView;
            }
        });

        leftCharacter.setOnAction(new EventHandler<ActionEvent>() {

            final Stage addCharacter = new Stage();

            @Override
            public void handle(ActionEvent event) {
                addCharacter.initModality(Modality.APPLICATION_MODAL);
                addCharacter.initOwner(primaryStage);
                ScrollPane gallery = new ScrollPane();
                TilePane poses = new TilePane();

                File folder = new File("src/images/characters");
                File[] listOfFiles = folder.listFiles();

                for (final File file : listOfFiles)
                {
                    ImageView imageView;
                    imageView = createImageView(file);
                    poses.getChildren().addAll(imageView);

                }

                gallery.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //horizonral
                gallery.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                gallery.setFitToWidth(true);
                gallery.setContent(poses);

                addCharacter.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
                addCharacter.setHeight(Screen.getPrimary().getVisualBounds().getHeight());

                Scene scene = new Scene(gallery);
                addCharacter.setScene(scene);
                addCharacter.show();
            }

            private ImageView createImageView(final File imageFile) {

                ImageView imageView = null;
                try {
                    final Image image = new Image(new FileInputStream(imageFile), 150, 0, true,
                            true);
                    imageView = new ImageView(image);
                    imageView.setFitWidth(150);
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent mouseEvent) {

                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                                if (mouseEvent.getClickCount() == 2) {
                                    try {
                                        comicPanel.setLeftCharacter(imageFile.getPath());
                                        addCharacter.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }
                    });
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                return imageView;
            }
        });

        flipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(character[0] == null || character[0].matches("none"))
                    return;

                System.out.println("Charater 0 is: " + character[0]);

                comicPanel.flipOrientation(character[0]);

            }
        });

        Text skin = new Text();
        skin.setText("Skin:");
        skin.setStyle("-fx-font-size: 36; -fx-font-family: 'Lucida Console'");

        Text hair = new Text();
        hair.setText("Hair:");
        hair.setStyle("-fx-font-size: 36; -fx-font-family: 'Lucida Console'");

        ColorPicker skinColorPicker = new ColorPicker();
        ColorPicker hairColorPicker = new ColorPicker();
        skinColorPicker = ButtonIcon.colorPickerStyling(skinColorPicker);
        hairColorPicker = ButtonIcon.colorPickerStyling(hairColorPicker);

        Button deleteButton = buttonIcon.getButtonIcon("src/images/buttons/delete.png");

        buttonLayout.addColumn(5, leftCharacter, rightCharacter);
        buttonLayout.addColumn(6, flipButton, genderButton);
        buttonLayout.addColumn(7, textButton, bubbleButton);
        buttonLayout.addColumn(18, skin, hair);
        buttonLayout.addColumn(19, skinColorPicker, hairColorPicker);
        buttonLayout.addColumn(25, deleteButton);

        HBox optionBox = new HBox(buttonLayout);
        optionBox.setAlignment(Pos.BOTTOM_LEFT);
        optionBox.setMargin(buttonLayout, new Insets(50, 10, 10, 10));
        optionBox.setStyle("-fx-background-color: #B9EBFF");
        optionBox.setPrefHeight(400);

        Button newPanelLeft = buttonIcon.getButtonIcon("src/images/buttons/plus.png");
        Button newPanelRight = buttonIcon.getButtonIcon("src/images/buttons/plus.png");
        newPanelLeft.setScaleX(0.5);
        newPanelLeft.setScaleY(0.5);
        newPanelRight.setScaleX(0.5);
        newPanelRight.setScaleY(0.5);
        newPanelLeft.setStyle("-fx-border-color: black");

        HBox comicStrip = new HBox();
        comicStrip.getChildren().add(newPanelLeft);
        comicStrip.getChildren().add(comicPanel);
        comicStrip.getChildren().add(newPanelRight);
        comicStrip.setAlignment(Pos.CENTER);
        comicStrip.setMargin(comicPanel, new Insets(10,10,10,10));
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
        mainPane.setStyle("-fx-background-color: #B9EBFF");
        mainPane.setMargin(scrollPane, new Insets(5,5,5,5));

        Scene scene = new Scene(mainPane, width, height);

        comicPanel.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                System.out.println("X: " + x);
                System.out.println("Y: " + y);

                if(x < 115 && x > 3 && y > 110 && y < 205)

                    character[0] = "left";
                else if(x < 280 && x > 170 && y > 110 && y < 205)
                    character[0] = "right";
                else
                    character[0] = "none";

                comicPanel.selectCharacter(character[0]);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

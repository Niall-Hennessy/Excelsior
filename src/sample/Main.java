package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.MouseAdapter;
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

        final ColorPicker[] skinColorPicker = {new ColorPicker()};
        final ColorPicker[] hairColorPicker = {new ColorPicker()};
        skinColorPicker[0] = ButtonIcon.colorPickerStyling(skinColorPicker[0]);
        hairColorPicker[0] = ButtonIcon.colorPickerStyling(hairColorPicker[0]);

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
        Button deleteButton = buttonIcon.getButtonIcon("src/images/buttons/delete.png");



        final Stage toolTip = new Stage();
        toolTip.initStyle(StageStyle.UNDECORATED);

        HoverTips hoverTips = new HoverTips();
        hoverTips.setToolTip(toolTip);

        String tipRightCharacter    = "Set Right Character";
        String tipLeftCharacter     = "Set Left Character";
        String tipFlipButton        = "Flip Direction of the Selected Character";
        String tipGenderButton      = "Change Selected Character's Gender";
        String tipTextButton        = "Set Caption Text for Bottom or Top of Panel";
        String tipBubbleButton      = "Insert Text Bubble for Selected Character";
        String tipDeleteButton      = "Delete Selected Object";
        String tipskinColorPicker   = "Choose Skin Colour";
        String tiphairColorPicker   = "Choose Hair Colour";

        rightCharacter.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipRightCharacter, mouseEvent, rightCharacter);
            }
        });

        leftCharacter.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipLeftCharacter, mouseEvent, leftCharacter);
            }
        });

        flipButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipFlipButton, mouseEvent, flipButton);
            }
        });

        genderButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipGenderButton, mouseEvent, genderButton);
            }
        });

        textButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipTextButton, mouseEvent, textButton);
            }
        });

        bubbleButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipBubbleButton, mouseEvent, bubbleButton);
            }
        });

        deleteButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipDeleteButton, mouseEvent, deleteButton);
            }
        });

        skinColorPicker[0].setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.colorToolTip(tipskinColorPicker, mouseEvent, skinColorPicker[0]);
            }
        });

        hairColorPicker[0].setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.colorToolTip(tiphairColorPicker, mouseEvent, hairColorPicker[0]);
            }
        });

        rightCharacter.setOnAction(new EventHandler<ActionEvent>() {

            final Stage addCharacter = new Stage();

            @Override
            public void handle(ActionEvent event) {

                if(addCharacter.isShowing()) {
                    addCharacter.initModality(Modality.APPLICATION_MODAL);
                    addCharacter.initOwner(primaryStage);
                }
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
                                        character[0] = "right";
                                        skinColorPicker[0].setValue(comicPanel.getRightCharacterSkin());
                                        hairColorPicker[0].setValue(comicPanel.getRightCharacterHair());
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

                if(addCharacter.isShowing()) {
                    addCharacter.initModality(Modality.APPLICATION_MODAL);
                    addCharacter.initOwner(primaryStage);
                }
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
                                        character[0] = "left";
                                        skinColorPicker[0].setValue(comicPanel.getLeftCharacterSkin());
                                        hairColorPicker[0].setValue(comicPanel.getLeftCharacterHair());
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

        genderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(character[0] == null || character[0].matches("none"))
                    return;

                comicPanel.genderSwap(character[0]);
            }
        });

        //pop up for when they hit the bubble button for speech
        bubbleButton.setOnAction(new EventHandler<ActionEvent>() {

            final Stage addBubble = new Stage(StageStyle.UNDECORATED);

            Pane bubbleDisplay = new Pane();
            ImageView bubbleImageView = new ImageView();

            private double xOffset = 0;
            private double yOffset = 0;

            @Override
            public void handle(ActionEvent event) {

                if(character[0] == null || character[0].matches("none")) {
                    return;
                }

                Button submit = new Button("Submit");
                submit.getStyleClass().add("submit");
                Button escape = new Button("X");
                escape.getStyleClass().add("escape");
                Button cancel = new Button("Cancel");
                cancel.getStyleClass().add("cancel");
                Button italic = new Button("Italic");
                italic.getStyleClass().add("italic");
                Button bold = new Button("Bold");
                bold.getStyleClass().add("bold");
                Button fonts = new Button("Font");
                fonts.getStyleClass().add("fonts");


//                ComboBox fontDrop = new ComboBox();
//
//                fontDrop.getItems().add("Choice 1");
//                fontDrop.getItems().add("Choice 2");
//
//                HBox fontbox = new HBox(fontDrop);

                bubbleDisplay.getChildren().add(bubbleImageView);

                if(addBubble.isShowing()) {
                    addBubble.initModality(Modality.APPLICATION_MODAL);
                    addBubble.initOwner(primaryStage);
                }

                ScrollPane bubbleGallery = new ScrollPane();
                bubbleGallery.getStyleClass().add("bubbleGallery");
                TilePane bubbles = new TilePane();
                bubbles.getStyleClass().add("bubbles");
                bubbles.setMinHeight(120);


                File folder = new File("src/images/bubbles");
                File[] listOfFiles = folder.listFiles();

                TextField textfield = new TextField();

                final boolean[] isBold = {false};
                final boolean[] isItalic = {false};

                if(character[0].matches("left") && comicPanel.leftTextBubble != null) {
                    textfield.setText(comicPanel.leftTextBubble.getText().getText().replaceAll("\n", " "));
                }
                else if(character[0].matches("right") && comicPanel.rightTextBubble != null)
                    textfield.setText(comicPanel.rightTextBubble.getText().getText());


                final Text[] characterCounter = {new Text(textfield.getText().length() + "/50")};

                textfield.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                        characterCounter[0].setText(Integer.toString(textfield.getText().length()) + "/50");
                        if (textfield.getText().length() > 50) {
                            String s = textfield.getText().substring(0, 50);
                            textfield.setText(s);
                        }
                    }
                });

                HBox textbox = new HBox(textfield);
                textfield.setPrefWidth(800);
                textfield.setPrefHeight(50);


                for (final File file : listOfFiles)
                {
                    ImageView imageView;
                    imageView = createImageView(file);
                    bubbles.getChildren().addAll(imageView);

                }

                addBubble.setWidth(Screen.getPrimary().getVisualBounds().getWidth()/1.5);
                addBubble.setHeight(Screen.getPrimary().getVisualBounds().getHeight()/1.2);

                bubbleGallery.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //horizontal
                bubbleGallery.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                bubbleGallery.setFitToWidth(true);
                bubbleGallery.setContent(bubbles);

                bubbleGallery.setPrefWidth(addBubble.getWidth());
                textbox.setPrefWidth(addBubble.getWidth());



                GridPane bubbleGrid = new GridPane();
                bubbleGrid.setPadding(new Insets(10, 10, 10, 10));
                bubbleGrid.setVgap(5);
                bubbleGrid.setHgap(5);
                bubbleGrid.getStyleClass().add("bubbleGrid");

                // page.add(Node, colIndex, rowIndex, colSpan, rowSpan):
                bubbleGrid.add(escape, 4, 1, 1, 1);
                bubbleGrid.add(bubbleGallery, 0, 2, 3, 3);
                bubbleGrid.setMargin(bubbleGallery, new Insets (10, 10, 10, 30));//its 30 to balance out the x buttons margins and get the otherside right
                bubbleGrid.add(bubbleDisplay, 0, 5, 3, 3);
                bubbleGrid.setMargin(bubbleDisplay, new Insets (10, 10, 10, 30));
                bubbleGrid.add(italic, 0,8, 1, 1 );
                bubbleGrid.setMargin(italic, new Insets (5, 2, 2, 30));
                bubbleGrid.add(bold, 1, 8, 1, 1);
                bubbleGrid.setMargin(bold, new Insets (5, 2, 2, 0));
                bubbleGrid.add(characterCounter[0],2, 11, 1, 1);
                bubbleGrid.setMargin(characterCounter[0], new Insets (2, 2, 2, 2));
                bubbleGrid.add(textbox, 0, 9, 3, 1);
                bubbleGrid.setMargin(textbox, new Insets (10, 10, 10, 30));
                bubbleGrid.add(submit, 0, 11, 1, 1);
                bubbleGrid.setMargin(submit, new Insets (2, 2, 2, 30));
                bubbleGrid.add(cancel,1, 11, 1, 1);
                bubbleGrid.setMargin(cancel, new Insets (2, 2, 2, 2));

                bold.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        isBold[0] = !isBold[0];

                        if(isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(!isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(isBold[0] && !isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, textfield.getFont().getSize()));
                        else
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.REGULAR, textfield.getFont().getSize()));
                    }
                });

                italic.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        isItalic[0] = !isItalic[0];

                        if(isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(!isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(isBold[0] && !isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, textfield.getFont().getSize()));
                        else
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.REGULAR, textfield.getFont().getSize()));
                    }
                });

                cancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        bubbleDisplay.getChildren().remove(bubbleImageView);
                        addBubble.close();
                    }
                });

                escape.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        bubbleDisplay.getChildren().remove(bubbleImageView);
                        addBubble.close();
                    }
                });

                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if(textfield.getText().replaceAll("\\s", "").matches(""))
                            return;

                        if(textfield.getText().matches("") || ((ImageView)bubbleDisplay.getChildren().get(0)).getImage() == null)
                            return;

                        if(isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(!isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(isBold[0] && !isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, textfield.getFont().getSize()));
                        else
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.REGULAR, textfield.getFont().getSize()));

                        if(character[0].matches("left"))
                            comicPanel.setLeftBubble(((ImageView)bubbleDisplay.getChildren().get(0)).getImage(), textfield.getText(), textfield.getFont());
                        else if(character[0].matches("right"))
                            comicPanel.setRightBubble(((ImageView)bubbleDisplay.getChildren().get(0)).getImage(), textfield.getText(), textfield.getFont());


                        bubbleDisplay.getChildren().remove(bubbleImageView);
                        addBubble.close();
                    }
                });

                Scene scene = new Scene(bubbleGrid);
                addBubble.setScene(scene);

                scene.getStylesheets().add("sample/style.css");

                scene.setOnMousePressed(pressEvent -> {
                    scene.setOnMouseDragged(dragEvent -> {
                        addBubble.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                        addBubble.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                    });
                });

                addBubble.show();
            }

            private ImageView createImageView(final File imageFile) {

                ImageView imageView = null;
                try {
                    final Image image = new Image(new FileInputStream(imageFile), 150, 150, true,
                            true);
                    imageView = new ImageView(image);
                    //imageView.setFitWidth(150);
                    imageView.setPickOnBounds(true);
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent mouseEvent) {

                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                                if (mouseEvent.getClickCount() == 1) {
                                        ((ImageView)bubbleDisplay.getChildren().get(0)).setImage(image);
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


        skinColorPicker[0].setOnAction(new EventHandler() {
            public void handle(Event t) {
                if(character[0].matches("left"))
                    comicPanel.setLeftCharacterSkin(skinColorPicker[0].getValue());
                else if(character[0].matches("right"))
                    comicPanel.setRightCharacterSkin(skinColorPicker[0].getValue());
            }
        });

        hairColorPicker[0].setOnAction(new EventHandler() {
            public void handle(Event t) {
                if(character[0].matches("left"))
                    comicPanel.setLeftCharacterHair(hairColorPicker[0].getValue());
                else if(character[0].matches("right"))
                    comicPanel.setRightCharacterHair(hairColorPicker[0].getValue());
            }
        });

        Text skin = new Text();
        skin.setText("Skin:");
        skin.setStyle("-fx-font-size: 36; -fx-font-family: 'Lucida Console'");

        Text hair = new Text();
        hair.setText("Hair:");
        hair.setStyle("-fx-font-size: 36; -fx-font-family: 'Lucida Console'");


        buttonLayout.addColumn(5, leftCharacter, rightCharacter);
        buttonLayout.addColumn(6, flipButton, genderButton);
        buttonLayout.addColumn(7, textButton, bubbleButton);
        buttonLayout.addColumn(18, skin, hair);
        buttonLayout.addColumn(19, skinColorPicker[0], hairColorPicker[0]);
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

        Scene scene = new Scene(mainPane, width, height, false);

        comicPanel.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();

                System.out.println("X: " + x);
                System.out.println("Y: " + y);

                if(x <= 110 && x >= 10 && y >= 100 && y <= 200) {
                    skinColorPicker[0].setValue(comicPanel.getLeftCharacterSkin());
                    hairColorPicker[0].setValue(comicPanel.getLeftCharacterHair());
                    character[0] = "left";
                }
                else if(x <= 340 && x >= 240 && y >= 100 && y <= 200) {
                    skinColorPicker[0].setValue(comicPanel.getRightCharacterSkin());
                    hairColorPicker[0].setValue(comicPanel.getRightCharacterHair());
                    character[0] = "right";
                }
                else {
                    skinColorPicker[0].setValue(new Color(1,1,1,1));
                    hairColorPicker[0].setValue(new Color(1,1,1,1));
                    character[0] = "none";
                }

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

package ux;

import comic.ComicPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import undo.Undo;
import undo.UndoList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

enum Entity{
    rightCharacter,
    leftCharacter,
    background,
    bubble
}

public class GalleryManager {

    private ComicPanel[] comicPanel = null;
    final Stage addCharacter = new Stage();
    private double height;

    Pane bubbleDisplay = new Pane();
    String bubbleName = null;

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setComicPanel(ComicPanel[] comicPanel) {
        this.comicPanel = comicPanel;
    }

    private void openGallery(String path, Entity entity) {
        ScrollPane gallery = new ScrollPane();
        TilePane poses = new TilePane();

        if(entity == Entity.background)
            poses.setPrefColumns(3);
        else
            poses.setPrefColumns(7);

        poses.setHgap(3);
        poses.setVgap(3);

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles)
        {
            ImageView imageView;
            imageView = createImageView(file, entity);
            poses.getChildren().addAll(imageView);
        }

        gallery.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //horizontal
        gallery.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        gallery.setFitToWidth(true);
        gallery.setFitToHeight(true);
        gallery.setContent(poses);

        Scene scene = new Scene(gallery);

        addCharacter.setScene(scene);
        addCharacter.setTitle("Click to select");
        addCharacter.show();
        addCharacter.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight());
        addCharacter.setX( (Screen.getPrimary().getVisualBounds().getWidth()/2) - (addCharacter.getWidth()/2) );
        addCharacter.setY( (Screen.getPrimary().getVisualBounds().getHeight()/2) - (addCharacter.getHeight()/2) );

        if(addCharacter.getHeight() > Screen.getPrimary().getVisualBounds().getHeight()) {
            addCharacter.setY(0);
        }
    }

    private void openBubbleOptions(String path, Entity entity) {
        final Stage addBubble = new Stage(StageStyle.UNDECORATED);
        ImageView bubbleImageView = new ImageView();

        double xOffset = 0;
        double yOffset = 0;

        Button submit = new Button("Submit");
        submit.getStyleClass().add("submit");
        Button escape = new Button("X");
        escape.getStyleClass().add("escape");
        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("cancel");
        Button delete = new Button("Delete");
        delete.getStyleClass().add("cancel");
        Button italic = new Button("Italic");
        italic.getStyleClass().add("italic");
        Button bold = new Button("Bold");
        bold.getStyleClass().add("bold");
        Button fonts = new Button("Font");
        fonts.getStyleClass().add("fonts");

        if(bubbleDisplay.getChildren().size() == 0)
            bubbleDisplay.getChildren().add(bubbleImageView);

        bubbleDisplay.setMinHeight(bubbleImageView.getFitHeight()+100);

        HBox bubbleGallery = new HBox();
        bubbleGallery.getStyleClass().add("bubbles");

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        TextField textfield = new TextField();

        final boolean[] isBold = {false};
        final boolean[] isItalic = {false};

        if(comicPanel[0].getLeftTextBubble() != null) {
            if(comicPanel[0].getLeftTextBubble().getText().getFont().toString().contains("Bold"))
                isBold[0] = true;
            if(comicPanel[0].getLeftTextBubble().getText().getFont().toString().contains("Italic"))
                isItalic[0] = true;
        }

        if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getLeftCharacter()) && comicPanel[0].getLeftTextBubble() != null) {

            if(comicPanel[0].getLeftTextBubble() != null) {
                if(comicPanel[0].getLeftTextBubble().getText().getFont().toString().contains("Bold"))
                    isBold[0] = true;
                if(comicPanel[0].getLeftTextBubble().getText().getFont().toString().contains("Italic"))
                    isItalic[0] = true;
            }

            textfield.setText(comicPanel[0].getLeftTextBubble().getText().getText().replaceAll("\n", ""));
        }
        else if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getRightCharacter()) && comicPanel[0].getRightTextBubble() != null) {

            if(comicPanel[0].getRightTextBubble() != null) {
                if(comicPanel[0].getRightTextBubble().getText().getFont().toString().contains("Bold"))
                    isBold[0] = true;
                if(comicPanel[0].getRightTextBubble().getText().getFont().toString().contains("Italic"))
                    isItalic[0] = true;
            }

            textfield.setText(comicPanel[0].getRightTextBubble().getText().getText().replaceAll("\n", ""));
        }

        if(isBold[0] && isItalic[0])
            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.ITALIC, textfield.getFont().getSize()));
        else if(!isBold[0] && isItalic[0])
            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.ITALIC, textfield.getFont().getSize()));
        else if(isBold[0] && !isItalic[0])
            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, textfield.getFont().getSize()));
        else
            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.REGULAR, textfield.getFont().getSize()));

        HBox textbox = new HBox(textfield);
        textfield.setPrefWidth(800);
        textfield.setPrefHeight(50);

        for (final File file : listOfFiles)
        {
            ImageView imageView;
            imageView = createImageView(file, entity);
            bubbleGallery.getChildren().add(imageView);
        }

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
        bubbleGrid.add(textbox, 0, 9, 3, 1);
        bubbleGrid.setMargin(textbox, new Insets (10, 10, 1, 30));
        bubbleGrid.add(submit, 0, 12, 1, 1);
        bubbleGrid.setMargin(submit, new Insets (2, 2, 2, 30));
        bubbleGrid.add(cancel,1, 12, 1, 1);
        bubbleGrid.setMargin(cancel, new Insets (2, 2, 2, 2));
        bubbleGrid.add(delete,2, 12, 1, 1);
        bubbleGrid.setMargin(delete, new Insets (2, 11, 2, 2));

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

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                comicPanel[0].removeBubble();
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

                Undo undo;

                if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getLeftCharacter())) {
                    if (comicPanel[0].getLeftTextBubble() == null) {
                        undo = new Undo("bubble", comicPanel[0], "left");
                        undo.setObj(comicPanel[0].getLeftTextBubble());
                    }
                    else {
                        undo = new Undo("bubble", comicPanel[0], "left");
                        undo.setObj(comicPanel[0].getLeftTextBubble());
                    }
                }

                else
                {
                    if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getRightCharacter())) {
                        undo = new Undo("bubble", comicPanel[0], "right");
                        undo.setObj(comicPanel[0].getRightTextBubble());
                    }
                    else
                    {
                        undo = new Undo("bubble", comicPanel[0], "right");
                        undo.setObj(comicPanel[0].getRightTextBubble());
                    }

                }

                UndoList.addUndo(undo);

                if(isBold[0] && isItalic[0])
                    textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.ITALIC, textfield.getFont().getSize()));
                else if(!isBold[0] && isItalic[0])
                    textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.ITALIC, textfield.getFont().getSize()));
                else if(isBold[0] && !isItalic[0])
                    textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, textfield.getFont().getSize()));
                else
                    textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.REGULAR, textfield.getFont().getSize()));

                if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getLeftCharacter()))
                    comicPanel[0].setLeftBubble(((ImageView)bubbleDisplay.getChildren().get(0)).getImage(), textfield.getText(), textfield.getFont(), bubbleName);
                else if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getRightCharacter()))
                    comicPanel[0].setRightBubble(((ImageView)bubbleDisplay.getChildren().get(0)).getImage(), textfield.getText(), textfield.getFont(), bubbleName);

                bubbleDisplay.getChildren().remove(bubbleImageView);
                addBubble.close();
            }
        });

        Scene scene = new Scene(bubbleGrid);
        addBubble.setScene(scene);

        scene.getStylesheets().add("main/style.css");

        scene.setOnMousePressed(pressEvent -> {
            scene.setOnMouseDragged(dragEvent -> {
                addBubble.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                addBubble.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });

        addBubble.show();
    }

    private ImageView createImageView(final File imageFile, Entity entity) {

        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 150, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);

            switch(entity) {
                case rightCharacter:
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(mouseEvent.getClickCount() == 1) {
                                try {
                                    comicPanel[0].setRightCharacter(imageFile.getPath());
                                    addCharacter.close();
                                    comicPanel[0].setSelectedCharacter(comicPanel[0].getRightCharacter());
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    break;
                case leftCharacter:
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {

                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                                if (mouseEvent.getClickCount() == 1) {
                                    try {
                                        comicPanel[0].setLeftCharacter(imageFile.getPath());
                                        addCharacter.close();
                                        comicPanel[0].setSelectedCharacter(comicPanel[0].getLeftCharacter());
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
                    break;
                case background:
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                                if (mouseEvent.getClickCount() == 1) {
                                    comicPanel[0].setBackgroundString(normaliseURL(imageFile.getPath().substring(4)));

                                    comicPanel[0].setStyle("-fx-background-image: url('" + comicPanel[0].getBackgroundString() + "'); " +
                                            "-fx-background-position: center center; " +
                                            "-fx-background-repeat: stretch; "  +
                                            "-fx-background-size: " + (height/2.4 + height/9.6) + " " + height/2.4 + ";" +
                                            "-fx-border-color: GOLD; " +
                                            "-fx-border-width: 5");
                                    addCharacter.close();
                                }
                            }
                        }
                    });
                    break;
                case bubble:
                    int width = (int) image.getWidth();
                    int height = (int) image.getHeight();

                    WritableImage writableImage = new WritableImage(width, height);

                    PixelReader pixelReader = image.getPixelReader();
                    PixelWriter pixelWriter = writableImage.getPixelWriter();

                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            Color color = pixelReader.getColor(x, y);

                            if (color.equals(Color.rgb(255, 254, 255))) {
                                pixelWriter.setColor(x, y, Color.rgb(0, 0, 0, 0));
                            }
                            else
                                pixelWriter.setColor(x, y, color);
                        }
                    }

                    imageView = new ImageView(writableImage);
                    imageView.setPickOnBounds(true);
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent mouseEvent) {

                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                                if (mouseEvent.getClickCount() == 1) {
                                    ((ImageView)bubbleDisplay.getChildren().get(0)).setImage(writableImage);
                                    bubbleName = imageFile.getPath().substring(19);
                                }
                            }
                        }
                    });

                    break;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }

    private String normaliseURL(String url) {
        return url.replace('\\', '/');
    }

    public void setRightCharacter(String path) {
        Entity entity = Entity.rightCharacter;
        openGallery(path, entity);
    }

    public void setLeftCharacter(String path) {
        Entity entity = Entity.leftCharacter;
        openGallery(path, entity);
    }

    public void setBackground(String path) {
        Entity entity = Entity.background;
        openGallery(path, entity);
    }

    public void setBubble(String path) {
        Entity entity = Entity.bubble;
        openBubbleOptions(path, entity);
    }
}

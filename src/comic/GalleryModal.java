package comic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import ux.GalleryManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GalleryModal extends Modal {

    protected Pane galleryPane = new Pane();
    ImageView galleryImageView = new ImageView();
    TextField textfield = new TextField();
    Pane bubbleDisplay = new Pane();
    String bubbleName = "";

    public GalleryModal(double width, double height, ComicPanel cPanel) {
        super(width, height, cPanel);
    }

    @Override
    public void addCancel() {
        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("cancel");

        layoutGrid.add(cancel,1, 12, 1, 1);
        layoutGrid.setMargin(cancel,new Insets (2, 2, 2, 2));

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                modal.close();
            }
        });
    }

    @Override
    public void addEscape() {
        Button escape = new Button("X");
        escape.getStyleClass().add("escape");

        layoutGrid.add(escape, 4, 1, 1, 1);
        layoutGrid.setMargin(escape, new Insets (0, 0, 0, 0));

        escape.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                modal.close();
            }
        });
    }

    @Override
    public void addSubmit() {
        Button submit = new Button("Submit");
        submit.getStyleClass().add("submit");

        layoutGrid.add(submit, 0, 12, 1, 1);
        layoutGrid.setMargin(submit, new Insets (2, 2, 2, 30));

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(comicPanel.getSelectedCharacter().equals(comicPanel.getLeftCharacter()))
                    comicPanel.setLeftBubble(((ImageView) bubbleDisplay.getChildren().get(0)).getImage(), textfield.getText(), textfield.getFont(), bubbleName);
                else if(comicPanel.getSelectedCharacter().equals(comicPanel.getRightCharacter()))
                    comicPanel.setRightBubble(((ImageView)bubbleDisplay.getChildren().get(0)).getImage(), textfield.getText(), textfield.getFont(), bubbleName);

                modal.close();
            }
        });
    }

    @Override
    public void addDelete() {
        Button delete = new Button("Delete");
        delete.getStyleClass().add("cancel");

        layoutGrid.add(delete,2, 12, 1, 1);
        layoutGrid.setMargin(delete, new Insets (2, 11, 2, 2));

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                comicPanel.removeBubble();
                modal.close();
            }
        });
    }

    @Override
    public void addTextField() {

        textfield.setText("");

        if(comicPanel.getSelectedCharacter().equals(comicPanel.getLeftCharacter()) && comicPanel.getLeftTextBubble()!= null) {
            textfield.setText(comicPanel.getLeftTextBubble().getText().getText());
            textfield.setFont(comicPanel.getLeftTextBubble().getText().getFont());
        }

        if(comicPanel.getSelectedCharacter().equals(comicPanel.getRightCharacter()) && comicPanel.getRightTextBubble()!= null) {
            textfield.setText(comicPanel.getRightTextBubble().getText().getText());
            textfield.setFont(comicPanel.getRightTextBubble().getText().getFont());
        }

        textfield.getStyleClass().add("capTextField");
        textfield.setPrefWidth(800);
        textfield.setPrefHeight(50);

        layoutGrid.add(textfield, 0, 9, 3, 1);
        layoutGrid.setMargin(textfield, new Insets (10, 10, 1, 30));
    }

    public void addGallery(){
        if(galleryPane.getChildren().size() == 0)
            galleryPane.getChildren().add(galleryImageView);

        galleryPane.setMinHeight(galleryImageView.getFitHeight()+100);

        HBox bubbleGallery = new HBox();
        bubbleGallery.getStyleClass().add("bubbles");

        File folder = new File("src/images/bubbles");
        File[] listOfFiles = folder.listFiles();

        GalleryManager galleryManager = new GalleryManager();

        for (final File file : listOfFiles)
                {
                    ImageView imageView;
                    imageView = galleryManager.createImageView(file, bubbleDisplay, bubbleName);
                    bubbleGallery.getChildren().add(imageView);
                }

        System.out.println(bubbleName);

        layoutGrid.add(bubbleGallery, 0, 2, 3, 3);
        layoutGrid.setMargin(bubbleGallery, new Insets(10, 10, 10, 30));

        layoutGrid.add(bubbleDisplay, 0, 5, 3, 3);
        layoutGrid.setMargin(bubbleDisplay, new Insets (10, 10, 10, 30));
    }

    public void addFormatButtons(){

        Button italic = new Button("Italic");
        italic.getStyleClass().add("italic");
        Button bold = new Button("Bold");
        bold.getStyleClass().add("bold");

        final boolean[] isBold = {false};
        final boolean[] isItalic = {false};

        layoutGrid.add(italic, 0,8, 1, 1 );
        layoutGrid.setMargin(italic, new Insets (5, 2, 2, 30));
        layoutGrid.add(bold, 1, 8, 1, 1);
        layoutGrid.setMargin(bold, new Insets (5, 2, 2, 0));

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

    }
}

package comic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import undo.Undo;
import undo.UndoList;
import ux.Entity;
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

                Undo undo = null;

                if(comicPanel.getSelectedCharacter().equals(comicPanel.getLeftCharacter())) {
                    if (comicPanel.getLeftTextBubble() == null) {
                        undo = new Undo("bubble", comicPanel, "left");
                        undo.setObj(null);
                    }
                    else {
                        undo = new Undo("bubble", comicPanel, "left");
                        undo.setObj(comicPanel.getLeftTextBubble());
                    }
                }
                else if(comicPanel.getSelectedCharacter().equals(comicPanel.getRightCharacter()))
                {
                    if(comicPanel.getRightTextBubble() == null) {
                        undo = new Undo("bubble", comicPanel, "right");
                        undo.setObj(null);
                    }
                    else
                    {
                        undo = new Undo("bubble", comicPanel, "right");
                        undo.setObj(comicPanel.getRightTextBubble());
                    }
                }

                if(undo != null)
                    UndoList.addUndo(undo);

                if(((ImageView) bubbleDisplay.getChildren().get(0)).getImage() == null || textfield.getText().matches("")) {
                    return;
                }

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

        galleryImageView.setFitHeight(150);
        galleryImageView.setFitWidth(150);

        if(galleryPane.getChildren().size() == 0)
            galleryPane.getChildren().add(galleryImageView);

        if(bubbleDisplay.getChildren().size() == 0)
            bubbleDisplay.getChildren().add(galleryImageView);

        if(comicPanel.getSelectedCharacter().equals(comicPanel.getLeftCharacter()) && comicPanel.getLeftTextBubble() != null){
            ((ImageView)bubbleDisplay.getChildren().get(0)).setImage(comicPanel.getLeftTextBubble().getBubble().getImage());
        }

        if(comicPanel.getSelectedCharacter().equals(comicPanel.getRightCharacter()) && comicPanel.getRightTextBubble() != null){
            ((ImageView)bubbleDisplay.getChildren().get(0)).setImage(comicPanel.getRightTextBubble().getBubble().getImage());
        }

        galleryPane.setMinHeight(galleryImageView.getFitHeight()+100);

        HBox bubbleGallery = new HBox();
        bubbleGallery.getStyleClass().add("bubbles");

        File folder = new File("src/images/bubbles");
        File[] listOfFiles = folder.listFiles();

        GalleryManager galleryManager = new GalleryManager();

        for (final File file : listOfFiles)
                {
                    ImageView imageView;
                    imageView = galleryManager.createImageView(file, Entity.bubble);
                    bubbleGallery.getChildren().add(imageView);
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent mouseEvent) {

                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                                if (mouseEvent.getClickCount() == 1) {
                                    ((ImageView)bubbleDisplay.getChildren().get(0)).setImage(imageView.getImage());
                                    bubbleName = file.getPath().substring(19);
                                }
                            }
                        }
                    });
                }

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

                  if(textfield.getFont().toString().contains("Bold"))
                      isBold[0] = false;
                  else
                      isBold[0] = true;

                  if(textfield.getFont().toString().contains("Italic"))
                      isItalic[0] = true;
                  else
                      isItalic[0] = false;

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

                if(textfield.getFont().toString().contains("Bold"))
                    isBold[0] = true;
                else
                    isBold[0] = false;

                if(textfield.getFont().toString().contains("Italic"))
                    isItalic[0] = false;
                else
                    isItalic[0] = true;

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

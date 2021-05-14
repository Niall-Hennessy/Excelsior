package comic;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.File;

public class GalleryModal extends Modal {

    protected Pane galleryPane = new Pane();
    ImageView galleryImageView = new ImageView();

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

        layoutGrid.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
                Node node = c.getList().get(c.getList().size()-1);
            }
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
            }
        });
    }

    @Override
    public void addTextField() {
        TextField textField = new TextField();

        textField.setText(comicPanel.getTopText().getText());

        textField.getStyleClass().add("capTextField");
        textField.setPrefWidth(800);
        textField.setPrefHeight(50);

        layoutGrid.add(textField, 0, 9, 3, 1);
        layoutGrid.setMargin(textField, new Insets (10, 10, 1, 30));
    }

    public void addGallery(){
        if(galleryPane.getChildren().size() == 0)
            galleryPane.getChildren().add(galleryImageView);

        galleryPane.setMinHeight(galleryImageView.getFitHeight()+100);

        HBox bubbleGallery = new HBox();
        bubbleGallery.getStyleClass().add("bubbles");

        Pane bubbleDisplay = new Pane();

        File folder = new File("src/images/bubbles");
        File[] listOfFiles = folder.listFiles();

        layoutGrid.add(bubbleGallery, 0, 2, 3, 3);
        layoutGrid.setMargin(bubbleGallery, new Insets(10, 10, 10, 30));

        layoutGrid.add(bubbleDisplay, 0, 5, 3, 3);
        layoutGrid.setMargin(bubbleDisplay, new Insets (10, 10, 10, 30));
    }
}

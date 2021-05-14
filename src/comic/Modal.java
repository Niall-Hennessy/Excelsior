package comic;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Modal {

    final Stage modal = new Stage(StageStyle.UNDECORATED);
    protected GridPane layoutGrid = new GridPane();
    protected ComicPanel comicPanel;

    public Modal(double width, double height, ComicPanel cPanel){
        Scene scene = new Scene(layoutGrid);
        scene.getStylesheets().add("main/style.css");

        layoutGrid.getStyleClass().add("layoutGrid");
        layoutGrid.setPadding(new Insets(10, 10, 10, 10));
        layoutGrid.setVgap(5);
        layoutGrid.setHgap(5);

        modal.setScene(scene);

        modal.setX(width);
        modal.setY(height);

        scene.setOnMousePressed(pressEvent -> {
            scene.setOnMouseDragged(dragEvent -> {
                modal.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                modal.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });

        this.comicPanel = cPanel;
    }

    public void addEscape(Insets insets){
        Button escape = new Button("X");
        escape.getStyleClass().add("escape");

        layoutGrid.add(escape, 21, 0, 1, 1);
        layoutGrid.setMargin(escape, insets);

        escape.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                modal.close();
            }
        });
    }

    public void addSubmit(Insets insets){
        Button submit = new Button("Submit");
        submit.getStyleClass().add("submit");

        layoutGrid.add(submit, 0, 4, 1, 1);
        layoutGrid.setMargin(submit, insets);

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

    public void addCancel(Insets insets){
        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("cancel");

        layoutGrid.add(cancel,1, 4, 1, 1);
        layoutGrid.setMargin(cancel, insets);

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                modal.close();
            }
        });
    }

    public void addDelete(Insets insets){
        Button delete = new Button("Delete");
        delete.getStyleClass().add("cancel");

        layoutGrid.add(delete,2, 4, 1, 1);
        layoutGrid.setMargin(delete, insets);

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
    }

    public void addTextField(Insets insets){
        TextField textField = new TextField();

        textField.setText(comicPanel.getTopText().getText());

        textField.getStyleClass().add("capTextField");
        textField.setPrefWidth(400);
        textField.setPrefHeight(25);

        layoutGrid.add(textField, 0, 2, 20, 1);
        layoutGrid.setMargin(textField, insets);
    }

    public void addComboBox(Insets insets, String... args){
        ComboBox combo_box = new ComboBox();
        combo_box.getStyleClass().add("bold");

        for (String arg : args) {
            combo_box.getItems().add(arg);
        }

        combo_box.getSelectionModel().selectFirst();

        layoutGrid.add(combo_box,1, 3, 1, 1);
        layoutGrid.setMargin(combo_box, insets);
    }

    public void show(){
        modal.show();
    }

    public void close(){
        modal.close();
    }
}

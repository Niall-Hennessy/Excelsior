package comic;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import undo.Undo;
import undo.UndoList;

public class TextModal extends Modal{

    private final boolean[] value1 = {true};
    private final boolean[] value2 = {false};
    TextField textField = new TextField();
    ComboBox comboBox = new ComboBox();

    String previousTopText = "";
    String previousBottomText = "";

    Font previousTopFont = Font.font("Segoe UI", 18);
    Font previousBottomFont = Font.font("Segoe UI", 18);


    public TextModal(double width, double height, ComicPanel cPanel) {
        super(width, height, cPanel);
    }

    @Override
    public void addSubmit(){
        Button submit = new Button("Submit");
        submit.getStyleClass().add("submit");

        layoutGrid.add(submit, 0, 4, 1, 1);
        layoutGrid.setMargin(submit,new Insets (5, 0, 5, 25));


        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Undo undo = null;

                if(value1[0]) {
                    undo = new Undo("caption", comicPanel, "top", previousTopText + "#" + previousTopFont.getName());

                    previousTopText = textField.getText();
                    previousTopFont = textField.getFont();
                }

                if(value2[0]) {
                    undo = new Undo("caption", comicPanel, "bottom", previousBottomText + "#" + previousBottomFont.getName());

                    previousBottomText = textField.getText();
                    previousBottomFont = textField.getFont();
                }

                if(undo != null)
                    UndoList.addUndo(undo);

                Font font = new Font(comboBox.getSelectionModel().getSelectedItem().toString(), 18);
                textField.setFont(font);

                if(value1[0]) {
                    comicPanel.setTopText(textField.getText(), font);
                }
                else if(value2[0]) {
                    comicPanel.setBottomText(textField.getText(), font);
                }
            }
        });
    }

    @Override
    public void addDelete(){
        Button delete = new Button("Delete");
        delete.getStyleClass().add("cancel");

        layoutGrid.add(delete,2, 4, 1, 1);
        layoutGrid.setMargin(delete, new Insets (5, 0, 5, 0));

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(value1[0]) {

                    Undo undo = new Undo("caption", comicPanel, "top", previousTopText + "#" + previousTopFont.getName());
                    UndoList.addUndo(undo);

                    previousTopText = textField.getText();
                    previousTopFont = textField.getFont();

                    comicPanel.setTopText("", Font.font("", 18));
                    textField.setText("");
                    textField.setFont(Font.font("Segoe UI", 18));
                    comboBox.getSelectionModel().selectFirst();
                }
                else if(value2[0]) {

                    Undo undo = new Undo("caption", comicPanel, "bottom", previousBottomText + "#" + previousBottomFont.getName());
                    UndoList.addUndo(undo);

                    comicPanel.setBottomText("", Font.font("", 18));
                    textField.setText("");
                    textField.setFont(Font.font("Segoe UI", 18));
                    comboBox.getSelectionModel().selectFirst();
                }
            }
        });
    }

    @Override
    public void addTextField() {

        if(!comicPanel.getTopText().getText().matches("")){
            textField.setText(comicPanel.getTopText().getText());
        }
        else if(!comicPanel.getBottomText().getText().matches("")){
            textField.setText(comicPanel.getBottomText().getText());
        }
        else
            textField.setText("");

        textField.getStyleClass().add("capTextField");
        textField.setPrefWidth(200);
        textField.setPrefHeight(50);
        Font font = new Font(comboBox.getSelectionModel().getSelectedItem().toString(), 18);
        textField.setFont(font);


        layoutGrid.add(textField, 0, 2, 20, 1);
        layoutGrid.setMargin(textField, new Insets(5, 0, 5, 25));
    }

    public void addBooleanButtons(String button_1, String button_2){
        Button button1 = new Button(button_1);
        button1.getStyleClass().add("topTextCaption");
        Button button2 = new Button(button_2);
        button2.getStyleClass().add("bottomTextCaption");

        layoutGrid.add(button1, 0, 1, 1, 1);
        layoutGrid.setMargin(button1, new Insets (5, 0, 5, 25));
        layoutGrid.add(button2, 1, 1, 1, 1);
        layoutGrid.setMargin(button2, new Insets (5, 0, 5, 0));

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                value1[0] = true;
                value2[0] = false;

                button1.setStyle("-fx-background-color: #755A85");
                button2.setStyle("-fx-background-color: #AF86C8;");

                textField.setText(comicPanel.getTopText().getText());
                textField.setFont(comicPanel.getTopText().getTextObject().getFont());
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                value1[0] = false;
                value2[0] = true;

                button2.setStyle("-fx-background-color: #755A85");
                button1.setStyle("-fx-background-color: #AF86C8;");

                textField.setText(comicPanel.getBottomText().getText());
                textField.setFont(comicPanel.getBottomText().getTextObject().getFont());
            }
        });
    }

    @Override
    public void addComboBox(String... args) {
        comboBox.getStyleClass().add("bold");

        for (String arg : args) {
            comboBox.getItems().add(arg);
        }

        comboBox.getSelectionModel().selectFirst();

        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                Font font = new Font(t1, 18);
                textField.setFont(font);
            }
        });

        Label fonts = new Label("Fonts");

        layoutGrid.add(comboBox,1, 3, 1, 1);
        layoutGrid.setMargin(comboBox, new Insets (5, 0, 5, 0));
        layoutGrid.add(fonts,0, 3, 1, 1);
        layoutGrid.setMargin(fonts, new Insets (5, 0, 5, 25));
    }
}

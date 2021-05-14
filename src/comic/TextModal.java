package comic;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class TextModal extends Modal{

    private final boolean[] value1 = {true};
    private final boolean[] value2 = {false};
    TextField textField;
    ComboBox comboBox;

    public TextModal(double width, double height, ComicPanel cPanel) {
        super(width, height, cPanel);
    }

    public void addSubmit(){
        Button submit = new Button("Submit");
        submit.getStyleClass().add("submit");

        final Font[] font = {Font.font("Segoe UI", 20)};

        final Button[] topText = {new Button()};
        final Button[] bottomText = {new Button()};

        layoutGrid.add(submit, 0, 4, 1, 1);
        layoutGrid.setMargin(submit, new Insets(5, 0, 5, 25));

        layoutGrid.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {

                Node node = c.getList().get(c.getList().size()-1);

                if(node.toString().contains("ComboBox")) {
                    comboBox = ((ComboBox) node);
                }

                if(node.toString().contains("TextField")) {
                    textField = ((TextField) node);
                }

                if(node.toString().contains("Top")) {
                    topText[0] = ((Button) node);
                }

                if(node.toString().contains("Bottom")) {
                    bottomText[0] = ((Button) node);
                }
            }
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                font[0] = Font.font((String) comboBox.getSelectionModel().getSelectedItem(), 20);

                if(value1[0]) {
                    comicPanel.setTopText(textField.getText(), font[0]);
                }
                else if(value2[0]) {
                    comicPanel.setBottomText(textField.getText(), font[0]);
                }
            }
        });
    }

    public void addDelete(){
        Button delete = new Button("Delete");
        delete.getStyleClass().add("cancel");

        layoutGrid.add(delete,2, 4, 1, 1);
        layoutGrid.setMargin(delete, new Insets (5, 0, 5, 0));

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(value1[0]) {
                    comicPanel.setTopText("", Font.font("", 20));
                    textField.setText("");
                    textField.setFont(Font.font("Segoe UI", 20));
                    comboBox.getSelectionModel().selectFirst();
                }
                else if(value2[0]) {
                    comicPanel.setBottomText("", Font.font("", 20));
                    textField.setText("");
                    textField.setFont(Font.font("Segoe UI", 20));
                    comboBox.getSelectionModel().selectFirst();
                }
            }
        });
    }

    public void addEscape(){
        super.addEscape();
    }

    public void addCancel(){
        super.addCancel();
    }

    public void addTextField(){
        super.addTextField();
    }

    public void addComboBox(String... args){
        super.addComboBox(args);

        comboBox.
    }

    public void addBooleanButtons(String button_1, String button_2){
        Button button1 = new Button(button_1);
        button1.getStyleClass().add("bold");
        Button button2 = new Button(button_2);
        button2.getStyleClass().add("bold");

        layoutGrid.add(button1, 0, 1, 1, 1);
        layoutGrid.setMargin(button1, new Insets (5, 0, 5, 25));
        layoutGrid.add(button2, 1, 1, 1, 1);
        layoutGrid.setMargin(button2, new Insets (5, 0, 5, 0));

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                value1[0] = true;
                value2[0] = false;

                textField.setText(comicPanel.getTopText().getText());
                textField.setFont(comicPanel.getTopText().getTextObject().getFont());
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                value1[0] = false;
                value2[0] = true;

                textField.setText(comicPanel.getBottomText().getText());
                textField.setFont(comicPanel.getBottomText().getTextObject().getFont());
            }
        });
    }

    public void show(){
        super.show();
    }

    public void close(){
        super.close();
    }

}

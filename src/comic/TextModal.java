package comic;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TextModal extends Modal{

    public TextModal(double width, double height, ComicPanel cPanel) {
        super(width, height, cPanel);
    }

    public void addSubmit(){
        Button submit = new Button("Submit");
        submit.getStyleClass().add("submit");

        final String[] input = {""};
        final Font[] font = {Font.font("Segoe UI", 20)};

        final ComboBox[] comboBox = new ComboBox[1];
        final TextField[] textField = new TextField[1];

        layoutGrid.add(submit, 0, 4, 1, 1);
        layoutGrid.setMargin(submit, new Insets(5, 0, 5, 25));

        layoutGrid.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {

                Node node = c.getList().get(c.getList().size()-1);

                if(node.toString().contains("ComboBox")) {
                    comboBox[0] = ((ComboBox) node);
                }

                if(node.toString().contains("TextField")) {
                    textField[0] = ((TextField) node);
                }
            }
        });

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                font[0] = Font.font((String) comboBox[0].getSelectionModel().getSelectedItem(), 20);
                comicPanel.setTopText(textField[0].getText(), font[0]);
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
    }

    public void addBooleanButtons(String button_1, String button_2){
        Button button1 = new Button(button_1);
        button1.getStyleClass().add("bold");
        Button button2 = new Button(button_2);
        button2.getStyleClass().add("bold");

        final boolean[] value1 = {true};
        final boolean[] value2 = {false};

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                value1[0] = true;
                value2[0] = false;
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                value1[0] = false;
                value2[0] = true;
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

package sample;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class TextCaption extends Pane {

    Text text = new Text();

    public TextCaption(String text){
        this.text.setText(text);
        this.getChildren().add(this.text);

    }

    public String getText() {
        String string = text.getText();
        return string;
    }
}

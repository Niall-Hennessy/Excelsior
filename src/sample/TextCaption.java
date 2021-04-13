package sample;

import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class TextCaption extends Pane {

    Text text = new Text();

    public TextCaption(String text){
        this.text.setText(text);
        this.text.setTranslateX((300 - this.text.getLayoutBounds().getWidth()) / 2);
        this.text.setTextAlignment(TextAlignment.CENTER);
        this.text.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 15));
        this.getChildren().add(this.text);

    }

    public String getText() {
        String string = text.getText();
        return string;
    }


}

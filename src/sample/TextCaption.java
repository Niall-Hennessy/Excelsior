package sample;

import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class TextCaption extends Pane {

    Text text = new Text();

    public TextCaption(String text, Font font){
        this.text.setText(text);
        this.text.setFont(font);
        while(this.text.getLayoutBounds().getWidth() > 500)
        {
            this.text.setText(this.text.getText().substring(0, this.text.getText().length()-1));
        }
        this.text.setTranslateX((500 - this.text.getLayoutBounds().getWidth()) / 2);
        this.getChildren().add(this.text);

    }

    public String getText() {
        String string = text.getText();
        return string;
    }


}

package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TextBubble extends Pane {

    ImageView bubble;
    Text text = new Text();

    public TextBubble(ImageView bubble, String text) {
        if(text.length() > 20){
            String newText = text.substring(0,20);
            newText += "\n";
            newText += text.substring(20);
            text = newText;
        }

        this.text.setText(text);
        this.text.setTranslateX(bubble.getTranslateX() + 20);
        this.text.setTranslateY(bubble.getTranslateY() + 20);
        bubble.setFitWidth(this.getWidth());
        this.getChildren().add(bubble);
        this.getChildren().add(this.text);
    }

    public ImageView getBubble() {
        return bubble;
    }

    public void setBubble(ImageView bubble) {
        this.bubble = bubble;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}

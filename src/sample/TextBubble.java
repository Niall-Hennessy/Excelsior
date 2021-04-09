package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TextBubble extends Pane {

    ImageView bubble;
    Text text = new Text();

    public TextBubble(ImageView bubble, String text) {
        text = parseText(text, 20);
        this.text.setText(text);
        this.text.setTranslateX(bubble.getTranslateX() + 20);
        this.text.setTranslateY(bubble.getTranslateY() + 20);
        //bubble.setFitWidth(this.getWidth());
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

    public String parseText(String text, int x){

        text = text.trim();

        if(text.length() > x){
            String newText = text.substring(0,x);

            if(newText.contains("\n"))
                return newText += parseText(text.substring(x), x);

            int whiteSpace = -1;

            for(int i = 0; i < x; i++)
                if (newText.charAt(i) == ' ')
                    whiteSpace = i;


            if(whiteSpace == -1)
                newText += "\n";
            else
                newText = newText.substring(0,whiteSpace) + "\n" + newText.substring(whiteSpace + 1);

            newText += parseText(text.substring(x), x);
            text = newText;
        }

        return text;
    }
}

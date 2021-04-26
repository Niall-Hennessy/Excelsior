package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextBubble extends Pane {

    ImageView bubble = new ImageView();
    Text text = new Text();

    public TextBubble(ImageView bubble, String text, Font font) {
        text = parseText(text, 20);
        this.text.setText(text);
        this.text.setFont(font);
        this.bubble.setImage(bubble.getImage());
        this.bubble.setFitWidth(this.text.getBoundsInLocal().getWidth() + 50);
        this.bubble.setFitHeight(this.text.getBoundsInLocal().getHeight()+ 65);
        this.text.setTranslateX(this.bubble.getBoundsInParent().getCenterX() - (this.text.getBoundsInParent().getWidth()/2));
        this.text.setTranslateY(this.bubble.getBoundsInParent().getCenterY() - (this.text.getBoundsInParent().getHeight()/2));
        this.getChildren().add(this.bubble);
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

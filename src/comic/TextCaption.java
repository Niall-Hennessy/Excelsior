package comic;

import javafx.geometry.VPos;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;


public class TextCaption extends Pane {

    private Text text = new Text();

    public TextCaption(String text, Font font){

        int height = (int) Screen.getPrimary().getBounds().getHeight();

        this.text.setText(text);
        this.text.setFont(font);
        this.text.setTextAlignment(TextAlignment.CENTER);
        this.text.setTextOrigin(VPos.BOTTOM);
        this.text.wrappingWidthProperty().set(height/2.4 + height/9.6);

        this.text.setTranslateX(((height/2.4 + height/9.6) - this.text.getLayoutBounds().getWidth()) / 2);
        this.getChildren().add(this.text);
    }

    public String getText() {
        String string = text.getText();
        return string;
    }

    public String getFont(){
        return text.getFont().getName();
    }

    public Text getTextObject() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}

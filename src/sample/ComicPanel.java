package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import javax.swing.plaf.PanelUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ComicPanel extends Pane {


    Image character1 = null;

    Image character2 = null;

    public ComicPanel(){

        this.setStyle("-fx-border-color: black");
    }

    public Image getCharacter1() {
        return character1;
    }

    public void setCharacter1(String character1) throws FileNotFoundException {
        character1 = "src/images/"+character1+".png";
        this.character1 = new Image(new FileInputStream(character1));
    }

    public Image getCharacter2() {
        return character2;
    }

    public void setCharacter2(String character2) throws FileNotFoundException {
        character2 = "src/images/"+character2+".png";
        this.character2 = new Image(new FileInputStream(character2));
    }
}

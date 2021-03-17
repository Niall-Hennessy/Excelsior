package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.swing.plaf.PanelUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ComicPanel extends Pane {

    ImageView leftCharacterView;
    ImageView rightCharacterView;

    public ComicPanel(){
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px");
        this.setPrefHeight(250);
        this.setPrefWidth(250);
    }

    public ImageView getLeftCharacterView() {
        return leftCharacterView;
    }

    public void setLeftCharacter(String imagePath) throws FileNotFoundException {
        imagePath = "src/images/" + imagePath + ".png";
        Image image = new Image(new FileInputStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        this.getChildren().add(imageView);
        this.leftCharacterView = imageView;
    }

    public ImageView getRightCharacter() {
        return rightCharacterView;
    }

    public void setRightCharacter(String imagePath) throws FileNotFoundException {
        imagePath = "src/images/" + imagePath + ".png";
        Image image = new Image(new FileInputStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        this.getChildren().add(imageView);
        this.rightCharacterView = imageView;
    }
}

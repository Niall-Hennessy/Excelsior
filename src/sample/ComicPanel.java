package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
        this.setPrefHeight(280);
        this.setPrefWidth(280);
    }

    public ImageView getLeftCharacterView() {
        return leftCharacterView;
    }

    public void setLeftCharacter(String imagePath) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setY(this.getTranslateY() + 100);
        imageView.setX(this.getTranslateX() + 10);
        this.getChildren().add(imageView);
        this.leftCharacterView = imageView;
    }

    public ImageView getRightCharacter() {
        return rightCharacterView;
    }

    public void setRightCharacter(String imagePath) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(imagePath));



        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setY(this.getTranslateY() + 100);
        imageView.setX(this.getTranslateX() + 160);

        this.getChildren().add(imageView);
        this.rightCharacterView = imageView;
    }
}

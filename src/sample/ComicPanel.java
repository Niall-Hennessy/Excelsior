package sample;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

import javax.swing.plaf.PanelUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ComicPanel extends Pane {

    ImageView leftCharacterView = new ImageView(new Image(new FileInputStream("src/images/characters/blank.png")));
    ImageView rightCharacterView = new ImageView(new Image(new FileInputStream("src/images/characters/blank.png")));

    public ComicPanel() throws FileNotFoundException {
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px");
        this.setPrefHeight(280);
        this.setPrefWidth(280);

        leftCharacterView.setPickOnBounds(true);
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
        imageView.setX(this.getTranslateX() + 170);
        imageView.setRotationAxis(Rotate.Y_AXIS);
        imageView.setRotate(180);
        this.getChildren().add(imageView);
        this.rightCharacterView = imageView;
    }

    public void flipOrientation(String character){

        ImageView flipCharacter;

        if(character.matches("left"))
            flipCharacter = leftCharacterView;
        else
            flipCharacter = rightCharacterView;

        flipCharacter.setRotationAxis(Rotate.Y_AXIS);
        if(flipCharacter.getRotate() == 180)
            flipCharacter.setRotate(0);
        else
            flipCharacter.setRotate(180);

        if(character.matches("left"))
            this.leftCharacterView = flipCharacter;
        else
            this.rightCharacterView = flipCharacter;

    }

    public void selectCharacter(String character){

        System.out.println("Character: " + character);

        if(character.matches("left")) {
            System.out.println("LEFTS");
            leftCharacterView.setStyle("-");
            rightCharacterView.setStyle("-fx-border-width: 0");
        }
        else if(character.matches("right")) {
            rightCharacterView.setStyle("-fx-border-color: cyan; -fx-border-width: 1");
            leftCharacterView.setStyle("-fx-border-width: 0");
        }
        else {
            leftCharacterView.setStyle("-fx-border-width: 0");
            rightCharacterView.setStyle("-fx-border-width: 0");
        }
    }

}

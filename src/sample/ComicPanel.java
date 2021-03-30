package sample;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import javax.swing.plaf.PanelUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ComicPanel extends Pane {


    ImageView leftCharacterView = new ImageView(new Image(new FileInputStream("src/images/characters/blank.png")));
    ImageView rightCharacterView = new ImageView(new Image(new FileInputStream("src/images/characters/blank.png")));
    BorderPane leftCharacterWrapper = new BorderPane(leftCharacterView);
    BorderPane rightCharacterWrapper = new BorderPane(rightCharacterView);

    Color leftCharacterHair = Color.rgb(240,255,0,1);
    Color leftCharacterSkin = Color.rgb(255,232,216,1);

    Color rightCharacterHair = Color.rgb(240,255,0,1);
    Color rightCharacterSkin = Color.rgb(255,232,216,1);


    public ComicPanel() throws FileNotFoundException {
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px");
        this.setPrefHeight(280);
        this.setPrefWidth(280);
    }

    public ImageView getLeftCharacterView() {
        return leftCharacterView;
    }

    public void setLeftCharacter(String imagePath) throws FileNotFoundException {
        this.getChildren().remove(leftCharacterWrapper);
        Image image = new Image(new FileInputStream(imagePath));
        ImageView imageView = new ImageView(image);
        leftCharacterView = imageView;
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        leftCharacterWrapper = new BorderPane(imageView);
        leftCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
        leftCharacterWrapper.setTranslateX(this.getTranslateX() + 10);
        this.getChildren().add(leftCharacterWrapper);
        leftCharacterWrapper.setStyle("-fx-border-color: cyan");
        rightCharacterWrapper.setStyle("-fx-border-color: white");

        updateImage("left");
    }

    public ImageView getRightCharacter() {
        return rightCharacterView;
    }

    public void setRightCharacter(String imagePath) throws FileNotFoundException {
        this.getChildren().remove(rightCharacterWrapper);
        Image image = new Image(new FileInputStream(imagePath));
        ImageView imageView = new ImageView(image);
        rightCharacterView = imageView;
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setRotationAxis(Rotate.Y_AXIS);
        imageView.setRotate(180);
        rightCharacterWrapper = new BorderPane(imageView);
        rightCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
        rightCharacterWrapper.setTranslateX(this.getTranslateX() + 170);
        this.getChildren().add(rightCharacterWrapper);
        leftCharacterWrapper.setStyle("-fx-border-color: white");
        rightCharacterWrapper.setStyle("-fx-border-color: cyan");

        updateImage("right");
    }

    public void flipOrientation(String character){

        ImageView flipCharacter;

        if(character.matches("left"))
            flipCharacter = (ImageView) leftCharacterWrapper.getChildren().get(0);
        else
            flipCharacter = (ImageView) rightCharacterWrapper.getChildren().get(0);

        flipCharacter.setRotationAxis(Rotate.Y_AXIS);
        if(flipCharacter.getRotate() == 180)
            flipCharacter.setRotate(0);
        else
            flipCharacter.setRotate(180);

        if(character.matches("left")) {
            leftCharacterWrapper = new BorderPane(flipCharacter);
            leftCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
            leftCharacterWrapper.setTranslateX(this.getTranslateX() + 10);
            leftCharacterWrapper.setStyle("-fx-border-color: cyan");
            this.getChildren().add(leftCharacterWrapper);
        }
        else {
            rightCharacterWrapper = new BorderPane(flipCharacter);
            rightCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
            rightCharacterWrapper.setTranslateX(this.getTranslateX() + 170);
            rightCharacterWrapper.setStyle("-fx-border-color: cyan");
            this.getChildren().add(rightCharacterWrapper);
        }

    }

    public void selectCharacter(String character){

        System.out.println("Character: " + character);

        if(character.matches("left")) {
            System.out.println("LEFTS");
            leftCharacterWrapper.setStyle("-fx-border-color: cyan");
            rightCharacterWrapper.setStyle("-fx-border-color: white");
        }
        else if(character.matches("right")) {
            rightCharacterWrapper.setStyle("-fx-border-color: cyan");
            leftCharacterWrapper.setStyle("-fx-border-color: white");
        }
        else {
            leftCharacterWrapper.setStyle("-fx-border-color: white");
            rightCharacterWrapper.setStyle("-fx-border-color: white");
        }
    }

    public void updateImage(String character){

        if(character.matches("left")) {
            Image image = leftCharacterView.getImage();

            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            WritableImage writableImage = new WritableImage(width, height);

            PixelReader pixelReader = image.getPixelReader();
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = pixelReader.getColor(x, y);

                    if (color.equals(Color.web("0xf0ff00ff"))) {
                        pixelWriter.setColor(x, y, leftCharacterHair);
                    } else if (color.equals(Color.web("0xffe8d8ff"))) {
                        pixelWriter.setColor(x, y, leftCharacterSkin);
                    } else if (color.equals(Color.web("#F9FF00"))) {
                        pixelWriter.setColor(x, y, leftCharacterHair);
                    }
                }
            }

            ImageView imageView = new ImageView(writableImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            leftCharacterWrapper.getChildren().removeAll();
            leftCharacterWrapper = new BorderPane(imageView);
            leftCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
            leftCharacterWrapper.setTranslateX(this.getTranslateX() + 10);
            leftCharacterWrapper.setStyle("-fx-border-color: cyan");
            this.getChildren().add(leftCharacterWrapper);
        }
        else if(character.matches("right")){
            Image image = rightCharacterView.getImage();

            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            WritableImage writableImage = new WritableImage(width, height);

            PixelReader pixelReader = image.getPixelReader();
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = pixelReader.getColor(x, y);

                    if (color.equals(Color.web("0xf0ff00ff"))) {
                        pixelWriter.setColor(x, y, rightCharacterHair);
                    } else if (color.equals(Color.web("0xffe8d8ff"))) {
                        pixelWriter.setColor(x, y, rightCharacterSkin);
                    } else if (color.equals(Color.web("#F9FF00"))) {
                        pixelWriter.setColor(x, y, rightCharacterHair);
                    }
                }
            }

            ImageView imageView = new ImageView(writableImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setRotate(180);

            rightCharacterWrapper.getChildren().removeAll();
            rightCharacterWrapper = new BorderPane(imageView);
            rightCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
            rightCharacterWrapper.setTranslateX(this.getTranslateX() + 170);
            rightCharacterWrapper.setStyle("-fx-border-color: cyan");
            this.getChildren().add(rightCharacterWrapper);
        }
    }


    public Color getLeftCharacterHair() {
        return leftCharacterHair;
    }

    public void setLeftCharacterHair(Color leftCharacterHair) {
        this.leftCharacterHair = leftCharacterHair;
        System.out.println("New Colour Set for Left Character Hair" + leftCharacterHair.toString());
        updateImage("left");
    }

    public Color getLeftCharacterSkin() {
        return leftCharacterSkin;
    }

    public void setLeftCharacterSkin(Color leftCharacterSkin) {
        this.leftCharacterSkin = leftCharacterSkin;
        updateImage("left");
    }

    public Color getRightCharacterHair() {
        return rightCharacterHair;
    }

    public void setRightCharacterHair(Color rightCharacterHair) {
        this.rightCharacterHair = rightCharacterHair;
        updateImage("right");
    }

    public Color getRightCharacterSkin() {
        return rightCharacterSkin;
    }

    public void setRightCharacterSkin(Color rightCharacterSkin) {
        this.rightCharacterSkin = rightCharacterSkin;
        updateImage("right");
    }
}

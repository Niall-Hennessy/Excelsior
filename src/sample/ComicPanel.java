package sample;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.Axis;
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

    boolean leftFemale = true;
    boolean rightFemale = true;

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

//        updateImage("left");
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
//        imageView.setRotationAxis(Rotate.Y_AXIS);
//        imageView.setRotate(180);
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

    public void genderSwap(String character){

        if(character.matches("left"))
            leftFemale = !leftFemale;
        else
            rightFemale = !rightFemale;

        updateImage(character);
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

                    if(color.equals(Color.WHITE)){
                        continue;
                    }
                    else if (color.equals(Color.web("0xf0ff00ff"))) {
                        if(!leftFemale)
                            pixelWriter.setColor(x, y, Color.WHITE);
                        else
                            pixelWriter.setColor(x, y, leftCharacterHair);
                    } else if (color.equals(Color.web("0xffe8d8ff"))) {
                        pixelWriter.setColor(x, y, leftCharacterSkin);
                    } else if (color.equals(Color.web("#F9FF00"))) {
                        pixelWriter.setColor(x, y, leftCharacterHair);
                    }else if (color.equals(Color.web("#ECB4B5")) && !leftFemale) {
                        pixelWriter.setColor(x, y, Color.WHITE);
                    }else if (color.equals(Color.web("#FF0000")) && !leftFemale) {
                        pixelWriter.setColor(x, y, leftCharacterSkin);
                    }else if(isOnLine(Color.web("#FF0000"), leftCharacterSkin, color) && !leftFemale){
                        pixelWriter.setColor(x, y, leftCharacterSkin);
                    }else if(isOnLine(Color.web("#ECB4B5"), Color.WHITE, color) && !leftFemale){
                        pixelWriter.setColor(x, y, Color.WHITE);
                    }else if(isOnLine(Color.web("0xf0ff00ff"), Color.WHITE, color) && !leftFemale){
                        pixelWriter.setColor(x, y, Color.WHITE);
                    }else {
                        pixelWriter.setColor(x, y, color);
                    }
                }
            }


            ImageView imageView = new ImageView(writableImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            this.getChildren().remove(leftCharacterWrapper);
            leftCharacterWrapper = new BorderPane(imageView);
            leftCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
            leftCharacterWrapper.setTranslateX(this.getTranslateX() + 10);
            leftCharacterWrapper.setStyle("-fx-border-color: cyan");
            rightCharacterWrapper.setStyle("-fx-border-color: white");
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
                        if(!leftFemale)
                            pixelWriter.setColor(x, y, Color.WHITE);
                        else
                            pixelWriter.setColor(x, y, rightCharacterHair);
                    } else if (color.equals(Color.web("0xffe8d8ff"))) {
                        pixelWriter.setColor(x, y, rightCharacterSkin);
                    } else if (color.equals(Color.web("#F9FF00"))) {
                        pixelWriter.setColor(x, y, rightCharacterHair);
                    }else if (color.equals(Color.web("#ECB4B5")) && !rightFemale) {
                        pixelWriter.setColor(x, y, Color.WHITE);
                    }else if (color.equals(Color.web("#FF0000")) && !rightFemale) {
                        pixelWriter.setColor(x, y, rightCharacterSkin);
                    }
                    else {
                        pixelWriter.setColor(x, y, color);
                    }
                }
            }

            ImageView imageView = new ImageView(writableImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setRotationAxis(Rotate.Y_AXIS);
            imageView.setRotate(180);

            this.getChildren().remove(rightCharacterWrapper);
            rightCharacterWrapper = new BorderPane(imageView);
            rightCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
            rightCharacterWrapper.setTranslateX(this.getTranslateX() + 170);
            rightCharacterWrapper.setStyle("-fx-border-color: cyan");
            leftCharacterWrapper.setStyle("-fx-border-color: white");
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

    public boolean isOnLine(Color p1, Color p2, Color p3)
    {

        double p1RedColor = Integer.parseInt(p1.toString().substring(2,4), 16);
        double p1GreenColor = Integer.parseInt(p1.toString().substring(4,6), 16);
        double p1BlueColor = Integer.parseInt(p1.toString().substring(6,8), 16);

        double p2RedColor = Integer.parseInt(p2.toString().substring(2,4), 16);
        double p2GreenColor = Integer.parseInt(p2.toString().substring(4,6), 16);
        double p2BlueColor = Integer.parseInt(p2.toString().substring(6,8), 16);

        double p3RedColor = Integer.parseInt(p3.toString().substring(2,4), 16);
        double p3GreenColor = Integer.parseInt(p3.toString().substring(4,6), 16);
        double p3BlueColor = Integer.parseInt(p3.toString().substring(6,8), 16);

        boolean red = false;
        boolean green = false;
        boolean blue = false;

        double redVal = -1;
        double greenVal = 0;
        double blueVal = 1;

        if(p1RedColor - p2RedColor == 0){
            if(p1RedColor == p3RedColor)
            {
                red = true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            redVal = (p3RedColor - p1RedColor) / (p2RedColor - p1RedColor);

            if(redVal >= 0 && redVal <= 1)
                red = true;
        }

        if(p1GreenColor - p2GreenColor == 0){
            if(p1GreenColor == p3GreenColor)
            {
                green = true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            greenVal = (p3GreenColor - p1GreenColor) / (p2GreenColor - p1GreenColor);

            if(greenVal >= 0 && greenVal <= 1)
                green = true;
        }

        if(p1BlueColor - p2BlueColor == 0){
            if(p1BlueColor == p3BlueColor)
            {
                blue = true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            blueVal = (p3BlueColor - p1BlueColor) / (p2BlueColor - p1BlueColor);

            if(blueVal >= 0 && blueVal <= 1)
                blue = true;
        }

        if(red && green && blue) {
            System.out.println("Found Anti-Aliasling: " + p3.toString());
            return true;
        }
        else if(redVal == greenVal && redVal == blueVal){
            System.out.println("Found Anti-Aliasling: " + p3.toString());
            return true;
        }
        else{
            return false;
        }

    }
}

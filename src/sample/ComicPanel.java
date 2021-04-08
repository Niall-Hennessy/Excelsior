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
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import javax.swing.plaf.PanelUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ComicPanel extends Pane {


    ImageView leftCharacterView = new ImageView(new Image(new FileInputStream("src/images/characters/blank.png")));
    ImageView rightCharacterView = new ImageView(new Image(new FileInputStream("src/images/characters/blank.png")));
    BorderPane leftCharacterWrapper = new BorderPane(leftCharacterView);
    BorderPane rightCharacterWrapper = new BorderPane(rightCharacterView);

    Color leftCharacterHair = Color.rgb(240,255,0,1);
    Color leftCharacterSkin = Color.rgb(255,232,216,1);

    Color rightCharacterHair = Color.rgb(240,255,0,1);
    Color rightCharacterSkin = Color.rgb(255,232,216,1);

    TextBubble leftTextBubble = null;
    TextBubble rightTextBubble = null;

    boolean leftFemale = true;
    boolean rightFemale = true;

    public ComicPanel() throws FileNotFoundException {
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px");
        this.setPrefHeight(280);
        this.setPrefWidth(350);
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
        rightCharacterWrapper.setTranslateX(this.getTranslateX() + 240);
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
            rightCharacterWrapper.setTranslateX(this.getTranslateX() + 240);
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
            PixelReader wPixelReader = writableImage.getPixelReader();
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = pixelReader.getColor(x, y);

                    if(color.equals(Color.WHITE)){
                        pixelWriter.setColor(x, y, color);
                    }else if(color.equals(Color.BLACK)){
                        pixelWriter.setColor(x, y, color);
                    }
                    else if(color.equals(Color.web("#A03E00"))){//Shoe Colour
                        pixelWriter.setColor(x, y, color);
                    }
                    else if (color.equals(Color.web("#FFE8D8"))) {//Skin Colour
                        pixelWriter.setColor(x, y, leftCharacterSkin);
                    } else if (color.equals(Color.web("#F0FF00"))) {//Female Hair Colour
                        if(!leftFemale) {
                            pixelWriter.setColor(x, y, Color.WHITE);
                        }
                        else
                            pixelWriter.setColor(x, y, leftCharacterHair);
                    }else if (color.equals(Color.web("#F9FF00"))) {//Male Hair Colour
                        pixelWriter.setColor(x, y, leftCharacterHair);
                    }
                    else if(isOnLine(Color.web("#F0FF00"), Color.web("#F9FF00"), color)){
                        pixelWriter.setColor(x, y, leftCharacterHair);
                    }
                    else if(isOnLine(Color.web("#C9E1E3"),Color.web("#75959C"),color)){
                        pixelWriter.setColor(x, y, color);
                    }
                    else if (color.equals(Color.web("#ECB4B5")) && !leftFemale){

                        if(x < 400 && x > 360 && y > 160 && y < 250)
                            pixelWriter.setColor(x, y, Color.WHITE);
                        else if(x < 385 && x > 250 && y > 155 && y < 300)
                            pixelWriter.setColor(x, y, color);
                        else if(x < 423 && x > 255 && y > 290 && y < 350)
                            pixelWriter.setColor(x, y, color);
                        else
                            pixelWriter.setColor(x, y, Color.WHITE);
                    }
                    else if(isOnLine(Color.web("#FF0000"), Color.web("#FFA1A1"), color) && !leftFemale){
                        pixelWriter.setColor(x, y, leftCharacterSkin);
                    }
                    else if(x < 400 && x > 200 && y > 200 && !leftFemale){
                        if(color.toString().substring(2,4).matches("ff")) {
                            pixelWriter.setColor(x, y, leftCharacterSkin);
                        }
                        else
                            pixelWriter.setColor(x, y, color);
                    }
                    else {
                        pixelWriter.setColor(x, y, color);
                    }
                }
            }

            ImageView imageView = new ImageView(writableImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setSmooth(false);
            imageView.setRotationAxis(Rotate.Y_AXIS);
            imageView.setRotate(leftCharacterWrapper.getChildren().get(0).getRotate());

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

                    if(color.equals(Color.WHITE)){
                        pixelWriter.setColor(x, y, color);
                    }else if(color.equals(Color.BLACK)){
                        pixelWriter.setColor(x, y, color);
                    }
                    else if(color.equals(Color.web("#A03E00"))){//Shoe Colour
                        pixelWriter.setColor(x, y, color);
                    }
                    else if (color.equals(Color.web("#FFE8D8"))) {//Skin Colour
                        pixelWriter.setColor(x, y, rightCharacterSkin);
                    } else if (color.equals(Color.web("#F0FF00"))) {//Female Hair Colour
                        if(!rightFemale) {
                            pixelWriter.setColor(x, y, Color.WHITE);
                        }
                        else
                            pixelWriter.setColor(x, y, rightCharacterHair);
                    }else if (color.equals(Color.web("#F9FF00"))) {//Male Hair Colour
                        pixelWriter.setColor(x, y, rightCharacterHair);
                    }
                    else if(isOnLine(Color.web("#C9E1E3"),Color.web("#75959C"),color)){
                        pixelWriter.setColor(x, y, color);
                    }
                    else if (color.equals(Color.web("#ECB4B5")) && !rightFemale){

                        if(x < 400 && x > 360 && y > 160 && y < 250)
                            pixelWriter.setColor(x, y, Color.WHITE);
                        else if(x < 385 && x > 250 && y > 155 && y < 300)
                            pixelWriter.setColor(x, y, color);
                        else if(x < 423 && x > 255 && y > 290 && y < 350)
                            pixelWriter.setColor(x, y, color);
                        else
                            pixelWriter.setColor(x, y, Color.WHITE);
                    }
                    else if(isOnLine(Color.web("#FF0000"), Color.web("#FFA1A1"), color) && !rightFemale){
                        pixelWriter.setColor(x, y, rightCharacterSkin);
                    }
                    else if(x < 400 && x > 200 && y > 200 && !rightFemale){
                        if(color.toString().substring(2,4).matches("ff")) {
                            pixelWriter.setColor(x, y, rightCharacterSkin);
                        }
                        else
                            pixelWriter.setColor(x, y, color);
                    }
                    else {
                        pixelWriter.setColor(x, y, color);
                    }
                }
            }

            ImageView imageView = new ImageView(writableImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            imageView.setSmooth(false);
            imageView.setRotationAxis(Rotate.Y_AXIS);
            imageView.setRotate(rightCharacterWrapper.getChildren().get(0).getRotate());

            this.getChildren().remove(rightCharacterWrapper);
            rightCharacterWrapper = new BorderPane(imageView);
            rightCharacterWrapper.setTranslateY(this.getTranslateY() + 100);
            rightCharacterWrapper.setTranslateX(this.getTranslateX() + 240);
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

        boolean red = false;
        boolean green = false;
        boolean blue = false;

        double redVal = -1;
        double greenVal = -2;
        double blueVal = -3;


        if(Double.compare(p1.getRed(), p2.getRed()) == 0)
            red = Double.compare(p1.getRed(), p3.getRed()) == 0;
        else
            redVal = decimalConverter((p3.getRed() - p1.getRed()) / (p2.getRed() - p1.getRed()));

        if(Double.compare(p1.getGreen(), p2.getGreen()) == 0)//who the QUACK wrote a language that cannot cast between ints and booleans
            green = Double.compare(p1.getGreen(), p3.getGreen()) == 0;
        else
            greenVal = decimalConverter((p3.getGreen() - p1.getGreen()) / (p2.getGreen() - p1.getGreen()));

        if(Double.compare(p1.getBlue(), p2.getBlue()) == 0)
            blue = Double.compare(p1.getBlue(), p3.getBlue()) == 0;
        else
            blueVal = decimalConverter((p3.getBlue() - p1.getBlue()) / (p2.getBlue() - p1.getBlue()));



        if((Double.compare(redVal, greenVal) == 0 && Double.compare(redVal, blueVal) == 0) || (red && Double.compare(greenVal, blueVal) == 0) ||
                (green && Double.compare(redVal, blueVal) == 0) || (green && red)) {

            return true;
        }
        else
            return false;

    }

    public static double decimalConverter(double decimal) {
        BigDecimal bigDecimal = BigDecimal.valueOf(decimal);
        bigDecimal = bigDecimal.setScale(1, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public void setLeftBubble(Image image, String text){
        this.getChildren().remove(leftTextBubble);

        ImageView imageView = new ImageView(image);
        leftTextBubble = new TextBubble(imageView, text);

        if(text.length() < 11) {
            imageView.setFitHeight(30);
            imageView.setFitWidth(100);
            leftTextBubble.setTranslateY(this.getTranslateY() + 50);
        } else if(text.length() < 21) {
            imageView.setFitHeight(30);
            imageView.setFitWidth(165);
            leftTextBubble.setTranslateY(this.getTranslateY() + 50);
        } else if(text.length() < 41) {
            imageView.setFitHeight(60);
            imageView.setFitWidth(165);
            leftTextBubble.setTranslateY(this.getTranslateY() + 35);
        } else {
            imageView.setFitHeight(90);
            imageView.setFitWidth(165);
            leftTextBubble.setTranslateY(this.getTranslateY() + 25);
        }

        leftTextBubble.setTranslateX(this.getTranslateX() + 50);
        this.getChildren().add(leftTextBubble);
    }


    public void setRightBubble(Image image, String text){
        this.getChildren().remove(rightTextBubble);

        ImageView imageView = new ImageView(image);
        rightTextBubble = new TextBubble(imageView, text);

        if(text.length() < 11) {
            imageView.setFitHeight(30);
            imageView.setFitWidth(100);
            rightTextBubble.setTranslateY(this.getTranslateY() + 50);
        } else if(text.length() < 21) {
            imageView.setFitHeight(30);
            imageView.setFitWidth(165);
            rightTextBubble.setTranslateY(this.getTranslateY() + 50);
        } else if(text.length() < 41) {
            imageView.setFitHeight(60);
            imageView.setFitWidth(165);
            rightTextBubble.setTranslateY(this.getTranslateY() + 35);
        } else {
            imageView.setFitHeight(90);
            imageView.setFitWidth(165);
            rightTextBubble.setTranslateY(this.getTranslateY() + 25);
        }

        rightTextBubble.setTranslateX(this.getTranslateX() + 250);
        this.getChildren().add(rightTextBubble);
    }
}

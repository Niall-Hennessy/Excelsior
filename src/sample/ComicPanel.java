package sample;

import javafx.scene.Cursor;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicReference;

public class ComicPanel extends Pane {

    ComicCharacter leftCharacter = new ComicCharacter();
    ComicCharacter rightCharacter =  new ComicCharacter();

    ComicCharacter selectedCharacter;

    TextBubble leftTextBubble = null;
    TextBubble rightTextBubble = null;

    Text topText;
    Text bottomText;

    public ComicPanel() throws FileNotFoundException {
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px");
        this.setPrefHeight(280);
        this.setPrefWidth(350);

        this.leftCharacter.setTranslateX(10);
        this.leftCharacter.setTranslateY(50);

        this.rightCharacter.setTranslateX(200);
        this.rightCharacter.setTranslateY(50);

        this.getChildren().add(leftCharacter);
        this.getChildren().add(rightCharacter);
    }

    public ComicCharacter getLeftCharacter() {
        return leftCharacter;
    }

    public void setLeftCharacter(String imagePath) throws FileNotFoundException {
        leftCharacter.setCharacterImageView(imagePath);
    }

    public ComicCharacter getRightCharacter() {
        return rightCharacter;
    }

    public void setRightCharacter(String imagePath) throws FileNotFoundException {
        rightCharacter.setCharacterImageView(imagePath);
        rightCharacter.flipOrientation();
    }

    public ComicCharacter getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(ComicCharacter selectedCharacter) {
        this.selectedCharacter = selectedCharacter;

        this.selectedCharacter.setStyle("-fx-border-width: 1; -fx-border-color: cyan");

        if(leftCharacter != selectedCharacter)
            leftCharacter.setStyle("-fx-border-width: 0");

        if(rightCharacter != selectedCharacter)
            rightCharacter.setStyle("-fx-border-width: 0");
    }

    public void removeBubble(String character){
    }

    public void setLeftBubble(Image image, String text, Font font) {
        this.getChildren().remove(leftTextBubble);
        double checkS = image.getWidth() + image.getHeight();

        ImageView imageView = new ImageView(image);

        if (checkS == 295.0) {       // regular shaped bubbles
            leftTextBubble = new TextBubble(imageView, text, font);

            if (text.length() < 21) {
                int len = text.length() * 7 + 45;
                imageView.setFitHeight(40);
                if (len > 165) {
                    imageView.setFitWidth(165);
                } else {
                    imageView.setFitWidth(len);
                }
                leftTextBubble.setTranslateY(this.getTranslateY() + 60);
            } else if (text.length() < 41) {
                imageView.setFitHeight(60);
                imageView.setFitWidth(165);
                leftTextBubble.setTranslateY(this.getTranslateY() + 40);
            } else {
                imageView.setFitHeight(85);
                imageView.setFitWidth(165);
                leftTextBubble.setTranslateY(this.getTranslateY() + 25);
            }
        } else {    // irregular bubbles
            leftTextBubble = new TextBubble(imageView, text, font);
            leftTextBubble.getText().setTranslateY(25);

            if (text.length() <= 20) {
                int len = text.length() * 7 + 45;
                imageView.setFitHeight(75);
                if (len > 165) {
                    imageView.setFitWidth(165);
                } else {
                    imageView.setFitWidth(len);
                }
                leftTextBubble.setTranslateY(this.getTranslateY() + 50);
                leftTextBubble.getText().setTranslateY(imageView.getTranslateY() + 33);
            } else if (text.length() <= 40) {
                imageView.setFitHeight(105);
                imageView.setFitWidth(165);
                leftTextBubble.setTranslateY(this.getTranslateY() + 40);
                leftTextBubble.getText().setTranslateY(imageView.getTranslateY() + 38);
            } else {
                imageView.setFitHeight(130);
                imageView.setFitWidth(165);
                leftTextBubble.setTranslateY(this.getTranslateY() + 25);
                leftTextBubble.getText().setTranslateY(imageView.getTranslateY() + 42);
            }
        }

        AtomicReference<Double> dragX = new AtomicReference<>((double) 0);
        AtomicReference<Double> dragY = new AtomicReference<>((double) 0);

        leftTextBubble.setOnMouseEntered(mouseEvent -> {
            leftTextBubble.getScene().setCursor(Cursor.MOVE);
        });

        leftTextBubble.setOnMouseExited(mouseEvent -> {
            leftTextBubble.getScene().setCursor(Cursor.DEFAULT);
        });

        leftTextBubble.setOnMousePressed(pressEvent -> {
            dragX.set(0.0);
            dragY.set(0.0);
            leftTextBubble.setOnMouseDragged(dragEvent -> {

                double offsetX = leftTextBubble.getTranslateX() + dragEvent.getScreenX() - pressEvent.getScreenX() - dragX.get();
                double offsetY = leftTextBubble.getTranslateY() + dragEvent.getScreenY() - pressEvent.getScreenY() - dragY.get();

                if(offsetX < 3)
                    offsetX = 3;
                else if(offsetX > 347 - leftTextBubble.getWidth())
                    offsetX = 347 - leftTextBubble.getWidth();

                if(offsetY < 3)
                    offsetY = 3;
                else if(offsetY > 263 - leftTextBubble.getHeight())
                    offsetY = 263 - leftTextBubble.getHeight();


                leftTextBubble.setTranslateX(offsetX);
                leftTextBubble.setTranslateY(offsetY);
                dragX.set(dragEvent.getScreenX() - pressEvent.getScreenX());
                dragY.set(dragEvent.getScreenY() - pressEvent.getScreenY());
            });
        });

        leftTextBubble.setTranslateX(leftTextBubble.getTranslateX() + dragX.get());
        leftTextBubble.setTranslateY(leftTextBubble.getTranslateY() + dragY.get());

        leftTextBubble.setTranslateX(this.getTranslateX() + 50);
        this.getChildren().add(leftTextBubble);
    }

    public void setRightBubble(Image image, String text, Font font){
        this.getChildren().remove(rightTextBubble);
        double checkS = image.getWidth() + image.getHeight();

        ImageView imageView = new ImageView(image);

        imageView.setRotationAxis(Rotate.Y_AXIS);
        imageView.setRotate(180);

        if (checkS == 295.0) {       // regular shaped bubbles
            rightTextBubble = new TextBubble(imageView, text, font);

            if (text.length() < 21) {
                int len = text.length() * 7 + 45;
                imageView.setFitHeight(40);
                if (len > 165) {
                    imageView.setFitWidth(165);
                } else {
                    imageView.setFitWidth(len);
                }
                rightTextBubble.setTranslateY(this.getTranslateY() + 60);
            } else if (text.length() < 41) {
                imageView.setFitHeight(60);
                imageView.setFitWidth(165);
                rightTextBubble.setTranslateY(this.getTranslateY() + 40);
            } else {
                imageView.setFitHeight(85);
                imageView.setFitWidth(165);
                rightTextBubble.setTranslateY(this.getTranslateY() + 25);
            }
        } else {    // irregular bubbles
            rightTextBubble = new TextBubble(imageView, text, font);
            rightTextBubble.getText().setTranslateY(25);

            if (text.length() < 21) {
                int len = text.length() * 7 + 45;
                imageView.setFitHeight(75);
                if (len > 165) {
                    imageView.setFitWidth(165);
                } else {
                    imageView.setFitWidth(len);
                }
                rightTextBubble.setTranslateY(this.getTranslateY() + 50);
                rightTextBubble.getText().setTranslateY(imageView.getTranslateY() + 33);
            } else if (text.length() < 41) {
                imageView.setFitHeight(105);
                imageView.setFitWidth(165);
                rightTextBubble.setTranslateY(this.getTranslateY() + 40);
                rightTextBubble.getText().setTranslateY(imageView.getTranslateY() + 38);
            } else {
                imageView.setFitHeight(130);
                imageView.setFitWidth(165);
                rightTextBubble.setTranslateY(this.getTranslateY() + 25);
                rightTextBubble.getText().setTranslateY(imageView.getTranslateY() + 42);
            }
        }

        AtomicReference<Double> dragX = new AtomicReference<>((double) 0);
        AtomicReference<Double> dragY = new AtomicReference<>((double) 0);

        rightTextBubble.setOnMouseEntered(mouseEvent -> {
            rightTextBubble.getScene().setCursor(Cursor.MOVE);
        });

        rightTextBubble.setOnMouseExited(mouseEvent -> {
            rightTextBubble.getScene().setCursor(Cursor.DEFAULT);
        });

        rightTextBubble.setOnMousePressed(pressEvent -> {
            dragX.set(0.0);
            dragY.set(0.0);
            rightTextBubble.setOnMouseDragged(dragEvent -> {

                double offsetX = rightTextBubble.getTranslateX() + dragEvent.getScreenX() - pressEvent.getScreenX() - dragX.get();
                double offsetY = rightTextBubble.getTranslateY() + dragEvent.getScreenY() - pressEvent.getScreenY() - dragY.get();

                if(offsetX < 3)
                    offsetX = 3;
                else if(offsetX > 347 - rightTextBubble.getWidth())
                    offsetX = 347 - rightTextBubble.getWidth();

                if(offsetY < 3)
                    offsetY = 3;
                else if(offsetY > 263 - rightTextBubble.getHeight())
                    offsetY = 263 - rightTextBubble.getHeight();


                rightTextBubble.setTranslateX(offsetX);
                rightTextBubble.setTranslateY(offsetY);
                dragX.set(dragEvent.getScreenX() - pressEvent.getScreenX());
                dragY.set(dragEvent.getScreenY() - pressEvent.getScreenY());
            });
        });

        rightTextBubble.setTranslateX(rightTextBubble.getTranslateX() + dragX.get());
        rightTextBubble.setTranslateY(rightTextBubble.getTranslateY() + dragY.get());

        rightTextBubble.setTranslateX(this.getTranslateX() + 200);
        this.getChildren().add(rightTextBubble);
    }
}

package sample;

import javafx.scene.Cursor;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.awt.*;
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

    TextCaption topText;
    TextCaption bottomText;

    int index;

    public ComicPanel() throws FileNotFoundException {
        this.setStyle("-fx-border-color: black; -fx-border-width: 3px");
        this.setPrefHeight(400);
        this.setPrefWidth(500);

        this.setMaxHeight(400);
        this.setMaxWidth(500);

        this.leftCharacter.setTranslateX(20);
        this.leftCharacter.setTranslateY(130);

        this.rightCharacter.setTranslateX(230);
        this.rightCharacter.setTranslateY(130);

        this.getChildren().add(leftCharacter);
        this.getChildren().add(rightCharacter);
    }

    public void select(){
        this.setStyle("-fx-border-color: hotpink; -fx-border-width: 5");
    }

    public void unselect(){
        this.setStyle("-fx-border-color: black; -fx-border-width: 3");
    }

    public ComicCharacter getLeftCharacter() {
        return leftCharacter;
    }

    public void setLeftCharacter(String imagePath) throws FileNotFoundException {
        leftCharacter.setCharacterImageView(imagePath);

        if(leftCharacter.imageName.matches("blank")){
            leftCharacter.setOnMouseEntered(mouseEvent -> {
            });

            leftCharacter.setOnMouseExited(mouseEvent -> {
            });

            leftCharacter.setOnMousePressed(pressEvent -> {
                leftCharacter.setOnMouseDragged(dragEvent -> {
                });
            });

            leftCharacter.setStyle("-fx-border-width: 0");
            return;
        }

        AtomicReference<Double> dragX = new AtomicReference<>((double) 0);
        AtomicReference<Double> dragY = new AtomicReference<>((double) 0);

        leftCharacter.setOnMouseEntered(mouseEvent -> {
            leftCharacter.getScene().setCursor(Cursor.MOVE);
        });

        leftCharacter.setOnMouseExited(mouseEvent -> {
            leftCharacter.getScene().setCursor(Cursor.DEFAULT);
        });

        leftCharacter.setOnMousePressed(pressEvent -> {

            setSelectedCharacter(leftCharacter);

            dragX.set(0.0);
            dragY.set(0.0);
            leftCharacter.setOnMouseDragged(dragEvent -> {

                double offsetX = leftCharacter.getTranslateX() + dragEvent.getScreenX() - pressEvent.getScreenX() - dragX.get();
                double offsetY = leftCharacter.getTranslateY() + dragEvent.getScreenY() - pressEvent.getScreenY() - dragY.get();

                if(offsetX < 3)
                    offsetX = 3;
                else if(offsetX > (this.getWidth()-3)/2 - leftCharacter.getWidth())
                    offsetX = (this.getWidth()-3)/2 - leftCharacter.getWidth();

                if(offsetY < 3)
                    offsetY = 3;
                else if(offsetY > this.getHeight()-3 - leftCharacter.getHeight())
                    offsetY = this.getHeight()-3 - leftCharacter.getHeight();


                leftCharacter.setTranslateX(offsetX);
                leftCharacter.setTranslateY(offsetY);
                dragX.set(dragEvent.getScreenX() - pressEvent.getScreenX());
                dragY.set(dragEvent.getScreenY() - pressEvent.getScreenY());
            });
        });

        leftCharacter.setTranslateX(leftCharacter.getTranslateX() + dragX.get());
        leftCharacter.setTranslateY(leftCharacter.getTranslateY() + dragY.get());
    }

    public ComicCharacter getRightCharacter() {
        return rightCharacter;
    }

    public void setRightCharacter(String imagePath) throws FileNotFoundException {
        rightCharacter.setCharacterImageView(imagePath);
        rightCharacter.flipOrientation();

        if(rightCharacter.imageName.matches("blank")){
            rightCharacter.setOnMouseEntered(mouseEvent -> {
            });

            rightCharacter.setOnMouseExited(mouseEvent -> {
            });

            rightCharacter.setOnMousePressed(pressEvent -> {
                rightCharacter.setOnMouseDragged(dragEvent -> {
                });
            });

            rightCharacter.setStyle("-fx-border-width: 0");
            return;
        }

        AtomicReference<Double> dragX = new AtomicReference<>((double) 0);
        AtomicReference<Double> dragY = new AtomicReference<>((double) 0);

        rightCharacter.setOnMouseEntered(mouseEvent -> {
            rightCharacter.getScene().setCursor(Cursor.MOVE);
        });

        rightCharacter.setOnMouseExited(mouseEvent -> {
            rightCharacter.getScene().setCursor(Cursor.DEFAULT);
        });

        rightCharacter.setOnMousePressed(pressEvent -> {

            setSelectedCharacter(rightCharacter);

            dragX.set(0.0);
            dragY.set(0.0);
            rightCharacter.setOnMouseDragged(dragEvent -> {

                double offsetX = rightCharacter.getTranslateX() + dragEvent.getScreenX() - pressEvent.getScreenX() - dragX.get();
                double offsetY = rightCharacter.getTranslateY() + dragEvent.getScreenY() - pressEvent.getScreenY() - dragY.get();

                if(offsetX < (this.getWidth()-3)/2)
                    offsetX = (this.getWidth()-3)/2;
                else if(offsetX > this.getWidth()-3 - rightCharacter.getWidth())
                    offsetX = this.getWidth()-3 - rightCharacter.getWidth();

                if(offsetY < 3)
                    offsetY = 3;
                else if(offsetY > this.getHeight()-3 - rightCharacter.getHeight())
                    offsetY = this.getHeight()-3 - rightCharacter.getHeight();


                rightCharacter.setTranslateX(offsetX);
                rightCharacter.setTranslateY(offsetY);
                dragX.set(dragEvent.getScreenX() - pressEvent.getScreenX());
                dragY.set(dragEvent.getScreenY() - pressEvent.getScreenY());
            });
        });

        rightCharacter.setTranslateX(rightCharacter.getTranslateX() + dragX.get());
        rightCharacter.setTranslateY(rightCharacter.getTranslateY() + dragY.get());
    }

    public ComicCharacter getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(ComicCharacter selectedCharacter) {

        if(selectedCharacter != null) {
            if (selectedCharacter.imageName.matches("blank"))
                return;

            this.selectedCharacter = selectedCharacter;

            this.selectedCharacter.setStyle("-fx-border-width: 1; -fx-border-color: cyan");
        }
        else {
            this.selectedCharacter = selectedCharacter;
        }

        if(leftCharacter != selectedCharacter)
            leftCharacter.setStyle("-fx-border-width: 0");

        if(rightCharacter != selectedCharacter)
            rightCharacter.setStyle("-fx-border-width: 0");
    }

    public void removeBubble(){
        if(selectedCharacter.equals(leftCharacter)){
            this.getChildren().remove(leftTextBubble);
            leftTextBubble = null;
        }
        else if(selectedCharacter.equals(rightCharacter)){
            this.getChildren().remove(rightTextBubble);
            rightTextBubble = null;
        }

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
                else if(offsetX > this.getWidth()-4 - leftTextBubble.getWidth())
                    offsetX = this.getWidth()-4 - leftTextBubble.getWidth();

                if(offsetY < 3)
                    offsetY = 3;
                else if(offsetY > this.getHeight()-4 - leftTextBubble.getHeight())
                    offsetY = this.getHeight()-4 - leftTextBubble.getHeight();


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
                else if(offsetX > this.getWidth()-4 - rightTextBubble.getWidth())
                    offsetX = this.getWidth()-4 - rightTextBubble.getWidth();

                if(offsetY < 3)
                    offsetY = 3;
                else if(offsetY > this.getHeight()-4 - rightTextBubble.getHeight())
                    offsetY = this.getHeight()-4 - rightTextBubble.getHeight();


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

    public void setTopText(String text, Font font){
        this.getChildren().remove(topText);
        topText = new TextCaption(text, font);
        topText.setTranslateY(-12);
        this.getChildren().add(topText);
    }

    public void setBottomText(String text, Font font){
        this.getChildren().remove(bottomText);
        bottomText = new TextCaption(text, font);
        bottomText.setTranslateY(this.getHeight()+22);
        this.getChildren().add(bottomText);
    }

    public void removeText(boolean[] top, boolean[] bottom){
        if (top[0]){
            this.getChildren().remove(topText);
            topText = null;
        }
        else if(bottom[0]){
            this.getChildren().remove(bottomText);
            bottomText = null;
        }

    }


}

package sample;

import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;

public class ComicPanel extends Pane {

    ComicCharacter leftCharacter = new ComicCharacter();
    ComicCharacter rightCharacter =  new ComicCharacter();

    ComicCharacter selectedCharacter;

    TextBubble leftTextBubble = null;
    TextBubble rightTextBubble = null;

    TextCaption topText;
    TextCaption bottomText;

    String background = "images/backgrounds/BlankBackground.jpg";

    Boolean isLocked;

    int index;

    public ComicPanel() throws FileNotFoundException {

        int width = (int) Screen.getPrimary().getBounds().getWidth();
        int height = (int) Screen.getPrimary().getBounds().getHeight();

        this.setMinHeight(height/2.4);
        this.setMinWidth(height/2.4 + height/9.6);

        this.setMaxHeight(height/2.4);
        this.setMaxWidth(height/2.4 + height/9.6);

        this.leftCharacter.setTranslateX(20);
        this.leftCharacter.setTranslateY(130);

        this.rightCharacter.setTranslateX((height/2.4 + height/9.6) - 180);
        this.rightCharacter.setTranslateY(130);

        this.getChildren().add(leftCharacter);
        this.getChildren().add(rightCharacter);

        this.setStyle("-fx-background-image: url('" + background + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch; "  +
                "-fx-background-size: " + (height/2.4 + height/9.6) + " " + height/2.4 + ";" +
                "-fx-border-color: black; " +
                "-fx-border-width: 3");

        isLocked = false;
    }

    public void select(){
        this.setStyle("-fx-background-image: url('" + background + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch; " +
                "-fx-background-size: " + this.getWidth() + " " + this.getHeight() + ";" +
                "-fx-border-color: HOTPINK; " +
                "-fx-border-width: 5");
    }

    public void unselect(){
        this.setStyle("-fx-background-image: url('" + background + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch; " +
                "-fx-background-size: " + this.getWidth() + " " + this.getHeight() + ";" +
                "-fx-border-color: BLACK; " +
                "-fx-border-width: 3");
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

    public void setRightCharacter(ComicCharacter rigthCharacter){
        this.rightCharacter = rigthCharacter;
    }

    public void setLeftCharacter(ComicCharacter leftCharacter){
        this.leftCharacter = leftCharacter;

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

    public void setCharacter(String imagePath, String side) throws FileNotFoundException {
        if(side.matches("left"))
            setLeftCharacter(imagePath);
        else if(side.matches("right"))
            setRightCharacter(imagePath);
    }

    public ComicCharacter getCharacter(String side){
        if(side.matches("left"))
            return leftCharacter;
        else if (side.matches("right"))
            return rightCharacter;
        return null;
    }

    public String getLeftRight() {
        if(selectedCharacter != null){
            if(selectedCharacter.equals(leftCharacter))
                return "left";
            else
                return "right";
        }

        return "";
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

    public void setLeftBubble(Image image, String text, Font font, String status) {
        this.getChildren().remove(leftTextBubble);
        double checkS = image.getWidth() + image.getHeight();

        ImageView imageView = new ImageView(image);

        leftTextBubble = new TextBubble(imageView, text, font, status);

        leftTextBubble.setTranslateX(leftCharacter.getTranslateX() + 70);
        leftTextBubble.setTranslateY(leftCharacter.getTranslateY() - 50);

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
        this.getChildren().add(leftTextBubble);
    }

    public void setLeftBubble(String imagePath, String text, Font font, String status) throws FileNotFoundException {
        this.getChildren().remove(leftTextBubble);

        ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/bubbles/" + imagePath + ".png")));

        leftTextBubble = new TextBubble(imageView, text, font, status);

        leftTextBubble.setTranslateX(leftCharacter.getTranslateX() + 70);
        leftTextBubble.setTranslateY(leftCharacter.getTranslateY() - 50);

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
        this.getChildren().add(leftTextBubble);
    }

    public void setRightBubble(Image image, String text, Font font, String status){
        this.getChildren().remove(rightTextBubble);
        double checkS = image.getWidth() + image.getHeight();

        ImageView imageView = new ImageView(image);

        rightTextBubble = new TextBubble(imageView, text, font, status);
        rightTextBubble.getBubble().setRotationAxis(Rotate.Y_AXIS);
        rightTextBubble.getBubble().setRotate(180);

        rightTextBubble.setTranslateX(rightCharacter.getTranslateX() - 20);
        rightTextBubble.setTranslateY(rightCharacter.getTranslateY() - 50);

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

        this.getChildren().add(rightTextBubble);
    }

    public void setRightBubble(String imagePath, String text, Font font, String status) throws FileNotFoundException {
        this.getChildren().remove(rightTextBubble);

        ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/bubbles/" + imagePath + ".png")));

        imageView.setRotationAxis(Rotate.Y_AXIS);
        imageView.setRotate(180);

        rightTextBubble = new TextBubble(imageView, text, font, status);


        rightTextBubble.setTranslateX(rightCharacter.getTranslateX() - 20);
        rightTextBubble.setTranslateY(rightCharacter.getTranslateY() - 50);

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

        this.getChildren().add(rightTextBubble);
    }

    public void setTopText(String text, Font font){
        this.getChildren().remove(topText);
        topText = new TextCaption(text, font);
        this.getChildren().add(topText);
    }

    public TextCaption getTopText() {
        return topText;
    }

    public void setBottomText(String text, Font font){

        int height = (int) Screen.getPrimary().getBounds().getHeight();

        this.getChildren().remove(bottomText);
        bottomText = new TextCaption(text, font);
        bottomText.text.setTextOrigin(VPos.TOP);
        bottomText.setTranslateY(height/2.4);
        this.getChildren().add(bottomText);
    }

    public TextCaption getBottomText() {
        return bottomText;
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

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public String getBackgroundString() { return background; }

    public void setBackgroundString(String path) {
        this.background = path;
    }

    public TextBubble getLeftTextBubble() {
        return leftTextBubble;
    }

    public TextBubble getRightTextBubble() {
        return rightTextBubble;
    }
}

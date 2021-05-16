package undo;

import comic.ComicStrip;
import comic.TextBubble;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UndoAction {

    HBox comicStrip;

    public UndoAction(HBox comicStrip) {
        this.comicStrip = comicStrip;
    }

    public void undo(){
    /*
            Actions that can be undone

            Rearranging panel: Panel, Previous Panel *To Do after fully implemented or on Sunday*
            Adding Text: Panel, T/B

             */

            /*
            Actually Implemented

            Adding Speech Bubble: Panel, L/R
            Locking Panel: Panel
            Add Background: Panel, Previous Image
            Changing Character Image: Panel, L/R, Previous Image
            Moving Character: Panel, L/R, Previous Location
            Delete Panel: Panel
            Moving Speech Bubble: Panel, L/R, Previous Location
            Flipping Character: Panel, L/R
            Gender Character: Panel, L/R
            Skin Character: Panel, L/R, Previous Colour
            Hair Character: Panel, L/R, Previous Colour
            Add Panel: Panel
        */

        if(UndoList.size() > 1) {

            Undo undo = UndoList.getUndo();

            if (undo.getOperation().matches("delete")) {
                comicStrip.getChildren().remove(undo.getObj());
                comicStrip.getChildren().add(undo.getComicPanel().getIndex(), undo.getComicPanel());
                comicStrip.getChildren().add(((Button)undo.getObj()));
            }
            else if(undo.getOperation().matches("background"))
            {
                undo.getComicPanel().setBackgroundString(undo.getValue_1());
                undo.getComicPanel().unselect();
            }
            else if (undo.getOperation().matches("flip")) {

                if (undo.getValue_1().matches("left"))
                    undo.getComicPanel().getLeftCharacter().flipOrientation();
                else
                    undo.getComicPanel().getRightCharacter().flipOrientation();

            }
            else if (undo.getOperation().matches("gender")){

                if (undo.getValue_1().matches("left"))
                    undo.getComicPanel().getLeftCharacter().genderSwap();
                else
                    undo.getComicPanel().getRightCharacter().genderSwap();
            }
            else if (undo.getOperation().matches("skin")){

                ((ColorPicker)undo.getObj()).setValue(Color.web(undo.getValue_2()));

                if (undo.getValue_1().matches("left"))
                    undo.getComicPanel().getLeftCharacter().setSkin(Color.web(undo.getValue_2()));
                else
                    undo.getComicPanel().getRightCharacter().setSkin(Color.web(undo.getValue_2()));
            }
            else if (undo.getOperation().matches("hair")) {

                ((ColorPicker)undo.getObj()).setValue(Color.web(undo.getValue_2()));

                if (undo.getValue_1().matches("left"))
                    undo.getComicPanel().getLeftCharacter().setHair(Color.web(undo.getValue_2()));
                else
                    undo.getComicPanel().getRightCharacter().setHair(Color.web(undo.getValue_2()));
            }
            else if (undo.getOperation().matches("caption")) {

                int i=0;
                while (undo.getValue_2().charAt(i) != '#')
                    i++;

                if(undo.getValue_1().matches("top")) {
                    undo.getComicPanel().setTopText(undo.getValue_2().substring(0,i), Font.font(undo.getValue_2().substring(i+1), FontWeight.NORMAL, 18));
                    undo.getComicPanel().getTopText().setText(new Text(undo.getValue_2().substring(0,i)));
                    undo.getComicPanel().getTopText().getTextObject().setFont(Font.font(undo.getValue_2().substring(i+1), FontWeight.NORMAL, 18));
                }else if(undo.getValue_1().matches("bottom")) {
                    undo.getComicPanel().setBottomText(undo.getValue_2().substring(0,i), Font.font(undo.getValue_2().substring(i+1), FontWeight.NORMAL, 18));
                    undo.getComicPanel().getBottomText().setText(new Text(undo.getValue_2().substring(0,i)));
                    undo.getComicPanel().getBottomText().getTextObject().setFont(Font.font(undo.getValue_2().substring(i+1), FontWeight.NORMAL, 18));
                }

            }
            else if(undo.getOperation().matches("bubble")) {
                if(undo.getValue_1().matches("left"))
                    undo.getComicPanel().setLeftTextBubble((TextBubble) undo.getObj());
                else
                    undo.getComicPanel().setRightTextBubble((TextBubble) undo.getObj());
            }
            else if (undo.getOperation().matches("panel")){
                comicStrip.getChildren().remove(undo.getComicPanel());
            }
            else if (undo.getOperation().matches("panelSwap")){
                comicStrip.getChildren().remove(undo.getComicPanel());
                comicStrip.getChildren().add(Integer.parseInt(undo.getValue_1()), undo.getComicPanel());
            }
            else if (undo.getOperation().matches("lock")){

                double height = Double.parseDouble(undo.getValue_1());

                undo.getComicPanel().setLocked(!undo.getComicPanel().getLocked());
                if(undo.getComicPanel().getLocked()) {
                    try {
                        ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/buttons/lock.png")));
                        imageView.setFitWidth(height * 0.09);
                        imageView.setFitHeight(height * 0.09);
                        ((Button)undo.getObj()).setGraphic(imageView);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/buttons/unlock.png")));
                        imageView.setFitWidth(height * 0.09);
                        imageView.setFitHeight(height * 0.09);
                        ((Button)undo.getObj()).setGraphic(imageView);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (undo.getOperation().matches("character")){

                if (undo.getValue_1().matches("left")) {
                    try {
                        undo.getComicPanel().getLeftCharacter().setCharacterImageView("src/images/characters/" + undo.getValue_2() + ".png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        undo.getComicPanel().getRightCharacter().setCharacterImageView("src/images/characters/" + undo.getValue_2() + ".png");
                        undo.getComicPanel().getRightCharacter().flipOrientation();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if(undo.getValue_2().matches("blank")) {
                    undo.getComicPanel().getCharacter(undo.getValue_1()).setStyle("");
                    undo.getComicPanel().setSelectedCharacter(null);
                }

            }
            else if (undo.getOperation().matches("moveCharacter")){

                int i=0;
                while (undo.getValue_2().charAt(i) != '#')
                    i++;

                if (undo.getValue_1().matches("left")){
                    undo.getComicPanel().getLeftCharacter().setTranslateX(Double.parseDouble(undo.getValue_2().substring(0, i)));
                    undo.getComicPanel().getLeftCharacter().setTranslateY(Double.parseDouble(undo.getValue_2().substring(i+1)));
                }
                else {
                    undo.getComicPanel().getRightCharacter().setTranslateX(Double.parseDouble(undo.getValue_2().substring(0, i)));
                    undo.getComicPanel().getRightCharacter().setTranslateY(Double.parseDouble(undo.getValue_2().substring(i+1)));
                }
            }
            else if (undo.getOperation().matches("moveBubble")){

                int i=0;
                while (undo.getValue_2().charAt(i) != '#')
                    i++;

                if (undo.getValue_1().matches("left")){
                    undo.getComicPanel().getLeftTextBubble().setTranslateX(Double.parseDouble(undo.getValue_2().substring(0, i)));
                    undo.getComicPanel().getLeftTextBubble().setTranslateY(Double.parseDouble(undo.getValue_2().substring(i+1)));
                }
                else {
                    undo.getComicPanel().getRightTextBubble().setTranslateX(Double.parseDouble(undo.getValue_2().substring(0, i)));
                    undo.getComicPanel().getRightTextBubble().setTranslateY(Double.parseDouble(undo.getValue_2().substring(i+1)));
                }
            }

        }
    }
}

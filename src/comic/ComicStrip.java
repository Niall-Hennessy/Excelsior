package comic;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ux.ButtonIcon;

import java.io.FileNotFoundException;

public class ComicStrip {

    static public HBox createComicStrip(double width, double height) throws FileNotFoundException {
        HBox comicStrip = new HBox();

        comicStrip.setAlignment(Pos.CENTER);
        comicStrip.setPrefHeight(height * 0.6 - 20);
        comicStrip.setPrefWidth(width - 20);
        comicStrip.getStyleClass().add("comicStrip");

        return comicStrip;
    }
}

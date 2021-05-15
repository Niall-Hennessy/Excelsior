package comic;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ux.ButtonIcon;

import java.io.FileNotFoundException;

public class ComicStrip extends HBox{

    ComicPanel currentPanel;

    public ComicStrip(double width, double height) throws FileNotFoundException {
        currentPanel = new ComicPanel();
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(height * 0.6 - 20);
        this.setPrefWidth(width - 20);
        this.getStylesheets().add("main/style.css");
        this.getStyleClass().add("comicStrip");

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.setDragDetect(true);
            }
        });
    }

    public void setCurrentPanel(ComicPanel comicPanel){
        currentPanel = comicPanel;
    }

    public ComicPanel getCurrentPanel() {
        return currentPanel;
    }
}

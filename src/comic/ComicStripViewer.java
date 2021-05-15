package comic;

import javafx.scene.control.ScrollPane;

public class ComicStripViewer extends ScrollPane {

    ComicStrip comicStrip;

    public ComicStripViewer(double width, double height){

        comicStrip = new ComicStrip(width, height);

        this.setContent(comicStrip);
        this.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        this.setPrefHeight(height * 0.6);
        this.setPrefWidth(width - 20);
    }

    public ComicStrip getComicStrip() {
        return comicStrip;
    }
}

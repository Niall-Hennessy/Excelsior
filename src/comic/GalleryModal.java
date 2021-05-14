package comic;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.File;

public class GalleryModal extends Modal {

    protected Pane galleryPane = new Pane();
    ImageView galleryImageView = new ImageView();

    public GalleryModal(double width, double height, ComicPanel cPanel) {
        super(width, height, cPanel);
    }

    public void addGallery(Insets insets){
        if(galleryPane.getChildren().size() == 0)
            galleryPane.getChildren().add(galleryImageView);

        galleryPane.setMinHeight(galleryImageView.getFitHeight()+100);

        HBox bubbleGallery = new HBox();
        bubbleGallery.getStyleClass().add("bubbles");

        File folder = new File("src/images/bubbles");
        File[] listOfFiles = folder.listFiles();

        layoutGrid.add(bubbleGallery, 0, 2, 3, 3);
        layoutGrid.setMargin(bubbleGallery, new Insets(10, 10, 10, 30));
    }
}

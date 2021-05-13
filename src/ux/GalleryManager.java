package ux;

import comic.ComicPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

enum Entity{
    rightCharacter,
    leftCharacter,
    background
}

public class GalleryManager {

    private ComicPanel[] comicPanel = null;
    final Stage addCharacter = new Stage();
    private double height;

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setComicPanel(ComicPanel[] comicPanel) {
        this.comicPanel = comicPanel;
    }

    private void openGallery(String path, Entity entity) {
        ScrollPane gallery = new ScrollPane();
        TilePane poses = new TilePane();

        if(entity == Entity.background)
            poses.setPrefColumns(3);
        else
            poses.setPrefColumns(7);

        poses.setHgap(3);
        poses.setVgap(3);

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (final File file : listOfFiles)
        {
            ImageView imageView;
            imageView = createImageView(file, entity);
            poses.getChildren().addAll(imageView);
        }

        gallery.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //horizontal
        gallery.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        gallery.setFitToWidth(true);
        gallery.setFitToHeight(true);
        gallery.setContent(poses);

        Scene scene = new Scene(gallery);

        addCharacter.setScene(scene);
        addCharacter.setTitle("Click to select");
        addCharacter.show();
        addCharacter.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight());
        addCharacter.setX( (Screen.getPrimary().getVisualBounds().getWidth()/2) - (addCharacter.getWidth()/2) );
        addCharacter.setY( (Screen.getPrimary().getVisualBounds().getHeight()/2) - (addCharacter.getHeight()/2) );

        if(addCharacter.getHeight() > Screen.getPrimary().getVisualBounds().getHeight()) {
            addCharacter.setY(0);
        }
    }

    private ImageView createImageView(final File imageFile, Entity entity) {

        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 150, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);

            switch(entity) {
                case rightCharacter:
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(mouseEvent.getClickCount() == 1) {
                                try {
                                    comicPanel[0].setRightCharacter(imageFile.getPath());
                                    addCharacter.close();
                                    comicPanel[0].setSelectedCharacter(comicPanel[0].getRightCharacter());
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    break;
                case leftCharacter:
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {

                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                                if (mouseEvent.getClickCount() == 1) {
                                    try {
                                        comicPanel[0].setLeftCharacter(imageFile.getPath());
                                        addCharacter.close();
                                        comicPanel[0].setSelectedCharacter(comicPanel[0].getLeftCharacter());
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
                    break;
                case background:
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                                if (mouseEvent.getClickCount() == 1) {
                                    comicPanel[0].setBackgroundString(normaliseURL(imageFile.getPath().substring(4)));

                                    comicPanel[0].setStyle("-fx-background-image: url('" + comicPanel[0].getBackgroundString() + "'); " +
                                            "-fx-background-position: center center; " +
                                            "-fx-background-repeat: stretch; "  +
                                            "-fx-background-size: " + (height/2.4 + height/9.6) + " " + height/2.4 + ";" +
                                            "-fx-border-color: HOTPINK; " +
                                            "-fx-border-width: 5");
                                    addCharacter.close();
                                }
                            }
                        }
                    });
                    break;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }

    private String normaliseURL(String url) {
        return url.replace('\\', '/');
    }

    public void setRightCharacter(String path) {
        Entity entity = Entity.rightCharacter;
        openGallery(path, entity);
    }

    public void setLeftCharacter(String path) {
        Entity entity = Entity.leftCharacter;
        openGallery(path, entity);
    }

    public void setBackground(String path) {
        Entity entity = Entity.background;
        openGallery(path, entity);
    }
}

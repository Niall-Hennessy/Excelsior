package sample;

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

public class GalleryStuff {

    ComicPanel[] comicPanel = null;
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
        gallery.setContent(poses);

        addCharacter.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        addCharacter.setHeight(Screen.getPrimary().getVisualBounds().getHeight());

        Scene scene = new Scene(gallery);
        addCharacter.setScene(scene);
        addCharacter.show();
    }

    private ImageView createImageView(final File imageFile, Entity entity) {

        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 150, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);

            switch(entity) {
                case rightCharacter:
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(mouseEvent.getClickCount() == 2) {
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

                                if (mouseEvent.getClickCount() == 2) {
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

                                if (mouseEvent.getClickCount() == 2) {
                                    comicPanel[0].setBackgroundString(normaliseURL(imageFile.getPath().substring(4)));
                                    System.out.println(normaliseURL(imageFile.getPath().substring(4)));

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

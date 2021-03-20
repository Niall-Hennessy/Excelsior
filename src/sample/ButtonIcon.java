package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ButtonIcon {

    public Button getButtonIcon(String imagePath) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(imagePath);
        Image image = new Image(input);
        ImageView view = new ImageView(image);
        view.setFitHeight(100);
        view.setFitWidth(100);
        Button buttonIcon = new Button("", view);
        buttonIcon.setPadding(new Insets(0,0,0,0));
        buttonIcon.setStyle("-fx-border-color: black");
        return buttonIcon;
    }
}

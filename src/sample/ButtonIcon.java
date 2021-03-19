package sample;

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
        view.setFitHeight(50);
        view.setFitWidth(50);
        Button buttonIcon = new Button("", view);

        return buttonIcon;
    }
}

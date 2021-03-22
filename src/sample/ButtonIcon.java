package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
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

    public static ColorPicker colorPickerStyling(ColorPicker xColor) throws FileNotFoundException {
        xColor.getStyleClass().add("button");
        xColor.setStyle("-fx-color-label-visible: false ; -fx-color-rect-width: 100; -fx-color-rect-height: 100; -fx-padding: -10");
        xColor.setPrefHeight(100);
        xColor.setPrefWidth(100);

        return xColor;
    }
}

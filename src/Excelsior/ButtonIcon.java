package Excelsior;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ButtonIcon {

    double height;
    public void setHeight(double height)
    {
        this.height = height;
    }

    public Button getButtonIcon(String imagePath) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(imagePath);
        Image image = new Image(input);
        ImageView view = new ImageView(image);
        view.setFitHeight(height * 0.09);
        view.setFitWidth(height * 0.09);
        Button buttonIcon = new Button("", view);
        buttonIcon.setPadding(new Insets(0,0,0,0));
        buttonIcon.setStyle("-fx-border-color: black");
        return buttonIcon;
    }

    public static ColorPicker colorPickerStyling(ColorPicker xColor, double height) throws FileNotFoundException {
        xColor.getStyleClass().add("button");
        xColor.setStyle("-fx-color-label-visible: false ; -fx-color-rect-width: "+ (height*0.09) +"; -fx-color-rect-height: "+ (height*0.09) +"; -fx-padding: -10");

        return xColor;
    }
}

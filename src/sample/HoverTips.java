package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HoverTips {
    Stage toolTip = null;

    public void setToolTip(Stage toolTip) {
        this.toolTip = toolTip;
    }

    public void colorToolTip(String message, MouseEvent mouseEvent, ColorPicker local) {
        Text testText = new Text(message);
        BorderPane borderPane = new BorderPane(testText);
        testText.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 20));
        toolTip.setX(mouseEvent.getScreenX() + 5);
        toolTip.setY(mouseEvent.getScreenY() - 15);
        Scene scene = new Scene(borderPane);
        toolTip.setScene(scene);
        toolTip.show();

        local.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                toolTip.setX(mouseEvent.getScreenX() + 5);
                toolTip.setY(mouseEvent.getScreenY() - 15);
            }
        });

        local.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                toolTip.close();
            }
        });
    }

    public void buttonToolTip(String message, MouseEvent mouseEvent, Button local) {
        Text testText = new Text(message);
        BorderPane borderPane = new BorderPane(testText);
        testText.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 20));
        toolTip.setX(mouseEvent.getScreenX() + 5);
        toolTip.setY(mouseEvent.getScreenY() - 15);
        Scene scene = new Scene(borderPane);
        toolTip.setScene(scene);
        toolTip.show();

        local.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                toolTip.setX(mouseEvent.getScreenX() + 5);
                toolTip.setY(mouseEvent.getScreenY() - 15);
            }
        });

        local.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                toolTip.close();
            }
        });
    }

    public void NoPanelSelectedTip(String text, Button local){
        Text testText = new Text(text);
        testText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        testText.setFill(Color.RED);
        BorderPane borderPane = new BorderPane(testText);
        Scene scene = new Scene(borderPane);
        toolTip.setScene(scene);
        toolTip.show();

        local.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                toolTip.setX(mouseEvent.getScreenX() + 5);
                toolTip.setY(mouseEvent.getScreenY() - 15);
            }
        });

        local.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                toolTip.close();
            }
        });
    }

    public void NoCharacterSelectedTip(String text, ColorPicker local){
        Text testText = new Text(text);
        testText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        testText.setFill(Color.RED);
        BorderPane borderPane = new BorderPane(testText);
        Scene scene = new Scene(borderPane);
        toolTip.setScene(scene);
        toolTip.show();

        local.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                toolTip.setX(mouseEvent.getScreenX() + 5);
                toolTip.setY(mouseEvent.getScreenY() - 15);
            }
        });

        local.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                toolTip.close();
            }
        });
    }
}

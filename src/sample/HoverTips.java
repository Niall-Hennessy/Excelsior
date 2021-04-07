package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HoverTips {
    Stage toolTip = null;
    Stage primaryStage = null;

    public void setToolTip(Stage toolTip) {
        this.toolTip = toolTip;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public void colorToolTip(String message, MouseEvent mouseEvent, ColorPicker local) {
        if(toolTip.isShowing()) {
            toolTip.initModality(Modality.APPLICATION_MODAL);
            toolTip.initOwner(primaryStage);
        }
        Text testText = new Text(message);
        BorderPane borderPane = new BorderPane(testText);
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
        if(toolTip.isShowing()) {
            toolTip.initModality(Modality.APPLICATION_MODAL);
            toolTip.initOwner(primaryStage);
        }
        Text testText = new Text(message);
        BorderPane borderPane = new BorderPane(testText);
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
}

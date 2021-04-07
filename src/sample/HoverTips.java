package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

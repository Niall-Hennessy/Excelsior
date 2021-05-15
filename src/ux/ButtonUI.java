package ux;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

public class ButtonUI {
    private MenuBar menuBar = new MenuBar();
    private FlowPane buttonLayout = new FlowPane();


    public void addLabelAndItems (Menu title, MenuItem ...options) {
        for(MenuItem i : options) {
            title.getItems().add(i);
        }
        menuBar.getMenus().add(title);
    }

    public MenuBar getMenuBar() {
        menuBar.getStyleClass().add("menuBar");
        return menuBar;
    }


    public void addButtonsToScrollPane(Button ...buttons) {
        for(Button i : buttons) {
            buttonLayout.getChildren().add(i);
            buttonLayout.setMargin(i, new Insets(10, 10, 10, 10));
        }
    }

    public void addOtherToScrollPane(ColorPicker skin, ColorPicker hair, Button undo, Button delete, Button lock) {
        buttonLayout.getChildren().add(skin);
        buttonLayout.getChildren().add(hair);
        buttonLayout.getChildren().add(undo);
        buttonLayout.getChildren().add(delete);
        buttonLayout.getChildren().add(lock);

        buttonLayout.setMargin(skin, new Insets(10, 10, 10, 10));
        buttonLayout.setMargin(hair, new Insets(10, 10, 10, 10));
        buttonLayout.setMargin(undo, new Insets(10, 10, 10, 10));
        buttonLayout.setMargin(delete, new Insets(10, 10, 10, 10));
        buttonLayout.setMargin(lock, new Insets(10, 10, 10, 10));
    }

    public FlowPane getFlowPane(Double width) {
        buttonLayout.setPrefWidth(width - 20);
        buttonLayout.getStyleClass().add("buttonLayout");

        return buttonLayout;
    }
}

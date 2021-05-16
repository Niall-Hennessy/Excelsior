package ux;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

//  Tools for making a top menu bar, build, expand and return menubar
//  Comic control buttons added and arranged in the flow pane, return as flowPane
public class ButtonUI {
    private MenuBar menuBar = new MenuBar();
    private FlowPane buttonLayout = new FlowPane();

    public void addLabelAndItems (String titleName, String ...options) {

        Menu title = new Menu(titleName);

        for(String i : options) {
            MenuItem newMenuItem = new MenuItem(i);
            title.getItems().add(newMenuItem);
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


    public MenuItem getMenuItem(String item){

        for(int i = 0; i < menuBar.getMenus().size(); i++) {
            for(int j = 0; j < menuBar.getMenus().get(i).getItems().size(); j++) {
                if(menuBar.getMenus().get(i).getItems().get(j).getText().matches(item)){
                    return menuBar.getMenus().get(i).getItems().get(j);
                }
            }
        }

        return null;
    }
}

package sample;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.List;

public class HelpMenu
{
    final Stage helpStage = new Stage();
    TabPane helpPane = new TabPane();

    public HelpMenu(){
        helpStage.setTitle("Help");
        helpPane.getStyleClass().add("helpPane");
    }

    //Create and Name Tabs
    public void createTab(String tabName){
        Tab newTab = new Tab(tabName);
        newTab.getStyleClass().add("helpTab");
        newTab.closableProperty().setValue(false);
        helpPane.getTabs().add(newTab);
    }

    //Set Tab Content
    public void setTabContent(String tab, String content){

        List<Tab> tabList = helpPane.getTabs();

        for(int i=0; i < tabList.size(); i++){
            if(tabList.get(i).getText().matches(tab)){
                Label label = new Label(content);

                ScrollPane instruction =  new ScrollPane(label);
                instruction.getStyleClass().add("contentPane");

                instruction.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                instruction.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                instruction.fitToWidthProperty().setValue(true);

                tabList.get(i).setContent(instruction);
                return;
            }
        }

    }

    public void showHelpMenu(){
        Scene scene = new Scene(helpPane);
        helpStage.setScene(scene);
        scene.getStylesheets().add("sample/style.css");
        helpStage.show();
    }

    //Set Width and Height
    public void setWidth(double width){
        helpStage.setWidth(width);
    }

    public void setHeight(double height){
        helpStage.setHeight(height);
    }
}

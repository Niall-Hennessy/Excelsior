package main;

import comic.*;
import io.LoadXML;
import io.SaveXML;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.*;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.w3c.dom.*;
import undo.Undo;
import undo.UndoList;
import ux.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Excelsior");
        primaryStage.setResizable(true);

        primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight());
        primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());

        double width = primaryStage.getWidth();
        double height = primaryStage.getHeight();

        double widthPrcnt = (width / 100) * 95;
        double heightPrcnt = (height / 100) * 95;

        primaryStage.setHeight(heightPrcnt);
        primaryStage.setWidth(widthPrcnt);

        GridPane mainPane = new GridPane();

        final String[] premise = {""};

        final ComicPanel[] comicPanel = {new ComicPanel()};

        List<ComicPanel> deletedPanels = new Stack<>();

        final String[] character = new String[1];

        MenuBar menuBar = new MenuBar();
        menuBar.getStyleClass().add("menuBar");

        Menu file = new Menu("File");
        MenuItem new_project = new MenuItem("New Project");
        MenuItem save_xml = new MenuItem("Save XML");
        MenuItem save_html = new MenuItem("Save HTML");
        MenuItem load_xml = new MenuItem("Load XML");
        MenuItem add_character = new MenuItem("Add Character");
        MenuItem add_background = new MenuItem("Add Background");
        file.getItems().add(new_project);
        file.getItems().add(save_xml);
        file.getItems().add(save_html);
        file.getItems().add(load_xml);
        file.getItems().add(add_character);
        file.getItems().add(add_background);


        menuBar.getMenus().add(file);


        Menu help = new Menu("Help");
        MenuItem helpItem = new MenuItem("Help");
        help.getItems().add(helpItem);
        menuBar.getMenus().add(help);

        HBox menuBox = new HBox(menuBar);

        HBox comicStrip = new HBox();

        final ColorPicker[] skinColorPicker = {new ColorPicker()};
        final ColorPicker[] hairColorPicker = {new ColorPicker()};
        skinColorPicker[0] = ButtonIcon.colorPickerStyling(skinColorPicker[0], primaryStage.getHeight());
        hairColorPicker[0] = ButtonIcon.colorPickerStyling(hairColorPicker[0], primaryStage.getHeight());

        FlowPane buttonLayout = new FlowPane();
        buttonLayout.setPrefWidth(width - 20);
        buttonLayout.getStyleClass().add("buttonLayout");

        menuBox.setMaxHeight(buttonLayout.getHeight()/2);

        ButtonIcon buttonIcon = new ButtonIcon();

        buttonIcon.setHeight(primaryStage.getHeight());

        Button rightCharacter = buttonIcon.getButtonIcon("src/images/buttons/lookLeft.png");
        Button leftCharacter = buttonIcon.getButtonIcon("src/images/buttons/lookRight.png");
        Button flipButton = buttonIcon.getButtonIcon("src/images/buttons/swapLR.png");
        Button genderButton = buttonIcon.getButtonIcon("src/images/buttons/MFButton.png");
        Button textButton = buttonIcon.getButtonIcon("src/images/buttons/T_Button.png");
        Button bubbleButton = buttonIcon.getButtonIcon("src/images/buttons/speech_bubble.png");
        Button backgroundButton = buttonIcon.getButtonIcon("src/images/buttons/background_button.png");
        final Button[] lockButton = {buttonIcon.getButtonIcon("src/images/buttons/unlock.png")};
        Button deleteButton = buttonIcon.getButtonIcon("src/images/buttons/delete.png");
        Button undoButton = buttonIcon.getButtonIcon("src/images/buttons/undo_button.png");


        final Stage toolTip = new Stage();
        toolTip.initStyle(StageStyle.UNDECORATED);

        HoverTips hoverTips = new HoverTips();
        hoverTips.setToolTip(toolTip);

        String tipRightCharacter    = "Set Right Character";
        String tipLeftCharacter     = "Set Left Character";
        String tipFlipButton        = "Flip Direction of the Selected Character";
        String tipGenderButton      = "Change Selected Character's Gender";
        String tipTextButton        = "Set Caption Text for Bottom or Top of Panel";
        String tipBubbleButton      = "Insert Text Bubble for Selected Character";
        String tipBackgroundButton      = "Add a Background to the Panel";
        String tipLockButton      = "Click to Lock this Panel to disable further editing";
        String tipUnlockButton      = "Click to Unlock this Panel to enable further editing";
        String tipLocked      = "This Panel is Locked Unlock to Allow Further Editing";
        String tipDeleteButton      = "Delete Selected Panel";
        String tipUndoButton   = "Undo Last Action";
        String tipskinColorPicker   = "Choose Skin Colour";
        String tiphairColorPicker   = "Choose Hair Colour";
        String tipNoCharacterSelected = "No character has been selected";
        String tipNoPanelSelected = "A comic panel must be selected first";


        rightCharacter.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipRightCharacter, mouseEvent, rightCharacter);
            }
        });

        leftCharacter.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipLeftCharacter, mouseEvent, leftCharacter);
            }
        });

        flipButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipFlipButton, mouseEvent, flipButton);
            }
        });

        genderButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipGenderButton, mouseEvent, genderButton);
            }
        });

        textButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipTextButton, mouseEvent, textButton);
            }
        });

        bubbleButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipBubbleButton, mouseEvent, bubbleButton);
            }
        });

        backgroundButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipBackgroundButton, mouseEvent, backgroundButton);
            }
        });

        deleteButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipDeleteButton, mouseEvent, deleteButton);
            }
        });

        lockButton[0].setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(comicPanel[0] != null) {
                    if (comicPanel[0].getLocked())
                        hoverTips.buttonToolTip(tipUnlockButton, mouseEvent, lockButton[0]);
                    else
                        hoverTips.buttonToolTip(tipLockButton, mouseEvent, lockButton[0]);
                }
            }
        });

        undoButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipUndoButton, mouseEvent, undoButton);
            }
        });


        save_xml.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                SaveXML saveXML = new SaveXML(comicStrip);
            }
        });

        add_character.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Add a character");

                File source = fileChooser.showOpenDialog(null);
                if(source != null) {
                    File dest = new File("src/images/characters/" + source.getName());

                    try {
                        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        add_background.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Add a background image");

                File source = fileChooser.showOpenDialog(null);

                if(source != null) {
                    File dest = new File("src/images/backgrounds/" + source.getName());

                    try {
                        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                HelpMenu helpMenu = new HelpMenu();

                helpMenu.setWidth(Screen.getPrimary().getVisualBounds().getWidth()/1.7);
                helpMenu.setHeight(Screen.getPrimary().getVisualBounds().getHeight()/2);

                helpMenu.createTab("Character");
                helpMenu.createTab("Speech Bubbles");
                helpMenu.createTab("Skin/Hair");
                helpMenu.createTab("Caption");
                helpMenu.createTab("Backgrounds");
                helpMenu.createTab("Lock/Unlock");
                helpMenu.createTab("Undo/Delete");

                helpMenu.setTabContent("Character", "\n Let's add a character to your comic!\n " +
                        "\n First press the plus icon in the white panel." +
                        "\n Now that a black comic panel has appeared, select it so that it is highlighted." +
                        "\n Click on the character icon to choose a left or right character." +
                        "\n Double click a character pose from the gallery." +
                        "\n Use the Flip Button to change which way they are facing." +
                        "\n Use the M/F button to change their gender.");

                helpMenu.setTabContent("Speech Bubbles", "\n Let's get your characters talking!\n " +
                        "\n Note: You have to have a character in your panel before you can make them talk.\n " +
                        "\n Click on the speech bubble icon." +
                        "\n Choose what bubble you want." +
                        "\n Write in the text-box what you want them to say - Careful, there is a character limit." +
                        "\n Choose if you want the text in italic, or bold, or both." +
                        "\n Hit Submit and voila!" +
                        "\n Hit Cancel if you change your mind." +
                        "\n Hit Delete if you want to get rid of the bubble.");

                helpMenu.setTabContent("Skin/Hair", "\n Let's add some colour!\n " +
                        "\n Select the character who's Skin/Hair you wish to change." +
                        "\n Select the Skin/Hair colour picker to select a new colour.");

                helpMenu.setTabContent("Caption", "\n Let's caption your panel!\n " +
                        "\n Hit the caption button." +
                        "\n Write what you want the caption to be." +
                        "\n Choose a font for you caption." +
                        "\n Hit 'Apply' and see it appear." +
                        "\n Hit 'Cancel' if you change your mind." +
                        "\n Hit 'Delete' after selecting either the 'Top Text' or 'Bottom Text' to remove the caption.");

                helpMenu.setTabContent("Backgrounds", "\n Let's add a background!\n" +
                        "\n Hit the background button and double click the image you want to use.");

                helpMenu.setTabContent("Lock/Unlock", "\n Let's protect your work!\n" +
                        "\n When you have finished working on your panel, to prevent accidental changes hit the open lock button." +
                        "\n This prevents changes to the panel until you unlock it." +
                        "\n To unlock the panel hit the now closed lock button.");

                helpMenu.setTabContent("Undo/Delete", "\n Undo: \n" +
                        "\n Hit the back arrow button to undo or hit 'z' on your keyboard ." +
                        "\n \n Delete:\n" +
                        "\n Hit the red trash can button to delete the selected panel. ");

                helpMenu.showHelpMenu();
            }
        });



        GalleryManager galleryView = new GalleryManager();



        rightCharacter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(!comicStrip.getChildren().contains(comicPanel[0])){
                    hoverTips.NoPanelSelectedTip(tipNoPanelSelected, rightCharacter);
                    return;
                }

                if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, rightCharacter);
                    return;
                }

                Undo undo;

                if(comicPanel[0].getRightCharacter().getImageName() == null)
                    undo = new Undo("character", comicPanel[0], "right", "blank");
                else
                    undo = new Undo("character", comicPanel[0], "right", comicPanel[0].getRightCharacter().getImageName().toString());

                UndoList.addUndo(undo);

                String path = "src/images/characters";
                galleryView.setComicPanel(comicPanel);
                galleryView.setRightCharacter(path);
                skinColorPicker[0].setValue(comicPanel[0].getRightCharacter().getSkin());
                hairColorPicker[0].setValue(comicPanel[0].getRightCharacter().getHair());
            }
        });

        leftCharacter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(!comicStrip.getChildren().contains(comicPanel[0])){
                    hoverTips.NoPanelSelectedTip(tipNoPanelSelected, leftCharacter);
                    return;
                }

                if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, leftCharacter);
                    return;
                }

                Undo undo;

                if(comicPanel[0].getLeftCharacter().getImageName() == null)
                    undo = new Undo("character", comicPanel[0], "left", "blank");
                else
                    undo = new Undo("character", comicPanel[0], "left", comicPanel[0].getLeftCharacter().getImageName().toString());

                UndoList.addUndo(undo);

                String path = "src/images/characters";
                galleryView.setComicPanel(comicPanel);
                galleryView.setLeftCharacter(path);
                skinColorPicker[0].setValue(comicPanel[0].getLeftCharacter().getSkin());
                hairColorPicker[0].setValue(comicPanel[0].getLeftCharacter().getHair());
            }
        });

        flipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(comicPanel[0].getSelectedCharacter() != null && !comicPanel[0].getLocked()) {
                    comicPanel[0].getSelectedCharacter().flipOrientation();

                    Undo undo = new Undo("flip", comicPanel[0], comicPanel[0].getLeftRight());
                    UndoList.addUndo(undo);
                } else if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, flipButton);
                    return;
                }
                else {
                    hoverTips.NoPanelSelectedTip(tipNoCharacterSelected, flipButton);
                }
            }
        });

        genderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(comicPanel[0].getSelectedCharacter() != null && !comicPanel[0].getLocked()) {
                    comicPanel[0].getSelectedCharacter().genderSwap();
                    Undo undo = new Undo("gender", comicPanel[0], comicPanel[0].getLeftRight());
                    UndoList.addUndo(undo);
                }else if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, genderButton);
                    return;
                }
                else {
                    hoverTips.NoPanelSelectedTip(tipNoCharacterSelected, genderButton);
                }
            }
        });

        bubbleButton.setOnAction(new EventHandler<ActionEvent>() {

//            final Stage addBubble = new Stage(StageStyle.UNDECORATED);
//
//            Pane bubbleDisplay = new Pane();
//            ImageView bubbleImageView = new ImageView();
//            String bubbleName;
//
//            private double xOffset = 0;
//            private double yOffset = 0;

            @Override
            public void handle(ActionEvent event) {

                if(!comicStrip.getChildren().contains(comicPanel[0])){
                    hoverTips.NoPanelSelectedTip(tipNoCharacterSelected, bubbleButton);
                    return;
                }

                if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, bubbleButton);
                    return;
                }

                if(comicPanel[0].getSelectedCharacter() == null) {
                    hoverTips.NoPanelSelectedTip(tipNoCharacterSelected, bubbleButton);
                    return;
                }

                GalleryModal bubbleGallery = new GalleryModal(width/2 -200, height/2, comicPanel[0]);

                bubbleGallery.addSubmit();
                bubbleGallery.addGallery();
                bubbleGallery.addTextField();
                bubbleGallery.addEscape();
                bubbleGallery.addCancel();
                bubbleGallery.addDelete();
                bubbleGallery.addFormatButtons();
                bubbleGallery.show();
            }
        });

        backgroundButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!comicStrip.getChildren().contains(comicPanel[0])){
                    hoverTips.NoPanelSelectedTip(tipNoPanelSelected, backgroundButton);
                    return;
                }

                if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, backgroundButton);
                    return;
                }

                Undo undo = new Undo("background", comicPanel[0], comicPanel[0].getBackgroundString());
                UndoList.addUndo(undo);

                String path = "src/images/backgrounds";
                galleryView.setComicPanel(comicPanel);
                galleryView.setHeight(height);
                galleryView.setBackground(path);
            }
        });

        lockButton[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                double imageWidth = backgroundButton.getWidth();
                double imageHeight = backgroundButton.getHeight();

                if(comicStrip.getChildren().contains(comicPanel[0])) {
                    comicPanel[0].setLocked(!comicPanel[0].getLocked());

                    if(comicPanel[0].getLocked()) {
                        try {
                            ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/buttons/lock.png")));
                            imageView.setFitWidth(imageWidth-4);
                            imageView.setFitHeight(imageHeight-4);
                            lockButton[0].setGraphic(imageView);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/buttons/unlock.png")));
                            imageView.setFitWidth(imageWidth-4);
                            imageView.setFitHeight(imageHeight-4);
                            lockButton[0].setGraphic(imageView);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    Undo undo = new Undo("lock", comicPanel[0]);
                    UndoList.addUndo(undo);

                }
                else {
                    hoverTips.NoPanelSelectedTip(tipNoCharacterSelected, lockButton[0]);
                }
            }
        });

        textButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextModal textModal = new TextModal(width/2 -200, height/2, comicPanel[0]);

                if(!comicStrip.getChildren().contains(comicPanel[0])){
                    hoverTips.NoPanelSelectedTip(tipNoPanelSelected, textButton);
                    return;
                }

                if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, textButton);
                    return;
                }

                textModal.addSubmit();
                textModal.addTextField();
                textModal.addBooleanButtons("Top Text", "Bottom Text");
                textModal.addCancel();
                textModal.addDelete();
                textModal.addComboBox( "Segoe UI", "Verdana", "Times New Roman", "Arial");
                textModal.addEscape();
                textModal.show();
            }
        });

        skinColorPicker[0].setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, skinColorPicker[0]);

                    Color color = skinColorPicker[0].getValue();

                    skinColorPicker[0].setOnAction(new EventHandler() {
                        public void handle(Event t) {
                            skinColorPicker[0].setValue(color);
                        }
                    });
                }
                else {
                    if (comicPanel[0].getSelectedCharacter() != null) {
                        hoverTips.colorToolTip(tipskinColorPicker, mouseEvent, skinColorPicker[0]);
                    } else {
                        hoverTips.NoCharacterSelectedTip(tipNoCharacterSelected, skinColorPicker[0]);
                    }
                    skinColorPicker[0].setOnAction(new EventHandler() {
                        Color current = skinColorPicker[0].getValue();
                        public void handle(Event t) {

                            if(comicPanel[0].getSelectedCharacter() != null) {
                                comicPanel[0].getSelectedCharacter().setSkin(skinColorPicker[0].getValue());
                                Undo undo = new Undo("skin", comicPanel[0], comicPanel[0].getLeftRight(), current.toString());
                                UndoList.addUndo(undo);
                            }
                            else {
                                hoverTips.NoCharacterSelectedTip(tipNoCharacterSelected, skinColorPicker[0]);
                            }
                        }
                    });
                }
            }
        });

        hairColorPicker[0].setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {

                if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, hairColorPicker[0]);

                    Color color = hairColorPicker[0].getValue();

                    hairColorPicker[0].setOnAction(new EventHandler() {
                        public void handle(Event t) {
                            hairColorPicker[0].setValue(color);
                        }
                    });
                }
                else {
                    if (comicPanel[0].getSelectedCharacter() != null) {
                        hoverTips.colorToolTip(tiphairColorPicker, mouseEvent, hairColorPicker[0]);
                    } else {
                        hoverTips.NoCharacterSelectedTip(tipNoCharacterSelected, hairColorPicker[0]);
                    }

                    hairColorPicker[0].setOnAction(new EventHandler() {
                        Color current = hairColorPicker[0].getValue();
                        public void handle(Event t) {

                            if(comicPanel[0].getSelectedCharacter() != null) {
                                comicPanel[0].getSelectedCharacter().setHair(hairColorPicker[0].getValue());
                                Undo undo = new Undo("hair", comicPanel[0], comicPanel[0].getLeftRight(), current.toString());
                                UndoList.addUndo(undo);
                            }else {
                                hoverTips.NoCharacterSelectedTip(tipNoCharacterSelected, hairColorPicker[0]);
                            }
                        }
                    });
                }
            }
        });

        Text skin = new Text();
        skin.setText("Skin:");
        skin.setStyle("-fx-font-size: 36; -fx-font-family: 'Lucida Console'");

        Text hair = new Text();
        hair.setText("Hair:");
        hair.setStyle("-fx-font-size: 36; -fx-font-family: 'Lucida Console'");

        buttonLayout.getChildren().add(leftCharacter);
        buttonLayout.getChildren().add(rightCharacter);
        buttonLayout.getChildren().add(flipButton);
        buttonLayout.getChildren().add(genderButton);
        buttonLayout.getChildren().add(textButton);
        buttonLayout.getChildren().add(bubbleButton);
        buttonLayout.getChildren().add(backgroundButton);
        buttonLayout.getChildren().add(skinColorPicker[0]);
        buttonLayout.getChildren().add(hairColorPicker[0]);
        buttonLayout.getChildren().add(undoButton);
        buttonLayout.getChildren().add(deleteButton);
        buttonLayout.getChildren().add(lockButton[0]);

        buttonLayout.setMargin(undoButton, new Insets(10,10,10,10));
        buttonLayout.setMargin(leftCharacter, new Insets(10,10,10,10));
        buttonLayout.setMargin(rightCharacter, new Insets(10,10,10,10));
        buttonLayout.setMargin(flipButton, new Insets(10,10,10,10));
        buttonLayout.setMargin(genderButton, new Insets(10,10,10,10));
        buttonLayout.setMargin(textButton, new Insets(10,10,10,10));
        buttonLayout.setMargin(bubbleButton, new Insets(10,10,10,10));
        buttonLayout.setMargin(backgroundButton, new Insets(10,10,10,10));
        buttonLayout.setMargin(skinColorPicker[0], new Insets(10,10,10,10));
        buttonLayout.setMargin(hairColorPicker[0], new Insets(10,10,10,10));
        buttonLayout.setMargin(deleteButton, new Insets(10,10,10,10));
        buttonLayout.setMargin(lockButton[0], new Insets(10,10,10,10));

        Button newPanelRight = buttonIcon.getButtonIcon("src/images/buttons/plus.png");
        Button newPanelLeft = buttonIcon.getButtonIcon("src/images/buttons/plus.png");
        newPanelRight.setScaleX(0.5);
        newPanelRight.setScaleY(0.5);

        comicStrip.getChildren().add(newPanelRight);
        comicStrip.getChildren().add(newPanelLeft);
        comicStrip.setAlignment(Pos.CENTER);
        comicStrip.setPrefHeight(height * 0.6 - 20);
        comicStrip.setPrefWidth(width - 20);
        comicStrip.getStyleClass().add("comicStrip");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(comicStrip);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefHeight(height * 0.6);
        scrollPane.setPrefWidth(width - 20);

        comicStrip.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.setDragDetect(true);
            }
        });

        mainPane.addRow(0, menuBox);
        mainPane.addRow(1, buttonLayout);
        mainPane.addRow(2, scrollPane);
        mainPane.getStyleClass().add("mainPane");

        scrollPane.getStyleClass().add("scrollPane");

        mainPane.setMargin(scrollPane, new Insets(width * 0.05, width * 0.01,0,width * 0.01));

        newPanelRight.setVisible(false);
        newPanelLeft.setVisible(false);

        load_xml.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoadXML loadXML = new LoadXML(comicStrip, premise[0], newPanelLeft, newPanelRight, width, height);
            }
        });

        save_html.setOnAction((ActionEvent event) -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Warning, number of comic panels does not match layout.\nDo you still wish to continue?");

            Stage popupwindow = new Stage();

            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.initStyle(StageStyle.UNDECORATED);

            Label popupPrompt = new Label("Title of your Comic Strip:");
            popupPrompt.getStyleClass().add("popUpPrompt");

            TextField popupField = new TextField();
            popupField.setPromptText("Title");
            popupField.setMinHeight(50);

            TextField htmlRow = new TextField();
            htmlRow.setPromptText("Number of Rows");
            htmlRow.setMinHeight(30);
            htmlRow.setMinWidth(10);

            TextField htmlCol = new TextField();
            htmlCol.setPromptText("Number of Columns");
            htmlCol.setMinHeight(30);
            htmlCol.setMinWidth(10);

            if (premise[0] != null)
                popupField.setText(premise[0]);

            Button popupNext = new Button("Next");
            popupNext.getStyleClass().add("popUpNext");

            popupNext.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    if (popupField.getText().isEmpty() || popupField.getText() == null)
                        return;

                    if((Integer.parseInt(htmlRow.getText()) * Integer.parseInt(htmlCol.getText()) < comicStrip.getChildren().size() - 2)){
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            popupwindow.close();
                        }
                    }else{
                        popupwindow.close();
                    }
                }
            });

            ComboBox popup_box = new ComboBox();
            popup_box.getStyleClass().add("400x400");
            popup_box.getItems().addAll("400x400", "600x600", "800x800", "1200x1200");
            popup_box.getSelectionModel().selectFirst();

            final Stage saveHTML = new Stage();

            if (saveHTML.isShowing()) {
                saveHTML.initOwner(primaryStage);
            }

            Button popupClose = new Button("Cancel");
            popupClose.getStyleClass().add("popUpCancel");
            final boolean[] cancel = {false};

            popupClose.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    cancel[0] = true;
                    popupwindow.close();
                }
            });

            HBox popupButtons = new HBox(5);
            popupButtons.getChildren().addAll(popupNext, popupClose);
            popupButtons.setAlignment(Pos.CENTER);

            HBox rowColField = new HBox(5);
            rowColField.getChildren().addAll(htmlRow, htmlCol);
            rowColField.setAlignment(Pos.CENTER);

            VBox popupLayout = new VBox(10);
            popupLayout.getStyleClass().add("popUpLayout");

            popupLayout.getChildren().addAll(popupPrompt, popupField, rowColField, popup_box, popupButtons);

            popupLayout.setAlignment(Pos.CENTER);

            Scene popupScene = new Scene(popupLayout, 300, 250);
            popupScene.getStylesheets().add("main/style.css");
            popupwindow.setScene(popupScene);

            popupScene.setOnMousePressed(pressEvent -> {
                popupScene.setOnMouseDragged(dragEvent -> {
                    popupwindow.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                    popupwindow.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                });
            });

            popupwindow.showAndWait();

            if (cancel[0]) {
                return;
            }
            comicPanel[0].unselect();
            comicPanel[0].setSelectedCharacter(null);

            FileChooser fileChooser = new FileChooser();
            File saveFile = fileChooser.showSaveDialog(saveHTML);

            if (saveFile != null)
                saveFile.mkdir();

            String value = popup_box.getValue().toString();

            value = value.substring(0, value.length() / 2);

            int saveSize = Integer.parseInt(value);

            double scaleFactor = (saveSize / (height / 2.4 + height / 9.6));

            double topMaxHeight = 0;
            double maxHeight = 0;
            double maxWidth = 0;

            for (int i = 1; i < comicStrip.getChildren().size() - 1; i++) {
                if (((ComicPanel) comicStrip.getChildren().get(i)).getTopText() != null && ((ComicPanel) comicStrip.getChildren().get(i)).getTopText().getHeight() > topMaxHeight)
                    topMaxHeight = ((ComicPanel) comicStrip.getChildren().get(i)).getTopText().getHeight();

                if (((ComicPanel) comicStrip.getChildren().get(i)).getHeight() > maxHeight)
                    maxHeight = ((ComicPanel) comicStrip.getChildren().get(i)).getHeight();

                if (((ComicPanel) comicStrip.getChildren().get(i)).getWidth() > maxWidth)
                    maxWidth = ((ComicPanel) comicStrip.getChildren().get(i)).getWidth();
            }

            for (int i = 1; i < comicStrip.getChildren().size() - 1; i++) {

                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setTransform(Transform.scale(scaleFactor, scaleFactor, scaleFactor, scaleFactor));

                WritableImage sizer = new WritableImage(saveSize, saveSize);

                Image img = comicStrip.getChildren().get(i).snapshot(snapshotParameters, sizer);

                double whiteSpace = topMaxHeight;

                if (((ComicPanel) comicStrip.getChildren().get(i)).getTopText() != null)
                    whiteSpace = topMaxHeight - ((ComicPanel) comicStrip.getChildren().get(i)).getTopText().getHeight();

                whiteSpace = whiteSpace * scaleFactor;

                int imgWidth = (int) img.getWidth();
                int imgHeight = (int) img.getHeight();

                WritableImage writableImage = new WritableImage(saveSize, saveSize);

                PixelReader pixelReader = img.getPixelReader();
                PixelWriter pixelWriter = writableImage.getPixelWriter();

                int y = 0;

                if (imgHeight > saveSize)
                    imgHeight = saveSize;

                if (imgWidth > saveSize)
                    imgWidth = saveSize;

                for (; y < whiteSpace; y++) {
                    for (int x = 0; x < imgWidth; x++) {
                        pixelWriter.setColor(x, y, Color.WHITE);
                    }
                }

                int v = y;

                for (; y < imgHeight; y++) {
                    for (int x = 0; x < imgWidth; x++) {
                        pixelWriter.setColor(x, y, pixelReader.getColor(x, (y - v)));
                    }
                }

                File toSave = new File(saveFile.getPath() + "\\" + (i - 1) + ".png");

                if (toSave != null) {
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(writableImage,
                                null), "png", toSave);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                if (toSave != null) {
                    StreamResult result = new StreamResult(toSave);
                    StreamResult consoleResult = new StreamResult(System.out);
                }
            }

            int row = Integer.parseInt(htmlRow.getText());
            int col = Integer.parseInt(htmlCol.getText());

            File htmlFile = new File(saveFile.getPath() + "\\index.html");
            try {
                FileWriter myWriter = new FileWriter(saveFile.getPath() + "\\index.html");
                myWriter.write(
                        "<!DOCTYPE html>\n" +
                                "<html>\n" +
                                "<head>\n" +
                                "<style>\n" +
                                "table, th, td {\n" +
                                "border: 10px solid white;\n" +
                                "border-collapse: collapse;\n" +
                                "}\n" +
                                "</style>\n" +
                                "</head>\n" +
                                "<center>\n" +
                                "<body style=\"background-color:white\">\n" +
                                "<h2>" + popupField.getText() + "</h2>\n" +
                                "<table>\n"
                );

                for(int i=0; i < row; i++){
                    myWriter.write("<tr>\n");
                    for(int j=0; j < col; j++){
                        if((col * i) + j + 1 > comicStrip.getChildren().size() - 2) {
                            i = row;
                            j = col;
                        }else{
                            myWriter.write("<td><center><img src=\"" + ((col * i) + j) + ".png\" width=\"500\" height=\"500\"></center></td>\n");
                        }
                    }
                    myWriter.write("</tr>\n");
                }

                myWriter.write(
                        "</table>\n" +
                        "</body>\n" +
                        "</html>\n" +
                        "</center>\n"
                );

                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        new_project.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Warning, starting a new project will delete any unsaved progress\nDo you still wish to continue?");

                Optional<ButtonType> result = alert.showAndWait();
                if (!(result.isPresent() && result.get() == ButtonType.OK)) {
                    return;
                }

                premise[0] = "";

                comicStrip.getChildren().clear();

                comicStrip.getChildren().add(newPanelLeft);
                comicStrip.getChildren().add(newPanelRight);

                newPanelRight.fire();
            }
        });

        comicStrip.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                newPanelRight.setVisible(true);
            }
        });

        comicStrip.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                newPanelRight.setVisible(false);
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Warning, about to delete the current panel.\nDo you still wish to continue?");

                if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, leftCharacter);
                    return;
                }

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    comicStrip.getChildren().remove(comicPanel[0]);

                    deletedPanels.add(comicPanel[0]);

                    Undo undo = new Undo("delete", comicPanel[0]);
                    UndoList.addUndo(undo);

                    hairColorPicker[0].setValue(Color.WHITE);
                    skinColorPicker[0].setValue(Color.WHITE);
                }
            }
        });

        undoButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {


                /*
                Actions that can be undone

                Rearranging panel: Panel, Previous Panel *To Do after fully implemented or on Sunday*

                Adding Speech Bubble: Panel, L/R
                 */

                /*
                Actually Implemented

                Locking Panel: Panel
                Add Background: Panel, Previous Image
                Changing Character Image: Panel, L/R, Previous Image
                Moving Character: Panel, L/R, Previous Location
                Delete Panel: Panel
                Moving Speech Bubble: Panel, L/R, Previous Location
                Flipping Character: Panel, L/R
                Gender Character: Panel, L/R
                Skin Character: Panel, L/R, Previous Colour
                Hair Character: Panel, L/R, Previous Colour
                Adding Text: Panel, T/B
                Add Panel: Panel
                 */

                if(UndoList.size() > 1) {

                    Undo undo = UndoList.getUndo();

                    if (undo.getOperation().matches("delete")) {
                        comicStrip.getChildren().remove(newPanelRight);
                        comicStrip.getChildren().add(undo.getComicPanel().getIndex(), undo.getComicPanel());
                        comicStrip.getChildren().add(newPanelRight);
                    }
                    else if(undo.getOperation().matches("background"))
                    {
                        undo.getComicPanel().setBackgroundString(undo.getValue_1());
                        undo.getComicPanel().unselect();
                    }
                    else if (undo.getOperation().matches("flip")) {

                        if (undo.getValue_1().matches("left"))
                            undo.getComicPanel().getLeftCharacter().flipOrientation();
                        else
                            undo.getComicPanel().getRightCharacter().flipOrientation();

                    } else if (undo.getOperation().matches("gender")){

                        if (undo.getValue_1().matches("left"))
                            undo.getComicPanel().getLeftCharacter().genderSwap();
                        else
                            undo.getComicPanel().getRightCharacter().genderSwap();
                    }
                    else if (undo.getOperation().matches("skin")){

                        skinColorPicker[0].setValue(Color.web(undo.getValue_2()));

                        if (undo.getValue_1().matches("left"))
                            undo.getComicPanel().getLeftCharacter().setSkin(Color.web(undo.getValue_2()));
                        else
                            undo.getComicPanel().getRightCharacter().setSkin(Color.web(undo.getValue_2()));
                    }else if (undo.getOperation().matches("hair")) {

                        hairColorPicker[0].setValue(Color.web(undo.getValue_2()));

                        if (undo.getValue_1().matches("left"))
                            undo.getComicPanel().getLeftCharacter().setHair(Color.web(undo.getValue_2()));
                        else
                            undo.getComicPanel().getRightCharacter().setHair(Color.web(undo.getValue_2()));
                    }else if (undo.getOperation().matches("caption")) {

                        int i=0;
                        while (undo.getValue_2().charAt(i) != '#')
                            i++;

                        if(undo.getValue_1().matches("top")) {
                            undo.getComicPanel().setTopText(undo.getValue_2().substring(0,i), Font.font(undo.getValue_2().substring(i+1), FontWeight.NORMAL, 20));
                            undo.getComicPanel().getTopText().setText(new Text(undo.getValue_2().substring(0,i)));
                            undo.getComicPanel().getTopText().getTextObject().setFont(Font.font(undo.getValue_2().substring(i+1), FontWeight.NORMAL, 20));
                        }else if(undo.getValue_1().matches("bottom")) {
                            undo.getComicPanel().setBottomText(undo.getValue_2().substring(0,i), Font.font(undo.getValue_2().substring(i+1), FontWeight.NORMAL, 20));
                            undo.getComicPanel().getBottomText().setText(new Text(undo.getValue_2().substring(0,i)));
                            undo.getComicPanel().getBottomText().getTextObject().setFont(Font.font(undo.getValue_2().substring(i+1), FontWeight.NORMAL, 20));
                        }

                    }
                    else if(undo.getOperation().matches("bubble"))
                    {
                        if(undo.getValue_1().matches("left"))
                            undo.getComicPanel().setLeftTextBubble((TextBubble) undo.getObj());
                        else
                            undo.getComicPanel().setRightTextBubble((TextBubble) undo.getObj());
                    }

                    else if (undo.getOperation().matches("panel")){
                        comicStrip.getChildren().remove(undo.getComicPanel());
                    }else if (undo.getOperation().matches("lock")){

                        undo.getComicPanel().setLocked(!undo.getComicPanel().getLocked());
                        double imageWidth = backgroundButton.getWidth();
                        double imageHeight = backgroundButton.getHeight();
                        if(undo.getComicPanel().getLocked()) {
                            try {
                                ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/buttons/lock.png")));
                                imageView.setFitWidth(imageWidth-4);
                                imageView.setFitHeight(imageHeight-2);
                                lockButton[0].setGraphic(imageView);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/buttons/unlock.png")));
                                imageView.setFitWidth(imageWidth-4);
                                imageView.setFitHeight(imageHeight-2);
                                lockButton[0].setGraphic(imageView);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }else if (undo.getOperation().matches("character")){

                        if (undo.getValue_1().matches("left")) {
                            try {
                                undo.getComicPanel().getLeftCharacter().setCharacterImageView("src/images/characters/" + undo.getValue_2() + ".png");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            try {
                                undo.getComicPanel().getRightCharacter().setCharacterImageView("src/images/characters/" + undo.getValue_2() + ".png");
                                undo.getComicPanel().getRightCharacter().flipOrientation();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        if(undo.getValue_2().matches("blank")) {
                            undo.getComicPanel().getCharacter(undo.getValue_1()).setStyle("");
                            undo.getComicPanel().setSelectedCharacter(null);
                        }

                    }else if (undo.getOperation().matches("moveCharacter")){

                        int i=0;
                        while (undo.getValue_2().charAt(i) != '#')
                            i++;

                        if (undo.getValue_1().matches("left")){
                            undo.getComicPanel().getLeftCharacter().setTranslateX(Double.parseDouble(undo.getValue_2().substring(0, i)));
                            undo.getComicPanel().getLeftCharacter().setTranslateY(Double.parseDouble(undo.getValue_2().substring(i+1)));
                        }
                        else {
                            undo.getComicPanel().getRightCharacter().setTranslateX(Double.parseDouble(undo.getValue_2().substring(0, i)));
                            undo.getComicPanel().getRightCharacter().setTranslateY(Double.parseDouble(undo.getValue_2().substring(i+1)));
                        }
                    }else if (undo.getOperation().matches("moveBubble")){

                        int i=0;
                        while (undo.getValue_2().charAt(i) != '#')
                            i++;

                        if (undo.getValue_1().matches("left")){
                            undo.getComicPanel().getLeftTextBubble().setTranslateX(Double.parseDouble(undo.getValue_2().substring(0, i)));
                            undo.getComicPanel().getLeftTextBubble().setTranslateY(Double.parseDouble(undo.getValue_2().substring(i+1)));
                        }
                        else {
                            undo.getComicPanel().getRightTextBubble().setTranslateX(Double.parseDouble(undo.getValue_2().substring(0, i)));
                            undo.getComicPanel().getRightTextBubble().setTranslateY(Double.parseDouble(undo.getValue_2().substring(i+1)));
                        }
                    }

                }
                else {
                    hoverTips.lockedTip("Nothing to undo", undoButton);
                }
            }
        });

        newPanelRight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    comicStrip.getChildren().remove(newPanelRight);

                    ComicPanel newComicPanel = new ComicPanel();
                    comicStrip.getChildren().add(newComicPanel);
                    comicStrip.setMargin(newComicPanel, new Insets(20,10,20,10));
                    comicStrip.getChildren().add(newPanelRight);

                    Undo undo = new Undo("panel", newComicPanel);
                    UndoList.addUndo(undo);

                    newComicPanel.setIndex(comicStrip.getChildren().indexOf(newComicPanel));

                    newComicPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            mouseEvent.setDragDetect(true);
                            addPressAndHoldHandler(newComicPanel, Duration.seconds(1),
                                    event -> {

                                            newComicPanel.getScene().setCursor(javafx.scene.Cursor.CLOSED_HAND);

                                            AtomicInteger index = new AtomicInteger(comicStrip.getChildren().indexOf(newComicPanel));
                                            AtomicInteger amount = new AtomicInteger(0);
                                            AtomicInteger scroll = new AtomicInteger(0);
                                            AtomicReference<Double> offset = new AtomicReference<>();
                                            offset.set(0.0);

                                            comicStrip.setOnMouseDragged(dragEvent -> {

                                                double increase = ((double)comicStrip.getChildren().size()/1000);

                                                if(increase > 5)
                                                    increase = 5;

                                                if(dragEvent.getScreenX() > (4*width/5)) {
                                                    scrollPane.setHvalue(scrollPane.getHvalue() + increase);
                                                    if(scrollPane.getHvalue() != 1) {
                                                        newComicPanel.setTranslateX(newComicPanel.getTranslateX() + 9);
                                                        scroll.set(scroll.get() + 9);
                                                    }
                                                }else if(dragEvent.getScreenX() < width/5){
                                                    scrollPane.setHvalue(scrollPane.getHvalue() - increase);
                                                    if(scrollPane.getHvalue() != 0) {
                                                        newComicPanel.setTranslateX(newComicPanel.getTranslateX() - 9);
                                                        scroll.set(scroll.get() - 9);
                                                    }
                                                }else{

                                                    if(amount.get() < 0)
                                                        offset.set(-(height/2.4 + height/9.6));
                                                    else if(amount.get() > 0)
                                                        offset.set((height/2.4 + height/9.6));
                                                    else
                                                        offset.set(0.0);

                                                    newComicPanel.setTranslateX(dragEvent.getScreenX() - mouseEvent.getSceneX() - offset.get());
                                                    newComicPanel.setTranslateY(dragEvent.getScreenY() - mouseEvent.getSceneY());
                                                }

                                                double presX = newComicPanel.getTranslateX();
                                                double presY = newComicPanel.getTranslateY();

                                                if(dragEvent.getScreenX() - mouseEvent.getSceneX() + scroll.get() < ((height/2.4 + height/9.6) * (amount.get() - 1))) {

                                                    amount.getAndDecrement();
                                                    index.getAndDecrement();

                                                    if(index.get() < 1)
                                                        index.set(1);

                                                    comicStrip.getChildren().remove(newComicPanel);
                                                    comicStrip.getChildren().add(index.get(), newComicPanel);
                                                    newComicPanel.setTranslateX(newComicPanel.getTranslateX() + (height/2.4 + height/9.6));

                                                }
                                                else if(dragEvent.getScreenX() - mouseEvent.getSceneX() + scroll.get() > ((height/2.4 + height/9.6) * (amount.get() + 1))) {
                                                    amount.getAndIncrement();
                                                    index.getAndIncrement();

                                                    if(index.get() > comicStrip.getChildren().size()-2)
                                                        index.set(comicStrip.getChildren().size()-2);

                                                    comicStrip.getChildren().remove(newComicPanel);
                                                    comicStrip.getChildren().add(index.get(), newComicPanel);
                                                    newComicPanel.setTranslateX(newComicPanel.getTranslateX() - (height/2.4 + height/9.6));
                                                }

                                                dragEvent.consume();
                                            });

                                            comicStrip.setOnMouseReleased(mouseEvent3 -> {

                                                newComicPanel.getScene().setCursor(javafx.scene.Cursor.DEFAULT);

                                                comicStrip.setMargin(newComicPanel, new Insets(20,10,20,10));

                                                newComicPanel.setTranslateX(0);
                                                newComicPanel.setTranslateY(0);

                                                comicStrip.setOnMouseDragged(dragEvent2 -> {
                                                    dragEvent2.consume();
                                                });

                                                comicStrip.setOnMouseReleased(dragEvent2 -> {
                                                    dragEvent2.consume();
                                                });

                                                mouseEvent3.consume();
                                            });
                                    });

                            if(!comicStrip.getChildren().contains(newComicPanel))
                                comicStrip.getChildren().add(newComicPanel);

                            if(!comicPanel[0].equals(newComicPanel)) {

                                if (comicPanel[0] != null) {
                                    comicPanel[0].setSelectedCharacter(null);
                                    comicPanel[0].unselect();
                                }


                                newComicPanel.setSelectedCharacter(null);
                                newComicPanel.select();
                                comicPanel[0] = newComicPanel;

                            }
                            else{
                                newComicPanel.select();
                            }

                            if(comicPanel[0].getSelectedCharacter() != null) {
                                hairColorPicker[0].setValue(comicPanel[0].getSelectedCharacter().getHair());
                                skinColorPicker[0].setValue(comicPanel[0].getSelectedCharacter().getSkin());
                            }
                            else {
                                hairColorPicker[0].setValue(Color.WHITE);
                                skinColorPicker[0].setValue(Color.WHITE);
                            }

                            if(comicPanel[0].getLocked()) {
                                try {
                                    ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/buttons/lock.png")));

                                    double imageWidth = backgroundButton.getWidth();
                                    double imageHeight = backgroundButton.getHeight();

                                    imageView.setFitWidth(imageWidth-4);
                                    imageView.setFitHeight(imageHeight-4);
                                    lockButton[0].setGraphic(imageView);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/buttons/unlock.png")));

                                    double imageWidth = backgroundButton.getWidth();
                                    double imageHeight = backgroundButton.getHeight();

                                    imageView.setFitWidth(imageWidth-4);
                                    imageView.setFitHeight(imageHeight-4);
                                    lockButton[0].setGraphic(imageView);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }

                            double w = scrollPane.getContent().getBoundsInLocal().getWidth();
                            double x = (newComicPanel.getBoundsInParent().getMaxX() +
                                    newComicPanel.getBoundsInParent().getMinX()) / 2.0;
                            double v = scrollPane.getViewportBounds().getWidth();
                            scrollPane.setHvalue(scrollPane.getHmax() * ((x - 0.5 * v) / (w - v)));
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


        Scene scene = new Scene(mainPane, width, height, false, SceneAntialiasing.DISABLED);
        scene.getStylesheets().add("main/style.css");

        scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();
            if(codeString.matches("Z"))
                undoButton.fire();
        });

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);

        newPanelRight.fire();

        scrollPane.setHvalue(0.5);

        scene.getStylesheets().add("main/style.css");

        primaryStage.show();

    }

    private void addPressAndHoldHandler(javafx.scene.Node node, Duration holdTime,
                                        EventHandler<MouseEvent> handler) {

        class Wrapper<T> { T content ; }
        Wrapper<MouseEvent> eventWrapper = new Wrapper<>();

        PauseTransition holdTimer = new PauseTransition(holdTime);
        holdTimer.setOnFinished(event -> handler.handle(eventWrapper.content));


        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            eventWrapper.content = event ;
            holdTimer.playFromStart();
        });
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> holdTimer.stop());
        node.addEventHandler(MouseEvent.DRAG_DETECTED, event -> holdTimer.stop());
    };

    public static void main(String[] args) {
        launch(args);
    }
}

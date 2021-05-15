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
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.*;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import undo.Undo;
import undo.UndoAction;
import undo.UndoList;
import ux.*;

import javax.imageio.ImageIO;
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

        ComicStripViewer comicStripViewer = new ComicStripViewer(width, height);

        final String[] premise = {""};

        final ComicPanel[] comicPanel = {new ComicPanel()};

        ButtonUI button_ui = new ButtonUI();
        button_ui.addLabelAndItems("File", "New Project", "Save XML", "Save HTML", "Load XML", "Add Background", "Add Character");
        button_ui.addLabelAndItems("Help", "Help");

        HBox menuBox = new HBox(button_ui.getMenuBar());

        ButtonIcon buttonIcon = new ButtonIcon(primaryStage.getHeight());

        final ColorPicker[] skinColorPicker = {new ColorPicker()};
        final ColorPicker[] hairColorPicker = {new ColorPicker()};
        skinColorPicker[0] = ButtonIcon.colorPickerStyling(skinColorPicker[0], primaryStage.getHeight());
        hairColorPicker[0] = ButtonIcon.colorPickerStyling(hairColorPicker[0], primaryStage.getHeight());

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

        button_ui.getMenuItem("Save XML").setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                FileChooser fileChooser = new FileChooser();

                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

                fileChooser.getExtensionFilters().add(extFilter);

                File saveFile = fileChooser.showSaveDialog(null);

                SaveXML saveXML = new SaveXML(comicStripViewer.getComicStrip(), saveFile);
            }
        });

        button_ui.getMenuItem("Add Character").setOnAction(new EventHandler<ActionEvent>() {
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

        button_ui.getMenuItem("Add Background").setOnAction(new EventHandler<ActionEvent>() {
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

        button_ui.getMenuItem("Help").setOnAction(new EventHandler<ActionEvent>() {
            HelpMenuConstructor helpMenuConstructor = new HelpMenuConstructor();
            @Override
            public void handle(ActionEvent event) {
                helpMenuConstructor.show();
            }
        });

        GalleryManager galleryView = new GalleryManager();

        rightCharacter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(!comicStripViewer.getComicStrip().getChildren().contains(comicPanel[0])){
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

                if(!comicStripViewer.getComicStrip().getChildren().contains(comicPanel[0])){
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

            @Override
            public void handle(ActionEvent event) {

                if(!comicStripViewer.getComicStrip().getChildren().contains(comicPanel[0])){
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
                if(!comicStripViewer.getComicStrip().getChildren().contains(comicPanel[0])){
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

                if(comicStripViewer.getComicStrip().getChildren().contains(comicPanel[0])) {
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

                    Undo undo = new Undo("lock", comicPanel[0], "" + height);
                    undo.setObj(lockButton[0]);
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

                if(!comicStripViewer.getComicStrip().getChildren().contains(comicPanel[0])){
                    hoverTips.NoPanelSelectedTip(tipNoPanelSelected, textButton);
                    return;
                }

                if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, textButton);
                    return;
                }

                textModal.addSubmit();
                textModal.addComboBox( "Segoe UI", "Verdana", "Times New Roman", "Arial");
                textModal.addTextField();
                textModal.addBooleanButtons("Top Text", "Bottom Text");
                textModal.addCancel();
                textModal.addDelete();
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
                                undo.setObj(skinColorPicker[0]);
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
                                undo.setObj(hairColorPicker[0]);
                                UndoList.addUndo(undo);
                            }else {
                                hoverTips.NoCharacterSelectedTip(tipNoCharacterSelected, hairColorPicker[0]);
                            }
                        }
                    });
                }
            }
        });

        button_ui.addButtonsToScrollPane(leftCharacter, rightCharacter, flipButton, genderButton, textButton, bubbleButton, backgroundButton);
        button_ui.addOtherToScrollPane(skinColorPicker[0], hairColorPicker[0], undoButton, deleteButton, lockButton[0]);
        FlowPane buttonLayout = button_ui.getFlowPane(width);

        Button newPanelRight = buttonIcon.getButtonIcon("src/images/buttons/plus.png");
        Button newPanelLeft = buttonIcon.getButtonIcon("src/images/buttons/plus.png");
        newPanelRight.setScaleX(0.5);
        newPanelRight.setScaleY(0.5);

        comicStripViewer.getComicStrip().getChildren().add(newPanelRight);
        comicStripViewer.getComicStrip().getChildren().add(newPanelLeft);

        mainPane.addRow(0, menuBox);
        mainPane.addRow(1, buttonLayout);
        mainPane.addRow(2, comicStripViewer);
        mainPane.getStyleClass().add("mainPane");

        comicStripViewer.getStyleClass().add("scrollPane");

        mainPane.setMargin(comicStripViewer, new Insets(width * 0.05, width * 0.01,0,width * 0.01));

        newPanelRight.setVisible(false);
        newPanelLeft.setVisible(false);

        button_ui.getMenuItem("Load XML").setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoadXML loadXML = new LoadXML(comicStripViewer.getComicStrip(), premise[0], newPanelLeft, newPanelRight, width, height);
            }
        });

        button_ui.getMenuItem("Save HTML").setOnAction((ActionEvent event) -> {

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

                    if((Integer.parseInt(htmlRow.getText()) * Integer.parseInt(htmlCol.getText()) < comicStripViewer.getComicStrip().getChildren().size() - 2)){
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

            for (int i = 1; i < comicStripViewer.getComicStrip().getChildren().size() - 1; i++) {
                if (((ComicPanel) comicStripViewer.getComicStrip().getChildren().get(i)).getTopText() != null && ((ComicPanel) comicStripViewer.getComicStrip().getChildren().get(i)).getTopText().getHeight() > topMaxHeight)
                    topMaxHeight = ((ComicPanel) comicStripViewer.getComicStrip().getChildren().get(i)).getTopText().getHeight();

                if (((ComicPanel) comicStripViewer.getComicStrip().getChildren().get(i)).getHeight() > maxHeight)
                    maxHeight = ((ComicPanel) comicStripViewer.getComicStrip().getChildren().get(i)).getHeight();

                if (((ComicPanel) comicStripViewer.getComicStrip().getChildren().get(i)).getWidth() > maxWidth)
                    maxWidth = ((ComicPanel) comicStripViewer.getComicStrip().getChildren().get(i)).getWidth();
            }

            for (int i = 1; i < comicStripViewer.getComicStrip().getChildren().size() - 1; i++) {

                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setTransform(Transform.scale(scaleFactor, scaleFactor, scaleFactor, scaleFactor));

                WritableImage sizer = new WritableImage(saveSize, saveSize);

                Image img = comicStripViewer.getComicStrip().getChildren().get(i).snapshot(snapshotParameters, sizer);

                double whiteSpace = topMaxHeight;

                if (((ComicPanel) comicStripViewer.getComicStrip().getChildren().get(i)).getTopText() != null)
                    whiteSpace = topMaxHeight - ((ComicPanel) comicStripViewer.getComicStrip().getChildren().get(i)).getTopText().getHeight();

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
                        if((col * i) + j + 1 > comicStripViewer.getComicStrip().getChildren().size() - 2) {
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

        button_ui.getMenuItem("New Project").setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Warning, starting a new project will delete any unsaved progress\nDo you still wish to continue?");

                Optional<ButtonType> result = alert.showAndWait();
                if (!(result.isPresent() && result.get() == ButtonType.OK)) {
                    return;
                }

                premise[0] = "";

                comicStripViewer.getComicStrip().getChildren().clear();

                comicStripViewer.getComicStrip().getChildren().add(newPanelLeft);
                comicStripViewer.getComicStrip().getChildren().add(newPanelRight);

                UndoList.clear();

                newPanelRight.fire();
            }
        });

        comicStripViewer.getComicStrip().setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                newPanelRight.setVisible(true);
            }
        });

        comicStripViewer.getComicStrip().setOnMouseExited(new EventHandler<MouseEvent>() {
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
                    comicStripViewer.getComicStrip().getChildren().remove(comicPanel[0]);

                    Undo undo = new Undo("delete", comicPanel[0]);
                    undo.setObj(newPanelRight);
                    UndoList.addUndo(undo);

                    hairColorPicker[0].setValue(Color.WHITE);
                    skinColorPicker[0].setValue(Color.WHITE);
                }
            }
        });

        undoButton.setOnAction(new EventHandler<ActionEvent>() {
            UndoAction undoAction = new UndoAction(comicStripViewer.getComicStrip());
            public void handle(final ActionEvent event) {
                undoAction.undo();
            }
        });

        newPanelRight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    comicStripViewer.getComicStrip().getChildren().remove(newPanelRight);

                    ComicPanel newComicPanel = new ComicPanel();
                    comicStripViewer.getComicStrip().getChildren().add(newComicPanel);
                    comicStripViewer.getComicStrip().setMargin(newComicPanel, new Insets(20,10,20,10));
                    comicStripViewer.getComicStrip().getChildren().add(newPanelRight);

                    Undo undo = new Undo("panel", newComicPanel);
                    UndoList.addUndo(undo);

                    newComicPanel.setIndex(comicStripViewer.getComicStrip().getChildren().indexOf(newComicPanel));

                    newComicPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            mouseEvent.setDragDetect(true);
                            addPressAndHoldHandler(newComicPanel, Duration.seconds(1),
                                    event -> {

                                            newComicPanel.getScene().setCursor(javafx.scene.Cursor.CLOSED_HAND);

                                            AtomicInteger index = new AtomicInteger(comicStripViewer.getComicStrip().getChildren().indexOf(newComicPanel));
                                            AtomicInteger amount = new AtomicInteger(0);
                                            AtomicInteger scroll = new AtomicInteger(0);
                                            AtomicReference<Double> offset = new AtomicReference<>();
                                            offset.set(0.0);

                                            comicStripViewer.getComicStrip().setOnMouseDragged(dragEvent -> {

                                                double increase = ((double)comicStripViewer.getComicStrip().getChildren().size()/1000);

                                                if(increase > 5)
                                                    increase = 5;

                                                if(dragEvent.getScreenX() > (4*width/5)) {
                                                    comicStripViewer.setHvalue(comicStripViewer.getHvalue() + increase);
                                                    if(comicStripViewer.getHvalue() != 1) {
                                                        newComicPanel.setTranslateX(newComicPanel.getTranslateX() + 9);
                                                        scroll.set(scroll.get() + 9);
                                                    }
                                                }else if(dragEvent.getScreenX() < width/5){
                                                    comicStripViewer.setHvalue(comicStripViewer.getHvalue() - increase);
                                                    if(comicStripViewer.getHvalue() != 0) {
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

                                                    comicStripViewer.getComicStrip().getChildren().remove(newComicPanel);
                                                    comicStripViewer.getComicStrip().getChildren().add(index.get(), newComicPanel);
                                                    newComicPanel.setTranslateX(newComicPanel.getTranslateX() + (height/2.4 + height/9.6));

                                                }
                                                else if(dragEvent.getScreenX() - mouseEvent.getSceneX() + scroll.get() > ((height/2.4 + height/9.6) * (amount.get() + 1))) {
                                                    amount.getAndIncrement();
                                                    index.getAndIncrement();

                                                    if(index.get() > comicStripViewer.getComicStrip().getChildren().size()-2)
                                                        index.set(comicStripViewer.getComicStrip().getChildren().size()-2);

                                                    comicStripViewer.getComicStrip().getChildren().remove(newComicPanel);
                                                    comicStripViewer.getComicStrip().getChildren().add(index.get(), newComicPanel);
                                                    newComicPanel.setTranslateX(newComicPanel.getTranslateX() - (height/2.4 + height/9.6));
                                                }

                                                dragEvent.consume();
                                            });

                                            comicStripViewer.getComicStrip().setOnMouseReleased(mouseEvent3 -> {

                                                newComicPanel.getScene().setCursor(javafx.scene.Cursor.DEFAULT);

                                                comicStripViewer.getComicStrip().setMargin(newComicPanel, new Insets(20,10,20,10));

                                                newComicPanel.setTranslateX(0);
                                                newComicPanel.setTranslateY(0);

                                                comicStripViewer.getComicStrip().setOnMouseDragged(dragEvent2 -> {
                                                    dragEvent2.consume();
                                                });

                                                comicStripViewer.getComicStrip().setOnMouseReleased(dragEvent2 -> {
                                                    dragEvent2.consume();
                                                });

                                                mouseEvent3.consume();
                                            });
                                    });

                            if(!comicStripViewer.getComicStrip().getChildren().contains(newComicPanel))
                                comicStripViewer.getComicStrip().getChildren().add(newComicPanel);

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

                            double w = comicStripViewer.getContent().getBoundsInLocal().getWidth();
                            double x = (newComicPanel.getBoundsInParent().getMaxX() +
                                    newComicPanel.getBoundsInParent().getMinX()) / 2.0;
                            double v = comicStripViewer.getViewportBounds().getWidth();
                            comicStripViewer.setHvalue(comicStripViewer.getHmax() * ((x - 0.5 * v) / (w - v)));
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

        comicStripViewer.setHvalue(0.5);

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
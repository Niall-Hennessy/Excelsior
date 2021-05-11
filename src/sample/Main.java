package sample;

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

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
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

        List<String> undoList = new Stack<>();
        List<ComicPanel> deletedPanels = new Stack<>();

        final String[] character = new String[1];

        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: #FBFFCC");

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

        Menu edit = new Menu("Edit");
        menuBar.getMenus().add(edit);
        Menu view = new Menu("View");
        menuBar.getMenus().add(view);
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
        buttonLayout.setStyle("-fx-background-color: #142042");

        menuBox.setMaxHeight(buttonLayout.getHeight()/2);

//      Lower Panel Buttons and and placement
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
                try {

                    FileChooser fileChooser = new FileChooser();

                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
                    fileChooser.getExtensionFilters().add(extFilter);

                    File saveFile = fileChooser.showSaveDialog(null);

                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.newDocument();

                    Element comic = doc.createElement("comic");
                    doc.appendChild(comic);

                    Element panels = doc.createElement("panels");
                    comic.appendChild(panels);

                    for(int i=1; i < comicStrip.getChildren().size() - 1; i++) {

                        ComicPanel toParse = ((ComicPanel) comicStrip.getChildren().get(i));

                        Element panel = doc.createElement("panel");
                        panels.appendChild(panel);

                        Element above = doc.createElement("above");
                        Attr fontAbove = doc.createAttribute("font");
                        Element left = doc.createElement("left");
                        Element right = doc.createElement("right");
                        Element below = doc.createElement("below");
                        Attr fontBelow = doc.createAttribute("font");
                        Element background = doc.createElement("background");
                        Attr locked = doc.createAttribute("locked");
                        locked.setValue(toParse.getLocked().toString());

                        if(toParse.getTopText() != null) {
                            above.appendChild(doc.createTextNode(toParse.getTopText().getText()));
                            fontAbove.setValue(toParse.getTopText().getFont());
                            above.setAttributeNode(fontAbove);
                        }

                        if(toParse.getBottomText() != null) {
                            below.appendChild(doc.createTextNode(toParse.getBottomText().getText()));
                            fontBelow.setValue(toParse.getBottomText().getFont());
                            below.setAttributeNode(fontBelow);
                        }

                        if(toParse.getBackgroundString() != null);
                            background.appendChild(doc.createTextNode(toParse.getBackgroundString()));


                        panel.appendChild(above);
                        panel.appendChild(left);
                        panel.appendChild(right);
                        panel.appendChild(below);
                        panel.appendChild(background);
                        panel.setAttributeNode(locked);

                        if(toParse.getLeftCharacter().getImageName() != null && !toParse.getLeftCharacter().getImageName().matches("blank")){
                            Element figure = doc.createElement("figure");
                            left.appendChild(figure);

                            Element name = doc.createElement("name");
                            Element appearance = doc.createElement("appearance");
                            Element skin = doc.createElement("skin");
                            Element hair = doc.createElement("hair");
                            Element lips = doc.createElement("lips");
                            Element pose = doc.createElement("pose");
                            Element facing = doc.createElement("facing");
                            Element xPosition = doc.createElement("xPosition");
                            Element yPosition = doc.createElement("yPosition");

                            figure.appendChild(name);

                            if(toParse.getLeftCharacter().isFemale())
                                appearance.appendChild(doc.createTextNode("female"));
                            else
                                appearance.appendChild(doc.createTextNode("male"));

                            figure.appendChild(appearance);

                            skin.appendChild(doc.createTextNode(toParse.getLeftCharacter().getSkin().toString()));
                            figure.appendChild(skin);

                            hair.appendChild(doc.createTextNode(toParse.getLeftCharacter().getHair().toString()));
                            figure.appendChild(hair);

                            figure.appendChild(lips);

                            pose.appendChild(doc.createTextNode(toParse.getLeftCharacter().getImageName()));
                            figure.appendChild(pose);

                            if(toParse.getLeftCharacter().characterImageView.getRotate() == 180)
                                facing.appendChild(doc.createTextNode("left"));
                            else
                                facing.appendChild(doc.createTextNode("right"));

                            figure.appendChild(facing);

                            xPosition.appendChild(doc.createTextNode(String.valueOf(toParse.getLeftCharacter().getTranslateX())));
                            yPosition.appendChild(doc.createTextNode(String.valueOf(toParse.getLeftCharacter().getTranslateY())));

                            figure.appendChild(xPosition);
                            figure.appendChild(yPosition);
                        }

                        if(toParse.getRightCharacter().getImageName() != null && !toParse.getRightCharacter().getImageName().matches("blank")){
                            Element figure = doc.createElement("figure");
                            right.appendChild(figure);

                            Element name = doc.createElement("name");
                            Element appearance = doc.createElement("appearance");
                            Element skin = doc.createElement("skin");
                            Element hair = doc.createElement("hair");
                            Element lips = doc.createElement("lips");
                            Element pose = doc.createElement("pose");
                            Element facing = doc.createElement("facing");
                            Element xPosition = doc.createElement("xPosition");
                            Element yPosition = doc.createElement("yPosition");

                            figure.appendChild(name);

                            if(toParse.getRightCharacter().isFemale())
                                appearance.appendChild(doc.createTextNode("female"));
                            else
                                appearance.appendChild(doc.createTextNode("male"));

                            figure.appendChild(appearance);

                            skin.appendChild(doc.createTextNode(toParse.getRightCharacter().getSkin().toString()));
                            figure.appendChild(skin);

                            hair.appendChild(doc.createTextNode(toParse.getRightCharacter().getHair().toString()));
                            figure.appendChild(hair);

                            figure.appendChild(lips);

                            pose.appendChild(doc.createTextNode(toParse.getRightCharacter().getImageName()));
                            figure.appendChild(pose);

                            if(toParse.getRightCharacter().characterImageView.getRotate() == 180)
                                facing.appendChild(doc.createTextNode("left"));
                            else
                                facing.appendChild(doc.createTextNode("right"));

                            figure.appendChild(facing);

                            xPosition.appendChild(doc.createTextNode(String.valueOf(toParse.getRightCharacter().getTranslateX())));
                            yPosition.appendChild(doc.createTextNode(String.valueOf(toParse.getRightCharacter().getTranslateY())));

                            figure.appendChild(xPosition);
                            figure.appendChild(yPosition);
                        }

                        if(toParse.getLeftTextBubble() != null){
                            Element balloonXPosition = doc.createElement("xPosition");
                            Element balloonYPosition = doc.createElement("yPosition");
                            Element balloon = doc.createElement("balloon");
                            left.appendChild(balloon);

                            Attr attr = doc.createAttribute("status");
                            attr.setValue(toParse.getLeftTextBubble().getStatus());
                            balloon.setAttributeNode(attr);

                            Element content = doc.createElement("content");
                            content.appendChild(doc.createTextNode(toParse.getLeftTextBubble().getText().getText()));
                            Attr bold = doc.createAttribute("bold");
                            Attr italic = doc.createAttribute("italic");

                            if(toParse.getLeftTextBubble().getText().getFont().toString().contains("Bold"))
                                bold.setValue("true");
                            else
                                bold.setValue("false");

                            if(toParse.getLeftTextBubble().getText().getFont().toString().contains("Italic"))
                                italic.setValue("true");
                            else
                                italic.setValue("false");

                            content.setAttributeNode(bold);
                            content.setAttributeNode(italic);

                            balloonXPosition.appendChild(doc.createTextNode(String.valueOf(toParse.getLeftTextBubble().getTranslateX())));
                            balloonYPosition.appendChild(doc.createTextNode(String.valueOf(toParse.getLeftTextBubble().getTranslateY())));

                            balloon.appendChild(content);
                            balloon.appendChild(balloonXPosition);
                            balloon.appendChild(balloonYPosition);
                        }

                        if(toParse.getRightTextBubble() != null){
                            Element balloonXPosition = doc.createElement("xPosition");
                            Element balloonYPosition = doc.createElement("yPosition");
                            Element balloon = doc.createElement("balloon");
                            right.appendChild(balloon);

                            Attr attr = doc.createAttribute("status");
                            attr.setValue(toParse.getRightTextBubble().getStatus());
                            balloon.setAttributeNode(attr);

                            Element content = doc.createElement("content");
                            content.appendChild(doc.createTextNode(toParse.getRightTextBubble().getText().getText()));
                            Attr bold = doc.createAttribute("bold");
                            Attr italic = doc.createAttribute("italic");

                            if(toParse.getRightTextBubble().getText().getFont().toString().contains("Bold"))
                                bold.setValue("true");
                            else
                                bold.setValue("false");

                            if(toParse.getRightTextBubble().getText().getFont().toString().contains("Italic"))
                                italic.setValue("true");
                            else
                                italic.setValue("false");

                            content.setAttributeNode(bold);
                            content.setAttributeNode(italic);

                            balloonXPosition.appendChild(doc.createTextNode(String.valueOf(toParse.getRightTextBubble().getTranslateX())));
                            balloonYPosition.appendChild(doc.createTextNode(String.valueOf(toParse.getRightTextBubble().getTranslateY())));

                            balloon.appendChild(content);
                            balloon.appendChild(balloonXPosition);
                            balloon.appendChild(balloonYPosition);
                        }

                    }

                    // write the content into xml file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                    DOMSource source = new DOMSource(doc);

                    if(saveFile != null) {
                        StreamResult result = new StreamResult(saveFile);
                        transformer.transform(source, result);
                    }
                    // Output to console for testing
                    StreamResult consoleResult = new StreamResult(System.out);
                    transformer.transform(source, consoleResult);
                } catch (TransformerException | ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

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

                if(comicPanel[0].getRightCharacter().getImageName() == null)
                    undoList.add("character|" + comicStrip.getChildren().indexOf(comicPanel[0]) + "|right|blank|");
                else
                    undoList.add("character|" + comicStrip.getChildren().indexOf(comicPanel[0]) + "|right|" + comicPanel[0].getRightCharacter().getImageName().toString() + "|");

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

                if(comicPanel[0].getRightCharacter().getImageName() == null)
                    undoList.add("character|" + comicStrip.getChildren().indexOf(comicPanel[0]) + "|left|blank|");
                else
                    undoList.add("character|" + comicStrip.getChildren().indexOf(comicPanel[0]) + "|left|" + comicPanel[0].getRightCharacter().getImageName() + "|");

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
                    undoList.add("flip|" + comicStrip.getChildren().indexOf(comicPanel[0]) + "|" + comicPanel[0].getLeftRight() + "||");
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
                    undoList.add("gender|" + comicStrip.getChildren().indexOf(comicPanel[0]) + "|" + comicPanel[0].getLeftRight() + "||");
                }else if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, genderButton);
                    return;
                }
                else {
                    hoverTips.NoPanelSelectedTip(tipNoCharacterSelected, genderButton);
                }
            }
        });

        //pop up for when they hit the bubble button for speech
        bubbleButton.setOnAction(new EventHandler<ActionEvent>() {

            final Stage addBubble = new Stage(StageStyle.UNDECORATED);

            Pane bubbleDisplay = new Pane();
            ImageView bubbleImageView = new ImageView();
            String bubbleName;

            private double xOffset = 0;
            private double yOffset = 0;

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

                Button submit = new Button("Submit");
                submit.getStyleClass().add("submit");
                Button escape = new Button("X");
                escape.getStyleClass().add("escape");
                Button cancel = new Button("Cancel");
                cancel.getStyleClass().add("cancel");
                Button delete = new Button("Delete");
                delete.getStyleClass().add("cancel");
                Button italic = new Button("Italic");
                italic.getStyleClass().add("italic");
                Button bold = new Button("Bold");
                bold.getStyleClass().add("bold");
                Button fonts = new Button("Font");
                fonts.getStyleClass().add("fonts");

                if(bubbleDisplay.getChildren().size() == 0)
                    bubbleDisplay.getChildren().add(bubbleImageView);

                bubbleDisplay.setMinHeight(bubbleImageView.getFitHeight()+100);

                HBox bubbleGallery = new HBox();
                bubbleGallery.getStyleClass().add("bubbles");

                File folder = new File("src/images/bubbles");
                File[] listOfFiles = folder.listFiles();

                TextField textfield = new TextField();

                final boolean[] isBold = {false};
                final boolean[] isItalic = {false};

                if(comicPanel[0].getLeftTextBubble() != null) {
                    if(comicPanel[0].getLeftTextBubble().getText().getFont().toString().contains("Bold"))
                        isBold[0] = true;
                    if(comicPanel[0].getLeftTextBubble().getText().getFont().toString().contains("Italic"))
                        isItalic[0] = true;
                }

                if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getLeftCharacter()) && comicPanel[0].getLeftTextBubble() != null) {

                    if(comicPanel[0].getLeftTextBubble() != null) {
                        if(comicPanel[0].getLeftTextBubble().getText().getFont().toString().contains("Bold"))
                            isBold[0] = true;
                        if(comicPanel[0].getLeftTextBubble().getText().getFont().toString().contains("Italic"))
                            isItalic[0] = true;
                    }

                    textfield.setText(comicPanel[0].getLeftTextBubble().getText().getText().replaceAll("\n", " "));
                }
                else if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getRightCharacter()) && comicPanel[0].getRightTextBubble() != null) {

                    if(comicPanel[0].getRightTextBubble() != null) {
                        if(comicPanel[0].getRightTextBubble().getText().getFont().toString().contains("Bold"))
                            isBold[0] = true;
                        if(comicPanel[0].getRightTextBubble().getText().getFont().toString().contains("Italic"))
                            isItalic[0] = true;
                    }

                    textfield.setText(comicPanel[0].getRightTextBubble().getText().getText().replaceAll("\n", " "));
                }

                if(isBold[0] && isItalic[0])
                    textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.ITALIC, textfield.getFont().getSize()));
                else if(!isBold[0] && isItalic[0])
                    textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.ITALIC, textfield.getFont().getSize()));
                else if(isBold[0] && !isItalic[0])
                    textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, textfield.getFont().getSize()));
                else
                    textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.REGULAR, textfield.getFont().getSize()));



                HBox textbox = new HBox(textfield);
                textfield.setPrefWidth(800);
                textfield.setPrefHeight(50);

                for (final File file : listOfFiles)
                {
                    ImageView imageView;
                    imageView = createImageView(file);
                    bubbleGallery.getChildren().add(imageView);

                }

                GridPane bubbleGrid = new GridPane();
                bubbleGrid.setPadding(new Insets(10, 10, 10, 10));
                bubbleGrid.setVgap(5);
                bubbleGrid.setHgap(5);
                bubbleGrid.getStyleClass().add("bubbleGrid");

                // page.add(Node, colIndex, rowIndex, colSpan, rowSpan):
                bubbleGrid.add(escape, 4, 1, 1, 1);
                bubbleGrid.add(bubbleGallery, 0, 2, 3, 3);
                bubbleGrid.setMargin(bubbleGallery, new Insets (10, 10, 10, 30));//its 30 to balance out the x buttons margins and get the otherside right
                bubbleGrid.add(bubbleDisplay, 0, 5, 3, 3);
                bubbleGrid.setMargin(bubbleDisplay, new Insets (10, 10, 10, 30));
                bubbleGrid.add(italic, 0,8, 1, 1 );
                bubbleGrid.setMargin(italic, new Insets (5, 2, 2, 30));
                bubbleGrid.add(bold, 1, 8, 1, 1);
                bubbleGrid.setMargin(bold, new Insets (5, 2, 2, 0));
                bubbleGrid.add(textbox, 0, 9, 3, 1);
                bubbleGrid.setMargin(textbox, new Insets (10, 10, 1, 30));
                bubbleGrid.add(submit, 0, 12, 1, 1);
                bubbleGrid.setMargin(submit, new Insets (2, 2, 2, 30));
                bubbleGrid.add(cancel,1, 12, 1, 1);
                bubbleGrid.setMargin(cancel, new Insets (2, 2, 2, 2));
                bubbleGrid.add(delete,2, 12, 1, 1);
                bubbleGrid.setMargin(delete, new Insets (2, 11, 2, 2));

                bold.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        isBold[0] = !isBold[0];

                        if(isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(!isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(isBold[0] && !isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, textfield.getFont().getSize()));
                        else
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.REGULAR, textfield.getFont().getSize()));
                    }
                });

                italic.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        isItalic[0] = !isItalic[0];

                        if(isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(!isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(isBold[0] && !isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, textfield.getFont().getSize()));
                        else
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.REGULAR, textfield.getFont().getSize()));
                    }
                });

                cancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        bubbleDisplay.getChildren().remove(bubbleImageView);
                        addBubble.close();
                    }
                });

                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        comicPanel[0].removeBubble();
                        addBubble.close();
                    }
                });

                escape.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        bubbleDisplay.getChildren().remove(bubbleImageView);
                        addBubble.close();
                    }
                });

                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if(textfield.getText().replaceAll("\\s", "").matches(""))
                            return;

                        if(textfield.getText().matches("") || ((ImageView)bubbleDisplay.getChildren().get(0)).getImage() == null)
                            return;

                        if(isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(!isBold[0] && isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.ITALIC, textfield.getFont().getSize()));
                        else if(isBold[0] && !isItalic[0])
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, textfield.getFont().getSize()));
                        else
                            textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.REGULAR, textfield.getFont().getSize()));

                        if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getLeftCharacter()))
                            comicPanel[0].setLeftBubble(((ImageView)bubbleDisplay.getChildren().get(0)).getImage(), textfield.getText(), textfield.getFont(), bubbleName);
                        else if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getRightCharacter()))
                            comicPanel[0].setRightBubble(((ImageView)bubbleDisplay.getChildren().get(0)).getImage(), textfield.getText(), textfield.getFont(), bubbleName);

                        bubbleDisplay.getChildren().remove(bubbleImageView);
                        addBubble.close();
                    }
                });

                Scene scene = new Scene(bubbleGrid);
                addBubble.setScene(scene);

                scene.getStylesheets().add("sample/style.css");

                scene.setOnMousePressed(pressEvent -> {
                    scene.setOnMouseDragged(dragEvent -> {
                        addBubble.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                        addBubble.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                    });
                });

                addBubble.show();
            }

            private ImageView createImageView(final File imageFile) {

                ImageView imageView = null;

                try {
                    final Image image = new Image(new FileInputStream(imageFile), 150, 150, true,
                            true);

                    int width = (int) image.getWidth();
                    int height = (int) image.getHeight();

                    WritableImage writableImage = new WritableImage(width, height);

                    PixelReader pixelReader = image.getPixelReader();
                    PixelWriter pixelWriter = writableImage.getPixelWriter();

                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            Color color = pixelReader.getColor(x, y);

                            if (color.equals(Color.rgb(255, 254, 255))) {
                                pixelWriter.setColor(x, y, Color.rgb(0, 0, 0, 0));
                            }
                            else
                                pixelWriter.setColor(x, y, color);
                        }
                    }

                    imageView = new ImageView(writableImage);
                    imageView.setPickOnBounds(true);
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent mouseEvent) {

                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                                if (mouseEvent.getClickCount() == 1) {
                                    ((ImageView)bubbleDisplay.getChildren().get(0)).setImage(writableImage);
                                    bubbleName = imageFile.getPath().substring(19);
                                }
                            }
                        }
                    });
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                return imageView;
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

                String path = "src/images/backgrounds";
                galleryView.setComicPanel(comicPanel);
                galleryView.setHeight(height);
                galleryView.setBackground(path);
            }
        });

        lockButton[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(comicStrip.getChildren().contains(comicPanel[0])) {
                    comicPanel[0].setLocked(!comicPanel[0].getLocked());


                    if(comicPanel[0].getLocked()) {
                        try {
                            ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/buttons/lock.png")));
                            imageView.setFitWidth(height*0.09);
                            imageView.setFitHeight(height*0.09);
                            lockButton[0].setGraphic(imageView);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/buttons/unlock.png")));
                            imageView.setFitWidth(height*0.09);
                            imageView.setFitHeight(height*0.09);
                            lockButton[0].setGraphic(imageView);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                }
                else {
                    hoverTips.NoPanelSelectedTip(tipNoCharacterSelected, lockButton[0]);
                }
            }
        });

        textButton.setOnAction(new EventHandler<ActionEvent>() {
            final Stage addText = new Stage(StageStyle.UNDECORATED);

            @Override
            public void handle(ActionEvent event) {

                if(!comicStrip.getChildren().contains(comicPanel[0])){
                    hoverTips.NoPanelSelectedTip(tipNoPanelSelected, textButton);
                    return;
                }

                if(comicPanel[0].getLocked()){
                    hoverTips.lockedTip(tipLocked, textButton);
                    return;
                }

                Button submit = new Button("Apply");
                submit.getStyleClass().add("submit");
                Button escape = new Button("X");
                escape.getStyleClass().add("escape");
                Button cancel = new Button("Cancel");
                cancel.getStyleClass().add("cancel");
                Button delete = new Button("Delete");
                delete.getStyleClass().add("cancel");
                Button topText = new Button("Top Text");
                topText.getStyleClass().add("bold");
                Button bottomText = new Button("Bottom Text");
                bottomText.getStyleClass().add("bold");
                Label label = new Label("Fonts: ");
                ComboBox combo_box = new ComboBox();
                combo_box.getStyleClass().add("bold");
                combo_box.getItems().addAll("Segoe UI", "Verdana", "Times New Roman", "Arial");
                combo_box.getSelectionModel().selectFirst();


                TextField captionTextfield = new TextField();

                if(comicPanel[0].getTopText() != null) {
                    captionTextfield.setFont(comicPanel[0].getTopText().getTextObject().getFont());
                    combo_box.setValue(comicPanel[0].getTopText().getTextObject().getFont().getName());
                }
                else
                    captionTextfield.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 20));

                captionTextfield.getStyleClass().add("capTextField");
                HBox topOrBot = new HBox(captionTextfield);
                captionTextfield.setPrefWidth(400);
                captionTextfield.setPrefHeight(25);



                GridPane layoutGrid = new GridPane();
                layoutGrid.getStyleClass().add("layoutGrid");
                layoutGrid.setPadding(new Insets(10, 10, 10, 10));
                layoutGrid.setVgap(5);
                layoutGrid.setHgap(5);

                // page.add(Node, colIndex, rowIndex, colSpan, rowSpan):
                layoutGrid.add(escape, 21, 0, 1, 1);
                layoutGrid.setMargin(escape, new Insets(5,0,5,0));
                layoutGrid.add(topText, 0, 1, 1, 1);
                layoutGrid.setMargin(topText, new Insets (5, 0, 5, 25));
                layoutGrid.add(bottomText, 1, 1, 1, 1);
                layoutGrid.setMargin(bottomText, new Insets (5, 0, 5, 0));
                layoutGrid.add(topOrBot, 0, 2, 20, 1);
                layoutGrid.setMargin(topOrBot, new Insets (5, 0, 5, 25));
                layoutGrid.add(label,0, 3, 1, 1);
                layoutGrid.setMargin(label, new Insets (5, 0, 5, 25));
                layoutGrid.add(combo_box,1, 3, 1, 1);
                layoutGrid.setMargin(combo_box, new Insets (5, 0, 5, 0));
                layoutGrid.add(submit, 0, 4, 1, 1);
                layoutGrid.setMargin(submit, new Insets (5, 0, 5, 25));
                layoutGrid.add(cancel,1, 4, 1, 1);
                layoutGrid.setMargin(cancel, new Insets (5, 0, 5, 0));
                layoutGrid.add(delete,2, 4, 1, 1);
                layoutGrid.setMargin(delete, new Insets (5, 0, 5, 0));


                final boolean[] top = {true};
                final boolean[] bottom = {false};

                topText.setStyle("-fx-background-color: #C089D7");
                bottomText.setStyle("-fx-background-color: #E5A6FF");

                if(comicPanel[0].getTopText() != null)
                    captionTextfield.setText(comicPanel[0].getTopText().getText());

                topText.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        captionTextfield.setText("");
                        bottom[0] = false;
                        top[0] = true;

                        topText.setStyle("-fx-background-color: #C089D7");
                        bottomText.setStyle("-fx-background-color: #E5A6FF");

                        if(comicPanel[0].getTopText() != null) {
                            captionTextfield.setText(comicPanel[0].getTopText().getText());
                            captionTextfield.setFont(comicPanel[0].getTopText().getTextObject().getFont());
                            combo_box.setValue(comicPanel[0].getTopText().getTextObject().getFont().getName());
                        }
                        else {
                            captionTextfield.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 20));
                            combo_box.setValue("Segoe UI");
                        }
                    }
                });

                bottomText.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        captionTextfield.setText("");
                        bottom[0] = true;
                        top[0] = false;

                        bottomText.setStyle("-fx-background-color: #C089D7");
                        topText.setStyle("-fx-background-color: #E5A6FF");

                        if(comicPanel[0].getBottomText() != null) {
                            captionTextfield.setText(comicPanel[0].getBottomText().getText());
                            captionTextfield.setFont(comicPanel[0].getBottomText().getTextObject().getFont());
                            combo_box.setValue(comicPanel[0].getBottomText().getTextObject().getFont().getName());
                        }
                        else {
                            captionTextfield.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 20));
                            combo_box.setValue("Segoe UI");
                        }
                    }
                });

                escape.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        bottom[0] = false;
                        top[0] = false;
                        addText.close();
                    }
                });

                cancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        bottom[0] = false;
                        top[0] = false;
                        addText.close();
                    }
                });

                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        Font font = Font.font((String)combo_box.getSelectionModel().getSelectedItem(), FontWeight.NORMAL, 20);
                        captionTextfield.setFont(Font.font((String)combo_box.getSelectionModel().getSelectedItem(), FontWeight.NORMAL, 20));

                        if(top[0]){
                            comicPanel[0].setTopText(captionTextfield.getText(), font);
                        }
                        else if(bottom[0]){
                            comicPanel[0].setBottomText(captionTextfield.getText(), font);
                        }
                    }
                });

                delete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        comicPanel[0].removeText(top, bottom);
                        bottom[0] = false;
                        top[0] = false;
                        addText.close();
                    }
                });

                Scene scene = new Scene(layoutGrid);
                scene.getStylesheets().add("sample/style.css");
                addText.setScene(scene);

                addText.setX(width/2 -200);
                addText.setY(height/2);



                addText.show();

                scene.setOnMousePressed(pressEvent -> {
                    scene.setOnMouseDragged(dragEvent -> {
                        addText.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                        addText.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                    });
                });
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
                                undoList.add("skin|" + comicStrip.getChildren().indexOf(comicPanel[0]) + "|" + comicPanel[0].getLeftRight() + "|" + current.toString() + "|");
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
                                undoList.add("hair|" + comicStrip.getChildren().indexOf(comicPanel[0]) + "|" + comicPanel[0].getLeftRight() + "|" + current.toString() + "|");
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
        comicStrip.setPrefWidth(width - 40);
        comicStrip.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 3px");


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(comicStrip);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
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
        mainPane.setStyle("-fx-background-color: #1A2A55");

        mainPane.setMargin(scrollPane, new Insets(width * 0.05, width * 0.01,0,width * 0.01));

        newPanelRight.setVisible(false);
        newPanelLeft.setVisible(false);

        load_xml.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                JFrame frame = null;
                
                try {

                    FileChooser fileChooser = new FileChooser();

                    File inputFile = fileChooser.showOpenDialog(null);

                    if(inputFile != null) {

                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(inputFile);
                        doc.getDocumentElement().normalize();

                        if(doc.getElementsByTagName("premise").item(0) != null)
                            premise[0] = doc.getElementsByTagName("premise").item(0).getTextContent();
                        else
                            premise[0] = "";

                        NodeList nList = doc.getElementsByTagName("panel");
                        Element figures = (Element) doc.getElementsByTagName("figures").item(0);
                        NodeList figureList;

                        if (figures != null)
                            figureList = figures.getElementsByTagName("figure");
                        else {
                            figureList = new NodeList() {
                                @Override
                                public Node item(int index) {
                                    return null;
                                }

                                @Override
                                public int getLength() {
                                    return 0;
                                }
                            };
                        }

                        comicStrip.getChildren().clear();

                        comicStrip.getChildren().add(newPanelLeft);
                        comicStrip.getChildren().add(newPanelRight);

                        int i;
                        HashMap<String, Element> characterHashMap = new HashMap<>();

                        for (i = 0; i < figureList.getLength(); i++) {
                            Element figure = (Element) figureList.item(i);

                            characterHashMap.put(figure.getElementsByTagName("name").item(0).getTextContent(), figure);
                        }

                        JProgressBar progressBar;
                        

                        frame = new JFrame("Loading...");
                        frame.setUndecorated(true);

                        JPanel panel = new JPanel();

                        progressBar = new JProgressBar();
                        progressBar.setValue(0);
                        progressBar.setStringPainted(true);

                        progressBar.setSize(150, 30);
                        panel.setSize(150, 30);
                        frame.setSize(150, 30);

                        panel.add(progressBar);


                        frame.setLocation((int) width / 2 - frame.getWidth() / 2, (int) height / 2 - frame.getHeight() / 2);

                        frame.add(panel);

                        frame.setVisible(true);

                        for (int temp = 0; temp < nList.getLength(); temp++) {
                            org.w3c.dom.Node nNode = nList.item(temp);

                            newPanelRight.fire();

                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) nNode;

                                Font fontTextCaption = Font.font("ARIAL", FontWeight.NORMAL, 20);

                                ComicPanel panelRef = ((ComicPanel) comicStrip.getChildren().get(temp + 1));

                                if (eElement.hasAttribute("locked"))
                                    if (eElement.getAttribute("locked").matches("true"))
                                        panelRef.setLocked(true);

                                Node currentNode = eElement.getFirstChild();
                                while (currentNode != null) {

                                    if (currentNode.getNodeName().matches("background")) {
                                        panelRef.setBackgroundString(currentNode.getTextContent());
                                        panelRef.setStyle("-fx-background-image: url('" + currentNode.getTextContent().replace('\\', '/') + "'); " +
                                                "-fx-background-position: center center; " +
                                                "-fx-background-repeat: stretch; " +
                                                "-fx-background-size: " + (height / 2.4 + height / 9.6) + " " + height / 2.4 + ";" +
                                                "-fx-border-color: black; " +
                                                "-fx-border-width: 5");
                                    }

                                    if (currentNode.getNodeName().matches("above")) {

                                        Element eCurrent = (Element) currentNode;

                                        if (eCurrent.hasAttribute("font"))
                                            fontTextCaption = Font.font(eCurrent.getAttribute("font"), FontWeight.NORMAL, 20);

                                        panelRef.setTopText(currentNode.getTextContent(), fontTextCaption);

                                        fontTextCaption = Font.font("ARIAL", FontWeight.NORMAL, 20);
                                    }

                                    if (currentNode.getNodeName().matches("below")) {

                                        Element eCurrent = (Element) currentNode;

                                        if (eCurrent.hasAttribute("font"))
                                            fontTextCaption = Font.font(eCurrent.getAttribute("font"), FontWeight.NORMAL, 20);

                                        panelRef.setBottomText(currentNode.getTextContent(), fontTextCaption);

                                        fontTextCaption = Font.font("ARIAL", FontWeight.NORMAL, 20);
                                    }

                                    if (currentNode.getNodeName().matches("locked"))
                                        if (currentNode.getTextContent().matches("locked"))
                                            panelRef.setLocked(true);

                                    if (currentNode.getNodeName().matches("left")) {
                                        Node leftNode = currentNode.getFirstChild();

                                        while (leftNode != null) {

                                            if (leftNode.getNodeName().matches("figure")) {
                                                Node figureNode = leftNode.getFirstChild();
                                                Element nameElement = null;

                                                if (!leftNode.getTextContent().matches(""))
                                                    panelRef.setLeftCharacter("src/images/characters/neutral.png");

                                                while (figureNode != null) {

                                                    if (figureNode.getTextContent().matches("")) {
                                                        figureNode = figureNode.getNextSibling();
                                                        continue;
                                                    } else if (figureNode.getNodeName().matches("name")) {
                                                        nameElement = characterHashMap.get(figureNode.getTextContent());

                                                        if(nameElement.getElementsByTagName("pose").item(0) != null)
                                                            panelRef.setLeftCharacter("src/images/characters/" + nameElement.getElementsByTagName("pose").item(0).getTextContent() + ".png");

                                                        if(nameElement.getElementsByTagName("appearance").item(0) != null)
                                                            if (nameElement.getElementsByTagName("appearance").item(0).getTextContent().matches("male"))
                                                                panelRef.getLeftCharacter().setFemale(false);

                                                        if(nameElement.getElementsByTagName("skin").item(0) != null)
                                                            if (!nameElement.getElementsByTagName("skin").item(0).getTextContent().matches("default"))
                                                                panelRef.getLeftCharacter().setSkin(Color.web(nameElement.getElementsByTagName("skin").item(0).getTextContent().toUpperCase()));

                                                        if(nameElement.getElementsByTagName("hair").item(0) != null) {
                                                            if (nameElement.getElementsByTagName("hair").item(0).getTextContent().matches("bald")) {
                                                                panelRef.getLeftCharacter().setHair(panelRef.getLeftCharacter().getSkin());
                                                            } else if (!nameElement.getElementsByTagName("hair").item(0).getTextContent().matches("default|blond")) {
                                                                panelRef.getLeftCharacter().setHair(Color.web(nameElement.getElementsByTagName("hair").item(0).getTextContent().toUpperCase()));
                                                            }
                                                        }
                                                        if(nameElement.getElementsByTagName("lips").item(0) != null) {
                                                            if (!nameElement.getElementsByTagName("lips").item(0).getTextContent().matches("default")) {
                                                                panelRef.getLeftCharacter().setLips(Color.web(nameElement.getElementsByTagName("lips").item(0).getTextContent().toUpperCase()));
                                                            }
                                                        }

                                                        if(nameElement.getElementsByTagName("facing").item(0) != null)
                                                            if (nameElement.getElementsByTagName("facing").item(0).getTextContent().matches("left"))
                                                                panelRef.getLeftCharacter().flipOrientation();

                                                        if(nameElement.getElementsByTagName("xPosition").item(0) != null)
                                                            panelRef.getLeftCharacter().setTranslateX(Double.parseDouble(nameElement.getElementsByTagName("xPosition").item(0).getTextContent()));

                                                        if(nameElement.getElementsByTagName("yPosition").item(0) != null)
                                                            panelRef.getLeftCharacter().setTranslateY(Double.parseDouble(nameElement.getElementsByTagName("yPosition").item(0).getTextContent()));

                                                    } else if (figureNode.getNodeName().matches("pose")) {
                                                        panelRef.setLeftCharacter("src/images/characters/" + figureNode.getTextContent() + ".png");
                                                    } else if (figureNode.getNodeName().matches("appearance")) {
                                                        if (figureNode.getTextContent().matches("male"))
                                                            panelRef.getLeftCharacter().setFemale(false);
                                                    } else if (figureNode.getNodeName().matches("skin")) {
                                                        if (!figureNode.getTextContent().matches("default"))
                                                            panelRef.getLeftCharacter().setSkin(Color.web(figureNode.getTextContent()));
                                                    } else if (figureNode.getNodeName().matches("hair")) {
                                                        if (figureNode.getTextContent().matches("bald")) {
                                                            panelRef.getLeftCharacter().setHair(panelRef.getLeftCharacter().getSkin());
                                                        } else if (!figureNode.getTextContent().matches("default|blond"))
                                                            panelRef.getLeftCharacter().setHair(Color.web(figureNode.getTextContent().toUpperCase()));
                                                    } else if (figureNode.getNodeName().matches("lips")) {
                                                        if (!figureNode.getTextContent().matches("default"))
                                                            panelRef.getLeftCharacter().setLips(Color.web(figureNode.getTextContent()));
                                                    } else if (figureNode.getNodeName().matches("facing")) {
                                                        if (figureNode.getTextContent().matches("left"))
                                                            panelRef.getLeftCharacter().flipOrientation();
                                                    } else if (figureNode.getNodeName().matches("xPosition")) {
                                                        panelRef.getLeftCharacter().setTranslateX(Double.parseDouble(figureNode.getTextContent()));
                                                    } else if (figureNode.getNodeName().matches("yPosition")) {
                                                        panelRef.getLeftCharacter().setTranslateY(Double.parseDouble(figureNode.getTextContent()));
                                                    }
                                                    figureNode = figureNode.getNextSibling();
                                                }
                                            }

                                            if (leftNode.getNodeName().matches("balloon")) {
                                                Node balloonNode = leftNode.getFirstChild();

                                                while (balloonNode != null) {

                                                    if (balloonNode.getTextContent().matches("")) {
                                                        balloonNode = balloonNode.getNextSibling();
                                                        continue;
                                                    }

                                                    if (balloonNode.getNodeName().matches("content")) {

                                                        Element eContent = (Element) balloonNode;

                                                        String content = balloonNode.getTextContent();
                                                        String status = leftNode.getAttributes().item(0).getTextContent();
                                                        FileInputStream fileInputStream = new FileInputStream("src/images/bubbles/" + status + ".png");
                                                        Image image = new Image(fileInputStream);
                                                        Font font;

                                                        boolean bold = false;
                                                        boolean italic = false;

                                                        if (eContent.hasAttribute("bold"))
                                                            if (eContent.getAttribute("bold").matches("true"))
                                                                bold = true;

                                                        if (eContent.hasAttribute("italic"))
                                                            if (eContent.getAttribute("italic").matches("true"))
                                                                italic = true;

                                                        if (bold && italic)
                                                            font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.ITALIC, 12);
                                                        else if (!bold && italic)
                                                            font = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.ITALIC, 12);
                                                        else if (bold && !italic)
                                                            font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 12);
                                                        else
                                                            font = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 12);

                                                        panelRef.setLeftBubble(image, content, font, status);

                                                        eContent = null;

                                                        fileInputStream = null;
                                                        content = null;
                                                        font = null;
                                                        status = null;
                                                        image = null;
                                                    }

                                                    if (balloonNode.getNodeName().matches("xPosition"))
                                                        panelRef.getLeftTextBubble().setTranslateX(Double.parseDouble(balloonNode.getTextContent()));

                                                    if (balloonNode.getNodeName().matches("yPosition"))
                                                        panelRef.getLeftTextBubble().setTranslateY(Double.parseDouble(balloonNode.getTextContent()));

                                                    balloonNode = balloonNode.getNextSibling();
                                                }
                                            }

                                            leftNode = leftNode.getNextSibling();
                                        }
                                    }

                                    if (currentNode.getNodeName().matches("right")) {
                                        Node rightNode = currentNode.getFirstChild();

                                        while (rightNode != null) {

                                            if (rightNode.getNodeName().matches("figure")) {
                                                Node figureNode = rightNode.getFirstChild();
                                                Element nameElement = null;

                                                if (!rightNode.getTextContent().matches(""))
                                                    panelRef.setRightCharacter("src/images/characters/neutral.png");

                                                while (figureNode != null) {

                                                    if (figureNode.getTextContent().matches("")) {
                                                        figureNode = figureNode.getNextSibling();
                                                        continue;
                                                    } else if (figureNode.getNodeName().matches("name")) {
                                                        nameElement = characterHashMap.get(figureNode.getTextContent());

                                                        if(nameElement.getElementsByTagName("pose").item(0) != null)
                                                            panelRef.setRightCharacter("src/images/characters/" + nameElement.getElementsByTagName("pose").item(0).getTextContent() + ".png");

                                                        if(nameElement.getElementsByTagName("appearance").item(0) != null)
                                                            if (nameElement.getElementsByTagName("appearance").item(0).getTextContent().matches("male"))
                                                                panelRef.getRightCharacter().setFemale(false);

                                                        if(nameElement.getElementsByTagName("skin").item(0) != null)
                                                            if (!nameElement.getElementsByTagName("skin").item(0).getTextContent().matches("default"))
                                                                panelRef.getRightCharacter().setSkin(Color.web(nameElement.getElementsByTagName("skin").item(0).getTextContent().toUpperCase()));

                                                        if(nameElement.getElementsByTagName("hair").item(0) != null) {
                                                            if (nameElement.getElementsByTagName("hair").item(0).getTextContent().matches("bald")) {
                                                                panelRef.getRightCharacter().setHair(panelRef.getRightCharacter().getSkin());
                                                            } else if (!nameElement.getElementsByTagName("hair").item(0).getTextContent().matches("default|blond")) {
                                                                panelRef.getRightCharacter().setHair(Color.web(nameElement.getElementsByTagName("hair").item(0).getTextContent().toUpperCase()));
                                                            }
                                                        }
                                                        if(nameElement.getElementsByTagName("lips").item(0) != null) {
                                                            if (!nameElement.getElementsByTagName("lips").item(0).getTextContent().matches("default")) {
                                                                panelRef.getRightCharacter().setLips(Color.web(nameElement.getElementsByTagName("lips").item(0).getTextContent().toUpperCase()));
                                                            }
                                                        }

                                                        if(nameElement.getElementsByTagName("facing").item(0) != null)
                                                            if (nameElement.getElementsByTagName("facing").item(0).getTextContent().matches("left"))
                                                                panelRef.getRightCharacter().flipOrientation();

                                                        if(nameElement.getElementsByTagName("xPosition").item(0) != null)
                                                            panelRef.getRightCharacter().setTranslateX(Double.parseDouble(nameElement.getElementsByTagName("xPosition").item(0).getTextContent()));

                                                        if(nameElement.getElementsByTagName("yPosition").item(0) != null)
                                                            panelRef.getRightCharacter().setTranslateY(Double.parseDouble(nameElement.getElementsByTagName("yPosition").item(0).getTextContent()));

                                                    } else if (figureNode.getNodeName().matches("pose")) {
                                                        panelRef.setRightCharacter("src/images/characters/" + figureNode.getTextContent() + ".png");
                                                    } else if (figureNode.getNodeName().matches("appearance")) {
                                                        if (figureNode.getTextContent().matches("male"))
                                                            panelRef.getRightCharacter().setFemale(false);
                                                    } else if (figureNode.getNodeName().matches("skin")) {
                                                        if (!figureNode.getTextContent().matches("default"))
                                                            panelRef.getRightCharacter().setSkin(Color.web(figureNode.getTextContent()));
                                                    } else if (figureNode.getNodeName().matches("hair")) {
                                                        if (figureNode.getTextContent().matches("bald")) {
                                                            panelRef.getRightCharacter().setHair(panelRef.getRightCharacter().getSkin());
                                                        } else if (!figureNode.getTextContent().matches("default|blond"))
                                                            panelRef.getRightCharacter().setHair(Color.web(figureNode.getTextContent().toUpperCase()));
                                                    } else if (figureNode.getNodeName().matches("lips")) {
                                                        if (!figureNode.getTextContent().matches("default"))
                                                            panelRef.getRightCharacter().setLips(Color.web(figureNode.getTextContent()));
                                                    } else if (figureNode.getNodeName().matches("facing")) {
                                                        if (figureNode.getTextContent().matches("right"))
                                                            panelRef.getRightCharacter().flipOrientation();
                                                    } else if (figureNode.getNodeName().matches("xPosition")) {
                                                        panelRef.getRightCharacter().setTranslateX(Double.parseDouble(figureNode.getTextContent()));
                                                    } else if (figureNode.getNodeName().matches("yPosition")) {
                                                        panelRef.getRightCharacter().setTranslateY(Double.parseDouble(figureNode.getTextContent()));
                                                    }
                                                    figureNode = figureNode.getNextSibling();
                                                }
                                            }

                                            if (rightNode.getNodeName().matches("balloon")) {
                                                Node balloonNode = rightNode.getFirstChild();

                                                while (balloonNode != null) {

                                                    if (balloonNode.getTextContent().matches("")) {
                                                        balloonNode = balloonNode.getNextSibling();
                                                        continue;
                                                    }

                                                    if (balloonNode.getNodeName().matches("content")) {

                                                        Element eContent = (Element) balloonNode;

                                                        String content = balloonNode.getTextContent();
                                                        String status = rightNode.getAttributes().item(0).getTextContent();
                                                        FileInputStream fileInputStream = new FileInputStream("src/images/bubbles/" + status + ".png");
                                                        Image image = new Image(fileInputStream);
                                                        Font font;

                                                        boolean bold = false;
                                                        boolean italic = false;

                                                        if (eContent.hasAttribute("bold"))
                                                            if (eContent.getAttribute("bold").matches("true"))
                                                                bold = true;

                                                        if (eContent.hasAttribute("italic"))
                                                            if (eContent.getAttribute("italic").matches("true"))
                                                                italic = true;

                                                        if (bold && italic)
                                                            font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.ITALIC, 12);
                                                        else if (!bold && italic)
                                                            font = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.ITALIC, 12);
                                                        else if (bold && !italic)
                                                            font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 12);
                                                        else
                                                            font = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 12);

                                                        panelRef.setRightBubble(image, content, font, status);

                                                        eContent = null;

                                                        fileInputStream = null;
                                                        content = null;
                                                        font = null;
                                                        status = null;
                                                        image = null;
                                                    }

                                                    if (balloonNode.getNodeName().matches("xPosition"))
                                                        panelRef.getRightTextBubble().setTranslateX(Double.parseDouble(balloonNode.getTextContent()));

                                                    if (balloonNode.getNodeName().matches("yPosition"))
                                                        panelRef.getRightTextBubble().setTranslateY(Double.parseDouble(balloonNode.getTextContent()));

                                                    balloonNode = balloonNode.getNextSibling();
                                                }
                                            }

                                            rightNode = rightNode.getNextSibling();
                                        }
                                    }

                                    currentNode = currentNode.getNextSibling();
                                }

                                progressBar.setValue((int) ((double) (temp + 1) / (double) nList.getLength() * 100));
                            }
                        }

                        frame.setVisible(false);
                        frame = null;
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Warning, file formatting of the XML document is incorrect.\n\nError: "+ e.getMessage());
                    alert.show();
                    frame.setVisible(false);
                    frame = null;
                }
            }
        });

        save_html.setOnAction((ActionEvent event) -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Warning, number of comic panels does not match layout.\nDo you still wish to continue?");

            //take 2
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

            //take 2
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

                    //popup window has to close but the file also has to not open
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
            popupScene.getStylesheets().add("sample/style.css");
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
                    undoList.add("delete|" + comicStrip.getChildren().indexOf(comicPanel[0]) + "|||");

                    hairColorPicker[0].setValue(Color.WHITE);
                    skinColorPicker[0].setValue(Color.WHITE);
                }
            }
        });

        undoButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {

                /*
                Actions that can be undone

                Moving Character: Panel, L/R, Previous Location
                Adding Speech Bubble: Panel, L/R
                Moving Speech Bubble: Panel, L/R, Previous Location
                Adding Text: Panel, T/B
                Add Background: Panel, Previous Image
                 */

                /*
                Actually Implemented

                Delete Panel: Panel
                Changing Character Image: Panel, L/R, Previous Image
                Flipping Character: Panel, L/R
                Gender Character: Panel, L/R
                Skin Character: Panel, L/R, Previous Colour
                Hair Character: Panel, L/R, Previous Colour
                Add Panel: Panel
                 */

                if(undoList.size() > 1) {

                    String toUndo = undoList.get(undoList.size() - 1);
                    undoList.remove(undoList.size() - 1);

                    int i=0;
                    while(toUndo.charAt(i) != '|')
                        i++;

                    String operation = toUndo.substring(0,i);
                    toUndo = toUndo.substring(i+1);


                    i=0;
                    while (toUndo.charAt(i) != '|')
                        i++;

                    String panel = toUndo.substring(0,i);
                    toUndo = toUndo.substring(i+1);


                    i=0;
                    while (toUndo.charAt(i) != '|')
                        i++;

                    String leftRight = toUndo.substring(0,i);
                    toUndo = toUndo.substring(i+1);


                    i=0;
                    while (toUndo.charAt(i) != '|')
                        i++;

                    String value = toUndo.substring(0,i);
                    toUndo = toUndo.substring(i+1);

                    if (operation.matches("delete")) {
                        comicStrip.getChildren().remove(newPanelRight);
                        if (deletedPanels.size() > 0) {
                            i = deletedPanels.get(deletedPanels.size() - 1).getIndex();
                            if (i > comicStrip.getChildren().size())
                                i = comicStrip.getChildren().size();
                            comicStrip.getChildren().add(i, deletedPanels.get(deletedPanels.size() - 1));
                            deletedPanels.remove(deletedPanels.size() - 1);
                        }
                        comicStrip.getChildren().add(newPanelRight);
                    }else if (operation.matches("flip")) {

                        if (leftRight.matches("left"))
                            ((ComicPanel) (comicStrip.getChildren().get(Integer.parseInt(panel)))).getLeftCharacter().flipOrientation();
                        else
                            ((ComicPanel) (comicStrip.getChildren().get(Integer.parseInt(panel)))).getRightCharacter().flipOrientation();

                    }else if (operation.matches("gender")){

                        if (leftRight.matches("left"))
                            ((ComicPanel) (comicStrip.getChildren().get(Integer.parseInt(panel)))).getLeftCharacter().genderSwap();
                        else
                            ((ComicPanel) (comicStrip.getChildren().get(Integer.parseInt(panel)))).getRightCharacter().genderSwap();
                    }else if (operation.matches("skin")){

                        skinColorPicker[0].setValue(Color.web(value));

                        if (leftRight.matches("left"))
                            ((ComicPanel) (comicStrip.getChildren().get(Integer.parseInt(panel)))).getLeftCharacter().setSkin(Color.web(value));
                        else
                            ((ComicPanel) (comicStrip.getChildren().get(Integer.parseInt(panel)))).getRightCharacter().setSkin(Color.web(value));
                    }else if (operation.matches("hair")){

                         hairColorPicker[0].setValue(Color.web(value));

                        if (leftRight.matches("left"))
                            ((ComicPanel) (comicStrip.getChildren().get(Integer.parseInt(panel)))).getLeftCharacter().setHair(Color.web(value));
                        else
                            ((ComicPanel) (comicStrip.getChildren().get(Integer.parseInt(panel)))).getRightCharacter().setHair(Color.web(value));
                    }else if (operation.matches("panel")){

                        comicStrip.getChildren().remove(Integer.parseInt(panel));
                    }else if (operation.matches("character")){

                        if (leftRight.matches("left")) {
                            try {
                                ((ComicPanel) (comicStrip.getChildren().get(Integer.parseInt(panel)))).getLeftCharacter().setCharacterImageView("src/images/characters/" + value + ".png");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            try {
                                ((ComicPanel) (comicStrip.getChildren().get(Integer.parseInt(panel)))).getRightCharacter().setCharacterImageView("src/images/characters/" + value + ".png");
                                ((ComicPanel) (comicStrip.getChildren().get(Integer.parseInt(panel)))).getRightCharacter().flipOrientation();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
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

                    undoList.add("panel|" + comicStrip.getChildren().indexOf(newComicPanel) + "|||");

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

                                                if(dragEvent.getScreenX() > (4*width/5)) {
                                                    double hV = scrollPane.getHvalue();
                                                    scrollPane.setHvalue(scrollPane.getHvalue() + 0.001);
                                                    if(scrollPane.getHvalue() != 1) {
                                                        newComicPanel.setTranslateX(newComicPanel.getTranslateX() + 9);
                                                        scroll.set(scroll.get() + 9);
                                                    }
                                                }else if(dragEvent.getScreenX() < width/5){
                                                    scrollPane.setHvalue(scrollPane.getHvalue() - 0.001);
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

                                    imageView.setFitWidth(imageWidth-2);
                                    imageView.setFitHeight(imageHeight-2);
                                    lockButton[0].setGraphic(imageView);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    ImageView imageView = new ImageView(new Image(new FileInputStream("src/images/buttons/unlock.png")));

                                    double imageWidth = backgroundButton.getWidth();
                                    double imageHeight = backgroundButton.getHeight();

                                    imageView.setFitWidth(imageWidth-2);
                                    imageView.setFitHeight(imageHeight-2);
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
        scene.getStylesheets().add("sample/style.css");


        scene.setOnKeyPressed(event -> {
            String codeString = event.getCode().toString();
            if(codeString.matches("Z"))
                undoButton.fire();
        });

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);

        newPanelRight.fire();

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

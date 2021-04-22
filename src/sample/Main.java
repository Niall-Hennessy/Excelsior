package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

        final ComicPanel[] comicPanel = {new ComicPanel()};

        List<ComicPanel> deletedPanels = new Stack<>();

        final String[] character = new String[1];

        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: #B9EBFF");

        Menu file = new Menu("File");
        MenuItem new_project = new MenuItem("New Project");
        MenuItem save_xml = new MenuItem("Save XML");
        MenuItem save_htMl = new MenuItem("Save HTMl");
        MenuItem load_xml = new MenuItem("Load XML");
        MenuItem load_html = new MenuItem("Load HTML");
        MenuItem add_character = new MenuItem("Add Character");
        file.getItems().add(new_project);
        file.getItems().add(save_xml);
        file.getItems().add(save_htMl);
        file.getItems().add(load_xml);
        file.getItems().add(load_html);
        file.getItems().add(add_character);

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
        skinColorPicker[0] = ButtonIcon.colorPickerStyling(skinColorPicker[0]);
        hairColorPicker[0] = ButtonIcon.colorPickerStyling(hairColorPicker[0]);

        GridPane buttonLayout = new GridPane();
        buttonLayout.setStyle("-fx-border-color: black; -fx-background-color: #FEF7D3; -fx-border-width: 3px");
        buttonLayout.setPrefHeight(300);
        buttonLayout.setPrefWidth(width);

//        buttonLayout.setHgap(50);
        buttonLayout.setVgap(50);
//        buttonLayout.setPadding(new Insets(50,50,50,50));

//      Lower Panel Buttons and and placement
        ButtonIcon buttonIcon = new ButtonIcon();
        Button rightCharacter = buttonIcon.getButtonIcon("src/images/buttons/lookLeft.png");
        Button leftCharacter = buttonIcon.getButtonIcon("src/images/buttons/lookRight.png");
        Button flipButton = buttonIcon.getButtonIcon("src/images/buttons/swapLR.png");
        Button genderButton = buttonIcon.getButtonIcon("src/images/buttons/MFButton.png");
        Button textButton = buttonIcon.getButtonIcon("src/images/buttons/T_Button.png");
        Button bubbleButton = buttonIcon.getButtonIcon("src/images/buttons/speech_bubble.png");
        Button deleteButton = buttonIcon.getButtonIcon("src/images/buttons/delete.png");



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
        String tipDeleteButton      = "Delete Selected Object";
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

        deleteButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hoverTips.buttonToolTip(tipDeleteButton, mouseEvent, deleteButton);
            }
        });

        skinColorPicker[0].setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(comicPanel[0].getSelectedCharacter() != null) {
                    hoverTips.colorToolTip(tipskinColorPicker, mouseEvent, skinColorPicker[0]);
                }
                else {
                    hoverTips.NoCharacterSelectedTip(tipNoCharacterSelected, skinColorPicker[0]);
                }
            }
        });

        hairColorPicker[0].setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(comicPanel[0].getSelectedCharacter() != null) {
                    hoverTips.colorToolTip(tiphairColorPicker, mouseEvent, hairColorPicker[0]);
                }
                else {
                    hoverTips.NoCharacterSelectedTip(tipNoCharacterSelected, hairColorPicker[0]);
                }
            }
        });

        save_xml.setOnAction(new EventHandler<ActionEvent>() {
            final Stage saveXML = new Stage();

            @Override
            public void handle(ActionEvent event) {

                if(saveXML.isShowing()) {
                    saveXML.initModality(Modality.APPLICATION_MODAL);
                    saveXML.initOwner(primaryStage);
                }

                GridPane savePane = new GridPane();

                Button save = new Button("Save");

                TextField textField = new TextField();

                saveXML.setWidth(Screen.getPrimary().getVisualBounds().getWidth()/10);
                saveXML.setHeight(Screen.getPrimary().getVisualBounds().getHeight()/10);

                savePane.add(textField, 0, 0, 1, 1);
                savePane.add(save, 0, 1, 1, 1);

                Scene scene = new Scene(savePane);
                saveXML.setScene(scene);
                saveXML.show();


                save.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if(textField.getText().trim().matches(""))
                            return;


                        try {
                            File myObj = new File("src/files/" + textField.getText() + ".xml");
                            if (myObj.createNewFile()) {
                                System.out.println("File created: " + myObj.getName());
                            } else {
                                System.out.println("File already exists.");
                            }
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        load_xml.setOnAction(new EventHandler<ActionEvent>() {
            final Stage saveXML = new Stage();

            @Override
            public void handle(ActionEvent event) {

                if(saveXML.isShowing()) {
                    saveXML.initOwner(primaryStage);
                }

                FileChooser fileChooser = new FileChooser();

                File selectedFile = fileChooser.showOpenDialog(saveXML);



                saveXML.setWidth(Screen.getPrimary().getVisualBounds().getWidth()/10);
                saveXML.setHeight(Screen.getPrimary().getVisualBounds().getHeight()/10);

                VBox vBox = new VBox();

                Scene scene = new Scene(vBox);
                saveXML.setScene(scene);
                saveXML.show();
            }
        });

        add_character.setOnAction(new EventHandler<ActionEvent>() {
            final Stage saveXML = new Stage();

            @Override
            public void handle(ActionEvent event) {

                if(saveXML.isShowing()) {
                    saveXML.initOwner(primaryStage);
                }

                FileChooser fileChooser = new FileChooser();

                File selectedFile = fileChooser.showOpenDialog(saveXML);

                File newFile = new File("src/images/characters/" + selectedFile.getName());

                selectedFile.renameTo(newFile);

                saveXML.setWidth(Screen.getPrimary().getVisualBounds().getWidth()/10);
                saveXML.setHeight(Screen.getPrimary().getVisualBounds().getHeight()/10);

                VBox vBox = new VBox();

                Scene scene = new Scene(vBox);
                saveXML.setScene(scene);
                saveXML.show();
            }
        });

        help.setOnAction(new EventHandler<ActionEvent>() {
            final Stage helpStage = new Stage();

            @Override
            public void handle(ActionEvent event) {

                helpStage.setTitle("Help");

                TabPane helpPane = new TabPane();
                helpPane.getStyleClass().add("helpPane");

                Tab characterTab = new Tab("Character");
                Tab speechBubbleTab = new Tab("Speech Bubbles");
                Tab colourTab = new Tab("Skin/Hair");
                Tab captionTab = new Tab("Caption");

                characterTab.getStyleClass().add("helpTab");
                speechBubbleTab.getStyleClass().add("helpTab");
                colourTab.getStyleClass().add("helpTab");
                captionTab.getStyleClass().add("helpTab");

                characterTab.closableProperty().setValue(false);
                speechBubbleTab.closableProperty().setValue(false);
                colourTab.closableProperty().setValue(false);
                captionTab.closableProperty().setValue(false);

                helpPane.getTabs().add(characterTab);
                helpPane.getTabs().add(speechBubbleTab);
                helpPane.getTabs().add(colourTab);
                helpPane.getTabs().add(captionTab);

                helpStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth()/1.7);
                helpStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight()/2);

                Label label = new Label();

                label = new Label(
                        "\n Let's add a character to your comic!\n " +
                                "\n First press the plus icon in the white panel." +
                                "\n Now that a black comic panel has appeared, select it so that it is highlighted." +
                                "\n Click on the character icon to choose a left or right character." +
                                "\n Double click a character pose from the gallery." +
                                "\n Use the Flip Button to change which way they are facing." +
                                "\n Use the M/F button to change their gender."
                );

                label.setStyle("-fx-background-color: white");
                ScrollPane instructionCharacter =  new ScrollPane(label);

                label = new Label(
                        "\n Let's get your characters talking!\n " +
                                "\n Note: You have to have a character in your panel before you can make them talk.\n " +
                                "\n Click on the speech bubble icon." +
                                "\n Choose what bubble you want." +
                                "\n Write in the text-box what you want them to say - Careful, there is a character limit." +
                                "\n Choose if you want the text in italic, or bold, or both." +
                                "\n Hit Submit and voila!" +
                                "\n Hit Cancel if you change your mind." +
                                "\n Hit Delete if you want to get rid of the bubble."
                );

                label.setStyle("-fx-background-color: white");
                ScrollPane instructionSpeechBubble =  new ScrollPane(label);

                label = new Label(
                        "\n Let's add some colour!\n " +
                                "\n Select the character who's Skin/Hair you wish to change." +
                                "\n Select the Skin/Hair colour picker to select a new colour."
                );

                label.setStyle("-fx-background-color: white");
                ScrollPane instructionColour =  new ScrollPane(label);

                label = new Label(
                        "\n Let's caption your panel!\n " +
                                "\n Hit the caption button." +
                                "\n Write what you want the caption to be." +
                                "\n Choose a font for you caption." +
                                "\n Hit 'Apply' and see it appear." +
                                "\n Hit 'Cancel' if you change your mind." +
                                "\n Hit 'Delete' after selecting either the 'Top Text' or 'Bottom Text' to remove the caption."
                );

                ScrollPane instructionCaption =  new ScrollPane(label);

                instructionCharacter.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                instructionCharacter.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                instructionCharacter.fitToWidthProperty().setValue(true);

                instructionSpeechBubble.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                instructionSpeechBubble.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                instructionSpeechBubble.fitToWidthProperty().setValue(true);

                instructionColour.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                instructionColour.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                instructionColour.fitToWidthProperty().setValue(true);

                instructionCaption.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                instructionCaption.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                instructionCaption.fitToWidthProperty().setValue(true);

                characterTab.setContent(instructionCharacter);
                speechBubbleTab.setContent(instructionSpeechBubble);
                colourTab.setContent(instructionColour);
                captionTab.setContent(instructionCaption);

                Scene scene = new Scene(helpPane);
                helpStage.setScene(scene);
                scene.getStylesheets().add("sample/style.css");
                helpStage.show();
            }
        });

        GalleryStuff galleryView = new GalleryStuff();

        rightCharacter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(!comicStrip.getChildren().contains(comicPanel[0])){
                    hoverTips.NoPanelSelectedTip(tipNoPanelSelected, rightCharacter);
                    return;
                }

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

                if(comicPanel[0].getSelectedCharacter() != null) {
                    comicPanel[0].getSelectedCharacter().flipOrientation();
                }
                else {
                    hoverTips.NoPanelSelectedTip(tipNoCharacterSelected, flipButton);
                }
            }
        });

        genderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(comicPanel[0].getSelectedCharacter() != null)
                    comicPanel[0].getSelectedCharacter().genderSwap();
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

            private double xOffset = 0;
            private double yOffset = 0;

            @Override
            public void handle(ActionEvent event) {

                if(!comicStrip.getChildren().contains(comicPanel[0])){
                    hoverTips.NoPanelSelectedTip(tipNoCharacterSelected, bubbleButton);
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

                if(addBubble.isShowing()) {
                    addBubble.initOwner(primaryStage);
                }

                HBox bubbleGallery = new HBox();
                bubbleGallery.getStyleClass().add("bubbles");

                File folder = new File("src/images/bubbles");
                File[] listOfFiles = folder.listFiles();

                TextField textfield = new TextField();

                final boolean[] isBold = {false};
                final boolean[] isItalic = {false};

                if(comicPanel[0].leftTextBubble != null) {
                    if(comicPanel[0].leftTextBubble.getText().getFont().toString().substring(17,21).matches("Bold")){
                        isBold[0] = true;
                    }
                    if(comicPanel[0].leftTextBubble.getText().getFont().toString().substring(22,28).matches("Italic")){
                        isItalic[0] = true;
                    }
                    else if(comicPanel[0].leftTextBubble.getText().getFont().toString().substring(17,23).matches("Italic")){
                        isItalic[0] = true;
                    }
                }

                if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getLeftCharacter()) && comicPanel[0].leftTextBubble != null) {
                    textfield.setText(comicPanel[0].leftTextBubble.getText().getText().replaceAll("\n", " "));

                    if(isBold[0] && isItalic[0])
                        textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.ITALIC, textfield.getFont().getSize()));
                    else if(!isBold[0] && isItalic[0])
                        textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.ITALIC, textfield.getFont().getSize()));
                    else if(isBold[0] && !isItalic[0])
                        textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.BOLD, FontPosture.REGULAR, textfield.getFont().getSize()));
                    else
                        textfield.setFont(Font.font(textfield.getFont().getName(), FontWeight.NORMAL, FontPosture.REGULAR, textfield.getFont().getSize()));
                }
                else if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getRightCharacter()) && comicPanel[0].rightTextBubble != null)
                    textfield.setText(comicPanel[0].rightTextBubble.getText().getText());


                final Text[] characterCounter = {new Text(textfield.getText().length() + "/50")};

                textfield.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                        characterCounter[0].setText(Integer.toString(textfield.getText().length()) + "/50");
                        if (textfield.getText().length() > 50) {
                            String s = textfield.getText().substring(0, 50);
                            textfield.setText(s);
                        }
                    }
                });

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
                bubbleGrid.add(characterCounter[0],0, 11, 1, 1);
                bubbleGrid.setMargin(characterCounter[0], new Insets (2, 2, 2, 30));
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
                            comicPanel[0].setLeftBubble(((ImageView)bubbleDisplay.getChildren().get(0)).getImage(), textfield.getText(), textfield.getFont());
                        else if(comicPanel[0].getSelectedCharacter().equals(comicPanel[0].getRightCharacter()))
                            comicPanel[0].setRightBubble(((ImageView)bubbleDisplay.getChildren().get(0)).getImage(), textfield.getText(), textfield.getFont());


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
                    imageView = new ImageView(image);
                    //imageView.setFitWidth(150);
                    imageView.setPickOnBounds(true);
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent mouseEvent) {

                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

                                if (mouseEvent.getClickCount() == 1) {
                                    ((ImageView)bubbleDisplay.getChildren().get(0)).setImage(image);
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

        textButton.setOnAction(new EventHandler<ActionEvent>() {
            final Stage addText = new Stage(StageStyle.UNDECORATED);

            @Override
            public void handle(ActionEvent event) {

                if(!comicStrip.getChildren().contains(comicPanel[0])){
                    hoverTips.NoPanelSelectedTip(tipNoPanelSelected, textButton);
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

                if(comicPanel[0].topText != null) {
                    captionTextfield.setFont(comicPanel[0].topText.text.getFont());
                    combo_box.setValue(comicPanel[0].topText.text.getFont().getName());
                }
                else
                    captionTextfield.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 20));

                captionTextfield.getStyleClass().add("capTextField");
                HBox topOrBot = new HBox(captionTextfield);
                captionTextfield.setPrefWidth(400);
                captionTextfield.setPrefHeight(25);

                final Text[] captionCharacterCounter = {new Text(captionTextfield.getText().length() + "/50")};

                captionTextfield.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                        captionCharacterCounter[0].setText(Integer.toString(captionTextfield.getText().length()) + "/50");
                        if (captionTextfield.getText().length() > 50) {
                            String s = captionTextfield.getText().substring(0, 50);
                            captionTextfield.setText(s);
                        }
                    }
                });

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
                layoutGrid.add(captionCharacterCounter[0], 2, 3, 1, 1);
                layoutGrid.setMargin(captionCharacterCounter[0], new Insets(5, 0, 5, 0));
                layoutGrid.add(submit, 0, 4, 1, 1);
                layoutGrid.setMargin(submit, new Insets (5, 0, 5, 25));
                layoutGrid.add(cancel,1, 4, 1, 1);
                layoutGrid.setMargin(cancel, new Insets (5, 0, 5, 0));
                layoutGrid.add(delete,2, 4, 1, 1);
                layoutGrid.setMargin(delete, new Insets (5, 0, 5, 0));



                addText.setWidth(Screen.getPrimary().getVisualBounds().getWidth()/3);
                addText.setHeight(Screen.getPrimary().getVisualBounds().getHeight()/3);

                final boolean[] top = {true};
                final boolean[] bottom = {false};

                topText.setStyle("-fx-background-color: #C089D7");
                bottomText.setStyle("-fx-background-color: #E5A6FF");

                if(comicPanel[0].topText != null)
                    captionTextfield.setText(comicPanel[0].topText.getText());

                topText.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        captionTextfield.setText("");
                        bottom[0] = false;
                        top[0] = true;

                        topText.setStyle("-fx-background-color: #C089D7");
                        bottomText.setStyle("-fx-background-color: #E5A6FF");

                        if(comicPanel[0].topText != null) {
                            captionTextfield.setText(comicPanel[0].topText.getText());
                            captionTextfield.setFont(comicPanel[0].topText.text.getFont());
                            combo_box.setValue(comicPanel[0].topText.text.getFont().getName());
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

                        if(comicPanel[0].bottomText != null) {
                            captionTextfield.setText(comicPanel[0].bottomText.getText());
                            captionTextfield.setFont(comicPanel[0].bottomText.text.getFont());
                            combo_box.setValue(comicPanel[0].bottomText.text.getFont().getName());
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

        skinColorPicker[0].setOnAction(new EventHandler() {
            public void handle(Event t) {

                if(comicPanel[0].getSelectedCharacter() != null)
                    comicPanel[0].getSelectedCharacter().setSkin(skinColorPicker[0].getValue());
                else {
                    hoverTips.NoCharacterSelectedTip(tipNoCharacterSelected, skinColorPicker[0]);
                }
            }
        });

        hairColorPicker[0].setOnAction(new EventHandler() {
            public void handle(Event t) {

                if(comicPanel[0].getSelectedCharacter() != null)
                    comicPanel[0].getSelectedCharacter().setHair(hairColorPicker[0].getValue());
                else {
                    hoverTips.NoCharacterSelectedTip(tipNoCharacterSelected,  hairColorPicker[0]);
                }
            }
        });

        Text skin = new Text();
        skin.setText("Skin:");
        skin.setStyle("-fx-font-size: 36; -fx-font-family: 'Lucida Console'");

        Text hair = new Text();
        hair.setText("Hair:");
        hair.setStyle("-fx-font-size: 36; -fx-font-family: 'Lucida Console'");


        buttonLayout.addColumn(0, leftCharacter, rightCharacter);
        buttonLayout.addColumn(1, flipButton, genderButton);
        buttonLayout.addColumn(2, textButton, bubbleButton);
        buttonLayout.addColumn(3, skin, hair);
        buttonLayout.addColumn(4, skinColorPicker[0], hairColorPicker[0]);
        buttonLayout.addColumn(5, deleteButton);

        buttonLayout.getColumnConstraints().add(0, new ColumnConstraints(primaryStage.getWidth()/6));
        buttonLayout.getColumnConstraints().add(1, new ColumnConstraints(primaryStage.getWidth()/6));
        buttonLayout.getColumnConstraints().add(2, new ColumnConstraints(primaryStage.getWidth()/6));
        buttonLayout.getColumnConstraints().add(3, new ColumnConstraints(primaryStage.getWidth()/6));
        buttonLayout.getColumnConstraints().add(4, new ColumnConstraints(primaryStage.getWidth()/6));


        HBox optionBox = new HBox(buttonLayout);
        optionBox.setAlignment(Pos.BOTTOM_LEFT);
        optionBox.setMargin(buttonLayout, new Insets(50, 10, 10, 10));

        optionBox.setStyle("-fx-background-color: #B9EBFF;");
        optionBox.setPrefHeight(400);

        Button newPanelRight = buttonIcon.getButtonIcon("src/images/buttons/plus.png");
        Button newPanelLeft = buttonIcon.getButtonIcon("src/images/buttons/plus.png");
        newPanelRight.setScaleX(0.5);
        newPanelRight.setScaleY(0.5);

        comicStrip.getChildren().add(newPanelRight);
        comicStrip.getChildren().add(newPanelLeft);
        comicStrip.setAlignment(Pos.CENTER);
        comicStrip.setPrefHeight(height/2 -20);
        comicStrip.setMinWidth(width - 20);
        comicStrip.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 3px");


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(comicStrip);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefHeight(height/2);
        scrollPane.setPrefWidth(width - 20);


        mainPane.addRow(0, menuBox);
        mainPane.addRow(1, scrollPane);
        mainPane.addRow(2, optionBox);
        mainPane.setStyle("-fx-background-color: #B9EBFF");

        newPanelRight.setVisible(false);
        newPanelLeft.setVisible(false);

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


                comicStrip.getChildren().remove(comicPanel[0]);

                deletedPanels.add(comicPanel[0]);

                hairColorPicker[0].setValue(Color.WHITE);
                skinColorPicker[0].setValue(Color.WHITE);
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
                    newComicPanel.index = comicStrip.getChildren().indexOf(newComicPanel);
                    System.out.println(newComicPanel.index);

                    newComicPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {


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

                            if(comicPanel[0].getSelectedCharacter() != null) {
                                hairColorPicker[0].setValue(comicPanel[0].getSelectedCharacter().getHair());
                                skinColorPicker[0].setValue(comicPanel[0].getSelectedCharacter().getSkin());
                            }
                            else {
                                hairColorPicker[0].setValue(Color.WHITE);
                                skinColorPicker[0].setValue(Color.WHITE);
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

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            mainPane.setPrefWidth(primaryStage.getWidth());

            menuBar.setPrefWidth(primaryStage.getWidth() - 20);
            menuBox.setPrefWidth(primaryStage.getWidth() - 20);

            scrollPane.setPrefWidth(primaryStage.getWidth() - 20);
            comicStrip.setPrefWidth(primaryStage.getWidth() - 20);

            optionBox.setPrefWidth(primaryStage.getWidth() - 20);

            buttonLayout.getColumnConstraints().remove(0);

            buttonLayout.getColumnConstraints().addAll(new ColumnConstraints(primaryStage.getWidth()/6));
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            mainPane.setPrefHeight(primaryStage.getHeight());
            scrollPane.setPrefHeight(primaryStage.getHeight()/2);
            comicStrip.setPrefHeight(primaryStage.getHeight()/2);
        });

        Scene scene = new Scene(mainPane, width, height, false);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.F5) {
                    comicStrip.getChildren().remove(newPanelRight);
                    if(deletedPanels.size() > 0) {
                        int i = deletedPanels.get(deletedPanels.size() - 1).index;
                        if(i > comicStrip.getChildren().size())
                            i = comicStrip.getChildren().size();
                        comicStrip.getChildren().add(i, deletedPanels.get(deletedPanels.size() - 1));
                        deletedPanels.remove(deletedPanels.size() - 1);
                    }
                    comicStrip.getChildren().add(newPanelRight);
                    keyEvent.consume();
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);

        newPanelRight.fire();

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

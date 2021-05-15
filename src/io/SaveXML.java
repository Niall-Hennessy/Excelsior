package io;

import comic.ComicPanel;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class SaveXML {

    public SaveXML(HBox comicStrip, File saveFile){

        try{
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

                    if(toParse.getLeftCharacter().getCharacterImageView().getRotate() == 180)
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

                    if(toParse.getRightCharacter().getCharacterImageView().getRotate() == 180)
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
}




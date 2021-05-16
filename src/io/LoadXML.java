package io;

import comic.ComicPanel;
import comic.Premise;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import undo.UndoList;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class LoadXML {

    //LoadXML loads in an XML file and sets the program as needed
    public LoadXML(File inputFile, HBox comicStrip, Premise premise, Button newPanelLeft, Button newPanelRight, double width, double height){
        JFrame frame = null;

        JProgressBar progressBar;

        try {

            if(inputFile != null) {

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);
                doc.getDocumentElement().normalize();

                if(doc.getElementsByTagName("premise").item(0) != null)
                    premise.setPremise(doc.getElementsByTagName("premise").item(0).getTextContent());
                else
                    premise.setPremise("");

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

            }

            inputFile = null;
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            frame.dispose();
            UndoList.clear();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Warning, file formatting of the XML document is incorrect.\n\nError: "+ e.getMessage());
            alert.show();

            if(frame != null) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                frame.dispose();
            }
        }
    }
}

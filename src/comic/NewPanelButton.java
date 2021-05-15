package comic;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class NewPanelButton extends Button {

    ComicStripViewer comicStripViewer;
    ComicStrip comicStrip;
    double width;
    double height;

    public NewPanelButton(ComicStripViewer comicStripViewer, double width, double height){
        this.comicStripViewer = comicStripViewer;
        this.comicStrip = this.comicStripViewer.getComicStrip();
        this.width = width;
        this.height = height;
    }

    public ComicPanel addPanel(ComicPanel currentPanel) throws FileNotFoundException {

        if(currentPanel == null)
            System.out.println("Shit");

        comicStrip.getChildren().remove(this);

        ComicPanel newComicPanel = new ComicPanel();

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

                                    comicStrip.getChildren().remove(newComicPanel);
                                    comicStrip.getChildren().add(index.get(), newComicPanel);
                                    newComicPanel.setTranslateX(newComicPanel.getTranslateX() + (height/2.4 + height/9.6));

                                }
                                else if(dragEvent.getScreenX() - mouseEvent.getSceneX() + scroll.get() > ((height/2.4 + height/9.6) * (amount.get() + 1))) {
                                    amount.getAndIncrement();
                                    index.getAndIncrement();

                                    if(index.get() > comicStripViewer.getComicStrip().getChildren().size()-2)
                                        index.set(comicStripViewer.getComicStrip().getChildren().size()-2);

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

                if(!currentPanel.equals(newComicPanel)) {

                    if (currentPanel != null) {
                        currentPanel.setSelectedCharacter(null);
                        currentPanel.unselect();
                    }

                    newComicPanel.setSelectedCharacter(null);
                    newComicPanel.select();
                    comicStrip.setCurrentPanel(newComicPanel);

                }
                else{
                    newComicPanel.select();
                }

                double w = comicStripViewer.getContent().getBoundsInLocal().getWidth();
                double x = (newComicPanel.getBoundsInParent().getMaxX() +
                        newComicPanel.getBoundsInParent().getMinX()) / 2.0;
                double v = comicStripViewer.getViewportBounds().getWidth();
                comicStripViewer.setHvalue(comicStripViewer.getHmax() * ((x - 0.5 * v) / (w - v)));
            }
        });

        comicStrip.getChildren().add(newComicPanel);
        comicStrip.setMargin(newComicPanel, new Insets(20,10,20,10));
        comicStrip.getChildren().add(this);

        return newComicPanel;
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
}

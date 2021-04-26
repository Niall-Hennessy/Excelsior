package sample;

import com.sun.javafx.application.LauncherImpl;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.sun.javafx.font.PrismFontFactory.isEmbedded;

public class SplashScreen extends Preloader {
    Stage stage;
    BooleanProperty ready = new SimpleBooleanProperty(false);

    private void longStart() {
        //simulate long init in background
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int max = 100;
                for (int i = 1; i <= max; i++) {
                    Thread.sleep(200);
                    // Send progress to preloader
                    notifyPreloader(new ProgressNotification(((double) i)/max));
                }
                // After init is ready, the app is ready to be shown
                // Do this before hiding the preloader stage to prevent the
                // app from exiting prematurely
                ready.setValue(Boolean.TRUE);

                notifyPreloader(new StateChangeNotification(
                        StateChangeNotification.Type.BEFORE_START));

                return null;
            }
        };
        new Thread(task).start();
    }

    //trying to make preloader go while stage sleeps
    @Override
    public void init () throws Exception{
        int COUNT_LIMIT = 40000;
        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
        }
    }

    @Override
    public void start(final Stage stage) throws Exception {
        // Initiate simulated long startup sequence
        longStart();

        stage.setScene(new Scene(new Label("Application started"),
                400, 400));

        // After the app is ready, show the stage
        ready.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (Boolean.TRUE.equals(t1)) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            stage.show();
                        }
                    });
                }
            }
        });;
    }
}


/*
public class SplashScreen extends Preloader {
    ProgressBar bar;
    Stage stage;

    private Scene createPreloaderScene() throws FileNotFoundException {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 300, 150);
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createPreloaderScene());
        stage.show();
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        bar.setProgress(pn.getProgress());
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt)
    {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START)
        {
            //stage.hide();
            //making the preloader fade out
            if(isEmbedded && stage.isShowing())
            {
                //fade out, hide stage at the end of animation
                FadeTransition fade = new FadeTransition(Duration.millis(1000), stage.getScene().getRoot());
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                final Stage s = stage;
                EventHandler<ActionEvent> acc = new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        s.hide();
                    }
                };

                fade.setOnFinished(acc);
                fade.play();
            }

            else
            {
                stage.hide();
            }
        }

    }
}
*/

package sample;

import javafx.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/*AAAARGH
public class SplashScreen extends Application {

    public SplashScreen getSplashScreen() {
        System.out.println("Checking");

        Pane splashLayout = new VBox();
        ImageView splashImage = new ImageView( new Image(getClass().getResourceAsStream("splahScreen/splash screen.png")));
        splashLayout.getChildren().add(splashImage);
        Scene scene = new Scene(splashLayout);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(3_000));
        System.out.println("Test 0");
        pause.setOnFinished(actionEvent -> {
            System.out.println("Test 1");
            Stage mainStage = new Stage();
            mainStage.setScene(scene);
            mainStage.show();
            stage.hide();
        });
        System.out.println("Test 2");
        pause.play();
    }

    @Override
    public void start(Stage stage) {
        Pane splashLayout = new VBox();
        ImageView splashImage = new ImageView( new Image(getClass().getResourceAsStream("splahScreen/splash screen.png")));
        splashLayout.getChildren().add(splashImage);
        Scene scene = new Scene(splashLayout);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(3_000));
        System.out.println("Test 0");
        pause.setOnFinished(actionEvent -> {
            System.out.println("Test 1");
            Stage mainStage = new Stage();
            mainStage.setScene(scene);
            mainStage.show();
            stage.hide();
        });
        System.out.println("Test 2");
        pause.play();

    }
}

*/
//AAAARGH



/*
public class SplashScreen extends Preloader {
    Stage stage;
    BooleanProperty ready = new SimpleBooleanProperty(false);

    static Scene splash;
    static Rectangle rect = new Rectangle();
    final private Pane pane;
    final private SequentialTransition seqT;

    public Splash() {
        pane = new Pane();
        pane.setStyle("-fx-background-color:black");

        splash = new Scene(pane);
        seqT = new SequentialTransition();
    }

    public void show() {
        /*
         * Part 1:
         * This is the rolling square animation.
         * This animation looks cool for a loading screen,
         * so I made this. Only the lines of code for fading
         * from Stack Overflow.
         */
/*
        //rectangle insertion
        int scale = 30;
        int dur = 800;
        rect = new Rectangle(100 - 2 * scale, 20, scale, scale);
        rect.setFill(Color.AQUAMARINE);

        //actual animations
        //initialising the sequentialTranslation
        //umm, ignore this, just some configs that work magic
        int[] rotins = {scale, 2 * scale, 3 * scale, 4 * scale, 5 * scale, -6 * scale, -5 * scale, -4 * scale, -3 * scale, -2 * scale};
        int x, y;
        for (int i : rotins) {
            //rotating the square
            RotateTransition rt = new RotateTransition(Duration.millis(dur), rect);
            rt.setByAngle(i / Math.abs(i) * 90);
            rt.setCycleCount(1);
            //moving the square horizontally
            TranslateTransition pt = new TranslateTransition(Duration.millis(dur), rect);
            x = (int) (rect.getX() + Math.abs(i));
            y = (int) (rect.getX() + Math.abs(i) + (Math.abs(i) / i) * scale);
            pt.setFromX(x);
            pt.setToX(y);
            //parallelly execute them and you get a rolling square
            ParallelTransition pat = new ParallelTransition();
            pat.getChildren().addAll(pt, rt);
            pat.setCycleCount(1);
            seqT.getChildren().add(pat);
        }
        //playing the animation
        seqT.play();
        //lambda code sourced from StackOverflow, fades away stage
        seqT.setNode(rect);
        //The text part
        Label label = new Label("Flight");
        label.setFont(new Font("Verdana", 40));
        label.setStyle("-fx-text-fill:white");
        label.setLayoutX(140);
        label.setLayoutY(70);
        Label lab = new Label("Launching...");
        lab.setFont(new Font("Times New Roman", 10));
        lab.setStyle("-fx-text-fill:white");
        lab.setLayoutX(170);
        lab.setLayoutY(180);
        //A complimentary image

        Image image = new Image("https://s3.amazonaws.com/media.eremedia.com/uploads/2012/08/24111405/stackoverflow-logo-700x467.png");
        ImageView iv = new ImageView(image);
        iv.setFitWidth(32);
        iv.setFitHeight(32);
        iv.setX(174);
        iv.setY(130);
        //now adding everything to position, opening the stage, start the animation
        pane.getChildren().addAll(rect, label, lab, iv);

        seqT.play();
    }

    public Scene getSplashScene() {
        return splash;
    }

    public SequentialTransition getSequentialTransition() {
        return seqT;
    }

}

/*
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
    /*
    @Override
    public void init () throws Exception{
        int COUNT_LIMIT = 40000;
        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
        }
    }*/
/*
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
        });
    }


    public void show() {
        stage.show();
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


class SplashScreen extends JWindow
{
    public SplashScreen(String filename, Frame f)
    {
        super(f);
        JLabel l = new JLabel(new ImageIcon("splahScreen/splash screen.png"));
        getContentPane().add(l, BorderLayout.CENTER);
        pack();
        Dimension screenSize =
                Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width/2 - (labelSize.width/2),
                screenSize.height/2 - (labelSize.height/2));
        setVisible(true);
        screenSize = null;
        labelSize = null;
        System.out.println("AAArgh");
    }

    public static SplashScreen getSplashScreen() {
        return getSplashScreen();
    }
}
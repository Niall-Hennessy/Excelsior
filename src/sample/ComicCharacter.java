package sample;

import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class ComicCharacter extends Pane {

    ImageView characterImageView = new ImageView(new Image(new FileInputStream("src/images/characters/blank.png")));
    int charBoxLength = (int)((Screen.getPrimary().getBounds().getHeight())/2.45)/3;

    Image characterImage;

    String imageName;

    boolean isFemale = true;

    Color skin = Color.rgb(255,232,216,1);
    Color hair =  Color.rgb(240,255,0,1);
    Color lips = Color.rgb(255, 200, 200, 1);

    public ComicCharacter() throws FileNotFoundException {
        this.getChildren().add(characterImageView);

        characterImage = characterImageView.getImage();

        updateImage();
    }

    public void updateImage(){
        this.getChildren().remove(characterImageView);
        Image image = characterImage;

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage writableImage = new WritableImage(width, height);

        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                    Color color = pixelReader.getColor(x, y);

                    if(color.equals(Color.WHITE)){
                        pixelWriter.setColor(x, y, Color.rgb(0, 0, 0, 0));
                    }else if(color.equals(Color.BLACK)){
                        pixelWriter.setColor(x, y, color);
                    }
                    else if(color.equals(Color.web("#A03E00"))){//Shoe Colour
                        pixelWriter.setColor(x, y, color);
                    }
                    else if (color.equals(Color.web("#FFE8D8"))) {//Skin Colour
                        pixelWriter.setColor(x, y, skin);
                    } else if (color.equals(Color.web("#F0FF00"))) {//Female Hair Colour
                        if(!isFemale) {
                            pixelWriter.setColor(x, y, Color.rgb(0, 0, 0, 0));
                        }
                        else
                            pixelWriter.setColor(x, y, hair);
                    }else if (color.equals(Color.web("#F9FF00"))) {//Male Hair Colour
                        pixelWriter.setColor(x, y, hair);
                    }
                    else if(isOnLine(Color.web("#F0FF00"), Color.web("#F9FF00"), color)){
                        pixelWriter.setColor(x, y, hair);
                    }
                    else if(isOnLine(Color.web("#C9E1E3"),Color.web("#75959C"),color)){
                        pixelWriter.setColor(x, y, color);
                    }
                    else if (color.equals(Color.web("#ECB4B5")) && !isFemale){

                        if(x < 400 && x > 360 && y > 160 && y < 250)
                            pixelWriter.setColor(x, y, Color.rgb(0, 0, 0, 0));
                        else if(x < 385 && x > 250 && y > 155 && y < 300)
                            pixelWriter.setColor(x, y, color);
                        else if(x < 423 && x > 255 && y > 290 && y < 350)
                            pixelWriter.setColor(x, y, color);
                        else
                            pixelWriter.setColor(x, y, Color.rgb(0, 0, 0, 0));
                    }
                    else if(color.equals(Color.web("#FF0000")) && !isFemale){
                        pixelWriter.setColor(x, y, lips);
                    }
                    else if(x < 400 && x > 200 && y > 200 && !isFemale){
                        if(color.toString().substring(2,4).matches("ff")) {
                            pixelWriter.setColor(x, y, skin);
                        }
                        else
                            pixelWriter.setColor(x, y, color);
                    }
                    else {
                        pixelWriter.setColor(x, y, color);
                    }
                }
            }

        ImageView imageView = new ImageView(writableImage);
        //imageView.setFitHeight(writableImage.getHeight()/4);
        //imageView.setFitWidth(writableImage.getWidth()/4);
        imageView.setFitHeight(charBoxLength);
        imageView.setFitWidth(charBoxLength);
        imageView.setSmooth(false);
        imageView.setRotationAxis(Rotate.Y_AXIS);
        imageView.setRotate(characterImageView.getRotate());

        characterImageView = imageView;

        this.getChildren().add(characterImageView);
    }

    public void genderSwap(){

        isFemale = !isFemale;

        updateImage();
    }

    public void flipOrientation(){

        ImageView flipCharacter = characterImageView;

        flipCharacter.setRotationAxis(Rotate.Y_AXIS);
        if(flipCharacter.getRotate() == 180)
            flipCharacter.setRotate(0);
        else
            flipCharacter.setRotate(180);

        characterImageView = flipCharacter;
    }

    public boolean isOnLine(Color p1, Color p2, Color p3)
    {

        boolean red = false;
        boolean green = false;
        boolean blue = false;

        double redVal = -1;
        double greenVal = -2;
        double blueVal = -3;


        if(Double.compare(p1.getRed(), p2.getRed()) == 0)
            red = Double.compare(p1.getRed(), p3.getRed()) == 0;
        else
            redVal = decimalConverter((p3.getRed() - p1.getRed()) / (p2.getRed() - p1.getRed()));

        if(Double.compare(p1.getGreen(), p2.getGreen()) == 0)//who the QUACK wrote a language that cannot cast between ints and booleans
            green = Double.compare(p1.getGreen(), p3.getGreen()) == 0;
        else
            greenVal = decimalConverter((p3.getGreen() - p1.getGreen()) / (p2.getGreen() - p1.getGreen()));

        if(Double.compare(p1.getBlue(), p2.getBlue()) == 0)
            blue = Double.compare(p1.getBlue(), p3.getBlue()) == 0;
        else
            blueVal = decimalConverter((p3.getBlue() - p1.getBlue()) / (p2.getBlue() - p1.getBlue()));



        if((Double.compare(redVal, greenVal) == 0 && Double.compare(redVal, blueVal) == 0) || (red && Double.compare(greenVal, blueVal) == 0) ||
                (green && Double.compare(redVal, blueVal) == 0) || (green && red)) {

            return true;
        }
        else
            return false;

    }

    public static double decimalConverter(double decimal) {
        BigDecimal bigDecimal = BigDecimal.valueOf(decimal);
        bigDecimal = bigDecimal.setScale(1, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public ImageView getCharacterImageView() {
        return characterImageView;
    }

    public void setCharacterImageView(String characterImagePath) throws FileNotFoundException {
        this.getChildren().remove(characterImageView);

        try {
            this.characterImage = new Image(new FileInputStream(characterImagePath));
        }catch (Exception e){
            this.characterImage = new Image(new FileInputStream("src/images/characters/neutral.png"));
        }

        this.characterImageView = new ImageView(characterImage);

        this.characterImageView.setFitWidth(100);
        this.characterImageView.setFitHeight(100);
        this.imageName = characterImagePath.substring(22,characterImagePath.length()-4);

        this.getChildren().add(characterImageView);

        updateImage();
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    public Color getSkin() {
        return skin;
    }

    public void setSkin(Color skin) {

        this.skin = skin;

        updateImage();
    }

    public Color getHair() {
        return hair;
    }

    public void setHair(Color hair) {

        this.hair = hair;

        updateImage();
    }

    public Color getLips() {
        return lips;
    }

    public void setLips(Color lips) {

        this.lips = lips;

        updateImage();
    }

    public String getImageName() {
        return imageName;
    }
}

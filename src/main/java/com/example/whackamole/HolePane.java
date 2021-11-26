package com.example.whackamole;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class HolePane extends StackPane {
    private static final Image emptyImage = new Image("file:src/main/java/com/example/whackamole/empty.png");
    private static final Image outImage = new Image("file:src/main/java/com/example/whackamole/out.png");
    private static final Image inImage =  new Image("file:src/main/java/com/example/whackamole/in.png");

    private ImageView imageView;

    private Text text;

    HolePane(){
        super();
        imageView = new ImageView(emptyImage);
        text = new Text();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Text getText() {
        return text;
    }

    public void hide() {
        imageView.setImage(emptyImage);
        text.setText("");
    }

    public void popOut(){
        imageView.setImage(outImage);
    }

    public void popIn(){
        imageView.setImage(inImage);
    }

    public boolean whack(){
        if (imageView.getImage() == outImage){
            text.setText("Ouch!!");
            popIn();
            return true;
        }

        else
            return false;
    }

}

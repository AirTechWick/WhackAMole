// Project: Whack A Mole
// Author: Erik Rodriguez
//
package com.example.whackamole;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

// Main application for starting the program
public class Main extends Application {
    HolePane[] holePanes = new HolePane[9]; // space for 9 hole panes

    int mole = 0; // represents the mole placement (0 to 8)

    Timer timer;

    // MolePopper subClass of TimerTask
    public class MolePopper extends TimerTask {
        final static int min = 0;
        final static int max = 8;

        @Override
        public void run() {
            holePanes[mole].hide();
            int randomMolePosition = ThreadLocalRandom.current().nextInt(min, max + 1);
            mole = randomMolePosition;
            holePanes[mole].popOut();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        fillHolePanes(); // fills hole panes array with hole pane objects

        // create buttons to add to hbox
        Button start = new Button("Start");
        Button stop = new Button("Stop");

        // create Hbox for 2 buttons and set it to the bottom of a border pane
        HBox hbox = createHBox(start, stop);
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(hbox);

        // add hole panes in 3x3 and set to center of border pane
        GridPane gridPane = new GridPane();
        fillGrid(gridPane, 3);
        borderPane.setCenter(gridPane);

        // Event Handling
        start.setOnAction(actionEvent -> {
            MolePopper molePopperTimerTask = new MolePopper();
            timer = new Timer();
            timer.schedule(molePopperTimerTask, 0, 500);
        });

        stop.setOnAction(actionEvent -> timer.cancel());

        for (HolePane holePane : holePanes) {
            holePane.setOnMousePressed(mouseEvent -> {
                HolePane holeThatWasHit = (HolePane) mouseEvent.getSource();
                boolean moleWhacked = holeThatWasHit.whack();
                if (moleWhacked)
                    timer.cancel();
            });
        }

        // creating the scene
        Scene scene = new Scene(borderPane, 561, 400); // window size of 561x400
        primaryStage.setScene(scene);
        primaryStage.setTitle("Whack-A-Mole");
        primaryStage.show();
    }

    // fills a gridpane with holepanes and also takes in a grid size argument
    private void fillGrid(GridPane gridPane, int gridSize) {
        int i = 0; // index for holePanes
        for (int column = 0; column < gridSize; column++) {
            for (int row = 0; row < gridSize; row++) {
                gridPane.add(holePanes[i].getImageView(), column, row);
                gridPane.add(holePanes[i].getText(), column, row);
                GridPane.setHalignment(holePanes[i].getText(), HPos.CENTER); // Used GridPane Halignment to center text
                gridPane.add(holePanes[i], column, row);
                i++;
            }
        }
    }

    // creates an HBox with two buttons
    private HBox createHBox(Button button1, Button button2) {

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(button1, button2);
        return hbox;
    }

    // fills hole panes array with holepane objects
    public void fillHolePanes() {
        for (int i = 0; i < holePanes.length; i++) {
            holePanes[i] = new HolePane();
        }
    }

}


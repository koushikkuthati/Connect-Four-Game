package com.connectfour.connectfourgame;


import com.sun.nio.sctp.AbstractNotificationHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectFourController implements Initializable {


    private static final int COLUMNS=7;
    private static final int ROWS=6;
    private static final int CIRCLE_DIAMETER=80;
    private static final String discColor1="#24303E";
    private static final String discColor2="#4CAA88";


    private static final String PlayerOne="Player One";
    private static final String PlayerTwo="Player Two";

    private boolean isPlayerOneTurn=true;


    @FXML
    public GridPane rootGridPane;

    @FXML
    public Pane insertedDiscsPane;

    @FXML
    public Label playerNameLabel;

    public void createPlayGround(){

        Shape rectangleWithHoles=new Rectangle(COLUMNS*CIRCLE_DIAMETER,ROWS*CIRCLE_DIAMETER);
        rectangleWithHoles.setFill(Color.WHITE);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

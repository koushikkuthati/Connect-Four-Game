package com.connectfour.connectfourgame;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConnectFourController implements Initializable {


    private static final int COLUMNS=7;
    private static final int ROWS=6;
    private static final int CIRCLE_DIAMETER=80;
    private static final String discColor1="#24303E";
    private static final String discColor2="#4CAA88";


    private static String PlayerOne="Player One";
    private static String PlayerTwo="Player Two";

    private boolean isPlayerOneTurn=true;



    private  Disc[][] insertedDiscsArray=new Disc[ROWS][COLUMNS];


    @FXML
    public GridPane rootGridPane;

    @FXML
    public Pane insertedDiscsPane;

    @FXML
    public Label playerNameLabel;

    @FXML
    public TextField playerOneTextField;

    @FXML
    public TextField playerTwoTextField;

    @FXML
    public Button setNamesButton;


    private boolean isAllowedToInsert =true;

    public void createPlayGround(){

        Shape rectangleWithHoles=createGameStructuralGrid();
        rootGridPane.add(rectangleWithHoles,0,1);

        List<Rectangle> rectangleList=createClickableColumns();
        for (Rectangle rectangle: rectangleList){
            rootGridPane.add(rectangle,0,1);
        }


        setNamesButton.setOnAction(actionEvent -> {
            PlayerOne=playerOneTextField.getText();
            PlayerTwo=playerTwoTextField.getText();
            playerNameLabel.setText(isPlayerOneTurn?PlayerOne:PlayerTwo);
        });

    }

    private Shape createGameStructuralGrid(){
        Shape rectangleWithHoles=new Rectangle((COLUMNS+1)*CIRCLE_DIAMETER,(ROWS+1)*CIRCLE_DIAMETER);


        for (int row=0;row<ROWS;row++){
            for (int column=0;column<COLUMNS;column++){
                Circle circle=new Circle();
                circle.setRadius(CIRCLE_DIAMETER/2);
                circle.setCenterX(CIRCLE_DIAMETER/2);
                circle.setCenterY(CIRCLE_DIAMETER/2);
                circle.setSmooth(true);


                circle.setTranslateX(column*(CIRCLE_DIAMETER+5)+CIRCLE_DIAMETER/4);
                circle.setTranslateY(row*(CIRCLE_DIAMETER+5)+CIRCLE_DIAMETER/4);
                rectangleWithHoles=Shape.subtract(rectangleWithHoles,circle);

            }
        }
        rectangleWithHoles.setFill(Color.WHITE);
        return rectangleWithHoles;

    }

    private List<Rectangle> createClickableColumns() {

        List<Rectangle> rectangleList = new ArrayList<>();

        for (int col = 0; col < COLUMNS; col++) {

            Rectangle rectangle = new Rectangle(CIRCLE_DIAMETER, (ROWS + 1) * CIRCLE_DIAMETER);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setTranslateX(col*(CIRCLE_DIAMETER+5)+CIRCLE_DIAMETER/4);

            rectangle.setOnMouseEntered(mouseEvent -> rectangle.setFill(Color.valueOf("#eeeeee26")));
            rectangle.setOnMouseExited(mouseEvent -> rectangle.setFill(Color.TRANSPARENT) );

            final int column=col;
            rectangle.setOnMouseClicked(mouseEvent -> {
                if (isAllowedToInsert) {
                    isAllowedToInsert=false;
                    insertDisc(new Disc(isPlayerOneTurn), column);
                }
            });

            rectangleList.add(rectangle);

        }
        return rectangleList;
    }


    private void insertDisc(Disc disc,int column){

        int row=ROWS-1;
        while (row>=0) {
            if (getDiscIfPresent(row,column) == null)
                break;
            row--;
        }
        if(row<0)
            return;


        insertedDiscsArray[row][column]=disc;
        insertedDiscsPane.getChildren().add(disc);
        disc.setTranslateX(column*(CIRCLE_DIAMETER+5)+CIRCLE_DIAMETER/4);
        int currentRow=row;
        TranslateTransition transition=new TranslateTransition(Duration.seconds(0.5),disc);
        transition.setToY(row*(CIRCLE_DIAMETER+5)+ CIRCLE_DIAMETER/4);

        transition.setOnFinished(actionEvent -> {

            isAllowedToInsert=true;

            if(gameEnded(currentRow,column)){
                gameOver();
                return;

            }

            isPlayerOneTurn=!isPlayerOneTurn;

            playerNameLabel.setText(isPlayerOneTurn? PlayerOne:PlayerTwo);
        });

        transition.play();



    }


    private boolean gameEnded(int currentRow, int column) {

        List<Point2D> veriticalPoints=IntStream.rangeClosed(currentRow-3,currentRow+3).
                mapToObj(r->new Point2D(r,column) ).
                collect(Collectors.toList());

        List<Point2D> horizontalPoints=IntStream.rangeClosed(column-3,column+3).
                mapToObj(col->new Point2D(currentRow,col) ).
                collect(Collectors.toList());

        Point2D startPoint1=new Point2D(currentRow-3,column+3);
        List<Point2D> diagonalPoints1=IntStream.rangeClosed(0,6).mapToObj(i->startPoint1.add(i,-i)).
                collect(Collectors.toList());


        Point2D startPoint2=new Point2D(currentRow-3,column-3);
        List<Point2D> diagonalPoints2=IntStream.rangeClosed(0,6).mapToObj(i->startPoint2.add(i,i)).
                collect(Collectors.toList());

        boolean isEnded=checkCombinations(veriticalPoints) || checkCombinations(horizontalPoints)|| checkCombinations(diagonalPoints1)||checkCombinations(diagonalPoints2);




        return isEnded;
    }

    private boolean checkCombinations(List<Point2D> points) {

        int chain=0;
       for(Point2D point:points) {

           int rowInderOfArray = (int) point.getX();
           int columnIndexOfArray = (int) point.getY();

           Disc disc=getDiscIfPresent(rowInderOfArray,columnIndexOfArray);

           if(disc!=null&&disc.isPlayerOneMove==isPlayerOneTurn){
               chain++;
               if(chain==4){
                    return true;
               }

           }else {
               chain=0;
           }
       }


        return false;
    }


    private Disc getDiscIfPresent(int row,int column){

       if(row>=ROWS||row<0|| column>=COLUMNS||column<0){
           return null;
       }


        return insertedDiscsArray[row][column];
    }



    private void gameOver() {

        String winner=isPlayerOneTurn?PlayerOne:PlayerTwo;
        System.out.println("winner is :"+winner);


        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connect Four");
        alert.setHeaderText("The winner is "+winner);
        alert.setContentText("Wanna Play again");

        ButtonType yesbtn=new ButtonType("Yes");
        ButtonType nobtn=new ButtonType("No, Exit");
        alert.getButtonTypes().setAll(yesbtn,nobtn);
        Platform.runLater(()-> {

            Optional<ButtonType> btnClicked=alert.showAndWait();
            if(btnClicked.isPresent()&&btnClicked.get().equals(yesbtn)){
                resetGame();
            }
            else    {

                Platform.exit();
                System.exit(0);
            }

        });

    }

    public void resetGame() {


        insertedDiscsPane.getChildren().clear();

        for(int row=0;row<insertedDiscsArray.length;row++){
            for(int column=0;column<insertedDiscsArray[row].length;column++){

                insertedDiscsArray[row][column]=null;
            }
        }
        isPlayerOneTurn=true;
        playerNameLabel.setText(PlayerOne);
        createPlayGround();
    }


    private static class Disc extends Circle{
        private final boolean isPlayerOneMove;

        public Disc(boolean isPlayerOneMove){
            this.isPlayerOneMove=isPlayerOneMove;
            setRadius(CIRCLE_DIAMETER/2);
            setFill(isPlayerOneMove? Color.valueOf(discColor1): Color.valueOf(discColor2));
            setCenterX(CIRCLE_DIAMETER/2);
            setCenterY(CIRCLE_DIAMETER/2);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}

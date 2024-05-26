package com.connectfour.connectfourgame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectFourApplication extends Application {
    private ConnectFourController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ConnectFourApplication.class.getResource("ConnectFour_view.fxml"));

        GridPane rootGridPane = fxmlLoader.load();

        controller = fxmlLoader.getController();
        controller.createPlayGround();

        MenuBar menuBar = createMenu();
        menuBar.prefWidthProperty().bind(stage.widthProperty());

        Pane menuPane = (Pane) rootGridPane.getChildren().get(0);

        menuPane.getChildren().add(menuBar);

        Scene scene = new Scene(rootGridPane);
        stage.setTitle("Connect Four ");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private MenuBar createMenu() {

        //File Menu
        Menu FileName = new Menu("File");

        MenuItem newGame = new MenuItem("New Game");

        newGame.setOnAction(ActionEvent -> {
            resetGame();

        });


        MenuItem resetGame = new MenuItem("Reset Game");
        resetGame.setOnAction(ActionEvent -> {
            resetGame();
        });

        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

        MenuItem exitGame = new MenuItem("Exit Game");

        exitGame.setOnAction(ActionEvent -> {
            exitGame();
        });

        FileName.getItems().addAll(newGame, resetGame, separatorMenuItem, exitGame);


        //Help Menu
        Menu HelpMenu = new Menu("Help");

        MenuItem newHelpMenu = new MenuItem("About Connect4");
        newHelpMenu.setOnAction(ActionEvent -> {
            aboutGame();
        });

        SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();
        MenuItem aboutApp = new MenuItem("About Me");
        aboutApp.setOnAction(ActionEvent -> {
            aboutMe();
        });

        HelpMenu.getItems().addAll(newHelpMenu, separatorMenuItem1, aboutApp);

        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(FileName, HelpMenu);


        return menuBar;

    }

    private void aboutMe() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About The Developer");
        alert.setHeaderText("Koushik Kuthati");
        alert.setContentText("I love to play around the code and create game. Connect4 is one them.");
        alert.show();


    }

    private void aboutGame() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Connect Four");
        alert.setHeaderText("How To Play?");
        alert.setContentText("Connect Four is a two-player " +
                "connection game in which the players first choose a color " +
                "and then take turns dropping colored discs from the top into a seven-column, " +
                "six-row vertically suspended grid. The pieces fall straight down, occupying the " +
                "next available space within the column. The objective of the game is to be " +
                "the first to form a horizontal, vertical, or diagonal line of four of one's own discs. " +
                "Connect Four is a solved game. The first player can always win by playing the right moves.");
        alert.show();


    }

    private void exitGame() {

        Platform.exit();
        System.exit(0);
    }

    private void resetGame() {


    }


}
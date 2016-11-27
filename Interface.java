package bj2;
//using javafx and google to figure out the scene drawing capabilities
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;

public class Interface extends Application {

    public Deck deck = new Deck();
    public Hand dealer, player;
    public Text message = new Text();

    public HBox hand_dealer = new HBox(10);
    public HBox hand_player = new HBox(10);

    public Parent makeGame() 
    {
    	
        dealer = new Hand(hand_dealer.getChildren());
        player = new Hand(hand_player.getChildren());

        //THIS is the base foundation that all graphics get added to and appear on
        Pane root = new Pane();

        Region background = new Region();

        HBox rootLayout = new HBox(200);
        Rectangle BG = new Rectangle(780, 440);
        BG.setFill(Color.FORESTGREEN);

        //Orienting the Board
        VBox VBox = new VBox(25);
        VBox.setAlignment(Pos.TOP_CENTER);

        /**Creating text fields that will display the scores of each player
         * These are binded so they get automatically updated
         */
        Text dealerScore = new Text("Dealer: ");
        Text playerScore = new Text("Player: ");

        //bank goes here-- TODO://

        Button btnPlay = new Button("Start Playing");
        Button btnHit = new Button("HIT");
        Button btnStay = new Button("STAY");
        Button btnQuit = new Button("Quit Playing");
        
        VBox.getChildren().addAll(dealerScore, hand_dealer, message, playerScore, hand_player);
        HBox bottom = new HBox(20);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.getChildren().addAll(btnPlay, btnHit, btnStay, btnQuit);

        // ADD BOTH STACKS TO ROOT LAYOUT
        rootLayout.getChildren().addAll(new StackPane(BG, VBox, bottom));
        //rootLayout.getChildren().addAll(new StackPane(BG, leftVBox), new StackPane(bottom));

        root.getChildren().addAll(background, rootLayout);

        playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.valueProperty()));
        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.valueProperty().asString()));

        player.valueProperty().addListener((obs, old, score) -> {
            if (score.intValue() >= 21) {
            	resolution();
            }
        });

        dealer.valueProperty().addListener((obs, old, score) -> {
            if (score.intValue() >= 21) {
            	resolution();
            }
        });

        /*""" Defining the Button Actions """*/

        /**
         * Play Starts a new Game
         */
        btnPlay.setOnAction(event -> {
        	startNewGame();
        	btnPlay.setDisable(true);//TODO: This will disable the button
        	});

        /**
         * Hit Draws a new card from deck and calculates score accordingly
         */
        btnHit.setOnAction(event -> {player.hitMe(deck.drawCard()); });

        /**
         * Stay will let the dealers turn go, if under 17 keep hitting. Calculates score also.
         */
        btnStay.setOnAction(event -> {
        	//According to Google, blackjack dealers must hit until they are over 17 so...
            while (dealer.valueProperty().get() < 17) 
            {
                dealer.hitMe(deck.drawCard());
            }
            //This method determines the winner of the game and when it is over
            resolution();
            btnPlay.setDisable(false);
        });
        /**
         * Exit Game
         */
        btnQuit.setOnAction(event -> {System.exit(0);});

        return root;
    }

    public void startNewGame() 
    {
        dealer.resetScore();
        player.resetScore();
        deck.shuffle();
        dealer.hitMe(deck.drawCard());
        dealer.hitMe(deck.drawCard());
        player.hitMe(deck.drawCard());
        player.hitMe(deck.drawCard());
        message.setText("");
    }

    public void resolution() {

        int dScore = dealer.valueProperty().get();
        int pScore = player.valueProperty().get();
        String winner = "Exceptional case: d: " + dScore + " p: " + pScore;

        //checking order since dealer wins when tie
        if (dScore == 21 || pScore > 21 || dScore == pScore
                || (dScore < 21 && dScore > pScore)) {
            winner = "Dealer";
        }
        else if (pScore == 21 || dScore > 21 || pScore > dScore) {
            winner = "You";
        }
       
        message.setText(winner + " Win");
    }

    @Override
    public void start(Stage one) throws Exception {
    	one.setScene(new Scene(makeGame()));
    	one.setWidth(900);
    	one.setHeight(640);
    	one.setResizable(false);
    	one.setTitle("BlackJack -- Courtesy of Group 10 (ASU SER215)");
    	one.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

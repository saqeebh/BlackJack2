package bj2;

import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Card extends Parent 
{
	/**
	 * Using Enums as the properties of cards
	 * @author WHOPPER
	 */
    enum Rank 
    {
        TWO(2), THREE(3), FoUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), 
        NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        final int value;
        private Rank(int value) 
        {
        	this.value = value;
        }
    };
    enum Suit { HEARTS, DIAMONDS, CLUBS, SPADES };

    public final Suit suit;
    public final Rank rank;
    public final int value;

    public Card(Suit suit, Rank rank) 
    {
    	//Creating the properties of the card
        this.suit = suit;
        this.rank = rank;
        this.value = rank.value;

        //Draw the card using javafx packages
        Rectangle card = new Rectangle(80, 100);
        card.setFill(Color.SNOW);

        //Using pictures is complicated/messy so instead opting to create fake cards using text
        Text card_face = new Text(this.toString());
        
        //Adding the card to the scene
        getChildren().add(new StackPane(card, card_face));
    }

    //This is what will appear over the cards in the GUI
    @Override
    public String toString() 
    {
        return rank + "\n of \n" + suit;
    }
}
package bj2;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import bj2.Card.Rank;


public class Hand {

	//Using an observablelist b/c easier to track changes
    private ObservableList<Node> cards;
    private SimpleIntegerProperty value = new SimpleIntegerProperty(0);

    private int aces = 0;
    
    //Default Constructor
    public Hand(ObservableList<Node> cards) 
    {
        this.cards = cards;
    }

    /**This method takes a card as a parameter and will calculate the 
     * new Score of the Player/Dealer accordingly
     * @param card
     */
    public void hitMe(Card card) 
    {
    	//Add the card do the hand
        cards.add(card);

        //Need to keep track of aces since they affect how score calculated
        if (card.rank == Rank.ACE)
            aces++;

        if (value.get()+card.value>21 && aces>0) 
        {
            value.set(value.get() + card.value - 10);//then ace=1, !=11
            aces-=1;
        }
        else
            value.set(value.get() + card.value);
    }
    /**
     * Reinitialize all the scores and values to 0 so the game can restart
     */
    public void resetScore() 
    {
        value.set(0);
        aces = 0;
        cards.clear();
    }

    /**
     * Temporary Workaround to get the value as an Int for the cards enum property.
     */
    public SimpleIntegerProperty valueProperty() 
    {
        return value;
    }
}
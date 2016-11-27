package bj2;

import bj2.Card.*;

public class Deck 
{

    private Card[] cards = new Card[52];

    //Default Deck constructor - Only shuffles the cards and creates a deck
    public Deck() 
    {
    	shuffle();
    }

    public final void shuffle() 
    {
    	//Creating a deck of all the cards
        int i = 0;
        //Nested for-loop to go through and initialize the whole deck
        for (Suit s : Suit.values()) 
            for (Rank r : Rank.values()) 
                cards[i++] = new Card(s, r);
    }

    /**
     * Draw a random card from the created deck, updating the 
     * Deck Accordingly to remove it from the list.
     * @returns a Card
     */
    public Card drawCard() 
    {
        Card card = null;
        while (card == null) 
        {
            int index = (int)(Math.random()*cards.length);
            card = cards[index];
            cards[index] = null;
        }
        return card;
    }
//    public Card drawCard() 
//    {
//        Card card = null;
//        card = cards[(int)(Math.random()*cards.length)];
//        return card;
//    }
}
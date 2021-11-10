package pokerspel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

    private Random r;

    private ArrayList<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        this.r = new Random();
        for (Card.Suite s : Card.Suite.values()) {
            for (Card.Rank r : Card.Rank.values()) {
                this.cards.add(new Card(s, r));
            }
        }
        Collections.shuffle(this.cards);
    }

    public void showCards() {
        for (Card c : this.cards) {
            System.out.println(c);
        }
    }

    public Card drawCard() {
        Card c = cards.remove(0);
        return c;
    }

}

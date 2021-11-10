package pokerspel;

import java.util.ArrayList;
import java.util.Collections;

public class PokerHand {

    private String name;
    private int rank;
    private ArrayList<Card> combination;
    private ArrayList<Card> remainingCards;

    public PokerHand(String name) {
        this.name = name;
        this.combination = new ArrayList<>();
        this.remainingCards = new ArrayList<>();
    }

    public Card getMostFrequentCard() {
        int highestFrequency = 0;
        Card highestFrequencyCard = null;
        for (int i = 0; i < this.combination.size(); i++) {
            if (Collections.frequency(this.combination, this.combination.get(i)) > highestFrequency) {
                highestFrequency = Collections.frequency(this.combination, this.combination.get(i));
                highestFrequencyCard = this.combination.get(i);
            }
        }
        return highestFrequencyCard;
    }

    public ArrayList<Card> getCombination() {
        return this.combination;
    }

    public ArrayList<Card> getRemainingCards() {
        return this.remainingCards;
    }

    public void setCombination(ArrayList<Card> cards) {
        this.combination.clear();
        this.combination.addAll(cards);
    }

    public void setRemainingCards(ArrayList<Card> cards) {
        this.remainingCards.clear();
        this.remainingCards.addAll(cards);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }
}

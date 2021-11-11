package pokerspel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Player {

    private String name;
    private ArrayList<Card> hand;
    private double money;
    private int betAmount;
    private PokerHand pokerHand;

    public Player(String name) {
        this.name = name;
        this.money = 1000.0;
        this.betAmount = 0;
        this.hand = new ArrayList<>();
        this.pokerHand = new PokerHand("");
    }

    public void giveCard(Card card) {
        this.hand.add(card);
    }

    public void removeCard(Card card) {
        this.hand.remove(card);
    }

    public void showHand() {
        for (Card c : this.hand) {
            System.out.println(c);
        }
    }

    public int chooseBetAmount(int min) {
        int choice = Dialogs.getIntInput(name + ", choose bet amount (" + min + "-" + (int)money + ")");
        betAmount = choice;
        money -= choice;
        return choice;
    }

    public void call(int amount) {
        money -= amount;
        betAmount += amount;
    }

    public void addWinnings(double amount) {
        money += amount;
    }

    public int countCardValues() {
        int sum = 0;
        for (Card c : hand) {
            sum += c.getRank().getRank();
        }
        return sum;
    }

    public void evaluateHand() {
        ArrayList<Card> sortedCards = new ArrayList<>(this.hand);
        sortedCards.sort(new Comparator<Card>() {
            @Override
            public int compare(Card c1, Card c2) {
                return Integer.compare(c2.getRank().getRank(), c1.getRank().getRank());
            }
        });
        if (isFlush(sortedCards) && isStraight(sortedCards)) {
            pokerHand.setName("Straight flush");
            pokerHand.setCombination(sortedCards);
            pokerHand.setRank(8);
        } else if (isFlush(sortedCards)) {
            pokerHand.setName("Flush");
            pokerHand.setCombination(sortedCards);
            pokerHand.setRank(5);
        } else if (isStraight(sortedCards)) {
            pokerHand.setName("Straight");
            pokerHand.setCombination(sortedCards);
            pokerHand.setRank(4);
        } else {
            isOther(sortedCards);
        }
    }

    private boolean isStraight(ArrayList<Card> cards) {
        int match = 0;
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getRank().getRank() == cards.get(i-1).getRank().getRank()+1) {
                match += 1;
            }
        }
        if (match == 4) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isFlush(ArrayList<Card> cards) {
        int match = 0;
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getSuite().getName().equals(cards.get(i - 1).getSuite().getName())) {
                match += 1;
            }
        }
        if (match == 4) {
            return true;
        } else {
            return false;
        }
    }

    private void isOther(ArrayList<Card> cards) {
        int[] matches = new int[cards.size()];
        ArrayList<Integer> cardRanks = new ArrayList<>();
        for (Card c : cards) {
            cardRanks.add(c.getRank().getRank());
        }
        for (int i = 0; i < cardRanks.size(); i++) {
            matches[i] = Collections.frequency(cardRanks, cardRanks.get(i));
        }
        int matchTwo = 0;
        int matchThree = 0;
        int matchFour = 0;

        for (int i : matches) {
            if (i == 2) {
                matchTwo++;
            } else if (i == 3) {
                matchThree++;
            } else if (i==4) {
                matchFour++;
            }
        }

        for (int i = 0; i < cards.size(); i++) {
            if (i == 0) {
                pokerHand.getRemainingCards().add(cards.get(i));
                continue;
            }
            if (cards.get(i).getRank().getRank() == cards.get(i-1).getRank().getRank()) {
                pokerHand.getCombination().add(cards.get(i-1));
                pokerHand.getCombination().add(cards.get(i));
                pokerHand.getRemainingCards().remove(cards.get(i-1));
            } else {
                pokerHand.getRemainingCards().add(cards.get(i));
            }
        }

        if (matchFour == 4) {
            pokerHand.setName("Four of a kind");
            pokerHand.setRank(7);
        } else if (matchThree == 3 && matchTwo == 2) {
            pokerHand.setName("Full house");
            pokerHand.setRank(6);
        } else if (matchThree == 3) {
            pokerHand.setName("Three of a kind");
            pokerHand.setRank(3);
        } else if (matchTwo == 4) {
            pokerHand.setName("Two pairs");
            pokerHand.setRank(2);
        } else if (matchTwo == 2) {
            pokerHand.setName("Pair");
            pokerHand.setRank(1);
        } else {
            pokerHand.setName("High card");
            pokerHand.setRank(0);
        }
    }

    public void clearPokerHand() {
        this.pokerHand = new PokerHand("");
    }

    public String getName() {
        return name;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public PokerHand getPokerHand() {
        return this.pokerHand;
    }

    public double getMoney() {
        return this.money;
    }
}

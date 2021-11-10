package pokerspel;

public class Card {

    /*Skapa en Card klass med två enums:

    Suite (Hearts, Spades, Clubs, Diamonds)
    Rank (Two, … King, Ace)
    En konstruktor som tar in dessa värden och skapar kortet.*/

    enum Suite {
        HEARTS("Hearts"), SPADES("Spades"), CLUBS("Clubs"), DIAMONDS("Diamonds");

        private final String name;
        Suite(String suiteName) {
          this.name = suiteName;
        }
        public String getName() {
            return this.name;
        }
    }

    enum Rank {
        TWO(2, "Two"), THREE(3, "Three"), FOUR(4, "Four"), FIVE(5, "Five"),
        SIX(6, "Six"), SEVEN(7, "Seven"), EIGHT(8, "Eight"), NINE(9, "Nine"),
        TEN(10, "Ten"), JACK(11, "Jack"), QUEEN(12, "Queen"), KING(13, "King"),
        ACE(14, "Ace");

        private final int rank;
        private final String name;

        Rank(int rank, String name) {
            this.rank = rank;
            this.name = name;
        }
        public int getRank(){
            return this.rank;
        }
        public String getName(){
            return this.name;
        }
    }

    private Suite suite;
    private Rank rank;

    public Card(Suite suite, Rank rank) {
        this.suite = suite;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return rank.getName() + " of " + suite.getName();
    }

    public Suite getSuite() {
        return suite;
    }

    public Rank getRank() {
        return rank;
    }
}

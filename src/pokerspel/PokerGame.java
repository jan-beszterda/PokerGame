package pokerspel;

import java.util.*;

public class PokerGame {

    private Deck deck;
    private LinkedList<Player> playerQueue;
    private Scanner scanner;
    private int pot;

    public PokerGame() {
        this.pot = 0;
        this.scanner = new Scanner(System.in);
        this.deck = new Deck();
        this.playerQueue = new LinkedList<>();
        initialisePlayers();
        showMenu();
    }

    private void initialisePlayers() {
        int numberOfPlayers;
        while (true) {
            System.out.print("Input number of players: ");
            try {
                numberOfPlayers = Integer.parseInt(scanner.nextLine());
                if (numberOfPlayers > 1 && numberOfPlayers < 5) {
                    break;
                }
            } catch (Exception ignore) {
                System.out.println("Wrong input!");
            }
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.print("Input player's name: ");
            String name = scanner.nextLine();
            playerQueue.add(new Player(name));
        }
    }

    private void showMenu() {
        int choice = Dialogs.getIntInput("Deal cards", "Place bets");
        switch (choice) {
            case 1:
                dealCards();
                newEvaluateGame(0);
                break;
            case 2:
                dealCards();
                pot = placeBets();
                if (playerQueue.size() > 1) {
                    changeCards();
                    placeBets();
                }
                newEvaluateGame(pot);
                break;
        }
    }

    private int placeBets() {
        for (Player p : playerQueue) {
            p.setBetAmount(0);
        }
        int currentBet = 0;
        int equalised = 0;
        boolean control = true;
        while (playerQueue.size() > 1 && control) {
            Player p = playerQueue.pollFirst();
            int decision;
            System.out.println("-".repeat(20));
            System.out.println(p.getName() + ", your turn!");
            System.out.println("-".repeat(20));
            System.out.println("Your hand:\n");
            p.showHand();
            if (currentBet == 0) {
                decision = Dialogs.getIntInput("Place bet", "Fold");
                switch (decision) {
                    case 1:
                        int betAmount = p.chooseBetAmount(1);
                        pot += betAmount;
                        currentBet = betAmount;
                        playerQueue.offerLast(p);
                        break;
                    case 2:
                        break;
                }
            } else {
                int call = currentBet-p.getBetAmount();
                if (call == 0) {
                    decision = Dialogs.getIntInput("Check", "Raise", "Fold");
                    switch (decision) {
                        case 1:
                            System.out.println("You checked.");
                            System.out.println("-".repeat(20));
                            //pot += call;
                            //p.call(call);
                            playerQueue.offerLast(p);
                            break;
                        case 2:
                            int betAmount = p.chooseBetAmount(currentBet*2);
                            pot += betAmount;
                            currentBet = betAmount;
                            p.setBetAmount(betAmount);
                            playerQueue.offerLast(p);
                            break;
                        case 3:
                            break;
                    }
                } else {
                    decision = Dialogs.getIntInput("Call " + call, "Raise", "Fold");
                    switch (decision) {
                        case 1:
                            System.out.println("You called.");
                            System.out.println("-".repeat(20));
                            pot += call;
                            p.call(call);
                            playerQueue.offerLast(p);
                            break;
                        case 2:
                            int betAmount = p.chooseBetAmount(currentBet*2);
                            pot += betAmount;
                            currentBet = betAmount;
                            p.setBetAmount(betAmount);
                            playerQueue.offerLast(p);
                            break;
                        case 3:
                            break;
                    }
                }
            }
            for (Player player : playerQueue) {
                if (player.getBetAmount() == currentBet) {
                    equalised++;
                } else {
                    equalised--;
                }
            }
            if (equalised == playerQueue.size()) {
                control = false;
            }
        }
        return pot;
    }

    private void dealCards() {
        for (Player p : playerQueue) {
            for (int i = 0; i < 5; i++) {
                Card c = deck.drawCard();
                p.giveCard(c);
            }
        }
    }

    private void changeCards() {
        for (Player player : playerQueue) {
            ArrayList<String> cards = new ArrayList<>();
            for (Card c : player.getHand()) {
                cards.add(c.toString());
            }
            ArrayList<Card> toRemove = new ArrayList<>();
            int[] choices = Dialogs.getIntArrayInput(cards.toArray(new String[0]));
            for (int i : choices) {
                if (i == 0) {
                    continue;
                } else {
                    toRemove.add(player.getHand().get(i-1));
                }
            }
            for (Card c : toRemove) {
                player.removeCard(c);
                player.giveCard(deck.drawCard());
            }
        }
    }

    private void newEvaluateGame(int pot) {
        for (Player p : playerQueue) {
            p.evaluateHand();
        }
        if (playerQueue.size() > 1) {
            for (Player p : playerQueue) {
                System.out.print(p.getName() + " - ");
                for (int i = 0; i < p.getHand().size(); i++) {
                    System.out.print(p.getHand().get(i).toString());
                    if (i != p.getHand().size()-1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
            LinkedList<Player> winners;
            winners = evaluateWinners(playerQueue);
            System.out.println("-".repeat(20));
            if (winners.size() > 1) {
                System.out.println("Draw: ");
                double winnings = (double) pot / winners.size();
                for (Player winner : winners) {
                    System.out.println(winner.getName() + " - " + winner.getPokerHand().getName());
                }
                System.out.println("You get " + winnings + " each");
            } else {
                System.out.println(winners.get(0).getName() + " - " + winners.get(0).getPokerHand().getName() +
                        "\nYou won! You got " + pot);
            }
        } else {
            System.out.println(playerQueue.get(0).getName() + ", you won! You got " + pot);
        }
    }

    private void evaluateGame(int pot) {
        ArrayList<Integer> intResults = new ArrayList<>();
        ArrayList<String> stringResults = new ArrayList<>();
        for (int i = 0; i < playerQueue.size(); i++) {
            //intResults.add(playerQueue.get(i).evaluateHand());
            stringResults.add(evaluateResult(intResults.get(i)));
        }
        boolean cardHandExists = false;
        for (int i : intResults) {
            if (i != 0) {
                cardHandExists = true;
                break;
            }
        }
        if (playerQueue.size() > 1) {
            if (!cardHandExists ) {
                intResults.clear();
                for (Player p : playerQueue) {

                    intResults.add(p.countCardValues());
                    System.out.println(p.getName() + " - " + intResults.get(playerQueue.indexOf(p)));
                }
            } else {
                for (Player p : playerQueue) {
                    System.out.println(p.getName() + " - " + stringResults.get(playerQueue.indexOf(p)));
                }
            }
        }
        int index = -1;
        int lowest = Integer.MIN_VALUE;
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < intResults.size(); i++) {
            if (intResults.get(i) > lowest) {
                indices.clear();
                indices.add(i);
                lowest = intResults.get(i);
            } else if (intResults.get(i) == lowest) {
                indices.add(i);
                lowest = intResults.get(i);
            }
        }
        System.out.println("-".repeat(20));
        if (indices.size() > 1) {
            System.out.println("Draw: ");
            double winnings = (double) pot / indices.size();
            for (int i : indices) {
                System.out.println(playerQueue.get(i).getName());
            }
            System.out.println("You get " + winnings + " each");
        } else {
            System.out.println(playerQueue.get(indices.get(0)).getName() + ", you won! You got " + pot);
        }
    }
    private String evaluateResult(int result) {
        String resultText = "";
        switch (result) {
            case 8:
                resultText = "Straight flush";
                break;
            case 7:
                resultText = "Four of a kind";
                break;
            case 6:
                resultText = "Full house";
                break;
            case 5:
                resultText = "Flush";
                break;
            case 4:
                resultText = "Straight";
                break;
            case 3:
                resultText = "Three of a kind";
                break;
            case 2:
                resultText = "Two pair";
                break;
            case 1:
                resultText = "Pair";
                break;
            default:
                resultText = "Nothing";
                break;
        }
        return resultText;
    }

    private LinkedList<Player> evaluateWinners(LinkedList<Player> players) {
        LinkedList<Player> winners = new LinkedList<>();
        int highest = 0;
        for (Player p : players) {
            if (p.getPokerHand().getRank() >= highest) {
                winners.add(p);
                highest = p.getPokerHand().getRank();
            }
        }
        if (winners.size() < 2) {
            return winners;
        }
        winners = compareWinningHands(winners);
        return winners;
    }

    private LinkedList<Player> compareWinningHands(LinkedList<Player> winners) {
        switch (winners.get(0).getPokerHand().getRank()) {
            case 8:
            case 4:
            case 7:
            case 3:
                winners = compareByHighestCard(winners);
                break;
            case 6:
                winners = compareByRankWithFrequency(winners);
                break;
            case 5:
                winners = compareByAllCards(winners, true);
                break;
            case 2:
                winners = compareByAllCards(winners, true);
                if (winners.size() > 1) {
                    winners = compareByAllCards(winners, false);
                }
                break;
            case 1:
                winners = compareByHighestCard(winners);
                if (winners.size() > 1) {
                    winners = compareByAllCards(winners, false);
                }
                break;
            case 0:
                winners = compareByAllCards(winners, false);
                break;
        }
        return winners;
    }
    private LinkedList<Player> compareByRankWithFrequency(LinkedList<Player> winners) {
        ArrayList<Card> cardsWithHighestFrequency = new ArrayList<>();
        for (Player p : winners) {
            cardsWithHighestFrequency.add(p.getPokerHand().getMostFrequentCard());
        }
        LinkedList<Player> winnersToKeep = new LinkedList<>();
        int highest = 0;
        for (int i = 0; i < cardsWithHighestFrequency.size(); i++) {
            if (cardsWithHighestFrequency.get(i).getRank().getRank() > highest) {
                winnersToKeep.clear();
                winnersToKeep.add(winners.get(i));
                highest = cardsWithHighestFrequency.get(i).getRank().getRank();
            }
        }
        return winnersToKeep;
    }

    private LinkedList<Player> compareByHighestCard(LinkedList<Player> winners) {
        LinkedList<Player> winnersToKeep = new LinkedList<>();
        int highest = 0;
        for (int i = 0; i < winners.size(); i++) {
            if (winners.get(i).getPokerHand().getCombination().get(0).getRank().getRank() > highest) {
                winnersToKeep.clear();
                winnersToKeep.add(winners.get(i));
                highest = winners.get(i).getPokerHand().getCombination().get(0).getRank().getRank();
            } else if (winners.get(i).getPokerHand().getCombination().get(0).getRank().getRank() == highest) {
                winnersToKeep.add(winners.get(i));
            }
        }
        return winnersToKeep;
    }

    private LinkedList<Player> compareByAllCards(LinkedList<Player> winners, boolean combination) {
        LinkedList<Player> winnersToKeep = new LinkedList<>(winners);
        int highest;
        if (combination) {
            for (int i = 0; i < winners.get(0).getPokerHand().getCombination().size(); i++) {
                highest = 0;
                for (int j = 0; j < winners.size(); j++) {
                    if (!winnersToKeep.contains(winners.get(j))){
                        continue;
                    }
                    if (j != 0) {
                        if (winners.get(j).getPokerHand().getCombination().get(i).getRank().getRank() > highest) {
                            winnersToKeep.remove(winners.get(j-1));
                            highest = winners.get(j).getPokerHand().getCombination().get(i).getRank().getRank();
                        } else if (winners.get(j).getPokerHand().getCombination().get(i).getRank().getRank() < highest) {
                            winnersToKeep.remove(winners.get(j));
                        }
                    } else {
                        if (winners.get(j).getPokerHand().getCombination().get(i).getRank().getRank() >= highest) {
                            highest = winners.get(j).getPokerHand().getCombination().get(i).getRank().getRank();
                        } else {
                            winnersToKeep.remove(winners.get(j));
                        }
                    }
                }
                if (winnersToKeep.size() < 2) {
                    break;
                }
            }
        } else {
            for (int i = 0; i < winners.get(0).getPokerHand().getRemainingCards().size(); i++) {
                highest = 0;
                for (int j = 0; j < winners.size(); j++) {
                    if (!winnersToKeep.contains(winners.get(j))){
                        continue;
                    }
                    if (j != 0) {
                        if (winners.get(j).getPokerHand().getRemainingCards().get(i).getRank().getRank() > highest) {
                            winnersToKeep.remove(winners.get(j-1));
                            highest = winners.get(j).getPokerHand().getRemainingCards().get(i).getRank().getRank();
                        } else if (winners.get(j).getPokerHand().getRemainingCards().get(i).getRank().getRank() < highest) {
                            winnersToKeep.remove(winners.get(j));
                        }
                    } else {
                        if (winners.get(j).getPokerHand().getRemainingCards().get(i).getRank().getRank() >= highest) {
                            highest = winners.get(j).getPokerHand().getRemainingCards().get(i).getRank().getRank();
                        } else {
                            winnersToKeep.remove(winners.get(j));
                        }
                    }
                }
                if (winnersToKeep.size() < 2) {
                    break;
                }
            }
        }
        return winnersToKeep;
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DouDiZhu {

    public static void main(String[] args) {
        DouDiZhu game = new DouDiZhu();
        game.startGame();
    }

    private List<Card> deck;
    private List<Player> players;

    public DouDiZhu() {
        deck = new ArrayList<>();
        players = new ArrayList<>();
        initializeDeck();
        initializePlayers();
    }

    private void initializeDeck() {
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        for (String suit : suits) {
            for (int rank = 2; rank <= 14; rank++) {
                deck.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(deck);
    }

    private void initializePlayers() {
        for (int i = 1; i <= 3; i++) {
            players.add(new Player("Player " + i));
        }
    }

    public void startGame() {
        System.out.println("斗地主游戏开始！");
        dealCards();
        showHands();
    }

    private void dealCards() {
        for (int i = 0; i < 51; i++) {
            players.get(i % 3).addCard(deck.remove(0));
        }
        // 发底牌
        for (Player player : players) {
            if (!deck.isEmpty()) {
                player.addCard(deck.remove(0));
            }
        }
    }

    private void showHands() {
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getHand());
        }
    }
}

class Card {
    private String suit;
    private int rank;

    public Card(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return suit + " " + rank;
    }
}

class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }
}

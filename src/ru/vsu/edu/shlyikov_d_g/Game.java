package ru.vsu.edu.shlyikov_d_g;

import java.util.LinkedList;
import java.util.Queue;

public class Game {

    private Card cur1P;
    private Card cur2P;

    private GameMode gameMode = GameMode.GAME_MENU;

    private SimpleLinkedListQueue<Card> q1P = new SimpleLinkedListQueue<>();
    private SimpleLinkedListQueue<Card> q2P = new SimpleLinkedListQueue<>();

    private SimpleLinkedListQueue<Card> cardsInConflictSLLQ = new SimpleLinkedListQueue<>();
    private Queue<Card> cardsInConflictQ = new LinkedList<>();

    private Queue<Card> qu1P = new LinkedList<Card>();
    private Queue<Card> qu2P = new LinkedList<Card>();

    String spades = "spades";
    String hearts = "hearts";
    String clubs = "clubs";
    String diamonds = "diamonds";

    Card[] spadesCards = new Card[9];
    Card[] heartsCards = new Card[9];
    Card[] clubsCards = new Card[9];
    Card[] diamondsCards = new Card[9];

    Card[] allCards = new Card[36];

    String[] values = new String[]{"6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    public Game() {
        createGame();
    }

    public void pariWin(int index) {

        if (index == 1) {
            if (gameMode == GameMode.GAME_FIRST) {
                q1P.add(cur1P);
                q1P.add(cur2P);
                if (!cardsInConflictSLLQ.empty()) {
                    q1P.add(cardsInConflictSLLQ);
                }
            } else {
                qu1P.add(cur1P);
                qu1P.add(cur2P);
                while (cardsInConflictQ.size() != 0) {
                    qu1P.add(cardsInConflictQ.element());
                    cardsInConflictQ.remove();
                }
            }
        }
        else {
            if (gameMode == GameMode.GAME_FIRST) {
                q2P.add(cur1P);
                q2P.add(cur2P);
                if (!cardsInConflictSLLQ.empty()) {
                    q2P.add(cardsInConflictSLLQ);
                }
            } else if (gameMode == GameMode.GAME_SECOND) {
                qu2P.add(cur1P);
                qu2P.add(cur2P);
                while (cardsInConflictQ.size() != 0) {
                    qu2P.add(cardsInConflictQ.element());
                    cardsInConflictQ.remove();
                }
            }
        }
    }

    public void cur(){
        if (gameMode == GameMode.GAME_FIRST) {
            try {
                cur1P = q1P.element();
                cur2P = q2P.element();
            } catch (Exception ignored) {
            }
        }
        else if (gameMode == GameMode.GAME_SECOND){
            if (!(qu1P.size() == 0 || qu2P.size() == 0)) {
                cur1P = qu1P.element();
                cur2P = qu2P.element();
            }
        }
    }

    public void pariConflict(){
        if (gameMode == GameMode.GAME_FIRST) {
            cardsInConflictSLLQ.add(cur1P);
            cardsInConflictSLLQ.add(cur2P);
        }
        else if(gameMode == GameMode.GAME_SECOND) {
            cardsInConflictQ.add(cur1P);
            cardsInConflictQ.add(cur2P);
        }
    }



    public String cardsInConflict(){
        return "Cards in conflict: " +
                (gameMode == GameMode.GAME_FIRST ? SimpleLinkedListQueue.watchQueue(cardsInConflictSLLQ)
                        : gameMode == GameMode.GAME_SECOND ?  SimpleLinkedListQueue.watchQueue(cardsInConflictQ) : "");
    }

    public String countText (int index){
        if (index == 1) {
            return "Cards count: " + (gameMode == GameMode.GAME_FIRST ? q1P.count() : gameMode == GameMode.GAME_SECOND ? qu1P.size() : "");
        }
        else{
            return "Cards count: " + (gameMode == GameMode.GAME_FIRST ? q2P.count() : gameMode == GameMode.GAME_SECOND ? qu2P.size() : "");
        }
    }

    public GameMode getGameMode(){
        return gameMode;
    }

    public int compare(){
        return cur1P.compareTo(cur2P);
    }

    public String getCur1P(){
        return cur1P.toString();
    }

    public String getCur2P(){
        return cur2P.toString();
    }

    public void firstGame(){
        gameMode = GameMode.GAME_FIRST;
        try {
            cur1P = q1P.element();
            q1P.removeLast();
            cur2P = q2P.element();
            q2P.removeLast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void secondGame(){
        gameMode = GameMode.GAME_SECOND;
        cur1P = qu1P.element();
        qu1P.remove();
        cur2P = qu2P.element();
        qu2P.remove();
    }

    public boolean qIsEmpty(int index){
        if (index == 1){
            return gameMode == GameMode.GAME_FIRST ? q1P.empty() : qu1P.size() == 0;
        }
        else{
            return gameMode == GameMode.GAME_FIRST ? q2P.empty() : qu2P.size() == 0;
        }
    }

    public void removeLast(){
        if (gameMode == GameMode.GAME_FIRST) {
            try {
                q1P.removeLast();
                q2P.removeLast();
            } catch (SimpleLinkedList.SimpleLinkedListException e) {
                e.printStackTrace();
            }
        }
        else{
            qu1P.remove();
            qu2P.remove();
        }
    }

    public String watchQueue(int index) {
        if (index == 1) {
            return SimpleLinkedListQueue.watchQueue(q1P);
        }
        else{
            return SimpleLinkedListQueue.watchQueue(q2P);
        }
    }

    public void setGameMode(GameMode gm){
        gameMode = gm;
    }

    public void createGame() {
        for (int i = 0; i < values.length; i++) {
            spadesCards[i] = new Card(values[i], spades);
            heartsCards[i] = new Card(values[i], hearts);
            clubsCards[i] = new Card(values[i], clubs);
            diamondsCards[i] = new Card(values[i], diamonds);

            allCards[4 * i] = spadesCards[i];
            allCards[4 * i + 1] = heartsCards[i];
            allCards[4 * i + 2] = clubsCards[i];
            allCards[4 * i + 3] = diamondsCards[i];
        }

        for (int i = 0; i < allCards.length; i += 4) {
            System.out.printf("%s %s %s %s\n", allCards[i], allCards[i + 1], allCards[i + 2], allCards[i + 3]);
        }
        System.out.println();

        Card.shake(allCards);

        for (int i = 0; i < allCards.length; i += 4) {
            System.out.printf("%s %s %s %s\n", allCards[i], allCards[i + 1], allCards[i + 2], allCards[i + 3]);
            q1P.add(allCards[i]);
            q2P.add(allCards[i + 1]);
            q1P.add(allCards[i + 2]);
            q2P.add(allCards[i + 3]);
            qu1P.add(allCards[i]);
            qu2P.add(allCards[i + 1]);
            qu1P.add(allCards[i + 2]);
            qu2P.add(allCards[i + 3]);
        }
//        qu1P.add(new Card("A", "hearts"));
//        qu2P.add(new Card("6", "hearts"));
//        q1P.add(new Card("A", "hearts"));
//        q2P.add(new Card("6", "hearts"));
        System.out.println();
    }
}

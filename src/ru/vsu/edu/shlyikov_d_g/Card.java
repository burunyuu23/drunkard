package ru.vsu.edu.shlyikov_d_g;

import java.util.*;

public class Card implements Comparable<Card> {

    private String suit;
    private String value;

    public Card(String value, String suit){
        this.suit = suit;
        this.value = value;
    }

    public static void shake(Card[] cards){
        List<Card> list = Arrays.asList(cards);
        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {
            cards[i] = list.get(i);
        }
    }

    //                      ♠     ♥      ♣      ♦
    //       Масти карт - пики, червы, трефы, бубны.
    //        String spades = "spades";
    //        String hearts = "hearts";
    //        String clubs = "clubs";
    //        String diamonds = "diamonds";

    private String getSuitSymbol(){
        return  this.suit.equals("spades") ? "♠" :
                this.suit.equals("hearts") ? "♥" :
                this.suit.equals("clubs") ? "♣" :
                this.suit.equals("diamonds") ? "♦" : "";
    }

    private static String[] values = new String[]{"6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    @Override
    public int compareTo(Card c2){
        String str1 = null;
        String str2 = null;
        for (String str : values) {
            if (Objects.equals(str, this.value)){
                str1 = str;
            }
            if (Objects.equals(str, c2.value)){
                str2 = str;
            }

            if (str1 != null && str2 == null){
                return -1;
            }
            else if (str1 != null){
                return 0;
            }
            else if (str2 != null){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return value + getSuitSymbol();
    }
}

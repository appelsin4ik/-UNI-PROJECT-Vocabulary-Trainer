package project;

import java.util.Arrays;
import java.util.ArrayList;

public class WeightingAlgorithm {
    private ArrayList<Card> cards;

    public WeightingAlgorithm(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void Sort() {
        cards.sort(Card::compareTo);
    }
}

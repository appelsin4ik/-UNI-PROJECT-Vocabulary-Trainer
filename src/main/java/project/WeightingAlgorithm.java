package project;

import java.util.Arrays;

public class WeightingAlgorithm {
    private Card[] cards;

    public WeightingAlgorithm(Card[] cards) {
        this.cards = cards;
    }

    public void Sort() {
        Arrays.sort(cards);
    }
}

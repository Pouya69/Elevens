package com.company.card;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Deck class represents a shuffled deck of cards.
 * It provides several operations including
 *      initialize, shuffle, deal, and check if empty.
 */
public class Deck {

	/**
	 * cards contains all the cards in the deck.
	 */
	private List<Card> cards;

	/**
	 * size is the number of not-yet-dealt cards.
	 * Cards are dealt from the top (highest index) down.
	 * The next card to be dealt is at size - 1.
	 */
	private int size;


	/**
	 * Creates a new <code>Deck</code> instance.<BR>
	 * It pairs each element of ranks with each element of suits,
	 * and produces one of the corresponding card.
	 * @param ranks is an array containing all of the card ranks.
	 * @param suits is an array containing all of the card suits.
	 * @param values is an array containing all of the card point values.
	 */
	public Deck(String[] ranks, String[] suits, int[] values) throws Exception {
		this.cards = new ArrayList<>();
		if (ranks.length != values.length)
			throw new Exception("Ranks and Values' length do not match");
		int indexCard = 0;
		for (String suit : suits) {
			for (int i = 0; i < ranks.length; i++) {
				this.cards.add(new Card(ranks[i], suit, values[i]));
				indexCard++;
			}
		}
		this.shuffle();
		this.size = this.cards.size();
	}


	/**
	 * Determines if this deck is empty (no undealt cards).
	 * @return true if this deck is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Accesses the number of undealt cards in this deck.
	 * @return the number of undealt cards in this deck.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Randomly permute the given collection of cards
	 * and reset the size to represent the entire deck.
	 */
	public void shuffle() {
		List<Card> valuesArrayList = new ArrayList<>(this.cards);

		Random random = new Random();
		int deckLength = this.cards.size();
		Card[] shuffled = new Card[deckLength];

		for(int i = 0; i < this.cards.size(); i++)
		{
			int bruh = random.nextInt(deckLength);
			shuffled[i] = valuesArrayList.get(bruh);
			valuesArrayList.remove(bruh);

			deckLength--;
		}
		this.cards = List.of(shuffled);
	}

	/**
	 * Deals a card from this deck.
	 * @return the card just dealt, or null if all the cards have been
	 *         previously dealt.
	 */
	public Card deal() {
		if (this.size == 0)
			return null;
		Card card = this.cards.get(this.size-1);
		this.size--;
		return card;
	}

	/**
	 * Generates and returns a string representation of this deck.
	 * @return a string representation of this deck.
	 */

	@Override
	public String toString() {
		StringBuilder rtn = new StringBuilder("size = " + size + "\nUndealt cards: \n");

		for (int k = size - 1; k >= 0; k--) {
			rtn.append(cards.get(k));
			if (k != 0) {
				rtn.append(", ");
			}
			if ((size - k) % 2 == 0) {
				// Insert carriage returns so entire deck is visible on console.
				rtn.append("\n");
			}
		}

		rtn.append("\nDealt cards: \n");
		for (int k = cards.size() - 1; k >= size; k--) {
			rtn.append(cards.get(k));
			if (k != size) {
				rtn.append(", ");
			}
			if ((k - cards.size()) % 2 == 0) {
				// Insert carriage returns so entire deck is visible on console.
				rtn.append("\n");
			}
		}

		rtn.append("\n");
		return rtn.toString();
	}

	public boolean containsPlayableValues(List<Card> table) {
		boolean contains = false;
		boolean isJPresent = false;
		boolean isQPresent = false;
		boolean isKPresent = false;
		for (int i = 0; i < table.size(); i++) {
			int cardValue = table.get(i).pointValue();
			if (cardValue == 13)
				isJPresent = true;
			else if (cardValue == 14)
				isQPresent = true;
			else if (cardValue == 15)
				isKPresent = true;
			for (int j = 0; j < table.size(); j++) {
				if (j == i)
					continue;
				if (table.get(j).pointValue() + cardValue == 11 || (isJPresent && isQPresent && isKPresent)) {
					contains = true;
					break;
				}
			}
			if (contains)
				break;
		}
		return contains;
	}
}

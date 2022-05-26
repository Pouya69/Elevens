package com.company.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This is a class that tests the Deck class.
 */
public class DeckTester {

	/**
	 * The main method in this class checks the Deck operations for consistency.
	 *	@param args is not used.
	 */
	public static void main(String[] args) throws Exception {
		String[] ranks = {"jack", "queen", "king"};
		String[] suits = {"blue", "red"};
		int[] pointValues = {11, 12, 13};
		Deck d = new Deck(ranks, suits, pointValues);

		System.out.println("**** Original Deck Methods ****");
		System.out.println("  toString:\n" + d.toString());
		System.out.println("  isEmpty: " + d.isEmpty());
		System.out.println("  size: " + d.size());
		System.out.println();
		System.out.println();

		System.out.println("**** Deal a Card ****");
		System.out.println("  deal: " + d.deal());
		System.out.println();
		System.out.println();

		System.out.println("**** Deck Methods After 1 Card Dealt ****");
		System.out.println("  toString:\n" + d.toString());
		System.out.println("  isEmpty: " + d.isEmpty());
		System.out.println("  size: " + d.size());
		System.out.println();
		System.out.println();

		System.out.println("**** Deal Remaining 5 Cards ****");
		for (int i = 0; i < 5; i++) {
			System.out.println("  deal: " + d.deal());
		}
		System.out.println();
		System.out.println();

		System.out.println("**** Deck Methods After All Cards Dealt ****");
		System.out.println("  toString:\n" + d.toString());
		System.out.println("  isEmpty: " + d.isEmpty());
		System.out.println("  size: " + d.size());
		System.out.println();
		System.out.println();

		System.out.println("**** Deal a Card From Empty Deck ****");
		System.out.println("  deal: " + d.deal());
		System.out.println();
		System.out.println();

		/* *** TO BE COMPLETED IN ACTIVITY 4 *** */
		String[] types = {"Diamond", "Heart", "Spade", "Club"};
		int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 13, 14, 15};
		String[] valueTypes = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Junior", "Queen", "King"};
		Deck mDeck = new Deck(valueTypes, types, values);
		List<Card> table = new ArrayList<>();
		Scanner input = new Scanner(System.in);
		while (table.size() < 9 && !mDeck.isEmpty()) {
			table.add(mDeck.deal());
		}
		if (mDeck.isEmpty() && table.size() == 0) {
			System.out.println("[+] You won!");
			System.exit(0);
		}
		if (!mDeck.containsPlayableValues()) {
			System.out.println("[+] You lost!");
			System.exit(0);
		}
		System.out.println("[*] Enter your cards: ");
		String userInput = input.nextLine().replaceAll(" ", "");
		int[] sCards = Arrays.stream(userInput.split(",")).mapToInt(Integer::parseInt).toArray();
		boolean onlyCheckJQK = false;
		if (!(sCards.length == 3 || sCards.length == 2)) {
			System.out.println("[-] You need to choose 2 cards");
			continue;
		}
		if (sCards.length == 3)
			onlyCheckJQK = true;
		try {
			int jIndex = -1;
			int qIndex = -1;
			int kIndex = -1;
			int card1Index = -1;
			int card2Index = -1;
			int sum = 0;
			for (int cardIndex : sCards) {
				cardIndex--;  // Index and number
				if (cardIndex > table.size()-1)
					throw new Exception("[-] Card index has to be less than equal table length");
				Card card = table.get(cardIndex);
				if (onlyCheckJQK && card.pointValue() < 13)
					throw new Exception("[-] Can only select J, Q, and K when selecting 3 cards");
				if (onlyCheckJQK) {
					if (card.pointValue() == 13)
						jIndex = cardIndex;
					else if (card.pointValue() == 14)
						qIndex = cardIndex;
					else if (card.pointValue() == 15)
						kIndex = cardIndex;
				}
				else {
					sum += card.pointValue();
					if (card1Index == -1)
						card1Index = cardIndex;
					else
						card2Index = cardIndex;
				}
			}
			if (onlyCheckJQK && jIndex != -1 && qIndex != -1 && kIndex != -1) {
				table.remove(jIndex);
				table.remove(qIndex);
				table.remove(kIndex);
			} else {
				if (sum == 11) {
					table.remove(card1Index);
					table.remove(card2Index);
				}
				else {
					throw new Exception("[-] Sum of the selected cards is not 11");
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			continue;
		}

	}
}

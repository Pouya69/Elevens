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
		while (true) {
			while (table.size() < 9 && !mDeck.isEmpty()) {
				table.add(mDeck.deal());
			}
			for (int i = 0; i < table.size(); i++) {
				System.out.print(i+1 + "- " + table.get(i).toString() + ", ");
				System.out.print(i%3==0 ? "\n" : "");
			}
			if (mDeck.isEmpty() && table.size() == 0) {
				System.out.println("[+] You won!");
				break;
			}
			if (!mDeck.containsPlayableValues(table)) {
				System.out.println("\n[-] You lost!");
				break;
			}
			System.out.println("\n\n[*] Enter your cards: ");
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
					if (cardIndex > table.size())
						throw new Exception("[-] Card index has to be less than equal table length");
				}
				for (int cardIndex : sCards) {
					cardIndex--;  // Index and number
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
					Card c1 = table.get(jIndex);
					Card c2 = table.get(jIndex);
					Card c3 = table.get(jIndex);
					table.remove(c1);
					table.remove(c2);
					table.remove(c3);
				} else {
					if (sum == 11) {
						Card c1 = table.get(card1Index);
						Card c2 = table.get(card2Index);
						table.remove(c1);
						table.remove(c2);
					}
					else
						throw new Exception("[-] Sum of the selected cards is not 11");
				}
			} catch (Exception e) {
				System.out.println("[-] " + e.getMessage());
			}
		}
	}
}

package view;

import java.util.Scanner;

import model.GameEngineCallbackImpl;
import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class TextClient {

	private static Scanner scanner;

	public static void main(String args[]) {
		scanner = new Scanner(System.in);
		final GameEngine gameEngine = new GameEngineImpl();
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
		int input;
		String id, name;
		int points, bet;
		do {
			printMenu();
			input = scanner.nextInt();
			switch (input) {
				case 1:
					System.out.print("Enter ID: ");
					id = scanner.next();
					System.out.print("Enter Name: ");
					name = scanner.next();
					System.out.print("Enter Points: ");
					points = scanner.nextInt();
					gameEngine.addPlayer(new SimplePlayer(id, name, points));
					break;
				case 2:
					System.out.print("Enter ID: ");
					id = scanner.next();
					gameEngine.removePlayer(gameEngine.getPlayer(id));
					break;
				case 3:
					System.out.print("Enter ID: ");
					id = scanner.next();
					System.out.print("Enter bet: ");
					bet = scanner.nextInt();
					gameEngine.placeBet(gameEngine.getPlayer(id), bet);
					break;
				case 4:
					for (Player p : gameEngine.getAllPlayers()) {
						gameEngine.dealPlayer(p, 10);
					}
					gameEngine.calculateResult();
					break;
			}
			System.out.println("");
		} while (input != 0);
		System.out.println("EXITED");
	}

	public static void printMenu() {
		System.out.println("Menu:");
		System.out.println("1: Add Player");
		System.out.println("2: Remove Player");
		System.out.println("3: Place Bet");
		System.out.println("4: Deal");
		System.out.println("0: Exit");
	}
}
package app;

import java.io.IOException;
import java.util.Scanner;

import model.GameEngineCallbackImpl;
import model.SimplePlayer;
import model.client.GameEngineClientStub;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class NetworkedTextClient {
	private static Scanner scanner;

	public static void main(String args[]) {
		scanner = new Scanner(System.in);
		GameEngine gameEngine = null;
		while (gameEngine == null) {
			System.out.print("Please enter the host: ");
			String host = scanner.next();
			System.out.print("Please enter the port: ");
			int port = scanner.nextInt();
			if (host.equals("0")) {
				host = "127.0.0.1";
			}
			if (port == 0) {
				port = 7234;
			}
			try {
				gameEngine = new GameEngineClientStub(host, port);
			} catch (IOException e) {
				System.err.println("Error could not connect to " + host + ":" + port);
			}
		}
		GameEngineCallback callback = new GameEngineCallbackImpl();
		gameEngine.addGameEngineCallback(callback);
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
			case 5:
				for (Player p : gameEngine.getAllPlayers()) {
					System.out.println(p.toString());
				}
				break;
			case 6:
				System.out.print("Enter ID: ");
				id = scanner.next();
				System.out.println(gameEngine.getPlayer(id));
				break;
			case 7:
				for (PlayingCard card : gameEngine.getShuffledDeck()) {
					System.out.println(card.toString());
				}
				break;
			case 8:
				gameEngine.removeGameEngineCallback(callback);
			}
			System.out.println("");
		} while (input != 0);
		gameEngine.removeGameEngineCallback(callback);
	}

	public static void printMenu() {
		System.out.println("Menu:");
		System.out.println("1: Add Player");
		System.out.println("2: Remove Player");
		System.out.println("3: Place Bet");
		System.out.println("4: Deal");
		System.out.println("5: List All Players");
		System.out.println("6: Get Player by ID");
		System.out.println("7: Get Shuffled Deck");
		System.out.println("8: Remove GEC");
		System.out.println("0: Exit\n");
		System.out.print("Selection: ");
	}
}

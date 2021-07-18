import java.util.Scanner;

public class Game {
	private static Scanner scanner = new Scanner(System.in);
	
	public void mainLoop() {
		while (true) {
			System.out.println("[1] New game");
			System.out.println("[2] High score");
			System.out.println("[3] Exit");
			
			System.out.print("Input: ");
			scanner.reset();
			String input = scanner.nextLine();
			System.out.println();
			
			if (input.equals("1")) {
				chooseDifficulty();
			} else if (input.equals("2")) {
				showHighscoreMenu();
			} else if (input.equals("3")) {
				return;
			}
		}
	}
	
	public void chooseDifficulty() {
		System.out.println("[1] Small map");
		System.out.println("[2] Normal map");
		System.out.println("[3] Large map");
		System.out.println("[4] Custom map");
		System.out.println("[5] Back to main menu");
		System.out.print("Input: ");
		String input = scanner.nextLine();
		System.out.println();
		
		if (input.equals("1")) {
			play(GameMode.getEasyMode());
		} else if (input.equals("2")) {
			play(GameMode.getNormalMode());
		} else if (input.equals("3")) {
			play(GameMode.getHardMode());
		} else if (input.equals("4")) {
			int height = LittleTools.saveIntInput("Height: ", Pitch.min, Pitch.max);
			int width = LittleTools.saveIntInput("Width: ", Pitch.min, Pitch.max);
			int minedPossibility = LittleTools.saveIntInput("Mined Possibility: ", Pitch.minimumMined, Integer.MAX_VALUE);
			play(GameMode.getCustomMode(height, width, minedPossibility));
		} else if (input.equals("5")) {
			return;
		}
	}
	
	public void play(GameMode mode) {
		Scanner scanner = new Scanner(System.in);
		Pitch pitch = new Pitch(mode.getHeight(), mode.getWidth(), mode.getMinedPossibility());
		
		pitch.displayUserInformation();
		
		while (!pitch.isCompletelyExplored() && !pitch.isDestroyed()) {
			pitch.display();
			pitch.getUserInput(scanner);
		}

		// game over
		pitch.display();
		if (pitch.isCompletelyExplored()) {
			System.out.println("You've won the game!");
		} else {
			System.out.println("You stepped on a mine.");
		}
		System.out.println();
	}
	
	public void showHighscoreMenu() {
		System.out.println("This feature is not implemented yet.\n");
	}
}

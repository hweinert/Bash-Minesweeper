import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int height = 10;
		int width = 20;
		int bombs = 16;
		Pitch pitch = new Pitch(height, width, bombs);
		
		pitch.displayUserInformation();
		
		while (!pitch.isCompletelyExplored() && !pitch.isDestroyed()) {
			pitch.display();
			pitch.getUserInput(scanner);
		}

		// game over
		pitch.display();
		if (pitch.isCompletelyExplored()) {
			System.out.println("You have won the game!");
		} else {
			System.out.println("You stepped on a mine");
		}
		scanner.close();
	}
}

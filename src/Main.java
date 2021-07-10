import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Pitch pitch = new Pitch(10, 8);
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

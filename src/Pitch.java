import java.util.Random;
import java.util.Scanner;

public class Pitch {
	private int height;
	private int width;
	private Field[][] fields;
	private int minedFields;
	private int fieldsAllreadyExplored;
	// if a mined field is discovered the pitch is destroyed
	private boolean destroyed;

	public Pitch(int height, int width, int minedPossibility) {
		if (height > 99) height = 99; System.out.println("The maximum height is 99.");
		if (height < 10) height = 10; System.out.println("The minimum height is 10.");
		if (width > 99) width = 99; System.out.println("The maximum width is 99.");
		if (width < 10) width = 10; System.out.println("The minimum width is 10.");
		this.height = height;
		this.width = width;
		
		// generate the pitch
		fields = new Field[height][width];
		Random random = new Random();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (random.nextInt(minedPossibility) + 1 == minedPossibility) {
					fields[i][j] = new Field(true);
					minedFields++;
				} else {
					fields[i][j] = new Field(false);
				}
			}
		}
	}

	public boolean isCompletelyExplored() {
		return height * width - minedFields - fieldsAllreadyExplored == 0;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	// tells the user how to enter data
	public void displayUserInformation() {
		System.out.println("x: line number");
		System.out.println("y: column number\n");
	}

	// returns true if discovered field is mined
	public boolean explore(int x, int y) {
		fieldsAllreadyExplored++;
		fields[x][y].explore();

		if (fields[x][y].isMined()) {
			destroyed = true;
			return true;
		}

		int[][] neighborFields = getNearbyFields(x, y);

		// check how many neighbor fields are mined
		int numberOfMinedNeighborFields = 0;
		for (int i = 0; i < neighborFields.length; i++) {
			if (fields[neighborFields[i][0]][neighborFields[i][1]].isMined()) {
				numberOfMinedNeighborFields++;
			}
		}

		// set appearance and if no neighbor fields are mined discover them too
		if (numberOfMinedNeighborFields == 0) {
			fields[x][y].setAppearance(" ");

			// discover neighbor fields
			for (int i = 0; i < neighborFields.length; i++) {
				if (!fields[neighborFields[i][0]][neighborFields[i][1]].isExplored()) {
					explore(neighborFields[i][0], neighborFields[i][1]);
				}
			}
		} else {
			fields[x][y].setAppearance(String.valueOf(numberOfMinedNeighborFields));
			System.out.print("");
		}

		return false;
	}

	public void mark(int x, int y) {
		fields[x][y].mark();
	}

	public void unmark(int x, int y) {
		fields[x][y].unmark();
	}

	private int[][] getNearbyFields(int x, int y) {
		// determine the coordinates of neighbor fields
		int[][] nearbyCoordinates = new int[][] { 
			{ x - 1, y - 1 }, { x - 1, y }, { x - 1, y + 1 }, 
			{ x,     y - 1 },               { x    , y + 1 }, 
			{ x + 1, y - 1 }, { x + 1, y }, { x + 1, y + 1 } 
		};

		// check how many valid coordinates exist
		int numberOfValidCoordinates = 0;
		int[] indices = new int[8];
		int indicesIndex = 0; // i find it kind of funny
		for (int i = 0; i < 8; i++) {
			if ((nearbyCoordinates[i][0] > -1 
			&& nearbyCoordinates[i][0] < height)
			&& (nearbyCoordinates[i][1] > -1 
			&& nearbyCoordinates[i][1] < width)) {
				numberOfValidCoordinates++;
				indices[indicesIndex] = i;
				indicesIndex++;
			}
		}

		// add the valid coordinates to a list and return it
		int[][] validCoordinates = new int[numberOfValidCoordinates][2];
		for (int i = 0; i < numberOfValidCoordinates; i++) {
			validCoordinates[i] = nearbyCoordinates[indices[i]];
		}
		return validCoordinates;
	}

	// asks the user which field he wants to explore
	public void getUserInput(Scanner scanner) {
		System.out.print("(u)n(m)ark or (e)xplore: ");
		String choice = scanner.nextLine().trim();
		
		if (choice.equalsIgnoreCase("x")) {
			System.exit(0);
		}
		
		if (LittleTools.equalsOneOf(true, choice, "e", "m", "u")) {
			int x = LittleTools.saveIntInput("x: ", 1, height);
			int y = LittleTools.saveIntInput("y: ", 1, width);
			System.out.println();
			if (choice.equalsIgnoreCase("e")) {
				explore(x, y);
			} else if (choice.equalsIgnoreCase("m")) {
				mark(x, y);
			} else if (choice.equalsIgnoreCase("u")) {
				unmark(x, y);
			}
		} else {
			System.out.println("Invalid input. Enter x for exit.\n");
		}
	}

	// displays all fields of the pitch with the indication of the coordinates
	public void display() {
		// display x coordinates
		System.out.print("     ");
		for (int i = 0; i < width; i++) {
			if (i < 10) {
				System.out.print(i + "  ");
			} else {
				System.out.print(i + " ");
			}
		}
		System.out.println("\n");

		// display all lines
		for (int i = 0; i < height; i++) {
			if (i < 10) {
				System.out.print(i + "    ");
			} else {
				System.out.print(i + "   ");
			}

			for (int j = 0; j < width; j++) {
				System.out.print(fields[i][j].getAppearance() + "  ");
			}
			System.out.println("\n");
		}
	}
}
import littlelib.StopWatch;
import littlelib.Tools;
import littlelib.score.HighScoreList;

import java.io.File;
import java.io.FileOutputStream; 
import java.io.ObjectOutputStream;
import java.io.FileInputStream; 
import java.io.ObjectInputStream;
import java.io.IOException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Game {
	private static final String SAVE_PATH = "Minesweeper.data";
	private static Scanner scanner = new Scanner(System.in);
	
	private HighScoreList easyHighScoreList;
	private HighScoreList normalHighScoreList;
	private HighScoreList hardHighScoreList;
	
	public Game() {
		File save = new File(SAVE_PATH);
		if (save.exists()) {
			load();
		} else {
			easyHighScoreList = new HighScoreList("Easy Mode", 5, "seconds", true);
			normalHighScoreList = new HighScoreList("Normal Mode", 5, "seconds", true);
			hardHighScoreList = new HighScoreList("Hard Mode", 5, "seconds", true);
		}
	}
	
	public void mainLoop() {
		while (true) {
			System.out.println("[1] New game");
			System.out.println("[2] High score");
			System.out.println("[3] Exit");
			
			System.out.print("Input: ");
			String input = scanner.nextLine();
			System.out.println();
			
			if (input.equals("1")) {
				chooseDifficultyAndPlay();
			} else if (input.equals("2")) {
				showHighscore();
			} else if (input.equals("3")) {
				return;
			} else {
				System.out.println("Your input is invalid.\n");
			}
		}
	}
	
	public void chooseDifficultyAndPlay() {
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
			int height = Tools.saveIntInput("Height: ", Pitch.min, Pitch.max);
			int width = Tools.saveIntInput("Width: ", Pitch.min, Pitch.max);
			int minedPossibility = Tools.saveIntInput("Mined Possibility: ", Pitch.minimumMined, Integer.MAX_VALUE);
			play(GameMode.getCustomMode(height, width, minedPossibility));
		} else if (input.equals("5")) {
			return;
		}
	}
	
	public void play(GameMode mode) {
		Scanner scanner = new Scanner(System.in);
		Pitch pitch = new Pitch(mode.getHeight(), mode.getWidth(), mode.getMinedPossibility());
		
		// measure time for high score placement
		StopWatch watch = new StopWatch();
		watch.start();
		
		pitch.displayUserInformation();
		
		while (!pitch.isCompletelyExplored() && !pitch.isDestroyed() && !pitch.isGivenUp()) {
			pitch.display();
			pitch.getUserInput(scanner);
		}

		// game over
		
		watch.stop();
		
		if (pitch.isGivenUp()) {
			System.out.println("You gave up.");
		} else {
			pitch.display();
			if (pitch.isCompletelyExplored()) {
				System.out.println("You've won the game!");
				checkForHighscore(mode.getDifficulty(), watch);
			} else {
				System.out.println("You stepped on a mine.");
			}
		}
		System.out.println();
	}
	
	private void checkForHighscore(Difficulty difficulty, StopWatch watch) {
		HighScoreList rightList;
		if (difficulty.equals(Difficulty.EASY)) {
			rightList = easyHighScoreList;
		} else if (difficulty.equals(Difficulty.NORMAL)) {
			rightList = normalHighScoreList;
		} else if (difficulty.equals(Difficulty.HARD)) {
			rightList = hardHighScoreList;
		} else {
			return;
		}
		
		if (rightList.checkIfWorthy(watch.getSeconds())) {
			System.out.println("Congratulations! You reached a new high score!");
			String name = Tools.saveStringInput("Enter your name: ", 3, 20);
			rightList.add(name, watch.getSeconds());
			save();
		}
	}
	
	public void showHighscore() {
		System.out.println(easyHighScoreList);
		System.out.println(normalHighScoreList);
		System.out.println(hardHighScoreList);
	}
	
	private void save() {
		List<HighScoreList> scoreLists = new ArrayList<HighScoreList>();
		scoreLists.add(easyHighScoreList);
		scoreLists.add(normalHighScoreList);
		scoreLists.add(hardHighScoreList);
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH))) {
			oos.writeObject(scoreLists);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void load() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_PATH))) {
			List<HighScoreList> scoreLists = new ArrayList<>();
			scoreLists = (ArrayList<HighScoreList>) ois.readObject();
			easyHighScoreList = scoreLists.get(0);
			normalHighScoreList = scoreLists.get(1);
			hardHighScoreList = scoreLists.get(2);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
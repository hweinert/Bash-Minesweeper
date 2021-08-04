public class GameMode {
	private Difficulty difficulty;
	private int height;
	private int width;
	private int minedPossibility;
	
	private GameMode(Difficulty difficulty, int height, int width, int minedPossibility) {
		this.difficulty = difficulty;
		this.height = height;
		this.width = width;
		this.minedPossibility = minedPossibility;
	}
	
	public static GameMode getEasyMode() {
		return new GameMode(Difficulty.EASY, 8, 10, 8);
	}
	
	public static GameMode getNormalMode() {
		return new GameMode(Difficulty.NORMAL, 14, 18, 7);
	}
	
	public static GameMode getHardMode() {
		return new GameMode(Difficulty.HARD, 15, 34, 5);
	}
	
	public static GameMode getCustomMode(int height, int width, int minedPossibility) {
		return new GameMode(Difficulty.CUSTOM, height, width, minedPossibility);
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getMinedPossibility() {
		return minedPossibility;
	}
}

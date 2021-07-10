import java.util.Scanner;

public class LittleTools {
	private static Scanner scanner = new Scanner(System.in);

	public static int saveIntInput(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return Integer.valueOf(scanner.nextLine());

			} catch (NumberFormatException e) {
				System.out.println("Not a valid number.");
			}
		}
	}
	
	public static boolean equalsOneOf(boolean ignoreCase, String string, String... symbols) {
		for (String symbol : symbols) {
			if (ignoreCase) {
				if (string.equalsIgnoreCase(symbol)) return true;
			} else {
				if (string.equals(symbol)) return false;
			}
		}
		return false;
	}
	
	
}

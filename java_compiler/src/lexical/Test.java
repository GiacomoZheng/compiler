package lexical;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Test {

	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static void lexer(String[] args) throws IOException {
		DFA dfa = new LocDFA();
		String text;

		if (args.length == 0) {
			System.out.println("loop start: ");
			Scanner scan = new Scanner(System.in);
			while (true) {
				text = scan.nextLine();
				if (text == "quit()") break; // +
				System.out.println("string:\t" + text + "\n\t\t" + dfa.analyze(text));
			}
			scan.close();
		} else if (args.length == 2) {
			String string = args[1];
			switch (args[0]) {
				case "-l":
				case "-L":
					System.out.println("read file: " + string); // +
					text = readFile(string, StandardCharsets.UTF_8);
					break;
				case "-c":
				case "-C":
					System.out.println("read text: " + string); // +
					text = string;
					break;
				default:
					text = "";
					System.out.println("error: invalid arguments");
					System.exit(0);
			}
			System.out.println("string:\t" + text + "\n\t" + dfa.analyze(text));
		} else {
			System.out.println("error: invalid arguments");
		}
	}

	public static void main(String[] args) throws IOException {
		lexer(args);
		// DFA dfa = new LocDFA();
		// System.out.println(dfa.getInitial().trans(';').records);
	}
}
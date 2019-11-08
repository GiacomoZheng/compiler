package lexical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.Tools;

/**
 * LocDFA, used to read the file to get all the location
 * 	i.e. only consider about ':'
 * DFA
 * ├── LocDFA
 * │ ├── ConsDFA
 * │ └── TempDFA
 * └── SenDFA (identifierSentence DFA)
 */
public class LocDFA implements DFA {
	// public Map<String, State> states;
	
	public final State initial = new State("__initial__"); // py style

	protected Set<Character> whitespaces;
	LocDFA() {
		whitespaces = new HashSet<>();

		State definition = new State("__definition__");
			State definator = new State("__definator__");
				State defineSubscript = new State("__defineSubscript__");
			State defineBody = new State("__defineBody__");

		State lineString = new State("__lineString__");
			State lineStringEscape = new State("__lineStringEscape__");

		State preString = new State("__preString__");
		
		State lineComment = new State("__lineComment__");
		
		State blockComment = new State("__blockComment__");
			State commentString = new State("__commentString__");
				State commentStringEscape = new State("__commentStringEscape__");

		// for test
		State test = new State("__test__");
		initial.addNext(test, "test");
		test.addTerminator(initial, "break");

		// for whitespaces
		whitespaces.addAll(Arrays.asList(' ', '\t', '\n'));
		initial.addNext(initial, whitespaces);

		// * for definition
		initial.auto = definition;
		definition.addNext(initial, whitespaces);
		definition.addTerminator(definator, ':'); // capture
	
		definator.auto = defineBody;
		definator.addNext(defineSubscript, '[');
			defineSubscript.addRecursionPair('[', ']');
		defineSubscript.addNext(definator, ']');
		definator.addRecord(defineBody, definator);
		
		// for lineString
        initial.addNext(lineString, '"');
            lineString.addNext(lineStringEscape, '\\');
                lineStringEscape.auto = lineString;
        lineString.addNext(initial, '\n', '"');

        // for preString
        initial.addNext(preString, '`');
        preString.addNext(initial, '`');

        // for lineComment
        initial.addNext(lineComment, ';');
        lineComment.addNext(initial, '\n'); // \n is not a part of lineComment

        // for blockComment
        initial.addNext(blockComment, '[');
            blockComment.addNext(commentString, '"');
                commentString.addNext(commentStringEscape, '\\');
                    commentStringEscape.auto = commentString;
            commentString.addNext(initial, '"');
        blockComment.addNext(initial, ']');
	}

	@Override
	public State getInitial() {return initial;}

	@Override
	public List<Token> analyze(String text) {
		text = Tools.check_end(text);
		final int length = text.length();
		
		List<Token> list = new ArrayList<Token>();
		int tokenBegin = 0;
		int nextIndex = 0;

		State current = initial;
		
		Character nextChar;
		State nextState;
		State recordState;

		while (true) {
			if (nextIndex >= length) break;
			nextChar = text.charAt(nextIndex);
			nextState = current.trans(nextChar);

			/** active capture */
			recordState = current.getRecord(nextState);
			System.out.println("char:\t" + Tools.fixEscape(nextChar) + "\t" + current + "\t->\t" + nextState + "\trecord: " + recordState);// + "\tauto: " + current.auto);
			if (recordState != null) {
				list.add(new Token(recordState, text.substring(tokenBegin, nextIndex + 1)));
				System.out.println("add: " + recordState + " : " + Tools.fixEscape(text.substring(tokenBegin, nextIndex + 1)));

                tokenBegin = nextIndex + 1;
			}
			current = nextState;
			nextIndex += 1;
		}
		return list;
	}

}
package structure.dfa;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import tools.Tools;

/**
 * DFA,
 * 	DFA
 *  ├── LocDFA
 *  │   ├── ConsDFA
 *  │   └── TempDFA
 *  └── SenDFA (sentence DFA)
 *      └── 
 */
public class SenDFA extends DFA {
    // mainly focus on ","
    SenDFA () {
        super();
        whitespace.addAll(Arrays.asList(' ', '\t', '\n')); // the whitespace characters (blanks, tabs, newlines, and a few others)

        State lineString = new State("__lineString__");
            State lineStringEscape = new State("__lineStringEscape__");

        State preString = new State("__preString__");
        
        State lineComment = new State("__lineComment__");
        
        State blockComment = new State("__blockComment__");
            State commentString = new State("__commentString__");
                State commentStringEscape = new State("__commentStringEscape__");

        addState(lineString, lineStringEscape, preString, lineComment, blockComment, commentString, commentStringEscape);

        // for whitespaces
        initial.addNext(initial, whitespace);

        // for lineString
        initial.addNext(lineString, '"');
            lineString.addNext(lineStringEscape, '\\');
                lineStringEscape.recover = lineString;
        lineString.addNext(initial, '\n'); // \n is not a part of string
        lineString.addTerminator('"'); // " is a part of string
            lineString.terminal = initial;

        // for preString
        initial.addNext(preString, '`');
        preString.addTerminator('`');
            preString.terminal = initial;

        // for lineComment
        initial.addNext(lineComment, ';');
        lineComment.addNext(initial, '\n'); // \n is not a part of lineComment

        // for blockComment
        initial.addNext(blockComment, '[');
            blockComment.addNext(commentString, '"');
                commentString.addNext(commentStringEscape, '\\');
                    commentStringEscape.recover = commentString;
            commentString.addTerminator('"');
                commentString.terminal = initial;
        blockComment.addTerminator(']');
            blockComment.terminal = initial;
    }

    /**
     * an lexer to get all the tokens from the text
     */
	@Override
	public List<Token> analyze(String text) {
        if (currentState != initial) {
            System.out.println("warning: Didn't reset lexer");
        }
        text = check_end(text);
        
        final int length = text.length();
        
        List<Token> list = new ArrayList<>();
        int tokenBegin = 0;
        int current = 0;
        Character currentChar;
        State nextState;
        while (true) {
            if (current >= length) break;

            currentChar = text.charAt(current);
            nextState = currentState.trans(currentChar);

            System.out.println("char:\t" + Tools.fixEscape(currentChar) + "\t" + currentState + "\t->\t" + nextState);

            if (nextState == initial) {
                if (currentState != initial) {
                    list.add(new Token(currentState, text.substring(tokenBegin, current)));
                }
                tokenBegin = current + 1; // for next token
            }
            currentState = nextState;
            current += 1;
        }
		return list;
    }
}
package structure.dfa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tools.Tools;

/**
 * DFA,
 * 	DFA
 *  ├── LocDFA
 *  │   ├── ConsDFA
 *  │   └── TempDFA
 *  └── SenDFA (identifierSentence DFA)
 */
public class SenDFA extends DFA {
    // the identifierSentence
    public Set<Character> separators;
    SenDFA () {
        super();
        separators = new HashSet<>();
        
        whitespaces.addAll(Arrays.asList(' ', '\t', '\n')); // the whitespace characters (blanks, tabs, newlines, and a few others)
        separators.add(',');

        State identifierSentence = new State("__identifierSentence__"); // in gm, almost very thing are identifierSentence
        
        // State separator = new State("__separator__");

        State lineString = new State("__lineString__");
            State lineStringEscape = new State("__lineStringEscape__");

        State preString = new State("__preString__");
        
        State lineComment = new State("__lineComment__");
        
        State blockComment = new State("__blockComment__");
            State commentString = new State("__commentString__");
                State commentStringEscape = new State("__commentStringEscape__");
        
        State functionBracketStart = new State("__functionBracketStart__");
        State roundBracketStart = new State("__roundBracketStart__");
        State roundBracketEnd = new State("__roundBracketEnd__");

        State subscriptBracketStart = new State("__subscriptBracketStart_");
        State squareBracketEnd = new State("__squareBracketEnd__");

        addState(
            identifierSentence,
            // separator,
            lineString, lineStringEscape,
            preString,
            lineComment, // + betterComment
            blockComment, commentString, commentStringEscape,
            functionBracketStart, roundBracketStart, roundBracketEnd,
            subscriptBracketStart, squareBracketEnd
        );

        // for whitespaces and separators
        initial.addNext(initial, whitespaces);
        initial.addNext(initial, separators);

        // for lineString
        initial.addNext(lineString, '"');
            lineString.addNext(lineStringEscape, '\\');
                lineStringEscape.recover = lineString;
        lineString.addNext(initial, '\n'); // \n is not a part of string
        lineString.addTerminator('"'); // " is a part of string

        // for preString
        initial.addNext(preString, '`');
        preString.addTerminator('`');

        // for lineComment
        initial.addNext(lineComment, ';');
        lineComment.addNext(initial, '\n'); // \n is not a part of lineComment

        // for blockComment
        initial.addNext(blockComment, '[');
            blockComment.addNext(commentString, '"');
                commentString.addNext(commentStringEscape, '\\');
                    commentStringEscape.recover = commentString;
            commentString.addTerminator('"');
        blockComment.addTerminator(']');

        // for roundBracket
        initial.addNext(roundBracketStart, '(');
            roundBracketStart.recover = initial;
        initial.addNext(roundBracketEnd, ')');
            roundBracketEnd.recover = initial;

        // for squareBracket
        initial.addNext(squareBracketEnd, ']');
            squareBracketEnd.recover = initial;

        // * for identifierSentence
        initial.recover = identifierSentence;
        identifierSentence.addNext(initial, separators);
        // identifierSentence.addNext(lineComment, by);
        identifierSentence.addTerminator(functionBracketStart, '(');
            // functionBracketStart.recover = initial;
        identifierSentence.addTerminator(subscriptBracketStart, '[');
            // subscriptBracketStart.recover = initial;
        identifierSentence.addBrake(')');
        identifierSentence.addBrake(']');
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
        int next = 0;
        
        Character nextChar;
        State nextState;
        // State lastTokenState; // used for function
        while (true) {
            if (next >= length) break;
            if (currentState.end == true) {
                /* active capture if meeting a end */
                list.add(new Token(currentState, text.substring(tokenBegin, next)));
                System.out.println("end add: " + currentState + " : " + text.substring(tokenBegin, next));

                currentState.end = false;
                currentState = initial;

                tokenBegin = next; // *
            } else if (currentState.shut == true) {
                /* active capture if meeting a shut */
                next = next - 1; // *

                list.add(new Token(currentState, text.substring(tokenBegin, next)));
                System.out.println("shut add: " + currentState + " : " + text.substring(tokenBegin, next));

                currentState.shut = false;
                currentState = initial;

                tokenBegin = next;
            }

            nextChar = text.charAt(next);
            nextState = currentState.trans(nextChar);

            System.out.println("char:\t" + Tools.fixEscape(nextChar) + "\t" + currentState + "\t->\t" + nextState + "\tend?: " + currentState.end + "\tshut?: " + currentState.shut);// + "\trecover: " + currentState.recover);
            
            /* passive capture if going back initial */
            if (nextState == initial) {
                if (currentState != initial) {
                    list.add(new Token(currentState, text.substring(tokenBegin, next)));
                    System.out.println("passive add: " + currentState + " : " + text.substring(tokenBegin, next));
                }
                tokenBegin = next + 1; // for next token
            }
            currentState = nextState;
            next += 1;
            // System.out.println("\t\t\t" + list);
        }
		return list;
    }
}
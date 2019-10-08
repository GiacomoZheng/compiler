package structure.dfa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * LocDFA, used to read the file to get all the location
 * DFA
 * ├── LocDFA 
 * │ ├── ConsDFA 
 * │ └── TempDFA 
 * └── SenDFA (sentence DFA)
 */
public class LocDFA extends DFA {
    // mainly consider about the ( and )
    public Set<Character> blank;

    LocDFA() {
        super();

        State definition = new State("__definition__");
        State braOpening = new State("__braOpening__");
        State braClosing = new State("__braClosing");
        State content = new State("__content__");
        addState(definition, braOpening, braClosing, content);
        // TODO
    }

    @Override
    public ArrayList<Token> analyze(String text) {
        // TODO
        ArrayList<Token> list = new ArrayList<>();
        return list;
    }

    public static void main(String[] args) {
        DFA dfa = new LocDFA();
        System.out.println(dfa.initial.name);
    }
}
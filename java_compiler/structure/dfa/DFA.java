package structure.dfa;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
    Source: https://en.wikipedia.org/wiki/Deterministic_finite_automaton
    A deterministic finite automaton M consisting of
    a finite set of states
    a finite set of input symbols called the alphabet
    a transition function
    an initial or start state
    a set of accept states
 */

/**
 * DFA,
 * 	DFA
 *  ├── LocDFA
 *  │   ├── ConsDFA
 *  │   └── TempDFA
 *  └── SenDFA (sentence DFA)
 */
public abstract class DFA {
    // a little different from the original one

    final public State initial;
    public HashMap<String, State> states;
    public State currentState;

    // Set<Character> alphabet; //useless in my version
    // Set<State> accepts; // useless here

    public Set<Character> whitespace; // he characters would be ignored


    DFA () {
        states = new HashMap<>();
        initial = new State("__initial__"); // py style
        addState(initial);
        currentState = initial;
        whitespace = new HashSet<>();
    }

    abstract public List<Token> analyze(String text);

    public static String check_end(String text) {
        if (!text.endsWith("\n")) {
            text += "\n";
            System.out.println("fixed: should be end with \"\\n\"");        
        }
        return text;
    }

    public void addState(State state) {
        states.put(state.name, state);
    }

    public void addState(State... stateArray) {
        for (State state: stateArray) addState(state);
    }

    public void check_correct() {
        if (states.containsKey("__initial__")) {
            return;
        }
        System.out.println("error: incorrect definition.");
        System.exit(0);
    }

    public static void main(String[] args) {
        // DFA s = new LocDFA();
        // State a1 = new State("1");
        // State a2 = new State("2");
        // State a3 = new State("3");
        // State a4 = new State("4");
        // s.addState(a1, a2, a3, a4);
        // String text = "a";
        // text = check_end(text);
        // System.out.println(text);
        Character c = '\n';
        System.out.println((c));
    }
}
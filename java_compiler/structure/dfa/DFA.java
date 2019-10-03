package structure.dfa;

import java.util.HashMap;
import java.util.Set;

import structure.tree.LocTree;

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
 *  └── 
 */
public class DFA {

    // instance property
    public HashMap<String, State> states;
    public Set<String> alphabet; // I want to use a criterion to replace it
    // transition
    public State initial;
    public Set<State> accepts;

    public State current;

    DFA () {
        states = new HashMap<>();
        initial = new State("__initial__"); // py style
        addState(initial);
        current = initial;
    }

    // public abstract LocTree analyze(String text);

    public void addState(State state) {
        states.put(state.name, state);
        System.out.println("1");
    }
    public void addState(State... stateArray) {
        for (State state: stateArray) addState(state);
        System.out.println("...");
    }

    public void check_correct() {
        if (states.containsKey("__initial__") & states.values().containsAll(accepts)) {
            return;
        }
        System.out.println("error: incorrect definition.");
        System.exit(0);
    }

    public void check_end() {
        if (accepts.contains(current)) {
            System.out.println("end");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        DFA s = new DFA();
        State a1 = new State("1");
        State a2 = new State("2");
        State a3 = new State("3");
        State a4 = new State("4");
        s.addState(stateArray);
    }
}
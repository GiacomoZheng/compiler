package structure.dfa;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class State {
    private HashMap<Character, State> transition;
    private Set<Character> terminators; // the terminators are still with this State, but will go to the terminal at next char

    public String name;
    public State recover;
    public State terminal;
    public Boolean end;

    State (String name) {
        this.name = name;
        this.recover = this;
        this.terminal = this;
        this.end = false;
        this.transition = new HashMap<>();
        this.terminators = new HashSet<>();
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * if the last character is a terminator, return terminal
     * if `a` is undefined, return recover
     */
    public State trans(Character by) {
        if (end) {
            end = false;
            return terminal;
        }
        if (terminators.contains(by)) {
            end = true;
        }
        State state = transition.get(by);
        if (state == null) return recover;
        return state;
    }

    // use to change the transition
    public void addNext(State next, Character by) {
        transition.put(by, next);
    }
    public void addNext(State next, Character... bys) {
        for (Character by: bys) addNext(next, by);
    }
    public void addNext(State next, Collection<Character> bys) {
        for (Character by: bys) addNext(next, by);
    }
    
    // use to change the terminators
    public void addTerminator(Character by) {
        terminators.add(by);
        addNext(this, by);
    }
    public void addTerminator(Character... bys) {
        for (Character by: bys) addTerminator(by);
    }
    public void addTerminator(Collection<Character> bys) {
        for (Character by: bys) addTerminator(by);
    }

    public static void main(String[] args) {
        State a = new State("");
        a.addNext(a, new HashSet<Character>());
    }
}
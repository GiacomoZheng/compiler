package structure.dfa;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class State {
    private HashMap<Character, State> transition;

    public Set<Character> terminators; // the terminators will maintain the State, and rasie an end
        public Boolean end;
    private Set<Character> brakes; // there will be a null added before the brakes
        public Boolean shut;
    
    public String name;
    public State recover;

    State (String name) {
        transition = new HashMap<>();
        this.name = name;
        recover = this;
        terminators = new HashSet<>();
            end = false;
        brakes = new HashSet<>();
            shut = false;
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
        State state = transition.get(by);
        if (state == null) state = recover;
        if (terminators.contains(by)) {
            state.end = true;
        }
        else if (brakes.contains(by)) {
            // they can never overlap
            state.shut = true;
        }
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
    
    // use to change the brakes
    public void addBrake(State next, Character by) {
        brakes.add(by);
        addNext(next, by);
    }
    public void addBrake(Character by) {addBrake(this, by);}

    public void addBrake(State next, Character... bys) {
        for (Character by: bys) addBrake(next, by);
    }
    public void addBrake(Character... bys) {addBrake(this, bys);}

    public void addBrake(State next, Collection<Character> bys) {
        for (Character by: bys) addBrake(next, by);
    }
    public void addBrake(Collection<Character> bys) {addBrake(this, bys);}
    
    // use to change the terminator
    public void addTerminator(State next, Character by) {
        terminators.add(by);
        addNext(next, by);
    }
    public void addTerminator(Character by) {addTerminator(this, by);}
    
    public void addTerminator(State next, Character... bys) {
        for (Character by: bys) addTerminator(next, by);
    }
    public void addTerminator(Character... bys) {addTerminator(this, bys);}
    
    public void addTerminator(State next, Collection<Character> bys) {
        for (Character by: bys) addTerminator(next, by);
    }
    public void addTerminator(Collection<Character> bys) {addTerminator(this, bys);}

    public static void main(String[] args) {
        State a = new State("");
        a.addNext(a, new HashSet<Character>());
    }
}
package structure.dfa;

import java.util.HashMap;

public class State {
    private HashMap<String, State> transition;
    
    public State trans(String a) {
        return transition.get(a);
    }
    public String name;

    State (String name) {
        this.transition = null;
        this.name = name;
    }

    // use to change the transition
    public void addNext(State next, String by) {
        
    }

    public static void main(String[] args) {
        // System.out.println(java.util);
    }
}
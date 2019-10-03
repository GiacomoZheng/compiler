package structure.dfa;

import structure.tree.LocTree;

/** LocDFA, used to read the file to get all the location
 *  DFA
 *  ├── LocDFA
 *  │   ├── ConsDFA
 *  │   └── TempDFA
 *  └── 
 */
public class LocDFA extends DFA {
    // mainly consider about the brackets
    LocDFA () {
        super();
        State definition = new State("__definition__");
        State braOpening = new State("__braOpening__");
        State braClosing = new State("__braClosing");
        State content = new State("__content__");
        addState(definition, braOpening, braClosing, content);
        
        
    }

    @Override
    public LocTree analyze(String text) {
        
    }
    
    public static void main(String[] args) {
        DFA dfa = new LocDFA();
        System.out.println(dfa.initial.name);
    }

}
package structure.dfa;

import tools.Tools;

public class Token {
    public State state;
    public String name;

    Token(State state, String name) {
        this.state = state;
        this.name = name;
    }

    @Override
    public String toString() {
        return "< " + state.name + " , " + Tools.fixEscape(name) + " >";
    }

    public static void main(String[] args) {
        State state = new State("set");
        Token token = new Token(state, "A");
        // token.name = "A";
        System.out.println(token);
    }
    
}
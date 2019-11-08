package lexical;

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
		return "< " + state.name + " >|< " + Tools.fixEscape(name) + " >";
	}
}
package lexical;

import java.util.List;

public interface DFA {

	public State getInitial();

	public List<Token> analyze(String text);

}
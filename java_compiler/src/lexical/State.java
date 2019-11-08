package lexical;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class State {
	private Map<Character, State> transition;
	private Map<State, State> records; // <next, record>
	
	public String name;
	public State auto; // default
	
	private int recursiveDepth;

	State (String name) {
		transition = new HashMap<>();

		records = new HashMap<>();
		this.name = name;
		auto = this;
		recursiveDepth = 0;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public State trans(Character by) {
		State state = transition.get(by);
		if (state == null) state = auto;
		return state;
	}

	public void addRecord(State next, State record) {
		records.put(next, record);
	}
	public State getRecord(State next) {
		return records.get(next);
	}

	public void addRecursionPair(String start, String end) {
		// TODO
	}
	public void addRecursionPair(Character start, Character end) {
		addRecursionPair("" + start, "" + end);
	}

	public void addNext(State next, Character by) {
		transition.put(by, next);
	}
	public void addNext(State next, Character... bys) {
		for (Character by: bys) addNext(next, by);
	}

	public void addNext(State next, String by) {
		final int LEN = by.length();
		State[] term = new State[LEN];
		term[0] = this;
		for (int j = 1; j < LEN; j++) {
			term[j] = new State(name + "." + by + "." + j);
			term[j - 1].addNext(term[j], by.charAt(j - 1));
			term[j - 1].auto = this;
		}
		term[LEN - 1].addNext(next, by.charAt(LEN - 1));
	}
	public void addNext(State next, String... bys) {
		for (String by: bys) addNext(next, by);
	}

	public <T> void addNext(State next, Collection<T> bys) {
		for (T by: bys) addNext(next, "" + by);
	}

	public void addTerminator(State next, Character terminator) {
		addNext(next, terminator);
		addRecord(next, this);
	}
	public void addTerminator(State next, Character... terminators) {
		for (Character terminator: terminators) addTerminator(next, terminator);
	}

	public void addTerminator(State next, String terminator) {
		final int LEN = terminator.length();
		State[] term = new State[LEN];
		term[0] = this;
		for (int j = 1; j < LEN; j++) {
			term[j] = new State(name + "." + terminator + "." + j);
			term[j - 1].addNext(term[j], terminator.charAt(j - 1));
			term[j - 1].auto = this;
		}
		term[LEN - 1].addNext(next, terminator.charAt(LEN - 1));
		term[LEN - 1].addRecord(next, this);
	}
	public void addTerminator(State next, String... terminators) {
		for (String terminator: terminators) addTerminator(next, terminator);
	}

	public <T> void addTerminator(State next, Collection<T> terminators) {
		for (T terminator: terminators) addTerminator(next, (String) terminator);
	}
}
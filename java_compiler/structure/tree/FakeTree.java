package structure.tree;

/**
 * FakeTree,
 * to describe structure of defined classes (with Mmulti-inheritance).
 * 	FakeTree
 * 	├── Tree
 * 	│   ├── ASTree
 *	│   ├── LocTree (location tree)
 *	│   ├── ScopeTree 
 *	└── InherTree(inheritance tree)
 */
public abstract class FakeTree {
	protected static void println(Object o) {
		if (o == null) System.out.println("null");
		else System.out.println(o.toString());
	}
	protected static void test(Object o) {
		if (o == null) System.out.println("test>>>\tnull");
		else println("test>>>\t" + o.toString());
	}

	// a fake tree node
	protected abstract static class Node {
		public Node parent;
		public String name;
		// public Lexeme content; // ?

		Node (String name) {
			this.name = name;
			parent = null;
			// content = null;
		}
	}
	
	public Node root;
	
	abstract public void insert(Node sub);

	abstract public void delete(Node sub);
}
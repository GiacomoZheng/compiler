package structure;

import language.Lexeme;

/**
 * FakeTree,
 * to describe structure of defined classes (with Mmulti-inheritance).
 * 	FakeTree
 * 	├── Tree
 * 	│   ├── ASTree
 *	│   ├── LocTree (location tree)
 *	└── InherTree(inheritance tree)
 */
public abstract class FakeTree {

	// a fake tree node
	protected static class Node {
		public Node parent;
		public Lexeme content; // ?

		Node() {
			parent = null;
			content = null;
		}
	}
	
	public Node root;
}
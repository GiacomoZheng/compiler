package structure;

import language.Lexeme;

/**
 * LocTree, or location tree
 * 	FakeTree
 * 	├── Tree
 *	│   ├── LocTree (location tree)
 * 	│   ├── ASTree
 *	└── InherTree(inheritance tree)
 */
public class LocTree extends Tree {
	// a LocTree node
	protected static class Node extends Tree.Node {
		/// * Node parent;
		// * Lexeme content;
		// * String name;
		// * LinkedHashMap<String, Node> children;
		// * Node firstChild;
		// * int depth; // ? I'm just interested in it

		public Lexeme attribute;
		public Node pointer; // by defalut, it is a "real" location

		Node (String name) {
			super(name);
			attribute.classLeval = 0;
			attribute.isDelimiter = false;
			pointer = this;
		}
		Node (String name, int classLeval) {
			super(name);
			attribute.classLeval = classLeval;
			attribute.isDelimiter = false;
			pointer = this;
		}
		Node (String name, int classLeval, Boolean isDelimiter) {
			super(name);
			attribute.classLeval = classLeval;
			attribute.isDelimiter = isDelimiter;
			pointer = this;
		}
		Node (String name, int classLeval, Boolean isDelimiter, Node pointer) {
			super(name);
			attribute.classLeval = classLeval;
			attribute.isDelimiter = isDelimiter;
			this.pointer = pointer;
		}
		
	}


}
package structure;
import java.util.LinkedHashMap;

/**
 * Tree, I use the LinkedHashMap to store the information of child nodes.
 * 	FakeTree
 * 	├── Tree
 * 	│   ├── ASTree
 *	│   ├── LocTree (location tree)
 *	└── InherTree(inheritance tree)
 */
public abstract class Tree extends FakeTree {

	// a tree node
	protected static class Node extends FakeTree.Node {
		// * Node parent;
		// * Lexeme content;
		public String name;
		protected LinkedHashMap<String, Node> children;
		public Node firstChild;
		// ! public Node sibling; // no use anymore
		public int depth; // ? I'm just interested in it

		Node (String name) {
			super();
			this.name = name;
			children = new LinkedHashMap<>();
			firstChild = children.entrySet().iterator().next().getValue(); // ?
			depth = 0;
		}
		
	}
	
	// * Node root;

}
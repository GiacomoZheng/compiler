package structure;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * Tree, I use the LinkedHashMap to store the information of child nodes.
 * 	FakeTree
 * 	├── Tree
 * 	│   ├── ASTree
 *  │   ├── LocTree (location tree)
 *	│   ├── ScopeTree 
 *	└── InherTree(inheritance tree)
 */
public abstract class Tree extends FakeTree {
	// a tree node
	protected static class Node extends FakeTree.Node {
		public Node parent; // inheritance
		// * String name;
		protected LinkedHashMap<String, Node> children;
		public int depth;
		public Node pointer;

		Node (String name) {
			super(name);
			children = new LinkedHashMap<>();
			depth = 0;
		}

		public Node firstChild() {
			return children.entrySet().iterator().next().getValue();
		}

		public String fullName() {
			if (parent != null) return parent.fullName() + "." + name;
			return name;
		}

		private static String repeat(int n, String str) {
			String res = "";
			for (int i = 0; i < n; i++) {
				res += str;
			}
			return res;
		}
		protected String toline(String suffix) {
			if (depth == 0) {
				return name + suffix + "\n";
			} else {
				return repeat(depth - 1, "│   ") + "├── " + name + suffix + "\n";
			}
		}
		protected String toline() {
			return toline("");
		}
		@Override
		public String toString() {
			String res = "";
			Iterator<Entry<String, Node>> iter = children.entrySet().iterator();
			while (iter.hasNext()) {
				res += iter.next().getValue().toString();
			}
			return toline() + res;
		}
	}
	
	public Node root; // inheritance
	public Node cwd; // currect work directory

	Tree () {
		super();
		root = null;
	}

	// * void insert(Node);

	// * void delete(Node);

	abstract public void delete(String key);

	abstract public void delete();

	abstract public void pwd();

	abstract public void cd(Node des);

	abstract public void cd(String key);

	abstract public void ls();
	
	abstract public void tree();
}
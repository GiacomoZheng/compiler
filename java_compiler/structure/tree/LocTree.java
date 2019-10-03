package structure.tree;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * LocTree, or location tree
 * 	FakeTree
 * 	├── Tree
 *	│   ├── LocTree (location tree)
 * 	│   ├── ASTree
 *	│   ├── ScopeTree 
 *	└── InherTree(inheritance tree)
 */
public class LocTree { // extends Tree 
	// a LocTree node
	protected static class Node extends Tree.Node {
		public Node parent; // inheritance
		public LinkedHashMap<String, Node> children; // inheritance
		// * String name;
		// * int depth;

		// below three are properties of a genreal location
		public Node pointer; // * important
		public Boolean isDelimiter;
		public int classLeval; // 0 : sample, 1 : class, ...

		Node (String name) { // useless
			super(name);
			children = new LinkedHashMap<>();
			classLeval = 0;
			isDelimiter = false;
			pointer = this; // by defalut, it is a "pure" location
			// println(children); // null (With redefinition of the children)
		}
		Node (String name, int classLeval) {
			super(name);
			children = new LinkedHashMap<>();
			this.classLeval = classLeval;
			isDelimiter = false;
			pointer = this;
		}
		Node (String name, int classLeval, Boolean isDelimiter) {
			super(name);
			children = new LinkedHashMap<>();
			this.classLeval = classLeval;
			this.isDelimiter = isDelimiter;
			pointer = this;
		}
		Node (String name, Node pointer) {
			this(name);
			children = pointer.children; // ****** very important
			classLeval = pointer.classLeval;
			isDelimiter = pointer.isDelimiter;
			this.pointer = pointer;
		}
		@Override
		public String toString() {
			String res = "";
			Iterator<Entry<String, Node>> iter = children.entrySet().iterator();
			// println("************************");
			while (iter.hasNext()) {
				Node tmp = iter.next().getValue();
				if (tmp.pointer == tmp) res += tmp.toString();
				else res += tmp.toline(" -> " + pointer.name);
			}
			return toline() + res;
		}
	}

	public Node root; // inheritance
	public Node cwd; // inheritance

	public LocTree () {
		super();
		root = null;
	}
	public void insert(Node sub) {
		if (cwd == null) {
			root = sub;
			cwd = root;
			sub.depth = 0;
		} else {
			sub.parent = cwd.pointer;
			cwd.children.put(sub.name, sub);
			sub.depth = sub.parent.depth + 1;
		}
	}

	public void delete(Node sub) {
		sub.parent.children.remove(sub.name);
	}

	public void delete(String key) {
		Node tmp = cwd.children.remove(key);
		if (tmp == null) {
			System.out.println("warning: no such sub-location");
		}
	}

	public void delete() {
		String key = cwd.name;
		cd("..");
		delete(key);
	}

	public void pwd() {
		System.out.println(cwd.fullName());
	}

	public void cd(Node des) {
		cwd = des;
		pwd();
	}

	public void cd(String key) {
		if (key == "..") {
			cd(cwd.parent);
			return;
		}
		Node tmp = cwd.children.get(key);
		if (tmp == null) {
			System.out.println("warning: no such sub-location");
		} else {
			cd(tmp);
		}
	}

	public void ls() {
		Iterator<Entry<String, Node>> iter = cwd.children.entrySet().iterator();
		// System.out.println("start");
		while (iter.hasNext()) {
			System.out.print(iter.next().getValue().name + ", ");
		}
		System.out.println();
	}

	public void tree() {
		System.out.println(root);
	}
	public static void main(String[] args) {
		LocTree t = new LocTree();
		Node s1 = new Node("1");
		// println(s1.children); // null
		t.insert(s1);
		Node s2 = new Node("2", s1);
		t.insert(s2);
		// println(t.root.equals(s1)); // true
		// println(s1.children); // null

		// println(t.root.firstChild().children);
		// println("# move to 2");
		t.cd("2");
		t.insert(new Node("4"));
		t.insert(new Node("1"));
		// println("# list all the subs");
		t.ls();
		// println(s2.parent);
		t.delete(s2);
		// println("# draw the tree");
		t.tree();
		// println(s2.children);
	}

}
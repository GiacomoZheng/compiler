package structure.tree;

/**
 * InherTree,
 * to describe the inheritance relation of classes
 * 	FakeTree
 * 	├── Tree
 * 	│   ├── ASTree
 *	│   ├── LocTree (location tree)
 *	│   ├── ScopeTree 
 *	└── InherTree(inheritance class tree)
 */
public class InherTree { // extends FakeTree

    protected static class Node extends FakeTree.Node {
        // * String name;
        public Node parent; // inheritance
        public Node superclass;
        public Node abstraction;
        public Node sample;
        // there should be only one attributes works in the above three

        public int classLeval;

        Node (String name) { // useless
            super(name);
        }

        public Boolean is_sub_of(Node superclass) {
            if (this == superclass) return true;
            else if (parent == null) return false;
            else return parent.is_sub_of(superclass);
        }

        public Node newNode(String name) throws InstantiationException, IllegalAccessException {
            Node node = Node.class.newInstance();
            node.name = name;
            node.parent = this;

            node.superclass = null;
            node.abstraction = null;
            node.sample = null;
            return node;
        }
        public Node newsubNode(String name) throws InstantiationException, IllegalAccessException {
            Node node = newNode(name);
            if (this.classLeval == 0) throw new InstantiationException("error: a sample already");
            node.classLeval = classLeval - 1; // cannot be negative
            node.superclass = node.parent;
            return node;
        }
        public Node newSampleNode(String name) throws InstantiationException, IllegalAccessException {
            Node node = newNode(name);
            node.classLeval = classLeval;
            node.abstraction = node.parent;
            return node;
        }
        public Node newAbstractNode(String name) throws InstantiationException, IllegalAccessException {
            Node node = newNode(name);
            node.classLeval = classLeval + 1;
            node.sample = node.parent;
            return node;
        }

    }

    public Node root; // inheritance
    // the root would be ◉, the universal class, or universal in short

}
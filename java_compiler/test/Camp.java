package test;
import java.util.LinkedHashMap;

public class Camp {
    static class Node {
        Node pointer;
        Node parent;
        Node[] children;
        int value = 0;
        Node () {
            pointer = this;
            children = new Node[10];
        }
        Node (Node pointer) {
            this.pointer = pointer;
            children = pointer.children;
        }
    }
    static class Tree {
        Node root;
        Node cwd;
        void insert(Node sub) {
            if (root == null) {
                root = sub;
                cwd = root;
            } else {
                sub.parent = cwd;
                cwd.children[0] = sub;
            }
        }
    }
    public static void main(String[] args) {
        Tree t = new Tree();
		Node s1 = new Node();
		t.insert(s1);
		Node s2 = new Node(s1);
		t.insert(s2);
        System.out.println(s1.children);
        
        LinkedHashMap<String, Node> l = new LinkedHashMap<>();
        // Node s1 = new Node();
        l.put("key", s1);
        l.get("key").value = 2;
        System.out.println(s1.value);
    }
}
package structure;

/**
 * BraStack,
 * to match the paring of barckets
 * 	Stack
 *  ├── CallStack
 *	└── BraStack
 */
public abstract class Stack {

    public static class Frame {
        public Object data;
        public Frame next;

        Frame (Object data) {
            this.data = data;
        }
    }

    public Frame head;
    public int size;

    Stack () {
        head = null;
        size = 0;
    }

    public boolean isempty() {
        return size == 0;
    }

    public void push(Object e) {
        Frame frame = new Frame(e);
        frame.next = head;
        head = frame;
        size++;
    }
    
    public Object top() {
        if (isempty()) {
            System.out.println("error: underflow");
            return null;
        }
        return head.data;
    }

    public Object pop() {
        Object e = top();
        if (e == null) return e;
        head = head.next;
        return e;
    }
}
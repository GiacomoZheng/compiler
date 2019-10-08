package structure.stack;
// ?
/**
 * BraStack,
 * to match the paring of barckets
 * 	Stack
 *  ├── CallStack
 *	└── BraStack
 */
public class BraStack { // extends Stack {
    public static class Bra { // !
        String opening;
        String closing;
    }
    
	public static class Frame extends Stack.Frame {
        public Bra bracket;
        Frame (String opening, String closing) {
            super(data); // ??
            bracket.opening = opening;
            bracket.closing = closing;
        }
    }

    public Frame head;
    public int size;

    public boolean isempty() {
        return size == 0;
    }




}
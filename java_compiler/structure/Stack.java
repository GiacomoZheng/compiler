// !!!!
package structure;
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */


/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Josh Bloch of Google Inc. and released to the public domain,
 * as explained at http://creativecommons.org/licenses/publicdomain.
 */

/*
 * adopted by giacomo for gm-compiler
 */
import java.util.*;

import language.Lexeme;

/**
 * I'll only use the <b>pop</b> and <b>push</b>
 */
public class Stack<E> extends java.util.AbstractCollection<E> implements java.util.Deque<E> {

	private transient Object[] elements;
	private transient int head;
    private transient int tail;
    private static final int MIN_INITIAL_CAPACITY = 8;
    
    private <T> T[] copyElements(T[] a) {
        if (head < tail) {
            System.arraycopy(elements, head, a, 0, size());
        } else if (head > tail) {
            int headPortionLen = elements.length - head;
            System.arraycopy(elements, head, a, 0, headPortionLen);
            System.arraycopy(elements, 0, a, headPortionLen, tail);
        }
        return a;
    }

    public <T> T[] toArray(T[] a) {
        int size = size();
        if (a.length < size)
            a = (T[])java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        copyElements(a);
        if (a.length > size)
            a[size] = null;
        return a;
    }

	private class DescendingIterator implements Iterator<E> { // E --> E
        /*
         * This class is nearly a mirror-image of DeqIterator, using
         * tail instead of head for initial cursor, and head instead of
         * tail for fence.
         */
        private int cursor = tail;
        private int fence = head;
        private int lastRet = -1;

        public boolean hasNext() {
            return cursor != fence;
        }

        public E next() {
            if (cursor == fence)
                throw new NoSuchElementException();
            cursor = (cursor - 1) & (elements.length - 1);
            E result = elements[cursor];
            if (head != fence || result == null)
                throw new ConcurrentModificationException();
            lastRet = cursor;
            return result;
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            if (!delete(lastRet)) {
                cursor = (cursor + 1) & (elements.length - 1);
                fence = head;
            }
            lastRet = -1;
        }
	}

	private class DeqIterator implements Iterator<E> {
        /**
         * Index of element to be returned by subsequent call to next.
         */
        private int cursor = head;

        /**
         * Tail recorded at construction (also in remove), to stop
         * iterator and also to check for comodification.
         */
        private int fence = tail;

        /**
         * Index of element returned by most recent call to next.
         * Reset to -1 if element is deleted by a call to remove.
         */
        private int lastRet = -1;

        public boolean hasNext() {
            return cursor != fence;
        }

        public E next() {
            if (cursor == fence)
                throw new NoSuchElementException();
            E result = elements[cursor];
            // This check doesn't catch all possible comodifications,
            // but does catch the ones that corrupt traversal
            if (tail != fence || result == null)
                throw new ConcurrentModificationException();
            lastRet = cursor;
            cursor = (cursor + 1) & (elements.length - 1);
            return result;
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            if (delete(lastRet)) { // if left-shifted, undo increment in next()
                cursor = (cursor - 1) & (elements.length - 1);
                fence = tail;
            }
            lastRet = -1;
        }
    }

    Stack () {
        elements = (E[]) new Object[16];
    }

    Stack (int numElements) {
        allocateElements(numElements);
    }

    Stack (Collection<? extends E> c) {
        allocateElements(c.size());
        addAll(c);
    }

    private void allocateElements(int numElements) {
        int initialCapacity = MIN_INITIAL_CAPACITY;
        // Find the best power of two to hold elements.
        // Tests "<=" because arrays aren't kept full.
        if (numElements >= initialCapacity) {
            initialCapacity = numElements;
            initialCapacity |= (initialCapacity >>>  1);
            initialCapacity |= (initialCapacity >>>  2);
            initialCapacity |= (initialCapacity >>>  4);
            initialCapacity |= (initialCapacity >>>  8);
            initialCapacity |= (initialCapacity >>> 16);
            initialCapacity++;

            if (initialCapacity < 0)   // Too many elements, must back off
                initialCapacity >>>= 1;// Good luck allocating 2 ^ 30 elements
        }
        elements = (E[]) new Object[initialCapacity];
    }

	private void doubleCapacity() {
        assert head == tail;
        int p = head;
        int n = elements.length;
        int r = n - p; // number of elements to the right of p
        int newCapacity = n << 1;
        if (newCapacity < 0)
            throw new IllegalStateException("Sorry, deque too big");
        Object[] a = new Object[newCapacity];
        System.arraycopy(elements, p, a, 0, r);
        System.arraycopy(elements, 0, a, r, p);
        elements = (E[]) a;
        head = 0;
        tail = n;
	}

	private boolean delete(int i) {
        checkInvariants();
        final E[] elements = this.elements;
        final int mask = elements.length - 1;
        final int h = head;
        final int t = tail;
        final int front = (i - h) & mask;
        final int back  = (t - i) & mask;

        // Invariant: head <= i < tail mod circularity
        if (front >= ((t - h) & mask))
            throw new ConcurrentModificationException();

        // Optimize for least element motion
        if (front < back) {
            if (h <= i) {
                System.arraycopy(elements, h, elements, h + 1, front);
            } else { // Wrap around
                System.arraycopy(elements, 0, elements, 1, i);
                elements[0] = elements[mask];
                System.arraycopy(elements, h, elements, h + 1, mask - h);
            }
            elements[h] = null;
            head = (h + 1) & mask;
            return false;
        } else {
            if (i < t) { // Copy the null tail as well
                System.arraycopy(elements, i + 1, elements, i, back);
                tail = t - 1;
            } else { // Wrap around
                System.arraycopy(elements, i + 1, elements, i, mask - i);
                elements[mask] = elements[0];
                System.arraycopy(elements, 1, elements, 0, t);
                tail = (t - 1) & mask;
            }
            return true;
        }
	}
	
	private void checkInvariants() {
        assert elements[tail] == null;
        assert head == tail ? elements[head] == null :
            (elements[head] != null &&
             elements[(tail - 1) & (elements.length - 1)] != null);
        assert elements[(head - 1) & (elements.length - 1)] == null;
    }
	
	public void addFirst(E e) {
		if (e == null)
            throw new NullPointerException();
        elements[head = (head - 1) & (elements.length - 1)] = e;
        if (head == tail)
            doubleCapacity();
	}

	public void addLast(E e) {
		if (e == null)
            throw new NullPointerException();
        elements[tail] = e;
        if ( (tail = (tail + 1) & (elements.length - 1)) == head)
            doubleCapacity();
	}

	public Iterator<E> descendingIterator() {
		return new DescendingIterator();
	}

	public E element() {
		return getFirst();
	}

	public E getFirst() {
		E x = elements[head];
        if (x == null)
            throw new NoSuchElementException();
        return x;
	}

	public E getLast() {
		E x = elements[(tail - 1) & (elements.length - 1)];
        if (x == null)
            throw new NoSuchElementException();
        return x;
	}

	public boolean offer(E e) {
		return offerLast(e);
	}

	public boolean offerFirst(E e) {
		addFirst(e);
        return true;
	}

	public boolean offerLast(E e) {
		addLast(e);
        return true;
	}

	public E peek() {
		return peekFirst();
	}

	public E peekFirst() {
		return elements[head];
	}

	public E peekLast() {
		return elements[(tail - 1) & (elements.length - 1)];
	}

	public E poll() {
		return pollFirst();
	}

	public E pollFirst() {
		int h = head;
        E result = elements[h]; // Element is null if deque empty
        if (result == null)
            return null;
        elements[h] = null;     // Must null out slot
        head = (h + 1) & (elements.length - 1);
        return result;
	}

	public E pollLast() {
		int t = (tail - 1) & (elements.length - 1);
        E result = elements[t];
        if (result == null)
            return null;
        elements[t] = null;
        tail = t;
        return result;
	}

	public E pop() {
		return removeFirst();
	}

	public void push(E e) {
		addFirst(e);
	}

	public E remove() {
		return removeFirst();
	}

	public E removeFirst() {
		E x = pollFirst();
        if (x == null)
            throw new NoSuchElementException();
        return x;
	}

	public boolean removeFirstOccurrence(Object o) {
		if (o == null)
            return false;
        int mask = elements.length - 1;
        int i = head;
        E x;
        while ( (x = elements[i]) != null) {
            if (o.equals(x)) {
                delete(i);
                return true;
            }
            i = (i + 1) & mask;
        }
        return false;
	}

	public E removeLast() {
		E x = pollLast();
        if (x == null)
            throw new NoSuchElementException();
        return x;
	}

	public boolean removeLastOccurrence(Object o) {
		if (o == null)
            return false;
        int mask = elements.length - 1;
        int i = (tail - 1) & mask;
        E x;
        while ( (x = elements[i]) != null) {
            if (o.equals(x)) {
                delete(i);
                return true;
            }
            i = (i - 1) & mask;
        }
        return false;
	}

	public Iterator<E> iterator() {
		return new DeqIterator();
	}

	public int size() {
		return (tail - head) & (elements.length - 1);
	}
}
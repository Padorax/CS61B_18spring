import sun.font.TrueTypeFont;

import java.util.LinkedList;

public class LinkedListDeque<T> {

    private class TNode {
        public T item;
        public TNode next;
        public TNode prev;

        public TNode(T i, TNode n, TNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }
    private int size;
    private TNode sentinel;

    /**Create empty linked list deque*/
    public LinkedListDeque() {
        size = 0;
        //sentinel = new TNode(null, sentinel, sentinel) will cause prev, next to be null
        sentinel = new TNode(null,null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public LinkedListDeque(T i) {
        size = 1;
        sentinel = new TNode(null,null,null);//no default for T so use i
        sentinel.next = new TNode(i, sentinel, sentinel);
        sentinel.prev = sentinel.next;
    }

//all non-static
    public void addFirst(T item) {
        size += 1;
        sentinel.next = new TNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }

    public void addLast(T item) {
        size += 1;
        sentinel.prev = new TNode(item, sentinel, sentinel.prev);
        sentinel.prev.prev.next = sentinel.prev;
    }

    public boolean isEmpty() {

        if (sentinel.next == sentinel) return true;
        return false;
        /**
        if (size == 0) {
            return true;
        }
        return false;
         */
        /**
        if(sentinel.next.item == null) return true;
        return false;
        */
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        TNode ptr = sentinel.next;
        while(ptr.item != null) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
        /** not recommended
        sentinel = sentinel.next;
        while(sentinel.item != null) {
            System.out.print(sentinel.item + " ");
            sentinel = sentinel.next;
        }
         */
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        T remove = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return remove;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        T remove = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return remove;
    }
//do not alter the deck
    public T get(int index) {
        TNode ptr = sentinel;
        for(int i = 0; i < index+1; i++) {
            if (ptr.next.item == null){
                return null;
            }
            ptr = ptr.next;
        }
        return ptr.item;
    }

    /**same as get, use recursion*/
    public T getRecursive(int index) {
        if (index==0) {
            return sentinel.next.item;
        }
        T rm = this.removeFirst();
        if (rm != null) {
            return getRecursive(index - 1);
        }
        //sentinel.next is a TNode, not LLDeque, so it can't call getRecursive(it doesn't have 'sentinel')
        //return sentinel.next.getRecursive(index-1);
        return null;
    }
}
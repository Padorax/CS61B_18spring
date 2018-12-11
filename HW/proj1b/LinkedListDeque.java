import sun.font.TrueTypeFont;

import java.util.LinkedList;

public class LinkedListDeque<Item> implements Deque<Item> {
    private class TNode {
        public Item item;
        public TNode next;
        public TNode prev;

        public TNode(Item i, TNode n, TNode p) {
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

    public LinkedListDeque(Item i) {
        size = 1;
        sentinel = new TNode(null,null,null);//no default for T so use i
        sentinel.next = new TNode(i, sentinel, sentinel);
        sentinel.prev = sentinel.next;
    }

    @Override
    public void addFirst(Item x) {
        size += 1;
        sentinel.next = new TNode(x, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }

    @Override
    public void addLast(Item x) {
        size += 1;
        sentinel.prev = new TNode(x, sentinel, sentinel.prev);
        sentinel.prev.prev.next = sentinel.prev;
    }

    @Override
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

    @Override
    public int size() {
        return this.size;
    }

    @Override
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

    @Override
    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        Item remove = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return remove;
    }

    @Override
    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        Item remove = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return remove;
    }

    @Override
    public Item get(int index) {
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
    public Item getRecursive(int index) {
        if (index==0) {
            return sentinel.next.item;
        }
        Item rm = this.removeFirst();
        if (rm != null) {
            return getRecursive(index - 1);
        }
        //sentinel.next is a TNode, not LLDeque, so it can't call getRecursive(it doesn't have 'sentinel')
        //return sentinel.next.getRecursive(index-1);
        return null;
    }
}
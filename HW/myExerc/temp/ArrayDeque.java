public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int nextLast;
    private int nextFirst;
    private static int factor = 4;

    /**Create an empty arraydeque*/
    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public ArrayDeque(Item x) {
        items = (Item[]) new Object[8];
        size = 1;
        nextFirst = items.length -1;
        nextLast = 1;
        items[0] = x;
    }

    /**1.generate new bigger deque 2.copy old contents to new deque 3.point items to new deque*/
    public void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            a[i] = this.get(i+1);
        }
        items = a;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    public void addFirst(Item x) {
        if (size == items.length) {
            resize(factor*size);
        }
        size += 1;
        items[nextFirst] = x;
        nextFirst = prev(nextFirst);
    }

    public void addLast(Item x) {
        if (size == items.length) {
            resize(factor*size);
        }
        size += 1;
        items[nextLast] = x;
        nextLast = next(nextLast);
    }

    public boolean isEmpty() {
        if(size == 0) {return true;}
        return false;
    }

    public int size() {return size;}

    public void printDeque() {
        int ptr = nextFirst;
        for (int i = 0; i < size; i++) {
            ptr = next(ptr);
            System.out.print(items[ptr]+" ");
        }
    }

    /**without nulling out deleted item*/
    public Item removeFirst() {
        if(items.length >= 16 && ((double) size)/items.length < 0.25){
            resize(size*(factor-1));
        }
        size -= 1;
        nextFirst = next(nextFirst);
        return items[nextFirst];
    }

    public Item removeLast() {
        if(items.length >= 16 && ((double) size)/items.length < 0.25){
            resize(size*(factor-1));
        }
        size -= 1;
        nextLast = prev(nextLast);
        return items[nextLast];
    }

    public Item get(int index) {
        /**Not constant time
        int ptr = nextFirst;
        for(int i = 0; i < index; i++) {
            ptr = next(ptr);
        }
        return items[ptr];
         */
        return items[nextn(nextFirst,index)];
    }

    /**Obtain the position of previous index*/
    private int prev(int ptr) {
        if (ptr == 0) {
            return items.length-1;
        } else {
            return ptr-1;
        }
    }

    private int next(int ptr) {
        if (ptr == items.length-1) {
            return 0;
        } else {
            return ptr+1;
        }
    }
    /**Obtain the circular position of next n step, starting from current ptr*/
    private int nextn(int ptr, int n) {return (ptr+n)%(items.length);}
}
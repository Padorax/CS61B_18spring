package synthesizer;
import org.junit.Test;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T>{
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];//all null
        first = 0;
        last = 0;
        this.capacity = capacity;
        this.fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        this.fillCount += 1;
        rb[last] = x;
        last = (last + 1)%capacity;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        fillCount -= 1;
        T retVal = rb[first];
        if (first == 0) {
            first = capacity - 1;
        } else {
            first = first - 1;
        }
        return retVal;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        return rb[first];
    }

    // TODO:support iteration.
    @Override
    public Iterator<T> iterator() {
        return new PtrIterator();
    }

    private class PtrIterator implements Iterator<T> {//!no T for PtrIterator
        private int ptr;
        public PtrIterator() {
            ptr = first;
        }

        public boolean hasNext() {
            return (ptr != last);
        }
        public T next() {
            //T returnItem = peek();
            T returnItem = rb[ptr];
            ptr = (ptr + 1)%capacity;
            return returnItem;
        }
    }

}
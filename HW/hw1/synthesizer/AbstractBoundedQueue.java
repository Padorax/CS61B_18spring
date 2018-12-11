package synthesizer;

/**What is abstract class? implementing class that fails to implement any abstract methods
 * inheritated from an interface must be declared abstract.
 *
 * Note abstract class cannot be instantiated, but can be extended and have constructors.
 * We can also add additional abstract methods.
 *
 * Why use abstract class? here simply to provide protected variable that all subclasses
 * will inherit, PLUS "getter" methods capacity(), fillCount() (save us a tinny little work).
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int fillCount;

    protected int capacity;

    //seems a little weird
    public int capacity() {
        return capacity;
    }

    public int fillCount() {
        return fillCount;
    }

    /**
     * Since the below methods are inherited from BoundedQueue, we won't declare them
     * explicitly in this abstract class
     */
    //public boolean isEmpty()

    //public boolean isFull()

    //public abstract T peek();

    //public abstract T dequeue();

    //public abstract void enqueue(T x);
}

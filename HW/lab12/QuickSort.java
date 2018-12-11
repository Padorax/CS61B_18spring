import edu.princeton.cs.algs4.Queue;
import java.util.Random;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        for (Item item : unsorted) {
            if (item.compareTo(pivot) < 0) {
                less.enqueue(item);
            }else if(item.compareTo(pivot) > 0) {
                greater.enqueue(item);
            }else equal.enqueue(item);
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        //in Java passing arguments is pass by value(similar to sentinel in Linked List)
        // Your code here!
        if (items != null && items.size() > 1){
            Queue<Item> less = new Queue<>();
            Queue<Item> equal = new Queue<>();
            Queue<Item> greater = new Queue<>();
            Item pivot = getRandomItem(items);
            partition(items, pivot, less, equal, greater);
            less = quickSort(less);
            greater = quickSort(greater);
            items = catenate(less, equal);
            //we give inner items a totally new reference
            //(if we change it here like items.dequeue(), the items outside will also be changed)
            items = catenate(items, greater);
        }
        return items;
    }

    private static <Item> void printQueue(Queue<Item> q) {
        for (Item s : q) {
            System.out.println(s);
        }
        System.out.println();
    }

    public static <Item> String toString(Queue<Item> queue) {
        StringBuilder s = new StringBuilder();
        for (Item item : queue) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    public static void main(String[] args) {
        /*
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        printQueue(students);
        Queue<String> sortedStudents = quickSort(students);
        printQueue(sortedStudents);
        */
        Queue<Integer> q = new Queue<>();
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            q.enqueue(r.nextInt(100));
        }

        System.out.println("---unsorted---");
        for (int x : q) {
            System.out.print(x + ", ");
        }
        System.out.println("END");

        Queue<Integer> sortedQ = quickSort(q);
        System.out.println("---sorted---");
        for (int y : sortedQ) {
            System.out.print(y + ", ");
        }
        System.out.println("END");

        System.out.println("---unsorted---");
        for (int x : q) {
            System.out.print(x + ", ");
        }
        System.out.println("END");
    }
}

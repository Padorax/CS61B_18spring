import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // TODO:Your code here!
        Queue<Queue<Item>> singleItemQueues = new Queue<>();
        for (Item item : items) {
            //if Item is primitive type(eg. String), singleItemQueues and items will not be affected by each other
            Queue<Item> singleItemQueue = new Queue<Item>();
            singleItemQueue.enqueue(item);//singleItemQueue contain only one item
            singleItemQueues.enqueue(singleItemQueue);
        }
        return singleItemQueues;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // TODO: Your code here!
        /*
        Queue<Item> q = new Queue<>();
        int compareNum =  q1.size() + q2.size();
        for (int i = 0; i < compareNum; i++) {
            q.enqueue(getMin(q1, q2));
        }
        return q;
        */
        //opt by just copy if q1/q2 is empty
        Queue<Item> ret = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            ret.enqueue(getMin(q1, q2));
        }
        return ret;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // TODO: Your code here!
        //if (items.size() == 1) return items;
        //Queue<Queue<Item>> singleQueue = makeSingleItemQueues(items);
        Queue<Queue<Item>> qList = makeSingleItemQueues(items);
        while (qList.size() > 1) {
            Queue<Item> q1 = qList.dequeue();
            Queue<Item> q2 = qList.dequeue();
            qList.enqueue(mergeSortedQueues(q1, q2));
        }
        items = qList.dequeue();
        return items;
    }

    public static void main(String[] args) {

        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        Queue<String> sortedStudents = mergeSort(students);
        for (String s : sortedStudents) {
            System.out.println(s);
        }
        /*//help to understand queue of queue
        Queue<Queue<String>> s = new Queue<>();
        Queue<String> a = new Queue<>();
        a.enqueue("a");
        Queue<String> b = new Queue<>();
        b.enqueue("b");
        Queue<String> c = new Queue<>();
        c.enqueue("c");
        s.enqueue(a);
        s.enqueue(b);
        s.enqueue(c);
        //a.dequeue();
        //s.peek();
        Queue<Queue<String>> s2 = new Queue<>();
        for (Queue<String> q : s) {
            s2.enqueue(q);
            //in this way, change to s2 will reflect s1
        }*/
        //CHECK https://www.geeksforgeeks.org/merge-sort/
    }
}

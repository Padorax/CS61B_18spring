package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer<>(10);

        ArrayRingBuffer<Double> a = new ArrayRingBuffer<Double>(10);
        //!the left <Double> corresponds to the Double for d
        a.enqueue(33.1);
        a.enqueue(44.8);
        a.enqueue(62.3);
        /*
        Iterator<Double> seer = a.iterator();
        while(seer.hasNext()) {
            System.out.println(seer.next());
        }
        */
        for (Double d : a) {
            System.out.println(d);
        }

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 

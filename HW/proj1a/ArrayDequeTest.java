import org.junit.Test;
import static org.junit.Assert.*;
public class ArrayDequeTest {

    @Test
    public void Testaddremove() {
        ArrayDeque<Integer> A = new ArrayDeque<>();
        assert(A.isEmpty());
        A.addFirst(2);
        A.addFirst(5);
        A.addLast(8);
        A.addFirst(9);
        assertEquals(A.size(), 4);
        int a = A.removeFirst();
        assertEquals(a,9);
        int b = A.removeLast();
        assertEquals(b, 8);
        assertEquals(A.size(),2);
    }

    @Test
    public void Testgetprint() {
        ArrayDeque<String> A = new ArrayDeque<>("have");
        A.addFirst("i");
        A.addLast("an");
        A.addLast("apple");
        String actual = A.get(2);
        assertEquals("have",actual);
    }

    @Test
    public void Testresizeadd() {
        ArrayDeque<String> A = new ArrayDeque<>("have");
        A.addFirst("i");
        A.addLast("an");
        A.addLast("apple");
        A.addLast("i");
        A.addLast("have");
        A.addLast("a");
        A.addLast("pen");
        assertEquals(8,A.size());
        A.addLast("ah");
        assertEquals(9,A.size());
        A.addLast("applepen");
        //assertEquals(32,A.items.length());
        assertEquals("ah",A.get(9));
    }
}

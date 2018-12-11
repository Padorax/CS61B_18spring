package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        /* TODO: Write a test that ensures the hashCode is perfect,
          meaning no two SimpleOomages should EVER have the same
          hashCode UNLESS they have the same red, blue, and green values!
         */
        //try out every possible combination of red, green and blue to ensure never see the
        //same value more than once

        HashSet<Integer> hashSet = new HashSet<>();
        int count = 0;
        for (int r = 0; r <= 255; r += 5) {
            for (int g = 0; g <= 255; g += 5) {
                for (int b = 0; b <= 255; b += 5) {
                    count += 1;
                    SimpleOomage Oomage = new SimpleOomage(r, g, b);
                    hashSet.add(Oomage.hashCode());
                }
            }
        }
        assertEquals(count, hashSet.size());

        /*this works fine
        boolean collision = false;
        ArrayList<Integer> list = new ArrayList<>();
        for (int r = 0; r <= 255; r += 5) {
            for (int g = 0; g <= 255; g += 5) {
                for (int b = 0; b <= 255; b += 5) {
                    SimpleOomage Oomage = new SimpleOomage(r, g, b);
                    int hash = Oomage.hashCode();
                    if (list.contains(hash)) {
                        collision = true;
                        break;
                    }
                }
            }
        }
        assertTrue(collision);*/
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }


    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    /* TODO: Uncomment this test after you finish haveNiceHashCode Spread in OomageTestUtility */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}

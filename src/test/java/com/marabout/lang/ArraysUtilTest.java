package com.marabout.lang;

import static com.marabout.lang.ArrayUtils.*;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:gmarabout@gmail.com">Grégoire Marabout</a>
 */
public class ArraysUtilTest extends TestCase {


    public void testCastArray() {
        String[] casted = castArray(new Object[]{ "Hello", "World" }, String.class);

        assertNotNull(casted);
        assertEquals(2, casted.length);
        assertEquals("Hello", casted[0]);
        assertEquals("World", casted[1]);
    }

    public void testFlatten1() {
        String[][] strings = new String[][]{ { "Hello", "World" }, { "a", "b", "c" } };
        Object[] flattened = flatten(strings);

        assertNotNull(flattened);

        assertEquals(5, flattened.length);

        assertEquals("Hello", flattened[0]);
        assertEquals("World", flattened[1]);
        assertEquals("a", flattened[2]);
        assertEquals("b", flattened[3]);
        assertEquals("c", flattened[4]);
    }

    public void testFlatten2() {
        Object[] flattened = flatten(new String[][][]{ { { "Hello", "World" } }, { { "a", }, { "b" } }, { { "c" } } });

        assertNotNull(flattened);

        assertEquals(5, flattened.length);

        assertEquals("Hello", flattened[0]);
        assertEquals("World", flattened[1]);
        assertEquals("a", flattened[2]);
        assertEquals("b", flattened[3]);
        assertEquals("c", flattened[4]);
    }

    public void testRestStr() {
        String[] strings = new String[]{ "a", "b", "c" };
        String[] restStr = rest(strings);

        assertNotNull(restStr);
        assertEquals(2, restStr.length);
        assertEquals("b", restStr[0]);
        assertEquals("c", restStr[1]);
    }


    public void testRestInt() {
        Integer[] ints = new Integer[]{ 1, 2, 3, 4 };
        Integer[] restInt = rest(ints);

        assertNotNull(restInt);
        assertEquals(3, restInt.length);
        assertEquals(2, restInt[0].intValue());
        assertEquals(3, restInt[1].intValue());
        assertEquals(4, restInt[2].intValue());
    }

    public void testConcatEmpty() {
        String[] concat = concat(new String[]{ }, new String[]{ "Yes" });

        assertEquals(1, concat.length);
        assertEquals("Yes", concat[0]);

        concat = concat(new String[]{ }, new String[]{ });

        assertEquals(0, concat.length);
    }

    public void testConcatStrings() {
        String[] concat = concat(
            new String[]{ "a", "b" },
            new String[]{ },
            new String[]{ "c" },
            new String[]{ "d" },
            new String[]{ "e", "f", "g" });

        assertEquals(7, concat.length);

        assertEquals("a", concat[0]);
        assertEquals("b", concat[1]);
        assertEquals("c", concat[2]);
        assertEquals("d", concat[3]);
        assertEquals("e", concat[4]);
        assertEquals("f", concat[5]);
        assertEquals("g", concat[6]);
    }

}

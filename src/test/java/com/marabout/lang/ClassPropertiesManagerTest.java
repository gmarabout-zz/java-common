package com.marabout.lang;

import static com.marabout.lang.ClassPropertiesManager.getInt;
import static com.marabout.lang.ClassPropertiesManager.getString;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:gmarabout@gmail.com">Grégoire Marabout</a>
 */
public class ClassPropertiesManagerTest extends TestCase {
    public void testGetString() {
        String value1 = getString("value1", this);
        assertEquals("1", value1);
        value1 = getString("value1", this.getClass());
        assertEquals("1", value1);
        String value2 = getString("value2", this);
        assertEquals("2", value2);
        value2 = getString("value2", this.getClass());
        assertEquals("2", value2);
        String value3 = getString("value3", this);
        assertEquals("greg", value3);
        value3 = getString("value3", this.getClass());
        assertEquals("greg", value3);

        String unkwnon = getString("unknwon", this);
        assertNull(unkwnon);
    }

    public void testGetInt() {
        int value1 = getInt("value1", this);
        assertEquals(1, value1);
        int value2 = getInt("value2", this);
        assertEquals(2, value2);

        boolean exceptionThrown = false;
        try {
            getInt("value3", this);
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        int unkwnon = getInt("unknwon", this);
        assertEquals(0, unkwnon);
    }
}

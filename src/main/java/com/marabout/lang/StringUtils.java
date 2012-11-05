package com.marabout.lang;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;

/**
 * An utility class for String related operations.
 * 
 * @author <a href="mailto:gmarabout@gmail.com">Grégoire Marabout</a>
 */
public class StringUtils {

    /**
     * Returns a human readable string representation of a hash map.
     * 
     * @param map
     *            The map
     * @param <T>
     *            The map key type
     * @param <V>
     *            The map value type
     * @return A string representation of this map.
     */
    public static <T, V> String mapToString(Map<T, V> map) {
        StringBuilder sbuilder = new StringBuilder();
        sbuilder.append(" [ ");
        boolean first = true;
        for (Map.Entry<T, V> entry : map.entrySet()) {
            if (!first) {
                sbuilder.append(", ");
            }

            sbuilder.append(entry.getKey()).append(" : ");
            sbuilder.append(entry.getValue());
            first = false;
        }
        sbuilder.append(" ] ");
        return sbuilder.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> String collectionToString(Collection<T> collection,
            final ToStringer<T> toStringer) {
        final StringBuffer buffer = new StringBuffer();

        CollectionUtils.forAllDo(collection, new Closure() {

            public void execute(Object input) {
                T obj = (T) input;
                buffer.append(toStringer.toString(obj)).append("\n");
            }
        });
        return buffer.toString();
    }

    public static <T> String collectionToString(Collection<T> collection) {
        return collectionToString(collection, new ToStringer<T>() {
            public String toString(T arg) {
                return arg.toString();
            };
        });
    }

    public static interface ToStringer<T> {
        String toString(T arg);
    }
}

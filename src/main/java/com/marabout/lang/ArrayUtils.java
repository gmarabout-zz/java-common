package com.marabout.lang;

import java.lang.reflect.Array;

/**
 * A collection of utilities to ease the manipulation of Java arrays.
 *
 * @author <a href="mailto:gmarabout@gmail.com">Grégoire Marabout</a>
 */

@SuppressWarnings("unchecked")
public final class ArrayUtils {

    // You shall not instantiate me!
    private ArrayUtils() {
    }

    /**
     * Casts an array of objects into an array of the specified type.
     *
     * @param array       the array.
     * @param elementType the element type.
     * @param <T>         the element type.
     * @return the array of T.
     */
    public static <T> T[] castArray(Object[] array, Class<T> elementType) {
        T[] result = (T[]) Array.newInstance(elementType, array.length);
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    /**
     * Takes an array of many dimensions, and returns a 'flatten' array of dimension 1.
     * If the input is a single object (not an array), then it returns an array
     * with this object as single element.
     *
     * @param input an input.
     * @return The flatten array.
     */
    public static Object[] flatten(Object input) {
        if (input instanceof Object[]) {
            Object[] result = new Object[]{ };
            for (Object o : (Object[]) input) {
                result = concat(result, flatten(o));
            }
            return result;
        }
        return new Object[]{ input };
    }

    /**
     * Returns the first element of the specified array.
     *
     * @param elements the array.
     * @param <T>      The component type of the array
     * @return the first element.
     */
    public static <T> T first(T[] elements) {
        if (elements != null && elements.length > 0)
            return elements[0];
        return null;
    }

    /**
     * Returns the array specified minus the first element.
     *
     * @param elements the array.
     * @param <T>      the component type of the array.
     * @return the rest of the array.
     */
    public static <T> T[] rest(T[] elements) {
        T[] newArray = (T[]) Array.newInstance(elements.getClass().getComponentType(),
            elements.length - 1);
        System.arraycopy(elements, 1, newArray, 0, elements.length - 1);
        return newArray;
    }


    /**
     * Returns a new array which is the concatenation of the specified
     * arrays.
     *
     * @param arrays An array of array to concatenate.
     * @param <T>    the arrays to concat.
     * @return concatenated arrays as a new array.
     */
    public static <T> T[] concat(T[]... arrays) {
        assert arrays.length > 0;
        int size = 0;
        for (T[] array : arrays)
            size += array.length;
        T[] concat = (T[]) Array.newInstance(arrays[0].getClass().getComponentType(), size);
        int position = 0;
        for (T[] array : arrays) {
            System.arraycopy(array, 0, concat, position, array.length);
            position += array.length;
        }
        return concat;
    }

    /**
     * Appends element at the end of array, and returns a new array.
     *
     * @param array   the array.
     * @param element the element.
     * @return the new array.
     */
    public static <T> T[] append(T[] array, T element) {
        T[] cons = (T[]) Array.newInstance(element.getClass(), array.length + 1);
        System.arraycopy(array, 0, cons, 0, array.length);
        cons[array.length] = element;
        return cons;
    }


}
